package BApp.CashMachine.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Account {
    private int accountNumber;
    private String pin;
    private double availableBalance;
    private double totalBalance;
    
    public Account() {}   
    public Account(int theAccountNumber, String thePIN,
            double theAvailableBalance, double theTotalBalance){
        accountNumber = theAccountNumber;
        pin = thePIN;
        availableBalance = theAvailableBalance;
        totalBalance = theTotalBalance;
    }
    ///////////////////
    //Setters & Getters
    
    public double getAvailableBalance() {
        return availableBalance;
    }
    public double getTotalBalance() {
        return totalBalance;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public String getPin() {
        return pin;
    }
    ///////////////////
    
    public boolean validatePIN(String userPIN){
        return (userPIN == null ? pin == null : userPIN.equals(pin));
    }
    public void credit(double amount){
        totalBalance += amount;
    }
    public void debit(double amount){
        availableBalance -= amount;
        totalBalance -= amount;
    }
}
