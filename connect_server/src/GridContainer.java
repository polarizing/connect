import processing.core.PVector;

public class GridContainer {
	public float x1;
	public float y1;
	public float x2;
	public float y2;
	public float width;
	public float height;
	
	public GridContainer(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}
	
	public GridContainer(PVector p1, PVector p2) {
		this.x1 = p1.x;
		this.y1 = p1.y;
		this.x2 = p2.x;
		this.y2 = p2.y;
		this.width = this.x2 - this.x1;
		this.height = this.y2 - this.y1;
	}
	
	public String toString() {
		return "x1:" + this.x1 + "y1: " + this.y1 + "x2:" + this.x2 + "y2:" + this.y2;
	}
}
