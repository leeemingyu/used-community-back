package com.community.used.dto;

public class Product {
    private String name;      // 상품명 (최대 40자)
    private String image;     // 이미지 URL
    private String category;  // 카테고리
    private String description; // 상품 설명 (최대 2000자)
    private int price;        // 가격

    // 생성자
    public Product(String name, String image, String category, String description, int price) {
        setName(name);
        setImage(image);
        setCategory(category);
        setDescription(description);
        setPrice(price);
    }

    public Product() {
        super();
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "Product [name=" + name + ", image=" + image + ", category=" + category 
               + ", description=" + description + ", price=" + price + "]";
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 40) {
            throw new IllegalArgumentException("상품명은 40자 이내여야 합니다.");
        }
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        if (description.length() > 2000) {
            throw new IllegalArgumentException("상품 설명은 2000자 이내여야 합니다.");
        }
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        this.price = price;
    }
}
