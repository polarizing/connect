
import ddf.minim.*;
import ddf.minim.effects.LowPassSP;
import ddf.minim.effects.HighPassSP;
import processing.core.PApplet;

public class Sample {

	private PApplet parent;
	private ConnectServer server;
	
	public String note;
	public AudioSample sound;
	public LowPassSP lowpass; 
	public HighPassSP highpass;

	public Sample(PApplet p, AudioSample sound) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.sound = sound;
		this.lowpass = new LowPassSP(200, 44100);
		this.highpass = new HighPassSP(800, 44100);
	}
	
	public Sample(PApplet p, String note, AudioSample sound) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.note = note;
		this.sound = sound;
		this.lowpass = new LowPassSP(200, 44100); 
		this.highpass = new HighPassSP(800, 44100);
	}
	
	public void setEffects(float lp, float hp) {
		this.sound.removeEffect(lowpass);
		this.lowpass.setFreq(lp);		
		this.sound.addEffect(lowpass);
		this.sound.removeEffect(highpass);
		this.highpass.setFreq(hp);		
		this.sound.addEffect(highpass);
	}
	
	public void play(float gain) {
		this.sound.setGain(gain);		
		this.sound.trigger();
	}
	public void play() {
		this.sound.trigger();
	}
	public String toString() {
		return "A sample class";
	}
}
