package fivefinger.oauth2.core.user.model.oauth2;

import java.util.Map;

import fivefinger.oauth2.core.user.provider.OAuth2Provider;

public interface  DefaultOAuth2UserInfo {
	OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

}
