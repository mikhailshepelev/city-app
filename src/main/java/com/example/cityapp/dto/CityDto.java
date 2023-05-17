package com.example.cityapp.dto;

import com.example.cityapp.domain.CityEntity;

public record CityDto(long id, String name, String imageUrl) {
    public CityDto(CityEntity entity) {
        this(entity.getId(), entity.getName(), entity.getImageUrl());
    }
}
