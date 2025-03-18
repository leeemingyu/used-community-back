package com.community.used.service;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.community.used.dao.LoginDao;
import com.community.used.dao.MemberDao;
import com.community.used.dao.SaltDao;
import com.community.used.dto.Login;
import com.community.used.dto.Member;
import com.community.used.dto.SaltInfo;
import com.community.used.util.OpenCrypt;

@Service
public class MemberService {
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	SaltDao saltDao;
	
	public Login tokenLogin(Member m) throws Exception {
		String pwd = m.getPwd();
		String salt = saltDao.checkSalt(m.getEmail());
		byte[] pwdOriginalHash=OpenCrypt.getSHA256(pwd, salt);
		String pwdHash=OpenCrypt.byteArrayToHex(pwdOriginalHash);
		m.setPwd(pwdHash);
		m=memberDao.login(m);
		
		if(m!=null) {
			String nickname=m.getNickname();
			
			if(nickname!=null && !nickname.trim().equals("")) {
				//member table에서 email과 pwd가 확인된 상황 즉 login ok
				
				String email=m.getEmail();
				
				//2. email을 hashing 한다
				byte[] EmailOriginalHash=OpenCrypt.getSHA256(email, salt);
				//3. db에 저장하기 좋은 포맷으로 인코딩한다
				String myToken=OpenCrypt.byteArrayToHex(EmailOriginalHash);
				
				//4. login table에 token 저장
				Login loginInfo=new Login(email, myToken, nickname, new Date());
				loginDao.insertToken(loginInfo);
				return loginInfo;
			}
		}
		
		return null;		 
	}
	
	public Member login(Member m) throws Exception {
		return memberDao.login(m);
	}
	
	public void insertMember(Member m) throws Exception {
	    
	    // 이메일 유효성 검사
	    String email = m.getEmail();
	    if (!isValidEmail(email)) {
	        throw new Exception("유효하지 않은 이메일 형식입니다.");
	    }

	    // 패스워드 유효성 검사
	    String pwd = m.getPwd();
	    if (!isValidPassword(pwd)) {
	        throw new Exception("패스워드는 8자리 이상이어야 하며, 특수문자와 숫자를 포함해야 합니다.");
	    }

	    // 이메일 중복 검사
	    boolean emailExists = memberDao.isEmailExists(email);
	    // 닉네임 중복 검사
	    String nickname = m.getNickname();
	    boolean nicknameExists = memberDao.isNicknameExists(nickname);

	    // 이메일과 닉네임 중복 검사 후, 조건에 맞는 예외 처리
	    if (emailExists && nicknameExists) {
	        throw new Exception("이메일이 이미 사용 중입니다. 닉네임이 이미 사용 중입니다.");
	    } else if (emailExists) {
	        throw new Exception("이메일이 이미 사용 중입니다.");
	    } else if (nicknameExists) {
	        throw new Exception("닉네임이 이미 사용 중입니다.");
	    }

	    // 패스워드 암호화
	    // 1. salt를 생성한다
	    String salt = UUID.randomUUID().toString();
	    
	    // 2. pwd를 hashing 한다
	    byte[] originalHash = OpenCrypt.getSHA256(pwd, salt);
	    // 3. db에 저장하기 좋은 포맷으로 인코딩한다
	    String pwdHash = OpenCrypt.byteArrayToHex(originalHash);

	    m.setPwd(pwdHash);
	    
	    // salt를 db에 저장
	    saltDao.insertSalt(new SaltInfo(email, salt));
	    
	    // member 정보를 db에 저장
	    memberDao.insertMember(m);
	}

	
	public void updateMember(Member m) throws Exception{
		memberDao.updateMember(m);
	}
	
	public void deleteMember(String email) throws Exception{
		memberDao.deleteMember(email);
	}

	public void logout(String authorization) throws Exception {
		loginDao.deleteToken(authorization);
		
	}
	
	// 이메일 유효성 검사 메서드
	private boolean isValidEmail(String email) {
	    // 이메일 패턴
	    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    return Pattern.matches(emailPattern, email);
	}
	
	// 패스워드 유효성 검사 메서드
	private boolean isValidPassword(String password) {
	    // 패스워드 패턴: 8자리 이상, 숫자 포함, 특수문자 포함
	    String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
	    return Pattern.matches(passwordPattern, password);
	}

	

}
