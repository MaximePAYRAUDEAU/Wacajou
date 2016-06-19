package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;

/**
 * 
 * @author Payraudeau Maxime
 *
 */
@Repository
@Transactional
public interface ParcoursModuleRepository extends
		JpaRepository<ParcoursModule, Long> {
	
	public static final String DELETE_BY_PARCOURS = "DELETE FROM ParcoursModule m WHERE m.parcours = :parcours ";

	List<ParcoursModule> findAll();

	List<ParcoursModule> findByParcours(Parcours parcours);

	List<ParcoursModule> findByModule(Module module);

	List<ParcoursModule> findByParcoursAndOptional(Parcours parcours,
			boolean optional);

	List<ParcoursModule> findByModuleAndOptional(Module module, boolean optional);

	List<ParcoursModule> findByParcoursAndModule(Parcours parcours,
			Module module);

	List<ParcoursModule> findByParcoursAndModuleAndOptional(Parcours parcours,
			Module module, boolean optional);

	@Modifying
	@Transactional
	@Query(DELETE_BY_PARCOURS)
	void deleteByParcours(@Param("parcours") Parcours parcours);

}