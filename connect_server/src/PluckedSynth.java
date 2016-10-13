import processing.core.PApplet;

public class PluckedSynth {

	private PApplet parent;
	private ConnectServer server;
	private NoteManager nm;
	private String name;

	public PluckedSynth(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.name = "Plucked Synth";
		this.nm = new NoteManager(this.parent);
		this.nm.setLoadDirectory("audios/plucked-synth", "mp3");
		this.nm.loadNotes("a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3");
	}
	
	public void playSound(int index) {
		this.nm.play(index, 8);
	}
	
	public void playSound(int index, float gain) {
		this.nm.play(index, 8 + gain);
	}
	
	public String toString() {
		return "A PluckedSynth instrument";
	}
}
