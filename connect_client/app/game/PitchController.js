define(['config/Colors', 'config/Config', 'game/PitchTile'], function(Colors, Config, PitchTile) {

    var tileMargin = Config.pitchTileMargin;

    function PitchController(controllerId, grid) {
        this.controllerId = controllerId;
        this.grid = grid;
        this.column = this.grid.getColumn(0);
        this.x1 = this.column.x1;
        this.y1 = this.column.y1 - 45;
        this.x2 = this.column.x2;
        this.y2 = this.column.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
        this.tiles = this.createPitchTiles(this.column)
        this.pitchRange = 4;
        this.pitchY = (this.column.y1 + this.column.y2) / 2;
    }

    PitchController.prototype.createPitchTiles = function(column) {
        return column.getTiles().map(function(tile, tileIdx) {
            return new PitchTile(tileIdx, tile.x1, tile.y1, tile.x2, tile.y2, false);
        })
    }

    PitchController.prototype.resizePitchTiles = function(column) {
        var self = this;
        column.getTiles().map(function(tile, tileIdx) {
            self.tiles[tileIdx].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
        })
    }

    PitchController.prototype.resizePitchY = function() {
        this.pitchY = (this.column.y1 + this.column.y2) / 2;
    }

    PitchController.prototype.resize = function(grid) {
        // resize container
        this.column = this.grid.getColumn(0);
        this.x1 = this.column.x1;
        this.y1 = this.column.y1 - 45;
        this.x2 = this.column.x2;
        this.y2 = this.column.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;

        // resize tiles
        this.resizePitchTiles(this.column)
        this.resizePitchY();
    }

    PitchController.prototype.getTiles = function() {
        return this.tiles;
    }

    PitchController.prototype.getPitchContainer = function() {
        return this.column;
    }

    PitchController.prototype.updatePitchY = function(yPos) {
        this.pitchY = yPos;
    }

    PitchController.prototype.drawContainerOutline = function(sketch) {
        sketch.fill('rgb(39, 39, 39)');
        sketch.stroke('rgb(0, 0, 0)')
        sketch.rect(this.x1, this.y1 + 30, this.width, this.height - 30, 0, 0, 3, 3);
        sketch.fill('rgb(30, 30, 30)');
        sketch.rect(this.x1, this.y1, this.width, 30, 3, 3, 0, 0);
        sketch.fill('rgb(201, 133, 72)')
        sketch.textAlign(sketch.LEFT, sketch.CENTER);
        sketch.textFont(sketch.sfRegular);
        sketch.textSize(14);
        if (this.controllerId == 1) {
            sketch.text('Harmony', (this.x1 + 10), (this.y1 + this.y1 + 30) / 2);
        } else if (this.controllerId == 2) {
            sketch.text('Refrain', (this.x1 + 10), (this.y1 + this.y1 + 30) / 2);
        }
    }

    PitchController.prototype.drawPitchTiles = function(sketch) {
        this.tiles.forEach(function(tile) {
            tile.draw(sketch);
        })
    }

    PitchController.prototype.drawPitchLine = function(sketch) {
        sketch.strokeWeight(2.0);
        sketch.fill('rgb(201, 133, 72)');
        sketch.line(this.x1 + 4, this.pitchY, this.x2 - 4, this.pitchY);
        sketch.strokeWeight(1.0);
    }

    PitchController.prototype.draw = function(sketch, activeColumn) {
        // Draw Outline (including top bar / background / border)
        this.drawContainerOutline(sketch);

        // Draw the Tiles
        this.drawPitchTiles(sketch);

        // Draw the Pitch Line
        this.drawPitchLine(sketch);
    };

    PitchController.prototype.collides = function(rect, x, y) {
        var isCollision = false;
        if (rect.x2 >= x && rect.x1 <= x && rect.y2 >= y && rect.y1 <= y) {
            isCollision = true;
        }

        return isCollision;
    }

    PitchController.prototype.update = function(touchX, touchY) {
        // return if touch is not even in controller container
        if (!this.collides(this.column, touchX, touchY)) return;

        this.updatePitchY(touchY);
        this.updatePitchY(touchY);

    }

    return PitchController;
});
