import processing.core.PApplet;

public class Harpsichord {

	private PApplet parent;
	private ConnectServer server;
	private SampleManager nm;
	private String name;

	public Harpsichord(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.name = "Harpsichord";
		this.nm = new SampleManager(this.parent);
		this.nm.setLoadDirectory("audios/harpsichord", "mp3");
		this.nm.loadNotes("a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3");
	}
	
	public void playSound(int index) {
		this.nm.play(index, -12);
	}
	
	public void playSound(int index, float gain) {
		this.nm.play(index, -12 + gain);
	}
	
	public String toString() {
		return "A harpsichord instrument";
	}
}
