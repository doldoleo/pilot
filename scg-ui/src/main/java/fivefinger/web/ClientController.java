package fivefinger.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import fivefinger.model.Greeting;
import fivefinger.oauth2.core.token.OAuthToken;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClientController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${api.gateway.url}")
	private String api_gateway_url;

	@Value("${spring.security.oauth2.client.registration.komsco.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.client.registration.komsco.client-secret}")
	private String clientSecret;
	

	
	@GetMapping("/payment")
	public String payment(Model model, HttpSession session) {
		HttpHeaders headers = getHeaders(session);
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
		URI uri = UriComponentsBuilder
				.fromUriString(api_gateway_url)
				.path("/api/v1/payment/check")
				.encode()
				.build()
				.toUri();

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri,  HttpMethod.GET, request, String.class);

		String res = responseEntity.getBody();
		model.addAttribute("result", res);

		return "pages/payment";
}

	@GetMapping("/user/greeting")
	public String greeting(Model model, HttpSession session) {
		HttpHeaders headers = getHeaders(session);
		HttpEntity<Greeting> request = new HttpEntity<Greeting>(headers);
		
		URI uri = UriComponentsBuilder
					.fromUriString(api_gateway_url)
					.path("/api/v1/user/greeting")
				    .queryParam("name", "Gil-Dong")
				    .encode()
				    .build()
				    .toUri();

		ResponseEntity<Greeting> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, request, Greeting.class);
		Greeting greeting = responseEntity.getBody();
		model.addAttribute("greeting", greeting);

		return "pages/user";
	}
	
	@GetMapping("/user/nogreeting")
	public String nogreeting(Model model, HttpSession session) {
		HttpHeaders headers = getHeaders(session);
		HttpEntity<Greeting> request = new HttpEntity<Greeting>(headers);

		URI uri = UriComponentsBuilder
					.fromUriString(api_gateway_url)
					.path("/api/v1/user/nogreeting")
					.queryParam("name", "Gil-Dong")
					.encode()
					.build()
					.toUri();

		ResponseEntity<Greeting> responseEntity = restTemplate.exchange(uri,  HttpMethod.GET, request, Greeting.class);
		Greeting greeting = responseEntity.getBody();
		model.addAttribute("greeting", greeting);

		return "pages/user";
	}

	@GetMapping("/merge")
	public String merge(Model model, HttpSession session) {
		HttpHeaders headers = getHeaders(session);
		HttpEntity<Greeting> request = new HttpEntity<Greeting>(headers);
		
		URI uri = UriComponentsBuilder
				.fromUriString(api_gateway_url)
				.path("/api/v1/merge/call")
				.encode()
				.build()
				.toUri();

		ResponseEntity<Greeting> responseEntity = restTemplate.exchange(uri,  HttpMethod.GET, request, Greeting.class);
		Greeting greeting = responseEntity.getBody();
		model.addAttribute("greeting", greeting);

		return "pages/merge";
	}
	
	/**
	 * 토큰가져오기
	 * @param session
	 * @return
	 */
	private HttpHeaders getHeaders(HttpSession session) {
		// 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    OAuthToken token = (OAuthToken)session.getAttribute("userSession");
	    headers.add("Authorization","Bearer "+ token.getAccessToken());
	    return headers;
	}
}
