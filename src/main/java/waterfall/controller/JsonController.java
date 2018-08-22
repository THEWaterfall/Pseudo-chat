package waterfall.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import waterfall.model.Message;
import waterfall.service.ChatService;


@WebServlet("/JsonController")
public class JsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(JsonController.class);
	
	@Resource(name="jdbc/WebApp")
	private DataSource dataSource;
	private ChatService chatService;
	
    public JsonController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	chatService = new ChatService(dataSource);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<Message> messages = (LinkedList<Message>) chatService.getMessages();
		File file = new File("chat.json");
		
		ObjectMapper mapper = new ObjectMapper();	
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, messages);
		
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=chat.json");
		response.setContentLength((int) file.length());

		logger.debug("Saving chat as JSON into {}", file);
		
		BufferedReader br = null;
		try (OutputStream os = response.getOutputStream()) {
			br = new BufferedReader(new FileReader(file));
			
			String currentLine;
			while((currentLine = br.readLine()) != null) {
				os.write(currentLine.getBytes(Charset.forName("UTF-8")));
				os.write("\n".getBytes());
			}
			
		} catch (IOException e) {
			logger.error("Error occured during saving chat as JSON");
		} finally {
			if(br!=null)
				br.close();
		}
		
		file.delete();	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
