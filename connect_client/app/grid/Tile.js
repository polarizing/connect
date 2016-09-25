define("grid/Tile", ["grid/GridContainer"], function(GridContainer) {

	"use strict";

    function Tile(x1, y1, x2, y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    Tile.prototype.toGridContainer = function () {
        return new GridContainer(this.x1, this.y1, this.x2, this.y2);
    }

    return Tile;

});