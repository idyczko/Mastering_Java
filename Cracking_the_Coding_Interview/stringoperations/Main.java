package stringoperations;

import java.util.Collection;
import java.util.ArrayList;

public class Main {

    public static void main(Strint[] args) {

    }

    /*Sooooo my first idea was - let's just go through the super string character by character
    * and then - go character by character in for loop inside the first loop. But maybe it would
    * make more sense if we cycled throug super string and just built the permutation on a side.
    * When a character occurs, which is not present in substring - the permutation is being reset
    * and we start algorithm from where we have found this character. For storing permutation
    * we could use a cyclic buffer.
    */
    public static Collection<String> findPermutations(String super, String sub) {
        ArrayList<String> permutations = new ArrayList()<>; // Type inference
        char[] superChars = super.toCharArray();
        char[] subChars = sub.toCharArray();

        for(int i = 0; i < superChars.length - subChars.length; i++) {
          String temp = sub;
          for(int j = 0; j < subChars.length; j++) {
            if(!sub.contains(String.valueOf(superChars[i+j]))){
              i+=j+1;
            }
          }
        }

        return permutations;
    }
}
