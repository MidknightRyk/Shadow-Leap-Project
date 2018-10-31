import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelBuilder{
	

	private static String[][] loader = new String[App.SCREEN_WIDTH][10];
	
	private static String BUS = "bus";
	private static String GRASS = "grass";
	private static String WATER = "water";
	private static String TREE = "tree";
	private static String BIKE = "bike";
	private static String BULL = "bulldozer";
	private static String LOG = "log";
	private static String LONGLOG = "longLog";
	private static String RACECAR = "racecar";
	private static String TURTLE = "turtle";
	
	public static void readFile(String filename) {
		//Read and Store level contents
		String levelFile = filename;
		String line = "";
		String seperator = ",";
		int lineNum = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(levelFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                loader[lineNum] = line.split(seperator);
                lineNum ++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		//Parse and Initialize level contents
		for (int i=0; i<lineNum; i++) {
			String type = loader[i][0];
			float x = Integer.parseInt(loader[i][1]);
			float y = Integer.parseInt(loader[i][2]);
			boolean moveRight;
			if (loader[i].length == 4) {
				moveRight = Boolean.parseBoolean(loader[i][3]);
			} else {
				moveRight = false;
			}
			
			LevelBuilder.loadPerLine(type, x, y, moveRight);
		}
	}
	

	public static void loadPerLine(String type, float x, float y, boolean moveRight) {
		
		if(type.equals(WATER)) {
			World.terrain.add(Tile.makeWater(x, y));
		}
		if(type.equals(GRASS)) {
			World.terrain.add(Tile.makeGrass(x, y));
		}
		if(type.equals(TREE)) {
			World.terrain.add(Tile.makeTree(x, y));
			if (y > Level.TREES) {
				Level.TREES = (int)y;
			}
		}
		if(type.equals(BUS)) {
			World.sprites.add(Vehicles.makeBus(x, y, moveRight));
		}
		if(type.equals(BIKE)) {
			World.sprites.add(Vehicles.makeBike(x, y, moveRight));
		}
		if(type.equals(BULL)) {
			World.sprites.add(Vehicles.makeBulldozer(x, y, moveRight));
		}
		if(type.equals(LOG)) {
			World.sprites.add(Vehicles.makeLog(x, y, moveRight));
		}
		if(type.equals(LONGLOG)) {
			World.sprites.add(Vehicles.makeLongLog(x, y, moveRight));
		}
		if(type.equals(RACECAR)) {
			World.sprites.add(Vehicles.makeRacecar(x, y, moveRight));
		}
		if(type.equals(TURTLE)) {
			World.sprites.add(Turtle.makeTurtle(x, y, moveRight));
		}
	}
}
