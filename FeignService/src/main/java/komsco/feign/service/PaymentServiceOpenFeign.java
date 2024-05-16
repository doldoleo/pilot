package komsco.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import komsco.feign.fallback.FeignPaymentRemoteServiceFallbackFactory;


@FeignClient(name = "payment", fallbackFactory = FeignPaymentRemoteServiceFallbackFactory.class)
public interface PaymentServiceOpenFeign {
	@GetMapping("/api/v1/payment/check")
	String check();
}
