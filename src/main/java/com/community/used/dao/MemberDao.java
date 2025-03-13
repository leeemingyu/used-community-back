package com.community.used.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.community.used.dto.Member;

@Mapper
public interface MemberDao {
	
	public Member login(Member m) throws Exception;
	
	public void insertMember(Member m) throws Exception;

	public void updateMember(Member m) throws Exception;
	
	public void deleteMember(String email) throws Exception;
	
}
