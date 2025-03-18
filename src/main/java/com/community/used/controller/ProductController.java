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
    @PostMapping("api/products/new")
    public Map<String, String> insertProduct(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("nickname") String nickname,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2,
            @RequestParam(value = "image3", required = false) MultipartFile image3) {

        Map<String, String> responseMap = new HashMap<>();
        try {
            // 상품 정보와 이미지 파일을 함께 저장
            Product product = new Product();
            product.setName(name);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            product.setNickname(nickname);
            System.out.println(nickname);
            productService.insertProduct(product, image1, image2, image3);

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
    @PostMapping("api/products/update")
    public Map<String, String> updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("nickname") String nickname,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2,
            @RequestParam(value = "image3", required = false) MultipartFile image3) {

        Map<String, String> responseMap = new HashMap<>();
        try {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            product.setNickname(nickname);

            productService.updateProduct(product, image1, image2, image3);

            responseMap.put("status", "ok");
            responseMap.put("message", "상품 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 수정 실패");
        }
        return responseMap;
    }
    
    @GetMapping("api/products")
    public Map<String, Object> getAllProducts() {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getAllProducts();  // 모든 상품을 조회
            responseMap.put("status", "ok");
            responseMap.put("products", products);  // 조회된 상품 리스트 반환
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 조회에 실패했습니다.");
        }
        return responseMap;
    }

    @GetMapping("api/products/{id}")
    public Map<String, Object> getProductById(@PathVariable("id") Long id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Product product = productService.getProductById(id);  // 상품 ID로 상품을 조회
            if (product != null) {
                responseMap.put("status", "ok");
                responseMap.put("product", product);  // 조회된 상품 반환
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "상품을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 조회에 실패했습니다.");
        }
        return responseMap;
    }
    
    @GetMapping("api/products/category/{category}")
    public Map<String, Object> getProductsByCategory(@PathVariable("category") String category) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getProductsByCategory(category);  // 카테고리로 상품 조회
            if (!products.isEmpty()) {
                responseMap.put("status", "ok");
                responseMap.put("products", products);  // 조회된 상품 리스트 반환
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "해당 카테고리에 상품이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "카테고리 조회에 실패했습니다.");
        }
        return responseMap;
    }

    @GetMapping("api/products/nickname/{nickname}")
    public Map<String, Object> getProductsByNickname(@PathVariable("nickname") String nickname) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getProductsByNickname(nickname);  // 카테고리로 상품 조회
            if (!products.isEmpty()) {
                responseMap.put("status", "ok");
                responseMap.put("products", products);  // 조회된 상품 리스트 반환
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "해당 사용자의 상품이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "해당 사용자의 상품 조회에 실패했습니다.");
        }
        return responseMap;
    }
    
    // 상품 삭제
    @DeleteMapping("api/products/{id}")
    public Map<String, String> deleteProduct(@PathVariable("id") Long id) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            boolean isDeleted = productService.deleteProduct(id);  // 상품 삭제 서비스 호출
            if (isDeleted) {
                responseMap.put("status", "ok");
                responseMap.put("message", "상품 삭제 성공");
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "상품을 찾을 수 없거나 삭제 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 삭제 실패");
        }
        return responseMap;
    }

}
