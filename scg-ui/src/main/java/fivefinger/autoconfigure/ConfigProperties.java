package fivefinger.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "scg.config")
public class ConfigProperties {
	private String gateway_uri;
	private String oauth2_revoke_uri;
}
