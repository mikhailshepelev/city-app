package com.example.cityapp.service;

import com.example.cityapp.dto.CityDto;
import com.example.cityapp.dto.CustomPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CityService {
    CustomPage<CityDto> search(String keyword, int page, int perPage);
    Optional<CityDto> getById(long id);
    CityDto save(long id, String name, MultipartFile image) throws IOException;
}
