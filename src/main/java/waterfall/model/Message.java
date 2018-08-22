package waterfall.model;

public class Message {
	private int id;
	private String message;
	private String author;
	
	public Message() {
		
	}
	
	@Override
	public String toString() {
		return "Message [message=" + message + ", author=" + author + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
