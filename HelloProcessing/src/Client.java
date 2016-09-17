import processing.core.PApplet;

/**
 * This class controls each client connected to the
 * application.
 * @author Kevin Li
 *
 */
public class Client {
	
	private String socketId;
	private boolean clientConnected;
	private PApplet parent;
	
	public Client(PApplet p) {
		parent = p;
		this.clientConnected = false;
		this.log("Client initialized.");
	}
	
	public boolean isClientConnected () {
		return this.clientConnected;
	}
	
	public void log (String msg) {
		parent.print("From the Client Class: ");
		parent.println(msg);
	}
	
	public String toString() {
		return "A client class";
	}
	
}
