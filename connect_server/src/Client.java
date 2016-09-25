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
	private ArrayList<Trigger> triggers;
	private Grid grid;
	
	public Client(PApplet p, int id, Grid g, int numTriggers) {
		this.parent = p;
		this.clientConnected = true;
		this.clientId = id;
		this.numTriggers = numTriggers;
		this.triggers = new ArrayList<Trigger>();
		this.grid = g;
//		this.log("Client initialized.");
	}
	
	public Grid getGrid() {
		return this.grid;
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
	
	public ArrayList<Trigger> getTriggers () {
		return this.triggers;
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
	
	public void log (String msg) {
		this.parent.print("From the Client Class: ");
		this.parent.println(msg);
	}
	
	public String toString() {
		return "A client class";
	}
	
	public void draw () {
		if (!this.isConnected()) {
			GridContainer gc = this.grid.getContainer();
			this.parent.stroke(255, 255, 255);
			this.parent.fill(255,0,0,63);
			this.parent.rect(gc.x1, gc.y1, gc.width, gc.height);
			this.parent.fill(255, 255, 255);
//			this.parent.textAlign(this.parent.CENTER, this.parent.CENTER);
			this.parent.text("Waiting ...", gc.x1, (gc.y2 - gc.y1) / 2 ); 

		}
	}
	
}
