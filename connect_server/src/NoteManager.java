import processing.core.PApplet;
import ddf.minim.*;

public class NoteManager {

	private PApplet parent;
	private ConnectServer server;
	public Note[] notes;
	public String notesDir;
	public String notesSoundExt;
	public String beatNotation;
	
	
	public NoteManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.notes = new Note[0];
		this.beatNotation = "";
		this.notesDir = "";
	}
	
	public void setLoadDirectory (String dir, String ext) {
		this.notesDir = dir;
		this.notesSoundExt = ext;
	}
	
	public void loadNotes (String notesString) {
		String[] notes = notesString.split(", ");
		this.notes = new Note[notes.length];
		
		for (int i = 0; i < notes.length; i++) {
			String note = notes[i];
			parent.println(this.notesDir + "/" + note + "." + notesSoundExt);
			AudioSample noteSound = this.server.minim.loadSample(this.notesDir + "/" + note + "." + notesSoundExt);
			this.notes[i] = new Note(this.parent, note, noteSound);
		}
	}
	
	public void play (int index, float gain) {
		notes[index].play(gain);
	}
	
	public void play (int index) {
		notes[index].play();
	}
//	
//	public void updateNotation (String notation) {
//		// if notation is different (some beat changed)
//		if (!this.beatNotation.equals(notation)) {
//			this.beatNotation = notation;
//			
//		}
//	}
	
	
	public String toString() {
		return "A note manager class";
	}
}
