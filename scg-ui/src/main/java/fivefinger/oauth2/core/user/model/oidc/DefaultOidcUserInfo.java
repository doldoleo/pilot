package fivefinger.oauth2.core.user.model.oidc;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import fivefinger.oauth2.core.user.provider.OAuth2Provider;

public interface DefaultOidcUserInfo  {
	OAuth2Provider getProvider();
	String getAccessToken();

    Map<String, Object> getAttributes();
    
    OidcIdToken getIdToken();
    
    String getEmail();
}
