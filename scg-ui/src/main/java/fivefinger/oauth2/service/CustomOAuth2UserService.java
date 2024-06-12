package fivefinger.oauth2.service;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import fivefinger.oauth2.core.user.factory.OAuth2UserInfoFactory;
import fivefinger.oauth2.core.user.model.oauth2.DefaultOAuth2UserInfo;
import fivefinger.oauth2.exception.OAuth2AuthenticationProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

    	 String refreshToken = (String) userRequest.getAdditionalParameters().get(OAuth2ParameterNames.REFRESH_TOKEN);
    	 String registrationId = userRequest.getClientRegistration().getRegistrationId();
         String accessToken = userRequest.getAccessToken().getTokenValue();

         log.debug("access_toekn={}, token_type={}, scop={}, accessToken={} , refresh={}", userRequest.getAccessToken().getTokenValue(),
	        		userRequest.getAccessToken().getTokenType().getValue(),
	        		userRequest.getAccessToken().getScopes().toString(), refreshToken);

         
         DefaultOAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, accessToken, oAuth2User.getAttributes());

         // OAuth2UserInfo field value validation
         if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
             throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
         }

         return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}