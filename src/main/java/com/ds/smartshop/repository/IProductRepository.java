package com.ds.smartshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.smartshop.entity.Product;

public interface IProductRepository extends JpaRepository<Product ,Long> {

}
