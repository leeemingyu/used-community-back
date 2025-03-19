package com.community.used.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.community.used.dto.Wishlist;

@Mapper
public interface WishlistDao {

		// 위시리스트 등록
		public void insertWishlist(Wishlist wishlist) throws Exception;
		
		// 닉네임으로 위시리스트 조회
		public List<Wishlist> getWishlistByNickname(String nickname) throws Exception;

		// 위시리스트 삭제
		public int deleteWishlist(Long wishlistId) throws Exception;
}
