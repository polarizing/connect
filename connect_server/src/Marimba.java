import processing.core.PApplet;
import ddf.minim.*;

public class Marimba {

	private PApplet parent;
	private ConnectServer server;
	
	private AudioPlayer a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3;
	
	public Marimba(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.loadAllFiles();
	}
	
	public void loadAllFiles() {
		a5 = this.server.minim.loadFile("audios/marimba/a5.mp3");
		g4 = this.server.minim.loadFile("audios/marimba/g4.mp3");
		e4 = this.server.minim.loadFile("audios/marimba/e4.mp3");
		d4 = this.server.minim.loadFile("audios/marimba/d4.mp3");
		c4 = this.server.minim.loadFile("audios/marimba/c4.mp3");
		a4 = this.server.minim.loadFile("audios/marimba/a4.mp3");
		g3 = this.server.minim.loadFile("audios/marimba/g3.mp3");
		e3 = this.server.minim.loadFile("audios/marimba/e3.mp3");
		d3 = this.server.minim.loadFile("audios/marimba/d3.mp3");
		c3 = this.server.minim.loadFile("audios/marimba/c3.mp3");
		a3 = this.server.minim.loadFile("audios/marimba/a3.mp3");
	}
	
	public void playSound() {
		a5.rewind();
		a5.play();
	}
	
	public String toString() {
		return "A bass synth instrument";
	}
}
