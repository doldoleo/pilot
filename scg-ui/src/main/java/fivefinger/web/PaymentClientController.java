package fivefinger.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentClientController {
//	private WebClient webClient;
	
//	@GetMapping(value = "/articles")
//    public String[] getArticles(
//      @RegisteredOAuth2AuthorizedClient("payment-client-authorization-code") OAuth2AuthorizedClient authorizedClient
//    ) {
//        return this.webClient
//          .get()
//          .uri("http://127.0.0.1:8090/articles")
//          .attributes(oauth2AuthorizedClient(authorizedClient))
//          .retrieve()
//          .bodyToMono(String[].class)
//          .block();
//    }
//	
//	
//	private String getAccessToken(String authorizationCode, String registrationId) {
//        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
//        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
//        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
//        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", authorizationCode);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity entity = new HttpEntity(params, headers);
//
//        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
//        JsonNode accessTokenNode = responseNode.getBody();
//        return accessTokenNode.get("access_token").asText();
//    }
}
