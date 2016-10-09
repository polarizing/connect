define(['grid/Grid', 'sockets/sb', 'game/BeatTile', 'game/PitchTile', 'game/PitchController', 'game/BeatController', 'config/Colors', 'interface/topMenu'], function(Grid, sb, BeatTile, PitchTile, PitchController, BeatController, Colors, topMenu) {
    var s = function(sketch) {

        var beatmakerGrid;
        var pitchGrid1;
        var pitchGrid2;
        var pitchTilesGrid1;
        var pitchTilesGrid2;

        var pitchCtrl1;
        var pitchCtrl2;
        var beatCtrl;
        var beatTiles;

        sketch.preload = function() {
            console.log('hi');
            sketch.sfThin = sketch.loadFont('/app/assets/sfThin.ttf');
            sketch.sfBold = sketch.loadFont('/app/assets/sfBold.ttf');
            sketch.sfRegular = sketch.loadFont('/app/assets/sfRegular.ttf');
            sketch.sfUltraLight = sketch.loadFont('/app/assets/sfUltraLight.ttf');
            sketch.glowingImg = sketch.loadImage('/app/assets/glowing.png');
            console.log(sketch.glowingImg);
        }

        sketch.setup = function() {

            // window.addEventListener('resize', resize.bind(this));
            // window.addEventListener('orientationchange', doOnOrientationChange);

            sketch.createCanvas(sketch.windowWidth, sketch.windowHeight);

            pitchGrid1 = new Grid(sketch, 0, 0, sketch.width * 0.20, sketch.height);
            pitchGrid1.setOffsets({ top: 65, right: 10, bottom: 20, left: 20 });
            pitchGrid1.setPartitions({ row: 8, column: 1 });

            pitchGrid2 = new Grid(sketch, sketch.width * 0.20, 0, sketch.width * 0.4, sketch.height)
            pitchGrid2.setOffsets({ top: 65, right: 20, bottom: 20, left: 10 });
            pitchGrid2.setPartitions({ row: 8, column: 1 });

            beatmakerGrid = new Grid(sketch, sketch.width * 0.4, 0, sketch.width, sketch.height);
            beatmakerGrid.setOffsets({ top: 10, right: 10, bottom: 10, left: 10 });
            beatmakerGrid.setPartitions({ row: 4, column: 4 });

            // Get a list of all game beatTiles
            beatTiles = beatmakerGrid.getColumns().map(function(column, columnIdx) {
                return column.getTiles().map(function(tile, tileIdx) {
                    return new BeatTile(columnIdx, tileIdx, tile.x1, tile.y1, tile.x2, tile.y2, false);
                })
            }).reduce(function(a, b) {
                return a.concat(b);
            });

            // Create two pitchControllers
            // Use pitch grid as starting point
            var pitchCtrl1Column = pitchGrid1.getColumn(0)
            pitchCtrl1 = new PitchController(1, pitchCtrl1Column, pitchCtrl1Column.x1, pitchCtrl1Column.y1 - 45, pitchCtrl1Column.x2, pitchCtrl1Column.y2);

            // Use pitch grid as starting point
            var pitchCtrl2Column = pitchGrid2.getColumn(0)
            pitchCtrl2 = new PitchController(2, pitchCtrl2Column, pitchCtrl2Column.x1, pitchCtrl2Column.y1 - 45, pitchCtrl2Column.x2, pitchCtrl2Column.y2);

            // Create a beatController

            var beatCtrlContainer = beatmakerGrid.getGrid();
            beatCtrl = new BeatController(beatCtrlContainer, beatCtrlContainer.x1, beatCtrlContainer.y1, beatCtrlContainer.x2, beatCtrlContainer.y2);

        };

        sketch.windowResized = function() {
            sketch.resizeCanvas(sketch.windowWidth, sketch.windowHeight);

            // resize beatmaker
            beatmakerGrid.setGrid(sketch.width * 0.4, 0, sketch.width, sketch.height);
            var currTileIndex = 0;
            beatmakerGrid.getColumns().forEach(function(column, columnIdx) {
                column.getTiles().forEach(function(tile, tileIdx) {
                    beatTiles[currTileIndex].updatePosition(tile.x1, tile.y1, tile.x2, tile.y2);
                    currTileIndex++;
                })
            })

            // resize pitch controllers
            pitchGrid1.setGrid(0, 0, sketch.width * 0.2, sketch.height);
            pitchGrid2.setGrid(sketch.width * 0.2, 0, sketch.width * 0.4, sketch.height);
            var pitchCtrl1Column = pitchGrid1.getColumn(0)
            pitchCtrl1.resize(pitchCtrl1Column, pitchCtrl1Column.x1, pitchCtrl1Column.y1 - 45, pitchCtrl1Column.x2, pitchCtrl1Column.y2);
            var pitchCtrl2Column = pitchGrid2.getColumn(0)
            pitchCtrl2.resize(pitchCtrl2Column, pitchCtrl2Column.x1, pitchCtrl2Column.y1 - 45, pitchCtrl2Column.x2, pitchCtrl2Column.y2);


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
            console.log('hi');
            console.log(rect);
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

        function drawDots(beatTiles) {
            beatTiles.forEach(function(tile) {
                sketch.fill(Colors.lightGrey);
                sketch.noStroke();
                var m = midpoint(tile.x1, tile.y1, tile.x2, tile.y2)
                sketch.ellipse(m.x, m.y, 5, 5);
            })
        }

        sketch.draw = function() {
            sketch.background(63, 63, 63);

            // Left Side of Application
            // Pitch Controller 1
            pitchCtrl1.draw(sketch);
            // Pitch Controller 2s
            pitchCtrl2.draw(sketch);

            // Right Side of Application
            beatCtrl.draw(sketch);
            drawDots(beatTiles);
            beatTiles.forEach(function(tile) {
                tile.draw(sketch);
            })

            // Logging
            sketch.fill(255);
            sketch.text('X: ' + sketch.touchX + ' Y: ' + sketch.touchY, 25, 25);

            var fps = sketch.frameRate();
            sketch.text("FPS: " + fps.toFixed(2), 10, sketch.height - 10);

            // Debugging
            // pitchGrid1.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } });
            // pitchGrid2.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } });
            // beatmakerGrid.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } })

        };

        sketch.mousePressed = function() {
            // if (sketch.mouseX > 100 && sketch.mouseX < sketch.width && sketch.mouseY > 0 && sketch.mouseY < sketch.height) {
            //  var fs = sketch.fullscreen();
            //  sketch.fullscreen(!fs);
            // }
            $('#update2').text('mousepressed event triggered');
            console.log('mousepressed');
        }

        sketch.touchStarted = function(evt) {
            // create a random background color
            // console.log(evt);
            $('#update').text('touchstarted event triggered');
            console.log('touchstarted');
            sb.send("buttonPress", "boolean", "true");


        }

        sketch.touchMoved = function(evt) {
            //stroke(255, 0, 0);
            //line(touchX, touchY, ptouchX, ptouchY);
            // console.log(evt);
            // $('#update').text('touchmoved event triggered');
            // sb.send("buttonPress", "boolean", "true");
            // console.log('touchmoved');

            var pitchCtrl1Container = pitchCtrl1.getPitchContainer();

            if (collides(pitchCtrl1Container, sketch.touchX, sketch.touchY)) {
                pitchCtrl1.updatePitchY(sketch.touchY);
            }


            var pitchCtrl2Container = pitchCtrl2.getPitchContainer();

            if (collides(pitchCtrl2Container, sketch.touchX, sketch.touchY)) {
                pitchCtrl2.updatePitchY(sketch.touchY);
            }


        }

        sketch.touchEnded = function(evt) {
            //stroke(0, 0, 255);
            //line(touchX, touchY, ptouchX, ptouchY);
            // console.log(evt);
            // console.log(topMenu.isNavOpen());

            if (!topMenu.isNavOpen()) {

                beatTiles.forEach(function(tile) {
                    if (collides(tile, sketch.touchX, sketch.touchY)) {
                        tile.setActive();
                    }
                })

                // $('#update').text('touchended event triggered');
                // sb.send("buttonPress", "boolean", "true");

            }


        }
    };
    return s;
})
