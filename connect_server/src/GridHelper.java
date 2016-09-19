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
public class GridHelper {
	
	private PApplet parent;
	private RectBoundingBox box;
	private RectBoundingBox marginBox;
//	private PVector center;
	private int topOffset;
	private int rightOffset;
	private int bottomOffset;
	private int leftOffset;
	private int rowPartition;
	private int columnPartition;
	private float columnSize;
	private float rowSize;
	private float[] columnIntervals;
	private float[] rowIntervals;

	/**
	 * 
	 * @param p
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public GridHelper (PApplet p, int x1, int y1, int x2, int y2) {
		
		this.parent = p;
		this.box = new RectBoundingBox(x1, y1, x2, y2);
		this.marginBox = new RectBoundingBox(x1, y1, x2, y2);
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
			this.columnSize = this.box.width;
			this.columnIntervals = new float[0];
		} else {
			this.columnSize = this.setColumnSize();
			this.columnIntervals = this.setColumnIntervals();
		}
		
		if (this.rowPartition == 0) {
			this.rowSize = this.box.height;
			this.rowIntervals = new float[0];
		} else {
			this.rowSize = this.setRowSize();
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
	private float setColumnSize () {
		int leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.box.width - leftRightOffset) / this.columnPartition;
	}
	
	/**
	 * Calculates size of each row from
	 * remaining space after subtracting top and bottom offsets and 
	 * dividing by number to partition by.
	 * @return row size in pixels 
	 */
	private float setRowSize () {
		int topBottomOffset = this.topOffset + this.bottomOffset;
		return (float)(this.box.height - topBottomOffset) / this.rowPartition;
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
				intervals[i] = this.box.x1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.box.x2;
			} else {
				intervals[i] = this.columnSize * i + this.leftOffset + this.box.x1;
			}
		}
		return intervals;
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
				intervals[i] = this.box.y1;
			} else if (i == intervals.length - 1) {
				intervals[i] = this.box.y2;
			} else {
				intervals[i] = this.rowSize * i + this.topOffset + this.box.y1;
			}
		}
		return intervals;
	}
	
	/**
	 * Sets bounding box for margin. Called whenever there are changes made to offsets.
	 */
	private void setMarginBoundingBox() {
		this.marginBox.x1 = this.box.x1 + this.leftOffset;
		this.marginBox.x2 = this.box.x2 - this.rightOffset;
		this.marginBox.y1 = this.box.y1 + this.topOffset;
		this.marginBox.y2 = this.box.y2 - this.bottomOffset;
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
		vectors.add(new PVector(marginBox.x1, marginBox.y1));
		for (int x = 0; x < colLength; x++) {
			if (x != 0 && x != colLength - 1) {
				vectors.add(new PVector(this.columnIntervals[x], marginBox.y1));
			}
		}
		vectors.add(new PVector(marginBox.x2, marginBox.y1));
		
		// Get middle intersection points
		for (int y = 0; y < rowLength; y++) {
			if (y != 0 && y != rowLength - 1) {
				for (int x = 0; x < colLength; x++) {
					if (x == 0) {
						vectors.add(new PVector(marginBox.x1, this.rowIntervals[y]));
					}
					else if (x != 0 && x != colLength - 1) {
						vectors.add(new PVector(this.columnIntervals[x], this.rowIntervals[y]));
					}
					else if (x == colLength - 1) {
						vectors.add(new PVector(marginBox.x2, this.rowIntervals[y]));
					}
				}
			}
		}
		
		// Get bottom intersection points
		vectors.add(new PVector(marginBox.x1, marginBox.y2));
		for (int x = 0; x < colLength; x++) {
			if (x != 0 && x != colLength - 1) {
				vectors.add(new PVector(this.columnIntervals[x], marginBox.y2));
			}
		}
		vectors.add(new PVector(marginBox.x2, marginBox.y2));
//		this.parent.println("Vector Array Size: " + vectors.size());
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
		return this.marginBox.x1;
	}
	
	public float getRightMarginX() {
		return this.marginBox.x2;
	}
	
	public float getTopMarginY() {
		return this.marginBox.y1;
	}
	
	public float getBottomMarginY() {
		return this.marginBox.y2;
	}
	
	public PVector getTopLeftMargin() {
		return new PVector(this.marginBox.x1, this.marginBox.y1);
	}
	
	public PVector getTopRightMargin() {
		return new PVector(this.marginBox.x2, this.marginBox.y1);
	}
	
	public PVector getBottomLeftMargin() {
		return new PVector(this.marginBox.x1, this.marginBox.y2);
	}
	
	public PVector getBottomRightMargin() {
		return new PVector(this.marginBox.x2, this.marginBox.y2);
	}
	
	public float getMiddleY() {
		return this.box.height / 2;
	}
	
	public float getMiddleX() {
		return this.box.width / 2;
	}
	
	public PVector getMidpoint() {
		return new PVector(this.getMiddleX(), this.getMiddleX());
	}
	
	
	
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
			this.parent.line(interval, box.y1, interval, box.y2);
		}
		
		for (float interval : this.rowIntervals) {
			this.parent.line(box.x1, interval, box.x2, interval);
		}
		
		// Draw margin offsets
		this.parent.stroke(this.parent.color(255, 0, 0));
		this.parent.line(box.x1, box.y1 + this.topOffset, box.x2, box.y1 + this.topOffset);
		this.parent.line(box.x1 + this.leftOffset, box.y1, box.x1 + this.leftOffset, box.y2);
		this.parent.line(box.x1, box.y2 - this.bottomOffset, box.x2, box.y2 - this.bottomOffset);
		this.parent.line(box.x2 - this.rightOffset, box.y1, box.x2 - this.rightOffset, box.y2);

		this.parent.stroke(0, 0, 0);
	}
	
	/**
	 * Offset the grid by a pixel amount (margin). The array must be in
	 * the order specified (see @param). If a position should not have offset,
	 * specify offset as 0.
	 * @param offsets [top, right, bottom, left] each element represents pixel amount to offset by
	 * @return GridHelper
	 */
	public GridHelper setOffsets(int[] offsets) {
		this.topOffset = offsets[0];
		this.rightOffset = offsets[1];
		this.bottomOffset = offsets[2];
		this.leftOffset = offsets[3];
		this.init();
		return this;
	}
	
	public int[] getOffsets() {
		return new int[]{this.topOffset, this.rightOffset, this.bottomOffset, this.leftOffset};
	}
	
	/**
	 * Partition or segment the grid into a number of rows and columns. The array must be
	 * in the order specified (see @param). If a position should not have a partition,
	 * specify partition as 0.
	 * @param partitions [row, column] each element represents number of partitions or segments
	 * @return GridHelper
	 */
	public GridHelper setPartitions(int[] partitions) {
		this.rowPartition = partitions[0];
		this.columnPartition = partitions[1];
		this.init();
		return this;
	}

	public int[] getPartitions() {
		return new int[]{this.rowPartition, this.columnPartition};
	}
	
	/**
	 * Sets a new bounding box.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public GridHelper setRectBoundingBox(int x1, int y1, int x2, int y2) {
		this.box = new RectBoundingBox(x1, y1, x2, y2);
		this.init();
		return this;
	}
}
