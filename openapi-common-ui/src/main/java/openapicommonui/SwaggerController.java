package openapicommonui;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {
	 private final DiscoveryClient discoveryClient;

	    // Services to exclude. You can modify this list as per your environment
	    private static final List<String> KUBE_SERVICES = Arrays.asList("kubernetes", "kube-dns", "dashboard-metrics-scraper", "kubernetes-dashboard");

	    public SwaggerController(final DiscoveryClient discoveryClient) {
	        this.discoveryClient = discoveryClient;
	    }
	    
	    @GetMapping("/v3/api-docs/swagger-config")
	    public Map<String, Object> swaggerConfig(ServerHttpRequest serverHttpRequest) throws URISyntaxException {
	        URI uri = serverHttpRequest.getURI();
	        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();
	        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
	        List<org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
	        
	        System.out.println("Services = " + discoveryClient.getServices());
	        
	        discoveryClient.getServices().stream().filter(s -> !KUBE_SERVICES.contains(s)).forEach(serviceName ->
	                        swaggerUrls.add(new org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl(serviceName,
	                                url + "/" + serviceName + "/v3/api-docs")));
	        swaggerConfig.put("urls", swaggerUrls);
	        return swaggerConfig;
	    }
}
