package fivefinger.oauth2.core.user.factory;

import java.util.Map;

import fivefinger.oauth2.core.user.model.oauth2.DefaultOAuth2UserInfo;
import fivefinger.oauth2.core.user.model.oauth2.KomscoOAuth2UserInfo;
import fivefinger.oauth2.core.user.provider.OAuth2Provider;
import fivefinger.oauth2.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

    public static DefaultOAuth2UserInfo getOAuth2UserInfo(String registrationId, String accessToken, Map<String, Object> attributes) {
        if (OAuth2Provider.KOMSCO.getRegistrationId().equals(registrationId)) {
            return new KomscoOAuth2UserInfo(accessToken, attributes);    
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}