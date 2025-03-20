package com.community.used.service;

import com.community.used.dao.LoginDao;
import com.community.used.dao.ProductDao;
import com.community.used.dao.WishlistDao;
import com.community.used.dto.Product;
import com.community.used.dto.Wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;
    
    @Autowired
    LoginDao loginDao;
    
    @Autowired
    WishlistDao wishlistDao;
    
    @Value("${upload.dir}")
	private String UPLOAD_DIR;
    
    @Value("${image.url}")
	private String IMAGE_URL;

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
    public void insertProduct(Product product, MultipartFile image1, MultipartFile image2, MultipartFile image3, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(product.getNickname(), Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        
        loginDao.updateLoginTime(Authorization);
    	
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
    
    // 전체 상품 조회
    public List<Product> getAllProducts(String nickname, String Authorization) throws Exception {
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
    	
    	List<Product> products = productDao.getAllProducts();
    	List<Wishlist> wishlist = wishlistDao.getWishlistByNickname(nickname);
    	
    	Set<Long> wishlistProductIds = wishlist.stream().map(Wishlist::getProductId).collect(Collectors.toSet());
    	
        // 각 상품에 대해 이미지 경로를 URL로 변경
        for (Product product : products) {
        	product.setIsLiked(wishlistProductIds.contains(product.getId()));
        	
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
    // 상품 id로 조회
    public Product getProductById(Long id, String nickname, String Authorization) throws Exception {
        if (Authorization != null && !Authorization.isEmpty()) {
            boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
            if (isAuthorized) {
                loginDao.updateLoginTime(Authorization);
            } else {
                loginDao.deleteToken(Authorization);
                throw new Exception("잘못된 인증 정보입니다.");
            }
        }
        // 사용자의 찜 목록 조회
        List<Wishlist> wishlist = wishlistDao.getWishlistByNickname(nickname);
        Product product = productDao.getProductById(id);

        // 상품이 존재하면
        if (product != null) {
            // 상품 이미지 경로를 URL로 변환
            convertImagePathsToUrls(product);

            // 찜 목록에 해당 상품이 있는지 확인하여 isLiked 설정
            boolean isProductLiked = wishlist.stream()
                                              .anyMatch(w -> w.getProductId().equals(product.getId())); // Wishlist에서 상품 ID가 일치하는지 체크
            product.setIsLiked(isProductLiked); // isLiked 값 설정
        }

        return product;
    }
    // 닉네임으로 상품 조회
    public List<Product> getProductsByNickname(String nickname, String Authorization) throws Exception {
        if (Authorization != null && !Authorization.isEmpty()) {
            boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
            if (isAuthorized) {
                loginDao.updateLoginTime(Authorization);
            } else {
                loginDao.deleteToken(Authorization);
                throw new Exception("잘못된 인증 정보입니다.");
            }
        }

        List<Product> products = productDao.getProductsByNickname(nickname);
        for (Product product : products) {
            convertImagePathsToUrls(product);
        }
        return products;
    }
    // 카테고리별 상품 조회
    public List<Product> getProductsByCategory(String category, String nickname, String Authorization) throws Exception {
        if (Authorization != null && !Authorization.isEmpty()) {
            boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
            if (isAuthorized) {
                loginDao.updateLoginTime(Authorization);
            } else {
                loginDao.deleteToken(Authorization);
                throw new Exception("잘못된 인증 정보입니다.");
            }
        }

        List<Product> products = productDao.getProductsByCategory(category);
        for (Product product : products) {
            convertImagePathsToUrls(product);
        }
        return products;
    }
    
    // 상품 수정
    public void updateProduct(Product product, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(product.getNickname(), Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        
        loginDao.updateLoginTime(Authorization);

        // DB에 수정된 상품 정보 저장
        productDao.updateProduct(product);
    }

    // 상품 삭제
    public boolean deleteProduct(Long id, String nickname, String Authorization) throws Exception {
    	boolean isAuthorized = loginDao.checkToken(nickname, Authorization);
        
        if (!isAuthorized) {
        	loginDao.deleteToken(Authorization);
            throw new Exception("잘못된 접근입니다.");  // 인증 실패 시 예외 발생
        }
        
        loginDao.updateLoginTime(Authorization);
    	
        int rowsDeleted = productDao.deleteProduct(id);
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
    private void convertImagePathsToUrls(Product product) {
        if (product.getImage1() != null && !product.getImage1().isEmpty()) {
            product.setImage1(addImageUrlsToPaths(product.getImage1()));
        }
        if (product.getImage2() != null && !product.getImage2().isEmpty()) {
            product.setImage2(addImageUrlsToPaths(product.getImage2()));
        }
        if (product.getImage3() != null && !product.getImage3().isEmpty()) {
            product.setImage3(addImageUrlsToPaths(product.getImage3()));
        }
    }

    
}
 