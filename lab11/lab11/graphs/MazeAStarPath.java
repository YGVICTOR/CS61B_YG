package lab11.graphs;

import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    //define the search node;
    private class SearchNode implements Comparable<SearchNode>{
        int priority;
        int current;
        SearchNode source;
        int currentDistance;

        SearchNode(int current,SearchNode source,int currentDistance){
            this.current = current;
            this.source = source;
            this.currentDistance = currentDistance;
            this.priority = currentDistance+ h(current);
        }
        @Override
        public int compareTo(SearchNode searchNode) {
            return this.priority - searchNode.priority;
        }
    }

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private boolean visited[] = new boolean[marked.length];

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int size = maze.N();
        int v_X =v%size+1;
        int v_y = v/size +1;
        int target_x = t%size +1;
        int target_y = t/size +1;
        return Math.abs(v_X-target_x)+Math.abs(v_y-target_y);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        marked[s] = true;
        announce();

        PriorityQueue<SearchNode> fringe = new PriorityQueue();
        fringe.add(new SearchNode(s,null,0));
        while(!fringe.isEmpty()&&h(fringe.peek().current)!=0){
            SearchNode mini = fringe.poll();
            visited[mini.current] = true;
            for(int w :maze.adj(mini.current)){
                if(mini.source == null || w!=mini.source.current&&visited[w]==false){
                    visited[w] = true;
                    fringe.add(new SearchNode(w,mini,mini.currentDistance+1));
                    marked[w] = true;
                    edgeTo[w] = mini.current;
                    distTo[w] = distTo[mini.current]+1;
                    announce();
                    if(w==t){
                        targetFound = true;
                    }
                }

            }

        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

