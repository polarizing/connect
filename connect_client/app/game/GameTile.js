define(['config/Colors', 'config/Config'], function(Colors, Config) {

	var tileMargin = Config.tileMargin;

	function GameTile (columnIdx, tileIdx, x1, y1, x2, y2, active) {

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

    GameTile.prototype.updatePosition = function (x1, y1, x2, y2) {
    	this.x1 = x1;
    	this.y1 = y1;
    	this.x2 = x2;
    	this.y2 = y2;
    	this.width = x2 - x1;
    	this.height = y2 - y1
    }

	GameTile.prototype.draw = function(sketch, activeColumn) {

		var margin = tileMargin;
		var note = Config.notes[this.tileIdx];
		if (this.columnIdx = activeColumn) sketch.fill(0);
		else sketch.fill(Colors[note]);

        if (this.active) {
        	sketch.noStroke();
        	sketch.rect(this.x1 + margin, this.y1 + margin, this.width - margin * 2, this.height - margin * 2);
        }

		// //get the note and color
		// var margin = tileMargin;
		// var note = Config.notes[this.y];
		// if (this.x === activeColumn) {
		// 	context.fillStyle = 'black';
		// } else {
		// 	context.fillStyle = Colors[note];
		// }
		// context.beginPath();
		// context.fillRect(this.x * width + tileMargin, this.y * height + tileMargin, width - tileMargin * 2, height - tileMargin * 2);
		// if (this._hovered) {
		// 	context.fillStyle = 'rgba(255, 255, 255, 0.4)';
		// 	context.beginPath();
		// 	context.fillRect(this.x * width + tileMargin, this.y * height + tileMargin, width - tileMargin * 2, height - tileMargin * 2);
		// }
	};

	GameTile.prototype.setInactive = function() {
		this.active = false;
	};

	GameTile.prototype.isActive = function() {
		return this.active;
	};

	GameTile.prototype.setActive = function() {
		this.active = true;
	};

	return GameTile;
});
