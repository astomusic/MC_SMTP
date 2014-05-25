package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SMTPServer {
	public static void main(String[] args) throws IOException {
		int port = 2525;
		ServerSocket server =  new ServerSocket(port);
		System.out.println("SMTPServer Start (port" + port + ")");
	
		Socket connection = null;
		while(true) {
			connection = server.accept();
			RequestThread thread = new RequestThread(connection);
			thread.start();
		}

	}

}
