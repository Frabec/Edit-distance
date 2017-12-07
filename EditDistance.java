import java.util.*;

public class EditDistance implements EditDistanceInterface {

	int c_i, c_d, c_r;
	static int MAX = Integer.MAX_VALUE;
	static int UNDEF = -1;

	public EditDistance(int c_i, int c_d, int c_r) {
		this.c_i = c_i;
		this.c_d = c_d;
		this.c_r = c_r;
	}

	public int[][] getEditDistanceDP(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();
		int[][] memoize = new int[m+1][n+1]; // array where memoized values are stored
		for (int i=0; i<m+1;i++){
			for (int j=0; j<n+1; j++){
				memoize[i][j]=-1;
			}
		}
		for (int i = 0; i < m + 1; i++) { // initialisation of memoize
			memoize[i][0] = i*this.c_d;
		}
		for (int j =0;j<n+1;j++){
			memoize[0][j] = j*this.c_i;
		}
		
		for (int k =2; k<=m+n;k++){
			for (int j = Math.max(1, k-m); j<=Math.min(k-1,n); j++){
				int i=k-j;
				if (s1.charAt(i-1)==s2.charAt(j-1)){
					memoize[i][j]=Math.min((memoize[i-1][j-1]), Math.min(memoize[i-1][j]+this.c_d,memoize[i][j-1]+this.c_i));
				}
				else {
					memoize[i][j]=Math.min((memoize[i-1][j-1]+this.c_r), Math.min(memoize[i-1][j]+this.c_d,memoize[i][j-1]+this.c_i));
				}
				
			}
		}
		return (memoize);
	}

	public List<String> getMinimalEditSequence(String s1, String s2) {
		/* To be completed in Part 2. Remove sample code block below. */
		LinkedList<String> ls = new LinkedList<>();
		if (c_r == 6) {
			ls.add("delete(1)");
			ls.add("delete(1)");
			ls.add("insert(2,c)");
			ls.add("insert(3,b)");
		} else {
			ls.add("replace(1,d)");
			ls.add("replace(3,b)");
		}
		return ls;
		/* Code block to be removed ends. */
	}
};
