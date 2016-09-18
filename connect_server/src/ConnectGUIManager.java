import processing.core.PApplet;
import controlP5.*;

// See: https://forum.processing.org/one/topic/controlp5-variable-passing-problem.html
public class ConnectGUIManager implements ControlListener{
	private PApplet parent;
	private ControlP5 cp5;
	private ControlWindow window;
	private Slider s1;
	CallbackListener cb;
	
	public ConnectGUIManager(PApplet p) {
		this.parent = p;
		this.cp5 = new ControlP5(p);
		this.cp5.addListener(this);
		
		// External Control Window Disabled in ControlP5 v2.0 ...
		// Look into using Java AWT Frame
//		window = cp5.addControlWindow("Control", 100, 100, 200, 200);
		
		// Number of Clients Slider
		s1 = this.cp5.addSlider("numClients")
	     .setPosition(parent.width - 200, 50)
	     .setWidth(75)
	     .setRange(1, 6) // values can range from big to small as well
	     .setValue(4)
	     .setNumberOfTickMarks(6)
	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;

		s1.addCallback(new CallbackListener() {
		    public void controlEvent(CallbackEvent theEvent) {
		      switch(theEvent.getAction()) {
		      case(ControlP5.ACTION_LEAVE):

		      }
		    }
		  });
		
		this.cp5.addSlider("numColumns")
		.setPosition(parent.width - 200, 75)
	     .setWidth(75)
	     .setRange(1, 24) // values can range from big to small as well
	     .setNumberOfTickMarks(6)
	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		this.cp5.addSlider("numRows")
		.setPosition(parent.width - 200, 100)
	     .setWidth(75)
	     .setRange(1, 24) // values can range from big to small as well
	     .setNumberOfTickMarks(6)
	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		this.cp5.addSlider("marginOffset")
		.setPosition(parent.width - 200, 125)
	     .setRange(0, 150)
		.plugTo(this);
		
		// connects controller to the controller method below.
		// (controlP5 version 0.5.9 or later)
	}
	
//	public void numClients(int theValue) {
//	  parent.println("a slider event. setting background to "+theValue);
//	}
//
//	public void numRows(int theValue) {
//		parent.println("wow");
//	}
	
	@Override
	public void controlEvent(ControlEvent theEvent) {
//		 TODO Auto-generated method stub
//		parent.println("got something");
//		parent.println(theEvent);
	}
	

}