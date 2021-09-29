package byog.lab5;
import byog.SaveDemo.World;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.Timer;
import java.util.jar.JarEntry;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    /**
     * compute the space in front of the line.
     * parameters: u for upper half d for lower half
     * */
    private static int computeSpace(int line, int size, char mode){
        if(mode == 'u'){
            return line - 1;
        }
        else{
            return size-line;
        }
    }

    private static int computeCharacter(int line, int size, char mode){
        if(mode == 'u'){
            return 3*size-2*line;
        }
        else{
            return size +2*line-2;
        }
    }

    private static void generateHalfHexagon(TETile[][] world,Position position,int size,TETile shape,char mode) {
        switch (mode){
            case 'u':
                for(int line=1;line<=size;line++){
                    int currentSpaceQty = computeSpace(line,size,'u');
                    int currentCharQty = computeCharacter(line,size,'u');
                    for(int i=0;i<currentCharQty;i++){
                        world[position.x+currentSpaceQty-1+i][position.y-1+line]=shape;
                    }
                }
                break;
            default:
                for(int line=1;line<=size;line++){
                    int currentSpaceQty = computeSpace(line,size,'d');
                    int currentCharQty = computeCharacter(line,size,'d');
                    for(int i=0;i<currentCharQty;i++){
                        world[position.x+currentSpaceQty-1+i][position.y-1+line]=shape;
                    }
                }
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        generateHalfHexagon(world,p,s,t,'d');
        Position newPosition = new Position(p.x,p.y+s);
        generateHalfHexagon(world,newPosition,s,t,'u');
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        addHexagon(world, new Position(11,9),3, Tileset.GRASS);
        addHexagon(world, new Position(11,3),3, Tileset.FLOWER);
        addHexagon(world, new Position(15,6),3, Tileset.TREE);
        addHexagon(world, new Position(19,9),3, Tileset.GRASS);
        addHexagon(world, new Position(23,12),3, Tileset.GRASS);
        ter.renderFrame(world);


    }
}
