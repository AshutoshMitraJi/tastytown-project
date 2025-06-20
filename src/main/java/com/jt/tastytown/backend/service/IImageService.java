package com.jt.tastytown.backend.service;

import java.io.IOException;

public interface IImageService {
    byte[] extractFoodImage(String foodImageName) throws IOException;
}