package BApp.CashMachine.Transactions;

import BApp.CashMachine.Data.AccountList;
import BApp.CashMachine.IO.Keypad;
import BApp.CashMachine.IO.Screen;
import BApp.CashMachine.Parts.CashDispenser;

public class Withdrawal extends Transaction{
    private int amount;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    //corresponting to menu option to cancel
    private final static int CANCELED = 6;
   
    public Withdrawal(int userAccountNumber, Screen atmScreen, 
            AccountList atmBankDatabase, Keypad atmKeypad, 
            CashDispenser atmCashDispenser) {
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    }
    /////////////////////////////////////////////////
    
    private int displayMenuOfAmounts(){
        int userChoice = 0;
        int[] amounts = {0, 20, 40, 60, 100, 200};
        Screen screen = getScreen();
        
        while (userChoice == 0){
            screen.displayMessageLine("\nWithdrawal Menu: ");
            screen.displayMessageLine("1 - $20");
            screen.displayMessageLine("2 - $40");
            screen.displayMessageLine("3 - $60");
            screen.displayMessageLine("4 - $100");
            screen.displayMessageLine("5 - $200");
            screen.displayMessageLine("6 - Cancel transaction");
            screen.displayMessage("\nChoose a withdrawal amount: ");
            int input = keypad.getInt();
        
            switch (input){
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                    userChoice = amounts[input];
                    break;
                case CANCELED:    
                    break;
                default: 
                    screen.displayMessageLine("\nInvalid selection, try again!");
            }        
        }
        return userChoice;
    }
        
    @Override
    public void execute() {
        boolean cashDispensed = false;
        double availableBalance; 
        
        AccountList bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        do{
            //obtain a chosen withdrawal amount from the user
            amount = displayMenuOfAmounts();
            if (amount != CANCELED){
                availableBalance =
                        bankDatabase.getAvailableBalance(getAccountNumber()); 
                //Checks if the user has enough money on the account
                if (amount <= availableBalance){
                    if(cashDispenser.isSufficientCashAvailableInMachine(amount)){
                        bankDatabase.debit(getAccountNumber(), amount);
                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;

                        screen.displayMessageLine("\nYour cash has been " +
                                "dispensed. Please take your cash now.");
                    } else
                        screen.displayMessageLine("\nATM does not have enough cash");
                } else { 
                    screen.displayMessageLine("\nCanceling transaction..");
                    return; 
                }
            }
        } while( !cashDispensed );
    }
}
