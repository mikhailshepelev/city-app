package com.example.cityapp.controller;

import com.example.cityapp.dto.CityDto;
import com.example.cityapp.dto.CustomPage;
import com.example.cityapp.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<CustomPage<CityDto>> searchCities(@RequestParam("keyword") String keyword,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("perPage") int perPage) {
        return ResponseEntity.ok(cityService.search(keyword, page, perPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCity(@PathVariable long id) {
        Optional<CityDto> optCity = cityService.getById(id);
        return optCity.map(cityDto -> new ResponseEntity<>(cityDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }


    @PostMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable long id,
                                        @RequestPart(value = "image", required = false) MultipartFile image,
                                        @RequestPart("name") String name) throws IOException {

        return ResponseEntity.ok(cityService.save(id, name, image));
    }
}
