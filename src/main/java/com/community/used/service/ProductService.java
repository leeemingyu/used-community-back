package com.community.used.service;

import com.community.used.dao.ProductDao;
import com.community.used.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;


    private static final String UPLOAD_DIR = "E:\\ureca\\workspace\\mini-project\\UsedCommunityBack\\src\\main\\resources\\static\\images"; // 파일이 저장될 디렉토리 경로
    private static final String IMAGE_URL = "http://127.0.0.1:8080/images/"; // 이미지 요청 URL 경로

    private String generateRandomFileName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "product_" + uuid + ".jpg"; // 예: product_123e4567-e89b-12d3-a456-426614174000.jpg
    }
    
    public String saveImages(MultipartFile[] images) throws IOException {
        StringJoiner imagePaths = new StringJoiner(","); // 여러 이미지 경로를 구분자 (,)로 구분하여 저장
        
        for (MultipartFile image : images) {
            // 랜덤한 파일 이름 생성
            String fileName = generateRandomFileName();
            File file = new File(UPLOAD_DIR, fileName);
            
            // 파일을 저장
            image.transferTo(file);
            
            // 저장된 이미지 경로를 imagePaths에 추가
            imagePaths.add(fileName); // 파일 경로를 DB에 저장할 형태로 추가
        }

        return imagePaths.toString();  // "product_123e4567.jpg,product_789e4567.jpg" 이런 식으로 경로 반환
    }

    // 전체 상품 조회 시 이미지 URL을 포함하여 반환
    public List<Product> getAllProductsWithImageUrls() throws Exception {
        List<Product> products = productDao.getAllProducts();
        
        for (Product product : products) {
            // 이미지 경로에 URL 추가
            String imageUrls = addImageUrlsToPaths(product.getImagePaths());
            product.setImagePaths(imageUrls); // 상품의 이미지 경로를 이미지 URL로 변경
        }

        return products;
    }

    private String addImageUrlsToPaths(String imagePaths) {
        String[] imageNames = imagePaths.split(",");
        StringJoiner imageUrls = new StringJoiner(",");
        
        for (String imageName : imageNames) {
            imageUrls.add(IMAGE_URL + imageName); // 이미지 URL로 경로 추가
        }

        return imageUrls.toString();
    }
    
    // 상품 등록
    public void insertProduct(Product product) throws Exception {
        // DB에 저장
        productDao.insertProduct(product);
    }

    // 상품 수정
    public void updateProduct(Product product) throws Exception {
        productDao.updateProduct(product);
    }

    // 상품 삭제
    public void deleteProduct(Long id) throws Exception {
        productDao.deleteProduct(id);
    }

    // 상품 1개 조회
    public Product getProductById(Long id) throws Exception {
        return productDao.getProductById(id);
    }

    // 전체 상품 조회
    public List<Product> getAllProducts() throws Exception {
        return productDao.getAllProducts();
    }

    // 카테고리별 상품 조회
    public List<Product> getProductsByCategory(String category) throws Exception {
        return productDao.getProductsByCategory(category);
    }
}
