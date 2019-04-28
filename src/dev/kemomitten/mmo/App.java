package dev.kemomitten.mmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import javax.swing.Timer;

public class App {
	
	public static final int PORT = 9001;
	public static long NEXT_UID = 0;
	private Thread connectThread;
	private HashMap<Long, Connection> users = new HashMap<Long, Connection>();
	private Timer ticker;
	
	public App() {
		try{
			// Init server socket
			final ServerSocket serverSocket = new ServerSocket(PORT);
			// Create seperate thread to accept any new incomming
			// Thread is created with a lambda expression to handle new connections
			connectThread = new Thread(() -> {
				try {
					Connection c = new Connection(serverSocket.accept());
					// Give user whatever starting info needed
					
					// Add to pool of connected users
					while (users.containsKey(NEXT_UID)) {
						// If UID in use then increment till not in use
						NEXT_UID++;
					}
					c.sendMsg(NEXT_UID+"");
					c.sendMsg("add,"+NEXT_UID+",dev.kemomitten.mmo.map.entities.Entity,0,0,32,32");
					users.put(NEXT_UID, c);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			// Start accepting connections
			connectThread.start();
			
			// Server loop, set to go 180 times a second
			ticker = new Timer(1000/180, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "+PORT+" or listening for a connection");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new App();
	}
}