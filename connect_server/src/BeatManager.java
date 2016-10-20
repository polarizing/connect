import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class BeatManager {

	private PApplet parent;
	private ConnectServer server;
	public ArrayList<BeatMeasure> beatMeasures;
	public int numBeats;
	public int numMeasures;
	
	public BeatManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.numBeats = 0;
		this.beatMeasures = new ArrayList<BeatMeasure>();
	}
	
	public boolean hasMeasures () {
		return this.beatMeasures.size() > 0;
	}
	
	public BeatMeasure getCurrentMeasure () {
		return this.beatMeasures.get(beatMeasures.size() - 1);
	}
	
	public ArrayList<BeatMeasure> getMeasures () {
		return this.beatMeasures;
	}
		
	public void addBeatMeasure () {
		int measureId = this.beatMeasures.size();
		BeatMeasure measure = new BeatMeasure(this.parent, measureId);
		this.beatMeasures.add(measure);
		this.numMeasures++;
	}
	
	public int getNumMeasures () {
		return this.numMeasures;
	}
	
	public int getNumBeats () {
		return this.numBeats;
	}
	
	public void display (Grid c) {
//
//		
//		for (int i = 0; i < this.beatMeasures.size(); i++) {
//				this.beatMeasures.get(i).draw(c, this.beatMeasures);				
//		}
		
		for(int i = this.beatMeasures.size() - 1 ; i > 0; i--){
			if (i > this.beatMeasures.size() - 75) {
				this.beatMeasures.get(i).draw(c, this.beatMeasures);	
			}
		}
		
		
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
	
	public String toString() {
		return "A beat class";
	}
}
