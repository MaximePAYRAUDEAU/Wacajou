package com.wacajou.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;

public class AdminInterceptor extends HandlerInterceptorAdapter {

	private String HOME;

	public AdminInterceptor(String HOME_PAGE) {
		this.HOME = HOME_PAGE;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null)
			if (user.isConnect()) {
				Statut statut = user.getStatut();
				if (statut.equals(Statut.RESPO_MODULE)
						|| statut.equals(Statut.RESPO_PARCOURS)
						|| statut.equals(Statut.RESPO_PEDAGOGIQUE)
						|| statut.equals(Statut.ADMIN))
					return true;
			}
		response.sendRedirect("/" + HOME + "/");
		return false;
	}
}