package com.example.cityapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CompressionServiceTest {

    @DisplayName("Compression should shrink file, byte[] are equal after compression/decompression")
    @Test
    public void testCompression() throws IOException {
        File image = new ClassPathResource("files/heavy-image.jpg").getFile();
        byte[] initialBytes = Files.readAllBytes(image.toPath());

        byte[] compressedData = CompressionService.compressBytes(initialBytes);
        assertThat(compressedData)
                .isNotNull()
                .isNotEmpty()
                .hasSizeLessThan(initialBytes.length);

        byte[] decompressedData = CompressionService.decompressBytes(compressedData);
        assertThat(decompressedData)
                .isNotNull()
                .isNotEmpty()
                .hasSizeGreaterThan(compressedData.length);

        assertArrayEquals(initialBytes, decompressedData);
    }
}