package com.ds.smartshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ds.smartshop.dto.ProductRequest;
import com.ds.smartshop.dto.ProductResponse;
import com.ds.smartshop.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@GetMapping("/api/products")
	public List<ProductResponse> getProducts() {
		return productService.getAllProducts();
		
	}
	
	@GetMapping("/api/products/{id}")
	public ProductResponse getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@PostMapping("/api/products")
	public ProductResponse saveProduct(@Valid @RequestBody ProductRequest request) {
		
		return productService.saveProduct(request);
	}
	
	@PutMapping("/api/products/{id}")
	public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
		
		return productService.updateProduct(id, request);
		
	}
	
	@DeleteMapping("/api/products/{id}")
	public void deleteProduct(@PathVariable Long id) {
		 productService.deleteProduct(id);
	}
}
