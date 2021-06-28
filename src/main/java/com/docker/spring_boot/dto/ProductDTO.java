package com.docker.spring_boot.dto;

import com.docker.spring_boot.domain.Product;

public class ProductDTO {

	private Long id;

	private String name;

	private String description;

	private Double price;

	public ProductDTO(Long id, String name, String description, Double price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public ProductDTO(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public ProductDTO() {
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
		if(this.id == null){
			return new Product(this.name,this.description,this.price);
		}else{
			return new Product(this.id,this.name,this.description,this.price);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
