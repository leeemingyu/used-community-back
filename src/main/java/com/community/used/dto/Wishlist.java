package com.community.used.dto;

import java.util.Date;

public class Wishlist {
	private Long productId;
	private String nickname, productName, isSold;
    private Date createAt; // 위시리스트 테이블 기준 createAt 최신 순으로 정렬해서 조회, 반환은 프로덕트 테이블 기준 시간으로 반환
    private int productPrice;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getIsSold() {
		return isSold;
	}
	public void setIsSold(String isSold) {
		this.isSold = isSold;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public Wishlist(Long productId, String nickname, String productName, String isSold, Date createAt,
			int productPrice) {
		super();
		this.productId = productId;
		this.nickname = nickname;
		this.productName = productName;
		this.isSold = isSold;
		this.createAt = createAt;
		this.productPrice = productPrice;
	}
	public Wishlist() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Wishlist [productId=" + productId + ", nickname=" + nickname + ", productName=" + productName
				+ ", isSold=" + isSold + ", createAt=" + createAt + ", productPrice=" + productPrice + "]";
	}
}
