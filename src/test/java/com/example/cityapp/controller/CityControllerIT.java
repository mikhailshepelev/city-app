package com.example.cityapp.controller;

import com.example.cityapp.CityappApplicationTests;
import com.example.cityapp.dto.CityDto;
import com.example.cityapp.dto.CustomPage;
import com.example.cityapp.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Sql(scripts = {
        "classpath:scripts/delete-data.sql",
        "classpath:scripts/cities.sql"
})
class CityControllerIT extends CityappApplicationTests {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("Search cities integration test: with not empty keyword")
    @Test
    void testSearchCities__notNullKeyword() {
        ResponseEntity<CustomPage<CityDto>> response = testRestTemplate.exchange(
                "/cities?keyword={keyword}&page={page}&perPage={perPage}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                Map.of("keyword", "o", "page", "3", "perPage", "3")
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertNotNull(response.getBody());

        CustomPage<CityDto> body = response.getBody();
        assertThat(body.page()).isEqualTo(3);
        assertThat(body.perPage()).isEqualTo(3);
        assertThat(body.totalElements()).isEqualTo(13);
        List<CityDto> cities = body.content();
        assertThat(cities).extracting(CityDto::id).containsExactly(16L, 17L, 18L);
    }

    @DisplayName("Search cities integration test: with empty keyword")
    @Test
    void testSearchCities__emptyKeyword() {
        ResponseEntity<CustomPage<CityDto>> response = testRestTemplate.exchange(
                "/cities?keyword={keyword}&page={page}&perPage={perPage}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                Map.of("keyword", "", "page", "2", "perPage", "4")
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertNotNull(response.getBody());

        CustomPage<CityDto> body = response.getBody();
        assertThat(body.page()).isEqualTo(2);
        assertThat(body.perPage()).isEqualTo(4);
        assertThat(body.totalElements()).isEqualTo(20);
        List<CityDto> cities = body.content();
        assertThat(cities).extracting(CityDto::id).containsExactly(9L, 10L, 11L, 12L);
    }


    @DisplayName("Get city integration test: with valid id")
    @Test
    void testGetCity__validId() {
        ResponseEntity<CityDto> response = testRestTemplate.getForEntity("/cities/7", CityDto.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertNotNull(response.getBody());
        CityDto dto = response.getBody();
        assertThat(dto).extracting(CityDto::id, CityDto::name, CityDto::imageUrl)
                .containsExactly(7L, "Sydney", "http://example.com/sydney.jpg");
    }

    @DisplayName("Get city integration test: with invalid id - should return 404")
    @Test
    void testGetCity__withInvalidId_shouldReturn404() {
        ResponseEntity<CityDto> response = testRestTemplate.getForEntity("/cities/25", CityDto.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertNull(response.getBody());
    }

    @DisplayName("Update city integration test: with valid file")
    @Test
    void testUpdateCity__withValidFile() throws IOException {
        Resource resource = new ClassPathResource("files/sample-image.jpeg");
        File file = resource.getFile();
        HttpEntity<FileSystemResource> imageEntity = new HttpEntity<>(
                new FileSystemResource(resource.getFile())
        );
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("name", "New name");
        requestBody.add("image", imageEntity);

        // Send the PATCH request to update the city
        ResponseEntity<CityDto> response = testRestTemplate.postForEntity(
                "/cities/{id}", new HttpEntity<>(requestBody), CityDto.class, 5L
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).extracting(CityDto::id, CityDto::name, CityDto::imageUrl, CityDto::image)
                .containsExactly(5L, "New name", null, Files.readAllBytes(file.toPath()));
    }

    @DisplayName("Update city integration test: with only name - no file")
    @Test
    void testUpdateCity__withOnlyNameNoFile() {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("name", "New name");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<CityDto> response = testRestTemplate.postForEntity(
                "/cities/{id}",
                new HttpEntity<>(requestBody, headers),
                CityDto.class,
                14L
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).extracting(CityDto::id, CityDto::name, CityDto::imageUrl, CityDto::image)
                .containsExactly(14L, "New name", "https://example.com/rio.jpg", null);
    }
}