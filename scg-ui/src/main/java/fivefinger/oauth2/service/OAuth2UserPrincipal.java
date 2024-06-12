package fivefinger.oauth2.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import fivefinger.oauth2.core.user.model.oauth2.DefaultOAuth2UserInfo;

public class OAuth2UserPrincipal implements OAuth2User, UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final DefaultOAuth2UserInfo userInfo;

    public OAuth2UserPrincipal(DefaultOAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    public DefaultOAuth2UserInfo getOAuth2UserInfo() {
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
}
