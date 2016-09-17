import processing.core.PApplet;
import controlP5.*;

public class ConnectGUIManager {
	private PApplet parent;
	private ControlP5 cp5;
	private ControlWindow window;
	private Controller<Slider> numClients;
	
	public ConnectGUIManager(PApplet p) {
		this.parent = p;
		this.cp5 = new ControlP5(p);
		
		// External Control Window Disabled in ControlP5 v2.0 ...
		// Look into using Java AWT Frame
//		window = cp5.addControlWindow("Control", 100, 100, 200, 200);
		
		// Number of Clients Slider
		this.cp5.addSlider("numClients")
	     .setPosition(100,50)
	     .setWidth(75)
	     .setRange(1, 6) // values can range from big to small as well
	     .setValue(4)
	     .setNumberOfTickMarks(6)
	     .setSliderMode(Slider.FLEXIBLE)
	     ;
	}
	
}