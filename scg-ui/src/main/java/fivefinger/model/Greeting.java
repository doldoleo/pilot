package fivefinger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Greeting {
	@Getter @Setter
	private long id;
	
	@Getter @Setter
	private String name;
}
