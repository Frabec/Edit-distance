import java.util.*;


public interface EditDistanceInterface {
    
    public int[][] getEditDistanceDP(String s1, String s2);
    public List<String> getMinimalEditSequence(String s1, String s2);
    
}
