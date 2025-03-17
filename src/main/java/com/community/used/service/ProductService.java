package com.community.used.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.used.dao.ProductDao;
import com.community.used.dto.Product;

@Service
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	
	public List<Product> getAllProducts() throws Exception {
		return productDao.getAllProducts();
	}

	public Product getProductByCode(Integer productCode) {
		// TODO Auto-generated method stub
		return null;
	}

}