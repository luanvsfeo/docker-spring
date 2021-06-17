package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.Customer;
import com.docker.spring_boot.domain.JwtResponse;
import com.docker.spring_boot.repository.CustomerRepository;
import com.docker.spring_boot.util.ConversionUtil;
import com.docker.spring_boot.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerRepository customerRepository;

	private final JwtTokenUtil jwtTokenUtil;

	public CustomerController(CustomerRepository customerRepository, JwtTokenUtil jwtTokenUtil) {
		this.customerRepository = customerRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(customerRepository.findAll());
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Customer customer){
		if(!customerRepository.existsByEmail(customer.getEmail())){
			customer.changePassword();
			return ResponseEntity.ok(customerRepository.save(customer));
		}else{
			return ResponseEntity.badRequest().build();
		}
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Customer customer){
		if(customer.validForLogin()){
			Customer customerDb = customerRepository.findByEmail(customer.getEmail());
			if(customerDb != null){
				if(ConversionUtil.matches(customer.getPassword(),customerDb.getPassword())){
					return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(customerDb)));
				}
			}
		}
		return ResponseEntity.badRequest().build();
	}

}
