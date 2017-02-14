#include <stdio.h>
#include <string.h>
#include <stdlib.h>
FILE *ltf,*stf;
int LC=0;						// LOCATION COUNTER
int L=0;
int LTCOUNT=0;
int LITORGINIT=0;
int equflag=0;
char sym[20],nme[20],opd[100];	//symbol , nme , opcode
void POTGET1();					// search pseudo-op table
void MOTGET();					// Search Machine Op-code table
void LTSTO();					// Process Literals
void STSTO();					// Process Symbols
void LITASS();
void LTORG();					// Process LTORG
void main(){
	FILE *fp;
	LC =0;
	fp=fopen("ACHUTE\\SP\\ibm.txt","rt");
	ltf= fopen("ACHUTE\\SP\\lt1.txt","wt");
	stf= fopen("ACHUTE\\SP\\st.txt","wt");
	while(fscanf(fp,"%s %s %s",&sym,&nme,&opd)!=EOF){
		L=0;
		equflag=0;
		POTGET1();
		MOTGET();
		LTSTO();
		//is there any symbol
		if(strcmp(sym,"-")!=0){
			STSTO();
		}

		//LTORG
		if(strcmp(nme,"LTORG")==0){
			LTORG();
		}

	printf("%s %s %s %d \n",sym,nme,opd,LC);

	LC = LC + L;
	}
	fclose(stf);
	fclose(ltf);
	fclose(fp);
	// at the end ASSIGN LITERALS
	if(LITORGINIT>0)
		LITASS();


}
void POTGET1(){
	//Search pseudo op table
	// valid entries are DC DS EQU USING DROP END
	int i=0,count=0,comma=1;
	int j=0,k=0;
	int DLENGTH=0;
	char ch;
	char form[4];
	char value[100];
	if(strcmp(nme,"DC")==0){
		/*
			various senarios :
			DC F'25,26,27'
			DC F'6'
			DC <FORMAT> ' <NUMBERS> '
		*/
		i=0;
		comma=1;
		count=0;
		do{
			ch=opd[i];
			if(count==0){
				//add to format
				form[i]=opd[i];
			}
			if(count==1)
				form[i]='\0';
			if(ch=='\'')
				count++;
			if(ch==',')
				comma++;
			i++;
		}while(count!=2 &&ch!='\0');

		//printf("\n The Format is %s ",form);   			//	<-TESTING
		if(strcmp(form,"F")==0)
			DLENGTH=4;
		if(strcmp(form,"HF")==0)
			DLENGTH=2;
		if(strcmp(form,"C")==0)
			DLENGTH=1;

		DLENGTH=DLENGTH*comma;
		L=DLENGTH;
		//printf(" LC after %s %d \n",nme,LC);
		// if there is symbol call STSTO
	}
	if(strcmp(nme,"DS")==0){
		// DS 2000F   , DS 2F , DS F
		i=0;count=0;
		do{
			ch=opd[i];
			if(isalpha(ch))
				count=1;
			if(count==0){
				value[k]=opd[i];
				k++;
			}
			if(count==1){
				form[j]=opd[i];
				j++;
			}
			i++;
			value[k]='\0';
			form[j]='\0';


		}while(ch!='\0');

		if(strcmp(form,"F")==0)
			DLENGTH=4;
		if(strcmp(form,"HF")==0)
			DLENGTH=2;
		if(strcmp(form,"C")==0)
			DLENGTH=1;

		DLENGTH=atoi(value)*DLENGTH;
		// if there is symbol call STSTO
		L=DLENGTH;
		//printf(" LC after %s %d \n",nme,LC);
	}
	if(strcmp(nme,"EQU")==0){
			// handle in STSTO --> EQU ALSO .. STSTO();
		equflag=1;
	}
	if(strcmp(nme,"USING")==0){
			// ignore
	}
	if(strcmp(nme,"DROP")==0){
			//ignore
	}
	if(strcmp(nme,"END")==0){
		// assign Literals First
		// Handled in LITASS
	}
}
void MOTGET(){
	//search machine op code table
	FILE *mfp;
	char mnme[5],mhex[3],mif[4],mlen[3];
	mfp=fopen("ACHUTE\\SP\\mot.txt","rt");
	while(fscanf(mfp,"%s %s %s %s",&mnme,&mhex,&mif,&mlen)!=EOF){
		if(strcmp(nme,mnme)==0){
			//printf("FOUND MOT\n");
			L = atoi(mlen);
		}
	}
	fclose(mfp);
}
void LTSTO(){
	//processing literals table
	// eg l database,=a(data)
	char len[20];
	char ch[20];
	char RL[10];
	int i=0,j=0;
	int flag=0;
	do
	{
		i++;
		if(opd[i]=='=')
			flag=1;
	}while(opd[i]!='='&&opd[i]!='\0'&&opd[i]!=EOF);
	if(flag==1)
	{
		j=0;
		i++;
   		while(opd[i]!='\0')
		{
			ch[j]=opd[i];
   			j++;
   			i++;
   		}
	ch[j]='\0';
	strcpy(len,"4");
   	strcpy(RL,"R");
   	printf("%s\n",ch );
   	LTCOUNT++;
	fprintf(ltf,"%s %d 4 R \n",ch,LC);
	}
}
void STSTO(){

	 // if EQU has been used
	int length=1;
	char len[10];
	char RL[3];
	char lcs[10];
	itoa(LC,lcs,10);
	strcpy(RL,"R");
	if(strcmp(nme,"DC")==0||strcmp(nme,"DS")==0){
				length=4;
	}
	if(strcmp(nme,"EQU")==0){
		length=1;
		if(opd[0]!='*')
		{
			strcpy(RL,"A");
			strcpy(lcs,opd);
		}
	}
	itoa(length,len,10);
	fprintf(stf,"%s %s % s %s \n",sym,lcs,len,RL);
}
void LITASS()
{
	FILE *ltf2;
	char sym[20],val[20],len[10],RL[3];
	int LLC=0;
	ltf= fopen("ACHUTE\\SP\\lt1.txt","rt");
	ltf2 = fopen("ACHUTE\\SP\\lt.txt","wt");
	LLC=LITORGINIT;
	printf("\n\n %d\n",LITORGINIT);
	while(fscanf(ltf,"%s %s %s %s",&sym,&val,&len,&RL)!=EOF){
		LLC=LLC+4;
		fprintf(ltf2,"%s %d 4 R \n",sym,LLC);
	}
	fclose(ltf2);
	fclose(ltf);
}
void LTORG()
{
	//find next higher multiple of 4
	int LCC;
	LCC=LC+1;
	do{
		LCC ++;
	}while(LCC%4!=0);
	//printf("FOund LCC %d \n",LCC);
	LC=LCC;
	LITORGINIT = LCC;
	LC=LC + 4 * LTCOUNT;
	LCC=LC+1;
	do{
		LCC ++;
	}while(LCC%4!=0);
	LC=LCC;
}
