package com.example.cityapp.controller;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.repository.CityRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
public record CityController(CityRepository cityRepository) {

    @GetMapping("/cities")
    public List<CityEntity> getAllEntities() {
        return cityRepository.findAll();
    }

    @GetMapping("/cities/search")
    public List<CityEntity> searchCityByName(@RequestParam("name") String cityName) {
        return cityRepository.findByNameContaining(cityName);
    }
}
