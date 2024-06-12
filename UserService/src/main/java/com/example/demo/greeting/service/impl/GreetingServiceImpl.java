package com.example.demo.greeting.service.impl;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.greeting.model.Greeting;
import com.example.demo.greeting.service.GreetingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GreetingServiceImpl implements GreetingService {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Override
	public Greeting greeting(String name) {
		log.info("Hello {}", name);
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@Override
	public Greeting greetingbynoauth(String name) {
		log.info("no auth: {}", name);
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

}
