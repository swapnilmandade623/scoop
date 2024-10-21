package com.tsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsl.model.Products;
@Repository
public interface ProductsRepo extends JpaRepository<Products,Integer > {
	 boolean existsBywebsite(String website);

	

}
