package com.example.demo.api.web;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "결제 서비스 V1", description = "결제 관련 API(기본)")
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@RestController
public class PaymentServiceController {
		private final PaymentService paymentService;
	 	@Operation(summary = "결제상태 확인", 
				   description = "결제상태를 반환하는 인터페이스", 
				   responses = {
				                 @ApiResponse(responseCode = "200", description = "결제상태를 반환함."), 
				                 @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자가 호출했을 경우")
				                 })
	    @GetMapping("/check")
	    public String check() {
	    	 return paymentService.check();
	    }
}
