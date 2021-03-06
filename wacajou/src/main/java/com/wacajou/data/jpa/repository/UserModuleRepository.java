package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserModule;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface UserModuleRepository extends JpaRepository<UserModule, Long>{
	Page<UserModule> findAll(Pageable pageable);
	Page<UserModule> findByModule(Module module, Pageable pageable);
	Page<UserModule> findByUserAndModule(User user, Module module, Pageable pageable);
	List<UserModule> findByUser(User user);
	
}