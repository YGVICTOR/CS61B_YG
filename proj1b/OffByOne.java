public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int xInt = x;
        int yInt = y;

        if(Math.abs(x - y) == 1){
            return true;
        }
        return false;
    }
}
