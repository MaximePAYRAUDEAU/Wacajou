package com.wacajou.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	
	public final static String FIND_BY_USER = "SELECT u " + 
			"FROM UserInfo u " + 
			"LEFT JOIN u.user ui WHERE ui = :user";
	
	@Query(FIND_BY_USER)
	UserInfo findByUser(@Param("user") User user);
}
