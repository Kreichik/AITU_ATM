public class ATM {
    private String id;
    private String address;
    private Bank bank;
    private ThermalPrinter printer;

    public ATM(String id, String address, Bank bank) {
        this.id = id;
        this.address = address;
        this.bank = bank;
        this.printer = new ThermalPrinter();
    }

    public void withdrawMoney(String pinCode, double amount) {
        Bank_account account = bank.findAccountByPin(pinCode);
        if (account != null) {
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful. New balance: " + account.getBalance());
                printer.printReceipt(bank.get_name(), account.getAccountNumber(), amount, account.getBalance());

            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Invalid PIN.");
        }
    }
}
