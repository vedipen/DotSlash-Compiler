import java.util.*;
import java.io.*;

class dotSlash {
    public static Set<String> keyWords = new HashSet<String> () {{
       add("include");
       add("int");
       add("main");
       add("return");
       add("if");
       add("else");
       add("return 0");
    }};
    public static Set<String> inbuiltFunctions = new HashSet<String> () {{
        add("printf");
        add("scanf");
    }};
    public static Set<String> specialSymbols = new HashSet<String> () {{
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
    public static Set<String> headerFiles = new HashSet<String> () {{
        add("stdio.h");
    }};
    public static Set<String> dataTypes = new HashSet<String> () {{
	add("int");
	add("char");
	add("float");
	add("long");
	add("double");
    }};
    public static void main (String[] args) throws IOException {
        FileInputStream fstream = new FileInputStream("input.c");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String input;
        while((input=br.readLine()) != null) {
            StringTokenizer in = new StringTokenizer(input, "\t(){}<>#;+\"");
            while(in.hasMoreElements()) {
		String temp=in.nextToken().trim();
		if(temp.length()!=0) {
			if(keyWords.contains(temp)) {
				System.out.println("Keyword : "+temp);
				if(temp.equals("if")) {
					System.out.println("Condition : "+in.nextToken().trim());
				}
				continue;
			}
			if(inbuiltFunctions.contains(temp)) {
				System.out.println("InbuiltFunction : "+temp);
				continue;
			}
			if(specialSymbols.contains(temp)) {
				System.out.println("Special Symbol : "+temp);
				continue;
			}
			if(headerFiles.contains(temp)) {
				System.out.println("Header File : "+temp);
				continue;
			}
			StringTokenizer in1 = new StringTokenizer(temp," \n");
			String temp1=in1.nextToken();
			if(dataTypes.contains(temp1)) {
				String temp2=in1.nextToken();
				if(temp2.equals("main")) {
					System.out.println("Function : "+temp2+", with return type : "+temp1);
				} else {
				  System.out.println("DataType : "+temp1);
					System.out.println("Variables : "+temp2);
					while(in1.hasMoreTokens()) {
						System.out.println("Variables : "+in1.nextToken());
					}
				}
				continue;
			}
			if(!temp.contains("%") && !temp.contains("&") && !temp.contains("||") && !temp.contains("&&")) {
				System.out.println("Instruction for user : "+temp);
				continue;
			}
			if(temp.contains("||") || temp.contains("&&")) {
				System.out.println("Condition : "+temp);
				continue;
			}
			if(temp1.contains("&") || temp1.contains("%")) {
				StringTokenizer temp3=new StringTokenizer(temp1,",");
				String temp4=temp3.nextToken();
				System.out.println("Special Symbol : "+temp4.charAt(0));
				System.out.println("Variable Name : "+temp4.substring(1));
				continue;
			}
			System.out.println("I am left to process "+temp);
		}
            }
            //System.out.println(input);
        }
        br.close();
    }
}
