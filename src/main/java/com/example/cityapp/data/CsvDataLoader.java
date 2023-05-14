package com.example.cityapp.data;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CsvDataLoader implements CommandLineRunner {

    @Value("classpath:data/cities.csv")
    private Resource dataResource;

    @Autowired
    private CityRepository cityRepository;

    private static final int EXPECTED_COLUMNS = 3;

    @Override
    public void run(String... args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != EXPECTED_COLUMNS) {
                    throw new IllegalArgumentException("Invalid number of columns: " + values.length);
                }
                CityEntity cityEntity = new CityEntity(Long.parseLong(values[0]), values[1], values[2]);
                cityRepository.save(cityEntity);
            }
        }
    }
}
