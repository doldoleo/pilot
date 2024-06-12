package fivefinger.oauth2.handler;


import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import fivefinger.oauth2.core.token.OAuthToken;
import fivefinger.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import fivefinger.oauth2.service.OAuth2UserPrincipal;
import fivefinger.oauth2.service.OidcUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect {}", targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {


        OAuth2UserPrincipal oAuth2UserPrincipal = getOAuth2UserPrincipal(authentication);

        if (oAuth2UserPrincipal != null) {
        	 log.info("email={}, name={}, nickname={}, accessToken={}", oAuth2UserPrincipal.getOAuth2UserInfo().getEmail(),
        			 oAuth2UserPrincipal.getOAuth2UserInfo().getName(),
        			 oAuth2UserPrincipal.getOAuth2UserInfo().getNickname(),
        			 oAuth2UserPrincipal.getOAuth2UserInfo().getAccessToken()
     	    );
     	
     	   OAuthToken oAuthToken = new OAuthToken();
     	   oAuthToken.setAccessToken(oAuth2UserPrincipal.getOAuth2UserInfo().getAccessToken());
     	   request.getSession().setAttribute("userSession", oAuthToken);
     	  
     	   return UriComponentsBuilder.fromUriString("/main").build().toUriString();
        } else {
        	OidcUserPrincipal oidcUserPrincipal = getOidcUserPrincipal(authentication);
        	
        	if ( oidcUserPrincipal != null ) {
	        	log.info("email={}, accessToken={}", oidcUserPrincipal.getOidcUserInfo().getEmail(),
	        			oidcUserPrincipal.getOidcUserInfo().getAccessToken()
	    	    );
	    	
				OAuthToken oAuthToken = new OAuthToken();
				oAuthToken.setAccessToken(oidcUserPrincipal.getOidcUserInfo().getAccessToken());
				request.getSession().setAttribute("userSession", oAuthToken);
				 return UriComponentsBuilder.fromUriString("/main").build().toUriString();
        	}
        }


        return UriComponentsBuilder.fromUriString("/")
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }
    
    private OidcUserPrincipal getOidcUserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OidcUserPrincipal) {
            return (OidcUserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}