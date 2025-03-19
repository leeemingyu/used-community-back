package com.community.used.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.used.dao.LoginDao;
import com.community.used.dao.WishlistDao;
import com.community.used.dto.Purchase;
import com.community.used.dto.Wishlist;

@Service
public class WishlistService {

	@Autowired
	WishlistDao wishlistDao;
	
	@Autowired
    LoginDao loginDao;
	
	// 위시리시트 등록
    public void insertWishlist(Wishlist wishlist, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(wishlist.getNickname(), Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        loginDao.updateLoginTime(Authorization);
        wishlistDao.insertWishlist(wishlist);
    }
    
    // 닉네임으로 위시리스트 조회
    public List<Wishlist> getWishlistByNickname(String wishNick, String nickname, String Authorization) throws Exception {
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
        return wishlistDao.getWishlistByNickname(wishNick);
    }
    
    // 위시리스트 삭제
    public boolean deleteWishlist(Long wishlistId, String nickname, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        
        loginDao.updateLoginTime(Authorization);
    	
        int rowsDeleted = wishlistDao.deleteWishlist(wishlistId);
        return rowsDeleted > 0;
    }
}
