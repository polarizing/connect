import processing.core.PApplet;
import processing.core.PVector;
import peasy.*;
import ddf.minim.*;

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
	
	/** Minim **/
	Minim minim;
	AudioPlayer song;
	
	/** Grid Lengths **/
	public int variableLength;
	
	/** Game Controller **/
	/** move logic later **/
	public NoteManager nm;
	public boolean[] beats;
	
	/** Sound Manager **/
	public SoundManager sm;
	
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
		
		this.numClients = 4;
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

		// Initialize PeasyCam
		cam = new PeasyCam(this, width / 2, height / 2, 0, 900);
		cam.setMinimumDistance(.001);
		cam.setMaximumDistance(50000);
		
		// Initialize and Setup Grid
		this.grid = new Grid(this, 0, 0, width, height);
		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset}).setPartitions(new int[]{numRows, numColumns});
		
		// Initialize Grid Animator
		this.gridAnimator = new GridAnimator(this);

		// Initialize Minim
		this.minim = new Minim(this);
		
		// Initialize Sound Manager
		this.sm = new SoundManager(this);
		
		// instantiate the spacebrewConnection variable
		sb = new Spacebrew( this );

		// declare your publishers
		sb.addPublish( "button_pressed", "boolean", true ); 

		// declare your subscribers
		sb.addSubscribe( "beatNotationChange", "string" );

		// connect to spacebrew
		sb.connect(server, name, description );
		
		print ("Setup finished.\n");
		frameRate(120);
		
	}
	
	public void draw() {

		// Clear canvas.
		  background(0);
		  
		  this.sm.play();
		  
		  // 3d z-axis
//		  Container gc = this.grid.getContainer();
//		  stroke(255, 255, 255);
//		  fill(255, 255, 255);
//		  line(gc.x1, gc.y2, 0, gc.x1, gc.y2, 500);
		  
		  
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
			this.grid.setGrid(0, 0, width, height);
			this.grid.draw();
			
			this.gridAnimator.runAnimations();
			
			this.gui.draw();
			
			// GUI RESIZE IS BROKEN
//		  this.gui.resize();

		// FrameRate indicator (3D)
		  this.cam.beginHUD();
			fill(255);
			text("FPS: " + frameRate,20,20);
			text("X: " + mouseX + " Y: " + mouseY, 20, 40);
		  this.cam.endHUD();
		
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

