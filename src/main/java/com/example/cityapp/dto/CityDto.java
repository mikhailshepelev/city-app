package com.example.cityapp.dto;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.service.CompressionService;

public record CityDto(long id, String name, String imageUrl, byte[] image) {
    public CityDto(CityEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getImage() != null ? CompressionService.decompressBytes(entity.getImage()) : null
        );
    }
}
