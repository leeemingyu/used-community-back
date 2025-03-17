package com.community.used.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.used.dto.Product;
import com.community.used.service.ProductService;

@RestController
@CrossOrigin("http://192.168.219.116:5500/")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	Map<String,Object> storage=new HashMap();
	
	@GetMapping("getAllProducts")
	public List<Product> getAllProducts() {
		try {
			Object o=storage.get("firstPageProducts");
			if(o==null) {
				List<Product> list=productService.getAllProducts();
				storage.put("firstPageProducts", list);
				return list;
			}
			
			return (List<Product>)o;
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	


}
