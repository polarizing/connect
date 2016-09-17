import processing.core.PApplet;
import controlP5.*;

public class ConnectGUIManager {
	private ControlP5 cp5;
	
	public ConnectGUIManager(PApplet p) {
		cp5 = new ControlP5(p);
		
		// Number of Clients Slider
		cp5.addSlider("numClients")
	     .setPosition(100,50)
	     .setWidth(75)
	     .setRange(1, 6) // values can range from big to small as well
	     .setValue(4)
	     .setNumberOfTickMarks(6)
	     .setSliderMode(Slider.FLEXIBLE)
	     ;
	}
	
}