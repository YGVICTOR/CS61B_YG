public class Test {
    public static void main(String[] args) {
        String[] asciis = new String[3];
        for(int i=0;i< asciis.length;i++){
            asciis[i] = "multiple regression";
        }

        for(String s : asciis){
            System.out.println(s);
        }
        System.out.println("test".concat(" "));

    }

}
