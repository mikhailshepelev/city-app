package com.example.cityapp.data;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.repository.CityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Service
public class CsvDataLoader implements CommandLineRunner {

    @Value("classpath:data/cities.csv")
    private Resource dataResource;

    private final CityRepository cityRepository;

    private static final int EXPECTED_COLUMNS = 3;

    public CsvDataLoader(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void setDataResource(Resource dataResource) {
        this.dataResource = dataResource;
    }

    @Override
    public void run(String... args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != EXPECTED_COLUMNS) {
                    throw new IllegalArgumentException("Invalid number of columns: " + values.length);
                }
                long id;
                try {
                    id = Long.parseLong(values[0]);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid city id: " + values[0] + ", should be a number");
                }
                CityEntity cityEntity = new CityEntity(id, values[1], values[2]);
                cityRepository.save(cityEntity);
            }
        }
    }
}
