import processing.core.PApplet;
import processing.core.PVector;
import peasy.*;

import java.util.List;

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
	public Grid grid2;
	
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
	
	/** PeasyCam **/
	public PeasyCam cam;
	
	/** Grid Lengths **/
	public int variableLength;
	
	/** Game Controller **/
	/** move logic later **/
	public NoteManager nm;
	public boolean[] beats;
	
	
	/**
	 * Debugging
	 */
	Column[] columns;
	Row[] rows;
	Tile[] tiles;
	Column c1;
	Row r1;
	Tile t1;
	
	/**
	 * For Fun
	 */
	Grid container;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		
		/** Initialize Grid Variables **/
		this.numColumns = 16;
		this.numRows = 16;
		this.marginOffset = 30;
		
		this.numClients = 1;
		this.clients = new ArrayList<Client>();
		this.triggers = new ArrayList<Trigger>();
		this.variableLength = 400;
		this.nm = new NoteManager(this);
		this.beats = new boolean[4];
		print ("Starting ConnectIO application ...");
	}
	
	/**
	 * Setup Processing Application.
	 * https://processing.org/reference/settings_.html
	 */
	public void settings() {
		print ("\nSettings called.\n");
		size(1200, 900, P3D);
		smooth(8);

		// use P2D for OpenGL faster processing (by a lot, especially for lines ...)
//		smooth(4);
//		fullScreen(P3D);
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
		this.grid = new Grid(this, 0, 0, width / 2, height);
		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset}).setPartitions(new int[]{numRows, numColumns});
		
		this.grid2 = new Grid(this, width / 2, 0, width, height);
		this.grid2.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset}).setPartitions(new int[]{numRows, numColumns});
		
		// Initialize Grid Animator
		this.gridAnimator = new GridAnimator(this);

//		this.container = new Grid(this, 0, 0, width, height);
//		this.container.setOffsets(new int[]{30, 30, 30, 30})
//				 .setPartitions(new int[]{7, 5});
		
		this.columns = this.grid.getColumns();
		this.rows = this.grid.getRows();
		this.tiles = this.grid.getTiles();
		
		
		for (Tile t : this.tiles) {
			t.tether();
		}
		
//		this.r1 = this.grid.getRow(1).tether();
//		this.c1 = this.grid.getColumn(1).tether();
//		this.t1 = this.grid.getTile(16*9-1).tether();
		
		// Initialize PeasyCam
		cam = new PeasyCam(this, width / 2, height / 2, 0, 900);
		cam.setMinimumDistance(.001);
		cam.setMaximumDistance(50000);
		
