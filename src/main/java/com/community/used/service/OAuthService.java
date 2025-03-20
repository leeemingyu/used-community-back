package com.community.used.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.community.used.dao.LoginDao;
import com.community.used.dto.Login;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OAuthService {
	@Value("${kakao.api.key}")
	String KAKAO_API_KEY ;
	
	@Autowired
	LoginDao loginDao;
	
	public void insertLoginInfo(String email, String nickname, String kakaoToken) throws Exception {
		Login loginInfo=new Login(email, kakaoToken, nickname, null);
		loginDao.insertToken(loginInfo);
	}
	
	public String getKaKaoAccessToken(String code) {
	    String access_Token = "";
	    String refresh_Token = "";
	    String reqURL = "https://kauth.kakao.com/oauth/token";
	   

	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);

	        // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	        StringBuilder sb = new StringBuilder();
	        sb.append("grant_type=authorization_code");
	        sb.append("&client_id=" + KAKAO_API_KEY );
	        sb.append("&redirect_uri=http://127.0.0.1:8080/kakaoLoginCallback");
	        sb.append("&code=" + code);
	        bw.write(sb.toString());
	        bw.flush();

	        // 결과 코드가 200이라면 성공
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";

	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);

	        // Jackson 라이브러리로 JSON 파싱
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(result);

	        access_Token = jsonNode.get("access_token").asText();
	        refresh_Token = jsonNode.get("refresh_token").asText();

	        System.out.println("access_token : " + access_Token);
	        System.out.println("refresh_token : " + refresh_Token);

	        br.close();
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return access_Token;
	}

	public String getKakaoUser(String token) {
	    String reqURL = "https://kapi.kakao.com/v2/user/me";

	    // access_token을 이용하여 사용자 정보 조회
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Authorization", "Bearer " + token);

	        // 결과 코드가 200이라면 성공
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);

	        // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";

	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);

	        // Jackson 라이브러리로 JSON 파싱
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(result);

	        int id = jsonNode.get("id").asInt();
	        boolean hasEmail = jsonNode.get("kakao_account").get("has_email").asBoolean();
	        String email = "";
	        if (hasEmail) {
	            email = jsonNode.get("kakao_account").get("email").asText();
	        }
        	String nickname = jsonNode.get("kakao_account").get("profile").get("nickname").asText();

	        System.out.println("nickname : " + nickname);
	        System.out.println("id : " + id);
	        System.out.println("email : " + email);
	        

	        br.close();

	        return nickname;

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
