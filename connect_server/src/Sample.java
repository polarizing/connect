
import ddf.minim.*;
import processing.core.PApplet;

public class Sample {

	private PApplet parent;
	private ConnectServer server;
	
	public String note;
	public AudioSample sound;
	
	public Sample(PApplet p, String note, AudioSample sound) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.note = note;
		this.sound = sound;
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
