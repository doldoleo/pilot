package com.example.demo;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "결재 서비스 V1", description = "결재 관련 API(기본)")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payment/v1")
@RestController
public class PaymentServiceController {
		
	 	@Value("${server.port}")
	    private String port;

	 	@Operation(summary = "결재상태 확인", 
				   description = "결재상태를 반환하는 인터페이스", 
				   responses = {
				                 @ApiResponse(responseCode = "200", description = "결재상태를 반환함.") 
				                 })
	    @GetMapping("/check")
	    public String check() {
	    	 log.trace("TRACE 로그!!");
	    	 log.debug("DEBUG 로그!!");
	    	 log.info("INFO 로그!!");
	    	 log.warn("WARN 로그!!");
	    	 log.error("ERROR 로그!!");
	        return String.format("First Service on PORT %s", port);
	       
	    }
}