////		cam.setYawRotationMode();   // like spinning a globe
////		cam.setPitchRotationMode(); // like a somersault
////		cam.setSuppressRollRotationMode();
////		cam.setRollRotationMode();  // like a radio knob
//
//		cam.setWheelScale(0.2); // 1.0 by default
//
//		// Create a list of GridContainers for each column
//		ArrayList<GridContainer> containers = this.grid.getFullColumnContainers();
//		// instantiate the spacebrewConnection variable
//		sb = new Spacebrew( this );
//
//		// declare your publishers
////		sb.addPublish( "button_pressed", "boolean", true ); 
//
//		// declare your subscribers
//		sb.addSubscribe( "beatNotationChange", "string" );
//
//		// connect to spacebrew
//		sb.connect(server, name, description );
//		
//		// Initialize all clients with their own grid system and add triggers.
//		for (int i = 0, triggerId = 0; i < this.numClients; i++) {
//			
//			int numTriggers = 3;
//			Grid g = new Grid(this, containers.get(i));
//			Client c = new Client(this, i, g, numTriggers);
//			this.clients.add(c);
//
//			Grid clientGrid = c.getGrid().setPartitions(new int[] {1, 4});
//			ArrayList<PVector> points = clientGrid.getMiddlePartitionPoints();
//			for (int j = 0; j < c.getNumTriggers(); j++) {
//				PVector pos = points.get(j);
//				int triggerColor = color(random(255), random(255), random(255));
//				Trigger t = new Trigger(this, triggerId, pos, triggerColor);
//				this.triggers.add(t);
//				c.addTrigger(t);
//				triggerId++;
//			}
//		}
//		
//		print ("Setup finished.\n");
//		noStroke();
		frameRate(120);
		
	}
	
	public void draw() {
//		  
//		println(this.grid.getRowSize());
//		
		

		  background(0);
		  
		  // 3d z-axis
//		  Container gc = this.grid.getContainer();
//		  stroke(255, 255, 255);
//		  fill(255, 255, 255);
//		  line(gc.x1, gc.y2, 0, gc.x1, gc.y2, 500);
		  
		  for (Tile t : this.tiles) {
			  fill(color(random(255), random(255), random(255)));
//			  rect(t.x1, t.y1, t.width, t.height);
			  
			  pushMatrix();
			  translate( (t.x1 + t.x2) / 2, (t.y1 + t.y2) / 2, 250);
			  box(t.width, t.height, random(500));
			  popMatrix();
		  }
		  
//	  fill(color(random(255), random(255), random(255)));
//
//	  rect(r1.x1, r1.y1, r1.width, r1.height);
//	  fill(color(random(255), random(255), random(255)));
////
//	  rect(t1.x1, t1.y1, t1.width, t1.height);
//

//		  fill(color(random(255), random(255), random(255)));
//		  rect(c.x1, c.y1, c.width, c.height);  
//		  
//			// Resize grid -- move logic to resize callback
//			// The main grid container is full canvas width and height
			this.grid.setGrid(0, 0, width / 2, height);
			this.grid.draw();
			this.grid2.setGrid(width / 2, 0, width, height);
			this.grid2.draw();
			
////		  fill(color(255, 0, 0));
//		  noFill();
//		  stroke(255);
//		  box(5);
//		  pushMatrix();
//		  
//		  // note 1
//		  translate(250, 200, 150);
//		  // note 2
//		  translate(25, -15, 0);
//		  fill(50, 205, 50);
//		  noStroke();
//		  if (beats[0]) {
//			  box(25, 4, 4);			  
//		  }
//		  translate(25, 15, -30);
//
//		  fill(255, 0, 0);
//		  noStroke();
//		  if (beats[1]) {
//			  box(25, 4, 4);			  			  
//		  }
//
////		  // note 2
//		  translate(25, 15, -30);
//		  fill(50, 205, 50);
//		  noStroke();
//			  box(25, 4, 4);
//		  // note 3
//		  translate(25, 15, 20);
//		  fill(255, 105, 180);
//		  noStroke();
//			  box(25, 4, 4);
//		  // note 4
//		  translate(25, 15, -10);
//		  fill(255, 215, 0);
//		  noStroke();
//			  box(25, 4, 4);
//		  
//		  popMatrix();
//		  
//		  line(0, 20, 10, 20);

		  // makes the gui stay on top of elements
		  // drawn before.
//		 
//		  pushMatrix();
//		  translate(0, 0, 30);
		  this.gui.draw();
//		  popMatrix();
//		  
		  
//		// optimize to call this only on resize event
//		  this.gui.resize();
//		 
////		// Clear canvas on every frame.
//		background(color(34, 34, 34));
//		
		// FrameRate indicator (2D)
//		fill(255);
//		text("FPS: " + frameRate,20,20);
//		
//		text("X: " + mouseX + " Y: " + mouseY, 20, 40);
//		
		
		// FrameRate indicator (3D)
		  this.cam.beginHUD();
			fill(255);
			text("FPS: " + frameRate,20,20);
			
			text("X: " + mouseX + " Y: " + mouseY, 20, 40);
			
		  this.cam.endHUD();
		
//		// Draw initial line in middle of screen.
//		stroke(45, 45, 45);
//		strokeWeight(1);
//		line(0, height / 2, width, height / 2);
//		
		
		
//		// Resize sub grids -- move logic to resize callback
//		// The sub grid containers are full column containers
//		ArrayList<GridContainer> containers = this.grid.getFullColumnContainers();
//		for (int i = 0; i < this.clients.size(); i++) {
//			Client c = this.clients.get(i);
//			Grid g = c.getGrid();
//			g.setGrid(containers.get(i));
//			
//			// resize triggers
//			GridContainer gc = containers.get(i);
//			ArrayList<PVector> pts = g.getMiddlePartitionPoints();
//			ArrayList<Trigger> t = c.getTriggers();
//			for (int j = 0; j < t.size(); j++) {
//				PVector pos = pts.get(j);
//				if (j == 0) {
//					pos.x = pos.x - (g.getColumnSize() / 3 );
//				}
//				if (j == t.size() - 1) {
//					pos.x = pos.x + (g.getColumnSize() / 3 );
//				};
//				
//				t.get(j).setPosition(pts.get(j));
//			}
//			// draw sub grid
//			g.draw(); // just for us
//		}
//		
//		// Check for and run grid animations	
//		this.gridAnimator.runAnimations();
//		
//		for (Client c : this.clients) {
//			if (c.isConnected()) {
//				for (Trigger t : c.getTriggers()) {
//					t.draw();
//				}
//			}
//			else {
//				c.draw();
//			}
//		}
		
		// Logging
		
//		variableLength += 1;

	}
	
	
	public void mousePressed(){

	}
	
	public void mouseReleased() {
		// send message to spacebrew
//		sb.send( "button_pressed", false);
	}

	public void mouseDragged() {

	}
	public void initTriggers() {
		
	}
	
	public void onBooleanMessage( String name, boolean value ){
		println("got bool message " + name + " : " + value); 
		
	}
	
	public void onStringMessage (String name, String string) {
		println("got string message " + name + " : " + string);
		
		// check for name
		if ( name.equals("beatNotationChange")) {
			// do something
			
			nm.updateNotation(string);
//			println("going to do something here");
			for(int i = 0; i < string.length(); i++)
			{
			   char c = string.charAt(i);
			   if (c == '-') {
				   this.beats[i] = false;
			   } else {
				   this.beats[i] = true;
			   }
			}
//			println(this.beats);
			
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

