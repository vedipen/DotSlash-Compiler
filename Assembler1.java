import java.util.*;
import java.io.*;

public class Assembler1 {
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
        int i=0,count=0,comma=1;
        int j=0,k=0;
        int DLENGTH=0;
    	char ch;
    	char[] form = new char[4];
    	char[] value = new char[100];
    	if(nme.equals("DC")) {
			// various senarios :
			// DC <FORMAT> ' <NUMBERS> '
    		i=0;
    		comma=1;
    		count=0;
			int opdlen = opd.length;
			int formflag=0;
			do {
    			ch=opd[i];
				if(count==0) {
    				//add to format
    				form[i]=opd[i];
				}
    			if(count==1 && formflag==0) {
    				form[i]='\0';
					formflag=1;
				}
    			if(ch=='\'') {
    				count++;
				}
    			if(ch==',')
    				comma++;
    			i++;
				System.out.println(i);
    		} while(count!=2 && (ch!='\0' && i<opdlen));
            if(form.equals("F"))
    			DLENGTH=4;
    		if(form.equals("HF"))
    			DLENGTH=2;
    		if(form.equals("C"))
    			DLENGTH=1;
    		DLENGTH=DLENGTH*comma;
    		L=DLENGTH;
    	}
		int opdlen=opd.length;
    	if(nme.equals("DS")){
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
    	if(nme.equals("EQU")) {
    			// handle in STSTO --> EQU ALSO .. STSTO();
    		equflag=1;
    	}
    	if(nme.equals("USING")) {
    			// ignore
    	}
    	if(nme.equals("DROP")) {
    			//ignore
    	}
    	if(nme.equals("END")) {
    		// assign Literals First
    		// Handled in LITASS
    	}

	}

	public static void MOTGET() {
		//search machine op code table
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
				if(nme.equals(mnme)) {
					L = Integer.parseInt(mlen);
				}
			}
			mfp.close();
		} catch(Exception e) {
			System.out.println("File mot.txt not found");
		}
	}

	public static void LTSTO() {
		//processing literals table
		// eg l database,=a(data)
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
	   		while(opd[i]!='\0' && i<opdlen-1) {
				ch[j]=opd[i];
	   			j++;
	   			i++;
	   		}
			ch[j]='\0';
			len="4".toCharArray();
		   	RL="R".toCharArray();
			System.out.println(ch);
		   	LTCOUNT++;
			try {
				ltf.write(ch+" "+LC+" "+"4 R\n");
			} catch(Exception e) {
				System.out.println("Couldn't write to file.");
			}
		}
	}

	public static void STSTO() {
		// if EQU has been used
	   int length=1;
	   char [] len = new char[10];
	   char [] RL = new char[3];
	   char [] lcs = new char[10];
	   lcs=(LC+"").toCharArray();
	   RL="R".toCharArray();
	   if(nme.equals("DC") || nme.equals("DS")) {
	   		length=4;
	   }
	   if(nme.equals("EQU")){
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
			System.out.println("Couldn't write to file");
		}
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
			System.out.println("\n\n"+LITORGINIT);
			String ltf0str = ltf0.readLine();
			StringTokenizer input;
			while(ltf0str.length()>0) {
				input = new StringTokenizer(ltf0str);
	    		sym=input.nextToken().toCharArray();
	    		val=input.nextToken().toCharArray();
	    		len=input.nextToken().toCharArray();
				RL=input.nextToken().toCharArray();
				LLC=LLC+4;
				ltf2.write((new String(sym))+" "+LLC+" 4 R\n");
				ltf0str = ltf0.readLine();
				System.out.println(ltf0str.length());
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
		do {
			LCC++;
		}while(LCC%4!=0);
		LC=LCC;
		LITORGINIT = LCC;
		LC=LC + 4 * LTCOUNT;
		LCC=LC+1;
		do{
			LCC ++;
		} while(LCC%4!=0);
		LC=LCC;
	}

	public static void main(String args[]) throws Exception {
		try {
			fp = new BufferedReader(new FileReader("extras/input1.asm"));
			ltf = new BufferedWriter(new FileWriter("extras/lt.txt"));
			stf = new BufferedWriter(new FileWriter("extras/st.txt"));
		} catch (Exception e) {
			System.out.println("File not found.");
		}
		StringTokenizer input = new StringTokenizer(fp.readLine());
		while(input.countTokens()>=3) {
    		sym=input.nextToken().toCharArray();
    		nme=input.nextToken().toCharArray();
    		opd=input.nextToken().toCharArray();
    		LC=0;
    		equflag=0;
    		POTGET1();
    		MOTGET();
    		LTSTO();
    		//check for symbol
    		if(!(new String(sym)).equals("-")) {
    			STSTO();
    		}
    		// LTORG
    		if((new String(nme)).equals("LTORG")) {
    			LTORG();
    		}
    		System.out.println((new String(sym))+" "+(new String(nme))+" "+(new String(opd))+" "+LC);
    		LC+=L;
			input=new StringTokenizer(fp.readLine());
		}
		stf.close();
		ltf.close();
		fp.close();
		//Literals
		if(LITORGINIT>0)	{
			LITASS();
		}
	}
}
