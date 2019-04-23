package model.dev.kemomitten.mmo.server.util;
import java.util.HashMap;


public class ServerProcessProtocol {
	HashMap<String,String> ids = new HashMap<String,String>();
	
	
	public String processInput(String input) {
		String output = null;
		System.out.println(input);
		if(input.equals("something")) {
			output = "You did a thing.";
			
		}
		
		return output;
	}
}
