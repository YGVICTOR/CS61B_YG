public class OffByN implements CharacterComparator{

    private int n;

    OffByN(int n){
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if(Math.abs(x - y) == n){
            return true;
        }
        return false;
    }
}
