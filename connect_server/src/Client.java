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
//	private String clientSocketId;
	private boolean clientConnected;
	private Grid grid;
	
	public Client(PApplet p, int id) {
		this.parent = p;
		this.clientConnected = true;
		this.clientId = id;
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	public int getClientId() {
		return this.clientId;
	}
	
	public boolean isConnected () {
		return this.clientConnected;
	}
	
	public void setConnected () {
		this.clientConnected = true;
	}
	
	public void setDisconnected () {
		this.clientConnected = false;
	}
	
	public String toString() {
		return "A client!";
	}
	
	public void draw () {
		if (!this.isConnected()) {
			
		}
	}
	
}
