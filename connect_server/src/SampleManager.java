import processing.core.PApplet;
import ddf.minim.*;

public class SampleManager {

	private PApplet parent;
	private ConnectServer server;
	public Sample[] samples;
	public String notesDir;
	public String notesSoundExt;
	public String beatNotation;
	
	public SampleManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.samples = new Sample[0];
		this.beatNotation = "";
		this.notesDir = "";
	}
	
	public void setLoadDirectory (String dir, String ext) {
		this.notesDir = dir;
		this.notesSoundExt = ext;
	}
	
	public void loadNotes (String notesString) {
		String[] notes = notesString.split(", ");
		this.samples = new Sample[notes.length];
		
		for (int i = 0; i < notes.length; i++) {
			String note = notes[i];
			parent.println(this.notesDir + "/" + note + "." + notesSoundExt);
			AudioSample noteSound = this.server.minim.loadSample(this.notesDir + "/" + note + "." + notesSoundExt);
			this.samples[i] = new Sample(this.parent, note, noteSound);
		}
	}
	
	public void play (int index, float gain) {
		samples[index].play(gain);
	}
	
	public void play (int index) {
		samples[index].play();
	}
	
	public String toString() {
		return "A sample manager class";
	}
}
