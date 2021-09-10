import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque();
        System.out.println(ad.isEmpty());
//        ad.addFirst(0);
//        int testVariable = ad.removeLast();
//        ad.addFirst(2);
//        ad.addFirst(3);
//        testVariable = ad.removeLast();
//        ad.addFirst(5);
//        testVariable = ad.removeLast();
//        testVariable = ad.removeLast();
//        ad.addFirst(8);
//        ad.addFirst(9);
//        testVariable = ad.removeLast();


        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        int testVariable = ad.removeLast();
//        ad.addLast(2);
//        ad.addLast(3);
//        ad.addLast(4);
//        ad.addLast(5);
//        ad.addLast(6);
//        ad.addLast(7);
//        ad.addLast(8);
//
//        ad.printDeque();
//        int test_get = ad.get(5);
//
//        ad.removeLast();
//        ad.removeLast();
//        ad.removeLast();
//        ad.removeLast();
//        int result = ad.removeLast();
//        System.out.println(result);


    }
}
