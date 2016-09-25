require(['grid/Grid', 'sketch',  'sockets/setupSb', 'sockets/sb'],function (Grid, sketch, setupSb, sb){
	document.ontouchmove = function(event) {
	    event.preventDefault();
	}

	window.onload = setupSb(sb);

	// var grid = new Grid();
	var myp5 = new p5(sketch, 'p5sketch');

	// $('#update').text('hi');

});
