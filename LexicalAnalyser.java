import java.util.*;

import java.io.*;



class Main {

    public static Set<String> keyWords = new HashSet<String> () {{

       add("include");

       add("int");

       add("main");

       add("return");

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

    public static void main (String[] args) throws IOException {

        //FileInputStream fstream = new FileInputStream("input.c");

        //BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      	String input;

        while((input=br.readLine()) != null) {

            // StringTokenizer in = new StringTokenizer(input, "\t(){}<>#;+\",");

            int len = input.length();

            for(int i=0;i<len;i++) {

            	if(input.charAt(i)=='\t' || input.charAt(i)=='\n' || input.charAt(i)==' ') {

            		continue;

            	}

            	// System.out.println((input.charAt(i)));

            	if(specialSymbols.contains(input.charAt(i)+"")) {

            		i++;

            		if(input.charAt(i)=='\t' || input.charAt(i)=='\n' || input.charAt(i)==' ') {

            			continue;

            		}

            		String word="";

            		while(!specialSymbols.contains(input.charAt(i)+"")) {

            			word+=input.charAt(i);

            			i++;

            		}

            		i--;

            		System.out.println(word);

            	}

            }

            //System.out.println(input);

        }

        br.close();

    }

}