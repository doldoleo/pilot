package fivefinger.oauth2.service;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import fivefinger.oauth2.core.user.factory.OidcUserInfoFactory;
import fivefinger.oauth2.core.user.model.oidc.DefaultOidcUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService  {
	
	  @Override
	  public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
		  
	    OidcUser oidcUser = super.loadUser(oidcUserRequest);
	    
	    try {
            return processOidcUser(oidcUserRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
	  }
	  
	  private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
		   	 String refreshToken = (String) userRequest.getAdditionalParameters().get(OAuth2ParameterNames.REFRESH_TOKEN);
		    	

	        String registrationId = userRequest.getClientRegistration().getRegistrationId();
	        String accessToken = userRequest.getAccessToken().getTokenValue();
	        log.debug("access_toekn={}, token_type={}, scop={}, accessToken={} , refresh={}", userRequest.getAccessToken().getTokenValue(),
	        		userRequest.getAccessToken().getTokenType().getValue(),
	        		userRequest.getAccessToken().getScopes().toString(), refreshToken);
	        
	        OidcIdToken idToken = userRequest.getIdToken();
	        
	        log.debug(oidcUser.getAttributes().toString());
	        
	        DefaultOidcUserInfo defaultOidcUserInfo = OidcUserInfoFactory.getOAuth2UserInfo(registrationId, accessToken, idToken, oidcUser.getAttributes());

	         return new OidcUserPrincipal(defaultOidcUserInfo);
	    }
	}