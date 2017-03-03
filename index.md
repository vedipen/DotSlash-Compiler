# ./ Compiler
My journey of learning Compiler Construction and developing my very own **./ Compiler**

## Lexical Analyser
***C branch*** contains code of Lexical Analyser for C language    

### To Run
```
> javac LexicalAnalyser.java
> java dotSlash
```
#### File Structure
<pre>
Input code for Lexical Analyser used is  /input.c    

Final Output is written in CLI itself</pre>

## 2 Pass Assembler
***Assembly branch**** contains both Pass 1 and Pass 2 of Two Pass Assembler IBM 360/370 architecture in Java 8    

### To Run
```
> javac Assembler
> java Assembler
```
#### File Structure
<pre>
Input code for IBM 360 Assembler used is in extras/input1.asm  
Input Machine Opcode Table used is in extras/mot.txt  

Symbol Table is written in extras/st.txt  
Literal Table is written in extras/lt.txt  
Intermediate code of pass 1 is written in extras/intermediate_pass1.txt  

Final Output of pass 2 is written in extras/pass2output.txt</pre>
