package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.JsonMessage;
import com.docker.spring_boot.domain.Product;
import com.docker.spring_boot.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Product product){
		product.create();
		return ResponseEntity.ok(productRepository.save(product));
	}


	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(productRepository.findAll());
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		Optional<Product> product = productRepository.findById(id);

		if(product.isPresent()){
			product.get().delete();
			productRepository.save(product.get());
			return ResponseEntity.ok(new JsonMessage("Produto excluido"));
		}

		return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel excluir o produto "));
	}


	@PutMapping
	public ResponseEntity<?> update(@RequestBody Product product){

		if(product != null){
			if(product.getId() != null){
				product.update();
				productRepository.save(product);
				return ResponseEntity.ok(new JsonMessage("Produto Atualizado"));
			}
		}

		return ResponseEntity.badRequest().body(new JsonMessage("Não foi possivel atualizar o produto "));
	}

}
