package com.docker.spring_boot.domain;

import com.docker.spring_boot.enumx.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
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

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private Double totalPrice;

	private Date deleteDate;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;


	public Order() {
	}

	public Order(Long id, List<Product> itens, Date orderDate, OrderStatus status, Double totalPrice, Date deleteDate, Customer customer) {
		this.id = id;
		this.itens = itens;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
		this.deleteDate = deleteDate;
		this.customer = customer;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void createDraft(){
		this.status = OrderStatus.DRAFT;
	}

	public void delete(){
		this.deleteDate = new Date();
		this.status = OrderStatus.DELETED;
	}

	public void confirm(){
		this.orderDate =  new Date();
		this.status = OrderStatus.CREATED;
		this.calcTotalPrice();
	}

	public void calcTotalPrice(){
		this.totalPrice  = 0.0;
		for(Product product : this.itens){
			this.totalPrice += product.getPrice();
		}
	}
}
