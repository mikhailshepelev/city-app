package com.example.cityapp.service;

import com.example.cityapp.domain.CityEntity;
import com.example.cityapp.dto.CityDto;
import com.example.cityapp.dto.CustomPage;
import com.example.cityapp.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CustomPage<CityDto> search(String keyword, int page, int perPage) {
        Page<CityEntity> entities = cityRepository.findByNameContaining(keyword, PageRequest.of(page, perPage));
        return new CustomPage<>(
                entities.getNumber(),
                entities.getSize(),
                entities.getTotalElements(),
                entities.getContent().stream().map(CityDto::new).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<CityDto> getById(long id) {
        Optional<CityEntity> entity = cityRepository.findById(id);
        return entity.map(CityDto::new);
    }

    @Override
    public CityDto save(long id, String name, MultipartFile image) throws IOException {
        CityEntity entity = cityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("CityEntity not found with given id: " + id)
        );

        if (image != null) {
            entity.setImage(CompressionService.compressBytes(image.getBytes()));
            entity.setImageUrl(null);
        }
        entity.setName(name);

        cityRepository.save(entity);
        return new CityDto(entity);
    }
}
