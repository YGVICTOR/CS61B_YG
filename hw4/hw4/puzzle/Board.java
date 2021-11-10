package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private static final int BLANK =  0 ;
    private int[][] goal;
    private int [][] start;
    private  int N;
    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     * */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    /**
     * Returns the neighbors of the current board
     * */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * Constructs a board from an N-by-N array of tiles where
     *     tiles[i][j] = tile at row i, column j
     */
    public Board(int [][] tiles){
        N = tiles.length;
        goal = new int[N][N];
        start = new int[N][N];
        int cnt = 1;
        for (int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                goal[i][j] = cnt ++;
            }
        }
        goal[N-1][N-1] = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                start[i][j]=tiles[i][j];
            }
        }
    }

    /**
    *  Returns value of tile at row i, column j (or 0 if blank)
    */
    public int tileAt(int i, int j){
        if(i<0 || i>=N|| j<0|| j>=N){
            throw new RuntimeException("i and j must between 0 and N-1");
        }
        return start[i][j];
    }

    /**
     * Returns the board size N
     * */
    public int size(){
        return N;
    }


    /**
     * Hamming estimate described below
     * */
    public int hamming(){
        int result = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(start[i][j] !=goal[i][j] && goal[i][j] != BLANK){
                    result ++;
                }
            }
        }
        return result;
    }

    /**
     * Manhattan estimate described below
     */
    public int manhattan(){
        int result = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                int current = start[i][j];
                if(current != 0){
                    int[][] target = to2DHelper(current);
                    int currentDistance = Math.abs(target[0][0] - i) + Math.abs(target[0][1] - j);
                    result += currentDistance;
                }
            }
        }
        return result;


    }

    private int[][] to2DHelper(int i){
        int[][]  result = new int[1][2];
        if(i == 0){
            result[0][0] = N-1;
            result[0][1] = N-1;
            return result;
        }
        else{
            int col =  (i-1) % N;
            int row = (i-1) / N;
            result[0][0] = row;
            result[0][1] = col;
            return  result;
        }
    }
    /**
     * Returns true if this board's tile values are the same
     *               position as y's
     */
    public boolean equals(Object y){
        if(y== this)return true;
        if(y==null || y.getClass()!=this.getClass())return false;
        Board obj = (Board) y;
        if(obj.size() != this.size())return false;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(obj.tileAt(i,j) != start[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


}
