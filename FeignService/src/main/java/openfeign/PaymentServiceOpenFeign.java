package openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "payment")
public interface PaymentServiceOpenFeign {
	@GetMapping("/api/v1/payment/check")
	String check();
}
