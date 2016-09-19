
public class AnimationController {
	
	String animation;
	String direction;
	float delta;
	float time;
	int frameCount;
	int startingFrame;
	int currentFrame;
	int endingFrame;
	
	public AnimationController (String animation, int frameCount, String direction, float delta, float time){
		this.animation = animation;
		this.direction = direction;
		this.delta = delta;
		this.time = time;
		this.frameCount = frameCount;
		this.startingFrame = frameCount;
		this.currentFrame = frameCount;
		this.endingFrame = (int) (frameCount + (this.time * 30));
	}
	
	public boolean isReady () {
		return this.currentFrame < this.endingFrame;
	}
	
	public String getAnimation() {
		return this.animation;
	}
	
	public void incrementFrameCount () {
		this.currentFrame += 1;
	}
	
	
	
}
