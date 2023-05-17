package com.example.cityapp.dto;

public record CitySearchRequest(String keyword, int page, int perPage) {}