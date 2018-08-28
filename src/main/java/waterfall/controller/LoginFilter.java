package waterfall.controller;

import java.io.IOException;
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

@WebFilter({"/ChatController", "*.jsp"})
public class LoginFilter implements Filter {

    public LoginFilter() {
    		
    }

	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
	     HttpServletResponse response = (HttpServletResponse) res;
	     HttpSession session = request.getSession();
	     String loginURI = request.getContextPath() + "/LoginController";

	     boolean loggedIn = session != null && session.getAttribute("user") != null;
	     boolean loginRequest = request.getRequestURI().equals(loginURI);

	     if (loggedIn || loginRequest) {
	    	 chain.doFilter(request, response);
	     } else {
	    	 response.sendRedirect(loginURI);
	     }
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
