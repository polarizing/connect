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
	private Slider n1, n2, n3, n4;
	private Button b1, b2, b3;
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

		n1 = this.cp5.addSlider("noteOneTime")
	     .setPosition(menuX, 50)
	     .setRange(0, 4) // values can range from big to small as well
	     .setValue((float) 0.25)
	     .setNumberOfTickMarks(17)
	     .plugTo(this)
	     ;
		
		n2 = this.cp5.addSlider("noteTwoTime")
			     .setPosition(menuX, 75)
			     .setRange(0, 4) // values can range from big to small as well
			     .setValue((float) 0.50)
			     .setNumberOfTickMarks(17)
			     .plugTo(this)
			     ;
		n3 = this.cp5.addSlider("noteThreeTime")
			     .setPosition(menuX, 100)
			     .setRange(0, 4) // values can range from big to small as well
			     .setValue((float) 0.75)
			     .setNumberOfTickMarks(17)

			     .plugTo(this)
			     ;
		n4 = this.cp5.addSlider("noteFourTime")
			     .setPosition(menuX, 125)
			     .setRange(0, 4) // values can range from big to small as well
			     .setValue((float) 1.0)
			     .setNumberOfTickMarks(17)
			     .plugTo(this)
			     ;
//		  this.parent.println("here:" + this.server.numClients);
		  
		  

		s2 = this.cp5.addSlider("numColumns")
		.setPosition(menuX, 150)
	     .setRange(1, 50) // values can range from big to small as well
	     .plugTo(this)
	     ;
		  this.parent.println("here:" + this.server.numColumns);

		s3 = this.cp5.addSlider("numRows")
		.setPosition(menuX, 175)
	     .setWidth(100)
	     .setRange(1, 50) // values can range from big to small as well
	     .plugTo(this)
	     ;

		s4 = this.cp5.addSlider("marginOffset")
		.setPosition(menuX, 200)
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

		b3 = this.cp5.addButton("setFast")
				.setPosition(menuX, 225)
				.setSize(100, 19)
				.plugTo(this);
		
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
	
	public void noteOneTime(float theValue) {
		parent.println(theValue);
		this.server.soundM.noteOneTime = theValue;
	}
	public void noteTwoTime(float theValue) {
		parent.println(theValue);
		this.server.soundM.noteTwoTime = theValue;
	}
	public void noteThreeTime(float theValue) {
		parent.println(theValue);
		this.server.soundM.noteThreeTime = theValue;
	}
	public void noteFourTime(float theValue) {
		parent.println(theValue);
		this.server.soundM.noteFourTime = theValue;
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
		
		float delta = 150;
//		int rowPartitions = this.server.grid.getRowPartitions();
//		float delta = this.server.grid.getRowSize(rowPartitions + 1) + this.server.grid.getRowSize();
		this.server.gridAnimator.slideIn(this, "top", delta, "emptyCb");
		this.server.gridAnimator.slideIn(this, "right", delta, "emptyCb");
		this.server.gridAnimator.slideIn(this, "bottom", delta, "emptyCb");
		this.server.gridAnimator.slideIn(this, "left", delta, "addClientCb");
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
	
	public void setFast(int theValue) {
		this.server.soundM.noteOneTime = 0.124;
		this.server.soundM.noteTwoTime = 0.249;
		this.server.soundM.noteThreeTime = 0.374;
		this.server.soundM.noteFourTime = 0.499;

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