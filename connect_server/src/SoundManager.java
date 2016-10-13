
import java.util.ArrayList;
import java.util.List;

import controlP5.ControlListener;
import processing.core.PApplet;

import ddf.minim.*;


public class SoundManager {

	private PApplet parent;
	private ConnectServer server;
	
	public AudioPlayer bgTrack;
	
	/**
	 * Managing Time (4/4)
	 */
	
	double currTime;
	double elapsedTime;
	double prevTime;
	
	/**
	 * Managing Time (1/1)
	 */

	double bgElapsedTime;
	double bgPrevTime;
	
	
	/**
	 * Has tracker been initialized?
	 */
	boolean trackerInitialized = false;
	
	/**
	 * Managing Playback
	 * Sounds should only play once every interval.
	 */
	boolean noteOnePlayed = false;
	boolean noteTwoPlayed = false;
	boolean noteThreePlayed = false;
	boolean noteFourPlayed = false;
		
	/**
	 * Timing
	 */
	
	public double noteOneTime = 0.250;
	public double noteTwoTime = 0.500;
	public double noteThreeTime = 0.750;
	public double noteFourTime = 1.000;
	
	
	
	/**
	 * Instrument Objects
	 */
	public BassSynth bassSynth;
	public Harpsichord harpsichord;
	public Marimba marimba;
	public PluckedSynth pluckedSynth;
	public Organ organ;
	public SteelDrum steelDrum;
	
	public SoundManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.initInstruments();
	}
	
	public void initTracker() {
		currTime = prevTime = bgPrevTime = this.parent.millis();
		elapsedTime = 0;
		bgElapsedTime = 4.01;
	}
	
	public void initInstruments() {
		bassSynth = new BassSynth(this.parent);
		harpsichord = new Harpsichord(this.parent);
		marimba = new Marimba(this.parent);
		pluckedSynth = new PluckedSynth(this.parent);
		organ = new Organ(this.parent);
		steelDrum = new SteelDrum (this.parent);
		this.bgTrack = this.server.minim.loadFile("audios/drums/simplerBeat.mp3");
	}
	
	public void setNoteOneTime(float time) {
		parent.println("setting time");
		this.noteOneTime = time;
	}
	
	public void play() {
		
		if (!trackerInitialized) {
			this.initTracker();
			trackerInitialized = true;
		}
		
		currTime = this.parent.millis();
		elapsedTime = (currTime - prevTime) / 1000.0;
		bgElapsedTime = (currTime - bgPrevTime) / 1000.0;
		
		
		List<Client> clients = this.server.clientM.getClients();
		
		if (elapsedTime >= noteOneTime && !noteOnePlayed) {
			if (bgElapsedTime > 3.99) {
				bgPrevTime = currTime;
				this.bgTrack.rewind();
				this.bgTrack.setGain(-15);
				this.bgTrack.play();
			}
			
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String harmony = c.getHarmonyPitchInterval();

				// Main Melody
				if (Character.isDigit(rhythm.charAt(0))) {
					int rhythmValue = Character.getNumericValue(rhythm.charAt(0));					
					int harmonyIntervalValue = Character.getNumericValue(harmony.charAt(0));

					if (instrument.equals("marimba")) {
						marimba.playSound(rhythmValue + harmonyIntervalValue);
					}
					else if (instrument.equals("bassSynth")) {
						bassSynth.playSound(rhythmValue + harmonyIntervalValue);
					}
					else if (instrument.equals("harpsichord")) {
						harpsichord.playSound(rhythmValue + harmonyIntervalValue);
					}
					else if (instrument.equals("pluckedSynth")) {
						pluckedSynth.playSound(rhythmValue + harmonyIntervalValue);
					}
					else if (instrument.equals("organ")) {
						organ.playSound(rhythmValue + harmonyIntervalValue);
					}
					else if (instrument.equals("steelDrum")) {
						steelDrum.playSound(rhythmValue + harmonyIntervalValue);
					}
				}
				
			}
					
//			bassSynth.playSound(7);
//			marimba.playSound(10);
//			harpsichord.playSound(0);
//			pluckedSynth.playSound(0);
			noteOnePlayed = true;
		}
		
		if (elapsedTime >= noteTwoTime && !noteTwoPlayed) {
			
			
//			
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String harmony = c.getHarmonyPitchInterval();

				if (Character.isDigit(rhythm.charAt(1))) {
					int rhythmValue = Character.getNumericValue(rhythm.charAt(1)); 
					int harmonyValue = Character.getNumericValue(harmony.charAt(0)); 
					if (instrument.equals("marimba")) {
						marimba.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("bassSynth")) {
						bassSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("harpsichord")) {
						harpsichord.playSound(rhythmValue + harmonyValue);
					}			
					else if (instrument.equals("pluckedSynth")) {
						pluckedSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("organ")) {
						organ.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("steelDrum")) {
						steelDrum.playSound(rhythmValue + harmonyValue);
					}
				}
			}
//			harpsichord.playSound(1);
			noteTwoPlayed = true;
		}
		
		if (elapsedTime >= noteThreeTime && !noteThreePlayed) {
			
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String harmony = c.getHarmonyPitchInterval();

				if (Character.isDigit(rhythm.charAt(2))) {
					int rhythmValue = Character.getNumericValue(rhythm.charAt(2)); 
					int harmonyValue = Character.getNumericValue(harmony.charAt(0)); 
					
					if (instrument.equals("marimba")) {
						marimba.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("bassSynth")) {
						bassSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("harpsichord")) {
						harpsichord.playSound(rhythmValue + harmonyValue);
					}	
					else if (instrument.equals("pluckedSynth")) {
						pluckedSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("organ")) {
						organ.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("steelDrum")) {
						steelDrum.playSound(rhythmValue + harmonyValue);
					}

				}

				
			}

//			marimba.playSound(4);
//			harpsichord.playSound(3);
//			pluckedSynth.playSound(4);
			noteThreePlayed = true;
		}
		
		if (elapsedTime >= noteFourTime && !noteFourPlayed) {
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String harmony = c.getHarmonyPitchInterval();

				if (Character.isDigit(rhythm.charAt(3))) {
					int rhythmValue = Character.getNumericValue(rhythm.charAt(3)); 
					int harmonyValue = Character.getNumericValue(harmony.charAt(0)); 

					if (instrument.equals("marimba")) {
						marimba.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("bassSynth")) {
						bassSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("harpsichord")) {
						harpsichord.playSound(rhythmValue + harmonyValue);
					}	
					else if (instrument.equals("pluckedSynth")) {
						pluckedSynth.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("organ")) {
						organ.playSound(rhythmValue + harmonyValue);
					}
					else if (instrument.equals("steelDrum")) {
						steelDrum.playSound(rhythmValue + harmonyValue);
					}
				}
	
			}
			

//			marimba.playSound(5);
//			harpsichord.playSound(2);
//			bassSynth.playSound(0);
			
			
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
