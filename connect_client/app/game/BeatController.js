define(['config/Colors', 'config/Config', 'game/BeatTile'], function(Colors, Config, BeatTile) {

    var tileMargin = Config.pitchTileMargin;

    function BeatController(container, x1, y1, x2, y2) {
        this.container = container;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;
        // this.tiles = this.createPitchTiles(this.column)
    }

    // BeatController.prototype.createPitchTiles = function(column) {
    //     return column.getTiles().map(function(tile, tileIdx) {
    //         return new PitchTile(tileIdx, tile.x1, tile.y1, tile.x2, tile.y2, false);
    //     })
    // }

    // BeatController.prototype.resizePitchTiles = function(column) {
    //     console.log('hi')
    //     var self = this;
    //     column.getTiles().map(function(tile, tileIdx) {
    //         self.tiles[tileIdx].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
    //     })
    // }

    BeatController.prototype.resize = function(column, x1, y1, x2, y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;

        this.column = column;
        // this.resizePitchTiles(this.column)
    }

    BeatController.prototype.drawContainerOutline = function(sketch) {
        sketch.fill('rgb(110, 110, 110)');
        // sketch.stroke('rgb(0, 0, 0)');
        sketch.noStroke();
        sketch.rect(this.x1, this.y1, this.width, this.height);

        sketch.stroke('rgb(0, 0, 0)')
        // sketch.filter(sketch.BLUR, 6);
        // sketch.stroke(0);
        sketch.line(this.x1, this.y1, this.x1, this.y2);
        // sketch.fill('rgb(39, 39, 39)');
        // sketch.stroke('rgb(0, 0, 0)')
        // sketch.rect(this.x1, this.y1 + 40, this.width, this.height - 40, 0, 0, 3, 3);
        // sketch.fill('rgb(30, 30, 30)');
        // sketch.rect(this.x1, this.y1, this.width, 40, 3, 3, 0, 0);
        // sketch.fill('rgb(201, 133, 72)')
        // sketch.textAlign(sketch.LEFT, sketch.CENTER);
        // sketch.textSize(15);
        // if (this.controllerId == 1) {
        //     sketch.text('Harmony', (this.x1 + 10), (this.y1 + this.y1 + 40) / 2);
        // } else if (this.controllerId == 2) {
        //     sketch.text('Refrain', (this.x1 + 10), (this.y1 + this.y1 + 40) / 2);
        // }
    }

    // BeatController.prototype.drawPitchTiles = function(sketch) {
    //     this.tiles.forEach(function(tile) {
    //         tile.draw(sketch);
    //     })
    // }

    BeatController.prototype.draw = function(sketch, activeColumn) {
        // Draw Outline (including top bar / background / border)
        this.drawContainerOutline(sketch);
        // Draw the Tiles
        // this.drawPitchTiles(sketch);
    };

    return BeatController;
});
