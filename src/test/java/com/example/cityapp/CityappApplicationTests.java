package com.example.cityapp;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "command.line.runner.enabled=false")
public class CityappApplicationTests {
}
