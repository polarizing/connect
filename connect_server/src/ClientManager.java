import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import ddf.minim.*;

public class ClientManager {

	private PApplet parent;
	private ConnectServer server;

	public List<Client> clients;
	
	public ClientManager(PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.clients = new ArrayList<Client>();
	}
	
	public List<Client> getClients() {
		return this.clients;
	}
	
	public boolean hasActiveClients () {
		return this.clients.size() > 0;
	}
	
	public void addClient (String id) {
		int nextClientId = this.clients.size();
		Client c = new Client(this.parent, nextClientId, id);
		this.clients.add(c);
		c.setStartPing(this.server.millis());
		c.setEndPing(this.server.millis());
		parent.println("New Client Connected: " + id);
		parent.println(this.getClients());
	}
	
	public void setClientPing (String id, float currTime) {
		for (Client c : this.clients) {
			String clientId = c.getClientId();
			if (id.equals(clientId)) {
				c.setEndPing(currTime);
			}
		}
	}
	
	public void setClientRhythm (String id, String rhythm) {
		for (Client c : this.clients) {
			String clientId = c.getClientId();
			if (id.equals(clientId)) {
				if (c.isConnected()) {
					c.setRhythm(rhythm);
				}
			}
		}
	}
	
	public void setClientHarmonyPitchInterval (String id, String interval) {
		for (Client c : this.clients) {
			String clientId = c.getClientId();
			if (id.equals(clientId)) {
				if (c.isConnected()) {
					c.setHarmonyPitchInterval(interval);
				}
			}
		}
	}
	
	public void setClientInstrument (String id, String instrument) {
		for (Client c : this.clients) {
			String clientId = c.getClientId();
			if (id.equals(clientId)) {
				if (c.isConnected()) {
					c.setInstrument(instrument);
				}
			}
		}
	}
	
	public void checkConnections () {
		parent.println("Checking connections...");
		List<Client> clientsToRemove = new ArrayList<Client>();
		this.server.sb.send("checkAlive", true);
		for (Client c : this.clients) {
			c.setStartPing(this.server.millis());
			if ( Math.abs(c.getPing()) > 8000) {
				parent.println(c.getPing());
				c.setDisconnected();
			}
		}
		for (Client c : new ArrayList<Client>(this.clients)) {
			if (!c.isConnected()) {
				parent.println("Client " + c.getClientId() + " has disconnected.");
				this.clients.remove(c);
			}
		}
		parent.println(this.clients);
	}
	
	
	
	public String toString() {
		return "A client manager class";
	}
}
