
public class RectBoundingBox {
	public float x1;
	public float y1;
	public float x2;
	public float y2;
	public float width;
	public float height;
	
	public RectBoundingBox(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}
}
