package fivefinger.oauth2.core.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * ID Token raw data
 *{
  "kid": "0d50423f-49fb-4949-897c-36dea89e58f9",
  "alg": "RS256"
  }
 * 
 * {
  "sub": "test",
  "aud": "your-client",
  "azp": "your-client",
  "auth_time": 1715556829,
  "iss": "http://localhost:9000",
  "exp": 1715559950,
  "iat": 1715558150,
  "jti": "16f0ed54-b502-4872-8ae4-180975f72cf0",
  "sid": "W4pjPB19shm4_z7eP_dGykMlbWNbMpc2PIv9KZfB6bo"
}
 */
@Getter
@Setter
@RequiredArgsConstructor
public class IDToken {
	private String sub;
	private String aud;
	private String azp;
	private String auth_time;
	private String iss;
	private String exp;
	private String iat;
	private String jti;
	private String sid;
}
