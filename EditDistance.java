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

	public int getEditDistanceDPminimal(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();
		int[][] memoize = new int[m+1][n+1]; // array where memoized values are stored
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
		return (memoize[m][n]);
	}
	
	public int[][] getEditDistanceDP(String s1, String s2,int x) {
		int m = s1.length();
		int n = s2.length();
		int[][] memoize = new int[m+1][n+1]; // array where memoized values are stored
		for (int i=0; i<m+1;i++){
			for (int j=0; j<n+1; j++){
				if (Math.abs(i-j) >Math.abs(n-m)+x){
					memoize[i][j]=MAX;
				}
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
				if (memoize[i][j]==MAX){
					continue;
				}
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
		int x=1;
		int i=s1.length();
		int j=s2.length();
		int[][] tab;
		int trueValue= getEditDistanceDPminimal(s1, s2);
		LinkedList<String> path = new LinkedList<String>();
		while (true){
			tab=getEditDistanceDP(s1, s2, x);
			if (tab[s1.length()][s2.length()]==trueValue){
				break;
			}
			x++;
		}
		Test.affiche(getEditDistanceDP(s1, s2));
		//we find the path
		while (i!=0 || j!=0){
			
			if (i>0 && j>0 && s1.charAt(i-1)==s2.charAt(j-1)&&tab[i][j]==tab[i-1][j-1]){
				i--;
				j--;
			}
			else if (i>0 && j>0 && s1.charAt(i-1)!=s2.charAt(j-1)&&tab[i][j]==tab[i-1][j-1]+this.c_r){
				i--;
				j--;
				path.addLast("r"+(i)+s2.charAt(j));
				
				
			}
			else if (i>0 && tab[i][j]==tab[i-1][j]+this.c_d){
				i--;
				path.addLast("d"+i);
				
				
			}
			else if (j>0 && tab[i][j]==tab[i][j-1]+this.c_i){
				path.addLast("i"+(i)+s2.charAt(j-1));
				j--;
			}
			System.out.println(i+ " "+j);
		}
			int compteur=0;
			LinkedList<String> finalPath = new LinkedList<String>();
			for (String s : path){
				if (s.charAt(0)=='r'){
					finalPath.addLast("replace("+(Character.getNumericValue(s.charAt(1))+compteur)+","+s.charAt(2)+")");
				}
				else if (s.charAt(0)=='d'){
					finalPath.addLast("delete("+(Character.getNumericValue(s.charAt(1))+compteur)+")");
					compteur--;
				}
				else if (s.charAt(0)=='i'){
					finalPath.addLast("insert("+(Character.getNumericValue(s.charAt(1))+compteur)+","+s.charAt(2)+")");
					compteur++;
				}
			}
			
		
		return finalPath;
	}

	
	public int[][] getEditDistanceDP(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();
		int[][] memoize = new int[m+1][n+1]; // array where memoized values are stored
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
};
