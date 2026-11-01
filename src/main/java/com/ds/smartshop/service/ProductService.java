package com.ds.smartshop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	
	public List<Product> getAllProducts() {
		
		logger.info("Fetching all products from database");
		
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		
		logger.info("Fetching product with Id: {}", id);
		
		return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product not found with ID: "+id));
	}
	
	public Product saveProduct(Product product) {
		
		logger.info("Saving new product: {}", product.getName()	);
		
		return productRepository.save(product);
		
	}
	
	public Product updateProduct(Long id, Product updateProduct) {
		
		Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product Not Found with Id: "+id));
		
		
			existingProduct.setName(updateProduct.getName());
			existingProduct.setPrice(updateProduct.getPrice());
			existingProduct.setStock(updateProduct.getStock());
			
			logger.info("Updating product with Id: {}",id);
			
			return productRepository.save(existingProduct);
		
	}
	
	public void deleteProduct(Long id) {
		productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException ("Product Not Found with Id: "+id));
		
			productRepository.deleteById(id);
			logger.info("Deleting Product with id: {}",id);
		
		
	}
}
