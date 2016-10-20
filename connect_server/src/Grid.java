import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

/**
 * A grid helper for Processing applications. Divide any rectangle
 * into a grid based on margin-offsets and number of row/column partitions. 
 * Draw a grid onto a canvas for visualization. Get a grid's various 
 * intersection points (partition, partition w/ margin, interval).
 * 
 * @author Kevin Li
 *
 */

public class Grid {
	
	private PApplet parent;
	private Container gridContainer;
	private Container marginContainer;
//	private PVector center;
	private float topOffset;
	private float rightOffset;
	private float bottomOffset;
	private float leftOffset;
	private int rowPartition;
	private int columnPartition;
	
	// Below are all set in initialization function.
	private float columnSize;
	private float rowSize;
	private float[] columnIntervals;
	private float[] rowIntervals;
	
	// Tethering
	private List<Column> tetheredColumns = new ArrayList<Column>();
	private List<Row> tetheredRows = new ArrayList<Row>();
	private List<Tile> tetheredTiles = new ArrayList<Tile>();
	
	/**
	 * Constructor function for a new Grid object.
	 * This grid can take in a general Container object to
	 * construct a grid with default margins and partitions set to 0.
	 * This grid does not have columns, rows, or margins, it is no
	 * different than a container for storing the vector points 
	 * of the grid.
	 * @param p processing drawing applet
	 * @param grid GridContainer
	 */
	
	public Grid(PApplet p, Container grid) {
		this.parent = p;
		this.gridContainer = grid;
		this.marginContainer = grid;
		this.topOffset = this.rightOffset = this.bottomOffset = this.leftOffset = 0;
		this.rowPartition = this.columnPartition = 0;
		this.init();	
	}
	
	/**
	 * Constructor function for a new Grid object.
	 * This grid can take a set of parameter vector points <x1, y1, x2, y2>  
	 * to construct a grid with default margins and partitions set to 0.
	 * This grid does not have columns, rows, or margins, it is no
	 * different than a container for storing the vector points 
	 * of the grid.
	 * @param p
	 * @param x1
	 * @param y1
	 * @param width
	 * @param height
	 */
	
	public Grid (PApplet p, float x1, float y1, float width, float height) {
		this.parent = p;
		this.gridContainer = new Container(x1, y1, width, height);
		this.marginContainer = new Container(x1, y1, width, height);
		this.topOffset = this.rightOffset = this.bottomOffset = this.leftOffset = 0;
		this.rowPartition = this.columnPartition = 0;
		this.init();
	}
	
	/**
	 * Main function that is called whenever the grid is updated.
	 * Sets both row/column size and row/column intervals to class instance.
	 * Optimize in the future to only call what is being updated. (i.e. setMarginBoundingBox()
	 * should only be called if offsets are changed.
	 */
	private void init() {
		if (this.columnPartition == 0) {
			this.columnSize = this.gridContainer.width;
			this.columnIntervals = new float[0];
		} else {
			this.columnSize = this.getColumnSize();
			this.columnIntervals = this.setColumnIntervals();
		}
		
		if (this.rowPartition == 0) {
			this.rowSize = this.gridContainer.height;
			this.rowIntervals = new float[0];

		} else {
			this.rowSize = this.getRowSize();
			this.rowIntervals = this.setRowIntervals();
		}
		
		// Resize Columns and Rows
		
		
		// Update margin bounding box.
		this.setMarginBoundingBox();	
	}
	
	
	/**
	 * Called when grid size is changed or setGrid() is called.
	 */
	private void resize() {

			this.columnSize = this.getColumnSize();
			this.columnIntervals = this.setColumnIntervals();
			this.resizeTetheredColumns();
		
			this.rowSize = this.getRowSize();
			this.rowIntervals = this.setRowIntervals();
			this.resizeTetheredRows();
			
			
			// add tile methods here
			this.resizeTetheredTiles();
			
			this.setMarginBoundingBox();
	}
	
