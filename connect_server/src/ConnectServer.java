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
	
	/** PeasyCam **/
	public PeasyCam cam;
	
	/** Minim **/
	public Minim minim;
	public AudioPlayer song;
	
	/** Spacebrew Sockets **/
	public Spacebrew sb;

	/** Grid Lengths **/
	public int variableLength;
	
	
	/** Game Controller **/
	/** move logic later **/
	public NoteManager noteM;
	public boolean[] beats;
	
	/** Sound Manager **/
	public SoundManager soundM;
	
	/** Client Manager **/
	public ClientManager clientM;
	
	/** Socket Manager **/
	public SocketManager socketM;
	
	/**
	 * Debugging
	 */
	Column[] columns;
	Row[] rows;
	Tile[] tiles;
	Column c1;
	Row r1;
	Tile t1;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		
		/** Initialize Grid Variables **/
		this.numColumns = 16;
		this.numRows = 16;
		this.marginOffset = 30;
		
		this.numClients = 4;
		this.variableLength = 400;
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

	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	public void setup() {
//		
		// Sets window to be resizable.
		this.surface.setResizable(true);
		
		// Initialize Minim
		this.minim = new Minim(this);
		
		// Initialize Spacebrew
		this.sb = new Spacebrew(this);
		
		// Initialize Managers
		this.gui = new GUIManager(this);
		this.noteM = new NoteManager(this);
		this.soundM = new SoundManager(this);
		this.clientM = new ClientManager(this);
		this.socketM = new SocketManager(this);

		// Initialize PeasyCam
		cam = new PeasyCam(this, width / 2, height / 2, 0, 900);
		cam.setMinimumDistance(.001);
		cam.setMaximumDistance(50000);
		
		// Initialize and Setup Grid
		this.grid = new Grid(this, 0, 0, width, height);
		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset}).setPartitions(new int[]{numRows, numColumns});
		
		// Initialize Grid Animator
		this.gridAnimator = new GridAnimator(this);
		
		frameRate(60);
		print ("Loading sounds ...");
		delay(3000);
		print ("Setup finished.\n");
		
	}
	
	public void draw() {

		// Clear canvas.
		  background(0);
		  
		  this.soundM.play();
		  
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
		  
		  if (frameCount % 120 == 0) {
			  this.clientM.checkConnections();
		  }
		
	}
	
	public void onBooleanMessage( String name, boolean value ){
		println("got bool message " + name + " : " + value); 
		
	}
	
	public void onStringMessage (String name, String value) {
		println("got string message " + name + " : " + value);
		
		if ( name.equals("connect")) {
			String[] valueArray = value.split("#");
			String state = valueArray[0]; // registered, unregistered
			String id = valueArray[1];
			String data = valueArray[2];
			if (state.equals("ping")) {
				this.clientM.setClientPing(id, millis());
			} 
			else if (state.equals("unregistered")) {
				this.clientM.addClient(id);
			}
			else if (state.equals("rhythm")) {
				this.clientM.setClientRhythm(id, data);

			}
			else if (state.equals("melody")) {
				this.clientM.setClientHarmonyPitchInterval(id, data);
			}
			else if (state.equals("refrain")) {
				this.clientM.setClientRefrainPitchInterval(id, data);
			}
			else if (state.equals("instrument")) {
				this.clientM.setClientInstrument(id, data);
			}
		} 
	}
	
	public void mousePressed(){

	}
	
	public void mouseReleased() {

		
	}

	public void mouseDragged() {

	}
	
	/**
	 * Starts a PApplet application, and tells it to use this class, ConnectServer, as the program to run. 
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

