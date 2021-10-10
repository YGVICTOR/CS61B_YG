package byog.lab6;
import org.junit.Test;
import static org.junit.Assert.*;
public class MemoryGameTest {
    MemoryGame memoryGame;
    public MemoryGameTest(){
        memoryGame = new MemoryGame(40,40,100L);
    }
    @Test
    public void testGenerateRandomString() {
        MemoryGameTest memoryGameTest = new MemoryGameTest();
        String actual = memoryGame.generateRandomString(5);
        String expected = "zuakv";
        assertEquals(actual,expected);
    }

    @Test
    public void testDrawFrame(){
        MemoryGameTest memoryGameTest = new MemoryGameTest();
        String txt = memoryGame.generateRandomString(5);
        while (true){
            memoryGame.drawFrame(txt);
        }
    }

    @Test
    public void testFlashSequence(){
        MemoryGameTest memoryGameTest = new MemoryGameTest();
        String txt = memoryGame.generateRandomString(5);
        while(true){
            memoryGame.flashSequence(txt);
        }
    }

    @Test
    public void testSolicitNCharsInput(){
        MemoryGameTest memoryGameTest = new MemoryGameTest();
        while (true) {
            memoryGame.solicitNCharsInput(10);
        }
    }
    @Test
    public void testRun(){
        MemoryGameTest memoryGameTest = new MemoryGameTest();
        memoryGame.startGame();
    }
}
