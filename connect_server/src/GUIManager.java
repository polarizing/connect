import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import controlP5.*;

// See: https://forum.processing.org/one/topic/controlp5-variable-passing-problem.html
public class GUIManager implements ControlListener{
	private PApplet parent;
	private ControlP5 cp5;
	private ControlWindow window;
	private Slider s1, s2, s3, s4;
	private Slider n0, n1, n2, n3, n4;
	private Button b1, b2, b3, b4, b5, b6;
	private int menuX;
	CallbackListener cb;
	// Really confusing as to why:
	// https://forum.processing.org/one/topic/a-basic-question-on-accessing-one-variable-from-different-classes-in-eclipse-java.html
	ConnectServer server;
	
	public GUIManager(PApplet p) {
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
//		  this.parent.println("here:" + this.server.numClients);
		  n0 = this.cp5.addSlider("setSpeed")
				     .setPosition(menuX, 25)
				     .setRange(0, 4) // values can range from big to small as well
				     .setNumberOfTickMarks(17)
				     .plugTo(this)
				     ;
		  
		n1 = this.cp5.addSlider("rotateX")
	     .setPosition(menuX, 50)
	     .setRange(-4, 4) // values can range from big to small as well
	     .plugTo(this)
	     ;
		
		n2 = this.cp5.addSlider("rotateY")
			     .setPosition(menuX, 75)
			     .setRange(-4, 4) // values can range from big to small as well
			     .plugTo(this)
			     ;
		n3 = this.cp5.addSlider("rotateZ")
			     .setPosition(menuX, 100)
			     .setRange(-4, 4) // values can range from big to small as well
			     .plugTo(this)
			     ;
		n4 = this.cp5.addSlider("lowPassVal")
			     .setPosition(menuX, 125)
			     .setRange(0, 2000) // values can range from big to small as well
			     .plugTo(this)
			     ;
//		  this.parent.println("here:" + this.server.numClients);
		  
		  

		s2 = this.cp5.addSlider("highPassVal")
			     .setPosition(menuX, 150)
			     .setRange(0, 2000) // values can range from big to small as well
			     .plugTo(this)
			     ;
//		s3 = this.cp5.addSlider("numRows")
//		.setPosition(menuX, 175)
//	     .setWidth(100)
//	     .setRange(1, 50) // values can range from big to small as well
//	     .plugTo(this)
//	     ;
//
//		s4 = this.cp5.addSlider("marginOffset")
//		.setPosition(menuX, 200)
//		.setWidth(100)
//	     .setRange(0, 150)
//		.plugTo(this);
//		
//		b1 = this.cp5.addButton("addClient")
//		.setPosition(menuX, 175)
//		.setSize(100, 19)
//		.plugTo(this)
//		;
//		
//		b2 = this.cp5.addButton("removeClient")
//		.setPosition(menuX, 200)
//		.setSize(100, 19)
//		.plugTo(this)
//		;
//
//		b3 = this.cp5.addButton("setFast")
//				.setPosition(menuX, 225)
//				.setSize(100, 19)
//				.plugTo(this);
//		b4 = this.cp5.addButton("setMediumFast")
//				.setPosition(menuX, 250)
//				.setSize(100, 19)
//				.plugTo(this);
//		b5 = this.cp5.addButton("setModerate")
//				.setPosition(menuX, 275)
//				.setSize(100, 19)
//				.plugTo(this);
//		b6 = this.cp5.addButton("setSlow")
//				.setPosition(menuX, 300)
//				.setSize(100, 19)
//				.plugTo(this);
		
		this.cp5.setAutoDraw(false);
		
		// connects controller to the controller method below.
		// (controlP5 version 0.5.9 or later)
	}
	
	public void draw () {
		  this.parent.hint(this.parent.DISABLE_DEPTH_TEST);
		  this.server.cam.beginHUD();
		  cp5.draw();
		  this.server.cam.endHUD();
		  this.parent.hint(this.parent.ENABLE_DEPTH_TEST);
	}
	
	public void rotateX(float theValue) {
		parent.println(theValue);
		this.server.rotateX = theValue;
	}
	public void rotateY(float theValue) {
		parent.println(theValue);
		this.server.rotateY = theValue;
	}
	public void rotateZ(float theValue) {
		parent.println(theValue);
		this.server.rotateZ = theValue;
	}
	public void lowPassVal(float theValue) {
		this.server.soundM.lowPassVal = theValue;
	}
	
