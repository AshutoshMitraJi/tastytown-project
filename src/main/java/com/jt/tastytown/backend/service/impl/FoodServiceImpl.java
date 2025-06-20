package com.jt.tastytown.backend.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.tastytown.backend.dto.FoodRequestDTO;
import com.jt.tastytown.backend.dto.FoodResponseDTO;
import com.jt.tastytown.backend.entity.Food;
import com.jt.tastytown.backend.mapper.FoodMapper;
import com.jt.tastytown.backend.repository.CategoryRepository;
import com.jt.tastytown.backend.repository.FoodRepository;
import com.jt.tastytown.backend.service.ICategoryService;
import com.jt.tastytown.backend.service.IFoodService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements IFoodService{
    private final ICategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;

    @Value("${upload.file.dir}")
    private String FILE_DIR;

    @Override
    public FoodResponseDTO createFood(FoodRequestDTO requestDTO, MultipartFile foodImageFile) throws IOException {
        var existingCategory = categoryService.getCategoryById(requestDTO.categoryId());

        //Save the image in the folder
        var fileName = uploadFile(foodImageFile);
        


        //Save the food in the database
        var food = FoodMapper.convertToEntity(requestDTO, existingCategory, fileName);        
        var savedFood = foodRepository.save(food);

        return FoodMapper.convertToDTO(savedFood);
    }

    @Override
    public List<FoodResponseDTO> getAllFoods() {
        var foods = foodRepository.findAll();
               
        // foods.stream().map((food) -> FoodMapper.convertToDTO(food)).toList();
        
        return foods.stream().map(FoodMapper :: convertToDTO).toList();
    }
    
    @Override
    public FoodResponseDTO getFoodById(String foodId) {
        var food = foodRepository.findById(foodId).orElseThrow(() -> new NoSuchElementException("Food Not Found by ID: " + foodId));
        return FoodMapper.convertToDTO(food);
    }

    @Override
    public Page<FoodResponseDTO> getPaginatedFoods(int pageNumber, int pageSize, String categoryId, String search) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        
        //filteration
        Page<Food> foodPage;
        if (!categoryId.equals("all") && !search.equals("all")) {
            foodPage = foodRepository.findByCategory_CategoryIdAndFoodNameContainingIgnoreCase(categoryId, search, pageable);
        } else if(!categoryId.equals("all")) {
            foodPage = foodRepository.findByCategory_CategoryId(categoryId, pageable);
        } else if(!search.equals("all")) {
            foodPage = foodRepository.findByFoodNameContainingIgnoreCase(search, pageable);
        } else {
            foodPage = foodRepository.findAll(pageable);
        }
        
        // Page<Food> foods = foodRepository.findAll(pageable);
        return foodPage.map(FoodMapper :: convertToDTO);
    }
    
    private String uploadFile(MultipartFile foodImage) throws IOException{
        if(!foodImage.isEmpty()) {
            var fileName = foodImage.getOriginalFilename(); //It extracts the full name including extension
            var newFileName = generateFileName(fileName);
            var fos = new FileOutputStream(FILE_DIR + File.separator + newFileName);
            fos.write(foodImage.getBytes());
            fos.close();
            return newFileName;
        }
        throw new FileNotFoundException("Food Image Not Found");
    }
    
    private String generateFileName(String fileName){
        var extensionName = fileName.substring(fileName.lastIndexOf('.'));
        var newFileName = UUID.randomUUID().toString();
        return newFileName + extensionName;
    }

    @Override
    public FoodResponseDTO deleteFoodById(String foodId) throws IOException {
        var food = foodRepository.findById(foodId).orElseThrow(() -> new NoSuchElementException("Food Not found by ID"));
        deleteFoodImage(food.getFoodImage());
        foodRepository.delete(food);
        return FoodMapper.convertToDTO(food);
    }
    
    @Override
    public FoodResponseDTO updateFoodById(String foodId, FoodRequestDTO dto, MultipartFile foodImage) throws IOException {
        var food = foodRepository.findById(foodId).orElseThrow(() -> new NoSuchElementException("Food Not found by ID"));
        food.setFoodName(dto.foodName());
        food.setFoodDescription(dto.foodDescription());
        food.setFoodPrice(dto.foodPrice());
        
        if(dto.categoryId() != null && !dto.categoryId().isEmpty()){
            var category = categoryService.getCategoryById(dto.categoryId());
            food.setCategory(category);
        }

        if (foodImage != null && !foodImage.isEmpty()) {
            //Update
            //-Delete existing
            deleteFoodImage(food.getFoodImage());
            //-Save new file
            var newFoodImageName = uploadFile(foodImage);
            food.setFoodImage(newFoodImageName);
        }

        var savedFood = foodRepository.save(food);
        return FoodMapper.convertToDTO(savedFood);
    }

    private void deleteFoodImage(String foodImageName) throws IOException {
        var file = new File(FILE_DIR + File.separator + foodImageName);
        if(!file.exists()) {
            throw new FileNotFoundException("Food Image Not Found");
        }
        file.delete();
    }
}
