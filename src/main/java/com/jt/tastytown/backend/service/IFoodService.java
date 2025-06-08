package com.jt.tastytown.backend.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jt.tastytown.backend.dto.FoodRequestDTO;
import com.jt.tastytown.backend.dto.FoodResponseDTO;

public interface IFoodService {
    FoodResponseDTO createFood(FoodRequestDTO requestDTO, MultipartFile file) throws IOException;
}
