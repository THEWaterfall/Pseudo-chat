package waterfall.controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import waterfall.model.Message;
import waterfall.service.ChatService;


@WebServlet("/ChatController")
public class ChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Resource(name="jdbc/WebApp")
	private DataSource dataSource;
	private ChatService chatService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		chatService = new ChatService(dataSource);
	}

	public ChatController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		
		if(command == null) 
			command = "SHOWCHAT";

		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length<2) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginView.jsp");
			dispatcher.forward(request, response);
		} else {
			switch(command) {
				case "SHOWCHAT":
					showChat(request, response);
					break;
				case "SHOWCHATINFRAME":
					showChatView(request, response);
					break;
				case "SENDMSG":
					sendMessage(request, response);
					break;
				default:
					showChatView(request, response);
				}
		}

	}

	private void showChat(HttpServletRequest request, HttpServletResponse response) {
		getNameFromCookie(request, response);

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Chat.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
				e.printStackTrace();
		}
	}

	private void getNameFromCookie(HttpServletRequest request, HttpServletResponse response) {
		String nickname = null;
		Cookie[] cookies = request.getCookies();
		
		for(Cookie tempCookie: cookies) {
			if(tempCookie.getName().equals("nickname")) {
				nickname = tempCookie.getValue();
				request.setAttribute("nickname", nickname);
			}
		}
		
		
	}

	private void sendMessage(HttpServletRequest request, HttpServletResponse response) {
		String msg = request.getParameter("message");
		String author = request.getParameter("author");
		logger.info("User {} with ip {} sent message: '{}'", author, request.getRemoteAddr(),  msg);
	
		chatService.addMessage(msg, author);
	
		try {
			response.sendRedirect(request.getContextPath() + "/ChatController");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showChatView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getNameFromCookie(request, response);

		LinkedList<Message> messages = (LinkedList<Message>) chatService.getMessages();
		request.setAttribute("messages", messages);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ChatView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
