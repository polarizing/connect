
import processing.core.PApplet;

public class Note {

	private PApplet parent;
	private ConnectServer server;
	
	public Note(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
	}
	
	public String toString() {
		return "A note class";
	}
}
