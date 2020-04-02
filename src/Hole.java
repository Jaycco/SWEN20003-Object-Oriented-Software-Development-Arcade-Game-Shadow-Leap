import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hole extends Sprite {
	private static Image imageHole;
	private boolean appear = false;
	
	private static final String HOLE_PATH = "assets/frog.png";
	
	public static Hole createHoleTile(float x, float y) {
		try {
			imageHole = new Image(HOLE_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return new Hole(HOLE_PATH, x, y, new String[] { Sprite.HOLE });
	}
	
	private Hole(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}
	
	public void render() {
		if (appear) {
			imageHole.drawCentered(getX(), getY());
		}
	}
	public final boolean getAppear() { return appear; }
	public final void setAppear(boolean appear) { this.appear = appear; }
}

