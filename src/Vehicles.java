

import org.newdawn.slick.Input;


public class Vehicles extends Sprite{
	
	//Sprite asset initializations
	private final static String BUS = "assets/bus.png";
	private final static String BULLDOZER = "assets/bulldozer.png";
	private final static String LOG = "assets/log.png";
	private final static String LONG_LOG = "assets/longlog.png";
	private final static String RACECAR = "assets/racecar.png";
	private final static String BIKE = "assets/bike.png";
	private final static String BIKEPATH = "bikepath";
	
	//Movement Speed
	protected float SPEED;
	
	//Movement Direction
	protected boolean moveRight;
	
	//Bike Movement Bounds
	private final static int BOUND_1 = 24;
	private final static int BOUND_2 = 1000;
	
	
	protected final float getInitialX() {
		return moveRight ? 0 - bound.getWidth()/2
						 : App.SCREEN_WIDTH + bound.getWidth()/2;
	}
	
	public static Vehicles makeBus(float x, float y, boolean moveRight) {
		return new Vehicles(BUS, x, y, new String[] {DANGER}, moveRight, 0.15f);
	}
	
	public static Vehicles makeRacecar(float x, float y, boolean moveRight) {
		return new Vehicles(RACECAR, x, y, new String[] {DANGER}, moveRight, 0.5f);
	}
	
	public static Vehicles makeBike(float x, float y, boolean moveRight) {
		return new Vehicles(BIKE, x, y, new String[] {DANGER, BIKEPATH}, moveRight, 0.2f);
	}
	
	public static Vehicles makeBulldozer(float x, float y, boolean moveRight) {
		return new Vehicles(BULLDOZER, x, y, new String[] {BLOCKED, SOLID}, moveRight, 0.05f);
	}
	
	public static Vehicles makeLog(float x, float y, boolean moveRight) {
		Level.logTotal ++;
		return new Vehicles(LOG, x, y, new String[] {PLATFORM, LOG}, moveRight, 0.1f);
	}
	
	public static Vehicles makeLongLog(float x, float y, boolean moveRight) {
		Level.logTotal ++;
		return new Vehicles(LONG_LOG, x, y, new String[] {PLATFORM, LOG}, moveRight, 0.07f);
	}
	
	public Vehicles (String imageSrc, float x, float y, String[] tags, boolean moveRight, float Speed){
		super(imageSrc, x, y, tags);
		
		this.moveRight = moveRight;
		
		this.SPEED = Speed;
	}
		
	@Override
	public void update(Input input, int delta) {
		
		//Reverses the bikes' direction when it has reached the screen bounds
		if(hasTag(BIKEPATH)) {
			if (getX() <= BOUND_1 || getX() >= BOUND_2) {
				moveRight = (moveRight ? false : true);
			}
		}
		
		//Respawn vehicles that have gone off screen
		if (getX() > App.SCREEN_WIDTH + bound.getWidth()/2 || getX() < 0 - bound.getWidth()/2
		 || getY() > App.SCREEN_HEIGHT + bound.getHeight()/2 || getY() < 0 - bound.getHeight()/2) {
			setX(getInitialX());
		}
		
		move(SPEED * delta * (moveRight ? 1 : -1), 0);

	}
	
}
