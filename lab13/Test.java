public class Test {
    public static void main(String[] args) {
        String[] asciis = new String[3];
        for(int i=0;i< asciis.length;i++){
            asciis[i] = "multiple regression";
        }

        for(String s : asciis){
            System.out.println(s);
        }
        int value = (int)'A';
        System.out.println("test".concat(" "));
        char value2 = (char)0;
        System.out.println(value2);

        System.out.println((int)' ');
        int[] count = new int[256];
        for(int current:count){
            System.out.println(current);
        }

    }

}
