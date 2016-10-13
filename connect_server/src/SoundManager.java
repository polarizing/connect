
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class SoundManager {

	private PApplet parent;
	private ConnectServer server;
	
	/**
	 * Managing Time
	 */
	
	double currTime;
	double elapsedTime;
	double prevTime;
	
	/**
	 * Managing Playback
	 * Sounds should only play once every interval.
	 */
	boolean noteOnePlayed = false;
	boolean noteTwoPlayed = false;
	boolean noteThreePlayed = false;
	boolean noteFourPlayed = false;
	
	/**
	 * Instrument Objects
	 */
	public BassSynth bassSynth;
	public HarpChord harpChord;
	public Marimba marimba;
	
	public SoundManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.initTracker();
		this.initInstruments();
	}
	
	public void initTracker() {
		currTime = prevTime = this.parent.millis();
		elapsedTime = 0;
//		noteOnePlayed = noteTwoPlayed = noteThreePlayed = noteFourPlayed = true;
	}
	
	public void initInstruments() {
		bassSynth = new BassSynth(this.parent);
		harpChord = new HarpChord(this.parent);
		marimba = new Marimba(this.parent);
	}
	
	public void play() {
		currTime = this.parent.millis();
		elapsedTime = (currTime - prevTime) / 1000.0;
		
		if (elapsedTime > 0.24 && !noteOnePlayed) {
			bassSynth.playSound("a5");
//			marimba.playSound("a5");
			noteOnePlayed = true;
		}
		
		if (elapsedTime > 0.49 && !noteTwoPlayed) {
//			harpChord.playSound();
			marimba.playSound();
			bassSynth.playSound("d4");
			noteTwoPlayed = true;
		}
		
		if (elapsedTime > 0.74 && !noteThreePlayed) {
			bassSynth.playSound("e4");
			marimba.playSound();
//			harpChord.playSound();

			noteThreePlayed = true;
		}
		
		if (elapsedTime > 0.99 && !noteFourPlayed) {
			harpChord.playSound();
			bassSynth.playSound("g4");
			noteFourPlayed = true;
			
			// Reset time interval.
			prevTime = currTime;
			
			// Reset playback tracker.
			noteOnePlayed = noteTwoPlayed = noteThreePlayed = noteFourPlayed = false;
		}
		

	}
	
	public String toString() {
		return "A sound manager class";
	}
}
