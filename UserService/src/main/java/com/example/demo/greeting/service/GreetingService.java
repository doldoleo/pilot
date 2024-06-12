package com.example.demo.greeting.service;

import com.example.demo.greeting.model.Greeting;

public interface GreetingService {
	public Greeting greeting(String name);
	public Greeting greetingbynoauth(String name);
}
