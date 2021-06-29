package com.docker.spring_boot.service;

import com.docker.spring_boot.domain.Product;
import com.docker.spring_boot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}


	public Product create(Product product){
		product.create();
		return productRepository.save(product);
	}


	public Product update(Product product){
		if(product != null){
			if(product.getId() != null) {
				product.update();
				return productRepository.save(product);
			}
		}
		return null;
	}

	public boolean deleteById(Long id){

		Optional<Product> product = productRepository.findById(id);

		if(product.isPresent()) {
			product.get().delete();
			productRepository.save(product.get());

			return true;
		}else{
			return false;
		}
	}

	public Optional<Product> getById(Long id){
		return productRepository.findById(id);
	}

	public List<Product> getALl(){
		return productRepository.findAllByDeletedAtIsNull();
	}

	public boolean isAvailable(Product product){
		return productRepository.existsByIdAndDeletedAtIsNull(product.getId());
	}

}
