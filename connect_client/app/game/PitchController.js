define(['config/Colors', 'config/Config', 'game/PitchTile'], function(Colors, Config, PitchTile) {

    var tileMargin = Config.pitchTileMargin;

    function PitchController(controllerId, column, x1, y1, x2, y2) {
        this.controllerId = controllerId;
        this.column = column;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
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
        console.log('hi')
        var self = this;
        column.getTiles().map(function(tile, tileIdx) {
            self.tiles[tileIdx].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
        })
    }

    PitchController.prototype.resize = function(column, x1, y1, x2, y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;

        this.column = column;
        this.resizePitchTiles(this.column)
    }

    PitchController.prototype.getTiles = function () {
        return this.tiles;
    }

    PitchController.prototype.getPitchContainer = function () {
        return this.column;
    }

    PitchController.prototype.updatePitchY = function (yPos) {
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

    return PitchController;
});
