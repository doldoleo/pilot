package openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import openfeign.model.Greeting;


@FeignClient(name = "user")
public interface UserServiceOpenFeign {
	@GetMapping("/api/v1/user/greeting")
	Greeting greeting(
			@RequestParam(required = false, name = "name") String name);
}
