public class ArrayDeque<T> {
    T[] storages;
    int size;

    public ArrayDeque(){
        storages = (T[])new Object[8];
    }

}
