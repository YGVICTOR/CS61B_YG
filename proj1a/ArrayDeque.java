public class ArrayDeque<T> {
    private T[] storages;
    private int size;
    private int nextFirstIdx;
    private int nextLastIdx;
    private double usageFactor;
    private int maxCapacity;
    private static final int REFACTOR = 2;

    public ArrayDeque(){
        storages = (T[])new Object[8];
        size = 0;
        nextFirstIdx = storages.length/2 -1;
        nextLastIdx = storages.length/2;
        usageFactor = 0.0;
        maxCapacity = storages.length;
    }

    private void resize(int newCapacity){
        T[] newStorages = (T[]) new Object[newCapacity];
        if(nextFirstIdx < nextLastIdx){
            System.arraycopy(storages,nextFirstIdx +1,newStorages,newCapacity/(2 * REFACTOR),size);
            storages = newStorages;
            usageFactor = Double.valueOf(size) / Double.valueOf(newCapacity);
            nextFirstIdx = newCapacity / (2 * REFACTOR) - 1;
            nextLastIdx = newCapacity / (2 * REFACTOR) + size;
            maxCapacity = storages.length;
        }
        else{
            T[] tempStorages = (T[]) new Object[size];
            if( nextFirstIdx+1 != storages.length){
                System.arraycopy(storages,nextFirstIdx+1,tempStorages,0,storages.length-(nextFirstIdx+1));
            }
            System.arraycopy(storages,0,tempStorages,storages.length-(nextFirstIdx+1),nextLastIdx);
            System.arraycopy(tempStorages,0 ,newStorages,newCapacity/4,size);
            storages = newStorages;
            usageFactor = Double.valueOf(size) / Double.valueOf(newCapacity);
            nextFirstIdx = newCapacity / (2 * REFACTOR) - 1;
            nextLastIdx = newCapacity / (2 * REFACTOR) + size;
            maxCapacity = storages.length;
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item){
        size += 1;
        storages[nextFirstIdx] = item;
        nextFirstIdx = nextFirstIdx - 1;
        if(nextFirstIdx < 0){
            nextFirstIdx += maxCapacity;
        }
        usageFactor = Double.valueOf(size) / Double.valueOf(maxCapacity);
        if(usageFactor > 0.75){
            resize(REFACTOR * maxCapacity);
        }
    }

    /**
     * Adds an item of type T to the back of the deque.
     * */
    public void addLast(T item){
        size += 1;
        storages[nextLastIdx] = item;
        nextLastIdx += 1;
        if(nextLastIdx >= maxCapacity){
            nextLastIdx -= maxCapacity;
        }
        usageFactor = Double.valueOf(size) / Double.valueOf(maxCapacity);
        if(usageFactor > 0.75){
            resize(REFACTOR * maxCapacity);
        }
    }


    /**
     * Returns the number of items in the deque.
     * */
    public int size(){
        return size;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * */
    public boolean isEmpty(){
        return size == 0;
    }


    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * */
    private T helperGetFirst(){
        if(size == 0){
            return null;
        }
        int target = nextFirstIdx + 1;
        if(target == maxCapacity){
            target -= maxCapacity;
        }
        return storages[target];
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        else {
            T result = helperGetFirst();
            nextFirstIdx += 1;
            if (nextFirstIdx == maxCapacity) {
                nextFirstIdx = nextFirstIdx - maxCapacity;
            }
            size -= 1;
            storages[nextFirstIdx] = null;
            usageFactor = Double.valueOf(size) / Double.valueOf(maxCapacity);
            if (maxCapacity >= 16) {
                if (usageFactor < 0.25) {
                    resize(maxCapacity / 2);
                }
            }
            return result;
        }
    }

        /**
         * Removes and returns the item at the back of the deque.
         * If no such item exists, returns null.
         * */
     private T helperGetLast(){
         if(size == 0){
             return null;
         }
         int target = nextLastIdx - 1;
         if(target < 0){
             target = maxCapacity - 1;
         }
         return storages[target];
     }

     public T removeLast(){
         if (size == 0) {
             return null;
         }
         else{
             T result = helperGetLast();
             nextLastIdx -= 1;
             if (nextLastIdx < 0) {
                 nextLastIdx = maxCapacity-1;
             }
             size -= 1;
             storages[nextLastIdx] = null;
             usageFactor = Double.valueOf(size) / Double.valueOf(maxCapacity);
             if (maxCapacity >= 16) {
                 if (usageFactor < 0.25) {
                     resize(maxCapacity / 2);
                 }
             }
             return result;
         }
     }

     /**
      * Gets the item at the given index, where 0 is the front,
      * 1 is the next item, and so forth.
      * If no such item exists,
      * returns null. Must not alter the deque!
      * */
     public T get(int index){
         if(index > size || index <0){
             return null;
         }
         int start = nextFirstIdx + 1;
         int end = nextLastIdx - 1;
         if(start == maxCapacity){
             start = 0;
         }
         if(end < 0){
             end = 0;
         }
         if(start < end){
             return storages[start + index];
         }
         else{
             if(start + index < maxCapacity){
                 return storages[start + index];
             }
             else{
                 int resultIdx = index - (maxCapacity - start);
                 return storages[resultIdx];
             }
         }
     }
    /**
     * Prints the items in the deque from first to last, separated by a space.
     * */
    public void printDeque(){
        int start = nextFirstIdx + 1;
        int end = nextLastIdx - 1;
        if(start == maxCapacity){
            start = 0;
        }
        if(end < 0){
            end = 0;
        }
        if(start < end){
            for(int i = start;i <= end;i++){
                System.out.print(storages[i]+" ");
            }
        }
        else{
            for(int i = start;i<maxCapacity;i++){
                System.out.print(storages[i]+" ");
            }
            for(int i = 0;i <=end ;i++){
                System.out.print(storages[i]+" ");
            }
        }
    }
}

