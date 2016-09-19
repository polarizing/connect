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

I think I've had enough of playing around with WebSockets today. I am going to look at a few frontend GUI's for Processing. I was inspired by Professor Moon's use in his [Thread Screen](https://www.youtube.com/watch?v=dvDHNDkO-Qo) project written in OpenFrameworks. I am trying to find a similar library to ofxGUI.

It looks like [ControlP5](https://github.com/sojamo/controlp5) can do the trick, the modules look [sleek](http://wiki.unity3d.com/images/8/87/ControlP5.png).

Got sidetracked a little bit ... read into Processing in Eclipse and I think it is very useful for the size of application we are building here. It seems as if the Processing editor is well-suited for building smaller sketches, not full-scale applications. Furthermore, doing OOP in Processing is a bit weird as all classes are treated as "inner classes," which extend PApplet. I can call rect() and draw onto the main window in any class in Processing -- seems messy and easy to lose control with lots of classes (also what about utility classes that are not used for drawing?). Anyways, I think I will run through the tutorial to use Eclipse (I think it will save me time in the long run). Basically, we are going to strip away from using the Processing IDE and what it does for us in the background. I can actually take this time to learn more Java syntax.

### September 17, 2016
Did a lot of coding over the weekend. I am starting to lay out the basic framework for the application. I started off by creating a main class following the tutorial [here](https://processing.org/tutorials/eclipse/). I named this main class ConnectServer (as it will function as the host / server for clients to connect and for the main visualization). There are two familiar functions that we have to implement, **setup()** and **draw()** and one more unfamiliar function called **settings()**. This is unique to using Processing in Eclipse.

> settings() function is necessary when using Processing code outside of the Processing Development Environment (PDE)
> Processing Documentation

Apparently, no other explanation is given as to why but we need to define functions like size(), smooth() or fullScreen() in here instead of in setup() as we do normally.

Our application now looks like this.

```java
import processing.core.PApplet;

public class ConnectServer extends PApplet{
  public void settings() {
  }
  public void setup() {
  }
  public void draw() {
  }
  public static void main(String[] args) {
    PApplet.main(new String[] {"ConnectServer"});
  }
}
```

Great! The main method does the following. It starts a PApplet application, and tells it to use this class, ConnectIO, as the program to run.
- This is done by calling PApplet's main method and giving it the name of this class as a parameter.
- The class constructor is called first, then settings(), setup(), and the draw() function are called in that respective order.

I went ahead and set the size of the canvas as well as making it resizable and a commented-out "fullScreen()" in case I needed to test my application in full-screen.

```java
  // in settings()
  size(900, 600);
  // fullScreen();
  
  // in setup()
  this.surface.setResizable(true);
```

I'm also going to go ahead and attach the ControlP5 GUI to my application. I decided to test this out early on because I feel it is an useful tool (not only as an end-product) but also for debugging purposes.

I downloaded controlP5.jar and included it as a library to my project build path. 





https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API



Professor Moon noted that the heavy lifting should be done in Processing. 
