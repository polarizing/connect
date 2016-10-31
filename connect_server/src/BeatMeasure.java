import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class BeatMeasure {

	private PApplet parent;
	private ConnectServer server;
	public int measureId;
	public String instrument;
	public String rhythm;
	public String harmony;
	public List<BeatNote> beats;
	
	public BeatMeasure(PApplet p, int measureId) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.measureId = measureId;
		this.beats = new ArrayList<BeatNote>();
	}
	
	
	public void addBeat (String instrument, char note, char pitchInterval) {
		int currBeat = this.beats.size();
		this.beats.add(new BeatNote(this.parent, currBeat, instrument, note, pitchInterval));
	}
	
	public BeatNote getBeatNote (int noteIdx) {
		return this.beats.get(noteIdx);
	}
	
	public void draw (Grid g, ArrayList<BeatMeasure> bm) {
		
//		parent.println( this.server.soundM.getElapsedTime() );
		
		Container gc = g.getContainer();

		Column c = g.getColumn(0);
//		this.server.pushMatrix();
//		this.server.translate(c.center.x, c.center.y, 0);
//		this.server.fill(255);
//		this.server.box(c.width, 20, 20);
//		this.server.popMatrix();
				
		float boxW = g.getColumnSize();
		float boxH = 15;
		float boxZ = 15;
		
		float timeDeltaOffset =  (float) ( this.server.soundM.getElapsedTime() * (boxW * 4));
		float measureOffsetW = (bm.size() - 1 - this.measureId) * (boxW * 4) + timeDeltaOffset;
		this.server.pushMatrix();
		this.server.translate(gc.x2 - measureOffsetW - (boxW * 2), gc.center.y, 200);
		// inside the box
		
			for (BeatNote note : this.beats) {
				
				if (note.hasSound()) {
					this.server.pushMatrix();
					this.server.noStroke();

					int beat = note.beat;
					float boxWOffset;
					float boxHOffset;
					switch(beat) {
						case 0:	boxWOffset = (float) (-boxW * 1.5);
								boxHOffset = (float) (-g.getRowSize() * 1.5);
								this.server.translate(boxWOffset, 0, boxH * (5 - note.getPitchValue()) * 2);
								break;
						case 1:	boxWOffset = (float) (-boxW * 0.5);
								boxHOffset = (float) (-g.getRowSize() * 0.5);
								this.server.translate(boxWOffset, 0, boxH * (5 - note.getPitchValue()) * 2);
								break;
						case 2: boxWOffset = (float) (boxW * 0.5);
								boxHOffset = (float) (g.getRowSize() * 0.5);
								this.server.translate(boxWOffset, 0, boxH * (5 - note.getPitchValue()) * 2);
								break;
						case 3: boxWOffset = (float) (boxW * 1.5);		
								boxHOffset = (float) (g.getRowSize() * 1.5);
								this.server.translate(boxWOffset, 0, boxH * (5 - note.getPitchValue()) * 2);
								break;
					}
					// Front
					// Top **
					 this.parent.beginShape(this.parent.QUADS);

					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(72, 251, 252));
										  break;
						case "harpsichord": this.server.fill(this.server.color(255, 145, 82));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(255, 87, 183));
						  break;
						case "marimba": this.server.fill(this.server.color(219, 255, 69));
						  break;
					}		
					
					 this.parent.vertex(-boxW / 2, -10,  boxZ);
					 this.parent.vertex( boxW / 2, -10,  boxZ);
					 this.parent.vertex( boxW / 2,  10,  boxZ);
					 this.parent.vertex(-boxW / 2,  10,  boxZ);
					 this.parent.endShape();
					 // Back
					 // BOTTOM **
					 this.parent.beginShape(this.parent.QUADS);

					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(72, 251, 252));
										  break;
						case "harpsichord": this.server.fill(this.server.color(255, 145, 82));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(255, 87, 183));
						  break;
						case "marimba": this.server.fill(this.server.color(219, 255, 69));
						  break;
					}							 
					 this.parent.vertex( boxW / 2, -10, -boxZ);
					 this.parent.vertex(-boxW / 2, -10, -boxZ);
					 this.parent.vertex(-boxW / 2,  10, -boxZ);
					 this.parent.vertex( boxW / 2,  10, -boxZ);
					 this.parent.endShape();
					 // Bottom
					 // LEFT **
					 this.parent.beginShape(this.parent.QUADS);
					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(54, 221, 220));
										  break;
						case "harpsichord": this.server.fill(this.server.color(234, 114, 63));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(243, 68, 145));
						  break;
						case "marimba": this.server.fill(this.server.color(170, 224, 49));
						  break;
					}		
					 this.parent.vertex(-boxW / 2,  10,  boxZ);
					 this.parent.vertex( boxW / 2,  10,  boxZ);
					 this.parent.vertex( boxW / 2,  10, -boxZ);
					 this.parent.vertex(-boxW / 2,  10, -boxZ);
					 this.parent.endShape();
					 // Top
					 // RIGHT **
					 this.parent.beginShape(this.parent.QUADS);
					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(95, 255, 255));
										  break;
						case "harpsichord": this.server.fill(this.server.color(255, 177, 102));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(255, 106, 221));
						  break;
						case "marimba": this.server.fill(this.server.color(230, 255, 100));
						  break;
					}
					 
					 this.parent.vertex(-boxW / 2, -10, -boxZ);
					 this.parent.vertex( boxW / 2, -10, -boxZ);
					 this.parent.vertex( boxW / 2, -10,  boxZ);
					 this.parent.vertex(-boxW / 2, -10,  boxZ);
					 this.parent.endShape();
					 // Right
					 // FRONT **
					 this.parent.beginShape(this.parent.QUADS);
					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(74, 255, 217));
										  break;
						case "harpsichord": this.server.fill(this.server.color(255, 87, 90));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(255, 84, 225));
						  break;
						case "marimba": this.server.fill(this.server.color(251, 255, 67));
						  break;
					}
					 						 
					 this.parent.vertex( boxW / 2, -10,  boxZ);
					 this.parent.vertex( boxW / 2, -10, -boxZ);
					 this.parent.vertex( boxW / 2,  10, -boxZ);
					 this.parent.vertex( boxW / 2,  10,  boxZ);
					 this.parent.endShape();
					 // BACK **
					 this.parent.beginShape(this.parent.QUADS);
					 switch (note.instrument) {
						case "bassSynth": this.server.fill(this.server.color(76, 228, 255));
										  break;
						case "harpsichord": this.server.fill(this.server.color(255, 207, 77));
						  break;
						case "pluckedSynth": this.server.fill(this.server.color(231, 66, 105));
						  break;
						case "marimba": this.server.fill(this.server.color(139, 255, 81));
						  break;
					}	
					 this.parent.vertex(-boxW / 2, -10, -boxZ);
					 this.parent.vertex(-boxW / 2, -10,  boxZ);
					 this.parent.vertex(-boxW / 2,  10,  boxZ);
					 this.parent.vertex(-boxW / 2,  10, -boxZ);
					 this.parent.endShape(); 
					
//					this.server.box(boxW, boxH, boxZ);
					this.server.popMatrix();	
				}
				
				
			}
			
		this.server.popMatrix();
	}
	
	public String toString() {
		return "A beat class";
	}
}
