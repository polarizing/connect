import processing.core.PApplet;
import processing.core.PVector;

/**
 * This class controls a trigger associated to a sound
 * and animation to each client.
 * @author Kevin Li
 *
 */

public class Trigger {
	
	private PApplet parent;
	
	public Trigger(PApplet p, int index, PVector pos) {
		parent = p;
		this.log("Trigger initialized");
	}
	
	public void log (String msg) {
		parent.print("From the Trigger Class: ");
		parent.println(msg);
	}
	
	public String toString() {
		return "A trigger class";
	}
	
}
