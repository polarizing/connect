import processing.core.PVector;

public class Column {

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
	 * A column belongs to a grid. It has an id set as it's column number
	 * relative to the parent grid. It contains a reference to the parent
	 * grid.
	 * @param id
	 * @param partitions
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Column(int id, Grid parentGrid, float x1, float y1, float x2, float y2) {
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
	
	public int getId() {
		return this.id;
	}
	
	public boolean isTethered() {
		return this.tethered;
	}
	
	public void resize(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = this.x2 - this.x1;
		this.height = this.y2 - this.y1;
	}
	
	/**
	 * Tethers Column to parent grid.
	 * @return
	 */
	public Column tether() {
		if (!this.tethered) {
			this.parentGrid.tetherColumn(this);
			this.tethered = true;	
		}
		return this;
	}
	
	/**
	 * Tethers Column to a different grid. Also have to check for edge cases ...
	 * if grid does not have column id, what happens?
	 * @param g
	 * @return
	 */
	public Column tether(Grid g) {
		if (!this.tethered) {
			g.tetherColumn(this);
			this.parentGrid = g;
			this.tethered = true;	
		}
		
		else if (this.tethered) {
			this.parentGrid.untetherColumn(this);
			this.tethered = false;
			g.tetherColumn(this);
			this.parentGrid = g;
			this.tethered = true;
		}
		return this;
	}
	
	public float getRowHeight() {
		return this.height / this.parentGrid.getColumnPartitions();
	}
	
	public int getNumPartitions() {
		return this.parentGrid.getColumnPartitions();
	}
	
	public Tile[] getTiles() {
		int numPartitions = this.parentGrid.getRowPartitions();
		float rowHeight = this.parentGrid.getRowSize();

		Tile[] tiles = new Tile[numPartitions];
		
	
		for (int row = 0; row < numPartitions; row++ ) {
			float rowHeightOffset = row * rowHeight;
			int tileId = (numPartitions * row) + this.id;

			if (row == 0) {
				tiles[row] = new Tile (this.id, this.parentGrid, this.x1, this.y1, this.x2, this.y1 + rowHeight);
			}
			else if (row == numPartitions - 1) {
				tiles[row] = new Tile (tileId, this.parentGrid, this.x1, this.y1 + rowHeightOffset, this.x2, this.y2);
			}
			else {
				tiles[row] = new Tile (tileId, this.parentGrid, this.x1, this.y1 + rowHeightOffset, this.x2, this.y1 + rowHeightOffset + rowHeight);
			}
		}
		return tiles;
	}
	
	public Container toContainer() {
		return new Container(this.x1, this.y1, this.x2, this.y2);
	}
	
	public String toString() {
		return "A Column Class with coordinates at x1:" + this.x1 + "y1: " + this.y1 + "x2:" + this.x2 + "y2:" + this.y2;
	}
}
