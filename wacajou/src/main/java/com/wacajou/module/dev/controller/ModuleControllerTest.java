package com.wacajou.module.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.service.ModuleService;


@Controller
public class ModuleControllerTest {
	
	@Autowired
    private ModuleService moduleService;
	
	@RequestMapping(value="/module/add")
    public ModelAndView addTeamPage() {
        ModelAndView modelAndView = new ModelAndView("add-team-form");
        modelAndView.addObject("module", new Module());
        return modelAndView;
    }
}
