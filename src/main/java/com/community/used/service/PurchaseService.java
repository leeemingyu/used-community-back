package com.community.used.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.community.used.dao.LoginDao;
import com.community.used.dao.ProductDao;
import com.community.used.dao.PurchaseDao;
import com.community.used.dto.Purchase;

@Service
public class PurchaseService {
	
	@Autowired
    PurchaseDao purchaseDao;
	
	@Autowired
    ProductDao productDao;
	
	@Autowired
    LoginDao loginDao;
	
	// 상품 구매
    public void insertPurchase(Purchase purchase, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(purchase.getBuyerNickname(), Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        loginDao.updateLoginTime(Authorization);
        productDao.updateProductStatus(purchase.getProductId(), "판매완료");
        
        
        // DB에 상품 정보와 이미지 경로 저장
        purchaseDao.insertPurchase(purchase);
    }
    
    // 닉네임으로 구매한 상품 조회
    public List<Purchase> getPurchasesByNickname(String buyerNickname, String nickname, String Authorization) throws Exception {
    	// 만약 Authorization이 있으면 토큰을 체크하고 로그인 시간을 업데이트
        if (Authorization != null && !Authorization.isEmpty()) {
            boolean isAuthorized = loginDao.checkToken(nickname, Authorization);  // 토큰 인증 확인
            if (isAuthorized) {
                loginDao.updateLoginTime(Authorization);  // 로그인 시간 업데이트
            } else {
            	loginDao.deleteToken(Authorization);
                throw new Exception("잘못된 인증 정보입니다.");
            }
        }
        return purchaseDao.getPurchasesByNickname(buyerNickname);  // nickname을 기준으로 구매한 상품을 조회하는 메서드 호출
    }
    
    // 구매 내역 삭제
    public boolean deletePurchase(Long productId, String nickname, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        
        loginDao.updateLoginTime(Authorization);
    	
        int rowsDeleted = purchaseDao.deletePurchase(productId);
        return rowsDeleted > 0;
    }
}
