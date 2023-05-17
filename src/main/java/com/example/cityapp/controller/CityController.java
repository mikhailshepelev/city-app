package com.example.cityapp.controller;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.dto.CityDto;
import com.example.cityapp.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/cities-paginated")
    public ResponseEntity<Page<CityDto>> getEntities(@RequestParam("keyword") String keyword,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("perPage") int perPage) {
        Page<CityEntity> entities = cityRepository.findByNameContaining(
                Objects.equals(keyword, "null") ? "" : keyword,
                PageRequest.of(page, perPage)
        );
        Page<CityDto> result = entities.map(CityDto::new);
        return ResponseEntity.ok(result);
    }
}
