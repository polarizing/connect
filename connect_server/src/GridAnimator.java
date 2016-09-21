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
	
	public GridAnimator (PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.grid = this.server.grid;
		this.slideInRight = this.grid.getRightOffset();
		this.slideInTop = this.grid.getTopOffset();
	}
	
	public GridAnimator setGrid (Grid g) {
		this.grid = g;
		return this;
	}
	
	public void slideIn(String direction, float delta) {
		
		if (direction == "right") {
			AnimationController ctrl = new AnimationController("slideIn", direction, delta);
			this.controllers.add(ctrl);
			this.slideInRight = this.slideInRight += delta;
		}
		
		if (direction == "top") {
			AnimationController ctrl = new AnimationController("slideIn", direction, delta);
			this.controllers.add(ctrl);
			this.slideInTop = this.slideInTop += delta;
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
			}
			
			// Done With Animation
			else if (controller.isDone()){
				this.slideInRight = 0;
				this.slideInTop = 0;
				// restore original grid **fix**
				this.server.grid.setOffsets(new int[]{30, 30, 30, 30});
				this.server.grid.setColumnPartitions(++this.server.numColumns);
				this.controllers.remove(ctrlIdx);
//				this.slideIn("right", 200);

				// callback specific stuff
				ArrayList<GridContainer> containers = this.server.grid.getFullColumnContainers();
				Grid g = new Grid(this.parent, containers.get(containers.size() - 1)).setPartitions(new int[] {0, 4});
				Client c = new Client(this.parent, this.server.clients.size(), g, 3);
				this.server.clients.add(c);
				
				ArrayList<PVector> pts = g.getMiddlePartitionPoints();
				for (int i = 0; i < c.getNumTriggers(); i++) {
					int id = this.server.triggers.size() + i;
					int triggerColor = this.parent.color(this.parent.random(255), this.parent.random(255), this.parent.random(255));
					Trigger t = new Trigger(this.parent, id, pts.get(i), triggerColor);
					this.server.triggers.add(t);
					c.addTrigger(t);
				}
				
				this.server.numClients++;


//				if (controller.getAnimation() == "slideIn") {
//
//					this.server.clients.add(new Client(parent, this.server.clients.size()));
//					this.server.grid.setColumnPartitions(++this.server.numColumns);
//					server.numClients++;
//					this.controllers.remove(ctrlIdx);
//				}

			}
		}
		
//		
//		if (Math.abs(this.grid.getRightOffset() - this.slideInRight) > 0.5) {
//			
//			parent.println(this.grid.getRightOffset());
//			parent.println(this.slideInRight);
//			parent.println("diff" + (this.grid.getRightOffset() - this.slideInRight));
//			
//			float currRightOffset = this.grid.getRightOffset();
//			float lerpOffset = (this.slideInRight - currRightOffset) / 5;
//			
//			// Adjust grid
//			this.grid.setRightOffset(currRightOffset + lerpOffset);
//			
//		} else {
//			// Restore grid to original position
//			this.server.grid.setOffsets(new int[]{30, 30, 30, 30});
//		}

//		for (int ctrlIdx = 0; ctrlIdx < this.controllers.size(); ctrlIdx++ ) {
//			AnimationController controller = this.controllers.get(ctrlIdx);
//			// Animating
//			
//			if (controller.isReady()) {
//				if (controller.getAnimation() == "slideIn") {
//					float currRightOffset = this.grid.getRightOffset();
//					float constantChange = controller.getDelta() / controller.getTotalFrameCount();
//					this.grid.setRightOffset(currRightOffset + constantChange);
//					controller.incrementFrameCount();
//				}
//			}
//			// Done With Animation
//			else if (controller.isDone()){
//				// restore original grid **fix**
//				this.server.grid.setOffsets(new int[]{30, 30, 30, 30});
//				
//				// Change into callback??
////				controller.onAnimationEnd(this.server);
//				
//				this.server.clients.add(new Client(parent, this.server.clients.size()));
//				this.server.grid.setColumnPartitions(++this.server.numColumns);
//				server.numClients++;
//				parent.println("getting in here");
//				this.controllers.remove(ctrlIdx);
//			}
//		}
	}
	
	
}
