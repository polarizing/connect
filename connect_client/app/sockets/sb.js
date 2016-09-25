define('sockets/sb', function () {

	var sb = new Spacebrew.Client("10.209.1.57", "Connect", "Connect Spacebrew Client", {port: 9000, reconnect: true} );
	return sb;
});