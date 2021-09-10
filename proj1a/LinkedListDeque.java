public class LinkedListDeque<T> {

    private class Node{
        public T content;
        public Node prev;
        public Node next;

        public Node(T content,Node prev,Node next){
            this.content = content;
            this.prev = prev;
            this.next =next;
        }
    }

    private int size;
    private Node sentinel;

    public  LinkedListDeque(){
        sentinel = new Node((T)"sentinel",null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

     /** Adds an item of type T to the front of the deque.*/
    public void addFirst(T item){
        Node newNode = new Node(item,null,null);
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        newNode.prev = sentinel;
        size ++;
    }

    /**Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        Node newNode = new Node(item,null,null);
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        newNode.next = sentinel;
        sentinel.prev= newNode;
        size ++;
    }

    /**Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /**Returns the number of items in the deque.*/
    public int size(){
        return size;
    }

    /**Prints the items in the deque from first to last, separated by a space*/
    public void printDeque(){
        Node ptr = sentinel.next;
        while(ptr != sentinel){
            System.out.print(ptr.content +" ");
            ptr = ptr.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst(){
        if(size == 0){
            return null;
        }
        else{
            Node ptr = sentinel.next;
            sentinel.next = ptr.next;
            ptr.next.prev = sentinel;
            ptr.prev = null;
            ptr.next = null;
            size --;
            return ptr.content;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    public T removeLast(){
        if(size == 0){
            return null;
        }
        else{
            Node ptr = sentinel.prev;
            sentinel.prev = ptr.prev;
            ptr.prev.next = sentinel;
            ptr.prev = null;
            ptr.next = null;
            size --;
            return ptr.content;
        }
    }

    /**Iterative way to find the indexth element in the LinkedListDeque*/
    public T get(int index){
        if(index > size-1){
            return null;
        }
        else{
            Node ptr = sentinel.next;
            int x = 0;
            while(x<index){
                ptr = ptr.next;
                x++;
            }
            return ptr.content;
        }
    }

    /**helper function to conduct the same function in a recursive way*/
    private T recursiveHelper(int index, Node node){
        if(index == 0){
            return node.content;
        }
        else{
            return recursiveHelper(index - 1, node.next);
        }
    }

    /**Same as get, but uses recursion.*/
    public T getRecursive(int index){
        if(index>size-1){
            return null;
        }
        else{
            return recursiveHelper(index,sentinel.next);
        }
    }

}
