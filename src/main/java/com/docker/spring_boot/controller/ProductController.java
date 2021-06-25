package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.JsonMessage;
import com.docker.spring_boot.dto.ProductDTO;
import com.docker.spring_boot.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(productService.getALl());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id")Long id){
		return ResponseEntity.ok(productService.getById(id));
	}


	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductDTO product){
		try{
			if(productService.create(product.convertToProduct()) != null){
				return ResponseEntity.ok(new JsonMessage("Produto criado com sucesso"));
			}else{
				return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel criar o produto "));
			}
		}catch (Exception e){
			return ResponseEntity.badRequest().body(new JsonMessage(e.getMessage()));
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){

		if(productService.deleteById(id)){
			return ResponseEntity.ok(new JsonMessage("Produto excluido"));
		}else{
			return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel excluir o produto "));
		}
	}


	@PutMapping
	public ResponseEntity<?> update(@RequestBody ProductDTO product){

		if(productService.update(product.convertToProduct()) != null){
			return ResponseEntity.ok(new JsonMessage("Produto Atualizado"));
		}else{
			return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel atualizar o produto "));
		}
	}

}
