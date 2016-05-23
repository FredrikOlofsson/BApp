package BApp.CashMachine.Data;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AccountList {
    @XmlElement
    private ArrayList<Account> databaseAccounts;
    
    public AccountList(){
        databaseAccounts = new ArrayList<>();  
    }
    ///////////////////
    //Setters & Getters
    
    public void setDatabaseAccounts(ArrayList<Account> databaseAccounts) {
        this.databaseAccounts = databaseAccounts;
    }
    public ArrayList<Account> getDatabaseAccounts() {
        return databaseAccounts;
    }       
    ///////////////////
    public void addAccount(Account acc){
        this.databaseAccounts.add(acc);
    }
    public Account getAccount (int accountNumber){
        for(Account currentAccount : databaseAccounts){
            if(currentAccount.getAccountNumber() == accountNumber){
                return currentAccount;
            }
        }
        System.out.println("error, no account found, returning null!");
        return null; //TODO Change to throw exception!
    }
    public boolean authenticateUser(int userAccountNumber, String userPIN){
        Account userAccount = getAccount(userAccountNumber);
        if(userAccount != null){
            return userAccount.validatePIN(userPIN);
        } else return false;
    }
    
    public double getAvailableBalance(int userAccountNumber){
        return getAccount(userAccountNumber).getAvailableBalance();
    }
    public double getTotalBalance(int userAccountNumber){
        return getAccount(userAccountNumber).getTotalBalance();
    }
    public void credit(int userAccountNumber, double amount){
        getAccount(userAccountNumber).credit(amount);
    }
    public void debit(int userAccountNumber, double amount){
        getAccount(userAccountNumber).debit(amount);
    }    
}