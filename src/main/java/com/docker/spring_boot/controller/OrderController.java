package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.User;
import com.docker.spring_boot.domain.JsonMessage;
import com.docker.spring_boot.domain.Order;
import com.docker.spring_boot.repository.UserRepository;
import com.docker.spring_boot.repository.OrderRepository;
import com.docker.spring_boot.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final JwtTokenUtil jwtTokenUtil;

	private final OrderRepository orderRepository;

	private final UserRepository userRepository;

	public OrderController(JwtTokenUtil jwtTokenUtil, OrderRepository orderRepository, UserRepository userRepository) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}

	@PostMapping
	public ResponseEntity<?> create(HttpServletRequest request, @RequestBody Order order){

		final String requestTokenHeader = request.getHeader("Authorization");

		User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(requestTokenHeader.substring(7)));

		if(orderRepository.existsByUserAndOrderDateIsNullAndDeleteDateIsNull(user)){
			return ResponseEntity.badRequest().body(new JsonMessage("Não é possivel criar um novo pedido sem excluir ou finalizar o atual"));
		}

		order.createDraft();
		order.setCustomer(user);

		return ResponseEntity.ok(orderRepository.save(order));
	}

	@PostMapping("/finish")
	public ResponseEntity<?> finish(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");

		User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(requestTokenHeader.substring(7)));

		Order order = orderRepository.findByUserAndOrderDateIsNullAndDeleteDateIsNull(user);
		order.confirm();
		orderRepository.save(order);

		return ResponseEntity.ok(new JsonMessage(("Pedido criado com sucesso")));
	}

	@DeleteMapping
	public ResponseEntity<?> delete(HttpServletRequest request){
		final String requestTokenHeader = request.getHeader("Authorization");

		User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(requestTokenHeader.substring(7)));

		Order order = orderRepository.findByUserAndOrderDateIsNullAndDeleteDateIsNull(user);
		order.delete();
		orderRepository.save(order);

		return ResponseEntity.ok(new JsonMessage("Pedido excluido"));
	}

}
