import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class SocketManager {
	private PApplet parent;
	private ConnectServer server;
	
	/** Socket Variables **/
	public String serverIp ="localhost";
	public String name="Connect Server";
	public String description ="Connect Spacebrew Socket Server";
	
	
	public SocketManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.init();
	}
	
	public void init() {
		// declare your publishers
		this.server.sb.addPublish("checkAlive", "boolean", "true");
		// declare your subscribers
//		this.server.sb.addSubscribe( "rhythm", "string");
		this.server.sb.addSubscribe("connect", "string");
		// connect to spacebrew
		this.server.sb.connect(this.serverIp, this.name, this.description );
	}
	
	
}