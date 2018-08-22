package waterfall.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
       
   
    public LoginController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickNameFromRequest = request.getParameter("nickname");
		logger.info("User {} with ip {} first logged in", nickNameFromRequest, request.getRemoteAddr());
		
		Cookie cookie = new Cookie("nickname", nickNameFromRequest);
		cookie.setMaxAge(60*60*60);
		response.addCookie(cookie);

		response.sendRedirect(request.getContextPath() + "/ChatController");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
