package com.example.demo.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix="config-service")
@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class MyConfig {
	private String profile;
    private String region;
}