package com.community.used.dao;

import com.community.used.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDao {

    // 상품 등록
    public void insertProduct(Product product) throws Exception;

    // 상품 수정
    public void updateProduct(Product product) throws Exception;

    // 상품 삭제
    public int deleteProduct(Long id) throws Exception;

    // 상품 1개 조회
    public Product getProductById(Long id) throws Exception;
    
    // 사용자별 상품 조회
    public List<Product> getProductsByNickname(String nickname) throws Exception;

    // 전체 상품 조회
    public List<Product> getAllProducts() throws Exception;

    // 카테고리별 상품 조회
    public List<Product> getProductsByCategory(String category) throws Exception;
}
