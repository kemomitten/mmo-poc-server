package dev.kemomitten.mmo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Connection(Socket socket) {
		try {
			this.socket = socket;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String str) {
		out.println(str);
	}
	public String getMsg() {
		try {
			if(in.ready()) {
				return in.readLine();
			}else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
