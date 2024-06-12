package com.example.demo.greeting.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Greeting(
		@Schema(description = "임의 수")
        @NotNull(message = "........") 
		long id, 
		
		@NotNull(message = "Name cannot be null")
		@Size(min = 2, message = "Name not be less than two characters")
		@Schema(description = "사용자 이름", nullable = false, example = "홍길동")
		String name
) { 
}
