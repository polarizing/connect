define(['grid/Grid', 'sockets/sb', 'game/GameTile', 'config/Colors', 'interface/topMenu'], function(Grid, sb, GameTile, Colors, topMenu) {
    var s = function(sketch) {

        var grid;
        var tiles;

        sketch.setup = function() {

            // window.addEventListener('resize', resize.bind(this));
            // window.addEventListener('orientationchange', doOnOrientationChange);

            sketch.createCanvas(sketch.windowWidth, sketch.windowHeight * 0.80);
            grid = new Grid(sketch, 0, 0, sketch.width, sketch.height);
            grid.setOffsets({ top: 0, right: 0, bottom: 0, left: 0 });
            grid.setPartitions({ row: 14, column: 8 });

            // Get a list of all game tiles
            tiles = grid.getColumns().map(function(column, columnIdx) {
                return column.getTiles().map(function(tile, tileIdx) {
                    return new GameTile(columnIdx, tileIdx, tile.x1, tile.y1, tile.x2, tile.y2, false);
                })
            }).reduce(function(a, b) {
                return a.concat(b);
            });

            // console.log(tiles);
            // console.log(columns);
            // console.log(grid);
            // grid.draw();

        };

        sketch.windowResized = function() {
            sketch.resizeCanvas(sketch.windowWidth, sketch.windowHeight * 0.80);
            grid.setGrid(0, 0, sketch.width, sketch.height);
            var currTileIndex = 0;
            grid.getColumns().forEach(function(column, columnIdx) {
                column.getTiles().forEach(function(tile, tileIdx) {
                    tiles[currTileIndex].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
                    currTileIndex++;
                })
            })
        }

        function doOnOrientationChange() {
            switch (window.orientation) {
                case -90:
                case 90:
                    alert('landscape');
                    resize();
                    break;
                default:
                    alert('portrait');
                    resize();
                    break;
            }
        }

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        function collides(rect, x, y) {
            var isCollision = false;

            if (rect.x2 >= x && rect.x1 <= x && rect.y2 >= y && rect.y1 <= y) {
                isCollision = true;
            }

            return isCollision;
        }

        function midpoint(x1, y1, x2, y2) {
            var xCenter = (x1 + x2) / 2;
            var yCenter = (y1 + y2) / 2;
            return {
                x: xCenter,
                y: yCenter
            };
        }

        function drawDots(tiles) {
            tiles.forEach(function(tile) {
                sketch.fill(Colors.lightGrey);
                sketch.noStroke();
                var m = midpoint(tile.x1, tile.y1, tile.x2, tile.y2)
                sketch.ellipse(m.x, m.y, 5, 5);
            })
        }

        sketch.draw = function() {
            sketch.background(47, 47, 51);

            drawDots(tiles);

            tiles.forEach(function(tile) {
                tile.draw(sketch);
            })

            // Logging
            sketch.fill(255);
            sketch.text('X: ' + sketch.touchX + ' Y: ' + sketch.touchY, 25, 25);

            var fps = sketch.frameRate();
            sketch.text("FPS: " + fps.toFixed(2), 10, sketch.height - 10);

            // Grid Logging
            // grid.draw();
        };

        sketch.mousePressed = function() {
            // if (sketch.mouseX > 100 && sketch.mouseX < sketch.width && sketch.mouseY > 0 && sketch.mouseY < sketch.height) {
            //  var fs = sketch.fullscreen();
            //  sketch.fullscreen(!fs);
            // }
            $('#update2').text('mousepressed event triggered');
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
            console.log(topMenu.isNavOpen());

            if (!topMenu.isNavOpen()) {

                tiles.forEach(function(tile) {
                    if (collides(tile, sketch.touchX, sketch.touchY)) {
                        tile.setActive();
                    }
                })

                $('#update').text('touchended event triggered');
                sb.send("buttonPress", "boolean", "true");
            
            }


        }
    };
    return s;
})
