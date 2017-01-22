import java.util.*;
import java.io.*;

class dotSlash {
    public static void main (String[] args) throws IOException {
        FileInputStream fstream = new FileInputStream("input.c");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String input;
        while((input=br.readLine()) != null) {
            System.out.println(input);
        }
        br.close();
    }
}
