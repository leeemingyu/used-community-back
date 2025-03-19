package com.community.used.dao;

import com.community.used.dto.Purchase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseDao {
	
	// 구매 내역 등록
	public void insertPurchase(Purchase purchase) throws Exception;
	
	// 닉네임으로 구매 내역 조회
	public List<Purchase> getPurchasesByNickname(String buyerNickname) throws Exception;

	// 구매 내역 삭제
	public int deletePurchase(Long productId) throws Exception;
}
