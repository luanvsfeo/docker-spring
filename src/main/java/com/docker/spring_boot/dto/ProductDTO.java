package com.docker.spring_boot.dto;

import com.docker.spring_boot.domain.Product;

public class ProductDTO {

	private String name;

	private String description;

	private Double price;

	public ProductDTO(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Product convertToProduct(){
		return new Product(this.name,this.description,this.price);
	}
}