package com.community.used.dao;

import java.sql.*;
import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.community.used.dto.Product;

@Mapper
public interface ProductDao {
	
	public  List<Product> getAllProducts() throws Exception;

	@Select("select price from product where prodcode = #{prodcode}")
    double getProductPriceByCode(String productCode);

}