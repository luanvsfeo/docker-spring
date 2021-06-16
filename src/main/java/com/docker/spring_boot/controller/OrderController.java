package com.docker.spring_boot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	@PostMapping
	public void create(){

	}

	@PostMapping("/finish")
	public void finish(){

	}


}
