package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;

public interface ParcoursModuleRepository extends JpaRepository<ParcoursModule, Long>{
	Page<ParcoursModule> findAll(Pageable page);
	Page<ParcoursModule> findByParcours(Parcours parcours, Pageable pageable);
	Page<ParcoursModule> findByModule(Module module, Pageable pageable);
	Page<ParcoursModule> findByParcoursAndOptional(Parcours parcours, boolean optional, Pageable pageable);
	Page<ParcoursModule> findByModuleAndOptional(Module module, boolean optional, Pageable pageable);
	Page<ParcoursModule> findByParcoursAndModule(Parcours parcours, Module module, Pageable pageable);
	Page<ParcoursModule> findByParcoursAndModuleAndOptional(Parcours parcours, Module module, boolean optional, Pageable pageable);

}