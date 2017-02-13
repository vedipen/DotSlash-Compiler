import java.util.*;
import java.io.*;

public class Assembler {	
	public static BufferedReader fp;
	public static BufferedWriter ltf;
	public static BufferedWriter stf;
	public static int LC=0;
	public static int L=0;
	public static int LTCOUNT=0;
	public static int LITORGINIT=0;
	public static int equflag=0;
	public static char[] sym = new char[20];
	public static char[] nme = new char[20];
	public static char[] opd = new char[100];
	public static void POTGET1() {
	}
	
	public static void MOTGET() {
	}

	public static void LTSTO() {
	}

	public static void STSTO() {
	}

	public static void LITASS() {
	}

	public static void LTORG() {
	}
	
	public static void main(String srgs[]) throws Exception {
		try {
			fp = new BufferedReader(new FileReader("extras/input.asm"));
			ltf = new BufferedWriter(new FileWriter("extras/lt.txt"));
 			stf = new BufferedWriter(new FileWriter("extras/st.txt"));
		} catch (Exception e) {
			System.out.println("File not found.");	
		}
		LC=0;
	}

}
