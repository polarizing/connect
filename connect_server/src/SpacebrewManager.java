import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class SpacebrewManager {
	private PApplet parent;
	private ConnectServer server;
	
	public SpacebrewManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
	}
	
	
}