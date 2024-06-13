package fivefinger.oauth2.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fivefinger.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import fivefinger.oauth2.service.LogoutService;
import fivefinger.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLogoutHandler implements LogoutHandler {
	@Autowired
	private LogoutService logoutService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			
			Assert.notNull(request, "HttpServletRequest required");
			HttpSession session = request.getSession(false);
			
			// 1. delete access token
			logoutService.revokeToken2(session);
			
			session.removeAttribute("userSession");
			if (session != null) {
	             session.invalidate();
	        }
			SecurityContextHolder.getContext().setAuthentication(null);
			authentication.setAuthenticated(false);
			SecurityContextHolder.clearContext();
			
			CookieUtils.deleteCookie(request, response, HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, "JSESSIONID");
			
		} catch (Exception e) {
			log.error("error", e);
		}	
			
	}
}