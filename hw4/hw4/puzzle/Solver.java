package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;

public final class Solver {
    MinPQ<SearchNode> minPQ = new MinPQ<>();
    ArrayList<WorldState> solution  = new ArrayList<>();
    //private int totalCnt = 0 ;
    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     *
     **/

    public Solver(WorldState initial){
        SearchNode searchNode = new SearchNode( initial,null,0);
        minPQ.insert(searchNode);
        while(!minPQ.min().word.isGoal()){
            SearchNode currentNode = minPQ.delMin();
            for(WorldState word :currentNode.word.neighbors()){
                if(currentNode.prev==null || !word.equals((currentNode.prev.word))){
                    SearchNode newSearchNode = new SearchNode(word,currentNode,currentNode.moveCnt+1);
                    minPQ.insert(newSearchNode);
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * */
    public int moves(){

        return minPQ.min().moveCnt;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * */
    public Iterable<WorldState> solution(){
        //goal located in the top of mini heap but are located at the bottom of the tree structure;
        Stack<WorldState> stack = new Stack<>();
        SearchNode currentSearchNode = minPQ.min();
        while(currentSearchNode !=  null){
            stack.push(currentSearchNode.word);
            currentSearchNode = currentSearchNode.prev;
        }
        while(!stack.isEmpty()){
            solution.add(stack.pop());
        }

        return solution;
    }
}
