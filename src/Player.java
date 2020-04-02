import org.newdawn.slick.Input;

public class Player extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";
	private float lastDx;
	private float lastDy;
	private boolean death = false;
	private float rideDif;
	
	
	public Player(float x, float y) {
		super(ASSET_PATH, x, y, new String[] { Sprite.PLAYER });
	}
	
	
	// most code from sample, (update method), lastDx, and lastDx was added
	@Override
	public void update(Input input, int delta) {
		int dx = 0,
				dy = 0;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
		}
		
		// make sure the frog stays on screen
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 	> App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		lastDx = dx;
		lastDy = dy;
		move(dx, dy);
	}
	
	@Override
	public void onCollision(Sprite other) {
		if (other.hasTag(Sprite.EXTRALIFE)) {
			if (other.getAppear()) {
				World.gainLife();
				World.resetExtraLife();
			}
		} else if (other.hasTag(Sprite.HAZARD)) {
			death = true;
		} else if (other.hasTag(Sprite.HOLE)) {
			if (other.getAppear()) {
				World.loseLife();
				setX(App.SCREEN_WIDTH / 2);
				setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
			} else {
				other.setAppear(true);
				setX(App.SCREEN_WIDTH / 2);
				setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
			}
			
		} else if (other.hasTag(Sprite.RIDEABLE)) {
		// not dead if player on turtles or log
			if (other.hasTag(Sprite.CANAPPEAR)) {
				if (other.getAppear()) {
					death = false;
				}
			} else if (other.hasTag(Sprite.LOG)) {
				death = false;
			}
			if (lastDy != 0) {
				rideDif = this.getX() - other.getX();
			} else {
				rideDif = rideDif + lastDx;
				setX(other.getX() + rideDif);
			}
			if (getX() > App.SCREEN_WIDTH - World.TILE_SIZE / 2) {
				setX(App.SCREEN_WIDTH - World.TILE_SIZE / 2);
			} else if (getX() < World.TILE_SIZE / 2) {
				setX(World.TILE_SIZE / 2);
			}
		} else if (other.hasTag(Sprite.SOLID)) {
			if (lastDy != 0) {
				move(-lastDx, -lastDy);
			} else if (other.getX() - this.getX() < 10 && other.getX() - this.getX() > -10){
				move(-lastDx, -lastDy);
				if (other.getX() - this.getX() > 0) {
					move((other.getX() - this.getX()) - 48, 0);
				} else if (other.getX() - this.getX() < 0) {
					move(48 + (other.getX() - this.getX()), 0);
				}
			} else if (other.getX() - this.getX() > 0) {
				move((other.getX() - this.getX()) - 48, 0);
			} else if (other.getX() - this.getX() < 0) {
				move(48 + (other.getX() - this.getX()), 0);
			}
			if (getX() > App.SCREEN_WIDTH - World.TILE_SIZE / 2 || getX() < World.TILE_SIZE / 2) {
				World.loseLife();
				setX(App.SCREEN_WIDTH / 2);
				setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
			}
		}
	}
	
	// if death is true then lose life and rest position
	public final void checkDeath() { 
		if (death) {
			World.loseLife();
			setX(App.SCREEN_WIDTH / 2);
			setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
			death = false;
		}
	}
}
