package com.community.used.service;

import com.community.used.dao.ProductDao;
import com.community.used.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    private static final String UPLOAD_DIR = "E:\\ureca\\workspace\\mini-project\\UsedCommunityBack\\src\\main\\resources\\static\\images"; // 파일이 저장될 디렉토리 경로
    private static final String IMAGE_URL = "http://127.0.0.1:8080/images/"; // 이미지 요청 URL 경로

    // 랜덤 파일 이름 생성
    private String generateRandomFileName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "product_" + uuid + ".jpg"; // 예: product_123e4567-e89b-12d3-a456-426614174000.jpg
    }

    // 이미지 저장 메소드
    public String saveImage(MultipartFile image) throws IOException {
        String fileName = generateRandomFileName();
        File file = new File(UPLOAD_DIR, fileName);

        // 디렉토리가 없으면 생성
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 파일 저장
        image.transferTo(file);

        // 저장된 이미지 경로를 DB에 저장할 형태로 반환
        return fileName;
    }

    // 상품 등록
    public void insertProduct(Product product, MultipartFile image1, MultipartFile image2, MultipartFile image3) throws Exception {
        // 이미지 파일을 저장하고 경로를 설정
        if (image1 != null) {
            product.setImage1(saveImage(image1));  // 첫 번째 이미지 경로 저장
        }
        if (image2 != null) {
            product.setImage2(saveImage(image2));  // 두 번째 이미지 경로 저장
        }
        if (image3 != null) {
            product.setImage3(saveImage(image3));  // 세 번째 이미지 경로 저장
        }

        // 현재 시간을 설정
        product.setCreatedAt(new Date());

        // DB에 상품 정보와 이미지 경로 저장
        productDao.insertProduct(product);
    }

    // 상품 수정
    public void updateProduct(Product product, MultipartFile image1, MultipartFile image2, MultipartFile image3) throws Exception {
        // 이미지 파일을 저장하고 경로를 설정
        if (image1 != null) {
            product.setImage1(saveImage(image1));  // 첫 번째 이미지 경로 저장
        }
        if (image2 != null) {
            product.setImage2(saveImage(image2));  // 두 번째 이미지 경로 저장
        }
        if (image3 != null) {
            product.setImage3(saveImage(image3));  // 세 번째 이미지 경로 저장
        }

        // DB에 수정된 상품 정보 저장
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
        List<Product> products = productDao.getAllProducts();

        // 각 상품에 대해 이미지 경로를 URL로 변경
        for (Product product : products) {
            if (product.getImage1() != null && !product.getImage1().isEmpty()) {
                String imageUrls1 = addImageUrlsToPaths(product.getImage1());
                product.setImage1(imageUrls1);  // 상품의 image1 경로를 이미지 URL로 변경
            }
            if (product.getImage2() != null && !product.getImage2().isEmpty()) {
                String imageUrls2 = addImageUrlsToPaths(product.getImage2());
                product.setImage2(imageUrls2);  // 상품의 image2 경로를 이미지 URL로 변경
            }
            if (product.getImage3() != null && !product.getImage3().isEmpty()) {
                String imageUrls3 = addImageUrlsToPaths(product.getImage3());
                product.setImage3(imageUrls3);  // 상품의 image3 경로를 이미지 URL로 변경
            }
        }

        return products;
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


    // 카테고리별 상품 조회
    public List<Product> getProductsByCategory(String category) throws Exception {
        return productDao.getProductsByCategory(category);
    }
}