	/**
	 * Calculates size of each column from
	 * remaining space after subtracting left and right offsets and 
	 * dividing by number to partition by.
	 * @return column size in pixels 
	 */
	public float getColumnSize () {
		float leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.gridContainer.width - leftRightOffset) / this.columnPartition;
	}
	
	/**
	 * Used as a helper method (for example, in animation).
	 * @param numPartition specify how many partitions to divide by
	 * @return
	 */
	public float getColumnSize (int numPartition) {
		float leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.gridContainer.width - leftRightOffset) / numPartition;
	}
	
	/**
	 * Calculates size of each row from
	 * remaining space after subtracting top and bottom offsets and 
	 * dividing by number to partition by.
	 * @return row size in pixels 
	 */
	public float getRowSize () {
		float topBottomOffset = this.topOffset + this.bottomOffset;
		return (float)(this.gridContainer.height - topBottomOffset) / this.rowPartition;
	}
	
	/**
	 * Used as a helper method (for example, in animation).
	 * @param numPartition specify how many partitions to divide by
	 * @return
	 */
	public float getRowSize (int numPartition) {
		float leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.gridContainer.width - leftRightOffset) / numPartition;
	}
	
	/**
	 * Creates an array of interval points. Intervals are always one more 
	 * than the number of partitions. If there are two partitions, 
	 * there are three intervals. If a sketch is 600px wide and there are
	 * two partitions (that means there are two columns of equal width), then
	 * there are three intervals at 0px, 300px, and 600px width.
	 * @return an array of width (column) intervals in pixels
	 */
	private float[] setColumnIntervals () {
		float[] intervals = new float[this.columnPartition + 1];
		for (int i = 0; i < intervals.length; i++) {
			if (i == 0) {
				intervals[i] = this.gridContainer.x1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.gridContainer.x2;
			} else {
				intervals[i] = this.columnSize * i + this.leftOffset + this.gridContainer.x1;
			}
		}
		return intervals;
	}
	
	public float[] getColumnIntervals() {
		return this.columnIntervals;
	}
	
	/**
	 * Creates an array of interval points. Intervals are always one more 
	 * than the number of partitions. If there are two partitions, 
	 * there are three intervals. If a sketch is 600px tall and there are
	 * two partitions (that means there are two rows of equal height), then
	 * there are three intervals at 0px, 300px, and 600px height.
	 * @return an array of height (row) intervals in pixels
	 */
	private float[] setRowIntervals () {
		float[] intervals = new float[this.rowPartition + 1];
		for (int i = 0; i < intervals.length; i++) {
			if (i == 0) {
				intervals[i] = this.gridContainer.y1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.gridContainer.y2;
			} else {
				intervals[i] = this.rowSize * i + this.topOffset + this.gridContainer.y1;
			}
		}
		return intervals;
	}
	
	public float[] getRowIntervals() {
		return this.rowIntervals;
	}
	
	
	/**
	 * Gets an new dynamically generated array of Columns in relation
	 * to the grid's offsets and partitions.
	 * A column width is calculated by subtracting the grid's left
	 * and right offsets and dividing by the number of column partitions.
	 * A column height is calculated by subtracting the grid's top and
	 * bottom offsets.
	 * @return array of columns
	 */	
	public Column[] getColumns() {
		int numColumns = this.columnPartition;
		float columnWidth = this.columnSize;
		Column[] columns = new Column[numColumns];

		for (int i = 0; i < numColumns; i++) {
			float columnOffset = i * columnWidth;
			if (i == 0) columns[i] = new Column(i, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
			else if (i == numColumns - 1) columns[i] = new Column(i, this, this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
			else columns[i] = new Column(i, this, this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnOffset + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
		}
		return columns;
	}
	
	/**
	 * Gets a specific Column in relation to the grid's offsets and partitions.
	 * @param column
	 * @return
	 */
	public Column getColumn(int column) {
		int numColumns = this.columnPartition;
		float columnWidth = this.columnSize;
		float columnOffset = column * columnWidth;

		if (column == 0) return new Column(column, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
		else if (column == numColumns - 1) return new Column(column, this, this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
		else return new Column(column, this, this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnOffset + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
	}
	
	/**
	 * Removes a column from the column tether store.
	 * @param c
	 */
	public void untetherColumn(Column c) {
		this.tetheredColumns.remove(c);
	}
	
	/**
	 * Adds a column to the column tether store.
	 * @param c
	 */
	public void tetherColumn(Column c) {
		this.tetheredColumns.add(c);
	}
	
	/**
	 * Gets all tethered columns.
	 * @return
	 */
	public List<Column> getTetheredColumns() {
		return this.tetheredColumns;
	}
	
	/**
	 * Resizes all tethered columns. Called when grid's resize() function is called.
	 */
	private void resizeTetheredColumns() {
		for (Column c: this.tetheredColumns) {
			int column = c.getId();
			int numColumns = this.columnPartition;
			float columnWidth = this.columnSize;
			float columnOffset = column * columnWidth;
			
			if (column == 0) c.resize(this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
			else if (column == numColumns - 1) c.resize(this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
			else c.resize(this.gridContainer.x1 + columnOffset + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x1 + columnOffset + columnWidth + this.leftOffset, this.gridContainer.y2 - this.bottomOffset);
		}
	}
	
	
	/**
	 * Gets an new dynamically generated array of Rows in relation
	 * to the grid's offsets and partitions.
	 * A row width is calculated by subtracting the grid's left
	 * and right offsets.
	 * A row height is calculated by subtracting the grid's top and
	 * bottom offsets and dividing by the number of row partitions.
	 * @return array of columns
	 */	
	public Row[] getRows() {
		int numRows = this.rowPartition;
		float rowHeight = this.rowSize;
		Row[] rows = new Row[numRows];

		for (int i = 0; i < numRows; i++) {
			float rowOffset = i * rowHeight;
			if (i == 0) rows[i] = new Row(i, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + this.topOffset + rowHeight);
			else if (i == numRows - 1) rows[i] = new Row(i, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
			else rows[i] = new Row(i, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + rowOffset + rowHeight + this.topOffset );
		}
		return rows;
	}
	
	/**
	 * Gets a specific Column in relation to the grid's offsets and partitions.
	 * @param column
	 * @return
	 */
	public Row getRow(int row) {
		int numRows = this.rowPartition;
		float rowHeight = this.rowSize;
		
			float rowOffset = row * rowHeight;
			if (row == 0) return new Row(row, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + this.topOffset + rowHeight);
			else if (row == numRows - 1) return new Row(row, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
			else return new Row(row, this, this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + rowOffset + rowHeight + this.topOffset );
	}
	
	/**
	 * Removes a row from the row tether store.
	 * @param c
	 */
	public void untetherRow(Row r) {
		this.tetheredRows.remove(r);
	}

	/**
	 * Adds a row to the row tether store.
	 * @param c
	 */
	public void tetherRow(Row r) {
		this.tetheredRows.add(r);
	}
	
	/**
	 * Gets all tethered row.
	 * @return
	 */
	public List<Row> getTetheredRows() {
		return this.tetheredRows;
	}
	
	/**
	 * Resizes all tethered rows. Called when grid's resize() function is called.
	 */
	private void resizeTetheredRows() {
		for (Row r: this.tetheredRows) {
			int row = r.getId();
			int numRows = this.rowPartition;
			float rowHeight = this.rowSize;
				float rowOffset = row * rowHeight;
				if (row == 0) r.resize(this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + this.topOffset + rowHeight);
				else if (row == numRows - 1) r.resize(this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y2 - this.bottomOffset);
				else r.resize(this.gridContainer.x1 + this.leftOffset, this.gridContainer.y1 + rowOffset + this.topOffset, this.gridContainer.x2 - this.rightOffset, this.gridContainer.y1 + rowOffset + rowHeight + this.topOffset );
		}
	}
	
	/**
	 * Gets an new dynamically generated array of Tiles in relation
	 * to the grid's offsets and partitions.
	 * A tile width is calculated by subtracting the grid's left
	 * and right offsets and dividing by the number of column partitions.
	 * A tile height is calculated by subtracting the grid's top and
	 * bottom offsets and dividing by the number of row partitions.
	 * @return array of tiles
	 */	
	public Tile[] getTiles() {
		
		Row[] rows = this.getRows();
		
		Tile[] tiles = new Tile[0];
		for (Row r : rows) {
			Tile[] currentRowTile = r.getTiles();
			tiles = (Tile[])ArrayUtils.addAll(tiles, currentRowTile);
		}
		
		return tiles;
	}
	
	/**
	 * Gets a specific Tile in relation to the grid's offsets and partitions.
	 * @param tile
	 * @return
	 */
	public Tile getTile(int tile) {
		int rowId = tile / this.getColumnPartitions();
		int columnId = tile - (rowId * this.getColumnPartitions());

		Row r = this.getRow(rowId);
		return r.getTile(columnId);
		
	}
	
	
	/**
	 * Removes a tile from the tile tether store.
	 * @param c
	 */
	public void untetherTile(Tile t) {
		this.tetheredTiles.remove(t);
	}

	/**
	 * Adds a tile to the tile tether store.
	 * @param c
	 */
	public void tetherTile(Tile t) {
		this.tetheredTiles.add(t);
	}
	
	/**
	 * Gets all tethered tiles.
	 * @return
	 */
	public List<Tile> getTetheredTiles() {
		return this.tetheredTiles;
	}
	
	/**
	 * Resizes all tethered tiles. Called when grid's resize() function is called.
	 */
	private void resizeTetheredTiles() {
		for (Tile t: this.tetheredTiles) {
			// temp get resized tile
			Tile resizedTile = this.getTile(t.id);
			t.resize(resizedTile.x1, resizedTile.y1, resizedTile.x2, resizedTile.y2);
		}
	}
	
	/**
	 * Returns a list of column containers (inside margin).
	 * @return
	 */
	public ArrayList<Container> getColumnContainers() {
		ArrayList<Container> containers = new ArrayList<Container>();
		
		ArrayList<PVector> topPoints = this.getTopPartitionPointsWithMargin();
		ArrayList<PVector> bottomPoints = this.getBottomPartitionPointsWithMargin();
		
		for (int i = 0; i < topPoints.size() - 1; i++) {
			PVector topPoint = topPoints.get(i);
			PVector bottomPoint = bottomPoints.get(i+1);
			Container c = new Container(topPoint, bottomPoint);
			containers.add(c);
		}
		return containers;
	}
	
	/**
	 * Returns a list of column containers stretching the full canvas height.
	 * @return
	 */
	public ArrayList<Container> getFullColumnContainers() {
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<PVector> topPoints = this.getTopOuterPartitionPointsWithMargin();
		ArrayList<PVector> bottomPoints = this.getBottomOuterPartitionPointsWithMargin();
		
		for (int i = 0; i < topPoints.size() - 1; i++) {
			PVector topPoint = topPoints.get(i);
			PVector bottomPoint = bottomPoints.get(i+1);
			Container c = new Container(topPoint, bottomPoint);
			containers.add(c);
		}
		return containers;
	}

	/**
	 * Sets bounding box for margin. Called whenever there are changes made to offsets.
	 */
	private void setMarginBoundingBox() {
		this.marginContainer.x1 = this.gridContainer.x1 + this.leftOffset;
		this.marginContainer.x2 = this.gridContainer.x2 - this.rightOffset;
		this.marginContainer.y1 = this.gridContainer.y1 + this.topOffset;
		this.marginContainer.y2 = this.gridContainer.y2 - this.bottomOffset;
	}
	
	public Container getMarginBox() {
		return marginContainer;
	}

	/**
	 * Gets all partition points (intersection between a row and column partition) 
	 * in order from left to right, top to bottom. 
	 * @return an array of PVector partition points
	 */
	public ArrayList<PVector> getPartitionPoints() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		for (int y = 0; y < this.rowIntervals.length; y++) {
			if (y != 0 && y != this.rowIntervals.length - 1) {
				for (int x = 0; x < this.columnIntervals.length; x++) {
					if (x != 0 && x != this.columnIntervals.length - 1) {
						vectors.add(new PVector(this.columnIntervals[x], this.rowIntervals[y]));
					}
				}
			}
		}
		return vectors;
	}
	
	/**
	 * Gets all partition points including where a partition intersects with a margin line
	 * (intersection between a row and column partition and their respective margin offset lines)
	 * in order from left to right, top to bottom.
	 * @param includeMargin
	 * @return 
	 */
	public ArrayList<PVector> getPartitionPointsWithMargin() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
	
		int colLength = this.columnIntervals.length;
		int rowLength = this.rowIntervals.length;
		
		// Get top intersection points
		vectors.add(new PVector(marginContainer.x1, marginContainer.y1));
		for (int x = 0; x < colLength; x++) {
			if (x != 0 && x != colLength - 1) {
				vectors.add(new PVector(this.columnIntervals[x], marginContainer.y1));
			}
		}
		vectors.add(new PVector(marginContainer.x2, marginContainer.y1));
		
		// Get middle intersection points
		for (int y = 0; y < rowLength; y++) {
			if (y != 0 && y != rowLength - 1) {
				for (int x = 0; x < colLength; x++) {
					if (x == 0) {
						vectors.add(new PVector(marginContainer.x1, this.rowIntervals[y]));
					}
					else if (x != 0 && x != colLength - 1) {
						vectors.add(new PVector(this.columnIntervals[x], this.rowIntervals[y]));
					}
					else if (x == colLength - 1) {
						vectors.add(new PVector(marginContainer.x2, this.rowIntervals[y]));
					}
				}
			}
		}
		
		// Get bottom intersection points
		vectors.add(new PVector(marginContainer.x1, marginContainer.y2));
		for (int x = 0; x < colLength; x++) {
			if (x != 0 && x != colLength - 1) {
				vectors.add(new PVector(this.columnIntervals[x], marginContainer.y2));
			}
		}
		vectors.add(new PVector(marginContainer.x2, marginContainer.y2));
//		this.parent.println("Vector Array Size: " + vectors.size());
		return vectors;
	}
	
	/**
	 * Gets all the outer partition points at the very top of grid 
	 * (outside margin line, at Y = 0, including points on margin lines)
	 * @return
	 */
	public ArrayList<PVector> getTopOuterPartitionPointsWithMargin() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		vectors.add( new PVector( this.getLeftMarginX() , 0) );
		for (int x = 0; x < this.columnIntervals.length; x++) {
			if (x != 0 && x != this.columnIntervals.length - 1) {
				vectors.add((new PVector(this.columnIntervals[x], 0)));
			}
		}
		vectors.add(new PVector (this.getRightMarginX(), 0));
		return vectors;
	}
	
	/**
	 * Gets all partition points at the top of margin-bounded grid 
	 * (on the margin line, including points on margin lines). 
	 * @return
	 */
	public ArrayList<PVector> getTopPartitionPointsWithMargin() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		vectors.add(this.getTopLeftMargin());
		float topMarginY = this.getTopMarginY();
		for (int x = 0; x < this.columnIntervals.length; x++) {
			if (x != 0 && x != this.columnIntervals.length - 1) {
				vectors.add((new PVector(this.columnIntervals[x], topMarginY)));
			}
		}
		vectors.add(this.getTopRightMargin());
		return vectors;
	}
	
	/**
	 * Gets all the bottom partition points at the bottom of margin-bounded grid
	 * (on the margin line, including points on margin lines)
	 * @return
	 */
	public ArrayList<PVector> getBottomPartitionPointsWithMargin() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		vectors.add(this.getBottomLeftMargin());
		float bottomMarginY = this.getBottomMarginY();
		for (int x = 0; x < this.columnIntervals.length; x++) {
			if (x != 0 && x != this.columnIntervals.length - 1) {
				vectors.add((new PVector(this.columnIntervals[x], bottomMarginY)));
			}
		}
		vectors.add(this.getBottomRightMargin());
		return vectors;
	}

	
	/**
	 * Gets all the bottom outer partition points at the very bottom of grid 
	 * (outside margin line, at Y = sketch height, including points on margin lines)
	 * @return
	 */
	public ArrayList<PVector> getBottomOuterPartitionPointsWithMargin() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		vectors.add( new PVector( this.getLeftMarginX() , this.parent.height) );
		for (int x = 0; x < this.columnIntervals.length; x++) {
			if (x != 0 && x != this.columnIntervals.length - 1) {
				vectors.add((new PVector(this.columnIntervals[x], this.parent.height)));
			}
		}
		vectors.add(new PVector (this.getRightMarginX(), this.parent.height));
		return vectors;
	}
	

	/**
	 * Gets all partition points in the middle (intersection between a row and column partition) 
	 * in order from left to right.
	 * @return an array of PVector partition points in the middle
	 */
	public ArrayList<PVector> getMiddlePartitionPoints() {
		ArrayList<PVector> vectors = new ArrayList<PVector>();
		float middleY = this.getMiddleY();
				for (int x = 0; x < this.columnIntervals.length; x++) {
					if (x != 0 && x != this.columnIntervals.length - 1) {
						vectors.add(new PVector(this.columnIntervals[x], middleY));
					}
				}
		return vectors;
	}
	
	public float getLeftMarginX() {
		return this.marginContainer.x1;
	}
	
	public float getRightMarginX() {
		return this.marginContainer.x2;
	}
	
	public float getTopMarginY() {
		return this.marginContainer.y1;
	}
	
	public float getBottomMarginY() {
		return this.marginContainer.y2;
	}
	
	public PVector getTopLeftMargin() {
		return new PVector(this.marginContainer.x1, this.marginContainer.y1);
	}
	
	public PVector getTopRightMargin() {
		return new PVector(this.marginContainer.x2, this.marginContainer.y1);
	}
	
	public PVector getBottomLeftMargin() {
		return new PVector(this.marginContainer.x1, this.marginContainer.y2);
	}
	
	public PVector getBottomRightMargin() {
		return new PVector(this.marginContainer.x2, this.marginContainer.y2);
	}
	
	public float getMiddleY() {
		return this.gridContainer.height / 2;
	}
	
	public float getMiddleX() {
		return this.gridContainer.width / 2;
	}
	
	public PVector getMidpoint() {
		return new PVector(this.getMiddleX(), this.getMiddleY());
	}
	
	
	/**
	 * Get all grid containers at an index position within but not including the margin boundaries.
	 * @return
	 */
	public ArrayList<Container> getContainers() {
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<PVector> partitionPoints = this.getPartitionPointsWithMargin();
		int part = this.getColumnPartitions();
		for (int i = 0; i < partitionPoints.size(); i++) {
			// if next element is the last element in the row or if element is part of last row
			if ( (i + 1) % part == 0 || i >= partitionPoints.size() - part) {
				continue;
			} else {
				containers.add(new Container(partitionPoints.get(i), partitionPoints.get(i + part - 1)));
			}
		}

		return containers;
	}
	
//	/**
//	 * Get all grid containers at an index position within and including the margin boundaries. 
//	 * @param pos
//	 * @return
//	 */
//	public ArrayList<GridContainer> getContainersWithMargin() {
//		
//	}
	
////	
//	/**
//	 * Gets all interval points (includes outer border)
//	 * @return 
//	 */
//	public PVector[] getIntervalPoints() {
//		
//	}
//	
	
	/**
	 * Gets four margin offset points (where the margin offset lines intersect).
	 * @return 
	 */
	public PVector[] getMarginPoints() {
		PVector[] vectors = new PVector[4];
		vectors[0] = this.getTopLeftMargin();
		vectors[1] = this.getTopRightMargin();
		vectors[2] = this.getBottomLeftMargin();
		vectors[3] = this.getBottomRightMargin();
		return vectors;
	}
	
	/**
	 * Draw the grid.
	 */
	public void draw() {
		// Draw column lines. Lines are always one less than the number
		// of partitions. If there are three partitions, there are two
		// gridlines.
		this.parent.stroke(59, 48, 84);
		this.parent.strokeWeight((float) 0.5);
		for (float interval : this.columnIntervals) {
			this.parent.line(interval, gridContainer.y1, interval, gridContainer.y2);
		}
		
		for (float interval : this.rowIntervals) {
			this.parent.line(gridContainer.x1, interval, gridContainer.x2, interval);
		}
		
		// Draw margin offsets
//		this.parent.stroke(this.parent.color(255, 0, 0));
//		this.parent.line(gridContainer.x1, gridContainer.y1 + this.topOffset, gridContainer.x2, gridContainer.y1 + this.topOffset);
//		this.parent.line(gridContainer.x1 + this.leftOffset, gridContainer.y1, gridContainer.x1 + this.leftOffset, gridContainer.y2);
//		this.parent.line(gridContainer.x1, gridContainer.y2 - this.bottomOffset, gridContainer.x2, gridContainer.y2 - this.bottomOffset);
//		this.parent.line(gridContainer.x2 - this.rightOffset, gridContainer.y1, gridContainer.x2 - this.rightOffset, gridContainer.y2);
//
//		this.parent.stroke(0, 0, 0);
//		this.parent.fill(this.parent.color(209, 217, 213));
//		this.parent.rect(gridContainer.x1, gridContainer.y1, gridContainer.width, gridContainer.height);
	}
	
	/**
	 * Offset the grid by a pixel amount (margin). The array must be in
	 * the order specified (see @param). If a position should not have offset,
	 * specify offset as 0.
	 * @param offsets [top, right, bottom, left] each element represents pixel amount to offset by
	 * @return GridHelper
	 */
	public Grid setOffsets(int[] offsets) {
		this.topOffset = offsets[0];
		this.rightOffset = offsets[1];
		this.bottomOffset = offsets[2];
		this.leftOffset = offsets[3];
		this.init();
		return this;
	}
	
	public float[] getOffsets() {
		return new float[]{this.topOffset, this.rightOffset, this.bottomOffset, this.leftOffset};
	}
	
	public Grid setRightOffset(float offset) {
		this.rightOffset = offset;
		this.init();
		return this;
	}
	
	public float getRightOffset() {
		return this.rightOffset;
	}
	
	public float getTopOffset() {
		return topOffset;
	}

	public Grid setTopOffset(float topOffset) {
		this.topOffset = topOffset;
		this.init();
		return this;
	}

	public float getBottomOffset() {
		return bottomOffset;
	}

	public Grid setBottomOffset(float bottomOffset) {
		this.bottomOffset = bottomOffset;
		this.init();
		return this;
	}

	public float getLeftOffset() {
		return leftOffset;
	}

	public Grid setLeftOffset(float leftOffset) {
		this.leftOffset = leftOffset;
		this.init();
		return this;
	}

	/**
	 * Partition or segment the grid into a number of rows and columns. The array must be
	 * in the order specified (see @param). If a position should not have a partition,
	 * specify partition as 0.
	 * @param partitions [row, column] each element represents number of partitions or segments
	 * @return GridHelper
	 */
	public Grid setPartitions(int[] partitions) {
		parent.println(partitions[1]);
		this.rowPartition = partitions[0];
		this.columnPartition = partitions[1];
		this.init();
		return this;
	}

	public int[] getPartitions() {
		return new int[]{this.rowPartition, this.columnPartition};
	}
	
	public int getColumnPartitions() {
		return this.columnPartition;
	}
	
	public int getRowPartitions() {
		return this.rowPartition;
	}
	
	public Grid setRowPartitions(int partitions) {
		this.rowPartition = partitions;
		this.init();
		return this;
	}
	
	public Grid setColumnPartitions(int partitions) {
		this.columnPartition = partitions;
		this.init();
		return this;
	}
	
	/**
	 * Sets a new bounding box for a grid.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public Grid setGrid(int x1, int y1, int x2, int y2) {
		this.gridContainer = new Container(x1, y1, x2, y2);
		this.resize();
		return this;
	}
	
	public Grid setGrid(Container box) {
		this.gridContainer = box;
		this.init();
		return this;
	}

	public Container getContainer() {
		return gridContainer;
	}

}
