package ATM_GUI;

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

 
    public void withdrawMoney(String accountNumber, String pinCode, double amount) {
        Bank_account account = bank.findAccount(accountNumber, pinCode);
        if (account != null) {
            if (account.withdraw(amount)) {
                System.out.println("Снятие успешно. Новый баланс: " + account.getBalance());
                printer.printReceipt(bank.get_name(), id, account.getAccountNumber(), amount, account.getBalance(), "Снятие");
 
                DBManager.updateAccount(account);
            } else {
                System.out.println("Недостаточно средств.");
            }
        } else {
            System.out.println("Неверный аккаунт или PIN.");
        }
    }

 
    public void depositMoney(String accountNumber, String pinCode, double amount) {
        Bank_account account = bank.findAccount(accountNumber, pinCode);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Пополнение успешно. Новый баланс: " + account.getBalance());
            printer.printReceipt(bank.get_name(), id, account.getAccountNumber(), amount, account.getBalance(),"Пополнение");
            DBManager.updateAccount(account);
        } else {
            System.out.println("Неверный аккаунт или PIN.");
        }
    }

    public String getId() {
        return id;
    }
}
