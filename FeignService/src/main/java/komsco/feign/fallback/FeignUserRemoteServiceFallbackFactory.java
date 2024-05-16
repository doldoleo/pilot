package komsco.feign.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import komsco.feign.model.Greeting;
import komsco.feign.service.UserServiceOpenFeign;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignUserRemoteServiceFallbackFactory implements FallbackFactory<UserServiceOpenFeign> {

	@Override
	public UserServiceOpenFeign create(Throwable cause) {
		log.error("t = " + cause);
		return new UserServiceOpenFeign() {
            @Override
            public Greeting greeting(String error) {
                return new Greeting(0, error);
            }
        };
	}

}
