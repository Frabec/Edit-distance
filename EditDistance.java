import java.util.*;

public class EditDistance implements EditDistanceInterface {
     
    int c_i, c_d, c_r;
    static int MAX = Integer.MAX_VALUE;
    static int UNDEF = -1;

    public EditDistance (int c_i, int c_d, int c_r) {
        this.c_i = c_i;
        this.c_d = c_d;
        this.c_r = c_r;
    }
        
    public int[][] getEditDistanceDP(String s1, String s2) {
        /* To be completed in Part 1. Remove line below. */
        return new int[s1.length()+1][s2.length()+1];
    }

    public List<String> getMinimalEditSequence(String s1, String s2) {
        /* To be completed in Part 2. Remove sample code block below. */
        LinkedList<String> ls = new LinkedList<> ();
        if (c_r == 6) {
            ls.add("delete(1)");
            ls.add("delete(1)");
            ls.add("insert(2,c)");
            ls.add("insert(3,b)");
        }
        else {
            ls.add("replace(1,d)");
            ls.add("replace(3,b)");
        }
        return ls;
        /* Code block to be removed ends. */
    }
};
