/**
 * INF 421 DM2 self-test framework. This file is provided for your benefit only.
 * It is supposed to help you test your code at home. If you don't find it helpful,
 * don't use it.
 * 
 * Last modified Nov 2017.
 * 
 * Please report bugs/issues to <adrian.kosowski@inria.fr>.
 */

import java.util.*;

class Main {
    
    static Random rGen = new Random(0);
    
    public static String getRandomString(int length) {
        return getRandomString (length, "abcdefghijklmnopqrstuvwxyz");
    }
    
    public static String getRandomString(int length, final String alphabet) {
       StringBuilder result = new StringBuilder();
       while(length > 0) {
           result.append(alphabet.charAt(rGen.nextInt(alphabet.length())));
           length--;
       }
       return result.toString();
    }

    public static boolean preValidateSequence(List<String> es, String s1, String s2)
    {
        StringBuilder s = new StringBuilder(s1);
        for (String op : es) {

            try
            {
                String [] args = op.split("[\\(\\)\\,]", 20);
                char command = 0;
                if(args[0].startsWith("replace"))
                    command = 'r';
                else if(args[0].startsWith("delete"))
                    command = 'd';
                else if(args[0].startsWith("insert"))
                    command = 'i';
                else throw new Exception ("Invalid command");
                if (!((args.length == 3 && command == 'd') ||
                        (args.length == 4 && (command == 'r' || command == 'i'))))
                    throw new Exception ("Invalid syntax or unsupported character {, ( )} in text string");
                int pos = 0;
                pos = Integer.parseInt(args[1]);
                if (pos < 0 || pos > s.length() || (pos >= s.length() && (command=='r' || command =='d')))
                    throw new Exception("Position outside of string range");
                char c = 0;
                if (command == 'r' || command == 'i'){
                    if (args[2].length() != 1)
                        throw new Exception ("Invalid replacement character");
                    c = args[2].charAt(0);
                }
                switch (command) {
                    case 'r':
                        s.setCharAt(pos, c);
                        break;
                    case 'd':
                        s.deleteCharAt(pos);
                        break;
                    case 'i':
                        s.insert(pos, c);
                        break;
                }
            }
            catch (Exception e){
                System.err.println("Parse exception: "+e.getMessage());
                System.err.println("Offending operation: " + op);                
                System.err.println("Current string: " + s);    
                return false;
            }
        }
        if (! s2.equals(s.toString())){
            System.err.println("The final string reached is not the intended one.");
            System.err.println("Objective: "+s2);
            System.err.println("Reached:   "+s);
            return false;
        }
        System.out.println("Edit Sequence OK. Note that the cost of the Edit Sequence is not being preValidateSequenced (!)");
        return true;
    }

    public static void test1(String s1,String s2) {
        EditDistance ed = new EditDistance(3,2,1);
        int[][] d = ed.getEditDistanceDP(s1,s2);
        System.out.println("Transforming "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,1)");
        System.out.println("Cost = "+d[s1.length()][s2.length()]);

        ed = new EditDistance(3,2,6);
        d = ed.getEditDistanceDP(s1,s2);
        System.out.println("Transforming "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,6)");
        System.out.println("Cost = "+d[s1.length()][s2.length()]);
    }


    public static void test2(String s1,String s2) {
        EditDistance ed = new EditDistance(3,2,1);
        int[][] d = ed.getEditDistanceDP(s1,s2);
        System.out.println("Transforming "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,1)");
        System.out.println("Cost = "+d[s1.length()][s2.length()]);
        int tot = 0;
        for (int i = 0; i <= s1.length(); i++)
            for (int j = 0; j <= s2.length(); j++)
                if (d[i][j] != -1) tot++;
        System.out.println("Values Computed = "+tot);

        ed = new EditDistance(3,2,6);
        d = ed.getEditDistanceDP(s1,s2);
        System.out.println("Transforming "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,6)");
        System.out.println("Cost = "+d[s1.length()][s2.length()]);
        tot = 0;
        for (int i = 0; i <= s1.length(); i++)
            for (int j = 0; j <= s2.length(); j++)
                if (d[i][j] != -1) tot++;
        System.out.println("Values Computed = "+tot);
    }
    
    public static void test3(String s1,String s2) {
        EditDistance ed = new EditDistance(3,2,1);
        List<String> m = ed.getMinimalEditSequence(s1,s2);
        System.out.println("Minimal edits from "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,1):");
        for (String s: m) System.out.println(s);
        preValidateSequence (m, s1, s2);
        
        ed = new EditDistance(3,2,6);
        m = ed.getMinimalEditSequence(s1,s2);
        System.out.println("Minimal edits from "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,6):");
        for (String s: m) System.out.println(s);
        preValidateSequence (m, s1, s2);
    }

    public static void test4() {
        EditDistance ed = new EditDistance(3,2,1);
        String s1 = "Mr. Sherlock Holmes+ who was usually very late in the mornings+ save upon those not infrequent occasions when he was up all night+ was seated at the breakfast table. I stood upon the hearth-rug and picked up the stick which our visitor had left behind him the night before. It was a fine+ thick piece of wood+ bulbous-headed+ of the sort which is known as a \"Penang lawyer.\" Just under the head was a broad silver band nearly an inch across. \"To James Mortimer+ M.R.C.S.+ from his friends of the C.C.H.+\" was engraved upon it+ with the date \"1884.\" It was just such a stick as the old-fashioned family practitioner used to carry -- dignified+ solid+ and reassuring.";
        String s2 = "Chapter I. Mr. Sherlock Holmes+ who was usually very late in the mornings+ save upon those not infrequent occasions when he was up all night+ was seated at the breakfast table. I stood upon the hearth-rug and picked up the walking stick which our visitor had left behind him the night before. It was a fine+ thick piece of wood+ of the sort which is known as a \"Penang lawyer.\" Just under the head was a broad silver band nearly an inch across. \"To James Mortimer+ M.R.C.S.+ from his friends of the C.C.H.+\" was engraved upon it+ with the date \"1984.\" It was just such a stick as the old-fashioned family practitioner used to carry -- dignified+ solid+ and reassuring. \"Well+ Watson+ what do you make of it?\"";

        int[][] d = ed.getEditDistanceDP(s1,s2);
        System.out.println("Transforming "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,6)");
        System.out.println("Cost = "+d[s1.length()][s2.length()]);

        List<String> m = ed.getMinimalEditSequence(s1,s2);
        System.out.println("Minimal edits from "+s1+" to "+s2+" with (c_i,c_d,c_r) = (3,2,1):");
        for (String s: m) System.out.println(s);
        preValidateSequence (m, s1, s2);
    }
    
    public static void main(String[] args) {

        String s1 = "abcd";
        String s2 = "adcb";
        test1(s1,s2);
        test2(s1,s2);
        test3(s1,s2);
        
        test3(getRandomString(33), getRandomString(42));
        
        test3(getRandomString(33, "ab"), getRandomString(42, "ab"));
        
        test4();
        
    }
}
