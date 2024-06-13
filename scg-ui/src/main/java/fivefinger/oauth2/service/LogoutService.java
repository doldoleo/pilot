package fivefinger.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${spring.security.oauth2.client.registration.komsco.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.client.registration.komsco.client-secret}")
	private String clientSecret;


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
	
	public void revokeToken2(HttpSession session) throws Exception {
		
		try {
			OAuthToken token = (OAuthToken) session.getAttribute("userSession");
	
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			
			params.add("token_type_hint", "access_token");
			params.add("token", token.getAccessToken());
			params.add("client_id", clientId);
			params.add("client_secret", clientSecret);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<?> entity = new HttpEntity<>(params, headers);
			restTemplate.exchange(siteUrlProperties.getOauth2_revoke_uri(), HttpMethod.POST, entity, Void.class);
		} catch (Exception e) {
			log.error("revokeToken>>>", e);
		}
	}
}
