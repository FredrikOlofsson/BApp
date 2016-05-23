package BApp.CashMachine.Transactions;

import BApp.CashMachine.Data.AccountList;
import BApp.CashMachine.IO.Screen;

public class RequestBalance extends Transaction{

    public RequestBalance(int userAccountNumber, Screen atmScreen, AccountList atmBankDatabase) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
    }

    @Override
    public void execute() {
        AccountList bankDatabase = getBankDatabase();
        Screen screen = getScreen(); 
        double availableBalance =
                bankDatabase.getAvailableBalance(getAccountNumber());
        double totalBalance = 
                bankDatabase.getTotalBalance(getAccountNumber());
        
        //display the balance information on screen
        screen.displayMessageLine("\nBalance Information: ");
        screen.displayMessage(" - Available balance: ");
        screen.displayDollarAmount(availableBalance);
        screen.displayMessage("\n - Total balance: ");
        screen.displayDollarAmount(totalBalance);
        screen.displayMessageLine("");
    }
}
