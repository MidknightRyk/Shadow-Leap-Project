import org.newdawn.slick.Input;



public class PlayerLives extends Sprite{

	private static final String EXTRA_LIFE = "assets/extralife.png";
	private float boundLeft;
	private float boundRight;
	
	
	private PlayerLives(String imageSrc, float x, float y, String[] tags) {
		super(imageSrc, x, y, tags);
	}
	
	public static PlayerLives makeExtraLife(float x, float y) {
		return new PlayerLives(EXTRA_LIFE, x, y, new String[] {Sprite.HEALTH});
	}
	
	public static void setLife(boolean lifeLoss) {
		
		//Checks for no more lives
		if (LIVES_LEFT == 0) {
			System.out.println("you lose :<");
			System.exit(0);
		}
		
		//Sets the next life spawn position
		nextLifePos = lifeLoss ? nextLifePos-LIVES_SPACE
								:nextLifePos+LIVES_SPACE;
		
	}
	
	@Override
	public void update(Input input, int delta) {
		int logNum = 0;
		
		//Find the log needed for spawning the extra life on
		for(Sprite sprites: World.sprites) {
			if(sprites.hasTag(LOG)) {
				logNum++;
			}
			if(logNum == Level.logNum) {
				spawnX = sprites.getX();
				spawnY = sprites.getY();
				boundLeft = sprites.bound.getLeft();
				boundRight = sprites.bound.getRight();
			}
		}
		
		//Checks to see if ExtraLife movement should happen
		if(!World.extraLife) {
			setX(spawnX);
			setY(spawnY);
		}
	}
	
	
}
