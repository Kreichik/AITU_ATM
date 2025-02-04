package ATM_SQL;

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
                System.out.println("Снятие успешно. Новый баланс: " + account.getBalance());
                printer.printReceipt(bank.get_name(), id, account.getAccountNumber(), amount, account.getBalance());

                DBManager.updateAccount(account);
            } else {
                System.out.println("Недостаточно средств.");
            }
        } else {
            System.out.println("Неверный PIN.");
        }
    }

    public String getId() {
        return id;
    }
}
