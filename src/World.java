import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;




public class World {
	
	//Level Files
	private static final String level0 = "assets/levels/0.lvl";
	private static final String level1 = "assets/levels/1.lvl";
	private static final String[] levels = {level0, level1};
	
	public static ArrayList<Sprite> sprites = new ArrayList<>();
	public static ArrayList<Sprite> terrain = new ArrayList<>();
	public static ArrayList<Sprite> goals = new ArrayList<>();
	public static boolean extraLife = false;
	private PlayerLives bonusHealth;
	
	private static int currentLevel = 0;
	
	
	public static boolean win = false;
	public static boolean newSpawn = false;
	public static int goalNum;
	
	
	public World(){
		
		//Builds the first level
		new Level(level0);
		
	
	}

	public void update(Input input, int delta) {
		//Checks if player has won the level
		if(win == true && currentLevel+1 != levels.length) {
			currentLevel += 1;
			new Level(levels[currentLevel]);
			win = false;
			newSpawn = false;
		//If there are no more levels, the game exits
		} else if (win == true && currentLevel+1 == levels.length) {
			System.out.println("you win!");
			System.exit(0);
		}
		
		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
		}
		for (Sprite goal: goals) {
			goal.update(input, delta);
		}
		
		if(Sprite.checkSpawnTime(delta)) {
			sprites.add(Sprite.spawnLife());
		}
		
		//Checks for collision 
		for (Sprite sprite1: sprites) {
			for (Sprite sprite2: sprites) {
				if (sprite1 != sprite2
						&& sprite1.collisionCheck(sprite2)) {
					sprite1.collisionAction(sprite2);
				}
			}
			
			if(!Sprite.rideOn) {
				for (Sprite sprite2: terrain) {
					if (sprite1.collisionCheck(sprite2)) {
						sprite1.collisionAction(sprite2);
					}
				}
			}
			
			for (Sprite sprite2: goals) {
				if (sprite1.collisionCheck(sprite2)) {
					sprite1.collisionAction(sprite2);
				}
			}
		}
		
		//Resets platform check on player for each game loop
		Sprite.rideOn = false;
		
		//Removes bonus life after obtained by player
		if(extraLife) {
			sprites.remove(bonusHealth);
			extraLife = false;
		
		}
	}
	
	public void render(Graphics g){
		for (Sprite terrain: terrain) {
			terrain.render();
		}
		for (Sprite sprite : sprites) {
			sprite.render();
		}
		for (Sprite goal: goals) {
			goal.render();
		}
		Sprite.renderHud();
	}
}
