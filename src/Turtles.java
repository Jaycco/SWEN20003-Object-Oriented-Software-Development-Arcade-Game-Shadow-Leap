import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Turtles extends Sprite {
	private static final String ASSET_PATH = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	public static final int TURTLES_BEFORE_APPEAR_TIME = 2 * 1000;
	public static final int TURTLES_BEFORE_DISAPPEAR_TIME = 7 * 1000;

	private boolean moveRight;
	private Image image;
	private float time = 0;
	private boolean appear = true;
	
	private final float getInitialX() {
		return moveRight ? -World.TURTLES_SIZE / 2
						 : App.SCREEN_WIDTH + World.TURTLES_SIZE / 2;
	}
	
	public Turtles(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.RIDEABLE, Sprite.CANAPPEAR});
		
		this.moveRight = moveRight;
		try {
			image = new Image(ASSET_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		// appear and disappear
		time = time + delta;
		if (appear) {
			if (time >= TURTLES_BEFORE_DISAPPEAR_TIME) {
				appear = false;
				time = time - TURTLES_BEFORE_DISAPPEAR_TIME;
			}
		} else {
			if (time >= TURTLES_BEFORE_APPEAR_TIME) {
				appear = true;
				time = time - TURTLES_BEFORE_APPEAR_TIME;
			}
		}
		
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		
		// check if the vehicle has moved off the screen
		if (getX() > App.SCREEN_WIDTH + World.TURTLES_SIZE / 2 || getX() < -World.TURTLES_SIZE / 2) {
			setX(getInitialX());
		}
	}
	
	public void render() {
		if (appear) {
			image.drawCentered(getX(), getY());
		}
	}
	
	public final boolean getAppear() { return appear; }
}
