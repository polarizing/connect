import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class BeatNote {

	private PApplet parent;
	private ConnectServer server;
	public String instrument;
	public int beat;
	public char note;
	public char pitchInterval;
	
	public BeatNote(PApplet p, int beat, String instrument, char note, char pitchInterval) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.beat = beat;
		this.instrument = instrument;
		this.note = note;
		this.pitchInterval = pitchInterval;
	}
	
	public boolean hasSound () {
		return Character.isDigit(note);
	}
	
	public int getPitchValue () {
		int rhythmValue = Character.getNumericValue(note);					
		int octaveValue = Character.getNumericValue(pitchInterval);
		return rhythmValue + octaveValue;
	}
	
	public String getInstrument () {
		return this.instrument;
	}
	
	public char getNote() {
		return this.note;
	}
	
	public void draw () {
		
	}
	
	public String toString() {
		return " Instrument: " + this.instrument + " Note: " + Character.toString(this.note) + " Octave: " + this.pitchInterval;
	}
}
