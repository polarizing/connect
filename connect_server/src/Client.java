import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * This class controls each client connected to the application.
 * 
 * @author Kevin Li
 *
 */
public class Client {

	private PApplet parent;
	private ConnectServer server;
	
	/**
	 * Client Specific Stuff
	 */
	private String uuid;
	private int id;
	private boolean clientConnected;
	private float startPing;
	private float endPing;

	/**
	 * Client Data
	 */
	public boolean initialized;
	public String rhythm;
	public String refrain;
	public String harmonyPitchInterval;
	public String refrainPitchInterval;
	public String instrument;
	public String noteHistory;
	
	public BeatManager bm;
	
	/**
	 * Client Drawing Variables
	 */
	public Grid container;
	
	/**
	 * Instantiate a client.
	 * @param p
	 * @param id
	 * @param uuid
	 */

	public Client(PApplet p, int id, String uuid) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.clientConnected = true;
		this.id = id;
		this.uuid = uuid;
		this.initialized = false;
		this.init();
	}
	
	public void init() {
		this.startPing = 0;
		this.endPing = 0;
		this.rhythm = "----";
		this.refrain = "----";
		this.instrument = "";
		this.harmonyPitchInterval = "4";
		this.refrainPitchInterval = "4";
		this.bm = new BeatManager(this.parent);
		
		float x1 = this.id == 0 ? 0 : (this.parent.height / 4 * this.id);
		float x2 = this.parent.height / 4 * (this.id + 1);
		this.container = new Grid(this.parent, 0, x1, this.parent.width, x2);
		this.container.setOffsets(new int[]{0, 0, 0, 0}).setPartitions(new int[]{4, 20});
	}
	
	public boolean isInitialized () {
		return this.initialized;
	}
	
	public void setInitialized () {
		this.initialized = true;
	}

	public String getClientId() {
		return this.uuid;
	}
	
	public BeatManager getBeatManager() {
		return this.bm;
	}

	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
		this.refrain = this.rhythm;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getInstrument() {
		return this.instrument;
	}

	public void setHarmonyPitchInterval(String harmonyPitchInterval) {
		this.harmonyPitchInterval = harmonyPitchInterval;
	}

	public void setRefrainPitchInterval(String refrainPitchInterval) {
		this.refrainPitchInterval = refrainPitchInterval;
	}

	public String getRhythm() {
		return this.rhythm;
	}

	public String getRefrain() {
		return this.refrain;
	}

	public String getPitchInterval() {
		return this.harmonyPitchInterval;
	}

	public String getRefrainPitchInterval() {
		return this.refrainPitchInterval;
	}

	public boolean isConnected() {
		return this.clientConnected;
	}

	public float getPing() {
		return endPing - startPing;
	}

	public void setStartPing(float currTime) {
		startPing = currTime;
	}

	public void setEndPing(float currTime) {
		endPing = currTime;
	}

	public void setConnected() {
		this.clientConnected = true;
	}

	public void setDisconnected() {
		this.clientConnected = false;
	}

	public String toString() {
		return "A client: " + this.uuid;
	}

	public void draw() {
			Container c = this.container.getContainer();
			// draw container box
//			this.parent.pushMatrix();
//			this.parent.noFill();
//			this.parent.stroke(255);
//			this.parent.translate( c.center.x, c.center.y, 100);
//			this.parent.box(c.width, c.height, 200);
//			this.parent.popMatrix();
//			
//			this.parent.pushMatrix();
//			this.parent.rotateX(this.parent.radians((float) -90.0));			
//			this.parent.translate( c.center.x, c.center.y, 0);
//			this.parent.textSize((float) 26.0);
//			this.parent.textAlign(this.parent.CENTER);
//			this.parent.text("Waiting for player to connect ...", 0, 0, 250);
//			this.parent.popMatrix();
			this.container.draw();

			//		parent.println(this.noteHistory);
		
			this.bm.display(this.container);
			
//		int numBoxes = this.noteHistory.length();
//		int boxW = 30;
//		int boxH = 15;
//		int boxZ = 15;
//		for (int i = 0; i < this.noteHistory.length(); i++) {
//			int xOffset = (numBoxes - i) * boxW;
//			int beat = i % 4;
//			this.server.pushMatrix();
//			this.server.noStroke();
//			
//			this.server.translate(c.center.x - xOffset, c.center.y, 200);
//			
//			String s = Character.toString(this.noteHistory.charAt(i));
//			
//			if (s.equals("-")) {
//				
//			} else {
//				int noteValue;
//				if (s.equals("X")) noteValue = 10;
//				else noteValue = Integer.parseInt(s);
//				
//				switch (instrument) {
//					case "bassSynth": this.server.fill(this.server.color(255, 189, 211));
//									  break;
//					case "harpsichord": this.server.fill(this.server.color(217, 224, 199));
//					  break;
//					case "pluckedSynth": this.server.fill(this.server.color(139, 201, 53));
//					  break;
//					case "marimba": this.server.fill(this.server.color(226, 87, 67));
//					  break;
//				}
//				
//				this.server.pushMatrix();
//				switch(beat) {
//					case 0:	this.server.translate(0, 0, boxH * (10 - noteValue) * 4);
//							break;
//					case 1:	this.server.translate(0, -20, boxH * (10 - noteValue) * 4);
//						break;
//					case 2:	this.server.translate(0, -40, boxH * (10 - noteValue) * 4);
//						break;
//					case 3:	this.server.translate(0, -60, boxH * (10 - noteValue) * 4);
//						break;
//				}
//				this.server.box(boxW, boxH, boxZ);
//				this.server.popMatrix();
//
//			}
//			this.server.popMatrix();
//		}

	}

}
