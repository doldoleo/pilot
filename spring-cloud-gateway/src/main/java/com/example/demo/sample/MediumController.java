package com.example.demo.sample;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MediumController {

    private final MediumServiceConfig config;

    @GetMapping("/sayHello")
    public String getMessage() {
    	log.info("test:{} ", config.getText());
        return config.getText();
    }
}
