package com.example.cityapp.dto;

import java.util.List;

public record CustomPage<C>(int page, int perPage, long totalElements, List<C> content) {
}
