package com.example.demo.greeting.web;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.greeting.model.Greeting;
import com.example.demo.greeting.service.GreetingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자 서비스 V1", description = "사용자 관련 API(기본)")
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class GreetingController {
	private final GreetingService service;

	@Operation(summary = "사용자 조회(인증된 사람만)", 
			   description = "사용자를 조회한 정보를 반환함", 
			   responses = {
			                 @ApiResponse(responseCode = "200", description = "조회된 사용자 정보를 반환함.") ,
			                 @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자는 권한없음을 반환함.") 
			                 })
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return service.greeting(name);
	}
	
	
	@Operation(summary = "사용자 조회(누구나)", 
			   description = "사용자를 조회한 정보를 반환함", 
			   parameters = {
					   
			   },
			   responses = {
			                 @ApiResponse(responseCode = "200", description = "조회된 사용자 정보를 반환함.") 
			                 })
	@GetMapping("/nogreeting")
	public Greeting nogreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return service.greetingbynoauth(name);
	}
	
	@Operation(summary = "맵테스트", 
			   description = "사용자를 조회한 정보를 반환함",
			  
			   responses = {
			                 @ApiResponse(responseCode = "200", description = "조회된 사용자 정보를 반환함.") 
			                 })
	 @io.swagger.v3.oas.annotations.parameters.RequestBody(
	            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
	            schema = @Schema(ref = "#/components/schemas/MyCustomSchema")))
	@PostMapping("/map")
	public Map<String, String> map(
			@RequestBody
			Map<String, String> params ) {
		return params;
	}
}
