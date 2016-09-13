import processing.core.PApplet;
import controlP5.*;

public class UsingProcessing extends PApplet{

	public static void main(String[] args) {
		PApplet.main(new String[] {"UsingProcessing"});
	}
	
//	An array of stripes
	Stripe[] stripes = new Stripe[50];
	ControlP5 cp5;
	Slider abc;

	public void settings() {
//		size(200,200);
		fullScreen();
	}

	public void setup() {
		// Initialize all "stripes"
		for (int i = 0; i < stripes.length; i++) {
			stripes[i] = new Stripe(this);
		}
		// Initialize ControlP5 GUI
		cp5 = new ControlP5(this);
		cp5.addSlider("sliderValue")
		   .setPosition(100, 50)
		   .setRange(0, 255)
		   .setSize(200, 30);

	}

	public void draw() {
		background(100);
		// Move and display all "stripes"
		for (int i = 0; i < stripes.length; i++) {
			stripes[i].move();
			stripes[i].display();
		}
	}
}
