package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserModule;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface ModuleRepository  extends JpaRepository<Module, Long>{
	
	public static final String FIND_BY_USER = "SELECT m " + 
			"FROM Module m " + 
			"LEFT JOIN  m.userModule um " +
			"LEFT JOIN um.user u " +
			"WHERE u.id = :user ";
	
	Module findByName(String name);
	Module findByRespo(User respo);
	Module findByUserModule(UserModule userModule);
	Module findByComments(Comments comments);
	List<Module> findByDomain(Domain domain);

	List<Module> findAll();
	Module findByParcoursModule(ParcoursModule parcoursModule);

	@Query(FIND_BY_USER)
	List<Module> findByUser(@Param("user") int id);
	
	
}