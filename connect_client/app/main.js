// Interface
require(['interface/topMenu'], function () {

})

// Main App Logic
require(['grid/Grid', 'sketch',  'sockets/setupSb', 'sockets/sb'],function (Grid, sketch, setupSb, sb){
	document.ontouchmove = function(event) {
	    event.preventDefault();
	}

	// window.onload = setupSb(sb);


	// var grid = new Grid();
	// console.log(grid);

	var myp5 = new p5(sketch, 'p5sketch');

	console.log(myp5);
	// $('#update').text('hi');

});
