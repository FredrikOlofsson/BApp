package BApp.CashMachine.IO;

import java.util.Scanner;

public class Keypad {
    private Scanner input;
    
    public Keypad(){
        input = new Scanner(System.in);
    }
    ////////////////////////////////////
    
    public int getInt(){
        return input.nextInt();
    }
    public String getString(){ //used for string password
        return input.next();
    }
}
