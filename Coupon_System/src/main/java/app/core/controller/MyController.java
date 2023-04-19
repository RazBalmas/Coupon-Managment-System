package app.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stam")
public class MyController {
	
	@GetMapping("/bbb")
	public String hi() {
		return "HI";
	}

}
