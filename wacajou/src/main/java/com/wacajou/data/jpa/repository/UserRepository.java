package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserModule;
import com.wacajou.data.jpa.domain.UserParcours;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	public final static String FIND_BY_MODULE = "SELECT u " + 
			"FROM User u " + 
			"LEFT JOIN u.userModule um " + 
			"LEFT JOIN um.module m " + 
			"WHERE m = :module";
	
	public final static String FIND_BY_PARCOURS = "SELECT u " + 
			"FROM User u " + 
			"LEFT JOIN u.userParcours up " + 
			"LEFT JOIN up.parcours p " + 
			"WHERE p = :parcours";

	User findByComments(Comments comments);

	User findByFnameAndLname(String fname, String lname);

	User findByMail(String mail);
	
	User findByLogin(String login);

	Page<User> findAll(Pageable pageable);

	Page<User> findByPromo(String promo, Pageable pageable);

	Page<User> findByUserParcours(UserParcours parcours, Pageable pageable);

	Page<User> findByUserModule(UserModule module, Pageable pageable);

	Page<User> findByStatut(Statut statut, Pageable pageable);

	@Query(FIND_BY_MODULE)
	List<User> findByModule(@Param("module") Module module);
	
	@Query(FIND_BY_PARCOURS)
	List<User> findByParcours(@Param("parcours") Parcours parcours);

}