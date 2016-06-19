package com.wacajou.controller.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Status;
import com.wacajou.data.jpa.domain.User;

public class ModuleForm {

	private Module module;
	
	@NotNull
	@Size(min = 1, max = 25)
	private String name;

	private Long respo;

	@Size(min = 0, max = 5000)
	private String description;
	
	@NotNull
	private Domain domain;

	private MultipartFile image;

	@NotNull
	@Size(min = 0, max = 10)
	private String code;
	
	@Size(max = 10)
	private String semester;

	@Max(90)
	private double tpCours;
	
	@Max(90)
	private double project;
	
	@Max(30)
	private double ects;
	
	@Size(max = 90)
	private String link;
	
	public ModuleForm(){
		module = new Module();
		module.setStatus(Status.CREATED);
	}
	
	public Module getModule(){
		return this.module;
	}
	
	public Module save(Module module){
		module.setDescription(getDescription());
		module.setDomain(getDomain());
		module.setEcts(getEcts());
		module.setLink(getLink());
		module.setTpCours(getTpCours());
		module.setProject(getProject());
		module.setSemester(getSemester());
		return module;
	}
	
	public void setModule(Module module){
		this.module = module;
		this.name = module.getName();
		this.code = module.getCode();
		this.description = module.getDescription();
		this.link = module.getLink();
		this.ects = module.getEcts();
		this.project = module.getProject();
		this.tpCours = module.getTpCours();
		this.semester = module.getSemester();
		this.domain = module.getDomain();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		module.setName(name);
		this.name = name;
	}

	public Long getRespo() {
		return respo;
	}

	public void setRespo(Long respo) {
		this.respo = respo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		module.setDescription(description);
		this.description = description;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		module.setDomain(domain);
		this.domain = domain;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		module.setCode(code);
		this.code = code;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		module.setSemester(semester);
		this.semester = semester;
	}

	public double getTpCours() {
		return tpCours;
	}

	public void setTpCours(double tpCours) {
		module.setTpCours(tpCours);
		this.tpCours = tpCours;
	}

	public double getProject() {
		return project;
	}

	public void setProject(double project) {
		module.setProject(project);
		this.project = project;
	}

	public double getEcts() {
		return ects;
	}

	public void setEcts(double ects) {
		module.setEcts(ects);
		this.ects = ects;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		module.setLink(link);
		this.link = link;
	}
	
}
