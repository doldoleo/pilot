package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "동적환경설정제공 서비스", description = "자동으로 환경이 변경되면  서버에 자동반환되는 서비스")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ConfigController {
	
	private final MyConfig myConfig;

	@Operation(summary = "설정조회", 
			   description = "property에서 profile과 region 정보를 조회합니다.", 
			   responses = {
			                 @ApiResponse(responseCode = "200", description = "profile, region정보 반환 ")
			                 })
    @GetMapping("/config")
    public ResponseEntity<String> config() {
        log.debug(myConfig.toString());
        return ResponseEntity.ok(myConfig.toString());
    }
}
