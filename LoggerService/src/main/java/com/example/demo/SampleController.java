package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/logger")
public class SampleController {
	@Value("${server.port}")
    private String port;
	
	@GetMapping("/logging")
    public String logging() {
    	 log.trace("TRACE 로그!!");
    	 log.debug("DEBUG 로그!!");
    	 log.info("INFO 로그!!");
    	 log.warn("WARN 로그!!");
    	 log.error("ERROR 로그!!");
        return String.format("First Service on PORT %s", port);
    }
}
