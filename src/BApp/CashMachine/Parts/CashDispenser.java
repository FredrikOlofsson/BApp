package BApp.CashMachine.Parts;

public class CashDispenser {
    private final static int INITIAL_COUNT = 500;
    private int machineAmount;
    
    public CashDispenser(){
            machineAmount = INITIAL_COUNT;
    }
    //////////////////////////////
    
    public void dispenseCash(int amount){
        machineAmount -= amount;
    }
    public boolean isSufficientCashAvailableInMachine(int amount){
        return machineAmount >= amount;
    }
}


