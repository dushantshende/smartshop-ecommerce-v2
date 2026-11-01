package com.ds.smartshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ds.smartshop.entity.Product;
import com.ds.smartshop.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@GetMapping("/api/products")
	public List<Product> getProducts() {
		return productService.getAllProducts();
		
	}
	
	@GetMapping("/api/products/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@PostMapping("/api/products")
	public Product saveProduct(@Valid @RequestBody Product product) {
		
		return productService.saveProduct(product);
	}
	
	@PutMapping("/api/products/{id}")
	public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
		
		return productService.updateProduct(id, product);
		
	}
	
	@DeleteMapping("/api/products/{id}")
	public void deletProduct(@PathVariable Long id) {
		 productService.deleteProduct(id);
	}
}
