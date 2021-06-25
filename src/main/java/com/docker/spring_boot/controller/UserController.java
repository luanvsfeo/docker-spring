package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.JsonMessage;
import com.docker.spring_boot.domain.JwtResponse;
import com.docker.spring_boot.dto.UserDTO;
import com.docker.spring_boot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody UserDTO user){
		try{
			if(userService.isValidToCreate(user.convertToUser())){
				userService.createUserCustomer(user.convertToUser());
				return ResponseEntity.ok(new JsonMessage("Usuario criado com sucesso"));
			}else{
				return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel criar o seu usuario"));
			}
		}catch (Exception e){
			return ResponseEntity.badRequest().body(new JsonMessage(e.getMessage()));
		}
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO user){
		final String token = userService.generateToken(user.convertToUser());

		if(token != null){
			return ResponseEntity.ok(new JwtResponse(token));
		}else{
			return ResponseEntity.badRequest().body(new JsonMessage("Ocorreu um erro ao logar"));
		}
	}

}