	public void highPassVal(float theValue) {
		this.server.soundM.highPassVal = theValue;
	}
//	
	public void numClients(int theValue) {
		Controller c = cp5.getController("numColumns");
		int numToAdd = theValue - this.server.clients.size();
		if (numToAdd > 0) {
			for (int i = 0; i < numToAdd; i++) {
				c.setValue(theValue);
				this.addClientCb();
			}
		}
		else {
			for (int i = 0; i < -numToAdd; i++) {
				c.setValue(theValue);
				this.server.clients.remove(this.server.clients.size() - 1);
			}			
		}
	}
	
	public void numColumns (int theValue) {
		this.server.grid.setColumnPartitions(theValue);

	}
	
	public void numRows (int theValue) {
		this.server.grid.setRowPartitions(theValue);

	}
	
	public void marginOffset (int theValue) {
		this.server.grid.setOffsets(new int[]{theValue, theValue, theValue, theValue});

	}
	
	public void addClient(int theValue) {
//		this.server.sb.send( "button_pressed", true ); 
		
		this.server.clientM.addClient("hi");
//		float delta = 150;
//		int rowPartitions = this.server.grid.getRowPartitions();
//		float delta = this.server.grid.getRowSize(rowPartitions + 1) + this.server.grid.getRowSize();
//		this.server.gridAnimator.slideIn(this, "top", delta, "emptyCb");
//		this.server.gridAnimator.slideIn(this, "right", delta, "emptyCb");
//		this.server.gridAnimator.slideIn(this, "bottom", delta, "emptyCb");
//		this.server.gridAnimator.slideIn(this, "left", delta, "addClientCb");
	}
	
	public void emptyCb() {
		
	}
	
	public void addClientCb () {
		parent.println("CALLED!!!!!!");
		
		this.server.grid.setOffsets(new int[]{30, 30, 30, 30});
		this.server.grid.setRowPartitions(++this.server.numRows);
				
//		// set for GUI
//		Controller c1 = cp5.getController("numColumns");
//		Controller c2 = cp5.getController("numClients");
//		int toSet = (int) c1.getValue() + 1;
//		float max = c1.getMax();
//		if (toSet > max) return;
//		else {
//			c1.setValue(toSet);
//			this.server.numClients = toSet;
//		}
//		c2.setValue(toSet);
	}
	
	
	public void removeClient(int theValue) {
		Controller c = cp5.getController("numColumns");
		Controller c2 = cp5.getController("numClients");
		int toSet = (int) c.getValue() - 1;
		float min = c.getMin();
		if (toSet < min) return;
		else {
			c.setValue(toSet);
			this.server.numClients = toSet;
			this.server.clients.remove(this.server.clients.size() - 1);
		}
		c2.setValue(toSet);
	}
	
	public void setSpeed (float theValue) {
		this.server.soundM.noteAverageSpeed = theValue;
	}
	
	public void setFast(int theValue) {
		this.server.soundM.noteOneTime = 0.124;
		this.server.soundM.noteTwoTime = 0.249;
		this.server.soundM.noteThreeTime = 0.374;
		this.server.soundM.noteFourTime = 0.499;

//		this.server.gridAnimator.slideIn("right", 30, 2);
	}
	
	public void setMediumFast(int theValue) {
		this.server.soundM.noteOneTime = 0.250;
		this.server.soundM.noteTwoTime = 0.500;
		this.server.soundM.noteThreeTime = 0.750;
		this.server.soundM.noteFourTime = 1.000;

//		this.server.gridAnimator.slideIn("right", 30, 2);
	}

	public void setModerate (int theValue) {
		this.server.soundM.noteOneTime = 0.500;
		this.server.soundM.noteTwoTime = 1.000;
		this.server.soundM.noteThreeTime = 1.500;
		this.server.soundM.noteFourTime = 2.000;
	}
	
	public void setSlow(int theValue) {
		this.server.soundM.noteOneTime = 1.000;
		this.server.soundM.noteTwoTime = 2.000;
		this.server.soundM.noteThreeTime = 3.000;
		this.server.soundM.noteFourTime = 4.000;

//		this.server.gridAnimator.slideIn("right", 30, 2);
	}
	
	public void resize() {
//		menuX = this.parent.width - 200;
//		s1.setPosition(menuX, 50);
//		s2.setPosition(menuX, 75);
//		s3.setPosition(menuX, 100);
//		s4.setPosition(menuX, 125);
//		b1.setPosition(menuX, 150);
//		b2.setPosition(menuX, 175);
//		b3.setPosition(menuX, 200);
//		cp5.setGraphics(this.parent, 0, 0);
	}
	
	
	@Override
	public void controlEvent(ControlEvent theEvent) {
//		 TODO Auto-generated method stub
//		parent.println("got something");
//		parent.println(theEvent);
	}
	

}