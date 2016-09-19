import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * This class controls each client connected to the
 * application.
 * @author Kevin Li
 *
 */
public class Client {
	
	private PApplet parent;
	private int clientId;
	private String clientSocketId;
	private boolean clientConnected;
	private int numTriggers;
	private List<Trigger> triggers;
	
	public Client(PApplet p, int id) {
		this.parent = p;
		this.clientConnected = false;
		this.clientId = id;
		this.numTriggers = 3;
		this.triggers = new ArrayList<Trigger>();
//		this.log("Client initialized.");
	}
	
	public int getClientId() {
		return this.clientId;
	}
	
	public void addTrigger (Trigger trigger) {
		this.triggers.add(trigger);
	}
	
	public int getNumTriggers () {
		return this.numTriggers;
	}
	
	public boolean isClientConnected () {
		return this.clientConnected;
	}
	
	public void log (String msg) {
		this.parent.print("From the Client Class: ");
		this.parent.println(msg);
	}
	
	public String toString() {
		return "A client class";
	}
	
}
