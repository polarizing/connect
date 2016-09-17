import processing.core.PApplet;

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
	private int frameCount;
	private int numClients;
	private Client[] clients;
	private int sliderValue = 100;
	private ConnectGUIManager gui;
	
	/** Constructor to setup the application. */
	public ConnectServer() {
		this.frameCount = 0;
		this.numClients = 4;
		this.clients = new Client[this.numClients];
		print ("Starting ConnectIO application ...");
	}
	
	/**
	 * Setup Processing Application.
	 * https://processing.org/reference/settings_.html
	 */
	public void settings() {
		print ("\nSettings called.\n");
		size(640, 360);
	// fullScreen();
	}

	/**
	 * setup() function is run once, when the program starts. 
	 * It's used to define initial environment properties such as screen size
	 * and the initial display.
	 */
	
	public void setup() {

		// Sets window to be resizable.
		surface.setResizable(true);

		// Initialize all clients
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client(this);
		}
		
//		// Initialize all triggers
//		for (int i = 0; i < clients.length; i++) {
//			
//		}
		
		// Initialize GUI
		gui = new ConnectGUIManager(this);
		
//		  cp5.addSlider("numClients")
//		     .setPosition(100,50)
//		     .setRange(0,255)
//		     ;
		
		print ("Setup called.\n");
		
		
	}

	public void draw() {
		println(this.numClients);
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

