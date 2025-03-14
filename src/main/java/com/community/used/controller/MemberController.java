package com.community.used.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.community.used.dto.Login;
import com.community.used.dto.Member;
import com.community.used.service.MemberService;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@PostMapping("logout")
	public void logout(@RequestHeader String authorization) {
		try {
			memberService.logout(authorization);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("login")
	public Map<String,String> tokenLogin(@RequestBody Member m) {
		
		Map<String,String> responseMap=new HashMap<>();
		
		try {
			Login loginInfo=memberService.tokenLogin(m);
			
			if(loginInfo!=null && loginInfo.getNickname()!=null && loginInfo.getToken()!=null) {
				responseMap.put("status", "ok");
				responseMap.put("nickname", loginInfo.getNickname());
				responseMap.put("Authorization", loginInfo.getToken());
			}else {
				responseMap.put("msg", "아이디 또는 비밀번호가 올바르지 않습니다");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseMap.put("msg", "아이디 또는 비밀번호가 올바르지 않습니다");
		}
		return responseMap;
	}
	
	@PostMapping("signup")
	public String insertMember(@RequestBody Member m) {
		try {
			memberService.insertMember(m);
			return "회원 가입 성공";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "회원 가입 실패";
		}
	}
	
	@PostMapping("updateMember")
	public String updateMember(@RequestBody Member m) {
		try {
			memberService.updateMember(m);
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "email과 pwd 확인해 주세요";
		}
	}
	
	@PostMapping("deleteMember")
	public String deleteMember(@RequestBody String email) {
		try {
			memberService.deleteMember(email);
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "email과 pwd 확인해 주세요";
		}
	}

}
