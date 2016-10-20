import processing.core.PVector;

public class Row {

	public int id;
	public Grid parentGrid;
	public boolean tethered;
	public float x1;
	public float y1;
	public float x2;
	public float y2;
	public float width;
	public float height;
	public PVector center;
	
	/**
	 * A row belongs to a grid. It has an id set as it's row number
	 * relative to the parent grid. It contains a reference to the parent
	 * grid.
	 * @param id
	 * @param partitions
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Row(int id, Grid parentGrid, float x1, float y1, float x2, float y2) {
		this.id = id;
		this.parentGrid = parentGrid;
		this.tethered = false;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = x2 - x1;
		this.height = y2 - y1;
		this.center = new PVector( (this.x1 + this.x2) / 2, (this.y1 + this.y2) / 2);

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
	 * Tethers Row to parent grid.
	 * @return
	 */
	public Row tether() {
		if (!this.tethered) {
			this.parentGrid.tetherRow(this);
			this.tethered = true;	
		}
		return this;
	}
//	
	/**
	 * Tethers Column to a different grid. Also have to check for edge cases ...
	 * if grid does not have column id, what happens?
	 * @param g
	 * @return
	 */
	public Row tether(Grid g) {
		if (!this.tethered) {
			g.tetherRow(this);
			this.parentGrid = g;
			this.tethered = true;	
		}
		
		else if (this.tethered) {
			this.parentGrid.untetherRow(this);
			this.tethered = false;
			g.tetherRow(this);
			this.parentGrid = g;
			this.tethered = true;
		}
		return this;
	}
	
	public float getRowHeight() {
		return this.height / this.parentGrid.getRowPartitions();
	}
	
	public int getNumPartitions() {
		return this.parentGrid.getRowPartitions();
	}
	
	public Tile[] getTiles() {
		int numPartitions = this.parentGrid.getColumnPartitions();
		float columnWidth = this.parentGrid.getColumnSize();

		Tile[] tiles = new Tile[numPartitions];
	
		for (int column = 0; column < numPartitions; column++ ) {
			float columnWidthOffset = column * columnWidth;
			int tileId;
			
			if (this.id == 0) {
				tileId = column;
			} else {
				tileId = (numPartitions * this.id) + column;
			}
			
			if (column == 0) {
				tiles[column] = new Tile (tileId, this.parentGrid, this.x1, this.y1, this.x1 + columnWidth, this.y2);
			}
			else if (column == numPartitions - 1) {
				tiles[column] = new Tile (tileId, this.parentGrid, this.x1 + columnWidthOffset, this.y1, this.x2, this.y2);
			}
			else {
				tiles[column] = new Tile (tileId, this.parentGrid, this.x1 + columnWidthOffset, this.y1, this.x1 + columnWidthOffset + columnWidth, this.y2);
			}
		}
		return tiles;
	}
	
	public Tile getTile(int tile) {
		int numPartitions = this.parentGrid.getColumnPartitions();
		float columnWidth = this.parentGrid.getColumnSize();
		
		float columnWidthOffset = tile * columnWidth;
		int tileId;
		
		if (this.id == 0) {
			tileId = tile;
		} else {
			tileId = (numPartitions * this.id) + tile;
		}
		
		if (tile == 0) {
			return new Tile (tileId, this.parentGrid, this.x1, this.y1, this.x1 + columnWidth, this.y2);
		}
		else if (tile == numPartitions - 1) {
			return new Tile (tileId, this.parentGrid, this.x1 + columnWidthOffset, this.y1, this.x2, this.y2);
		}
		else {
			return new Tile (tileId, this.parentGrid, this.x1 + columnWidthOffset, this.y1, this.x1 + columnWidthOffset + columnWidth, this.y2);
		}
	}
	
	public Container toContainer() {
		return new Container(this.x1, this.y1, this.x2, this.y2);
	}
	
	public String toString() {
		return "A row Class with coordinates at x1:" + this.x1 + "y1: " + this.y1 + "x2:" + this.x2 + "y2:" + this.y2;
	}
}
