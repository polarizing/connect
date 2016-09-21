import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GridAnimator {
	
	private PApplet parent;
	private Grid grid;
	ConnectServer server;
	ArrayList<AnimationController> controllers = new ArrayList<AnimationController>();
	
	private float slideInRight;
	private float slideInTop;
	private float slideOutRight;
	
	public GridAnimator (PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.grid = this.server.grid;
		this.slideInRight = this.grid.getRightOffset();
		this.slideInTop = this.grid.getTopOffset();
		this.slideOutRight = this.grid.getRightOffset();
	}
	
	public GridAnimator setGrid (Grid g) {
		this.grid = g;
		return this;
	}
	
	public void slideIn(Object scope, String direction, float delta, String cb) {
		
		if (direction == "right") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInRight = this.slideInRight += delta;
		}
		
		if (direction == "top") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInTop = this.slideInTop += delta;
		}
	}
	
	public void slideOut(Object scope, String direction, float delta, String cb) {
		
		if (direction == "right") {
			AnimationController ctrl = new AnimationController(scope, "slideOut", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideOutRight = this.slideOutRight -= delta;
		}
		
	}
	
	public void runAnimations () {
		
		for (int ctrlIdx = 0; ctrlIdx < this.controllers.size(); ctrlIdx++ ) {
			AnimationController controller = this.controllers.get(ctrlIdx);
			// Animating
			
			if (!controller.isDone()) {
				
//				String animation = controller.getAnimation();
//				switch(animation) {
//					case "slideIn": String direction = controller.getDirection();
//									switch(direction) {
//										case "right": 
//									}
//									break;
//				}
//				
				if (controller.getAnimation() == "slideIn") {
					if (controller.getDirection() == "right") {
						parent.println("running slideinright animation");
						if (this.slideInRight - this.grid.getRightOffset() > 0.1) {
							float currRightOffset = this.grid.getRightOffset();
							float lerpOffset = (this.slideInRight - currRightOffset) / 5; // 5 is speed
							// Adjust grid
							this.grid.setRightOffset(currRightOffset + lerpOffset);
						} 
						else {
							controller.endAnimation();
						}
					}
					else if (controller.getDirection() == "top") {
						parent.println("running slideinright animation");
						if (this.slideInTop - this.grid.getTopOffset() > 0.1) {
							float currTopOffset = this.grid.getTopOffset();
							float lerpOffset = (this.slideInTop - currTopOffset) / 5; // 5 is speed
							// Adjust grid
							this.grid.setTopOffset(currTopOffset + lerpOffset);
						} 
						else {
							controller.endAnimation();
						}
					}
				}
				
				
				if (controller.getAnimation() == "slideOut") {
//					float correctOffset = this.grid.getRightOffset() - this.slideOutRight;
//					parent.println(correctOffset);
					if (Math.abs(this.slideOutRight - this.grid.getRightOffset()) > 0.1) {
						float currRightOffset = this.grid.getRightOffset();
						float lerpOffset = (this.slideOutRight - currRightOffset) / 5;
						// Adjust grid
						this.grid.setRightOffset(currRightOffset - lerpOffset);
					}
					else {
						controller.endAnimation();
					}
				}
				
				
				
			}
			
			// Done With Animation
			else if (controller.isDone()){
				this.controllers.remove(ctrlIdx);
				
				this.slideInRight = 0;
				this.slideInTop = 0;
				this.slideOutRight = 0;
				
				Callback.invoke(controller.getCallbackScope(), controller.getCallback());

//				if (controller.getAnimation() == "slideIn") {
//
//					this.server.clients.add(new Client(parent, this.server.clients.size()));
//					this.server.grid.setColumnPartitions(++this.server.numColumns);
//					server.numClients++;
//					this.controllers.remove(ctrlIdx);
//				}

			}
		}
	}
	
	
}
