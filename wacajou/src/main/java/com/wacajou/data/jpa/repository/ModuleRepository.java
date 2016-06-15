package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
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
			"WHERE u = :user ";
	
	public final static String FIND_BY_RESPO = "SELECT m " + 
			"FROM Module m " + 
			"LEFT JOIN m.respo u " + 
			"WHERE u = :respo";
	
	public final static String FIND_BY_PARCOURS = "SELECT m " + 
			"FROM Module m " +
			"LEFT JOIN m.parcoursModule pm " +
			"LEFT JOIN pm.parcours p " +
			"WHERE p = :parcours ";

	
	Module findByName(String name);
	
	@Query(FIND_BY_RESPO)
	Module findByRespo(@Param("respo") User respo);
	
	Module findByUserModule(UserModule userModule);
	
	Module findByComments(Comments comments);
	
	List<Module> findByDomain(Domain domain);

	List<Module> findAll();
	
	Module findByParcoursModule(ParcoursModule parcoursModule);

	@Query(FIND_BY_USER)
	List<Module> findByUser(@Param("user") User user);
	
	@Query(FIND_BY_PARCOURS)
	List<Module> findByParcours(@Param("parcours")Parcours parcours);
	
}