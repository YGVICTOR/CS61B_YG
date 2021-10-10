package byog.Core;

import byog.Core.Util.ArrayDeque;
import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


public class HallWay {
    //private static final int WIDTH = 61;
    //private static final int HEIGHT = 31;
    static ArrayDeque<Position> positions = new ArrayDeque<Position>();


    public static void generateHallWay(TETile[][] world,int width,int height){
        //generate allWall Line;
        for(int i=0;i<width;i+=2){
            for(int j=0; j<height;j++){
                world[i][j] = Tileset.WALL;
            }
        }
        for(int i=1;i<world.length;i+=2){
            for(int j=0;j<world[0].length;j+=2){
                world[i][j] = Tileset.WALL;
            }
        }
    }

    //detect if the position at the boarder
    /**
     * @ parameter:
     * flag: 0 lowerBound;
     *       1 leftBound;
     *       2 upperBound;
     *       3 RightBound;
     * */
    public static boolean isAtTheBoarder(TETile[][] world,int width,int height,Position position,int flag){
        switch (flag){
            case 0:
                return position.y == 0;
            case 1:
                return position.x==0;
            case 2:
                return position.y == height -1;
            case 3:
                return position.x == width - 1;
        }
        return false;

    }

    public static boolean isAtTheBoarder(TETile[][] world,int width,int height,Position position){
        if(position.x == 0 || position.x==width-1 || position.y ==0 || position.y ==height-1){
            return true;
        }
        else{
            return false;
        }
    }

    //randomly generate start point, move the WallPoint to rightDown position;
    public static Position randomStart(TETile[][] world,int width, int height, Random random){
        int potentialX = random.nextInt(width);
        int potentialY = random.nextInt(height);
        if(world[potentialX][potentialY].equals(Tileset.NOTHING)){
            Position result = new Position(potentialX,potentialY);
            return result;
        }
        else{
            //左下顶点
            if(potentialX==0&&potentialY==0){
                return new Position(1,1);
            }
            //右下顶点
            if(potentialX==width-1&&potentialY==0){
                return new Position(width-2,1);
            }
            //下边界
            if (isAtTheBoarder(world,width,height,new Position(potentialX,potentialY),0)){
                if(potentialX % 2 ==0){
                    return new Position(potentialX+1,1);
                }
                if(potentialX%2 != 0 ){
                    return new Position(potentialX,1);
                }
            }
            //右边界
            if(isAtTheBoarder(world,width,height,new Position(potentialX,potentialY),3)){
                if(potentialY%2==0){
                    return new Position(potentialX-1,potentialY-1);
                }
                else{
                    return new Position(potentialX-1,potentialY);
                }
            }
            //中间部分
            Position newPosition = new Position(potentialX,potentialY-1);
            if(world[newPosition.x][newPosition.y].equals(Tileset.WALL)){
                return new Position((newPosition.x)+1, newPosition.y);
            }
            else{
                return newPosition;
            }
        }
    }

    public static boolean isLeftOverFlow(TETile[][] world,int width,int height,Position currentPosition){
        if(currentPosition.x -2 <0){
            return true;
        }
        return false;
    }

    public static boolean isRightOverFlow(TETile[][] world,int width,int height,Position currentPosition){
        if(currentPosition.x +2 >=width){
            return true;
        }
        return false;
    }

    public static boolean isUpOverFlow(TETile[][] world,int width,int height,Position currentPosition){
        if(currentPosition.y +2 >= height){
            return true;
        }
        return false;
    }

    public static boolean isLowerOverFlow(TETile[][] world,int width,int height,Position currentPosition){
        if(currentPosition.y -2 <0){
            return true;
        }
        return false;
    }


    public static ArrayDeque<Position> checkAvailableOptions(TETile[][] world,int width,int height, Position position){
        ArrayDeque<Position> availableOptions = new ArrayDeque<Position>();
        if(! isRightOverFlow(world,width,height,position)){
            if(world[position.x+2][position.y].equals(Tileset.NOTHING)){
                availableOptions.addLast(new Position(position.x+2, position.y ));
            }
        }
        if(!isLeftOverFlow(world,width,height,position)){
            if(world[position.x-2][position.y].equals(Tileset.NOTHING)){
                availableOptions.addLast(new Position(position.x-2,position.y));
            }
        }
        if(!isUpOverFlow(world,width,height,position)){
            if(world[position.x][position.y+2].equals(Tileset.NOTHING)){
                availableOptions.addLast(new Position(position.x, position.y+2));
            }
        }

        if(! isLowerOverFlow(world,width,height,position)){
            if(world[position.x][position.y-2].equals(Tileset.NOTHING)){
                availableOptions.addLast(new Position(position.x, position.y-2));
            }
        }
        return availableOptions;
    }

    //iterator over the whole map to get the hallway;
    public static void hallWayGenerator(Random random,TETile[][] world,int width,int height){
        Position startPosition = randomStart(world,width,height,random);
        world[startPosition.x][startPosition.y] = Tileset.FLOOR;
        positions.addLast(startPosition);
        while(! positions.isEmpty()){
            int last = positions.size()-1;
            Position currentPosition = positions.get(last);
            ArrayDeque<Position> availableOptions = checkAvailableOptions(world,width,height,currentPosition);
            if(! availableOptions.isEmpty()){
                connect(world,availableOptions,currentPosition,random,positions);
            }
            else{
                positions.removeLast();
            }

        }
    }

    private static void connect(TETile[][] world, ArrayDeque<Position> availableOptions, Position currentPosition, Random random,ArrayDeque<Position> positions) {
        int pick = random.nextInt(availableOptions.size());
        Position destination = availableOptions.get(pick);
        positions.addLast(destination);
        if(currentPosition.x == destination.x){
            if(currentPosition.y > destination.y){
                Position p1 = new Position(currentPosition.x,currentPosition.y);
                Position p2 = new Position(currentPosition.x,currentPosition.y-1);
                Position p3 = new Position(currentPosition.x, destination.y);
                world[p1.x][p1.y] = Tileset.FLOOR;
                world[p2.x][p2.y] = Tileset.FLOOR;
                world[p3.x][p3.y] = Tileset.FLOOR;
            }
            else {
                Position p1 = new Position(currentPosition.x,currentPosition.y);
                Position p2 = new Position(currentPosition.x,destination.y-1);
                Position p3 = new Position(currentPosition.x, destination.y);
                world[p1.x][p1.y] = Tileset.FLOOR;
                world[p2.x][p2.y] = Tileset.FLOOR;
                world[p3.x][p3.y] = Tileset.FLOOR;
            }
        }
        else{
            if(currentPosition.x > destination.x){
                Position p1 = new Position(currentPosition.x,currentPosition.y);
                Position p2 = new Position(currentPosition.x-1,currentPosition.y);
                Position p3 = new Position(destination.x, currentPosition.y);
                world[p1.x][p1.y] = Tileset.FLOOR;
                world[p2.x][p2.y] = Tileset.FLOOR;
                world[p3.x][p3.y] = Tileset.FLOOR;
            }
            else{
                Position p1 = new Position(currentPosition.x,currentPosition.y);
                Position p2 = new Position(destination.x-1,currentPosition.y);
                Position p3 = new Position(destination.x, currentPosition.y);
                world[p1.x][p1.y] = Tileset.FLOOR;
                world[p2.x][p2.y] = Tileset.FLOOR;
                world[p3.x][p3.y] = Tileset.FLOOR;
            }

        }

    }
}
