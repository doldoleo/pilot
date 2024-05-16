package komsco.api.web;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import komsco.feign.model.Greeting;
import komsco.feign.service.PaymentServiceOpenFeign;
import komsco.feign.service.UserServiceOpenFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Feign 서비스 V1", description = "MSA간 서비스 호출 하는 방법과 관련된 Controller")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/merge")
@RestController
public class OpenFeignTestController {
	@Autowired
	private UserServiceOpenFeign userOpenFeign;

	@Autowired
	private PaymentServiceOpenFeign paymentOpenFeign;

	@Operation(summary = "", description = "사용자 서비스와 지급결제를 서비스를 호출해서 ", responses = {
			@ApiResponse(responseCode = "200", description = "feign 서비스 처리결과 반환함.") })
	@GetMapping("/call")
	public Greeting call() {
		String resultStr = paymentOpenFeign.check();
		log.debug("paymentOpenFeign ==>" + resultStr);
		Greeting result = userOpenFeign.greeting(resultStr);
		return result;
	}


}
