define(['grid/Grid', 'sockets/sb', 'game/BeatTile', 'game/PitchTile', 'game/PitchController', 'game/BeatController', 'game/ClientInfoController', 'config/Colors', 'interface/topMenu'], function(Grid, sb, BeatTile, PitchTile, PitchController, BeatController, ClientInfoController, Colors, topMenu) {
    var s = function(sketch) {

        var pitchGrid1;
        var pitchGrid2;
        var beatmakerGrid;

        var pitchCtrl1;
        var pitchCtrl2;
        var beatCtrl;

        sketch.preload = function() {
            console.log('Preloading fonts and assets ...');
            sketch.sfThin = sketch.loadFont('/app/assets/sfThin.ttf');
            sketch.sfBold = sketch.loadFont('/app/assets/sfBold.ttf');
            sketch.sfRegular = sketch.loadFont('/app/assets/sfRegular.ttf');
            sketch.sfUltraLight = sketch.loadFont('/app/assets/sfUltraLight.ttf');
            sketch.glowingImg = sketch.loadImage('/app/assets/glowing.png');
            sketch.bassSynth = sketch.loadImage('/app/assets/bass-synth.png');
            sketch.harpsichord = sketch.loadImage('/app/assets/harpsichord.png');
            sketch.pluckedSynth = sketch.loadImage('/app/assets/plucked-synth.png');
            sketch.marimba = sketch.loadImage('/app/assets/marimba.png');
            sketch.orientation = sketch.loadImage('/app/assets/rotate.png');
        }

        sketch.setup = function() {

            // window.addEventListener('resize', resize.bind(this));
            // window.addEventListener('orientationchange', doOnOrientationChange);

            sketch.createCanvas(sketch.windowWidth, sketch.windowHeight);

            // Creating pitch grids
            pitchGrid1 = new Grid(sketch, 0, sketch.height * 0.10, sketch.width * 0.40, sketch.height);
            pitchGrid1.setOffsets({ top: 65, right: 20, bottom: 20, left: 20 });
            pitchGrid1.setPartitions({ row: 8, column: 1 });


            infoGrid1 = new Grid(sketch, 0, 0, sketch.width * 0.4, sketch.height * 0.1 + 10)
            infoGrid1.setOffsets({ top: 65, right: 20, bottom: 0, left: 20 });
            infoGrid1.setPartitions({ row: 1, column: 1 });


            // clientInfoGrid = new Grid(sketch, sketch.width * 0.20, 0, sketch.width * 0.4, sketch.height * 0.5)
            // clientInfoGrid.setOffsets({ top: 65, right: 20, bottom: 20, left: 10 });
            // clientInfoGrid.setPartitions({ row: 8, column: 1 });


            // Create pitch controllers using pitch grids as starting point
            pitchCtrl1 = new PitchController(1, pitchGrid1);
            // pitchCtrl2 = new PitchController(2, pitchGrid2);
            infoCtrl1 = new ClientInfoController(infoGrid1);

            // Creating beatmaker grids
            beatmakerGrid = new Grid(sketch, sketch.width * 0.40, 0, sketch.width, sketch.height);
            beatmakerGrid.setOffsets({ top: 10, right: 10, bottom: 10, left: 10 });
            beatmakerGrid.setPartitions({ row: 4, column: 4 });

            // Create beat controller using beat grid as starting point
            beatCtrl = new BeatController(beatmakerGrid);
        };

        sketch.windowResized = function() {
            sketch.resizeCanvas(sketch.windowWidth, sketch.windowHeight);

            // Resize beatmaker
            beatmakerGrid.setGrid(sketch.width * 0.4, 0, sketch.width, sketch.height);
            beatCtrl.resize(beatmakerGrid);

            // Resize pitch controllers
            pitchGrid1.setGrid(0, sketch.height * 0.10, sketch.width * 0.40, sketch.height);
            // pitchGrid2.setGrid(sketch.width * 0.2, sketch.height * 0.4, sketch.width * 0.4, sketch.height);
            infoGrid1.setGrid(0, 0, sketch.width * 0.4, sketch.height * 0.1 + 10);

            // Resize pitch controllers and grid system
            pitchCtrl1.resize(pitchGrid1);
            infoCtrl1.resize(infoGrid1);
            // pitchCtrl2.resize(pitchGrid2);

            // Resize client info controller
            // clientInfoGrid.setGrid(sketch.width * 0.20, 0, sketch.width * 0.4, sketch.height * 0.5)
            // clientInfoCtrl.resize(clientInfoGrid);


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

        sketch.draw = function() {

            sketch.background(63, 63, 63);

            // If in Portrait Mode
            if (sketch.height > sketch.width) {
                sketch.image(sketch.orientation, 0, 0, sketch.orientation.width, sketch.orientation.height, 0, 0, sketch.width, sketch.height)

            } else {

                // Left Side of Application
                // Pitch Controller 1
                pitchCtrl1.draw(sketch);
                infoCtrl1.draw(sketch);
                // Pitch Controller 2s
                // pitchCtrl2.draw(sketch);

                // clientInfoCtrl.draw(sketch);

                // Right Side of Application
                beatCtrl.draw(sketch);

                // Logging
                // sketch.fill(255);
                // sketch.text('X: ' + sketch.touchX + ' Y: ' + sketch.touchY, 40, 15);

                // var fps = sketch.frameRate();
                // sketch.text("FPS: " + fps.toFixed(2), 40, sketch.height - 10);

                // Debugging
                // pitchGrid1.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } });
                // infoGrid1.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } });
                // beatmakerGrid.draw({ column: true, row: true, margin: true, color: { lines: "rgb(0, 0, 0)", margin: 'rgb(255, 0, 0)' } })

            }


        };

        sketch.mousePressed = function() {
            $('#update2').text('mousepressed event triggered');
        }

        sketch.touchStarted = function(evt) {
            $('#update').text('touchstarted event triggered');

        }

        sketch.touchMoved = function(evt) {

            var pitchCtrl1Updated = pitchCtrl1.update(sketch.touchX, sketch.touchY);
            if (pitchCtrl1Updated) {
                var melodyPitch = pitchCtrl1.getNotation();
                sb.send('connect', 'string', "melody#" + random_id + "#" + melodyPitch);
            }

        }

        sketch.touchEnded = function(evt) {

            var beatCtrlUpdated = beatCtrl.update(sketch.touchX, sketch.touchY);

            if (beatCtrlUpdated) {
                var beatNotation = beatCtrl.getNotation();

                sb.send('connect', 'string', "rhythm#" + random_id + "#" + beatNotation);
            }


        }
    };
    return s;
})
