package com.community.used.service;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.community.used.dao.LoginDao;
import com.community.used.dao.WishlistDao;
import com.community.used.dto.Product;
import com.community.used.dto.Purchase;
import com.community.used.dto.Wishlist;

@Service
public class WishlistService {

	@Autowired
	WishlistDao wishlistDao;
	
	@Autowired
    LoginDao loginDao;
	
	@Value("${image.url}")
	private String IMAGE_URL;
	
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
        List<Wishlist> wishs = wishlistDao.getWishlistByNickname(wishNick);
        for (Wishlist w : wishs) {
            convertImagePathsToUrls(w);
        }
        return wishs;
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
    
    // 이미지 경로에 URL 추가
    private String addImageUrlsToPaths(String imagePaths) {
        String[] imageNames = imagePaths.split(",");
        StringJoiner imageUrls = new StringJoiner(",");

        // 각 이미지 경로에 URL을 추가
        for (String imageName : imageNames) {
            imageUrls.add(IMAGE_URL + imageName);  // 이미지 URL로 경로 추가
        }

        return imageUrls.toString();
    }
    // 개별 상품의 이미지 경로를 URL로 변환하는 메서드
    private void convertImagePathsToUrls(Wishlist wishlist) {
        if (wishlist.getImage1() != null && !wishlist.getImage1().isEmpty()) {
        	wishlist.setImage1(addImageUrlsToPaths(wishlist.getImage1()));
        }
    }
}
