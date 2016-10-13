
import ddf.minim.*;
import processing.core.PApplet;

public class Note {

	private PApplet parent;
	private ConnectServer server;
	
	public String note;
	public AudioSample sound;
	
	public Note(PApplet p, String note, AudioSample sound) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.note = note;
		this.sound = sound;
	}
	
	public void play(float gain) {
//		this.sound.rewind();
		this.sound.setGain(gain);
//		this.sound.play();
		
		this.sound.trigger();
	}
	public void play() {
//		this.sound.mute();
//		this.sound.rewind();
//		this.sound.play();
//		this.sound.loop();

		this.sound.trigger();
	}
	public String toString() {
		return "A note class";
	}
}
