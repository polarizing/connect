import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GridAnimator {
	
	private PApplet parent;
	private Grid grid;
	ConnectServer server;
	ArrayList<AnimationController> controllers = new ArrayList<AnimationController>();
	
	private float slideInTop;
	private float slideInRight;
	private float slideInBottom;
	private float slideInLeft;
	
	private float slideOutRight;
	
	
	public GridAnimator (PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.grid = this.server.grid;
		this.slideInRight = this.grid.getRightOffset();
		this.slideInTop = 0;
		this.slideInRight = 0;
		this.slideInBottom = 0;
		this.slideInLeft = 0;
		
		this.slideOutRight = 0;
		
	}
	
	public GridAnimator setGrid (Grid g) {
		this.grid = g;
		return this;
	}
	
	public void slideIn(Object scope, String direction, float delta, String cb) {
		if (direction == "top") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInTop = this.slideInTop += delta;
		}
		if (direction == "right") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInRight = this.slideInRight += delta;
		}
		if (direction == "bottom") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInBottom = this.slideInBottom += delta;
		}
		if (direction == "left") {
			AnimationController ctrl = new AnimationController(scope, "slideIn", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideInLeft = this.slideInLeft += delta;
		}
	}
	
	public void slideOut(Object scope, String direction, float delta, String cb) {
		
		if (direction == "right") {
			AnimationController ctrl = new AnimationController(scope, "slideOut", direction, delta, cb);
			this.controllers.add(ctrl);
			this.slideOutRight = this.slideOutRight += delta;
		}
		
	}
	
	public void runAnimations () {
		
		for (int ctrlIdx = 0; ctrlIdx < this.controllers.size(); ctrlIdx++ ) {
			AnimationController controller = this.controllers.get(ctrlIdx);
			// Animating
			
			if (!controller.isDone()) {
				
				if (controller.getAnimation() == "slideIn") {
					 if (controller.getDirection() == "top") {
						parent.println("running slideintop animation");
						if (this.slideInTop - this.grid.getTopOffset() > 0.1) {
							float currTopOffset = this.grid.getTopOffset();
							float lerpOffset = (this.slideInTop - currTopOffset) / 5; // 5 is speed
							// Adjust grid
							this.grid.setTopOffset(currTopOffset + lerpOffset);
						} 
						else {
							this.slideInTop -= controller.getDelta();
							controller.endAnimation();
						}
					}
					 else if (controller.getDirection() == "right") {
						parent.println("running slideinright animation");
						
						if ( this.slideInRight - this.grid.getRightOffset() > 0.1) {
							float currRightOffset = this.grid.getRightOffset();
							float lerpOffset = ( this.slideInRight - currRightOffset) / 5; // 5 is speed
							parent.println(currRightOffset);
							// Adjust grid
							this.grid.setRightOffset(currRightOffset + lerpOffset);
						} 
						else {
							this.slideInRight -= controller.getDelta();
							controller.endAnimation();
						}
					}
					 else if (controller.getDirection() == "bottom") {
						 parent.println("running slideinbottom animation");
							if ( this.slideInBottom - this.grid.getBottomOffset() > 0.1) {
								float currBottomOffset = this.grid.getBottomOffset();
								float lerpOffset = (this.slideInBottom - currBottomOffset) / 5; // 5 is speed
								// Adjust grid
								this.grid.setBottomOffset(currBottomOffset + lerpOffset);
							} 
							else {
								this.slideInBottom -= controller.getDelta();
								controller.endAnimation();
							}
					 }
					 else if (controller.getDirection() == "left") {
						 parent.println("running slideinleft animation");
							if (this.slideInLeft - this.grid.getLeftOffset() > 0.1) {
								float currLeftOffset = this.grid.getLeftOffset();
								float lerpOffset = (this.slideInLeft - currLeftOffset) / 5; // 5 is speed
								// Adjust grid
								this.grid.setLeftOffset(currLeftOffset + lerpOffset);
							} 
							else {
								this.slideInLeft -= controller.getDelta();
								controller.endAnimation();
							}
					 }
					
				}
				
				
				if (controller.getAnimation() == "slideOut") {
//					float correctOffset = this.grid.getRightOffset() - this.slideOutRight;
//					parent.println(correctOffset);
					if (controller.getDirection() == "right") {
						if ( this.slideOutRight - this.grid.getRightOffset() > 0.01) {
							float currRightOffset = this.grid.getRightOffset();
							float lerpOffset = (this.slideOutRight - currRightOffset) / 5;
							// Adjust grid
//							parent.println(currRightOffset);
							this.grid.setRightOffset(currRightOffset - lerpOffset);
						}
						else {
							this.slideOutRight -= controller.getDelta();
							controller.endAnimation();
						}
					}
				}
				
				
				
			}
			
			// Done With Animation
			else if (controller.isDone()){

				this.controllers.remove(ctrlIdx);
				parent.println("Done");
//				this.slideInTop = 0;
//				this.slideOutRight = 0;
//				
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
