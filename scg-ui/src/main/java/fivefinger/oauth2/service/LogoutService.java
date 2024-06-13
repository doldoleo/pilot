package fivefinger.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import fivefinger.autoconfigure.ConfigProperties;
import fivefinger.oauth2.core.token.OAuthToken;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired(required = true)
	private ConfigProperties siteUrlProperties;

	public void revokeToken(HttpSession session) throws Exception {
		try {
			OAuthToken token = (OAuthToken) session.getAttribute("userSession");
	
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", "Bearer " + token.getAccessToken());
			HttpEntity<?> entity = new HttpEntity<>(params, headers);
			restTemplate.exchange(siteUrlProperties.getOauth2_revoke_uri(), HttpMethod.GET, entity, Void.class);
		} catch (Exception e) {
			log.error("revokeToken>>>", e);
		}
	}
}
