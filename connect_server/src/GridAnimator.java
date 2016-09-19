import processing.core.PApplet;
import java.util.ArrayList;

public class GridAnimator {
	
	private PApplet parent;
	private GridHelper grid;
	ConnectServer server;
	ArrayList<AnimationController> controllers = new ArrayList<AnimationController>();
	
	public GridAnimator (PApplet p) {
		this.parent = p;
		this.server = (ConnectServer) p;
		this.grid = this.server.grid;
	}
	
	public void slideIn(String direction, float delta, float time) {
		parent.println(this.parent.frameCount);
		AnimationController ctrl = new AnimationController("slideIn", this.parent.frameCount, direction, delta, time);
		this.controllers.add(ctrl);
	}
	
	public void runAnimations () {
//		parent.println(this.controllers);
		
		for (int ctrlIdx = 0; ctrlIdx < this.controllers.size(); ctrlIdx++ ) {
			AnimationController controller = this.controllers.get(ctrlIdx);
			// Animating
			if (controller.isReady()) {
				if (controller.getAnimation() == "slideIn") {
					int currRightOffset = this.grid.getRightOffset();
					this.grid.setRightOffset(currRightOffset + 2);
					controller.incrementFrameCount();
				}
			}
			// Done With Animation
			else if (controller.isDone()){
				this.server.grid.setOffsets(new int[]{30, 30, 30, 30});

				// Change into callback??
//				controller.onAnimationEnd(this.server);
				
				this.server.clients.add(new Client(parent, this.server.clients.size()));
				this.server.grid.setColumnPartitions(++this.server.numColumns);
				server.numClients++;
				parent.println("getting in here");
				this.controllers.remove(ctrlIdx);
			}
		}
	}
	
	
}
