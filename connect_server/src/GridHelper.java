import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;
import java.util.ArrayList;

public class GridHelper {
	
	private PApplet parent;
	private RectBoundingBox box;
	private PVector center;
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

	public GridHelper (PApplet p, int x1, int y1, int x2, int y2) {
		
		this.parent = p;
		this.box = new RectBoundingBox(x1, y1, x2, y2);
//		this.center = new PVector(this.width / 2, this.height / 2);
		this.topOffset = this.rightOffset = this.bottomOffset = this.leftOffset = 0;
		this.rowPartition = this.columnPartition = 0;
		this.init();
	}
	
	private void init() {
		if (this.columnPartition == 0) {
			this.columnSize = this.box.width;
			this.columnIntervals = new float[0];
		} else {
			this.columnSize = this.setColumnSize();
			// Intervals are always one more than the number
			// of partitions. If there are two partitions, there are three intervals.
			this.columnIntervals = this.setColumnIntervals();
		}
		
		if (this.rowPartition == 0) {
			this.rowSize = this.box.height;
			this.rowIntervals = new float[0];
		} else {
			this.rowSize = this.setRowSize();
			this.rowIntervals = this.setRowIntervals();
		}
	}
	
	private float setColumnSize () {
		int leftRightOffset = this.leftOffset + this.rightOffset;
		return (float)(this.box.width - leftRightOffset) / this.columnPartition;
	}
	
	private float setRowSize () {
		int topBottomOffset = this.topOffset + this.bottomOffset;
		return (float)(this.box.height - topBottomOffset) / this.rowPartition;
	}
	
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
	 * Gets all partition points (intersection between a row and column partition) 
	 * in order from left to right, top to bottom. 
	 * @return 
	 */
	public ArrayList<PVector> getPartitionPoints() {
		parent.println(this.rowIntervals);
		ArrayList vectors = new ArrayList<PVector>();
		for (int x = 0; x < this.columnIntervals.length; x++) {
			if (x != 0 && x != this.columnIntervals.length - 1) {
				for (int y = 0; y < this.rowIntervals.length; y++) {
					if (y != 0 && y != this.rowIntervals.length - 1) {
						parent.println(x);
						vectors.add(new PVector(this.columnIntervals[x], this.rowIntervals[y]));
					}
				}
			}
		}
		return vectors;
	}
	
//	/**
//	 * Gets all partition points including where a partition intersects with a margin line
//	 * (intersection between a row and column partition and their respective margin offset lines).
//	 * @param includeMargin
//	 * @return 
//	 */
//	public ArrayList<PVector> getPartitionPoints(boolean withMargin) {
//		ArrayList vectors = new ArrayList<PVector>();
//		for (int x = 0; x < this.columnIntervals.length; x++) {
//			if (x != 0 && x != this.columnIntervals.length - 1) {
//				for (int y = 0; y < this.rowIntervals.length; y++) {
//					if (y != 0 && y != this.rowIntervals.length - 1) {
//						parent.println(x);
//						vectors.add(new PVector(this.columnIntervals[x], this.rowIntervals[y]));
//					}
//				}
//			}
//		}
//		return vectors;
//	}
//	
//	/**
//	 * Gets all interval points (includes outer border)
//	 * @return 
//	 */
//	public PVector[] getIntervalPoints() {
//		
//	}
//	
//	/**
//	 * Gets four margin offset points (where the margin offset lines intersect).
//	 * @return 
//	 */
//	public PVector[] getMarginPoints() {
//		
//	}
//	
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
		this.parent.ellipse(300, 300, 50, 50);
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
	
	
}
