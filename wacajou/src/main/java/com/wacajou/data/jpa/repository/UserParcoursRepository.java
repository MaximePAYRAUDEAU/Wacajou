package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserParcours;

public interface UserParcoursRepository extends JpaRepository<UserParcours, Long> {
	Page<UserParcours> findAll(Pageable pageable);
	Page<UserParcours> findByParcours(Parcours parcours, Pageable pageable);
	UserParcours findByUser(User user);
	Page<UserParcours> findByUserAndParcours(User user, Parcours parcours, Pageable pageable);

	
	
}