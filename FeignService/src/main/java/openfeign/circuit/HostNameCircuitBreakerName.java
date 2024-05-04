package openfeign.circuit;


import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.stereotype.Component;

import feign.Target;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HostNameCircuitBreakerName implements CircuitBreakerNameResolver {
	
	@Override
	public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {
		String url = target.url();
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            log.error("MalformedURLException is ouccered: {}", url);
            return "default";
        }
	}

}
