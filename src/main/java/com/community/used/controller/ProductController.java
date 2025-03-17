package com.community.used.controller;

import com.community.used.dto.Product;
import com.community.used.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class ProductController {

    @Autowired
    ProductService productService;

    // 상품 등록
    @PostMapping("/products/new")
    public Map<String, String> insertProduct(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("nickname") String nickname,
            @RequestParam("images") MultipartFile[] images) { // 여러 파일 처리

        Map<String, String> responseMap = new HashMap<>();
        try {
            // 이미지 처리
            String imagePaths = productService.saveImages(images);  // 이미지 파일을 저장하고 경로를 반환하는 메소드

            // 상품 정보와 이미지 경로를 함께 저장
            productService.insertProduct(new Product(name, category, description, Integer.parseInt(price), imagePaths, nickname));
            
            responseMap.put("status", "ok");
            responseMap.put("message", "상품 등록 성공");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 등록 실패");
        }
        return responseMap;
    }

    // 상품 수정
    @PostMapping("/products/update")
    public Map<String, String> updateProduct(@RequestBody Product product) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            productService.updateProduct(product);
            responseMap.put("status", "ok");
            responseMap.put("message", "상품 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 수정 실패");
        }
        return responseMap;
    }

    // 상품 삭제
    @PostMapping("/products/delete")
    public Map<String, String> deleteProduct(@RequestBody Long id) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            productService.deleteProduct(id);
            responseMap.put("status", "ok");
            responseMap.put("message", "상품 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 삭제 실패");
        }
        return responseMap;
    }

    // 상품 1개 조회
    @GetMapping("/products/{id}")
    public Map<String, Object> getProduct(@PathVariable Long id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Product product = productService.getProductById(id);
            responseMap.put("status", "ok");
            responseMap.put("product", product);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 조회 실패");
        }
        return responseMap;
    }

    // 전체 상품 조회 (이미지 URL 포함)
    @GetMapping("/products")
    public Map<String, Object> getAllProducts() {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getAllProductsWithImageUrls();  // 이미지 URL을 포함한 상품 조회
            responseMap.put("status", "ok");
            responseMap.put("products", products);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 조회 실패");
        }
        return responseMap;
    }
    

    // 카테고리별 상품 조회
    @GetMapping("/products/category/{category}")
    public Map<String, Object> getProductsByCategory(@PathVariable String category) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getProductsByCategory(category);
            responseMap.put("status", "ok");
            responseMap.put("products", products);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "카테고리별 상품 조회 실패");
        }
        return responseMap;
    }
}
