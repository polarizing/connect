define('sockets/setupSb', ['sockets/sb'], function(sb) {

    // Spacebrew Object
    // var sb = sb;
    app_name = "client";
    random_id = "0000" + Math.floor(Math.random() * 10000);

    /**
     * setup Configure spacebrew connection and adds the mousedown listener.
     */
    var setup = function(sb) {
        // we make a random name, which allows you connect more than one
        // of this example from the same computer
        // passing the "name" argument in a query string overrides this!

        app_name = app_name + ' ' + random_id.substring(random_id.length - 4);

        // create spacebrew client object
        // sb = new Spacebrew.Client("10.209.1.57", "Connect", "Connect Spacebrew Client", {port: 9000, reconnect: true} );
        $('#update').text(sb);
        // set the base description
        sb.name(app_name);
        sb.description("This spacebrew client sends and receives boolean messages.");

        // configure the publication and subscription feeds
        sb.addPublish("connect", "string", "");
        sb.addSubscribe("checkAlive", "boolean");

        // override Spacebrew events - this is how you catch events coming from Spacebrew
        sb.onBooleanMessage = onBooleanMessage;
        sb.onOpen = onOpen;

        // connect to spacbrew
        sb.connect();

    }

    /**
     * Function that is called when Spacebrew connection is established
     */
    function onOpen() {
        var message = "Connected as <strong>" + sb.name() + "</strong>. ";
        if (sb.name() === app_name) {
            message += "<br>You can customize this app's name in the query string <br>by adding <strong>name=your_app_name</strong>."
        }
        // var nameElement = document.getElementById("name");
        // nameElement.innerHTML = message;
    }

    /**
     * Function that is called whenever the button is pressed.  
     * @param  {Event object} evt Holds information about the button press event
     */
    function onButtonPress(evt) {
        // this line prevents the event being called twice
        // on mobile 
        evt.preventDefault();

        console.log("[onButtonPress] button has been pressed");
        sb.send("buttonPress", "boolean", "true");
    }

    // *
    //  * Function that is called whenever the button is released.  
    //  * @param  {Event object} evt Holds information about the button press event

    function onButtonRelease(evt) {
        // this line prevents the event being called twice
        // on mobile 
        evt.preventDefault();

        console.log("[onButtonRelease] button has been released");
        sb.send("buttonPress", "boolean", "false");
    }

    /**
     * onBooleanMessage Function that is called whenever new spacebrew boolean messages are received.
     *          It accepts two parameters:
     * @param  {String} name    Holds name of the subscription feed channel
     * @param  {Boolean} value  Holds value received from the subscription feed
     */
    function onBooleanMessage(name, value) {
        console.log("[onBooleanMessage] boolean message received ", value);
        console.log(name);
        if (name == "checkAlive") {
			sb.send('connect', 'string', 'ping#' + random_id + '#null');
        }
    }

    return setup;

})
