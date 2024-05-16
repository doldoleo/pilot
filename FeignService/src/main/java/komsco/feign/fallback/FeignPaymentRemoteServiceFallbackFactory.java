package komsco.feign.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import feign.FeignException;
import komsco.feign.service.PaymentServiceOpenFeign;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignPaymentRemoteServiceFallbackFactory implements FallbackFactory<PaymentServiceOpenFeign> {

	@Override
	public PaymentServiceOpenFeign create(Throwable cause) {
		log.error("t = " + cause);
		
		return new PaymentServiceOpenFeign() {
            @Override
            public String check() {
                if (cause instanceof FeignException.Unauthorized) {
                    return "Unthorized!!!!";
                }
                if (cause instanceof FeignException.NotFound) {
                    return "Not Found!!!";
                }
                if (cause instanceof Exception) {
                    return "Exception!!!";
                }
                return cause.getMessage();
            }
        };
	}

}
