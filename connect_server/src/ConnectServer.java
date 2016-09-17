import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;
import java.util.ArrayList;

// Used to detect frame resize event.
import java.awt.event.*;

/**
 * The main class for the ConnectIO Server.
 * It acts as the overall controller of the game.
 * @author Kevin Li
 *
 */

public class ConnectServer extends PApplet{
	
	/** ConnectIO Instance Variables **/
	private int numClients;
	private List<Client> clients;
	private List<Trigger> triggers;
	private ConnectGUIManager gui;
	private GridHelper grid;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		this.numClients = 4;
		this.clients = new ArrayList<Client>();
		this.triggers = new ArrayList<Trigger>();
		print ("Starting ConnectIO application ...");
	}
	
	/**
	 * Setup Processing Application.
	 * https://processing.org/reference/settings_.html
	 */
	public void settings() {
		print ("\nSettings called.\n");
		size(900, 600);
	// fullScreen();
	}

	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	public void setup() {
		fill(0, 0, 0);
		rect(30, 30, 100, 100);
		// Setup a grid helper.
		this.grid = new GridHelper(this, 30, 30, 130, 130);
		// Sets thirty pixels margin to top, left, bottom, and right
		// Partitions grid into two rows and two columns.
		this.grid.setOffsets(new int[]{30, 30, 30, 30})
				.setPartitions(new int[]{4, 2})
				;
		this.grid.draw();
		println(this.grid.getPartitionPoints());
		
		// Sets window to be resizable.
		this.surface.setResizable(true);
		
		// Initialize GUI
		this.gui = new ConnectGUIManager(this);
		
		// Initialize all clients
		for (int i = 0; i < this.numClients; i++) {
			this.clients.add(new Client(this, i));
		}
		
		// Initialize all triggers
		for (int clientId = 0, triggerId = 0; clientId < this.numClients; clientId++) {
			Client client = this.clients.get(clientId);
			int numTriggers = client.getNumTriggers();
			for (int i = 0; i < numTriggers; i++) {
				PVector pos = new PVector(100, 100);
				Trigger t = new Trigger(this, triggerId, pos);
				// These should point to the same trigger object (pass-by-reference).
				// Add trigger to list of all triggers.
				this.triggers.add(t);
				// Add trigger to client's trigger list.
				client.addTrigger(t);
				triggerId++;
			}
		}
		print ("Setup called.\n");
		frameRate(120);
	}

	public void draw() {
		
//		// Clear canvas on every frame.
//		background(color(34, 34, 34));
//		
//		// Draw initial line in middle of screen.
//		stroke(45, 45, 45);
//		strokeWeight(1);
//		line(0, height / 2, width, height / 2);
//		
//		for (Trigger trigger : this.triggers) {
//			trigger.draw();
//		}		
//		
//		println(width);
//		// Slower logging (1 sec)
////		if (frameCount % 30 == 0) {
////			println("hi");
////			for (Trigger trigger : this.triggers) {
////				println(trigger.getId());
////			}
////		}
	}
	
	
	public void mousePressed(){
		  ellipse( mouseX, mouseY, 2, 2 );
		
		  text( "x: " + mouseX + " y: " + mouseY, mouseX + 2, mouseY );
		}
	
	public void initTriggers() {
		
	}
	
	/**
	 * Starts a PApplet application, and tells it to use this class, ConnectIO, as the program to run. 
	 * This is done by calling PApplet's main method and giving it the name of this class as a parameter.
	 * The class constructor is called first, then settings(), setup(), and the draw() function are called
	 * in that respective order.
	 * https://processing.org/tutorials/eclipse/
	 * @param args
	 */
	
	public static void main(String[] args) {
		PApplet.main(new String[] {"ConnectServer"});
	}
	
	
}

