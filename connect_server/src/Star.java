import processing.core.PApplet;
import processing.core.PVector;

public class Star {

	private PApplet parent;
	private ConnectServer server;
	
	private PVector centerPos;
	private float triHeight, triBase;
	private int h, s, b;
	
	private boolean fadeIn;
	private int fadeInOutFrame;
	private int fadeInOutSpeed;
	
	private boolean pulseIn;
	private int pulseInOutFrame;
	private int pulseInOutSpeed;
	private float pulseScaleFrom;
	private float pulseScaleTo;
  
  Star (PApplet p, PVector center, float h, float b) {
		this.parent = p;
		this.server = (ConnectServer) p;

		this.centerPos = center;
		this.triHeight = h;
		this.triBase = b;
		this.fadeIn = true;
		this.fadeInOutFrame = 0;
		this.pulseIn = true;
		this.pulseInOutFrame = 0;
  }
  
  void setColor (int hue, int saturation, int brightness) {
    this.h = hue;
    this.s = saturation;
    this.b = brightness;
  }
  
  void setStartingFadeInOutFrame (int startFrame) {
    this.fadeInOutFrame = startFrame;
  }
  
  void setStartingPulseInOutFrame (int startFrame) {
    this.pulseInOutFrame = startFrame; 
  }
  
  void setFadeInOutSpeed (int speed) {
    this.fadeInOutSpeed = speed;
  }
  
  void setPulseInOutSpeed (int speed) {
	  this.pulseInOutSpeed = speed;
  }
  
  void setPulseScaleFrom (float val) {
	  this.pulseScaleFrom = val;
  }
  
  void setPulseScaleTo (float val) {
	  this.pulseScaleTo = val;
  }
  
  void fadeInOut () {
	  this.fadeInOutFrame += this.fadeInOutSpeed;
    if (this.fadeInOutFrame % 255 < this.fadeInOutSpeed ) this.fadeIn = !this.fadeIn;
    if (this.fadeIn) this.parent.fill(this.h, this.s, this.fadeInOutFrame % 255);
    else if (!this.fadeIn) this.parent.fill(this.h, this.s, this.b - (this.fadeInOutFrame % 255));
  }
  
  void pulseInOut () {
	  this.pulseInOutFrame += this.pulseInOutSpeed;
    float totalPulseFrame = (float) 1000.0;
    float currPulseFrame = this.pulseInOutFrame % totalPulseFrame;
    float scaleRatio = totalPulseFrame / (this.pulseScaleTo - this.pulseScaleFrom) ;
    if (currPulseFrame < this.pulseInOutSpeed ) this.pulseIn = !this.pulseIn; // one loop of scale in 
    if (this.pulseIn) {
      this.parent.scale( (currPulseFrame / scaleRatio) + this.pulseScaleFrom, (currPulseFrame / scaleRatio)  + this.pulseScaleFrom);
    }
    else if (!pulseIn) {
      this.parent.scale( ((totalPulseFrame - currPulseFrame) / scaleRatio) + this.pulseScaleFrom, ((totalPulseFrame - currPulseFrame) / scaleRatio) + this.pulseScaleFrom );
    }
  }
  
  void drawStar () {
    for (int angle = 0; angle < 360; angle += 90) {
      this.parent.pushMatrix();
      this.parent.translate( centerPos.x, centerPos.y );
      this.parent.rotate( this.parent.radians(angle));
      pulseInOut();
      this.parent.triangle(0, -this.triHeight, -this.triBase, 0, this.triBase, 0);
      this.parent.popMatrix();
   }
  }
  
  void display () {
        this.parent.colorMode(this.parent.HSB);
        this.parent.noStroke();
        //fadeInOut();
        drawStar();
        this.parent.stroke(1);
       this.parent.colorMode(this.parent.RGB);
  }
}