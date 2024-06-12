package fivefinger.oauth2.core.user.factory;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import fivefinger.oauth2.core.user.model.oidc.DefaultOidcUserInfo;
import fivefinger.oauth2.core.user.model.oidc.KomscoOidcUserInfo;
import fivefinger.oauth2.core.user.provider.OAuth2Provider;
import fivefinger.oauth2.exception.OAuth2AuthenticationProcessingException;

public class OidcUserInfoFactory {

	public static DefaultOidcUserInfo getOAuth2UserInfo(String registrationId, String accessToken, OidcIdToken idToken, Map<String, Object> attributes) {
		if (OAuth2Provider.KOMSCO.getRegistrationId().equals(registrationId)) {
			return new KomscoOidcUserInfo(accessToken, idToken, attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
		}
	}
}