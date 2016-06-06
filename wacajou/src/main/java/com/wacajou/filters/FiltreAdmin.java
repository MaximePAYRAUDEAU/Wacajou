package com.wacajou.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;

/**
 * Servlet Filter implementation class FiltreAdmin
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD }, urlPatterns = { "/wacajou/admin/*" })
public class FiltreAdmin implements Filter {
	public static final String SESSION_USER = "session_user";
	public static final String ACCES_RESTREINT = "/home";

    /**
     * Default constructor. 
     */
    public FiltreAdmin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        System.out.println("Admin filter called ");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        if(user != null)
        	if(user.getStatut().equals(Statut.ADMIN))
        		chain.doFilter(request, response);
        	else 
                res.sendRedirect( req.getContextPath() + ACCES_RESTREINT );
        else
        	res.sendRedirect( req.getContextPath() + ACCES_RESTREINT );
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
