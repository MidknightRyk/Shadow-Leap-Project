
import org.newdawn.slick.Input;

public class Player extends Sprite{
	
	
	
	private static final String PLAYER = "assets/frog.png";
	
	private static int dx, dy;
	
	
	public Player(float x, float y) {
		super(PLAYER, x, y);
	}

	public void update(Input input, int delta) {
		dy = 0;
		dx = 0;
		// Checks for quit game keys
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		//For switching levels (debugger)
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			World.win = true;
			World.newSpawn = true;
		}
		// Checks for arrow key inputs
		// Calculates the distance moved
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= Level.TILE_SIZE;
		}

		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += Level.TILE_SIZE;
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += Level.TILE_SIZE;
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= Level.TILE_SIZE;
		}
		
		// Checks and prevents off screen movements and solid collisions
		
		if (getX() + dx - Level.TILE_SIZE/2 < 0 || getX() + dx + Level.TILE_SIZE/2 > App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - Level.TILE_SIZE/2 < 0 || getY() + dy + Level.TILE_SIZE/2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		
		//Checks is player has arrived at a goal
		for (int i=0; i<Level.goalGap.length; i+=2) {
			if (getY() + dy - Level.TILE_SIZE/2 >= Level.TREES) {
				break;
			}
			if (getX()+dx > Level.goalGap[i] && getX()+dx < Level.goalGap[i+1]) {
				dy = 0;
				dx = 0;
				reset(App.SCREEN_WIDTH/2, App.SCREEN_HEIGHT-Level.TILE_SIZE);
				goalAction(Level.goalGap[i], Level.goalGap[i+1]);
				
				//Sets goal as occupied
				Level.goalGap[i] = Level.goalGap[i+1] = 0;
			}
		}
		
		move(dx, dy);
		//Sets -1 if no movement was made to either dy or dx
		if(dx == 0) {
			dx = -1;
		}
		if(dy == 0) {
			dy = -1;
		}
		
	}
	
	@Override
	public void collisionAction(Sprite other) {
		
		//Ridable Objects
		if (other.hasTag(PLATFORM)) {
			rideOn = true;
			
			setX(other.getX());
			
			//If Player reaches the bounds of the screen, player stops moving
			if(bound.getRight() >= App.SCREEN_WIDTH) {
				setX(App.SCREEN_WIDTH-bound.getWidth()/2);
			}
			if(bound.getLeft() <= 0) {
				setX(0+bound.getWidth()/2);
			}
		}
		
		//Player is unable to move through 
		if (other.hasTag(SOLID)) {
			if(dx != -1) {
				move(-dx, 0);
			}
			if(dy != -1) {
				move(0, -dy);
			}
		}
		
		//Instant Life Loss
		if (other.hasTag(DANGER)) {
			deathAction();			
		}
		
		//Bulldozer
		if (other.hasTag(BLOCKED)) {
			//Sets Blockade Sprites to push Player when encountered
			if(other.bound.getLeft() <= bound.getRight() && other.getY() == getY()) {
				setX((int)other.bound.getRight()+bound.getWidth()/2);
			}
			
			//If Player gets pushed out of bounds, player loses and life and returns to initial position
			if(bound.getLeft() >= App.SCREEN_WIDTH || bound.getRight() <= 0) {
				deathAction();
			}
			
		}
		
		//For when player encounters an ExtraLife
		if (other.hasTag(HEALTH)) {
			Sprite.LIVES_LEFT += 1;
			PlayerLives.setLife(true);
			World.extraLife = true;
			World.newSpawn = true;
			
			//Yeet that bonus life outta existence
			other.setX(App.SCREEN_WIDTH*10);
		}
	}
	
	
}
