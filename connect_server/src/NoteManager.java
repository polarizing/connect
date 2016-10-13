
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;



public class NoteManager {

	private PApplet parent;
	private ConnectServer server;
	public List<Note> notes;
	public String beatNotation;
	
	public NoteManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.notes = new ArrayList<Note>();
		this.beatNotation = "";
	}
	
	public void addNote () {
		notes.add(new Note(this.parent)); // do i need reference?
	}
	
	public void updateNotation (String notation) {
		// if notation is different (some beat changed)
		if (!this.beatNotation.equals(notation)) {
			this.beatNotation = notation;
			
		}
	}
	
	
	public String toString() {
		return "A note manager class";
	}
}
