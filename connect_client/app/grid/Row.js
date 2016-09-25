define("grid/Row", function() {

	"use strict";

    function Row(numPartitions, x1, y1, x2, y2) {
        this.numPartitions = numPartitions;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    Row.prototype.getTiles = function () {

    }
    
    Row.prototype.toGridContainer = function () {
        return new GridContainer(this.x1, this.y1, this.x2, this.y2);
    }

    return Row;
    
});