
import java.util.ArrayList;
import java.util.List;

import controlP5.ControlListener;
import processing.core.PApplet;

import ddf.minim.*;


public class SoundManager {

	private PApplet parent;
	private ConnectServer server;
	
	public Sample bgTrack;
	
	public float lowPassVal = 200;
	public float highPassVal = 800;
	
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
	boolean noteReset = false;
	
		
	/**
	 * Timing
	 */
	
	public double noteOneTime = 0.250;
	public double noteTwoTime = 0.500;
	public double noteThreeTime = 0.750;
	public double noteFourTime = 1.000;
	public double noteAverageSpeed = 1;
	
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
	
	public double getElapsedTime() {
		return this.elapsedTime;
	}
	
	public void initInstruments() {
		bassSynth = new BassSynth(this.parent);
		harpsichord = new Harpsichord(this.parent);
		marimba = new Marimba(this.parent);
		pluckedSynth = new PluckedSynth(this.parent);
		organ = new Organ(this.parent);
		steelDrum = new SteelDrum (this.parent);
		this.bgTrack = new Sample(this.parent, this.server.minim.loadSample("audios/drums/simplerBeat.mp3"));

	}
	
	public void playInstrument (String instrument, int pitch) {
		if (instrument.equals("marimba")) {
			marimba.playSound(pitch);
		}
		else if (instrument.equals("bassSynth")) {
			bassSynth.playSound(pitch);
		}
		else if (instrument.equals("harpsichord")) {
//			harpsichord.addEffect(pitch, this.lowPassVal, this.highPassVal);
			harpsichord.playSound(pitch);
		}
		else if (instrument.equals("pluckedSynth")) {
//			pluckedSynth.addEffect(pitch, this.lowPassVal, this.highPassVal);
			pluckedSynth.playSound(pitch);
		}
		else if (instrument.equals("organ")) {
			organ.playSound(pitch);
		}
		else if (instrument.equals("steelDrum")) {
			steelDrum.playSound(pitch);
		}		
	}
	
	public void play() {
		
		
		List<Client> clients = this.server.clientM.getClients();

		if (!trackerInitialized) {
			this.initTracker();
			trackerInitialized = true;
		}
		
		currTime = this.parent.millis();
		elapsedTime = (currTime - prevTime) / 1000.0;
		bgElapsedTime = (currTime - bgPrevTime) / 1000.0;
		
		if (elapsedTime >= (noteAverageSpeed / 4) * 1 - 0.25 && !noteOnePlayed) {
			noteReset = false;

			this.server.sb.send("notePlayed", "0");

			if (bgElapsedTime > 3.99) {
				bgPrevTime = currTime;
//				this.bgTrack.setEffects(this.lowPassVal, this.highPassVal);
				this.bgTrack.play(-12);
//				this.bgTrack.setGain(-12); // originally -10
//				this.bgTrack.play();
			}
			
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String pitchInterval = c.getPitchInterval();

				// if client has no measures
				// in case clients connect on another 
				if (!c.getBeatManager().hasMeasures()) {
					c.getBeatManager().addBeatMeasure();
				}
				// always add a new beat measure every beat ...
				else {
					c.getBeatManager().addBeatMeasure();
				}
				

				// Main Melody
				// Play!
				BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
				measure.addBeat(instrument, rhythm.charAt(0), pitchInterval.charAt(0));
				BeatNote note = measure.getBeatNote(0);
				
				// is not a - (empty note -- non specified by user)
				if (note.hasSound()) {
					int pitchValue = note.getPitchValue();
					this.playInstrument(instrument, pitchValue);
				}
				
			}
			noteOnePlayed = true;
		}
		
		if (elapsedTime >= (noteAverageSpeed / 4) * 2 - 0.25 && !noteTwoPlayed) {
			this.server.sb.send("notePlayed", "1");
			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String pitchInterval = c.getPitchInterval();
				
				if (!c.getBeatManager().hasMeasures()) {
					c.getBeatManager().addBeatMeasure();
					BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
				}
				
				BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
				measure.addBeat(instrument, rhythm.charAt(1), pitchInterval.charAt(0));
				BeatNote note = measure.getBeatNote(1);
				
				if (note.hasSound()) {
					int pitchValue = note.getPitchValue();
					this.playInstrument(instrument, pitchValue);
				}

			noteTwoPlayed = true;
			}
		}
		
		if (elapsedTime >= (noteAverageSpeed / 4) * 3 - 0.25 && !noteThreePlayed) {
			this.server.sb.send("notePlayed", "2");

			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String pitchInterval = c.getPitchInterval();
				
				if (!c.getBeatManager().hasMeasures()) {
					c.getBeatManager().addBeatMeasure();
					BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
				}
				
				BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
				measure.addBeat(instrument, rhythm.charAt(2), pitchInterval.charAt(0));
				BeatNote note = measure.getBeatNote(2);
				
				if (note.hasSound()) {
					int pitchValue = note.getPitchValue();
					this.playInstrument(instrument, pitchValue);
				}

			}
			noteThreePlayed = true;
		}
		
		if (elapsedTime >= (noteAverageSpeed / 4) * 4 - 0.25 && !noteFourPlayed) {
			this.server.sb.send("notePlayed", "3");

			for (Client c : clients) {
				String instrument = c.getInstrument();
				String rhythm = c.getRhythm();
				String pitchInterval = c.getPitchInterval();
				
				if (!c.getBeatManager().hasMeasures()) {
					c.getBeatManager().addBeatMeasure();
					BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
					measure.addBeat(instrument, '-', pitchInterval.charAt(0));
				}
				
				BeatMeasure measure = c.getBeatManager().getCurrentMeasure();
				measure.addBeat(instrument, rhythm.charAt(3), pitchInterval.charAt(0));
				BeatNote note = measure.getBeatNote(3);
				
				if (note.hasSound()) {
					int pitchValue = note.getPitchValue();
					this.playInstrument(instrument, pitchValue);
				}
			
			noteFourPlayed = true;
		}
		}
		
		if (elapsedTime >= (noteAverageSpeed / 4) * 4 && !noteReset) {
			
			for (Client c : clients) {
				// Reset time interval.
				prevTime = currTime;
				
				// Reset playback tracker.
				noteOnePlayed = noteTwoPlayed = noteThreePlayed = noteFourPlayed = false;
	
				noteReset = true;
			}
		}
		
	}
	
	public String toString() {
		return "A sound manager class";
	}
}
