package com.example.cityapp.repository;

import com.example.cityapp.domain.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Page<CityEntity> findByNameContaining(String name, Pageable pageable);
}
