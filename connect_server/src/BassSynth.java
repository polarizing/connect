import processing.core.PApplet;
import ddf.minim.*;

public class BassSynth {

	private PApplet parent;
	private ConnectServer server;

	private AudioPlayer a5, g4, e4, d4, c4, a4, g3, e3, d3, c3, a3;

	public BassSynth(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.loadAllFiles();
	}

	public void loadAllFiles() {
		a5 = this.server.minim.loadFile("audios/bass-synth/a5.mp3");
		g4 = this.server.minim.loadFile("audios/bass-synth/g4.mp3");
		e4 = this.server.minim.loadFile("audios/bass-synth/e4.mp3");
		d4 = this.server.minim.loadFile("audios/bass-synth/d4.mp3");
		c4 = this.server.minim.loadFile("audios/bass-synth/c4.mp3");
		a4 = this.server.minim.loadFile("audios/bass-synth/a4.mp3");
		g3 = this.server.minim.loadFile("audios/bass-synth/g3.mp3");
		e3 = this.server.minim.loadFile("audios/bass-synth/e3.mp3");
		d3 = this.server.minim.loadFile("audios/bass-synth/d3.mp3");
		c3 = this.server.minim.loadFile("audios/bass-synth/c3.mp3");
		a3 = this.server.minim.loadFile("audios/bass-synth/a3.mp3");
	}

	public void playSound(String pitch) {
		switch (pitch) {
			case "a5":
				a5.rewind();
				a5.play();
				break;
			case "g4":
				g4.rewind();
				g4.play();
				break;
			case "e4":
				e4.rewind();
				e4.play();
				break;
			case "d4":
				d4.rewind();
				d4.play();
				break;
			case "c4":
				c4.rewind();
				c4.play();
				break;
			case "a4":
				a4.rewind();
				a4.play();
				break;
			case "g3":
				g3.rewind();
				g3.play();
				break;
			case "e3":
				e3.rewind();
				e3.play();
				break;
			case "d3":
				d3.rewind();
				d3.play();
				break;
			case "c3":
				c3.rewind();
				c3.play();
				break;
			case "a3":
				a3.rewind();
				a3.play();
				break;
		}
	}

	public String toString() {
		return "A bass synth instrument";
	}
}
