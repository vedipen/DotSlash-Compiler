/*
Author - @vedipen
*/

import java.util.*;
import java.io.*;

public class Assembler {
	public static BufferedReader fp;
	public static BufferedReader ltf0;
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
		// Search pseudo op table
		int i=0,count=0,comma=1;
		int j=0,k=0;
		int DLENGTH=0;
		char ch;
		char[] form = new char[4];
		char[] value = new char[100];
		if((new String(nme)).equals("DC")) {
			// DC F'25,26,27'
			i=0;
			int j1=0;
			comma=1;
			count=0;
			int opdlen = opd.length;
			int formflag=0;
			do {
				ch=opd[i];
				if(count==0) {
					// Add to format
					form[j1]=opd[i];
					j1++;
					count++;
				}
				form[j1]='\0';
				char ch9='\'';
				if(((ch+"").trim().equals("’"))) {
					count++;
				}
				if(ch==',')
				comma++;
				i++;
			} while(count!=2 && (ch!='\0' && i<opdlen));
			if((new String(form)).trim().equals("F")) {
				DLENGTH=4;
			}
			if((new String(form)).trim().equals("HF")) {
				DLENGTH=2;
			}
			if((new String(form)).trim().equals("C")) {
				DLENGTH=1;
			}
			DLENGTH=DLENGTH*comma;
			L=DLENGTH;
		}
		int opdlen=opd.length;
		if((new String (nme)).equals("DS")){
			i=0;
			count=0;
			do {
				ch=opd[i];
				if(!Character.isDigit(ch)) {
					count=1;
				}
				if(count==0) {
					value[k]=opd[i];
					k++;
				}
				if(count==1) {
					form[j]=opd[i];
					j++;
				}
				i++;
				value[k]='\0';
				form[j]='\0';
			} while(ch!='\0' && i<opdlen);
			if(form.equals("F"))
			DLENGTH=4;
			if(form.equals("HF"))
			DLENGTH=2;
			if(form.equals("C"))
			DLENGTH=1;
			String valstr = new String(value);
			DLENGTH=Integer.parseInt(valstr.trim())*DLENGTH;
			L=DLENGTH;
		}
		if((new String(nme)).equals("EQU")) {
			// Handle in STSTO --> EQU ALSO .. STSTO();
			equflag=1;
		}
		if((new String(nme)).equals("USING")) {
			// Ignore
		}
		if((new String(nme)).equals("DROP")) {
			// Ignore
		}
		if((new String(nme)).equals("END")) {
			// Assign Literals First
			// Handled in LITASS
		}

	}

	public static void MOTGET() {
		// Search Machine Opcode table
		BufferedReader mfp;
		String mnme;
		char [] mhex = new char [3];
		char [] mif = new char [4];
		String mlen;
		try {
			mfp = new BufferedReader(new FileReader("extras/mot.txt"));
			StringTokenizer input = new StringTokenizer(mfp.readLine());
			while(input.countTokens()>=4) {
				mnme=input.nextToken();
				mhex=input.nextToken().toCharArray();
				mif=input.nextToken().toCharArray();
				mlen=input.nextToken();
				if((new String(nme)).equals(mnme)) {
					L = Integer.parseInt(mlen);
				}
				try {
					input = new StringTokenizer(mfp.readLine());
				} catch (Exception e) {
					break;
				}
			}
			mfp.close();
		} catch(Exception e) {
			System.out.println("File mot.txt not found "+e);
		}
	}

	public static void LTSTO() {
		// Processing literals table
		// Eg l database,=a(data)
		char [] len = new char[20];
		char [] ch = new char[20];
		char [] RL = new char[10];
		int i=0,j=0;
		int flag=0;
		int opdlen = opd.length;
		if(opdlen>1) {
			do {
				i++;
				if(opd[i]=='=')
				flag=1;
			} while(opd[i]!='=' && (opd[i]!='\0' && i<opdlen-1));
		}
		if(flag==1) {
			j=0;
			i++;
			while(opd[i]!='\0' && i<=opdlen-1) {
				ch[j]=opd[i];
				j++;
				i++;
				if(i>opdlen-1) {
					break;
				}
			}
			ch[j]='\0';
			len="4".toCharArray();
			RL="R".toCharArray();
			// System.out.println(new String(ch));
			LTCOUNT++;
			try {
				ltf.write((new String(ch))+" "+LC+" "+"4 R\n");
			} catch(Exception e) {
				System.out.println("Couldn't write to file "+e);
			}
		}
	}

	public static void STSTO() {
		// If EQU has been used
		int length=1;
		char [] len = new char[10];
		char [] RL = new char[3];
		char [] lcs = new char[10];
		lcs=(LC+"").toCharArray();
		RL="R".toCharArray();
		if((new String(nme)).equals("DC") || (new String(nme)).equals("DS")) {
			length=4;
		}
		if((new String(nme)).equals("EQU")){
			length=1;
			if(opd[0]!='*') {
				RL="A".toCharArray();
				lcs=(new String(opd)).toCharArray();
			}
		}
		len=(length+"").toCharArray();
		try {
			stf.write((new String(sym))+" "+(new String(lcs))+" "+(new String(len))+" "+(new String(RL))+"\n");
		} catch(Exception e) {
			System.out.println("Couldn't write to file "+e);
		}
	}

	public static String opdid(String s) throws IOException {
		try {
			BufferedReader brst = new BufferedReader(new FileReader("extras/st.txt"));
			BufferedReader brlt = new BufferedReader(new FileReader("extras/lt.txt"));
			String inputst = brst.readLine();
			String inputlt = brlt.readLine();
			int idst=1;
			while(inputst!=null) {
				StringTokenizer inputToken = new StringTokenizer(inputst);
				String labelid = inputToken.nextToken();
				if(s.equals(labelid.trim())) {
					return ("ID#"+idst);
				}
				idst++;
				inputst=brst.readLine();
			}
			int idlt=1;
			while(inputlt!=null) {
				StringTokenizer inputTokenL = new StringTokenizer(inputlt);
				String labelidL = inputTokenL.nextToken();
				if(s.equals(labelidL.trim())) {
					return ("LT#"+idlt);
				}
				idlt++;
				inputlt=brlt.readLine();
			}
		} catch (Exception e) {
			System.out.println("Cant get ID "+e);
			return s;
		}
		return s;
	}

	public static void LITASS() {
		try {
			BufferedWriter ltf2;
			char [] sym = new char[20];
			char [] val = new char[20];
			char [] len = new char[10];
			char [] RL = new char[3];
			int LLC=0;
			ltf0 = new BufferedReader(new FileReader("extras/lt1.txt"));
			ltf2 = new BufferedWriter(new FileWriter("extras/lt.txt"));
			LLC=LITORGINIT;
			// LC at first literal
			// System.out.println("\n\n"+LITORGINIT);
			String ltf0str = ltf0.readLine();
			StringTokenizer input;
			while(ltf0str.trim().length()>0) {
				input = new StringTokenizer(ltf0str);
				sym=input.nextToken().toCharArray();
				val=input.nextToken().toCharArray();
				len=input.nextToken().toCharArray();
				RL=input.nextToken().toCharArray();
				ltf2.write((new String(sym))+" "+LLC+" 4 R\n");
				LLC=LLC+4;
				ltf0str = ltf0.readLine();
			}
			ltf2.close();
			ltf0.close();
		} catch(Exception e) {
			System.out.println("File not found. " +e);
		}
	}

	public static void LTORG() {
		//find next higher multiple of 4
		int LCC;
		LCC=LC+1;
		LITORGINIT = LC;
		LC=LC + 4 * LTCOUNT;
		LCC=LC;
		while(LCC%4!=0) {
			LCC ++;
		}
		LC=LCC;
	}

	public static void main(String args[]) throws Exception {
		try {
			fp = new BufferedReader(new FileReader("extras/input1.asm"));
			ltf = new BufferedWriter(new FileWriter("extras/lt1.txt"));
			stf = new BufferedWriter(new FileWriter("extras/st.txt"));
		} catch (Exception e) {
			System.out.println("File not found "+e);
		}
		StringTokenizer input = new StringTokenizer(fp.readLine());
		Vector<String> save = new Vector<String>();
		Vector<String> saveId = new Vector<String>();
		Vector<String> saveLC = new Vector<String>();
		while(input.countTokens()>=1) {
			if(input.countTokens()<=2) {
				sym="-".toCharArray();
			} else {
				sym=input.nextToken().toCharArray();
			}
			nme=input.nextToken().toCharArray();
			if(input.hasMoreTokens()) {
				opd=input.nextToken().toCharArray();
			}
			L=0;
			equflag=0;
			POTGET1();
			MOTGET();
			LTSTO();
			// Check for symbol
			if(!(new String(sym)).equals("-")) {
				STSTO();
			}
			// LTORG
			if((new String(nme)).equals("LTORG")) {
				LTORG();
			}
			int k1=0;
			String opdNo="",opdLabel="";
			for(int k=0;k<opd.length;k++) {
				if(opd[k]!=',') {
					opdNo+=opd[k];
				} else if(opd[k]==','){
					opdNo+=opd[k];
					k1=k;
					break;
				}
			}
			if(k1<opd.length-1 && k1>0) {
				if(opd[k1+1]=='=') {
					opdNo+=opd[k1+1];
					k1++;
				}
				for(int k=k1+1;k<opd.length;k++) {
					opdLabel+=opd[k];
				}
			}
			save.add((new String(sym))+" "+(new String(nme))+" "+opdNo);
			saveId.add(opdLabel);
			saveLC.add(" "+LC);
			// Print code with LC
			// System.out.println((new String(sym))+" "+(new String(nme))+" "+opdNo+opdLabel+" "+LC);
			LC=LC+L;
			try {
				input=new StringTokenizer(fp.readLine());
			} catch (Exception e) {
				break;
			}
		}
		ltf.write("  ");
		stf.close();
		ltf.close();
		fp.close();
		//Literals
		if(LITORGINIT>0)	{
			LITASS();
		}
		try {
			BufferedWriter ic = new BufferedWriter(new FileWriter("extras/intermediate_pass1.txt"));
			ic.write("------------------INTERMEDIATE CODE Generated By PASS 1---------------------\n\n");
			for(int x=0;x<save.size();x++) {
				ic.write(saveLC.get(x)+" "+save.get(x)+opdid(saveId.get(x))+"\n");
			}
			ic.close();
		} catch(Exception e) {
			System.out.println("Couldn't write intermediate table "+e);
		}
		//For Reference
		System.out.println("\nPass 1\n");
		System.out.println("Input code for IBM 360 Assembler used is in extras/input1.asm");
		System.out.println("Input Machine Opcode Table used is in extras/mot.txt\n");
		System.out.println("Symbol Table is written in extras/st.txt");
		System.out.println("Literal Table is written in extras/lt.txt");
		System.out.println("Intermediate code is written in extras/intermediate_pass1.txt\n");
	}
}
