define(['config/Colors', 'config/Config'], function(Colors, Config) {

    var tileMargin = Config.pitchTileMargin;

    function PitchTile(tileIdx, x1, y1, x2, y2, active) {

        this.tileIdx = tileIdx;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;

        this.active = active || false;
    }

    PitchTile.prototype.updatePosition = function(x1, y1, x2, y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1
    }

    PitchTile.prototype.draw = function(sketch, activeColumn) {

        var margin = tileMargin;
        var note = Config.notes[this.tileIdx];


        // if first pitch
        sketch.textFont(sketch.sfThin);

        sketch.fill('rgb(208, 136, 78)');
        sketch.stroke('rgb(200, 133, 72)');
        sketch.strokeWeight(0.3);
        var shortLineDist = (this.width / 4);
        sketch.line(this.x1 + margin, this.y1, this.x1 + shortLineDist, this.y1);
        sketch.line(this.x1 + shortLineDist * 3, this.y1, this.x2 - margin, this.y1);

        sketch.strokeWeight(0);
        sketch.textSize(10);
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.textStyle(sketch.NORMAL);
        sketch.text(Config.pitchTileRanges[this.tileIdx], this.x1 + shortLineDist * 1 + ((shortLineDist * 2) * 0.5), this.y1)
        sketch.strokeWeight(1);

    };

    PitchTile.prototype.setInactive = function() {
        this.active = false;
    };

    PitchTile.prototype.isActive = function() {
        return this.active;
    };

    PitchTile.prototype.setActive = function() {
        this.active = true;
    };

    return PitchTile;
});
