package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private class Node{
        int current;
        int source;
        Node(int current,int source){
            this.current = current;
            this.source = source;
        }
    }

    private Maze maze;
    private Stack<Integer> stack = new Stack<>();
    private boolean hasCircle;
    private boolean overlap;
    private int repeatPoint;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(new Node(0,0));

        while (!stack.isEmpty()){
            int top = stack.pop();
            if(stack.isEmpty()){
                break;
            }
            edgeTo[stack.peek()] = top;
            announce();
        }
    }
    private void dfs(Node node){
        marked[node.current] = true;
        announce();
        for(int w : maze.adj(node.current)){
            if(marked[w] && node.source != w){
                hasCircle = true;
                stack.push(w);
                repeatPoint = w;
                overlap = false;
                return;
            }
            if(!marked[w]){
                Node newStart = new Node(w,node.current);
                dfs(newStart);
            }
            if(hasCircle){
                if(!overlap){
                    stack.push(w);
                }
                if(w == repeatPoint){
                    overlap = true;
                }
                return;
            }
        }
    }

    // Helper methods go here
}

