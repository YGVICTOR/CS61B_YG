public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> characterArrayDeque= new ArrayDeque<Character>();
        for(char i : word.toCharArray()){
            characterArrayDeque.addLast(i);
        }
        return characterArrayDeque;
    }

    /**
     * return true if the word is palindrome;
     * */
    public boolean isPalindrome(String word){
        if(word == null){
            return true;
        }
        Deque<Character> characterArrayDeque = this.wordToDeque(word);
        String reverse = new String();
        while (!characterArrayDeque.isEmpty()){
            Character current = characterArrayDeque.removeLast();
            reverse += current.toString();
        }
        if(reverse.equals(word)){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean isPalindrome(String word,CharacterComparator cc){
        if(word.length() == 0 || word.length() == 1){
            return true;
        }
        Deque<Character> tmpCharacterDeque = wordToDeque(word);
        if(cc.equalChars(tmpCharacterDeque.removeFirst(),tmpCharacterDeque.removeLast())) {
            String tmpString = new String();
            while(!tmpCharacterDeque.isEmpty()){
                tmpString += tmpCharacterDeque.removeFirst().toString();
            }
            return isPalindrome(tmpString,cc);
        }
        return false;
    }

}
