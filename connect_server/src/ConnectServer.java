import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

import controlP5.ControlEvent;

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
	
	/** Helper Classes **/
	private ConnectGUIManager gui;
	private GridHelper grid;
	
	/** Grid Variables **/
	private int numColumns;
	private int numRows;
	private int marginOffset;
	private int rightOffset;
	
	private int numClients;
	private List<Client> clients;
	private List<Trigger> triggers;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		
		/** Initialize Grid Variables **/
		this.numColumns = 4;
		this.numRows = 2;
		this.marginOffset = 30;
		this.rightOffset = 30;
		
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
//	 fullScreen();
	}

	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	public void setup() {

		// Sets window to be resizable.
		this.surface.setResizable(true);
		
		// Initialize GUI
		this.gui = new ConnectGUIManager(this);
		
		// Initialize all clients
		for (int i = 0; i < this.numClients; i++) {
			this.clients.add(new Client(this, i));
		}
		
		// Initialize all triggers
//		for (int clientId = 0, triggerId = 0; clientId < this.numClients; clientId++) {
//			Client client = this.clients.get(clientId);
//			int numTriggers = client.getNumTriggers();
//			for (int i = 0; i < numTriggers; i++) {
//				PVector pos = new PVector(100, 100);
//				Trigger t = new Trigger(this, triggerId, pos);
//				// These should point to the same trigger object (pass-by-reference).
//				// Add trigger to list of all triggers.
//				this.triggers.add(t);
//				// Add trigger to client's trigger list.
//				client.addTrigger(t);
//				triggerId++;
//			}
//		}
		print ("Setup called.\n");
		frameRate(30);
	}

	public void draw() {
		println(numColumns);

//		// Clear canvas on every frame.
		background(color(34, 34, 34));

		// Draw initial line in middle of screen.
		stroke(45, 45, 45);
		strokeWeight(1);
		line(0, height / 2, width, height / 2);
		
		// Update the grid helper. Optimize later.
			this.grid = new GridHelper(this, 0, 0, width, height);
			this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset})
					.setPartitions(new int[]{numRows, numColumns});
			this.grid.draw();
		
//		ArrayList<PVector> points = this.grid.getMiddlePartitionPoints();
//		points.add(0, new PVector(this.grid.getLeftMarginX(), this.grid.getMiddleY()));		
//		points.add(points.size(), new PVector(this.grid.getRightMarginX(), this.grid.getMiddleY()));
//		
//		for (PVector point :  points) {
//			ellipse(point.x, point.y, 15, 15);
//		}
//		
//		for (int i = 0, triggerId = 0; i < this.numClients; i++) {
//			Client client = this.clients.get(i);
//			int numTriggers = client.getNumTriggers();
//			PVector leftPoint = points.get(i);
//			PVector rightPoint = points.get(i+1);
//			float yPos = leftPoint.y;
//			float xPos = leftPoint.x;
//			float xDist = rightPoint.x - leftPoint.x;
//			float xInterval = xDist / (numTriggers + 1);
//
//			for (int j = 1; j <= numTriggers; j++) {
//				PVector pos = new PVector( (xInterval * j) + xPos, yPos);
//				Trigger t = new Trigger(this, triggerId, pos);
//				t.draw();
////				this.triggers.add(t);
////				client.addTrigger(t);
////				triggerId++;
//			}
//		}

//		
//		for (Trigger t : this.triggers) {
//			t.draw();
//		}
		
//		 Slower logging (1 sec)
		if (frameCount % 30 == 0) {
			println("Clients = "+this.numClients);
			println("Rows = "+this.numRows);
			println("Columns = "+this.numColumns);
//			println(this.grid.getPartitionPoints());
			println(this.grid.getPartitionPointsWithMargin()); // with margin
		}
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

