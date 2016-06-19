package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.domain.User;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface ParcoursRepository extends JpaRepository<Parcours, Long>{
	public final static String FIND_BY_USER = "SELECT p " + 
			"FROM Parcours p " + 
			"LEFT JOIN p.userParcours up " + 
			"LEFT JOIN up.user u " +
			"WHERE u = :user";
	
	public final static String FIND_BY_RESPO = "SELECT p " + 
			"FROM Parcours p " + 
			"LEFT JOIN p.respo u " + 
			"WHERE u = :respo";
	
	Parcours findByName(String name);
	
	Parcours findByComments(Comments comments);
	
	Parcours findByParcoursModule(ParcoursModule parcoursModule);
	
	@Query(FIND_BY_USER)
	Parcours findByUser(@Param("user") User user);
	
	@Query(FIND_BY_RESPO)
	Parcours findByRespo(@Param("respo") User respo);
	
	List<Parcours> findAll();

	
}