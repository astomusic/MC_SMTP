package server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	public static final String NEWLINE = System.getProperty("line.separator");
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		System.out.println("Client On!");
		
		String message;
			
		Socket socket = new Socket("127.0.0.1", 2525);
		OutputStream out = socket.getOutputStream();
		message = "MAIL FROM:<as@as.com>";
		message += NEWLINE;
		message += "RCPT TO:<test@test.com>";
		message += NEWLINE;
		message += "DATA";
		message += NEWLINE;
		message += "hello, world";
		message += NEWLINE;
		message += "bye, world";
		message += NEWLINE;
		message += "<CRLF>.<CRLF>";
		out.write(message.getBytes());
		
		socket.close();

		
	}

}
