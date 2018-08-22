package waterfall.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LoginController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickNameFromRequest = request.getParameter("nickname");
		System.out.println("User logged in " + nickNameFromRequest);
		System.out.println(request.getRemoteAddr());
		Cookie cookie = new Cookie("nickname", nickNameFromRequest);
		cookie.setMaxAge(60*60*60);
		response.addCookie(cookie);

		response.sendRedirect(request.getContextPath() + "/ChatController");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
