package openfeign;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.util.Arrays;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import feign.Capability;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.Retryer;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableFeignClients(basePackages = "openfeign")
public class OpenFeignConfig {
	@Bean
	RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			if (Arrays.isNullOrEmpty(requestTemplate.body()) && !isGetOrDelete(requestTemplate)) {
				// body가 비어있는 경우에 요청을 보내면 411 에러가 생김
				// content-length로 처리가 안되어서 빈 값을 항상 보내주도록 함
				requestTemplate.body("{}");
			}
		};
	}

	private boolean isGetOrDelete(RequestTemplate requestTemplate) {
		return Request.HttpMethod.GET.name().equals(requestTemplate.method())
				|| Request.HttpMethod.DELETE.name().equals(requestTemplate.method());
	}

	@Bean
	FeignFormatterRegistrar dateTimeFormatterRegistrar() {
		return registry -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setUseIsoFormat(true);
			registrar.registerFormatters(registry);
		};
	}

	@Slf4j
	static class CustomFeignRequestLogging extends Logger {

		private final ThreadLocal<String> requestId = new ThreadLocal<>();

		@Override
		protected void logRequest(String configKey, Level logLevel, Request request) {
			if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
				super.logRequest(configKey, logLevel, request);
			} else {
				requestId.set(Integer.toString((int) (Math.random() * 1000000)));
				String stringBody = createRequestStringBody(request);
				log.info("[{}] URI: {}, Method: {}, Headers:{}, Body:{} ", requestId.get(), request.url(),
						request.httpMethod(), request.headers(), stringBody);
			}
		}

		private String createRequestStringBody(Request request) {
			return request.body() == null ? "" : new String(request.body(), StandardCharsets.UTF_8);
		}

		@Override
		protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
				throws IOException {
			if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
				super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
				return response;
			} else {
				byte[] byteArray = getResponseBodyByteArray(response);
				log.info("[{}] Status: {}, Body:{} ", requestId.get(), HttpStatus.valueOf(response.status()),
						new String(byteArray, StandardCharsets.UTF_8));
				return response.toBuilder().body(byteArray).build();
			}
		}

		private byte[] getResponseBodyByteArray(Response response) throws IOException {
			if (response.body() == null) {
				return new byte[] {};
			}

			return StreamUtils.copyToByteArray(response.body().asInputStream());
		}

		@Override
		protected void log(String configKey, String format, Object... args) {
			log.debug(format(configKey, format, args));
		}

		protected String format(String configKey, String format, Object... args) {
			return String.format(methodTag(configKey) + format, args);
		}
	}

	@Bean
	Retryer.Default retryer() {
		// 0.1초의 간격으로 시작해 최대 3초의 간격으로 점점 증가하며, 최대5번 재시도한다.
		return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5);
	}
	
	@Bean
	Capability capability(final MeterRegistry registry) {
	    return new MicrometerCapability(registry);
	}
}
