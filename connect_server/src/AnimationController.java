
public class AnimationController {
	
	String animation;
	String direction;
	float delta;
	boolean isAnimating;
	
	public AnimationController (String animation, String direction, float delta){
		this.animation = animation; 
		this.direction = direction;
		this.delta = delta;
		this.isAnimating = true;
	}
	
//	
	public boolean isDone () {
		return !this.isAnimating;
	}
	
	public void endAnimation() {
		this.isAnimating = false;
	}
	
	public String getAnimation() {
		return this.animation;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public float getDelta() {
		return this.delta;
	}
	
//	public float getTotalFrameCount() {
//		return this.totalFrameCount;
//	}
	
//	public void onAnimationEnd(ConnectServer server) {
//		server.clients.add(new Client(parent, this.server.clients.size()));
//	}
//	
//	public void incrementFrameCount () {
//		this.currentFrame += 1;
//	}
	
	
	
}
