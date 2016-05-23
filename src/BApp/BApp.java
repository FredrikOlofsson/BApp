package BApp;

import BApp.CashMachine.CashMachine;
import javax.xml.bind.JAXBException;


public class BApp {
    public static void main(String[] args) throws JAXBException {
        CashMachine machine  = new CashMachine();
        machine.run();
    }    
}