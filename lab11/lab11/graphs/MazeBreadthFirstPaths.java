package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private Queue<Integer> fringe;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX,sourceY);
        t = maze.xyTo1D(targetX,targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new Queue<>();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        fringe.enqueue(v);
        marked[v] = true;
        announce();

        while(!fringe.isEmpty()){
            if(v==t){
                targetFound = true;
            }
            if(targetFound){
                return;
            }
            int out = fringe.dequeue();
            for(int w:maze.adj(out)){
                if(!marked[w]){
                    fringe.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = out;
                    distTo[w] = distTo[out] +1;
                    announce();
                    if(v==t){
                        targetFound = true;
                    }
                    if(targetFound){
                        return;
                    }
                }
            }
        }
    }
    @Override
    public void solve() {
        bfs(s);
    }
}

