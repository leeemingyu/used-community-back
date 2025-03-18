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
            @RequestParam(value = "image3", required = false) MultipartFile image3,
            @RequestHeader String Authorization) {

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
            productService.insertProduct(product, image1, image2, image3, Authorization);

            responseMap.put("status", "ok");
            responseMap.put("message", "상품 등록 성공");
        		} catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            if (e.getMessage().equals("잘못된 접근입니다.")) {
            	responseMap.put("message", e.getMessage());
            } else {
            	responseMap.put("message", "오류가 발생했습니다. 다시 시도해주세요.");    
            }
        }
        return responseMap;
    }

    // 전체 상품 조회
    @GetMapping("api/products")
    public Map<String, Object> getAllProducts(@RequestHeader(value = "Authorization", required = false) String Authorization) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getAllProducts(Authorization);  // 모든 상품을 조회
            responseMap.put("status", "ok");
            responseMap.put("products", products);  // 조회된 상품 리스트 반환
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "상품 조회에 실패했습니다.");
        }
        return responseMap;
    }
    // 상품 id로 조회
    @GetMapping("api/products/{id}")
    public Map<String, Object> getProductById(@PathVariable("id") Long id, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Product product = productService.getProductById(id, Authorization);  // 상품 ID로 상품을 조회
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
    // 닉네임으로 상품 조회
    @GetMapping("api/products/nickname/{nickname}")
    public Map<String, Object> getProductsByNickname(@PathVariable("nickname") String nickname, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getProductsByNickname(nickname, Authorization);  // 카테고리로 상품 조회
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
    // 카테고리별 상품 조회
    @GetMapping("api/products/category/{category}")
    public Map<String, Object> getProductsByCategory(@PathVariable("category") String category, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Product> products = productService.getProductsByCategory(category, Authorization);  // 카테고리로 상품 조회
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
            @RequestParam(value = "image3", required = false) MultipartFile image3,
            @RequestHeader String Authorization) {

        Map<String, String> responseMap = new HashMap<>();
        try {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            product.setNickname(nickname);

            productService.updateProduct(product, image1, image2, image3, Authorization);

            responseMap.put("status", "ok");
            responseMap.put("message", "상품 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            if (e.getMessage().equals("잘못된 접근입니다.")) {
            	responseMap.put("message", e.getMessage());
            } else {
            	responseMap.put("message", "오류가 발생했습니다. 다시 시도해주세요.");    
            }
        }
        return responseMap;
    }
    
    // 상품 삭제
    @DeleteMapping("api/products/{id}")
    public Map<String, String> deleteProduct(@PathVariable("id") Long id, @RequestHeader String Authorization) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            boolean isDeleted = productService.deleteProduct(id, Authorization);  // 상품 삭제 서비스 호출
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
            if (e.getMessage().equals("잘못된 접근입니다.")) {
            	responseMap.put("message", e.getMessage());
            } else {
            	responseMap.put("message", "오류가 발생했습니다. 다시 시도해주세요.");    
            }
        }
        return responseMap;
    }

}