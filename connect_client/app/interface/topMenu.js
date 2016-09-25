define("interface/topMenu", function() {

    var isNavOpen = false;
    $('.navigation').on('touchstart', function() {
        console.log('hi');
        isNavOpen = true;
        $('.navigation').css('color', 'rgb(133, 121, 242)');
    });

    $("#mypanel").panel({
        close: function(event, ui) {
        	isNavOpen = false;
        	console.log('closing panel');
        }
    });

    return {
        isNavOpen: function() {
            return isNavOpen;
        }
    };
    // console.log(x);
})
