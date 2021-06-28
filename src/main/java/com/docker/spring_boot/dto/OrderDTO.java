package com.docker.spring_boot.dto;

import com.docker.spring_boot.domain.Product;

import java.util.List;

public class OrderDTO {


	private List<ProductDTO> itens;


	public OrderDTO(List<ProductDTO> itens) {
		this.itens = itens;
	}

	public List<ProductDTO> getItens() {
		return itens;
	}

	public void setItens(List<ProductDTO> itens) {
		this.itens = itens;
	}
}
