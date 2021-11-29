/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int max = Integer.MIN_VALUE;
        String[] copyAsciis = new String[asciis.length];
        for(int i=0; i< asciis.length;i++){
            copyAsciis[i] = asciis[i];
            if(asciis[i].length()>max){
                max = asciis[i].length();
            }
        }
        for(int k=max-1;k>=0;k--){
            sortHelperLSD(copyAsciis,k);
        }
        return copyAsciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */

    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] count = new int[256];
        for(String current : asciis){
            if(index >= current.length()){
                count[0] ++;
            }
            else{
                char tmp = current.charAt(index);
                count[(int)tmp]++;
            }
        }

        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += count[i];
        }

        String[] sorted = new String[asciis.length];
       // int k = 0;
        for (int i = 0; i < asciis.length; i += 1) {
            String item = asciis[i];
            int place = 0;
            if(index >= item.length()){
                sorted[starts[0]] = item;
                starts[0] = starts[0] +1;
            }
            else{
                place = starts[(int)item.charAt(index)];
                sorted[place] = item;
                place += 1;
            }

//            if(index >= item.length()){
//                starts[0] = starts[0] +1;
//            }

//            else{
//                starts[(int)item.charAt(index)] = starts[(int)item.charAt(index)] +1;
//            }
            if(index < item.length()){
                starts[(int)item.charAt(index)] = starts[(int)item.charAt(index)] +1;

            }
        }
        for(int i=0;i<sorted.length;i++){
            asciis[i] = sorted[i];
        }

        return;
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

//    public static void main(String[] args) {
//        String[] unsorted =  {"105","20000","11"};
//        String[] result = sort(unsorted);
//        for(String current : result){
//            System.out.println(current);
//        }
//    }

}
