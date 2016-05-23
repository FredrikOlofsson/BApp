package BApp.CashMachine.Transactions;

import BApp.CashMachine.Data.AccountList;
import BApp.CashMachine.IO.Screen;

public abstract class Transaction {
    private int accountNumber; 
    private Screen screen;
    private AccountList bankDatabase;
    
    public Transaction (int userAccountNumber, Screen atmScreen,
            AccountList atmBankDatabase){
        accountNumber = userAccountNumber;
        screen = atmScreen;
        bankDatabase = atmBankDatabase;
    }
    //////////////////////////////////////////////

    public int getAccountNumber() {
        return accountNumber;
    }

    public Screen getScreen() {
        return screen;
    }

    public AccountList getBankDatabase() {
        return bankDatabase;
    }
    abstract public void execute(); //abstract forces subclasses to overwrite
}
