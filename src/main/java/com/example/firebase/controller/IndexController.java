package com.example.firebase.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("")
	public String hello() {
		return "view/index.html";
	}
	
    @GetMapping("test")
    ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("view/index.html");
    }
	
}
