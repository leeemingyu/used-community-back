package com.community.used.dao;

import org.apache.ibatis.annotations.Mapper;

import com.community.used.dto.Member;

@Mapper
public interface MemberDao {
	
	public Member login(Member m) throws Exception;
	
	public void insertMember(Member m) throws Exception;

	public void updateMember(Member m) throws Exception;
	
	public void deleteMember(String email) throws Exception;
	
	boolean isEmailExists(String email);
    
    boolean isNicknameExists(String nickname);
}
