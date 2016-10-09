define(['config/Colors', 'config/Config'], function(Colors, Config) {

    var tileMargin = Config.beatTileMargin;

    function BeatTile(columnIdx, tileIdx, x1, y1, x2, y2, active) {

        this.columnIdx = columnIdx;
        this.tileIdx = tileIdx;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1;

        this.active = active || false;
    }

    BeatTile.prototype.updatePosition = function(x1, y1, x2, y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = x2 - x1;
        this.height = y2 - y1
    }

    BeatTile.prototype.draw = function(sketch, activeColumn) {

        var margin = tileMargin;
        var note = Config.notes[this.tileIdx];
        if (this.columnIdx = activeColumn) sketch.fill(0);
        else sketch.fill(Colors[note]);

        // if (this.active) {
        //     sketch.noStroke();
        //     sketch.rect(this.x1 + margin, this.y1 + margin, this.width - margin * 2, this.height - margin * 2);
        // }

        sketch.stroke('rgb(0, 0, 0)');
        sketch.strokeWeight(1.0);


        // draw shadows
        if (!this.active) {

        sketch.fill('rgb(254, 143, 67)');

        sketch.rect(this.x1 + margin, this.y1 + margin, this.width - margin * 2, this.height - margin * 2, 3, 3, 3, 3);

            sketch.strokeCap(sketch.ROUND);
            sketch.stroke('rgb(0, 0, 0)');
            sketch.strokeWeight(0.9);
            sketch.line(this.x1 + margin + 1.5, this.y2 - margin + 0.5, this.x2 - margin - 1.5, this.y2 - margin + 0.5);
            sketch.stroke('rgb(10, 10, 10)');
            sketch.strokeWeight(0.8);
            sketch.line(this.x1 + margin + 1.6, this.y2 - margin + 1, this.x2 - margin - 1.6, this.y2 - margin + 1);
            sketch.stroke('rgb(20, 20, 20)');
            sketch.strokeWeight(0.7);
            sketch.line(this.x1 + margin + 1.7, this.y2 - margin + 1.5, this.x2 - margin - 1.7, this.y2 - margin + 1.5);
            sketch.stroke('rgb(30, 30, 30)');
            sketch.strokeWeight(0.60);
            sketch.line(this.x1 + margin + 1.8, this.y2 - margin + 2, this.x2 - margin - 1.8, this.y2 - margin + 2);
            sketch.stroke('rgb(50, 50, 50)');
            sketch.strokeWeight(0.55);
            sketch.line(this.x1 + margin + 1.9, this.y2 - margin + 2.5, this.x2 - margin - 1.9, this.y2 - margin + 2.5);
            sketch.stroke('rgb(70, 70, 70)');
            sketch.strokeWeight(0.5);
            sketch.line(this.x1 + margin + 2.0, this.y2 - margin + 3.0, this.x2 - margin - 2.0, this.y2 - margin + 3.0);
            sketch.stroke('rgb(90, 90, 90)');
            sketch.strokeWeight(0.45);
            sketch.line(this.x1 + margin + 2.1, this.y2 - margin + 3.5, this.x2 - margin - 2.1, this.y2 - margin + 3.5);
            sketch.stroke('rgb(110, 110, 110)');
            sketch.strokeWeight(0.4);
            sketch.line(this.x1 + margin + 2.2, this.y2 - margin + 4.0, this.x2 - margin - 2.2, this.y2 - margin + 4.0);

        }

        if (this.active) {

            sketch.fill('rgb(254, 177, 110)');
            sketch.rect(this.x1 + margin, this.y1 + margin, this.width - margin * 2, this.height - margin * 2, 3, 3, 3, 3);

            sketch.stroke('rgb(255, 255, 255)');
            sketch.strokeWeight(2.0);
            sketch.rect(this.x1 + margin, this.y1 + margin, this.width - margin * 2, this.height - margin * 2, 3, 3, 3, 3);
            sketch.strokeWeight(1.0);
            sketch.image(sketch.glowingImg, 0, 0, sketch.glowingImg.width, sketch.glowingImg.height, this.x1, this.y1, this.width, this.height)

        }

    };

    BeatTile.prototype.setInactive = function() {
        this.active = false;
    };

    BeatTile.prototype.isActive = function() {
        return this.active;
    };

    BeatTile.prototype.setActive = function() {
        this.active = true;
    };

    return BeatTile;
});
