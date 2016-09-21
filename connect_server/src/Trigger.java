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
	private int color;
	
	public Trigger(PApplet p, int id, PVector pos, int triggerColor) {
		this.parent = p;
		this.id = id;
		this.pos = pos;
		this.color = triggerColor;
//		this.log("Trigger initialized");
	}
	
	
	public void setPosition (PVector pos) {
		this.pos = pos;
	}
	
	public float getX () {
		return this.pos.x;
	}
	
	public float getY () {
		return this.pos.y;
	}
	
	/**
	 * Draws the trigger on the canvas.
	 */
	public void draw () {
		this.parent.noFill();
		this.parent.stroke(this.color);
		this.parent.strokeWeight(new Float(1.5));
		this.parent.ellipse(this.pos.x, this.pos.y, 8, 8);
		this.parent.fill(255);
		this.parent.stroke(1);
		this.parent.strokeWeight(1);

	}
	
	public int getId() {
		return this.id;
	}
	
	/**
	 * This log function creates a message.
	 * @param msg
	 */
	public void log (String msg) {
		this.parent.print("From the Trigger Class: ");
		PApplet.println(msg);
	}
	
	public String toString() {
		return "A trigger class";
	}
	
}
