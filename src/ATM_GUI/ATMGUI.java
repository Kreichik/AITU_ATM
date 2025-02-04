package ATM_GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame {
    private Bank bank;
    private ATM atm;

    private JTextField accountField;
    private JPasswordField pinField;
    private JTextField amountField;

    public ATMGUI(Bank bank, ATM atm) {
        this.bank = bank;
        this.atm = atm;
        initUI();
    }

    private void initUI() {
        setTitle("ATM мега супер пупер про макс");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Номер счета:"));
        accountField = new JTextField();
        inputPanel.add(accountField);

        inputPanel.add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        inputPanel.add(pinField);

        inputPanel.add(new JLabel("Сумма:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton depositBtn = new JButton("Пополнение");
        JButton withdrawBtn = new JButton("Снятие");
        JButton balanceBtn = new JButton("Проверка баланса");
        JButton accountMgmtBtn = new JButton("Управление аккаунтом");

        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(accountMgmtBtn);

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();
                double amount = parseAmount();
                if (amount <= 0) {
                    showMessage("Введите корректную сумму для пополнения.");
                    return;
                }
                atm.depositMoney(account, pin, amount);
                showMessage("Пополнение выполнено.");
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();
                double amount = parseAmount();
                if (amount <= 0) {
                    showMessage("Введите корректную сумму для снятия.");
                    return;
                }
                atm.withdrawMoney(account, pin, amount);
                showMessage("Операция снятия выполнена.");
            }
        });

        balanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();
                Bank_account acc = bank.findAccount(account, pin);
                if (acc != null) {
                    showMessage("Баланс счета " + account + ": " + acc.getBalance() + " KZT");
                    DBManager.selectAccounts();
                } else {
                    showMessage("Аккаунт не найден или неверный PIN.");
                }
            }
        });

        accountMgmtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountManagementGUI accMgmt = new AccountManagementGUI(bank);
                accMgmt.setVisible(true);
            }
        });

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private double parseAmount() {
        try {
            return Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void showMessage(String message) {

        JOptionPane.showMessageDialog(this, message);
    }
}
