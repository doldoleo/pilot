package fivefinger.oauth2.core.user.model.oidc;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import fivefinger.oauth2.core.user.provider.OAuth2Provider;

public class KomscoOidcUserInfo implements DefaultOidcUserInfo {
    private final Map<String, Object> attributes;
    private final String accessToken;
    private final OidcIdToken idToken;
    private final String email;
    
    public KomscoOidcUserInfo(String accessToken, OidcIdToken idToken,  Map<String, Object> attributes) {
        this.accessToken = accessToken;
        this.attributes = attributes;
        this.idToken = idToken;
        
        this.email = (String) attributes.get("email");
    }
    
	@Override
	public OAuth2Provider getProvider() {
		return OAuth2Provider.KOMSCO;
	}

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public OidcIdToken getIdToken() {
		return this.idToken;
	}

	@Override
	public String getEmail() {
		return this.email;
	}
}
