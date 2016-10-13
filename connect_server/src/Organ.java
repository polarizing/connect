import processing.core.PApplet;

public class Organ {

	private PApplet parent;
	private ConnectServer server;
	private NoteManager nm;
	private String name;

	public Organ(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.name = "Organ";
		this.nm = new NoteManager(this.parent);
		this.nm.setLoadDirectory("audios/organ", "mp3");
		this.nm.loadNotes("a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3");
	}

	public void playSound(int index) {
		this.nm.play(index, 0);
	}

	public void playSound(int index, float gain) {
		this.nm.play(index, 0 + gain);
	}
	public String toString() {
		return "An organ instrument";
	}
}