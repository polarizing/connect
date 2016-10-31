define(['config/Colors', 'config/Config'], function(Colors, Config) {


    function ClientInfoController(grid) {
        this.grid = grid;
        this.column = this.grid.getColumn(0);
        this.x1 = this.column.x1;
        this.y1 = this.column.y1 - 45;
        this.x2 = this.column.x2;
        this.y2 = this.column.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
    }

    ClientInfoController.prototype.resize = function(grid) {
        // resize container
        this.column = this.grid.getColumn(0);
        this.x1 = this.column.x1;
        this.y1 = this.column.y1 - 45;
        this.x2 = this.column.x2;
        this.y2 = this.column.y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
    }


    ClientInfoController.prototype.getClientInfoContainer = function() {
        return this.column;
    }

    ClientInfoController.prototype.drawContainerOutline = function(sketch) {
        sketch.fill('rgb(39, 39, 39)');
        sketch.stroke('rgb(0, 0, 0)')
        sketch.rect(this.x1, this.y1, this.width, this.height);

    }


    ClientInfoController.prototype.drawInstrumentInfo = function(sketch) {

        sketch.noStroke();

        colorTextCenter = sketch.createVector(this.x1 + this.width * 0.5, (this.y1 + this.y2) / 2)
        sketch.fill('rgb(201, 133, 72)')
        sketch.textAlign(sketch.LEFT);
        sketch.textFont(sketch.sfRegular);
        sketch.textSize(14);
        sketch.text('Color:', colorTextCenter.x, colorTextCenter.y);

        colorTextWidth = sketch.textWidth('Color:')

        instrumentTextCenter = sketch.createVector(this.x1 + this.width * 0.05, (this.y1 + this.y2) / 2)
        sketch.fill('rgb(201, 133, 72)')
        sketch.textAlign(sketch.LEFT);
        sketch.textFont(sketch.sfRegular);
        sketch.textSize(14);
        sketch.text('Instrument:', instrumentTextCenter.x, instrumentTextCenter.y);
        instrumentTextWidth = sketch.textWidth('Instrument:')


        sketch.rectMode(sketch.CENTER);
        instrumentRectCenter = sketch.createVector(this.x1 + this.width * 0.05 + instrumentTextWidth + 10, ((this.y1 + this.y2) / 2) - 7 );

        if (Config.instrument == 'marimba') {
            sketch.image(sketch.marimba, 0, 0, sketch.marimba.width, sketch.marimba.height, instrumentRectCenter.x, instrumentRectCenter.y, 16, 16)
        } else if (Config.instrument == 'bassSynth') {
            sketch.image(sketch.bassSynth, 0, 0, sketch.bassSynth.width, sketch.bassSynth.height, instrumentRectCenter.x, instrumentRectCenter.y, 16, 16)

        } else if (Config.instrument == 'harpsichord') {
            sketch.image(sketch.harpsichord, 0, 0, sketch.harpsichord.width, sketch.harpsichord.height, instrumentRectCenter.x, instrumentRectCenter.y, 16, 16)

        } else if (Config.instrument == 'pluckedSynth') {
            sketch.image(sketch.pluckedSynth, 0, 0, sketch.pluckedSynth.width, sketch.pluckedSynth.height, instrumentRectCenter.x, instrumentRectCenter.y, 16, 16)
        }


        if (Config.instrument == 'marimba') {
            sketch.fill('rgb(170, 224, 49)');
        } else if (Config.instrument == 'bassSynth') {
            sketch.fill('rgb(54, 221, 220)');

        } else if (Config.instrument == 'harpsichord') {
            sketch.fill('rgb(234, 114, 63)');

        } else if (Config.instrument == 'pluckedSynth') {
            sketch.fill('rgb(243, 68, 145)');
        }
        sketch.stroke(1.0);
        rectCenter = sketch.createVector(this.x1 + this.width * 0.5 + colorTextWidth + 15, (this.y1 + this.y2) / 2);
        sketch.rect(rectCenter.x, rectCenter.y, 15, 15);
        sketch.rectMode(sketch.CORNER);


    }

    ClientInfoController.prototype.draw = function(sketch, activeColumn) {
        // Draw Outline (including top bar / background / border)
        this.drawContainerOutline(sketch);
        this.drawInstrumentInfo(sketch);


    };

    return ClientInfoController;
});
