package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserParcours;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface ParcoursRepository extends JpaRepository<Parcours, Integer>{
	Parcours findByName(String name);
	Parcours findByComments(Comments comments);
	Parcours findByParcoursModule(ParcoursModule parcoursModule);
	Parcours findByUserParcours(UserParcours userParcours);
	Parcours findByRespo(User respo);
	Page<Parcours> findAll(Pageable page);
	
}