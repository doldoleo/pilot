package komsco.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import komsco.feign.fallback.FeignUserRemoteServiceFallbackFactory;
import komsco.feign.model.Greeting;


@FeignClient(name = "user", fallbackFactory = FeignUserRemoteServiceFallbackFactory.class)
public interface UserServiceOpenFeign {
	@GetMapping("/api/v1/user/greeting")
	Greeting greeting(
			@RequestParam(required = false, name = "name") String name);
}
