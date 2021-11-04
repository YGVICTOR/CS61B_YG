package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode> {

    public WorldState word;
    public int moveCnt;
    public SearchNode prev;


    public SearchNode(WorldState word,SearchNode prev,int moveCnt){
        this.word = word;
        this.prev = prev;
        this.moveCnt = moveCnt;
    }

    @Override
    public int compareTo(SearchNode searchNode) {
        return this.word.estimatedDistanceToGoal()+moveCnt - (searchNode.word.estimatedDistanceToGoal()+searchNode.moveCnt);
    }
}
