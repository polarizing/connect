import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;
import peasy.*;

import java.util.List;

//import controlP5.ControlEvent;
import controlP5.Controller;

import java.util.ArrayList;

// Used to detect frame resize event.
import java.awt.event.*;

// Sockets
import spacebrew.*;

/**
 * The main class for the ConnectIO Server.
 * It acts as the overall controller of the game.
 * @author Kevin Li
 *
 */

public class ConnectServer extends PApplet{
	
	/** ConnectIO Instance Variables **/
	
	/** Helper Classes **/
	private GUIManager gui;
	public Grid grid;
	public GridAnimator gridAnimator;
	
	/** Grid Variables **/
	public int numColumns;
	public int numRows;
	public int marginOffset;	
	
	public int numClients;
	public List<Client> clients;
	public List<Trigger> triggers;
	
	/** Socket Variables **/

	public String server="localhost";
	public String name="Connect Sockets Server";
	public String description ="Connect Spacebrew Socket Server";
	
	public Spacebrew sb;
	
	public int color_on = color(255, 255, 50);
	public int color_off = color(255, 255, 255);
	int currentColor = color_off;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		
		/** Initialize Grid Variables **/
		this.numColumns = 1;
		this.numRows = 2;
		this.marginOffset = 30;
		
		this.numClients = 1;
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
		size(900, 600, P3D);
		// use P2D for OpenGL faster processing (by a lot, especially for lines ...)
//		smooth(4);
//		fullScreen(P2D);
	}

	public List<Client> getClients() {
		return this.clients;
	}
	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	public void setup() {
//		  
		// Sets window to be resizable.
		this.surface.setResizable(true);

		// Initialize GUI
		this.gui = new GUIManager(this);

		// Initialize and Setup Grid
		this.grid = new Grid(this, 0, 0, width, height);
		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset})
				 .setPartitions(new int[]{numRows, numColumns});
		
		// Initialize Grid Animator
		this.gridAnimator = new GridAnimator(this);
		
		// Create a list of GridContainers for each column
		ArrayList<GridContainer> containers = this.grid.getFullColumnContainers();
		// instantiate the spacebrewConnection variable
		sb = new Spacebrew( this );

		// declare your publishers
		sb.addPublish( "button_pressed", "boolean", true ); 


		// declare your subscribers
		sb.addSubscribe( "change_background", "boolean" );

		// connect to spacebre
		sb.connect(server, name, description );
		
		
		// Initialize all clients with their own grid system and add triggers.
		for (int i = 0, triggerId = 0; i < this.numClients; i++) {
			
			int numTriggers = 3;
			Grid g = new Grid(this, containers.get(i));
			Client c = new Client(this, i, g, numTriggers);
			this.clients.add(c);

			Grid clientGrid = c.getGrid().setPartitions(new int[] {1, 4});
			ArrayList<PVector> points = clientGrid.getMiddlePartitionPoints();
			for (int j = 0; j < c.getNumTriggers(); j++) {
				PVector pos = points.get(j);
				int triggerColor = color(random(255), random(255), random(255));
				Trigger t = new Trigger(this, triggerId, pos, triggerColor);
				this.triggers.add(t);
				c.addTrigger(t);
				triggerId++;
			}
		}
		
		print ("Setup finished.\n");
		noStroke();
		frameRate(32);
		
	}
	
	public void draw() {
		
		// optimize to call this only on resize event
		gui.resize();
		 
//		// Clear canvas on every frame.
		background(color(34, 34, 34));
		
		// draw button
		fill(255,0,0);
		stroke(200,0,0);
//		rectMode(CENTER);
		ellipse(width/2,height/2,250,250);

		// add text to button
		fill(230);
		if (mousePressed == true) {
			text("That Feels Good", width/2, height/2 + 12);
		} else {
			text("Click Me", width/2, height/2 + 12);
		}
		
		// FrameRate indicator
		fill(255);
		text("FPS: " + frameRate,20,20);

		// Draw initial line in middle of screen.
		stroke(45, 45, 45);
		strokeWeight(1);
		line(0, height / 2, width, height / 2);
		
		// This logic is moved to ConnectGUIManager
		//		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset})
		//		.setPartitions(new int[]{numRows, numColumns})
		
		// Resize grid -- move logic to resize callback
		// The main grid container is full canvas width and height
		this.grid.setGrid(0, 0, width, height);
		
		// Resize sub grids -- move logic to resize callback
		// The sub grid containers are full column containers
		ArrayList<GridContainer> containers = this.grid.getFullColumnContainers();
		for (int i = 0; i < this.clients.size(); i++) {
			Client c = this.clients.get(i);
			Grid g = c.getGrid();
			g.setGrid(containers.get(i));
			
			// resize triggers
			GridContainer gc = containers.get(i);
			ArrayList<PVector> pts = g.getMiddlePartitionPoints();
			ArrayList<Trigger> t = c.getTriggers();
			for (int j = 0; j < t.size(); j++) {
				PVector pos = pts.get(j);
				if (j == 0) {
					pos.x = pos.x - (g.getColumnSize() / 3 );
				}
				if (j == t.size() - 1) {
					pos.x = pos.x + (g.getColumnSize() / 3 );
				};
				
				t.get(j).setPosition(pts.get(j));
			}
			// draw sub grid
			g.draw(); // just for us
		}
		
		// Check for and run grid animations	
		this.gridAnimator.runAnimations();
		
		for (Client c : this.clients) {
			if (c.isConnected()) {
				for (Trigger t : c.getTriggers()) {
					t.draw();
				}
			}
			else {
				c.draw();
			}
		}
		
		// Logging
//		if (frameCount % 30 == 0) {
//			println("Rows = "+this.numRows);
//			println("Columns = "+this.numColumns);			
//			for (Client client : this.clients) {
//				int clientId = client.getClientId();
//				println("Client: " + clientId);
//				println("Client Triggers: " + client.getTriggers());
//			}
//			println("Total Clients = "+this.numClients);
//		}
	}
	
	
	public void mousePressed(){
		  ellipse( mouseX, mouseY, 2, 2 );
		  text( "x: " + mouseX + " y: " + mouseY, mouseX + 2, mouseY );

			// send message to spacebrew
			sb.send( "button_pressed", true);
		}
	
	public void mouseReleased() {
		// send message to spacebrew
		sb.send( "button_pressed", false);
	}

	public void mouseDragged() {

	}
	public void initTriggers() {
		
	}
	
	public void onBooleanMessage( String name, boolean value ){
		println("got bool message " + name + " : " + value); 
	
		// update background color
		if (value == true) {
			currentColor = color_on;
		} else {
			currentColor = color_off;
		}
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

