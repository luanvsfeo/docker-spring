package com.docker.spring_boot.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payment_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@JoinTable(
			name = "order_product",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private List<Product> itens;

	private Date orderDate;

	private String status; //create enum

	private Double totalPrice;


	public Order() {
	}

	public Order(Long id, List<Product> itens, Date orderDate, String status, Double totalPrice) {
		this.id = id;
		this.itens = itens;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Product> getItens() {
		return itens;
	}

	public void setItens(List<Product> itens) {
		this.itens = itens;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
