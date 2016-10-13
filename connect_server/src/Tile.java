public class Tile {

	public int id;
	public Grid parentGrid;
	public boolean tethered;
	public float x1;
	public float y1;
	public float x2;
	public float y2; 
	public float width;
	public float height;

	/**
	 * A tile belongs to a grid. It has an id set as it's tile number
	 * relative to its parent grid (left-to-right, top-to-bottom).
	 * There are also methods to get the column (or column index) or 
	 * row (or row index) that the tile belongs to.
	 * @param id
	 * @param partitions
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Tile(int id, Grid parentGrid, float x1, float y1, float x2, float y2) {
		this.id = id;
		this.parentGrid = parentGrid;
		this.tethered = false;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}
	
	public void resize(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = this.x2 - this.x1;
		this.height = this.y2 - this.y1;
	}
	

	public boolean isTethered() {
		return this.tethered;
	}
	
	public int getRowId() {
		return this.id / this.parentGrid.getColumnPartitions();
	}
	
	public int getColumnId() {
		return this.id - (this.getRowId() * this.parentGrid.getColumnPartitions());
	}
	
	/**
	 * Tethers Tile to parent grid.
	 * @return
	 */
	public Tile tether() {
		if (!this.tethered) {
			this.parentGrid.tetherTile(this);
			this.tethered = true;	
		}
		return this;
	}
//	
	/**
	 * Tethers tile to a different grid. Also have to check for edge cases ...
	 * if grid does not have tile id, what happens?
	 * @param g
	 * @return
	 */
	public Tile tether(Grid g) {
		if (!this.tethered) {
			g.tetherTile(this);
			this.parentGrid = g;
			this.tethered = true;	
		}
		
		else if (this.tethered) {
			this.parentGrid.untetherTile(this);
			this.tethered = false;
			g.tetherTile(this);
			this.parentGrid = g;
			this.tethered = true;
		}
		return this;
	}
	
	public int getId() {
		return this.id;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public Container toContainer() {
		return new Container(this.x1, this.y1, this.x2, this.y2);
	}
	
	public String toString() {
		return "A Tile Class with coordinates at x1:" + this.x1 + "y1: " + this.y1 + "x2:" + this.x2 + "y2:" + this.y2;
	}
}
