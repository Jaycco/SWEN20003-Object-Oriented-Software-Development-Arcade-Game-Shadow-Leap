import org.newdawn.slick.Input;

public class Log extends Sprite {
	private static final String ASSET_PATH = "assets/log.png";
	private static final float SPEED = 0.1f;
	
	private boolean moveRight;
	
	private final float getInitialX() {
		return moveRight ? -World.LOG_SIZE / 2
						 : App.SCREEN_WIDTH + World.LOG_SIZE / 2;
	}
	
	public Log(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.RIDEABLE, Sprite.LOG });
		
		this.moveRight = moveRight;
	}
	
	@Override
	public void update(Input input, int delta) {
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		setLastX(SPEED * delta * (moveRight ? 1 : -1));
		
		// check if the vehicle has moved off the screen
		if (getX() > App.SCREEN_WIDTH + World.LOG_SIZE / 2 || getX() < -World.LOG_SIZE / 2) {
			setLastX(getInitialX() - getX());
			setX(getInitialX());
		}
	}
	
	
}
