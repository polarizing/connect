import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import controlP5.*;

// See: https://forum.processing.org/one/topic/controlp5-variable-passing-problem.html
public class ConnectGUIManager implements ControlListener{
	private PApplet parent;
	private ControlP5 cp5;
	private ControlWindow window;
	private Slider s1, s2, s3, s4;
	private Button b1, b2, b3;
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
		  this.parent.println("here:" + this.server.numClients);

		s1 = this.cp5.addSlider("numClients")
	     .setPosition(menuX, 50)
//	     .setWidth(100)
	     .setRange(1, 500) // values can range from big to small as well
//	     .setNumberOfTickMarks(201)
	     .plugTo(this)
	     ;
		  this.parent.println("here:" + this.server.numClients);

		s2 = this.cp5.addSlider("numColumns")
		.setPosition(menuX, 75)
//	     .setWidth(100)
	     .setRange(1, 500) // values can range from big to small as well
//	     .setNumberOfTickMarks(201)
	     .plugTo(this)
	     ;
		  this.parent.println("here:" + this.server.numColumns);

		s3 = this.cp5.addSlider("numRows")
		.setPosition(menuX, 100)
	     .setWidth(100)
	     .setRange(1, 12) // values can range from big to small as well
	     .setNumberOfTickMarks(12)
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

		b3 = this.cp5.addButton("setClientsActive")
				.setPosition(menuX, 225)
				.setSize(100, 19)
				.plugTo(this);
		
		// connects controller to the controller method below.
		// (controlP5 version 0.5.9 or later)
	}
	
//	public void numColumns(int theValue) {
//		Controller c = cp5.getController("numColumns");
//		if (theValue < this.server.clients.size()) {
//			c.setValue(theValue + 1);
//		}
//	}
//	
	public void numClients(int theValue) {
//		Controller c = cp5.getController("numColumns");
//		int numToAdd = theValue - this.server.clients.size();
//		if (numToAdd > 0) {
//			for (int i = 0; i < numToAdd; i++) {
//				c.setValue(theValue);
//				this.server.clients.add(new Client(parent, this.server.clients.size()));
//			}
//		}
//		else {
//			for (int i = 0; i < -numToAdd; i++) {
//				c.setValue(theValue);
//				this.server.clients.remove(this.server.clients.size() - 1);
//			}			
//		}
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
		float delta = this.server.grid.getColumnSize(this.server.grid.getColumnPartitions() + 1);
//		this.server.gridAnimator.slideIn(this, "right", delta, "addClientCb");
		this.server.gridAnimator.slideOut(this, "right", 50, "addClientCb");

//		delta = 50;
//		this.server.gridAnimator.slideIn(this, "top", delta);
		
//		parent.println("a button event from addClient: " + theValue);
		
//		Controller c = cp5.getController("numColumns");
//		Controller c2 = cp5.getController("numClients");
//		int toSet = (int) c.getValue() + 1;
//		float max = c.getMax();
//		if (toSet > max) return;
//		else {
//			c.setValue(toSet);
//			this.server.numClients = toSet;
//			this.server.clients.add(new Client(parent, this.server.clients.size()));
//		}
//		c2.setValue(toSet);
//		parent.println(toSet);
//		parent.println("Set");
	}

	public void addClientCb () {
		parent.println("CALLED!!!!!!");
//		this.server.grid.setOffsets(new int[]{30, 30, 30, 30});
//		this.server.grid.setColumnPartitions(++this.server.numColumns);
//		ArrayList<GridContainer> containers = this.server.grid.getFullColumnContainers();
//		Grid g = new Grid(this.parent, containers.get(containers.size() - 1)).setPartitions(new int[] {0, 4});
//		Client c = new Client(this.parent, this.server.clients.size(), g, 3);
//		this.server.clients.add(c);
//		
//		ArrayList<PVector> pts = g.getMiddlePartitionPoints();
//		for (int i = 0; i < c.getNumTriggers(); i++) {
//			int id = this.server.triggers.size() + i;
//			int triggerColor = this.parent.color(this.parent.random(255), this.parent.random(255), this.parent.random(255));
//			Trigger t = new Trigger(this.parent, id, pts.get(i), triggerColor);
//			this.server.triggers.add(t);
//			c.addTrigger(t);
//		}
//		this.server.numClients++;
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
	
	public void setClientsActive(int theValue) {
		parent.println("pressed");
		for (Client c : this.server.clients) {
			c.setConnected();
		}
//		this.server.gridAnimator.slideIn("right", 30, 2);
	}
	
	public void resize() {
		menuX = this.parent.width - 200;
		s1.setPosition(menuX, 50);
		s2.setPosition(menuX, 75);
		s3.setPosition(menuX, 100);
		s4.setPosition(menuX, 125);
		b1.setPosition(menuX, 150);
		b2.setPosition(menuX, 175);
		b3.setPosition(menuX, 200);
		cp5.setGraphics(this.parent, 0, 0);
	}
	
	
	@Override
	public void controlEvent(ControlEvent theEvent) {
//		 TODO Auto-generated method stub
//		parent.println("got something");
//		parent.println(theEvent);
	}
	

}