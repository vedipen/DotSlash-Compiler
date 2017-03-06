/*
Author - @vedipen
*/

import java.util.*;
import java.io.*;

public class MacroAssembler {
	public static BufferedReader inp;
  static ArrayList<String> macNameVec = new ArrayList<String>();
  static ArrayList<String> argumentList = new ArrayList<String>();
  static ArrayList<String> defaultList = new ArrayList<String>();

	public static void main(String args[]) throws Exception {
		try {
			inp = new BufferedReader(new FileReader("extras/input.asm"));
  		StringTokenizer input = new StringTokenizer(inp.readLine());
      while(input.hasMoreTokens()) {
        if(input.countTokens()==1) {
          String firstToken=input.nextToken();
          if(firstToken.equals("MACRO")) {
            input = new StringTokenizer(inp.readLine());
            firstToken = input.nextToken(); // For Macro Name
            macNameVec.add(firstToken);
            if(input.hasMoreTokens()) {
              String arguments = input.nextToken();
              System.out.println("Length is "+arguments);
              for(int i=0;i<arguments.length();i++) {
                while(arguments.charAt(i)=='&') {
                  i++;
                  String arg="";
                  String def="";
                  if(i>=arguments.length()) {
                    break;
                  }
                  while(i<arguments.length() & arguments.charAt(i)!=',') {
                    if(arguments.charAt(i)=='=') {
                      i++;
                      if(i>=arguments.length()) {
                        break;
                      }
                      while(i<arguments.length() & arguments.charAt(i)!=',') {
                        def+=arguments.charAt(i)+"";
                        i++;
                        if(i>=arguments.length()) {
                          break;
                        }
                      }
                      if(i>=arguments.length()) {
                        break;
                      }                    }
                    arg+=arguments.charAt(i)+"";
                    i++;
                    if(i>=arguments.length()) {
                      break;
                    }
                  }
                  argumentList.add(arg);
                  defaultList.add(def);
                  i++;
                }
              }
            } // For arguments
            String input0 = inp.readLine().trim();
            input = new StringTokenizer(input0);
            firstToken = input.nextToken(); // For Macro Name
            String instr="";
            while(!firstToken.equals("MEND")) {
              instr+=input0+"\n";
              input0 = inp.readLine().trim();
              input = new StringTokenizer(input0);
              firstToken = input.nextToken(); // For MEND
            }
            if(firstToken.equals("MEND")) {
              instr+=input0+"\n";
            }
          } else if(firstToken.equals("END")) {
              System.out.println("END");
          }
        }
        input=new StringTokenizer(inp.readLine());
      }
    } catch (Exception e) {
      System.out.println("File not found "+e);
    }
  }
}
