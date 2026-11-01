package com.ds.smartshop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ds.smartshop.dto.ProductRequest;
import com.ds.smartshop.dto.ProductResponse;
import com.ds.smartshop.entity.Product;
import com.ds.smartshop.exception.ProductNotFoundException;
import com.ds.smartshop.repository.IProductRepository;

@Service
public class ProductService {

	private final IProductRepository productRepository;
	
	private static final Logger logger=LoggerFactory.getLogger(ProductService.class);
	
	public ProductService(IProductRepository productRepository) {
		this.productRepository=productRepository;
		
	}
	
	private Product mapToEntity(ProductRequest request) {
		
		Product product= new Product();
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		
		return product;
	}
	
	private ProductResponse mapToResponse(Product product) {
		
		ProductResponse response=new ProductResponse();
		
		response.setId(product.getId());
		response.setName(product.getName());
		response.setPrice(product.getPrice());
		response.setStock(product.getStock());
		
		return response;
	}
	
	public List<ProductResponse> getAllProducts() {
		
		logger.info("Fetching all products from database");
		
		return productRepository.findAll().stream().map(this::mapToResponse).toList();
		
	}
	
	public ProductResponse getProductById(Long id) {
		
		logger.info("Fetching product with Id: {}", id);
		
		Product product= productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product not found with ID: "+id));
		
		return mapToResponse(product);
	}
	
	public ProductResponse saveProduct(ProductRequest request) {
		
		logger.info("Saving new product: {}", request.getName()	);
		
		Product product=mapToEntity(request);
		
		Product savedProduct=productRepository.save(product);
		return mapToResponse(savedProduct);
		
	}
	
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		
		Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product not found with Id: "+id));
		
		
			existingProduct.setName(request.getName());
			existingProduct.setPrice(request.getPrice());
			existingProduct.setStock(request.getStock());
			
			logger.info("Updating product with Id: {}",id);
			
			Product updatedProduct= productRepository.save(existingProduct);
			
			return mapToResponse(updatedProduct);
		
	}
	
	public void deleteProduct(Long id) {
		productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product not found with Id: "+id));
		
			productRepository.deleteById(id);
			logger.info("Deleting Product with id: {}",id);
		
		
	}
}
