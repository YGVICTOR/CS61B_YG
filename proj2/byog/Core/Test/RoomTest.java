package byog.Core.Test;

import byog.Core.HallWay;
import byog.Core.Position;
import byog.Core.Room;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class RoomTest {
    private static final int WIDTH = 61;
    private static final int HEIGHT = 31;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);

        //Initialized to nothing
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        Room.RoomGenerator(new Random(),30,world,WIDTH,HEIGHT);
        ter.renderFrame(world);
    }
}
