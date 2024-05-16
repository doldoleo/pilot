package komsco.feign.handler;

import java.util.Collections;

import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FeignException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public  ResponseEntity<?> feignExceptionHandler(FeignException ex) {
        ex.printStackTrace();
//        return ex.getMessage();
		return ResponseEntity.badRequest().body(Collections.singletonMap("code", ex.getMessage()));
    }
	
//	@ExceptionHandler(FeignException.class)
//	public ResponseEntity<?> handleFeignException(FeignException e) {
//		return ResponseEntity.badRequest().body(Collections.singletonMap("code", "FeignException"));
//	}

	@ExceptionHandler(NoFallbackAvailableException.class)
	public ResponseEntity<?> handleNoFallbackAvailableException(NoFallbackAvailableException e) {
		return ResponseEntity.badRequest().body(Collections.singletonMap("code", "NoFallbackAvailableException"));
	}

	@ExceptionHandler(CallNotPermittedException.class)
	public ResponseEntity<?> handleCallNotPermittedException(CallNotPermittedException e) {
		return ResponseEntity.internalServerError().body(Collections.singletonMap("code", "CallNotPermittedException"));
	}
	
}
