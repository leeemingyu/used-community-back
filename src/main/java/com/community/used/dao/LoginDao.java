package com.community.used.dao;

import org.apache.ibatis.annotations.Mapper;
import com.community.used.dto.Login;

@Mapper
public interface LoginDao {	
	public void insertToken(Login login) throws Exception;

	public void deleteToken(String token) throws Exception;
	
    public boolean checkToken(String nickname, String Authorization) throws Exception;
    
    public void updateLoginTime(String Authorization) throws Exception;
}
