package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserModule;
import com.wacajou.data.jpa.domain.UserParcours;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public final static String FIND_BY_MODULE = "SELECT u " + 
			"FROM User u " + 
			"LEFT JOIN u.userModule um " + 
			"LEFT JOIN um.module m " + 
			"WHERE m.name = :moduleName";
	
	public final static String FIND_BY_PARCOURS = "SELECT u " + 
			"FROM User u " + 
			"LEFT JOIN u.userParcours up " + 
			"LEFT JOIN up.parcours p " + 
			"WHERE p.name = :parcoursName";

	User findByComments(Comments comments);

	User findByFnameAndLname(String fname, String lname);

	User findByMail(String mail);

	Page<User> findAll(Pageable pageable);

	Page<User> findByPromo(String promo, Pageable pageable);

	Page<User> findByUserParcours(UserParcours parcours, Pageable pageable);

	Page<User> findByUserModule(UserModule module, Pageable pageable);

	Page<User> findByStatut(Statut statut, Pageable pageable);

	@Query(FIND_BY_MODULE)
	Page<User> findByModule(@Param("moduleName") String moduleName, Pageable pageable);
	
	@Query(FIND_BY_PARCOURS)
	Page<User> findByParcours(@Param("parcoursName") String parcoursName, Pageable pageable);

}