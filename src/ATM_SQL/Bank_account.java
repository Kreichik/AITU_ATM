package ATM_SQL;

public class Bank_account {
    private String number;
    private String PIN_code;
    private double remainder;
    private Bank bank;

    public Bank_account(String number, String PIN_code, double remainder, Bank bank) {
        this.number = number;
        this.PIN_code = PIN_code;
        this.remainder = remainder;
        this.bank = bank;
    }

    public void deposit(double amount) {
        remainder += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= remainder) {
            remainder -= amount;
            return true;
        }
        return false;
    }

    public String getPinCode() {
        return PIN_code;
    }

    public double getBalance() {
        return remainder;
    }

    public String getAccountNumber() {
        return number;
    }
}
