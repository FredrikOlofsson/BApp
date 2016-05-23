package BApp.CashMachine;
import BApp.CashMachine.Data.Account;
import BApp.CashMachine.Data.*;
import BApp.CashMachine.IO.Keypad;
import BApp.CashMachine.IO.Screen;
import BApp.CashMachine.Parts.CashDispenser;
import BApp.CashMachine.Parts.DepositSlot;
import BApp.CashMachine.Transactions.Deposit;
import BApp.CashMachine.Transactions.RequestBalance;
import BApp.CashMachine.Transactions.Transaction;
import BApp.CashMachine.Transactions.Withdrawal;
import java.io.File;
import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class CashMachine {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private AccountList bankDatabase;
    
    static DocumentBuilderFactory dbFactory = null;
    static DocumentBuilder dBuilder = null;
    static Document doc_artiklar = null;

    
    //menu options
    
    private static final int LOG_IN = 1;
    private static final int REGISTER_NEW = 2;
    
    private static final int BALANCE_REQUEST = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;
    
    public CashMachine() throws JAXBException{
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new AccountList();
    }
    ///////////////////
    
    public void run() throws JAXBException{
        //authenticates user; perform transactions
        while(true){
            unpackXML();
            welcomeMenu();
            packXML();
        }
    }
    ///////////////////
    private void welcomeMenu() throws JAXBException{
        screen.displayMessageLine("\nWelcome!");
        
        boolean userExitedWelcomeMenu = false;
        while (!userExitedWelcomeMenu){
            screen.displayMessageLine("\nLog in:");
            screen.displayMessageLine("1 - Log in  ");
            screen.displayMessageLine("2 - Registrate new user");
            screen.displayMessage("Enter a choice: ");
            int MenuSelection = keypad.getInt();
            
            switch(MenuSelection){
                case LOG_IN:
                    authenticateUser();
                    break;
                case REGISTER_NEW:
                    registerNewUser();
                    break;
                default:
                    screen.displayMessageLine(
                    "\nYou did not enter a valid selection. Try again!");
                    break;        
            }
        }
        screen.displayMessage("\nThank you! Goodbye!");
    }
    private void registerNewUser() throws JAXBException{
        screen.displayMessage("\nPlease enter a account number: ");
        int accountNumber = keypad.getInt();
        screen.displayMessage("\nPlease enter a password: ");
        String pin = keypad.getString();
        
        if(bankDatabase.authenticateUser(accountNumber, pin)){
            System.out.println("Acocunt exists!");
        } else{
            createUser(accountNumber, pin, 300, 300);
            System.out.println("Account created!");
            System.out.println("300 dollars has been added to your account!");
            System.out.println("Trying to pack to xml..");
            packXML();
        }
    }    
    private void authenticateUser() throws JAXBException{
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = keypad.getInt();
        screen.displayMessage("\nPlease enter your password: ");
        String pin = keypad.getString();
        
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
        
        //check if authentication was succsessful
        if(userAuthenticated){
            currentAccountNumber = accountNumber;
            performTransactions();
        } else{
            screen.displayMessageLine(
                    "Invalid account number or PIN. Please try again");
        }        
    }
    private void performTransactions() throws JAXBException{
        Transaction currentTransaction = null;
        
        
        boolean userExited = false;
        while (!userExited){
            screen.displayMessageLine("\nMain Menu:");
            screen.displayMessageLine("1 - View my balance  ");
            screen.displayMessageLine("2 - Withdraw cash    ");
            screen.displayMessageLine("3 - Deposit founds   ");
            screen.displayMessageLine("4 - Exit\n           ");
            screen.displayMessage("Enter a choice: ");
            int mainMenuSelection = keypad.getInt();
            
            switch(mainMenuSelection){
                case BALANCE_REQUEST:
                case WITHDRAWAL:
                case DEPOSIT:
                    currentTransaction = 
                            createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the system..");
                    userAuthenticated = false;
                    userExited = true; 
                    break;
                default:
                    screen.displayMessageLine(
                    "\nYou did not enter a valid selection. Try again!");
                    break;
            }
            packXML();
        }
    }
    private void createUser(int AccountNumber, String PIN, int balance, int totalBalance){
        bankDatabase.addAccount(new Account(AccountNumber, PIN, balance, totalBalance));
    }
    private void unpackXML(){
        System.out.println("Unpacking XML!");
         try {
		File file = new File("Accounts.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(AccountList.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		this.bankDatabase = (AccountList) jaxbUnmarshaller.unmarshal(file);
	  } catch (JAXBException e) {
              System.out.println("Could not unpack the xml file.");
	  }
    }
    private void packXML() throws JAXBException{ 
        try {
            XmlMagic xml = new XmlMagic(bankDatabase);
            xml.pack("Accounts");
        } catch (JAXBException e){
            System.out.println("Could not pack to the database!");
        }
    }
    ///////////////////
    private Transaction createTransaction(int type){
        Transaction temp = null;
        
        switch(type){
            case BALANCE_REQUEST:
                temp = new RequestBalance(
                    currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(
                    currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT:
                temp = new Deposit(
                    currentAccountNumber, screen, bankDatabase, keypad, depositSlot);    
                break;
        }
        return temp;
    }         
    
}//end of ATM()
