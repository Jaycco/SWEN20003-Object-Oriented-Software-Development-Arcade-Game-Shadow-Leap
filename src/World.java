import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	public static final int TILE_SIZE = 48;
	public static final int NUMBER_OF_HOLES = 5;
	public static final int LOG_SIZE = 48 * 3;
	public static final int TURTLES_SIZE = 48 * 3;
	public static final int LONGLOG_SIZE = 48 * 5;
	public static final int LIVE_INI_X = 24;
	public static final int LIVE_INI_Y = 744;
	public static final int LIVE_GAP_X = 32;
	public static final int HOLE_FIRST_X = (int) (48 * 2.5);
	public static final int HOLE_FIRST_Y = 48;
	public static final int INITIAL_LIVES = 3;
	public static final int EXTRA_LIFE_LOWER_TIME = 25;
	public static final int EXTRA_LIFE_UPPER_TIME = 35;
	
	private Image livesImage;
	private static final String LIVES_PATH = "assets/lives.png";
	private static ArrayList<Sprite> sprites = new ArrayList<>();
	private static int lives = INITIAL_LIVES;
	private static int numberLog;
	private static int level;
	private static int extraOnLog;
	static float randomSecond;
				
	
	
	public World() {
		// initialize attribute for extra life
		numberLog = 0;
		randomSecond = ((float)getRandomNumber() * (EXTRA_LIFE_UPPER_TIME - EXTRA_LIFE_LOWER_TIME) + EXTRA_LIFE_LOWER_TIME) * 1000;
		// read file and create several sprites
		try {
			livesImage = new Image(LIVES_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = 
				new BufferedReader(new FileReader("assets/levels/"+Integer.toString(0)+".lvl"))) {
			String text;
			while ((text = br.readLine()) != null) {
				String[] columns = text.split(",");
				int x = Integer.parseInt(columns[1]);
				int y =	Integer.parseInt(columns[2]);
		// create tiles
				if (columns[0].equals("water")) {
					sprites.add(Tile.createWaterTile(x, y));
				} else if (columns[0].equals("grass")) {
					sprites.add(Tile.createGrassTile(x, y));
				} else if (columns[0] .equals("tree")) {
					sprites.add(Tile.createTreeTile(x, y));
				}
		// create vehicles
				if (columns[0].equals("bike")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Bike(x, y, moveRight));
				} else if (columns[0].equals("racecar")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Racecar(x, y, moveRight));
				} else if (columns[0].equals("bus")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Vehicle(x, y, moveRight));
				} else if (columns[0].equals("bulldozer")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Bulldozer(x, y, moveRight));
		// things on water
				} else if (columns[0].equals("log")) {
					numberLog = numberLog + 1;
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Log(x, y, moveRight));
				} else if (columns[0].equals("turtle")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Turtles(x, y, moveRight));
				} else if (columns[0].equals("longLog")) {
					numberLog = numberLog + 1;
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Longlog(x, y, moveRight));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// create hole
		int holeX = HOLE_FIRST_X;
		for(int i = 0; i < NUMBER_OF_HOLES; i++) {
			sprites.add(Hole.createHoleTile(holeX, HOLE_FIRST_Y));
			holeX = holeX + TILE_SIZE * 4;
		}
		
		
		// create player
		sprites.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - TILE_SIZE));
		
		// number of log, and random chooses one of the logs
		extraOnLog = (int)(getRandomNumber() * (numberLog));
				
		// create extra life
		int count = 0;
		for (Sprite sprite2: sprites) {
			if (sprite2.hasTag(Sprite.LOG)) {
				if (count == extraOnLog) {
					sprites.add(new Extra(sprite2.getX(), sprite2.getY(), randomSecond, true, sprite2.getImageSize() - TILE_SIZE));
					break;
				}
				count += 1;
			}
		}
	}
	
	public void resetWorld(int level) {
		// initialize attribute for extra life
		numberLog = 0;
		randomSecond = ((float)getRandomNumber() * (EXTRA_LIFE_UPPER_TIME - EXTRA_LIFE_LOWER_TIME) + EXTRA_LIFE_LOWER_TIME) * 1000;
		// read file and create several sprites
		try {
			livesImage = new Image(LIVES_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = 
				new BufferedReader(new FileReader("assets/levels/"+Integer.toString(level)+".lvl"))) {
			String text;
			while ((text = br.readLine()) != null) {
				String[] columns = text.split(",");
				int x = Integer.parseInt(columns[1]);
				int y =	Integer.parseInt(columns[2]);
		// create tiles
				if (columns[0].equals("water")) {
					sprites.add(Tile.createWaterTile(x, y));
				} else if (columns[0].equals("grass")) {
					sprites.add(Tile.createGrassTile(x, y));
				} else if (columns[0] .equals("tree")) {
					sprites.add(Tile.createTreeTile(x, y));
				}
		// create vehicles
				if (columns[0].equals("bike")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Bike(x, y, moveRight));
				} else if (columns[0].equals("racecar")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Racecar(x, y, moveRight));
				} else if (columns[0].equals("bus")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Vehicle(x, y, moveRight));
				} else if (columns[0].equals("bulldozer")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Bulldozer(x, y, moveRight));
		// things on water
				} else if (columns[0].equals("log")) {
					numberLog = numberLog + 1;
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Log(x, y, moveRight));
				} else if (columns[0].equals("turtle")) {
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Turtles(x, y, moveRight));
				} else if (columns[0].equals("longLog")) {
					numberLog = numberLog + 1;
					boolean moveRight = Boolean.parseBoolean(columns[3]);
					sprites.add(new Longlog(x, y, moveRight));
				}
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// create hole
		int holeX = HOLE_FIRST_X;
		for(int i = 0; i < NUMBER_OF_HOLES; i++) {
			sprites.add(Hole.createHoleTile(holeX, HOLE_FIRST_Y));
			holeX = holeX + TILE_SIZE * 4;
		}
		
		
		// create player
		sprites.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - TILE_SIZE));
		
		// number of log, and random chooses one of the logs
		extraOnLog = (int)(getRandomNumber() * (numberLog));
				
		// create extra life
		int count = 0;
		for (Sprite sprite2: sprites) {
			if (sprite2.hasTag(Sprite.LOG)) {
				if (count == extraOnLog) {
					sprites.add(new Extra(sprite2.getX(), sprite2.getY(), randomSecond, true, sprite2.getImageSize() - TILE_SIZE));
					break;
				}
				count += 1;
			}
		}
	}
	
	
	
	public void update(Input input, int delta) {
		// update sprites, code from sample
		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
		}
		
		// loop over all pairs of sprites and test for intersection, code from sample
		for (Sprite sprite1: sprites) {
			for (Sprite sprite2: sprites) {
				if (sprite1 != sprite2
						&& sprite1.collides(sprite2)) {
					sprite1.onCollision(sprite2);
				}
			}
		}
		
		// check death for reduce lives and number of holes already accessed
		int countFullHole = 0;
		for (Sprite sprite1: sprites) {
			if (sprite1.hasTag(Sprite.PLAYER)) {
				sprite1.checkDeath();
			}
			if (sprite1.hasTag(Sprite.HOLE) && sprite1.getAppear()){
				countFullHole += 1;
				if (countFullHole == NUMBER_OF_HOLES) {
					if(level == 1) {
						System.exit(0);
					} else {
						sprites.clear();
						resetWorld(1);
						level = 1;
						break;
					}
				}
			}
		}
		
	
		
		// move extra life with log
		for (Sprite sprite1: sprites) {
			if (sprite1.hasTag(Sprite.EXTRALIFE)) {
				int count = 0;
				for (Sprite sprite2: sprites) {
					if (sprite2.hasTag(Sprite.LOG)) {
						if (count == extraOnLog) {
							sprite1.move(sprite2.getLastX(), 0);
							break;
						}
						count += 1;
					}
				}
				break;
			}
		}
		// check lives for game over
		if (lives < 0) {
			System.exit(0);
		}
		
	}
	
	//code from sample
	public void render(Graphics g) {
		for (int i = 0; i < lives; i++ ) {
			livesImage.drawCentered(LIVE_INI_X + i * LIVE_GAP_X, LIVE_INI_Y);
		}
		for (Sprite sprite : sprites) {
			sprite.render();
		}
	}
	
	
	
	
	// get a random number from 0 to 1
	public static double getRandomNumber(){
	    double r = Math.random();
	    return r;
	}
	
	//  reset extra life
	public static void resetExtraLife(){
		randomSecond = ((float)getRandomNumber() * (EXTRA_LIFE_UPPER_TIME - EXTRA_LIFE_LOWER_TIME) + EXTRA_LIFE_LOWER_TIME) * 1000;
		extraOnLog = (int)(getRandomNumber() * (numberLog));
		for (Sprite sprite1: sprites) {
			if (sprite1.hasTag(Sprite.EXTRALIFE)) {
				int count = 0;
				for (Sprite sprite2: sprites) {
					if (sprite2.hasTag(Sprite.LOG)) {
						if (count == extraOnLog) {
							sprite1.reset(sprite2.getX(), sprite2.getY(), randomSecond, sprite2.getImageSize() - TILE_SIZE);
							break;
						}
						count += 1;
					}
				}
				break;
			}
			
		}
	}
	
	public final static void loseLife() { lives -= 1;}
	public final static void gainLife() { lives += 1;}
	
}
