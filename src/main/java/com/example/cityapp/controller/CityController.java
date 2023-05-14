package com.example.cityapp.controller;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.repository.CityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public record CityController(CityRepository cityRepository) {

    @GetMapping("/cities")
    public List<CityEntity> getAllEntities() {
        return cityRepository.findAll();
    }
}
