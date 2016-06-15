package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Rating;
import com.wacajou.data.jpa.domain.User;
/**
 * 
 * @author Payraudeau Maxime
 * @param <T>
 *
 */
public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
	List<Comments> findByParcours(Parcours parcoues);
	List<Comments> findByParcoursAndRating(Parcours parcours, Rating rating);
	List<Comments> findByModule(Module module);
	List<Comments> findByModuleAndRating(Module module, Rating rating);
	List<Comments> findByUser(User user);
	List<Comments> findByUserAndRating(User user, Rating rating);
	List<Comments> findByParcoursAndModule(Parcours parcours, Module module);
	List<Comments> findByParcoursAndModuleAndRating(Parcours parcours, Module module, Rating rating);
	List<Comments> findByModuleAndUser(Module module, User user);
	List<Comments> findByParcoursAndUser(Parcours parcours, User user);
	List<Comments> findByParcoursAndModuleAndUser(Parcours parcours, Module module, User user);
}