package com.community.used.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.community.used.dto.Product;
import com.community.used.dto.Purchase;
import com.community.used.service.PurchaseService;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class purchaseController {

	@Autowired
    PurchaseService purchaseService;
	
	// 상품 등록
    @PostMapping("api/purchase/new")
    public Map<String, String> insertProduct(
    		@RequestBody Purchase p,
            @RequestHeader String Authorization) {

        Map<String, String> responseMap = new HashMap<>();
        try {
            // 상품 정보와 이미지 파일을 함께 저장
            purchaseService.insertPurchase(p, Authorization);

            responseMap.put("status", "ok");
            responseMap.put("message", "상품 구매 성공");
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
    
    // 닉네임으로 구매 상품 조회
    @GetMapping("api/purchase/nickname/{buyerNickname}")
    public Map<String, Object> getProductsByNickname(@PathVariable("buyerNickname") String buyerNickname, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            List<Purchase> purchase = purchaseService.getPurchasesByNickname(buyerNickname, Authorization);  // 카테고리로 상품 조회
            responseMap.put("status", "ok");
            responseMap.put("products", purchase);  // 조회된 구매 내역 리스트 반환
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "해당 사용자의 상품 조회에 실패했습니다.");
        }
        return responseMap;
    }
    
    // 구매 내역 삭제
    @DeleteMapping("api/purchase/{id}")
    public Map<String, String> deleteProduct(@PathVariable("productId") Long productId, @RequestHeader String Authorization) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            boolean isDeleted = purchaseService.deletePurchase(productId, Authorization);  // 상품 삭제 서비스 호출
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
