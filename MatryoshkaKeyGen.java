package matryoshka;

import java.io.*;
import java.util.*;

//Author: Eric Wenzke
public class Matryoshka {

    //This program is a key generator for the Matryoshka.exe file. It does not take input, and returns the five passwords
    //for the Matryoshka program as it runs on your computer. It contains some sloppy code. It is intended to be run on a 
    //Windows machine.
    public static void main(String[] args) throws IOException{
        String password4 = "";
        String password5 = "";

        
        //The first password takes the string "ABCEDF123456", and adds 5 to each character's hexadecimal value.
        //It then compares that to the users' input values. As such, we add 5 to the hexadecimal values of the string
        //to obtain the password.
        char[] tmp1 = { 'A', 'B', 'C', 'D', 'E', 'F', '1', '2', '3', '4', '5', '6' };
        for (int i=0; i < tmp1.length; i++) {
            tmp1[i] =  (char) (tmp1[i] + 5);
        }
        String password1 = new String(tmp1);
        System.out.println("Password 1: " + password1);
        
        
        //The second password adds the counter ESI to the user's inputted character, and compares it to the string
        //"ARCHIEMILLER". Therefore, for the ith character, we must subtract i to find the correct input character.
        char[] tmp2 = { 'A', 'R', 'C', 'H', 'I', 'E', 'M', 'I', 'L', 'L', 'E', 'R' };
        for (int i=0; i < tmp2.length; i++) {
            tmp2[i] = (char) (tmp2[i] - i);
        }    
        String password2 = new String(tmp2);
        System.out.println("Password 2: " + password2);
        
        
        //The third password first XORs the ith user's inputted character with the corresponding ith character
        //of the string "GO FLYERS!!!". It then compares this result to the ESI counter. Therefore, to obtain the character
        //necessary, we XOR each character of "GO FLYERS!!!" with its corresponding pointer value.
        //                  x XOR [i]"GO FLYERS!!!" = y
        //                  y XOR [i]"GO FLYERS!!!" = x
        char[] tmp3 = { 'G', 'O', ' ', 'F', 'L', 'Y', 'E', 'R', 'S', '!', '!', '!' };
        for (int i=0; i < tmp3.length; i++) {
            tmp3[i] = (char) (tmp3[i] ^ i);
        }
        String password3 = new String(tmp3);
        System.out.println("Password 3: " + password3);
        
        
        //The fourth password finds the serial number of the C:// volume on the computer it is run on. It then converts this
        //number from a hexadecimal value to a decimal value, and compares that to the user's input. Therefore, to obtain the
        //password, a command is run on the command line to obtain the serial number, and it is converted to a 12-digit decimal valueaccordingly.
        String command = "cmd /c vol C:";
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader In = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        String line = null;
        while (In.readLine() != null) {
            line = In.readLine();
            if (line.contains("Serial")) {
                password4 = line.substring((line.indexOf("is ") + 3), line.length());
            }
        }
        password4 = password4.replace("-", "");
        long pw4 = Integer.parseUnsignedInt(password4, 16);
        System.out.println("Password 4: " + pw4);
       
        
        //The fifth password obtains the MAC address of the first network interface that appears after
        //calling the GetAdaptersAddresses method. It then looks for the first four bytes of the MAC address,
        //in reverse order and compares them to the user's input. Similar to password 4, we run a command
        //and find the first MAC address, and convert it to the correct password output.
        command = "cmd /c ipconfig /all";
        Process p2 = Runtime.getRuntime().exec(command);
        BufferedReader In2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        
        while(In2.readLine() != null) {
            line = In2.readLine();
            if (line.contains("Physical Address")) {
                password5 = line.substring((line.indexOf(". : ") + 4), line.length());
                break;
            }
        }
        password5 = password5.replace("-", "");
        password5 = password5.substring(0, password5.length()-4);
        String[] MAC = new String[4];
        for (int i=3;i>=0;i--) {
            MAC[i] = password5.substring(password5.length()-(i*2 + 2), password5.length()-(i*2));
        }
        System.out.println("Password 5: " + MAC[0] + MAC[1] + MAC[2] + MAC[3]);

    }
    
}
