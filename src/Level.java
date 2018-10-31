import java.util.Random;

public class Level{
	
	
	public static final int TILE_SIZE = 48;
	public static int TREES;
	
	//Goal Placement
	public static final float[] goalGap = new float[50];
	
	//RNG
	private static Random random = new Random();
	//Random Log chosen for ExtraLife spawn point
	private static int minTime = 25;
	private static int timeRange = 10;
	public static int spawnTime;
	public static int logTotal = 0;
	public static int logNum;
	    

	public Level(String levelNum) {
		
		//Reset the lives for each level
		Sprite.LIVES_LEFT = Sprite.LIVES_NUM;
		World.extraLife = false;
		logTotal = 0;
		
		//Reset all sprites
		World.sprites.clear();
		World.goals.clear();
		
		//Reload the sprites into the array used or rendering
		LevelBuilder.readFile(levelNum);
		
		//Player Setup
		World.sprites.add(new Player(App.SCREEN_WIDTH/2, App.SCREEN_HEIGHT - Level.TILE_SIZE));

		
		//Goal Setup
		float k = 1f;
		for (int i=0; i<=50; i+=2) {
			if (TILE_SIZE*k + TILE_SIZE/2 >= App.SCREEN_WIDTH) {
				World.goalNum = i/2 - 1;
				break;
			}
			goalGap[i] = TILE_SIZE*k + TILE_SIZE/2;
			goalGap[i+1] = goalGap[i] + TILE_SIZE*2;
			k += 4f;
		}
		
		//Generates random numbers for Extra Life components
		RNG();
	}
	
	//Random Number Generator
	public static void RNG() {
		spawnTime = logNum = 0;
		spawnTime = minTime + random.nextInt(timeRange);
		logNum = random.nextInt(logTotal);
		System.out.println(logTotal);
		System.out.println(logNum);
		System.out.println(spawnTime);
	}

}
