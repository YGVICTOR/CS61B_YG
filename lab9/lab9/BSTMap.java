package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author YU,Gan
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> ,Iterable<K>{

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private ArrayDeque<K> keys = new ArrayDeque<K>();

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if(p==null){
            return null;
        }
        if(p.key.equals(key)){
            return p.value;
        }
        else if(p.key.compareTo(key)>0){
            return getHelper(key,p.left);
        }
        else{
            return getHelper(key,p.right);
        }
        //throw new UnsupportedOperationException();
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key,this.root);
        //throw new UnsupportedOperationException();
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */

    private Node putHelper(K key, V value, Node p) {
        Node newRoot = new Node(key,value);
        if(p==null){
            newRoot.left = null;
            newRoot.right = null;
            this.size++;
            newRoot.key = key;
            newRoot.value = value;
            if(root == null){
                root = newRoot;
            }
            keys.addLast(newRoot.key);
            return newRoot;
        }
        else{
            if(p.key.compareTo(key)>0){
                p.left = putHelper(key,value,p.left);
            }
            else if(p.key.equals(key)){
                p.value = value;
            }
            else{
                p.right = putHelper(key,value,p.right);
            }
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        putHelper(key, value, this.root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
        //throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private void traversal(Node treeRoot){
        if(treeRoot == null){
            return;
        }
        //System.out.println(treeRoot.key);
        keys.addLast(treeRoot.key);
        traversal(treeRoot.left);
        traversal(treeRoot.right);


    }

    @Override
    public Set<K> keySet() {
        keys.makeEmpty();
        traversal(root);
        Set<K> result  = new HashSet<K>();
        for(K key : keys ){
            result.add(key);
        }
        return result;
        //throw new UnsupportedOperationException();
    }

    private V removeLeftWithOneChild(Node currentNode){
        V result = currentNode.left.value;
        if(currentNode.left.left==null&&currentNode.left.right != null){
            currentNode.left = currentNode.left.right;
        }
        else{
            currentNode.left = currentNode.left.left;
        }
        size--;
        return result;
    }

    private V removeLeftWithNoChild(Node currentNode){
        V result = currentNode.left.value;
        currentNode.left = null;
        size--;
        return result;
    }
    /**
     *delete right subTree most left Node;
     * */
    private V removeLeftWithTwoChildren(Node currentNode){
        Node currentRoot = currentNode.left.right;
        Node prePtr = currentNode.left;
        V result = currentNode.left.value;

        while(currentRoot.left !=null){
            prePtr = currentRoot;
            currentRoot = currentRoot.left;
        }
        K potentialKey = currentRoot.key;
        V potentialValue = currentRoot.value;
        if (currentRoot.left == null && currentRoot.right == null){
            removeRightWithNoChild(prePtr);
        }
        else{
            removeLeftWithOneChild(prePtr);
        }
        currentNode.left.key = potentialKey;
        currentNode.left.value = potentialValue;
        size --;
        return result;
    }

    private V removeRightWithNoChild(Node currentNode){
        V result = currentNode.right.value;
        currentNode.right = null;
        size--;
        return result;
    }

    private V removeRightWithOneChild(Node currentNode){
        V result = currentNode.right.value;
        if(currentNode.right.left==null&&currentNode.right.right != null){
            currentNode.right = currentNode.right.right;
        }
        else{
            currentNode.right = currentNode.right.left;
        }
        size--;
        return result;
    }

    private V removeRightWithTwoChildren(Node currentNode){
        Node currentRoot = currentNode.right.right;
        Node prePtr = currentNode.right;
        V result = currentNode.right.value;

        while(currentRoot.left !=null){
            prePtr = currentRoot;
            currentRoot = currentRoot.left;
        }
        K potentialKey = currentRoot.key;
        V potentialValue = currentRoot.value;
        if (currentRoot.left == null && currentRoot.right == null){
            removeRightWithNoChild(prePtr);
        }
        else{
            removeRightWithOneChild(prePtr);
        }
        currentNode.right.key = potentialKey;
        currentNode.right.value = potentialValue;
        size --;
        return result;
    }

    private int subCount(Node node){
        if(node.left ==null && node.right ==null){
            return 0;
        }
        else if(node.left != null && node.right != null){
            return 2;
        }
        else{
            return 1;
        }
    }

    private V removeIntegratedHelpter(K key,Node node){
        while(node != null){
            if(node.key.compareTo(key)>0){
                if(node.left == null){
                    throw new RuntimeException("key: "+key+" doesn't exist");
                }
                if(node.left.key.equals(key)){
                    int count = subCount(node.left);
                    switch (count){
                        case 0:
                            return removeLeftWithNoChild(node);
                        case 1:
                            return removeLeftWithOneChild(node);
                        case 2:
                            return removeLeftWithTwoChildren(node);
                    }
                }
                else{
                    node = node.left;
                }

            }
            else if(node.key.compareTo(key)<0){
                if(node.right == null){
                    throw new RuntimeException("key:"+key+" doesn't exist");
                }
                if(node.right.key.equals(key)){
                    int count = subCount(node.right);
                    switch (count){
                        case 0:
                            return removeRightWithNoChild(node);
                        case 1:
                            return removeRightWithOneChild(node);
                        case 2:
                            return removeRightWithTwoChildren(node);
                    }
                }
                else{
                    node = node.right;
                }
            }
            //delete the root
            else{
                Node sudoRoot = new Node(null,null);
                sudoRoot.left = root;
                sudoRoot.right = root;
                int flag = subCount(root);
                switch (flag){
                    case 0:
                        root =null;
                    case 1:
                        try{
                            V result = removeRightWithOneChild(sudoRoot);
                            root = sudoRoot.right;
                            return result;
                        } catch (Exception e) {
                            V result = removeLeftWithOneChild(sudoRoot);
                            root = sudoRoot.left;
                            return result;
                        }
                    case 2:
                        try{
                            V result = removeRightWithTwoChildren(sudoRoot);
                            root = sudoRoot.right;
                            return result;
                        } catch (Exception e) {
                            V result = removeLeftWithTwoChildren(sudoRoot);
                            root = sudoRoot.left;
                            return result;
                        }
                }
            }
        }
        return null;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V result =  removeIntegratedHelpter(key,root);
        keys.makeEmpty();
        traversal(root);
        ArrayDeque<K> tmp = keys;

        return result;
        //throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V tmpValue = get(key);
        if(tmpValue==null){
            throw new RuntimeException("key-value pair:"+key+","+value+" doesn't exist.");
        }
        if(value.equals(tmpValue)){
            remove(key);
            return tmpValue;
        }
        else{
            throw new RuntimeException("key-value pair:"+key+","+value+" doesn't exist.");
        }
        //throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator<K>();
        //throw new UnsupportedOperationException();
    }

    private class KeyIterator<K> implements Iterator<K>{
        int magicalPtr;

        KeyIterator(){
            magicalPtr = 0 ;
        }

        @Override
        public boolean hasNext() {

            return magicalPtr<=keys.size()-1;
        }

        @Override
        public K next() {
            return (K) keys.get(magicalPtr++);
        }
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("z", 1);
        bstmap.put("y",4);
        bstmap.put("x", 2);
        bstmap.put("w", 3);
        bstmap.put("v", 3);


        //System.out.println(bstmap.get("fish"));
        //HashSet<String> keys= (HashSet<String>) bstmap.keySet();
        for (String key : bstmap){
            System.out.println(key);
        }

        bstmap.remove("z");
        System.out.println("REMOVE TEST! GOD HELP");
        for(String key : bstmap){
            System.out.println(key);
        }
    }
}
