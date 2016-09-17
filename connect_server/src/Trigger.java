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
	private int id;
	private PVector pos;
	
	public Trigger(PApplet p, int id, PVector pos) {
		this.parent = p;
		this.id = id;
		this.pos = pos;
//		this.log("Trigger initialized");
	}
	
	/**
	 * Draws the trigger on the canvas.
	 */
	public void draw () {
		this.parent.ellipse(this.pos.x, this.pos.y, 50, 50);
	}
	
	public int getId() {
		return this.id;
	}
	
	public void log (String msg) {
		this.parent.print("From the Trigger Class: ");
		this.parent.println(msg);
	}
	
	public String toString() {
		return "A trigger class";
	}
	
}
