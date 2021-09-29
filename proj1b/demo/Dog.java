package demo;

public interface Dog {
    default void bark(){
        System.out.println("Dog bark");
    }
}
