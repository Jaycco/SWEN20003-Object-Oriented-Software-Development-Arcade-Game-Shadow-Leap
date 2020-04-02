import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Extra extends Sprite {
	public static final int EXTRA_LIFE_APPEAR_TIME = 14 * 1000;
	public static final int EXTRA_LIFE_MOVE_SPEED = 2 * 1000;
	private float moveOnLog = 0;
	private float time = 0;
	private float randomSecond = 0;
	
	
	private static final String ASSET_PATH = "assets/extralife.png";
	
	private boolean moveRight;
	private Image image;
	private int logLength;
	private float moveTime = 0;
	private boolean appear = false;
	
	public Extra(float x, float y, float randomSecond, boolean moveRight, int logLength) {
		super(ASSET_PATH, x, y, new String[] { Sprite.EXTRALIFE });
		
		this.moveRight = moveRight;
		this.randomSecond = randomSecond;
		this.logLength = logLength;
		try {
			image = new Image(ASSET_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Input input, int delta) {
		
	// update of appearance
		time = time + delta;
		if (!appear) {
			if (time >= randomSecond) {
				appear = true;
				time = time - randomSecond;
			}
		} else {
			if (time >= EXTRA_LIFE_APPEAR_TIME) {
				appear = false;
				time = time - EXTRA_LIFE_APPEAR_TIME;
				World.resetExtraLife();
			}
		}
	// update of moving on log
		if (appear) {
			moveTime += delta;
			if (moveTime >= EXTRA_LIFE_MOVE_SPEED) {
				if (moveOnLog <= -(logLength / 2)) {
					moveRight = true;
				} else if (moveOnLog >= (logLength / 2)) {
					moveRight = false;
				}
				moveTime -= EXTRA_LIFE_MOVE_SPEED;
				move(World.TILE_SIZE * (moveRight ? 1 : -1), 0);
				moveOnLog += World.TILE_SIZE * (moveRight ? 1 : -1);
			}
		}
		
		
	}
	
	public void render() {
		if (appear) {
			image.drawCentered(getX(), getY());
		}
	}
	
	// reset extra
	public void reset(float x, float y, float randomSecond, int logLength){
		setX(x);
		setY(y);
		this.randomSecond = randomSecond;
		this.logLength = logLength;
		moveTime = 0;
		appear = false;
		moveRight = true;
		moveOnLog = 0;
		time = 0;
	}
	
	public final boolean getAppear() { 
		return appear; 
	}
}
