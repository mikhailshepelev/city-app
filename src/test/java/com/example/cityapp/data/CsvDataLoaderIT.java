package com.example.cityapp.data;

import com.example.cityapp.CityappApplicationTests;
import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvDataLoaderIT extends CityappApplicationTests {

    @Autowired
    private CityRepository cityRepository;

    @DisplayName("CsvDataLoader test: with valid csv data")
    @Test
    void testCsvDataLoader__withValidCsvData() throws IOException {
        CsvDataLoader csvDataLoader = new CsvDataLoader(cityRepository);
        csvDataLoader.setDataResource(new ClassPathResource("data/cities-valid.csv"));

        csvDataLoader.run();

        List<CityEntity> result = cityRepository.findAll();

        assertThat(result.get(0)).extracting(CityEntity::getId, CityEntity::getName, CityEntity::getImageUrl)
                .containsExactly(1L, "Tokyo", "https://upload.wikimedia.org/.jpg");
        assertThat(result.get(1)).extracting(CityEntity::getId, CityEntity::getName, CityEntity::getImageUrl)
                .containsExactly(2L, "Jakarta", "https://wikipedia/commons.jpg");
        assertThat(result.get(2)).extracting(CityEntity::getId, CityEntity::getName, CityEntity::getImageUrl)
                .containsExactly(3L, "Delhi", "https://wikipedia/5/55/IN-DL.svg/439px-IN-DL.svg.png");
    }

    @DisplayName("CsvDataLoader test: with invalid id in csv - should throw IllegalArgumentException")
    @Test
    void testCsvDataLoader__withInvalidId() {
        CsvDataLoader csvDataLoader = new CsvDataLoader(cityRepository);
        csvDataLoader.setDataResource(new ClassPathResource("data/cities-invalid-id.csv"));

        assertThrows(IllegalArgumentException.class, csvDataLoader::run);
    }

    @DisplayName("CsvDataLoader test: with invalid column amount - should throw IllegalArgumentException")
    @Test
    void testCsvDataLoader__withInvalidColumnAmount() {
        CsvDataLoader csvDataLoader = new CsvDataLoader(cityRepository);
        csvDataLoader.setDataResource(new ClassPathResource("data/cities-invalid-column-amount.csv"));

        assertThrows(IllegalArgumentException.class, csvDataLoader::run);
    }
}