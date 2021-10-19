package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        Integer[] cnt = new Integer[M];
        for(int i=0;i<M;i++){
            cnt[i]=0;
        }
        for(Oomage current:oomages){
            int bucketNum = (current.hashCode() & 0x7FFFFFFF) % M;
            cnt[bucketNum]+=1;
        }
        Boolean flag = true;
        int N = oomages.size();
        for(int i=0;i<M;i++){
            if(cnt[i]< N*1.0/50 || cnt[i]> N/2.5){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
