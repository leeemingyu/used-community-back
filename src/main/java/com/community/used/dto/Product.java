package com.community.used.dto;

import java.util.Date;

public class Product {
	private Long id;
    private String name;
    private String category;
    private String description;
    private int price;
    private Date createdAt;
    private String imagePaths;
    private String nickname;
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
	public String getImagePaths() {
		return imagePaths;
	}
	public void setImagePaths(String imagePaths) {
		this.imagePaths = imagePaths;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Product(Long id, String name, String category, String description, int price, Date createdAt,
			String imagePaths, String nickname) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.createdAt = createdAt;
		this.imagePaths = imagePaths;
		this.nickname = nickname;
	}
	public Product(String name, String category, String description, int price, String imagePaths, String nickname) {
	    this.name = name;
	    this.category = category;
	    this.description = description;
	    this.price = price;
	    this.imagePaths = imagePaths;
	    this.nickname = nickname;
	    this.createdAt = new Date();  // 상품 등록 시 생성 시간을 현재 시간으로 설정
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", price=" + price + ", createdAt=" + createdAt + ", imagePaths=" + imagePaths + ", nickname="
				+ nickname + "]";
	}
    
    
    
}
