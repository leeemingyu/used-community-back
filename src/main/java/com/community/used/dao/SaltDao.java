package com.community.used.dao;

import org.apache.ibatis.annotations.Mapper;

import com.community.used.dto.SaltInfo;

@Mapper
public interface SaltDao {
	
	public void insertSalt(SaltInfo saltInfo) throws Exception;
	
	public String checkSalt(String email) throws Exception;

}
