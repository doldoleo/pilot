package fivefinger.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 636922550403085200L;

	public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}