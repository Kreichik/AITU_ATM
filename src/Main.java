
public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank("Halyk Bank");

        System.out.println(myBank.get_name());


        Bank_account acc1 = new Bank_account("123456", "1111", 5000, myBank);
        Bank_account acc2 = new Bank_account("654321", "2222", 3000, myBank);


        myBank.addAccount(acc1);
        myBank.addAccount(acc2);


        ATM atm1 = new ATM("ATM-AITU", "пр-т. Мангилик Ел", myBank);
        ATM atm2 = new ATM("ATM-ENU", "Street 2", myBank);
        ATM atm3 = new ATM("ATM-NU", "Street 3", myBank);


        myBank.addATM(atm1);
        myBank.addATM(atm2);
        myBank.addATM(atm3);


        System.out.println("Account 123456 balance: " + acc1.getBalance());


        atm1.withdrawMoney("1111", 110);


        System.out.println("Updated Account 123456 balance: " + acc1.getBalance());
    }
}
