// Interface
require(['interface/topMenu'], function () {

})

// Main App Logic
require(['grid/Grid', 'sketch',  'sockets/setupSb', 'sockets/sb', 'config/Config'],function (Grid, sketch, setupSb, sb, config){
	document.ontouchmove = function(event) {
	    event.preventDefault();
	}

	window.onload = setupSb(sb);

	$('#connect').on('click', function () {
		sb.send('connect', 'string', 'unregistered#' + random_id + '#null');
		config.connected = true;
		$(this).hide();
	})

	$('#selectInstrument').on('click', function() {
		sb.send('connect', 'string', 'instrument#' + random_id + '#' + $( "#instrument" ).val() );
		$(this).hide();
				$('#instrument-container').hide();

		$('canvas').show();
	})

	// var grid = new Grid();
	// console.log(grid);

	var myp5 = new p5(sketch, 'p5sketch');

	console.log(myp5);
	// $('#update').text('hi');

});
