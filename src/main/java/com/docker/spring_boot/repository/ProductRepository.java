package com.docker.spring_boot.repository;

import com.docker.spring_boot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

	boolean existsByIdAndDeletedAtIsNull(Long id);

	List<Product> findAllByDeletedAtIsNull();
}
