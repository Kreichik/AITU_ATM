package ATM_SQL;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<Bank_account> accounts = new ArrayList<>();
    private ArrayList<ATM> atms = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    public String get_name(){
        return name;
    }
    public void addAccount(Bank_account account) {
        accounts.add(account);
    }

    public void removeAccount(Bank_account account) {
        accounts.remove(account);
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public Bank_account findAccountByPin(String pinCode) {
        for (Bank_account acc : accounts) {
            if (acc.getPinCode().equals(pinCode)) {
                return acc;
            }
        }
        return null;
    }
}
