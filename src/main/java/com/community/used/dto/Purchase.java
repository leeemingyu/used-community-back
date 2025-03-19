package com.community.used.dto;

import java.util.Date;

public class Purchase {
	private Long productId;
    private String buyerNickname, productName, productCategory, productDescription, sellerNickname;
    private int productPrice;
    private Date purchaseDate;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getBuyerNickname() {
		return buyerNickname;
	}
	public void setBuyerNickname(String buyerNickname) {
		this.buyerNickname = buyerNickname;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getSellerNickname() {
		return sellerNickname;
	}
	public void setSellerNickname(String sellerNickname) {
		this.sellerNickname = sellerNickname;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Purchase(Long productId, String buyerNickname, String productName, String productCategory,
			String productDescription, String sellerNickname, int productPrice, Date purchaseDate) {
		super();
		this.productId = productId;
		this.buyerNickname = buyerNickname;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productDescription = productDescription;
		this.sellerNickname = sellerNickname;
		this.productPrice = productPrice;
		this.purchaseDate = purchaseDate;
	}
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Purchase [productId=" + productId + ", buyerNickname=" + buyerNickname + ", productName=" + productName
				+ ", productCategory=" + productCategory + ", productDescription=" + productDescription
				+ ", sellerNickname=" + sellerNickname + ", productPrice=" + productPrice + ", purchaseDate="
				+ purchaseDate + "]";
	}
    
    
    
    
}
