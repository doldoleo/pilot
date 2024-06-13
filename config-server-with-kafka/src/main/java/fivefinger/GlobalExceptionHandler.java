package fivefinger;



import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.config.server.environment.EnvironmentNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * profile 이 없으면 empty로 return
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EnvironmentNotFoundException.class)
    public Map<String, String> handleEnvironmentNotFoundException(EnvironmentNotFoundException e) {
	     Map<String, String> emptyProfile = new HashMap<>();
	     return emptyProfile;
    }
	
}
