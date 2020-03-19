package com.example.demorest.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demorest.vo.User;

@RestController		//this gives response of each method in json
@RequestMapping("/database/")
public class validation {
	
	//@ResponseBody  //this annotation is not required when restcontroller annotation is represented in class level
	@PostMapping(path="valid", consumes = "application/json", produces = "application/json")
	public Boolean valid(@RequestBody User user) {

		if (user.getUsername().equals("username") && user.getPassword().equals("password"))
			return true;
		else
			return false;

	}
	
	@GetMapping(path="va")
	public String view() {

		return "hello world";
	}
}