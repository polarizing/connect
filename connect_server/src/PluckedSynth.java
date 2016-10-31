import processing.core.PApplet;

public class PluckedSynth {

	private PApplet parent;
	private ConnectServer server;
	private SampleManager nm;
	private String name;

	public PluckedSynth(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.name = "Plucked Synth";
		this.nm = new SampleManager(this.parent);
		this.nm.setLoadDirectory("audios/plucked-synth", "mp3");
		this.nm.loadNotes("a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3");
	}
	
	public void playSound(int index) {
		this.nm.play(index, 7);
	}
	
//	public void playSound(int index, float lowPass) {
//		this.nm.play(index, 7 + gain);
//	}
	
	public void addEffect (int index, float lowPass, float highpass) {
		this.nm.addEffect(index, lowPass, highpass);
	}
	
	public String toString() {
		return "A PluckedSynth instrument";
	}
}
