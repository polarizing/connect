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
		
		  cp5.setColorForeground(0xffaa0000);
		  cp5.setColorBackground(0xff660000);
		  cp5.setColorActive(0xffff0000);
		
		// External Control Window Disabled in ControlP5 v2.0 ...
		// Look into using Java AWT Frame
//		window = cp5.addControlWindow("Control", 100, 100, 200, 200);
		
		// Number of Clients Slider
		s1 = this.cp5.addSlider("numClients")
	     .setPosition(parent.width - 200, 50)
	     .setWidth(100)
	     .setRange(1, 24) // values can range from big to small as well
	     .setValue(4)
	     .setNumberOfTickMarks(24)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;

//		s1.addCallback(new CallbackListener() {
//		    public void controlEvent(CallbackEvent theEvent) {
//		      switch(theEvent.getAction()) {
//		      case(ControlP5.ACTION_LEAVE):
//
//		      }
//		    }
//		  });
		
		this.cp5.addSlider("numColumns")
		.setPosition(parent.width - 200, 75)
	     .setWidth(100)
	     .setRange(1, 12) // values can range from big to small as well
	     .setNumberOfTickMarks(12)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		this.cp5.addSlider("numRows")
		.setPosition(parent.width - 200, 100)
	     .setWidth(100)
	     .setRange(1, 12) // values can range from big to small as well
	     .setMax(12)
	     .setNumberOfTickMarks(12)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		this.cp5.addSlider("marginOffset")
		.setPosition(parent.width - 200, 125)
		.setWidth(100)
	     .setRange(0, 150)
		.plugTo(this);
		
		this.cp5.addSlider("rightOffset")
		.setPosition(parent.width - 200, 150)
		.setWidth(100)
	     .setRange(0, 400)
		.plugTo(this);
		
		this.cp5.addButton("addClient")
//		.setValue(5)
		.setPosition(parent.width - 200, 175)
		.setSize(100, 19)
		.plugTo(this)
		;
		// connects controller to the controller method below.
		// (controlP5 version 0.5.9 or later)
	}
	
	public void addClient(int theValue) {
		parent.println("a button event from addClient: " + theValue);
		Controller c = cp5.getController("numColumns");
		float toSet = c.getValue() + 1;
		parent.println(toSet);
		c.setValue(toSet);
		c.setValue(toSet);
		parent.println("Set");
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