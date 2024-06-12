package com.example.demo.payment.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.payment.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
 	@Value("${server.port}")
    private String port;

	@Override
	public String check() {
		log.trace("TRACE 로그!!");
		log.debug("DEBUG 로그!!");
		log.info("INFO 로그!!");
		log.warn("WARN 로그!!");
		log.error("ERROR 로그!!");
		return String.format("First Service on PORT %s", port);
	}

}
