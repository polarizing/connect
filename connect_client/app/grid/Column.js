define("grid/Column", ["grid/Tile"], function(Tile) {

	"use strict";

    function Column(numPartition, x1, y1, x2, y2) {
        this.numPartitions = numPartition;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    Column.prototype.getRowHeight = function () {
        return this.height / this.numPartitions;
    }

    Column.prototype.getTiles = function () {
        var tiles = [];

        var rowHeight = this.getRowHeight();

        for (var i = 0; i < this.numPartitions; i++) {
            var rowHeightOffset = i * rowHeight;
            if (i === 0) {
                tiles.push( new Tile (this.x1, this.y1, this.x2, this.y1 + rowHeight))
            }
            else if (i === this.numPartitions - 1) {
                tiles.push( new Tile (this.x1, this.y1 + rowHeightOffset, this.x2, this.y2))
            }
            else {
                tiles.push( new Tile(this.x1, this.y1 + rowHeightOffset, this.x2, this.y1 + rowHeightOffset + rowHeight) );
            }
        }

        return tiles;
    }

    Column.prototype.toGridContainer = function () {
        return new GridContainer(this.x1, this.y1, this.x2, this.y2);
    }

    return Column;

});