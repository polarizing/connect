import processing.core.PApplet;
import controlP5.*;

// See: https://forum.processing.org/one/topic/controlp5-variable-passing-problem.html
public class ConnectGUIManager implements ControlListener{
	private PApplet parent;
	private ControlP5 cp5;
	private ControlWindow window;
	private Slider s1, s2, s3, s4;
	private Button b1, b2;
	private int menuX;
	CallbackListener cb;
	// Really confusing as to why:
	// https://forum.processing.org/one/topic/a-basic-question-on-accessing-one-variable-from-different-classes-in-eclipse-java.html
	ConnectServer server;
	
	public ConnectGUIManager(PApplet p) {
		this.parent = p;
		// read up!!
		// https://forum.processing.org/one/topic/a-basic-question-on-accessing-one-variable-from-different-classes-in-eclipse-java.html
		this.server = (ConnectServer) p;
		this.cp5 = new ControlP5(p);
		this.cp5.addListener(this);
		
		  this.cp5.setColorForeground(0xffaa0000);
		  this.cp5.setColorBackground(0xff660000);
		  this.cp5.setColorActive(0xffff0000);
		  
		  this.menuX = parent.width - 200;
		
		// External Control Window Disabled in ControlP5 v2.0 ...
		// Look into using Java AWT Frame
//		window = cp5.addControlWindow("Control", 100, 100, 200, 200);
		
		// Number of Clients Slider
		s1 = this.cp5.addSlider("numClients")
	     .setPosition(menuX, 50)
	     .setWidth(100)
	     .setRange(1, 12) // values can range from big to small as well
	     .setValue(4)
	     .setNumberOfTickMarks(12)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		s2 = this.cp5.addSlider("numColumns")
		.setPosition(menuX, 75)
	     .setWidth(100)
	     .setRange(1, 15) // values can range from big to small as well
	     .setNumberOfTickMarks(15)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		s3 = this.cp5.addSlider("numRows")
		.setPosition(menuX, 100)
	     .setWidth(100)
	     .setRange(1, 12) // values can range from big to small as well
	     .setMax(12)
	     .setNumberOfTickMarks(12)
//	     .setSliderMode(Slider.FLEXIBLE)
	     .plugTo(this)
	     ;
		
		s4 = this.cp5.addSlider("marginOffset")
		.setPosition(menuX, 125)
		.setWidth(100)
	     .setRange(0, 150)
		.plugTo(this);
		
		b1 = this.cp5.addButton("addClient")
		.setPosition(menuX, 175)
		.setSize(100, 19)
		.plugTo(this)
		;
		
		b2 = this.cp5.addButton("removeClient")
		.setPosition(menuX, 200)
		.setSize(100, 19)
		.plugTo(this)
		;
		// connects controller to the controller method below.
		// (controlP5 version 0.5.9 or later)
	}
	
	public void numClients(int theValue) {
		Controller c = cp5.getController("numColumns");
		
	}
	
	
	public void addClient(int theValue) {
		parent.println("a button event from addClient: " + theValue);
		Controller c = cp5.getController("numColumns");
		Controller c2 = cp5.getController("numClients");
		int toSet = (int) c.getValue() + 1;
		float max = c.getMax();
		if (toSet > max) return;
		else {
			c.setValue(toSet);
			this.server.numClients = toSet;
			this.server.clients.add(new Client(parent, this.server.clients.size()));
		}
		c2.setValue(toSet);
		parent.println(toSet);
		parent.println("Set");
	}
	
	public void removeClient(int theValue) {
		Controller c = cp5.getController("numColumns");
		int toSet = (int) c.getValue() - 1;
		float min = c.getMin();
		if (toSet < min) return;
		else {
			c.setValue(toSet);
			this.server.numClients = toSet;
			this.server.clients.remove(this.server.clients.size() - 1);
		}
	}
	
	public void resize() {
		menuX = this.parent.width - 200;
		s1.setPosition(menuX, 50);
		s2.setPosition(menuX, 75);
		s3.setPosition(menuX, 100);
		s4.setPosition(menuX, 125);
		b1.setPosition(menuX, 150);
		b2.setPosition(menuX, 175);
		cp5.setGraphics(this.parent, 0, 0);
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