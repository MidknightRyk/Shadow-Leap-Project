import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public abstract class Sprite {
	
	
	
	//Tag constants
    public final static String DANGER = "danger";
    public final static String BLOCKED = "blocked";
    public final static String HEALTH = "health";
    public final static String PLATFORM = "platform";
    public final static String SOLID = "solid";
    public final static String LOG = "log";
    public final static String NULL = "tester";
    
    protected static float nextLifePos;
    
    //Health bar Stats
    private final static int LIVES_POS_X = 24;
    private final static int LIVES_POS_Y = 744;
    public final static int LIVES_SPACE = 32;
    public final static int LIVES_NUM = 3;
    public static int LIVES_LEFT;
    
    //ExtraLife Stuff
    private static float curDelta = 0;
    private final static int miliSec = 1000;
	public static float spawnX;
	public static float spawnY;
	
    //Goals needed to fill to win Level
    private final static int goalWin = 5;
    
    protected BoundingBox bound;
	private Image image;
	private float x;
	private float y;
	public static boolean rideOn = false;
	
	private String[] tags;
	
	public Sprite(String imageSrc, float x, float y){
		makeSprite(imageSrc, x, y);
	}
	
	public Sprite(String imageSrc, float x, float y, String[] tag){
		makeSprite(imageSrc, x, y);
		tags = tag;
	}
	
	private void makeSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		
		bound = new BoundingBox(image, (int)x, (int)y);
		
		tags = new String[0];
	}
	
	public final void setX(float x) { this.x = x; bound.setX((int)x); }
	
	public final void setY(float y) { this.y = y; bound.setY((int)y); }
	
	public final float getX() {return x;}
	
	public final float getY() {return y;}
	
	public final void reset(float x, float y) { setX(x); setY(y); }
	
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}
	
	public void update(Input input, int delta) {
	}
	
	public void render() {
		image.drawCentered(x, y);
	}
	
	//Renders HUD mechanics: Lifebar
	public static void renderHud() {
		//Player Lifebar Setup
		Image lives = null;
		try {
			lives = new Image("assets/lives.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		float j = LIVES_POS_X;
		for (int i=0; i < LIVES_LEFT; i++) {
			lives.drawCentered(j, (float)LIVES_POS_Y);
			Sprite.nextLifePos = i;
			j += LIVES_SPACE;
		}
	}
	
	public final boolean collisionCheck(Sprite other) {
		return bound.intersects(other.bound);
	}
	
	public void collisionAction(Sprite other) {}
	
	//Checks for Sprite Tags
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkSpawnTime(float delta) {
		curDelta += delta;
		System.out.print("current time is: ");
		System.out.println(curDelta);
		
		//Determines when to spawn Extra Life
		if(World.newSpawn) {
			World.newSpawn = false;
			curDelta = 0;
		}
		if(curDelta >= Level.spawnTime*miliSec) {
			Level.RNG();
			curDelta = 0;
			return true;
		}
		return false;
	}
	
	public static PlayerLives spawnLife(){
		int logNum = 0;
		
		//Finds the log to spawn Extra Life on
		for(Sprite sprites: World.sprites) {
			if(sprites.hasTag(LOG)) {
				logNum++;
			}
			if(logNum == Level.logNum) {
				spawnX = sprites.getX();
				spawnY = sprites.getY();
			}
		}
		
		return PlayerLives.makeExtraLife(spawnX, spawnY);	
	}
	
	//Called when Player encounters Enemy Sprite
	public void deathAction() {
		LIVES_LEFT -= 1;
		PlayerLives.setLife(true);
		reset(App.SCREEN_WIDTH/2, App.SCREEN_HEIGHT-Level.TILE_SIZE);
	}
	
	//Called when Player reaches an Empty goal
	public void goalAction (float goalStart, float goalEnd) {
		//Fills the goal with an enemy sprite so Player may not encounter the same gaol twice
		World.goals.add(Tile.fillGoal(goalStart + (goalEnd-goalStart)/2, Level.TREES));
		
		if (World.goals.size() == goalWin) {
			World.win = true;
			World.newSpawn = true;
		}
	}
	
}
