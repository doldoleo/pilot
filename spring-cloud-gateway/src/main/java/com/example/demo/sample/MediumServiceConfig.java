package com.example.demo.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "echo.message")
public class MediumServiceConfig {

    private String text;
}
