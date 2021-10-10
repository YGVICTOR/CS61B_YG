package byog.Core.Test;

import byog.Core.HallWay;
import byog.Core.Position;
import byog.Core.RandomUtils;
import byog.Core.Room;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class IntegratedTest {
    private static final int WIDTH = 61;
    private static final int HEIGHT = 31;
    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);

        //Initialized to nothing
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        HallWay.generateHallWay(world,WIDTH,HEIGHT);
        Random random = new Random(10000000l);
        HallWay.hallWayGenerator(random,world,WIDTH,HEIGHT);
        Room.RoomGenerator(random,1500,world,WIDTH,HEIGHT);
        //openTheDoor(world,WIDTH, HEIGHT,  random);

        //ter.renderFrame(world);


        TERenderer ter2 = new TERenderer();
        TETile[][] world2 = new TETile[WIDTH][HEIGHT];
        ter2.initialize(WIDTH, HEIGHT);
        //Initialized to nothing
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                world2[i][j] = Tileset.NOTHING;
            }
        }
        for(int i=0;i<Room.roomList.size();i++){
            Room.roomPrinter(world2,Room.roomList.get(i));
        }
        ter2.renderFrame(world2);


    }

    public static boolean isDeadEnd(TETile[][]world,Position position){
        int count = 0;
        Position upper = new Position(position.x,position.y+1);
        Position lower = new Position(position.x,position.y-1);
        Position left = new Position(position.x -1,position.y);
        Position right = new Position(position.x+1,position.y);
        if(world[upper.x][upper.y].equals(Tileset.WALL)){
            count += 1;
        }
        if(world[lower.x][lower.y].equals(Tileset.WALL)){
            count += 1;
        }
        if(world[left.x][left.y].equals(Tileset.WALL)){
            count += 1;
        }
        if(world[right.x][right.y].equals(Tileset.WALL)){
            count += 1;
        }
        if(count==3){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isAtTheBoarder(TETile[][] world,int width,int height,Position position){
        if(position.x == 0 || position.x==width-1 || position.y ==0 || position.y ==height-1){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean canBeRemoved(TETile[][]world,int width,int height, Position position){
        if(isAtTheBoarder(world,width,height,position)){
            return false;
        }
        if(isDeadEnd(world,position)){
            return false;
        }
        return true;


    }

    public static void openTheDoor(TETile[][] world, int width, int height, Random random){
        for(int i=0;i<Room.roomList.size();i++){
            int sidePicker = random.nextInt(4);
            //int sidePicker = 3;
            switch (sidePicker){
                case 0:
                    Room currentRoom = Room.roomList.get(i);
                    int locationPicker = RandomUtils.uniform(random,1,Room.roomList.get(i).WIDTH-1);
                    if(canBeRemoved(
                            world,width,height
                            ,new Position(currentRoom.LFPOSITION.x+locationPicker,currentRoom.LFPOSITION.y))){
                        world[currentRoom.LFPOSITION.x+locationPicker][currentRoom.LFPOSITION.y] = Tileset.FLOOR;
                    }
                    //world[currentRoom.LFPOSITION.x+locationPicker][currentRoom.LFPOSITION.y] = Tileset.FLOOR;
                    break;
                case 1:
                     currentRoom = Room.roomList.get(i);
                     locationPicker = RandomUtils.uniform(random,1,Room.roomList.get(i).HEIGHT-1);
                    if(canBeRemoved(
                            world,width,height
                            ,new Position(currentRoom.LFPOSITION.x,currentRoom.LFPOSITION.y+locationPicker))){
                        world[currentRoom.LFPOSITION.x][currentRoom.LFPOSITION.y+locationPicker] = Tileset.FLOOR;
                    }
                    //world[currentRoom.LFPOSITION.x][currentRoom.LFPOSITION.y+locationPicker] = Tileset.FLOOR;
                    break;
                case 2:
                    currentRoom = Room.roomList.get(i);
                    locationPicker = RandomUtils.uniform(random,1,Room.roomList.get(i).WIDTH-1);
                    if(canBeRemoved(
                            world,width,height
                            ,new Position(currentRoom.LFPOSITION.x+locationPicker,currentRoom.LFPOSITION.y+currentRoom.HEIGHT-1))){
                        world[currentRoom.LFPOSITION.x+locationPicker][currentRoom.LFPOSITION.y+currentRoom.HEIGHT-1] = Tileset.FLOOR;
                    }
                    //world[currentRoom.LFPOSITION.x+locationPicker][currentRoom.LFPOSITION.y+currentRoom.HEIGHT-1] = Tileset.TREE;
                    break;
                case 3:
                    currentRoom = Room.roomList.get(i);
                    locationPicker = RandomUtils.uniform(random,1,Room.roomList.get(i).HEIGHT-1);
                    if(canBeRemoved(
                            world,width,height
                            ,new Position(currentRoom.LFPOSITION.x+currentRoom.WIDTH-1,currentRoom.LFPOSITION.y+locationPicker))){
                        world[currentRoom.LFPOSITION.x+currentRoom.WIDTH-1][currentRoom.LFPOSITION.y+locationPicker] = Tileset.FLOOR;
                    }
                    //world[currentRoom.LFPOSITION.x+currentRoom.WIDTH-1][currentRoom.LFPOSITION.y+locationPicker] = Tileset.MOUNTAIN;
                    break;




            }
        }

    }

}
