package fivefinger.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fivefinger.oauth2.core.token.OAuthToken;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/main")
	public String main(HttpSession session) {
		OAuthToken token = (OAuthToken)session.getAttribute("userSession");
		if (token == null ) {
			return "/";
		}
		return "pages/main";
	}
	
}
