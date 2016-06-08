package com.wacajou.data.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public interface ParcoursModuleRepository extends JpaRepository<ParcoursModule, Long>{
	List<ParcoursModule> findAll();
	List<ParcoursModule> findByParcours(Parcours parcours);
	List<ParcoursModule> findByModule(Module module);
	List<ParcoursModule> findByParcoursAndOptional(Parcours parcours, boolean optional);
	List<ParcoursModule> findByModuleAndOptional(Module module, boolean optional);
	List<ParcoursModule> findByParcoursAndModule(Parcours parcours, Module module);
	List<ParcoursModule> findByParcoursAndModuleAndOptional(Parcours parcours, Module module, boolean optional);

}