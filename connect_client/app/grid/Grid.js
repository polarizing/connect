define(["grid/GridContainer"], function(GridContainer) {

	"use strict";

    function Grid(sketch, x1, y1, x2, y2) {
        this.parent = sketch;
        this.container = new GridContainer(x1, y1, x2, y2);
        this.marginContainer = new GridContainer(x1, y1, x2, y2);
        this.offsets = {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
        };
        this.partitions = {
            row: 0,
            column: 0
        };
        this.columnSize = 0;
        this.rowSize = 0;
        this.columnIntervals = [];
        this.rowIntervals = [];
        this.init();
    }

    Grid.prototype.init = function(message) {
        console.log(message);
        if (this.partitions.column === 0) {
            this.columnSize = this.container.width;
            this.columnIntervals = [];
        } else {
            this.columnSize = this.getColumnSize();

            this.columnIntervals = this.setColumnIntervals();
        }

        if (this.partitions.row === 0) {
            this.rowSize = this.container.height;
            this.rowIntervals = [];
        } else {
            this.rowSize = this.getRowSize();
            this.rowIntervals = this.setRowIntervals();
        }

        this.setMarginBoundingBox();
    }

    Grid.prototype.getColumnSize = function() {
        var leftRightOffset = this.offsets.left + this.offsets.right;
        return (this.container.width - leftRightOffset) / this.partitions.column;
    }

    Grid.prototype.setColumnIntervals = function() {
        var intervals = [];
        var numIntervals = this.partitions.column + 1;
        for (var i = 0; i < numIntervals; i++) {
            if (i === 0) intervals[i] = this.container.x1;
            else if (i === numIntervals - 1) intervals[i] = this.container.x2;
            else intervals[i] = this.columnSize * i + this.offsets.left + this.container.x1;
        }
        return intervals;
    }

    Grid.prototype.getRowSize = function() {
        var topBottomOffset = this.offsets.top + this.offsets.bottom;
        return (this.container.height - topBottomOffset) / this.partitions.row;
    }

    Grid.prototype.setRowIntervals = function() {
        var intervals = [];
        var numIntervals = this.partitions.row + 1;
        for (var i = 0; i < numIntervals; i++) {
            if (i === 0) intervals[i] = this.container.y1;
            else if (i === numIntervals - 1) intervals[i] = this.container.y2;
            else intervals[i] = this.rowSize * i + this.offsets.top + this.container.y1;
        }
        return intervals;
    }

    Grid.prototype.getRowIntervals = function() {
        return this.rowIntervals;
    }

    /**
     * Offset the grid by a pixel amount (margin). The array must be in
     * the order specified (see @param). If a position should not have offset,
     * specify offset as 0.
     * @param offsets [top, right, bottom, left] each element represents pixel amount to offset by
     * @return GridHelper
     */

    Grid.prototype.setOffsets = function(offsets) {
        this.offsets.top = offsets.top;
        this.offsets.right = offsets.right;
        this.offsets.bottom = offsets.bottom;
        this.offsets.left = offsets.left;
        this.init('Called setOffsets()');
        return this;
    }

    Grid.prototype.setPartitions = function(partitions) {
        this.partitions.row = partitions.row;
        this.partitions.column = partitions.column;
        this.init('Called setPartitions()');
        return this;
    }

    /**
     * Sets bounding box for margin. Called whenever there are changes made to offsets.
     */
    Grid.prototype.setMarginBoundingBox = function() {
        this.marginContainer.x1 = this.container.x1 + this.offsets.left;
        this.marginContainer.x2 = this.container.x2 - this.offsets.right;
        this.marginContainer.y1 = this.container.y1 + this.offsets.top;
        this.marginContainer.y2 = this.container.y2 - this.offsets.bottom;
    }


    Grid.prototype.getMarginBox = function() {
        return this.marginContainer;
    }

    Grid.prototype.draw = function() {
        // this.parent.rect(100,100,30,30);
        this.parent.stroke(0);
        for (var i = 0; i < this.columnIntervals.length; i++) {
            this.parent.line(this.columnIntervals[i], this.container.y1, this.columnIntervals[i], this.container.y2);
        };
        for (var i = 0; i < this.rowIntervals.length; i++) {
            this.parent.line(this.container.x1, this.rowIntervals[i], this.container.x2, this.rowIntervals[i]);
        };

        // Draw margin offsets

        this.parent.stroke(255, 0, 0);
        this.parent.line(this.container.x1, this.container.y1 + this.offsets.top, this.container.x2, this.container.y1 + this.offsets.top);
        this.parent.line(this.container.x1 + this.offsets.left, this.container.y1, this.container.x1 + this.offsets.left, this.container.y2);
        this.parent.line(this.container.x1, this.container.y2 - this.offsets.bottom, this.container.x2, this.container.y2 - this.offsets.bottom);
        this.parent.line(this.container.x2 - this.offsets.right, this.container.y1, this.container.x2 - this.offsets.right, this.container.y2);

        this.parent.stroke(0, 0, 0);

    }

    return Grid;
});