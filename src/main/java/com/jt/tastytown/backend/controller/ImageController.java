package com.jt.tastytown.backend.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.tastytown.backend.service.IImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/images")
@Tag(name="Image API", description="This controller is responsible for extraction of the food image")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;


    @GetMapping("/{fileName}")
    @ApiResponse(responseCode="200", description="Extract Food Image with Image Name")
    @Operation(summary="Extracted Food Image")
    public ResponseEntity<byte[]> serveImage(@PathVariable String fileName) throws IOException {
        byte[] image = imageService.extractFoodImage(fileName);
        
        var lowerFoodImageName = fileName.toLowerCase();
        String contentType = "";
        if(lowerFoodImageName.endsWith(".jpg") || lowerFoodImageName.endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        } else if(lowerFoodImageName.endsWith(".png")) {
            contentType = MediaType.IMAGE_PNG_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(image);
    }    
}
