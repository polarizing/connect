import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

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
	private GridContainer container;
	private GridContainer marginContainer;
//	private PVector center;
	private float topOffset;
	private float rightOffset;
	private float bottomOffset;
	private float leftOffset;
	private int rowPartition;
	private int columnPartition;
	private float columnSize;
	private float rowSize;
	private float[] columnIntervals;
	private float[] rowIntervals;

	/**
	 * 
	 * @param p
	 * @param grid
	 */
	public Grid(PApplet p, GridContainer grid) {
		this.parent = p;
		this.container = grid;
		this.marginContainer = grid;
		this.topOffset = this.rightOffset = this.bottomOffset = this.leftOffset = 0;
		this.rowPartition = this.columnPartition = 0;
		this.init();	
	}
	
	/**
	 * 
	 * @param p
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	
	public Grid (PApplet p, int x1, int y1, int x2, int y2) {
		
		this.parent = p;
//		this.server = (ConnectServer) p;
		this.container = new GridContainer(x1, y1, x2, y2);
		this.marginContainer = new GridContainer(x1, y1, x2, y2);
//		this.center = new PVector(this.width / 2, this.height / 2);
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
			this.columnSize = this.container.width;
			this.columnIntervals = new float[0];
		} else {
			this.columnSize = this.getColumnSize();
			this.columnIntervals = this.setColumnIntervals();
		}
		
		if (this.rowPartition == 0) {
			this.rowSize = this.container.height;
			this.rowIntervals = new float[0];
		} else {
			this.rowSize = this.getRowSize();
			this.rowIntervals = this.setRowIntervals();
		}
		
		// Update margin bounding box.
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
		return (float)(this.container.width - leftRightOffset) / this.columnPartition;
	}
	
	/**
	 * Used as a helper method (for example, in animation).
	 * @param numPartition specify how many partitions to divide by
	 * @return
	 */
	public float getColumnSize (int numPartition) {
		float leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.container.width - leftRightOffset) / numPartition;
	}
	
	/**
	 * Calculates size of each row from
	 * remaining space after subtracting top and bottom offsets and 
	 * dividing by number to partition by.
	 * @return row size in pixels 
	 */
	public float getRowSize () {
		float topBottomOffset = this.topOffset + this.bottomOffset;
		return (float)(this.container.height - topBottomOffset) / this.rowPartition;
	}
	
	/**
	 * Used as a helper method (for example, in animation).
	 * @param numPartition specify how many partitions to divide by
	 * @return
	 */
	public float getRowSize (int numPartition) {
		float leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.container.width - leftRightOffset) / numPartition;
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
				intervals[i] = this.container.x1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.container.x2;
			} else {
				intervals[i] = this.columnSize * i + this.leftOffset + this.container.x1;
			}
		}
		return intervals;
	}
	
	public float[] getColumnIntervals() {
		return this.columnIntervals;
	}
	
	/**
	 * Returns a list of column containers (inside margin).
	 * @return
	 */
	public ArrayList<GridContainer> getColumnContainers() {
		ArrayList<GridContainer> containers = new ArrayList<GridContainer>();
		
		ArrayList<PVector> topPoints = this.getTopPartitionPointsWithMargin();
		ArrayList<PVector> bottomPoints = this.getBottomPartitionPointsWithMargin();
		
		for (int i = 0; i < topPoints.size() - 1; i++) {
			PVector topPoint = topPoints.get(i);
			PVector bottomPoint = bottomPoints.get(i+1);
			GridContainer c = new GridContainer(topPoint, bottomPoint);
			containers.add(c);
		}
		return containers;
	}
	
	/**
	 * Returns a list of column containers stretching the full canvas height.
	 * @return
	 */
	public ArrayList<GridContainer> getFullColumnContainers() {
		ArrayList<GridContainer> containers = new ArrayList<GridContainer>();
		ArrayList<PVector> topPoints = this.getTopOuterPartitionPointsWithMargin();
		ArrayList<PVector> bottomPoints = this.getBottomOuterPartitionPointsWithMargin();
		
		for (int i = 0; i < topPoints.size() - 1; i++) {
			PVector topPoint = topPoints.get(i);
			PVector bottomPoint = bottomPoints.get(i+1);
			GridContainer c = new GridContainer(topPoint, bottomPoint);
			containers.add(c);
		}
		return containers;
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
				intervals[i] = this.container.y1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.container.y2;
			} else {
				intervals[i] = this.rowSize * i + this.topOffset + this.container.y1;
			}
		}
		return intervals;
	}
	
	public float[] getRowIntervals() {
		return this.rowIntervals;
	}

	/**
	 * Sets bounding box for margin. Called whenever there are changes made to offsets.
	 */
	private void setMarginBoundingBox() {
		this.marginContainer.x1 = this.container.x1 + this.leftOffset;
		this.marginContainer.x2 = this.container.x2 - this.rightOffset;
		this.marginContainer.y1 = this.container.y1 + this.topOffset;
		this.marginContainer.y2 = this.container.y2 - this.bottomOffset;
	}
	
	public GridContainer getMarginBox() {
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
		return this.container.height / 2;
	}
	
	public float getMiddleX() {
		return this.container.width / 2;
	}
	
	public PVector getMidpoint() {
		return new PVector(this.getMiddleX(), this.getMiddleY());
	}
	
	
	/**
	 * Get all grid containers at an index position within but not including the margin boundaries.
	 * @return
	 */
	public ArrayList<GridContainer> getContainers() {
		ArrayList<GridContainer> containers = new ArrayList<GridContainer>();
		ArrayList<PVector> partitionPoints = this.getPartitionPointsWithMargin();
		int part = this.getColumnPartitions();
		for (int i = 0; i < partitionPoints.size(); i++) {
			// if next element is the last element in the row or if element is part of last row
			if ( (i + 1) % part == 0 || i >= partitionPoints.size() - part) {
				continue;
			} else {
				containers.add(new GridContainer(partitionPoints.get(i), partitionPoints.get(i + part - 1)));
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
		this.parent.stroke(255, 255, 255);
		for (float interval : this.columnIntervals) {
			this.parent.line(interval, container.y1, interval, container.y2);
		}
		
		for (float interval : this.rowIntervals) {
			this.parent.line(container.x1, interval, container.x2, interval);
		}
		
		// Draw margin offsets
		this.parent.stroke(this.parent.color(255, 0, 0));
		this.parent.line(container.x1, container.y1 + this.topOffset, container.x2, container.y1 + this.topOffset);
		this.parent.line(container.x1 + this.leftOffset, container.y1, container.x1 + this.leftOffset, container.y2);
		this.parent.line(container.x1, container.y2 - this.bottomOffset, container.x2, container.y2 - this.bottomOffset);
		this.parent.line(container.x2 - this.rightOffset, container.y1, container.x2 - this.rightOffset, container.y2);

		this.parent.stroke(0, 0, 0);
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
		this.container = new GridContainer(x1, y1, x2, y2);
		this.init();
		return this;
	}
	
	public Grid setGrid(GridContainer box) {
		this.container = box;
		this.init();
		return this;
	}

	public GridContainer getContainer() {
		return container;
	}

}
