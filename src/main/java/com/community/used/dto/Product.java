package com.community.used.dto;

import java.util.Date;

public class Product {
	private Long id;
    private String name;
    private String category;
    private String description;
    private int price;
    private Date createdAt;
    private String image1;
    private String image2;
    private String image3;
    private String nickname;
    private String isSold;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIsSold() {
		return isSold;
	}
	public void setIsSold(String isSold) {
		this.isSold = isSold;
	}
	public Product(Long id, String name, String category, String description, int price, Date createdAt, String image1,
			String image2, String image3, String nickname, String isSold) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.createdAt = createdAt;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.nickname = nickname;
		this.isSold = isSold;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", price=" + price + ", createdAt=" + createdAt + ", image1=" + image1 + ", image2=" + image2
				+ ", image3=" + image3 + ", nickname=" + nickname + ", isSold=" + isSold + "]";
	}
    
	
    
    
    
}
