// Main App Logic
require(['grid/Grid', 'sketch', 'sockets/setupSb', 'sockets/sb', 'config/Config'], function(Grid, sketch, setupSb, sb, config) {
    document.ontouchmove = function(event) {
        event.preventDefault();
    }

    window.onload = setupSb(sb);

    $('#join').on('click', function() {
        $('#instruments').slideDown("slow");
        $('#sub-text').hide();
    })

    $('.instrument').on('click', function() {

        // connect
        sb.send('connect', 'string', 'unregistered#' + random_id + '#null');
        config.connected = true;

        var instrument = $(this).data('instrument');
        config.instrument = instrument;

        sb.send('connect', 'string', 'instrument#' + random_id + '#' + instrument);
        $('.container').hide();
        $('canvas').show();
    })

    // $('#connect').on('click', function () {
    //  sb.send('connect', 'string', 'unregistered#' + random_id + '#null');
    //  config.connected = true;
    //  $(this).hide();
    // })

    $('#selectInstrument').on('click', function() {
        sb.send('connect', 'string', 'instrument#' + random_id + '#' + $("#instrument").val());
        $(this).hide();
        $('#instrument-container').hide();

        $('canvas').show();
    })

    var myp5 = new p5(sketch, 'p5sketch');


});
