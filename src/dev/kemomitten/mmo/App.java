package dev.kemomitten.mmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import javax.swing.Timer;

import dev.kemomitten.mmo.map.Map;
import dev.kemomitten.mmo.map.entities.Entity;
import dev.kemomitten.mmo.map.structure.Block;

public class App {
	
	public static final int PORT = 9001;
	public static long NEXT_UID = 0;
	private Thread connectThread;
	private HashMap<String, Connection> users = new HashMap<String, Connection>();
	
	private Timer ticker;
	private long lastTick = System.currentTimeMillis();
	
	private Map map;
	
	public App() {
		try{
			// Init server socket
			final ServerSocket serverSocket = new ServerSocket(PORT);
			
			map = new Map();
			
			// Create seperate thread to accept any new incomming
			// Thread is created with a lambda expression to handle new connections
			connectThread = new Thread(() -> {
				while (true) {
					try {
						Connection c = new Connection(serverSocket.accept());
						// Give user whatever starting info needed
						
						// Add to pool of connected users
						while (users.containsKey(NEXT_UID+"")) {
							// If UID in use then increment till not in use
							NEXT_UID++;
						}
						c.sendMsg(NEXT_UID+"");
						users.put(NEXT_UID+"", c);
						Entity e = new Entity(0, 0, 32, 32);
						e.setDx(1);
						map.getBlocks().forEach((String uid, Block b) -> {
							c.sendMsg("add,"+uid+","+b.getClass().getName()+","+b.getState());
						});
						map.getEntities().forEach((String uid, Entity ent) -> {
							c.sendMsg("add,"+uid+","+ent.getClass().getName()+","+ent.getState());
						});
						map.addEntity(NEXT_UID+"", e);
						broadcast("add,"+NEXT_UID+","+e.getClass().getName()+","+e.getState());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// Start accepting connections
			connectThread.start();
			
			// Server loop, set to go 180 times a second
			ticker = new Timer(1000/180, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Calc delta
					double delta = System.currentTimeMillis()-lastTick;
					lastTick = System.currentTimeMillis();
					// Process events
					users.forEach((String uid, Connection con) -> {
						if(con.isClosed()) {
							// Disconnect
							System.out.println("removing "+uid);
							map.removeSprite(uid);
							users.remove(uid);
							broadcast("remove,"+uid);
						}
					});
					// Update everything
					map.update(delta);
					// Send new states
					map.getEntities().forEach((String uid1, Entity ent) -> {
						broadcast("update,"+uid1+","+ent.getClass().getName()+","+ent.getState());
					});
				}
			});
			ticker.start();
			
			while(ticker.isRunning());
			serverSocket.close();
		}catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "+PORT+" or listening for a connection");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void broadcast(String str) {
		users.forEach((String uid, Connection con) -> {
			con.sendMsg(str);
		});
	}
	
	public static void main(String[] args) {
		new App();
	}
}
