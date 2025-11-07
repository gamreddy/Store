package com.biz.store.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCode(String code);
	List<Product> findAllByCodeIn(List<String> codes);
}

