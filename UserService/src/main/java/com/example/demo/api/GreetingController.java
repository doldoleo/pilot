package com.example.demo.api;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "사용자 서비스 V1", description = "사용자 관련 API(기본)")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
@RestController
public class GreetingController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Operation(summary = "사용자 조회", 
			   description = "사용자를 조회한 정보를 반환함", 
			   responses = {
			                 @ApiResponse(responseCode = "200", description = "조회된 사용자 정보를 반환함.") 
			                 })
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("Hello sleuth");
		log.info("Bye sleuth");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
}
