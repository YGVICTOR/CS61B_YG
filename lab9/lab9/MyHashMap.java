package lab9;

import javax.management.MBeanAttributeInfo;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author YU,Gan
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private int loadingSize;

    private double loadFactor() {
        return loadingSize*1.0 / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        this.loadingSize = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        int hashCode = hash(key);
        ArrayMap<K,V> startPoint = buckets[hashCode];
        if(startPoint.size==0){
            return null;
        }
        V value = startPoint.get(key);
        return value;
        //throw new UnsupportedOperationException();
    }
    private void resize(int capacity){
        ArrayMap<K,V>[] newBucket = new ArrayMap[capacity];
        for (int i = 0; i < newBucket.length; i += 1) {
            newBucket[i] = new ArrayMap<>();
        }
        for(int i=0;i<buckets.length;i+=1){
            if(buckets[i].size != 0){
                for(K currentKey : buckets[i].keySet()){
                    int newResult;
                    int numBuckets = newBucket.length;
                    newResult = Math.floorMod(currentKey.hashCode(), numBuckets);
                    newBucket[newResult].put(currentKey,buckets[i].get(currentKey));
                }
            }
        }
        buckets = newBucket;


    }
    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        int hashCode = hash(key);
        ArrayMap<K,V> startPoint = buckets[hashCode];
        if(startPoint.size==0){
            buckets[hashCode].put(key,value);
            size++;
            loadingSize++;
            if(loadFactor()>MAX_LF){
                resize(buckets.length*2);
            }
        }
        else{
            V flag = startPoint.get(key);
            if(flag==null){
                startPoint.put(key,value);
                size++;
            }
            else{
                startPoint.remove(key);
                startPoint.put(key,value);
            }

        }

        //throw new UnsupportedOperationException();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
        //throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {


        MyHashMap<String,Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("a",1);
        myHashMap.put("b",2);
        myHashMap.put("c",3);
        myHashMap.put("d",4);
        myHashMap.put("e",5);
        myHashMap.put("f",6);
        myHashMap.put("g",7);
        myHashMap.put("h",8);
        myHashMap.put("i",9);
        myHashMap.put("j",10);
        myHashMap.put("k",11);
        myHashMap.put("l",12);
        myHashMap.put("m",13);
        myHashMap.put("n",14);
        myHashMap.put("o",15);
        myHashMap.put("p",16);
        myHashMap.put("q",17);
        myHashMap.put("r",18);
        myHashMap.put("s",19);
        myHashMap.put("t",20);
        myHashMap.put("u",21);
        myHashMap.put("v",22);
        myHashMap.put("w",23);
        myHashMap.put("x",24);
        myHashMap.put("y",25);
        myHashMap.put("z",26);
//        for (int i = 0; i < 455; i++) {
//            myHashMap.put("hi" + i, 1);
//        }
        //System.out.println(myHashMap.get("t"));

        MyHashMap<String, String> dictionary = new MyHashMap<>();
        dictionary.put("hello", "world");
        dictionary.put("hello", "kevin");
        System.out.println(dictionary.size());



//        System.out.println(myHashMap.size());
//        System.out.println(myHashMap.loadingSize);
//        System.out.println(myHashMap.loadFactor());
    }
}
