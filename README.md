# connect

### September 13, 2016
After a talk with Professor Moon, he has given me the go-ahead to proceed with the project. I asked one question of particular interest, whether the individual smartphones themselves should have the animations playing alongside the main visualization or whether they should be kept simple, having the collaborative animation on the main screen. He explained that it would be better to not have the audience lose focus by paying attention to their own devices, rather how the whole visualization comes together. I think this makes sense and would be easier to develop than including animations that run on each device as well as the main screen (which don’t really serve a wider audience).

For the smartphone application, there are many hybrid frameworks for developing native web-apps (namely, Ionic, React Native, PhoneGap). A good comparison [here](https://www.codementor.io/react-js/tutorial/react-native-vs-ionic). In this project, I think it boils down to what we really need the app to do and what framework (React or Angular) I am more comfortable in. React Native is slightly faster, more responsive, and feels more native than Ionic but this only matters if we need these highly native components. For me, what I need the app to do, is to be able to interface with a main server, send data through websockets, and provide some customizability on the users end (maybe to select instruments or connect sounds to certain visualizations). I am not sure if I want to include a general visualization (that is default to all smartphones connecting, regardless of instrument) or not. This seems like something doable in Ionic with the given plus that I am more experienced in coding Angular. 

I have done a project in the past using barebones HTML/CSS/JS to build a mobile app and making the layout fully mobile-responsive and manually saving the webpage as a web-app (to launch from home screen) but the process was extremely arduous requiring a lot of hackish JS (hiding the address bar, maintaining device orientation, dealing with the infamous 300ms click delay -- see [this](http://developer.telerik.com/featured/300-ms-click-delay-ios-8/), etc.). Overall, it’s just a real pain. 

So I believe I am going to try Ionic and document my experience for the first time!

I am starting to research into the various Processing libraries used for web sockets. For a good primer on WebSockets, [see this](http://blog.teamtreehouse.com/an-introduction-to-websockets). Web Sockets are a way to make a *bidirectional* connection between a client and a server. One common implementation of web sockets are based off the [publish-subscribe pattern](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern). One common library that JavaScript uses for WebSockets is [socket.io](http://socket.io/). Unfortunately, this mainly being a Processing project, we must find another implementation.

I fired up Google and found [this helpful StackOverflow post](http://stackoverflow.com/questions/18900187/processing-how-to-send-data-through-websockets-to-javascript-application). Seems like Java WebSockets can do the trick but this seems a bit "low-level" for our purposes. But wait! Looks like there is a WebSockets library written in Processing called *spacebrew* which wraps the java_websocket library into its own little protocol. Hmm, seems a bit similar to how Socket.IO works. What's better yet, it looks like spacebrew has two implementations, one in Processing and another in Javascript! Sweet :)

I ran through the basic tutorials [here](http://docs.spacebrew.cc/tutorials/2015/10/12/basics-spacebrew-processing) and [here](http://docs.spacebrew.cc/tutorials/2014/10/21/basics-spacebrew-javasript) dealing with using Spacebrew with Processing and with browser-based Javascript. I highly recommend everyone to try it out, it's neat and the Spacebrew frontend GUI is awesome. Just note that Spacebrew's socket server is written in Node so to run it locally, you need to install NodeJS (they do provide a cloud-based testing sandbox hosted on Amazon EC2 servers, for demo purposes). I won't go into great detail because all the tutorials and documentation is written very accessibly on their own site. 

After having it all linked up, you are now able to control your Processing app through the web, or through another Processing app. It seems like voodoo magic (yes, websockets are like voodoo magic). 

https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API



Professor Moon noted that the heavy lifting should be done in Processing. 
