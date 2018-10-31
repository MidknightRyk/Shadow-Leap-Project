import org.newdawn.slick.Input;

public class Turtle extends Vehicles{
	

	private final static String TURTLE = "assets/turtles.png";
	
	//Movement Time
	private final static int miliSec = 1000;
	private final static int floatTime = 7;
	private final static int sinkTime = 2;
	private boolean isFloat = true;
	private float curDelta;
	private float xStop;
	

	public Turtle(String imageSrc, float x, float y, String[] tags, boolean moveRight, float Speed) {
		super(imageSrc, x, y, tags, moveRight, Speed);
	}
	
	public static Vehicles makeTurtle(float x, float y, boolean moveRight) {
		return new Turtle(TURTLE, x, y, new String[] {Sprite.PLATFORM}, moveRight, 0.085f);
	}

	@Override
	public void update(Input input, int delta) {
		//Calculates when to sink Turtles
		curDelta += delta;
		
		//Checks if turtle is afloat during the time required
		if (isFloat && curDelta >= floatTime*miliSec) {
			isFloat = false;
			xStop = getX();
			curDelta = 0;
			setX(App.SCREEN_WIDTH*10);
		} else if(!isFloat && curDelta >= sinkTime*miliSec) {
			isFloat = true;
			setX(xStop);
			curDelta = 0;
		//If the turtles are afloat, it acts the same way according to the parent
		} else if (isFloat){
			move(SPEED * delta * (moveRight ? 1 : -1), 0);
			if (getX() > App.SCREEN_WIDTH + bound.getWidth()/2 || getX() < 0 - bound.getWidth()/2
			 || getY() > App.SCREEN_HEIGHT + bound.getHeight()/2 || getY() < 0 - bound.getHeight()/2) {
				setX(getInitialX());
			}
		}
		
		
	}
}
