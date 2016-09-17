
public class RectBoundingBox {
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	public int width;
	public int height;
	
	public RectBoundingBox(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}
}
