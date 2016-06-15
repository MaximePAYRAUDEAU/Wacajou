package com.wacajou.controller.connexion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;
import com.wacajou.security.Validate;

/**
 * Class ConnexionController that assure connexion in wacajou application.
 * 
 * @author Payraudeau Maxime
 *
 */
@Controller
public class ConnexionController extends GenericModelAttribute {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ParcoursService parcoursService;
	
	@RequestMapping("/login")
	public String connexion(Model model) {
		return "redirect:home";
	}

	/**
	 * Get POST method from /login Url that have @param login and @param mdp
	 * value Send to the view a message if successfully log on the application
	 * 
	 * @param login
	 * @param mdp
	 * @param model
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView connexion(
			@RequestParam(value = "login", required = true) String login,
			@RequestParam(value = "mdp", required = true) String mdp,
			HttpServletRequest request,
			ModelAndView modelAndView) {
		
		User user = userService.Connect(login, mdp);
		
		if (userService.getError() == null) {
			UserInfo info = null;
			info = userService.getInfos(user);
			if(info == null){
				info = new UserInfo();
				info.setActivities("Activities");
				info.setCv("Cv");
				info.setInternship("Stage");
				info.setLdm("Lettre de motivation");
				info.setMark("Notes");
				info.setUniversity("Université");
			}else{
				if(info.getActivities() == null)
					info.setActivities("Activities");
				if(info.getCv() == null)
					info.setCv("Cv");
				if(info.getInternship() == null)
					info.setInternship("Stage");
				if(info.getLdm() == null)
					info.setLdm("Lettre de motivation");
				if(info.getMark() == null)
					info.setMark("Notes");
				if(info.getUniversity() == null)
					info.setUniversity("Université");
			}
			
			int visits = (int) request.getServletContext().getAttribute(WebServletConfig.VIEW);
			request.getServletContext().setAttribute(WebServletConfig.VIEW, visits++);
			
			modelAndView.addObject("user", user);
			modelAndView.addObject("userInfo", info);
			modelAndView.addObject("userRight", user.getStatut());
			modelAndView.addObject("userParcours", userService.getUserParcours(user));
			modelAndView.addObject("userModule", userService.getUserModule(user));
			modelAndView.setViewName("home");
			return modelAndView;
		} else {
			modelAndView.addObject("message", "Fail to log on.");
			modelAndView.setViewName("redirect:home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/login/test", method = RequestMethod.POST)
	public ModelAndView connexionTest(
			@RequestParam(value = "login", required = true) String login,
			@RequestParam(value = "mdp", required = true) String mdp,
			HttpServletRequest request,
			ModelAndView modelAndView) {
		Validate.isValid(login, "");
		User user = userService.ConnexionAlwayTrue(login);
		if (userService.getError() == null) {
			UserInfo info = null;
			info = userService.getInfos(user);
			if(info == null){
				info = new UserInfo();
				info.setActivities("Activities");
				info.setCv("Cv");
				info.setInternship("Stage");
				info.setLdm("Lettre de motivation");
				info.setMark("Notes");
				info.setUniversity("Université");
			}else{
				if(info.getActivities() == null)
					info.setActivities("Activities");
				if(info.getCv() == null)
					info.setCv("Cv");
				if(info.getInternship() == null)
					info.setInternship("Stage");
				if(info.getLdm() == null)
					info.setLdm("Lettre de motivation");
				if(info.getMark() == null)
					info.setMark("Notes");
				if(info.getUniversity() == null)
					info.setUniversity("Université");
			}
			Parcours parcours = userService.getUserParcours(user);
			List<Module> module = userService.getUserModule(user);
			int visits = (int) request.getServletContext().getAttribute(WebServletConfig.VIEW);
			request.getServletContext().setAttribute(WebServletConfig.VIEW, visits++);
			Statut statut = user.getStatut();
			if(statut.equals(Statut.RESPO_MODULE))
				modelAndView.addObject("responsability", moduleService.getByRespo(user));
			else if(statut.equals(Statut.RESPO_PARCOURS))
				modelAndView.addObject("responsability", parcoursService.getByRespo(user));
			else if(statut.equals(Statut.RESPO_PEDAGOGIQUE))
				modelAndView.addObject("responsability", "all");
			else if(statut.equals(Statut.ADMIN))
				modelAndView.addObject("responsability", "admin");
			else
				modelAndView.addObject("responsability", "none");
			modelAndView.addObject("user", user);
			modelAndView.addObject("userInfo", info);
			modelAndView.addObject("userRight", statut);
			
			if(parcours == null)
				modelAndView.addObject("userParcours", false );
			else
				modelAndView.addObject("userParcours", parcours );
			if(module == null)
				modelAndView.addObject("userModule", false);
			else
				modelAndView.addObject("userModule", module);
			modelAndView.setViewName("home");
		} else {
			modelAndView.addObject("message", "Fail to log on.");
			modelAndView.setViewName("redirect:../home");
		}
		return modelAndView;
	}

	/**
	 * Get any method from /logout Url. Unlog user and return him to home page
	 * 
	 * @return Home page
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView deconnexion(SessionStatus session) {
		session.setComplete();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.clear();
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
