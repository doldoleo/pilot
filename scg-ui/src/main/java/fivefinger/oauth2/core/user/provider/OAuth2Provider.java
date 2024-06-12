package fivefinger.oauth2.core.user.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    NAVER("naver"),
    KOMSCO("komsco"),
    KAKAO("kakao");

    private final String registrationId;
}