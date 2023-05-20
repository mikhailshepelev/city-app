package com.example.cityapp.controller;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.dto.CityDto;
import com.example.cityapp.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/cities")
public record CityController(CityRepository cityRepository) {

    @GetMapping
    public ResponseEntity<Page<CityDto>> searchCities(@RequestParam("keyword") String keyword,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("perPage") int perPage) {
        Page<CityEntity> entities = cityRepository.findByNameContaining(
                Objects.equals(keyword, "null") ? "" : keyword,
                PageRequest.of(page, perPage)
        );
        Page<CityDto> result = entities.map(CityDto::new);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public CityDto getCity(@PathVariable long id) {
        CityEntity entity = cityRepository.findById(id).orElseThrow();
        return new CityDto(entity);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable long id,
                                        @RequestPart(value = "image", required = false) MultipartFile file,
                                        @RequestPart("name") String name) throws IOException {
        CityEntity entity = cityRepository.findById(id).orElseThrow();
        if (file != null) {
            entity.setImage(file.getBytes());
            entity.setImageUrl(null);
        }
        entity.setName(name);

        cityRepository.save(entity);

        return ResponseEntity.ok(entity);
    }
}
