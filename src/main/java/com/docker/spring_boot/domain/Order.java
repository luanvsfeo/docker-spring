package com.docker.spring_boot.domain;

import com.docker.spring_boot.dto.OrderDTO;
import com.docker.spring_boot.dto.ProductDTO;
import com.docker.spring_boot.enumx.OrderStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
			name = "order_product",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private Set<Product> itens;

	private Date orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private Double totalPrice;

	private Date deleteDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	public Order() {
	}

	public Order(Set<Product> itens, User user) {
		this.itens = itens;
		this.user = user;
	}

	public Order(Long id, Set<Product> itens, Date orderDate, OrderStatus status, Double totalPrice, Date deleteDate, User user) {
		this.id = id;
		this.itens = itens;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
		this.deleteDate = deleteDate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Product> getItens() {
		return itens;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setItens(Set<Product> itens) {
		this.itens = itens;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public User getCustomer() {
		return user;
	}

	public void setCustomer(User user) {
		this.user = user;
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

	public OrderDTO convertToDTO(){
		List<ProductDTO> productDTOS = new ArrayList<>();

		for( Product product : this.itens){
			productDTOS.add(product.convertToDTO());
		}

		return new OrderDTO(productDTOS);
	}
}
