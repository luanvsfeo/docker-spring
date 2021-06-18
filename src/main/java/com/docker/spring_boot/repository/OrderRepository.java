package com.docker.spring_boot.repository;

import com.docker.spring_boot.domain.Customer;
import com.docker.spring_boot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	Order findByCustomerAndOrderDateIsNullAndDeleteDateIsNull(Customer customer);

	boolean existsByCustomerAndOrderDateIsNullAndDeleteDateIsNull(Customer customer);
}
