
public class Tile extends Sprite{
	private static final String GRASS_TILE = "assets/grass.png";
	private static final String WATER_TILE = "assets/water.png";
	private static final String TREE_TILE = "assets/tree.png";
	private static final String GOAL_PLAYER = "assets/frog.png";
	
	
	private Tile(String imageSrc, float x, float y) {		
		super(imageSrc, x, y);
	}
	private Tile(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}

	public static Tile makeGrass(float x, float y) {
		return new Tile(GRASS_TILE, x, y);
	}
	
	public static Tile makeWater(float x, float y) {
		return new Tile(WATER_TILE, x, y, new String[] {Sprite.DANGER});
	}
	
	public static Tile makeTree(float x, float y) {
		return new Tile(TREE_TILE, x, y, new String[] {Sprite.SOLID});
	}
	
	public static Tile fillGoal(float x, float y) {
		return new Tile(GOAL_PLAYER, x, y, new String[] {Sprite.DANGER});
	}
}
