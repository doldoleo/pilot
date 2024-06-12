package fivefinger.oauth2.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import fivefinger.oauth2.core.user.model.oidc.DefaultOidcUserInfo;

public class OidcUserPrincipal implements OidcUser, UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final DefaultOidcUserInfo userInfo;

    public OidcUserPrincipal(DefaultOidcUserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    public DefaultOidcUserInfo getOidcUserInfo() {
        return userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return userInfo.getEmail();
    }

	@Override
	public Map<String, Object> getClaims() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return userInfo.getIdToken();
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}
}
