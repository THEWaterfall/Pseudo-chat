package waterfall.service;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import waterfall.dao.ChatDAO;
import waterfall.model.Message;

public class ChatService {
	private ChatDAO chatDAO;
	
	public ChatService(DataSource dataSource) {
		chatDAO = new ChatDAO(dataSource);
	}

	public List<Message> getMessages() {
		LinkedList<Message> messages = (LinkedList<Message>) chatDAO.getMessages();
		return messages;
	}
	
	public void addMessage(String msg, String author) {
		chatDAO.addMessage(msg, author);
	}
	
}
