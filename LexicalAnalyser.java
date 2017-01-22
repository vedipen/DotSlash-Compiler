import java.util.*;
import java.io.*;

class dotSlash {
    public Set<String> keyWords = new HashSet<String> () {{
       add("include");
       add("int");
       add("main");
       add("return");
    }};
    public Set<String> inbuiltFunctions = new HashSet<String> () {{
        add("printf");
        add("scanf");
    }};
    public Set<String> specialSymbols = new HashSet<String> () {{
        add("(");
        add(")");
        add("{");
        add("}");
        add("<");
        add(">");
        add("#");
        add(";");
        add("+");
        add("\"");
        add(",");
    }};
    public Set<String> headerFiles = new HashSet<String> () {{
        add("stdio.h");
    }};
    public static void main (String[] args) throws IOException {
        FileInputStream fstream = new FileInputStream("input.c");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String input;
        while((input=br.readLine()) != null) {
            StringTokenizer in = new StringTokenizer(input, "\t(){}<>#;+\",");
            while(in.hasMoreElements()) {
                System.out.println(in.nextToken());
            }
            //System.out.println(input);
        }
        br.close();
    }
}
