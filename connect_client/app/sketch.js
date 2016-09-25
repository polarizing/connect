define(['grid/Grid', 'sockets/sb'], function (Grid, sb) {
    var s = function(sketch) {

        var x = 100;
        var y = 100;

        var c = 255;

        sketch.setup = function() {
            sketch.createCanvas(sketch.windowWidth, sketch.windowHeight - 100);
            grid = new Grid(sketch, 0, 0, sketch.width, sketch.height);
            grid.setOffsets({ top: 0, right: 0, bottom: 0, left: 0 });
            grid.setPartitions({ row: 16, column: 8 });
            console.log(grid);
            // grid.draw();

        };

        sketch.draw = function() {
            sketch.background(c);
            // grid.draw();

            sketch.fill(0);
            sketch.text('X: ' + sketch.touchX + ' Y: ' + sketch.touchY, 25, 25);

            // sketch.fill(255);
            // sketch.rect(x,y,50,50);
            grid.draw();
        };

        sketch.mousePressed = function() {
            // if (sketch.mouseX > 100 && sketch.mouseX < sketch.width && sketch.mouseY > 0 && sketch.mouseY < sketch.height) {
            //  var fs = sketch.fullscreen();
            //  sketch.fullscreen(!fs);
            // }
            // $('#update').text('mousepressed event triggered');
        }

        sketch.touchStarted = function(evt) {
            // create a random background color
            // console.log(evt);
            $('#update').text('touchstarted event triggered');
            sb.send("buttonPress", "boolean", "true");

        }

        sketch.touchMoved = function(evt) {
            //stroke(255, 0, 0);
            //line(touchX, touchY, ptouchX, ptouchY);
            // console.log(evt);
            $('#update').text('touchmoved event triggered');
            sb.send("buttonPress", "boolean", "true");
        }

        sketch.touchEnded = function(evt) {
            //stroke(0, 0, 255);
            //line(touchX, touchY, ptouchX, ptouchY);
            // console.log(evt);
            $('#update').text('touchended event triggered');
            sb.send("buttonPress", "boolean", "true");

        }
    };
    return s;
})