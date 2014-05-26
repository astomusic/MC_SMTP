package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestThread extends Thread {
	public static final String NEWLINE = System.getProperty("line.separator");
	Socket conn;

	public RequestThread(Socket connection) {
		this.conn = connection;
	}

	public void run() {
		try {
			InputStream is = conn.getInputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			DataParser parser = new DataParser();
			
			String line = br.readLine();
			long time = System.currentTimeMillis();
			String sender = "";
			String reciever = "";
			String data = "";	
			
			while(true) {
				String header = parser.pasingHeader(line);
				String contents = parser.pasingContents(line);
				System.out.println("S : " + line);
				if(header.equals("MAIL")) {
					sender = contents;
					System.out.println("R : 250 OK");
					bw.write("250 OK" + NEWLINE);
					bw.flush();
				} else if (header.equals("RCPT")) {
					reciever = contents;
					System.out.println("R : 250 OK");
					bw.write("250 OK" + NEWLINE);
					bw.flush();
				} else if (header.equals("DATA")) {
					System.out.println("R : 354 Start mail input; end with <CRLF>.<CRLF>");
					bw.write("354 Start mail input; end with <CRLF>.<CRLF>" + NEWLINE);
					bw.flush();
					line = br.readLine();
					while(!line.equals("<CRLF>.<CRLF>")){
						System.out.println("S : " + line);
						data += line;
						data += NEWLINE;
						line = br.readLine();
					}
					System.out.println("S : " + line);
					System.out.println("R : 250 OK");
					bw.write("250 OK" + NEWLINE);
					bw.flush();
				}
				if(!sender.isEmpty() && !reciever.isEmpty() && !data.isEmpty()) {
					System.out.println("R : SEND MAIL");
					bw.write("SEND MAIL" + NEWLINE);
					bw.flush();
					break;
				}
				line = br.readLine();
			}
			
			String fileName = reciever + time + ".txt";
			System.out.println("Save File - FileName : " + fileName);
			String mail = sender + NEWLINE + reciever + NEWLINE + data;
			
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(mail);
			
			bw.close();
			out.close();
			is.close();
			conn.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}		

}
