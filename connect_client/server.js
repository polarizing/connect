var path = require('path');

var http = require('http');
var server = http.createServer();

var express = require('express');
var app = express();

server.on('request', app);

server.listen(1337, function () {
    console.log('The server is listening on port 1337!');
    console.log(path.join(__dirname, 'build'));
});

app.use(express.static(path.join(__dirname)));

app.get('/', function (req, res) {
	console.log('hi');
    res.sendFile(path.join(__dirname, 'index.html'));
})

// app.get('/client', function (req, res) {
//     res.sendFile(path.join(__dirname, 'client.html'));
// })