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
	
	public GridAnimator (PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.grid = this.server.grid;
		this.slideInTop = 0;
		this.slideInRight = 0;
		this.slideInBottom = 0;
		this.slideInLeft = 0;		
	}
	
	public GridAnimator setGrid (Grid g) {
		this.grid = g;
		return this;
	}
	
	public void slideIn(Object scope, String direction, float delta, String cb) {
		parent.println("sliding");
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
							float lerpOffset = (this.slideInTop - currTopOffset) / 10; // 5 is speed
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
							float lerpOffset = ( this.slideInRight - currRightOffset) / 10; // 4 is speed
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
								float lerpOffset = (this.slideInBottom - currBottomOffset) / 10; // 4 is speed
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
								float lerpOffset = (this.slideInLeft - currLeftOffset) / 10; // 4 is speed
								// Adjust grid
								this.grid.setLeftOffset(currLeftOffset + lerpOffset);
							} 
							else {
								this.slideInLeft -= controller.getDelta();
								controller.endAnimation();
							}
					 }
					
				}
				
				
			}
			
			// Done With Animation
			else if (controller.isDone()){

				this.controllers.remove(ctrlIdx);
				parent.println("Done with animation.");
				
				Callback.invoke(controller.getCallbackScope(), controller.getCallback());


			}
		}
	}
	
	
}
