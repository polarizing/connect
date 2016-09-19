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
		parent.println(this.controllers);
		
		for (int ctrlIdx = 0; ctrlIdx < this.controllers.size(); ctrlIdx++ ) {
			AnimationController controller = this.controllers.get(ctrlIdx);
			if (controller.isReady()) {
				if (controller.getAnimation() == "slideIn") {
					int currRightOffset = this.grid.getRightOffset();
					this.grid.setRightOffset(currRightOffset + 1);
					controller.incrementFrameCount();
				}
			}
			else {
				parent.println("getting in here");
				controllers.remove(ctrlIdx);
			}
		}
	}
	
	
}
