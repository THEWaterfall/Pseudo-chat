package waterfall.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import waterfall.service.UserService;
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
	@Resource(name="jdbc/WebApp")
	private DataSource dataSource;
	private UserService userService;
   
    public LoginController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	userService = new UserService(dataSource); 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath() + "/HomeController");
			return;
		}
		
		if (command == null) {
			command = "LOGIN";
		}
		
		if(command.equals("LOGIN")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginView.jsp");
			dispatcher.forward(request, response);
		} else if (command.equals("LOGGEDIN")) {
			checkLogin(request, response);
		}
	}

	
	private void checkLogin(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("nickname");
		String password = request.getParameter("password");
		boolean isExist = userService.checkByNameAndPass(name, password);
		
		try {
			if(isExist) {
				setSession(request, response);
				response.sendRedirect(request.getContextPath() + "/ChatController");
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/LoginController");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setSession(HttpServletRequest request, HttpServletResponse response) {
		logger.info("User {} with ip {} logged in", request.getAttribute("nickname"), request.getRemoteAddr());
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(60*60);
	
		session.setAttribute("user", request.getParameter("nickname"));
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
