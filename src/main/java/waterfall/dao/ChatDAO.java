package waterfall.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import waterfall.model.Message;
import waterfall.model.ParameterObject;
import waterfall.util.DBUtil;

public class ChatDAO {
	private DBUtil dbUtil;
	
	public ChatDAO(DataSource dataSource) {
		dbUtil = new DBUtil(dataSource);
	}

	public List<Message> getMessagesFromResultSet(ResultSet rs) {
		LinkedList<Message> messages = new LinkedList<>();
		
		try {
			while(rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setMessage(rs.getString("messages"));
				message.setAuthor(rs.getString("author"));
				messages.add(message);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		return messages;
	}
	
	public List<Message> getMessages() {
		String query = "SELECT * FROM chat.messages";
		ResultSet rs = null;
		
		rs = dbUtil.dbExecuteQuery(query);
		
		LinkedList<Message> messages = (LinkedList<Message>) getMessagesFromResultSet(rs);
		return messages;
	}
	
	public void addMessage(String msg, String author) {
//		String query = "INSERT INTO chat.messages(messages, author) VALUES('"+msg+"','"+author+"')";
//		dbUtil.dbExecuteUpdate(query);
		
		String query = "INSERT INTO chat.messages(messages, author) VALUES (?,?)";
		ParameterObject po = new ParameterObject();
		po.setValue1(msg);
		po.setValue2(author);
		
		dbUtil.dbExecuteUpdatePrepStmt(query, po);
	}
}	

