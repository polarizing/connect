define(['config/Colors', 'config/Config', 'game/BeatTile', 'sockets/sb'], function(Colors, Config, BeatTile, sb) {

    var tileMargin = Config.pitchTileMargin;

    function BeatController(grid) {
        this.grid = grid;
        this.container = this.grid.getGrid(); // grid container
        this.x1 = this.container.x1;
        this.y1 = this.container.y1;
        this.x2 = this.container.x2;
        this.y2 = this.container.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
        this.tiles = this.createBeatTiles(this.grid);
        this.beats = this.getNumBeats(this.grid);
        this.activeTiles = [];
        this.notation = "";
    }

    BeatController.prototype.createBeatTiles = function(grid) {
        return grid.getColumns().map(function(column, columnIdx) {
            return column.getTiles().map(function(tile, tileIdx) {
                return new BeatTile(columnIdx, tileIdx, tile.x1, tile.y1, tile.x2, tile.y2, false);
            })
        }).reduce(function(a, b) {
            return a.concat(b);
        });
    }

    BeatController.prototype.getNumBeats = function(grid) {
        return grid.getColumns().length;
    }

    BeatController.prototype.collides = function(rect, x, y) {
        var isCollision = false;
        if (rect.x2 >= x && rect.x1 <= x && rect.y2 >= y && rect.y1 <= y) {
            isCollision = true;
        }

        return isCollision;
    }

    BeatController.prototype.update = function(touchX, touchY) {

        // return if touch is not even in controller container
        if (!this.collides(this.container, touchX, touchY)) return;
        var self = this;
        // otherwise check for collision with a beat tile
        this.tiles.forEach(function(tile) {
            if (self.collides(tile, touchX, touchY)) {

                var tileColumnIndex = tile.getColumnIndex();
                var activeTile = self.activeTiles[tileColumnIndex];

                if (!activeTile) {
                    tile.setActive();
                    self.activeTiles[tileColumnIndex] = tile;
                } else {
                    if (activeTile.getColumnIndex() == tile.columnIdx && activeTile.getTileIndex() == tile.tileIdx) {
                        tile.setInactive();
                        self.activeTiles[tileColumnIndex] = undefined;
                    } else {
                        activeTile.setInactive();
                        tile.setActive();
                        self.activeTiles[tileColumnIndex] = tile;
                    }
                }
                // update beat notation to string
                self.updateNotation();
            }
        });

        return true;
    }

    BeatController.prototype.updateNotation = function() {
        var beatString = "";

        for (var i = 0; i < this.beats; i++) {
            var tile = this.activeTiles[i];
            if (tile) {
                if (tile.isActive) {
                    beatString += tile.getTileIndex();
                } else {
                    beatString += '-'
                }
            }
            else {
                beatString += '-';
            }
        }
        

        this.notation = beatString;
        return this.notation;
    }

    BeatController.prototype.getNotation = function () {
        return this.notation;
    }

    BeatController.prototype.resizeBeatTiles = function(grid) {
        var currTileIndex = 0;
        var self = this;
        grid.getColumns().forEach(function(column, columnIdx) {
            column.getTiles().forEach(function(tile, tileIdx) {
                self.tiles[currTileIndex].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
                currTileIndex++;
            })
        })
    }

    BeatController.prototype.resize = function(grid) {
        // resize container
        this.grid = grid;
        this.container = this.grid.getGrid(); // grid container
        this.x1 = this.container.x1;
        this.y1 = this.container.y1;
        this.x2 = this.container.x2;
        this.y2 = this.container.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;

        // resize beat tiles
        this.resizeBeatTiles(grid);
    }

    BeatController.prototype.drawContainerOutline = function(sketch) {
        sketch.fill('rgb(110, 110, 110)');
        // sketch.stroke('rgb(0, 0, 0)');
        sketch.noStroke();
        sketch.rect(this.x1, this.y1, this.width, this.height);

        sketch.stroke('rgb(0, 0, 0)')
        sketch.line(this.x1, this.y1, this.x1, this.y2);

    }

    BeatController.prototype.midpoint = function(x1, y1, x2, y2) {
        var xCenter = (x1 + x2) / 2;
        var yCenter = (y1 + y2) / 2;
        return {
            x: xCenter,
            y: yCenter
        };
    }

    BeatController.prototype.drawTileDots = function(sketch) {
        var self = this;
        this.tiles.forEach(function(tile) {
            // draw dots
            sketch.fill(Colors.lightGrey);
            sketch.noStroke();
            var m = self.midpoint(tile.x1, tile.y1, tile.x2, tile.y2)
            sketch.ellipse(m.x, m.y, 5, 5);
        })
    }

    BeatController.prototype.drawTiles = function(sketch) {
        this.tiles.forEach(function(tile) {
            tile.draw(sketch);
        })
    }

    BeatController.prototype.draw = function(sketch) {
        // Draw Outline (including top bar / background / border)
        this.drawContainerOutline(sketch);
        // Draw the Tile
        this.drawTiles(sketch);
        // Draw the Tile Dots
        this.drawTileDots(sketch);
    };

    return BeatController;
});
