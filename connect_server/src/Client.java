import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * This class controls each client connected to the
 * application.
 * @author Kevin Li
 *
 */
public class Client {
	
	private PApplet parent;	
	private ConnectServer server;
	private String clientId;
	private boolean clientConnected;
	private float startPing;
	private float endPing;
	
	public String rhythm;
	public String refrain;
	public String harmonyPitchInterval;
	public String refrainPitchInterval;
	
	public String instrument;
	
	public Client(PApplet p, String id) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.clientConnected = true;
		this.clientId = id;
		this.startPing = 0;
		this.endPing = 0;
		this.rhythm = "----";
		this.refrain = "----";
		this.instrument = "bassSynth";
		this.harmonyPitchInterval = "4";
		this.refrainPitchInterval = "4";
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
		this.refrain = this.rhythm;
	}
	
	public void setInstrument (String instrument) {
		this.instrument = instrument;
		parent.println("instrument set");
	}
	
	public String getInstrument () {
		return this.instrument;
	}
	
	public void setHarmonyPitchInterval (String harmonyPitchInterval) {
		this.harmonyPitchInterval = harmonyPitchInterval;
	}
	
	public void setRefrainPitchInterval (String refrainPitchInterval) {
		this.refrainPitchInterval = refrainPitchInterval;
	}
	
	public String getRhythm() {
		return this.rhythm;
	}
	
	public String getRefrain() {
		return this.refrain;
	}
	
	public String getHarmonyPitchInterval () {
		return this.harmonyPitchInterval;
	}
	
	public String getRefrainPitchInterval () {
		return this.refrainPitchInterval;
	}
	
	public boolean isConnected () {
		return this.clientConnected;
	}
	
	public float getPing () {
		return endPing - startPing;
	}
	
	public void setStartPing (float currTime) {
		startPing = currTime;
	}
	
	public void setEndPing (float currTime) {
		endPing = currTime;
	}
	
	public void setConnected () {
		this.clientConnected = true;
	}
	
	public void setDisconnected () {
		this.clientConnected = false;
	}
	
	public String toString() {
		return "A client: " + this.clientId;
	}
	
	public void draw () {
		if (!this.isConnected()) {
			
		}
	}
	
}
