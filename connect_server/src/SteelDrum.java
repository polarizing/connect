import processing.core.PApplet;

public class SteelDrum {

	private PApplet parent;
	private ConnectServer server;
	private NoteManager nm;
	private String name;

	public SteelDrum(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.name = "SteelDrum";
		this.nm = new NoteManager(this.parent);
		this.nm.setLoadDirectory("audios/steel-drums", "mp3");
		this.nm.loadNotes("a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3");
	}

	public void playSound(int index) {
		this.nm.play(index, 0);
	}

	public void playSound(int index, float gain) {
		this.nm.play(index, 0 + gain);
	}
	
	public String toString() {
		return "A steel drum instrument";
	}
}
