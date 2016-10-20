import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import peasy.*;
import ddf.minim.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;

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
	public SampleManager noteM;
	public boolean[] beats;
	
	/** Sound Manager **/
	public SoundManager soundM;
	
	/** Client Manager **/
	public ClientManager clientM;
	
	/** Socket Manager **/
	public SocketManager socketM;
	
	/** Drawing! **/
	/** Stars **/
	public List<Star> stars;
	public PImage bg;
	
    public Random rnd = new Random();
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		
		/** Initialize Grid Variables **/
		this.numColumns = 4;
		this.numRows = 16;
		this.marginOffset = 50;
		
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
//		size(1200, 900, P3D);
		smooth(8);
		fullScreen(P3D);
	}

	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	public void setup() {

		// Sets window to be resizable.
//		this.surface.setResizable(true);
		
		// Initialize Minim
		this.minim = new Minim(this);
		
		// Initialize Spacebrew
		this.sb = new Spacebrew(this);
		
		// Initialize Managers
		this.noteM = new SampleManager(this);
		this.soundM = new SoundManager(this);
		this.gui = new GUIManager(this);
		this.clientM = new ClientManager(this);
		this.socketM = new SocketManager(this);
		
		// Initialize PeasyCam
		cam = new PeasyCam(this, width / 2, height / 2, 0, 2000);
		cam.setMinimumDistance(.001);
		cam.setMaximumDistance(50000);
		
		// Initialize and Setup Grid
		this.grid = new Grid(this, 0, 0, width, height);
		this.grid.setOffsets(new int[]{marginOffset, marginOffset, marginOffset, marginOffset}).setPartitions(new int[]{numRows, numColumns});
		
		// Initialize Grid Animator
		this.gridAnimator = new GridAnimator(this);
		
		// Initialize Stars
		this.stars = new ArrayList<Star>();
		  for (int x = 0; x < 100; x++) {
//				 int[] heightExclusionRange =   IntStream.rangeClosed(0 + height / 6, height).toArray();
//				 int[] widthExclusionRange =   IntStream.rangeClosed(0 + width / 6, width - width / 6).toArray();
				 int[] heightExclusionRange = {1, 2};
				 int[] widthExclusionRange =  {1, 2};
				 int randomH = getRandomWithExclusion(this.rnd, 0, height, heightExclusionRange);
				 int randomW = getRandomWithExclusion(this.rnd, 0, width, widthExclusionRange);

				 Star s;
				 if ((int) random(0, 2) == 0) {
				     s = new Star(this, new PVector(random(0, width), randomH), 3, (float) 3.0 );					 
				 } else {
				     s = new Star(this, new PVector(randomW, random(0, height / 2)), 3, (float) 3.0 );					 
				 }
			     s.setColor(0, 0, 255);
			     s.setStartingPulseInOutFrame( (int) random(0, 1000) );
			     s.setPulseInOutSpeed(2);
			     s.setPulseScaleFrom((float) 0.1);
			     s.setPulseScaleTo((float) 1.0);
			     stars.add( s );
			  }
		  
		// Load Background
		 bg = loadImage("assets/bg2.jpg");
		 bg.resize(width, height);
		frameRate(120);
		print ("Loading sounds ...");
//		delay(6000);
		print ("Setup finished.\n");

	}
	
	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	public void draw() {

		// Clear canvas.
//		  background(this.bg);
			// Draw Background
//		background(0, 194, 178);
		  background(23, 12, 48);
		  this.cam.beginHUD();
//		  setGradient(0, height / 2, width, height / 2, color(100, 100, 100), color(21, 30, 26), 1);
//		  setGradient(0, 0, width, height / 2, color(22, 33, 30), color(22, 33, 30), 1);
		  
		  this.cam.endHUD();

		  this.cam.beginHUD();
		  for (int i = 0; i < stars.size(); i++) {
			    Star s = stars.get(i);
			    s.display();
			  }
		  this.cam.endHUD();
		  
		  // Play Sounds
		  if (this.clientM.hasActiveClients()) {
			  this.soundM.play();			  
		  }

		  // Update Clients
		  List<Client> clients = this.clientM.getClients();
		  
		  for (Client c : clients) {
			  c.draw();
		  }
		  
//			// Resize grid -- move logic to resize callback
//			// The main grid container is full canvas width and height
			this.grid.setGrid(0, 0, width, height);
//			this.grid.draw();
			this.gridAnimator.runAnimations();
			
		
			// Draw GUI
			this.gui.draw();
			
			// GUI RESIZE IS BROKEN
			// this.gui.resize();

		// FrameRate indicator (3D)
		  this.cam.beginHUD();
			fill(255);
			text("FPS: " + frameRate,20,20);
			text("X: " + mouseX + " Y: " + mouseY, 20, 40);
		  this.cam.endHUD();
		  
		  // Check Clients Still Alive

		  if (frameCount % 120 == 0) {
			  this.clientM.checkConnections();
		  }
		  
	}
	
	public void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

//		  noFill();
		  
		  
		  if (axis == 1) {  // Top to bottom gradient
		    for (int i = y; i <= y+h; i++) {
		      float inter = map(i, y, y+h, 0, 1);
		      int c = lerpColor(c1, c2, inter);
		      stroke(c);
		      line(x, i, x+w, i);
		    }
		  }  
		  else if (axis == 2) {  // Left to right gradient
		    for (int i = x; i <= x+w; i++) {
		      float inter = map(i, x, x+w, 0, 1);
		      int c = lerpColor(c1, c2, inter);
		      stroke(c);
		      line(i, y, i, y+h);
		    }
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

