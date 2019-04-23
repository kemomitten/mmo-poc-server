package dev.kemomitten.mmo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import model.dev.kemomitten.mmo.server.util.ServerProcessProtocol;

public class App {

	public static void main(String[] args) {
		int portNumber = 9001;
		
		try{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String inputLine, outputLine;
			
			ServerProcessProtocol sp = new ServerProcessProtocol();
			//outputLine = sp.processInput(null);
			//out.println(outputLine);
			
			
			while(clientSocket.isConnected()) {
				while((inputLine = in.readLine()) != null) {
					outputLine = sp.processInput(inputLine);
					out.println(outputLine);
					if(outputLine.equals("Disconnect")) {
						break;
					}
				}
			}
			
		}catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "+portNumber+" or listening for a connection");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
