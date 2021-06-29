package com.docker.spring_boot.dto;


import java.util.List;

public class OrderDTO {


	private List<ProductDTO> itens;


	public OrderDTO(List<ProductDTO> itens) {
		this.itens = itens;
	}

	public OrderDTO() {
	}

	public List<ProductDTO> getItens() {
		return itens;
	}

	public void setItens(List<ProductDTO> itens) {
		this.itens = itens;
	}
}
