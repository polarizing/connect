
public class AnimationController {
	
	String animation;
	String direction;
	String callback;
	Object callbackScope;
	float delta;
	boolean isAnimating;
	
	
	public AnimationController (Object scope, String animation, String direction, float delta, String cb){
		this.animation = animation; 
		this.direction = direction;
		this.delta = delta;
		this.isAnimating = true;
		this.callback = cb;
		this.callbackScope = scope;
	}
	
	public Object getCallbackScope () {
		return callbackScope;
	}
	
	public void setCallback(String cb) {
		this.callback = cb;
	}
	
	public String getCallback() {
		return this.callback;
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
