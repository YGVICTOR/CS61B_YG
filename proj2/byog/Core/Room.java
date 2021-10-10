package byog.Core;

import byog.Core.Util.ArrayDeque;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    public final Position LFPOSITION;
    public final int WIDTH;
    public final int HEIGHT;
    public static ArrayDeque<Room> roomList = new ArrayDeque<Room>();

    public Room(Position downLeft,int width,int height){
        this.LFPOSITION = downLeft;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public static void roomPrinter(TETile[][] world,Room room){
        for(int i=0;i< room.WIDTH;i++){
            for(int j=0;j<room.HEIGHT;j++){
                if(j==0||j==room.HEIGHT-1||i==0||i==room.WIDTH-1){
                    world[room.LFPOSITION.x+i][room.LFPOSITION.y+j] = Tileset.WALL;
                }
                else{
                    world[room.LFPOSITION.x+i][room.LFPOSITION.y+j] = Tileset.MOUNTAIN;
                }

            }
        }
    }

    public static Room randomRoomProducer(Random random,int width,int height){
        int x = RandomUtils.uniform(random,0,width);
        int y = RandomUtils.uniform(random,0,height);
        int randomWidth = RandomUtils.uniform(random,5,10);
        int randomHeight = RandomUtils.uniform(random,5,10);
        Position lowerLeft = new Position(x,y);
        return new Room(lowerLeft,randomWidth,randomHeight);
    }

    public static boolean isOverlap(Room room1,Room room2){
        int room1Left = room1.LFPOSITION.x;
        int room1Right = room1.LFPOSITION.x + room1.WIDTH-1;
        int room1Lower = room1.LFPOSITION.y;
        int room1Upper = room1.LFPOSITION.y+room1.HEIGHT-1;

        int room2Left = room2.LFPOSITION.x;
        int room2Right = room2.LFPOSITION.x + room2.WIDTH-1;
        int room2Lower = room2.LFPOSITION.y;
        int room2Upper = room2.LFPOSITION.y+room2.HEIGHT-1;

        if(     (room1Left>=room2Left && room1Left<=room2Right)||
                (room1Left<=room2Left && room1Right>=room2Left)||

                (room1Right>=room2Left && room1Right <= room2Right)||
                (room1Right<=room2Right && room1Right>= room2Left)||

                (room1Upper<room2Upper && room1Upper>= room2Lower) ||
                (room1Lower<=room2Upper && room1Lower >=room2Lower)
        ){
            return true;
        }
        return  false;

    }

    public static boolean isCrossedByBoarder(Room room,int width, int height){
        if(room.LFPOSITION.x <0){
            return true;
        }
        if(room.LFPOSITION.x+room.WIDTH >= width){
            return true;
        }
        if(room.LFPOSITION.y < 0){
            return true;
        }
        if(room.LFPOSITION.y+room.HEIGHT>=height){
            return true;
        }
        return false;
    }

    public static void RoomGenerator(Random random,int maximumRooms,TETile[][]world,int width,int height){
        for(int i=0;i<maximumRooms;i++){
            Room newRoom = randomRoomProducer(random,width,height);
            boolean overlapFlag = false;
            for(int j=0;j<roomList.size();j++){
                if(isOverlap(roomList.get(j),newRoom)){
                    overlapFlag = true;
                }
            }
            if(overlapFlag ==false){
                if(!isCrossedByBoarder(newRoom,width,height)){
                    roomList.addLast(newRoom);
                }
            }
        }
        for(int i=0;i<roomList.size();i++){
            roomPrinter(world,roomList.get(i));
        }
    }
}
