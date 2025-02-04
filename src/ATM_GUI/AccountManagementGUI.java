package ATM_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountManagementGUI extends JFrame {
    private Bank bank;

    private JTextField newAccNumberField;
    private JPasswordField newAccPinField;
    private JTextField newAccBalanceField;

    private JTextField delAccNumberField;
    private JPasswordField delAccPinField;

    public AccountManagementGUI(Bank bank) {
        this.bank = bank;
        initUI();
    }

    private void initUI() {
        setTitle("Управление аккаунтами");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

 
        JPanel addPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addPanel.add(new JLabel("Номер счета:"));
        newAccNumberField = new JTextField();
        addPanel.add(newAccNumberField);

        addPanel.add(new JLabel("PIN:"));
        newAccPinField = new JPasswordField();
        addPanel.add(newAccPinField);

        addPanel.add(new JLabel("Начальный баланс:"));
        newAccBalanceField = new JTextField();
        addPanel.add(newAccBalanceField);

        JButton addButton = new JButton("Добавить аккаунт");
        addPanel.add(addButton);
        addPanel.add(new JLabel("")); 

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accNumber = newAccNumberField.getText().trim();
                String pin = new String(newAccPinField.getPassword()).trim();
                double balance;
                try {
                    balance = Double.parseDouble(newAccBalanceField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AccountManagementGUI.this, "Некорректный баланс.");
                    return;
                }
                Bank_account newAcc = new Bank_account(accNumber, pin, balance, bank);
                bank.addAccount(newAcc);
                DBManager.insertAccount(newAcc);
                JOptionPane.showMessageDialog(AccountManagementGUI.this, "Аккаунт добавлен.");
            }
        });

 
        JPanel deletePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        deletePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        deletePanel.add(new JLabel("Номер счета:"));
        delAccNumberField = new JTextField();
        deletePanel.add(delAccNumberField);

        deletePanel.add(new JLabel("PIN:"));
        delAccPinField = new JPasswordField();
        deletePanel.add(delAccPinField);

        JButton deleteButton = new JButton("Удалить аккаунт");
        deletePanel.add(deleteButton);
        deletePanel.add(new JLabel(""));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accNumber = delAccNumberField.getText().trim();
                String pin = new String(delAccPinField.getPassword()).trim();
                Bank_account acc = bank.findAccount(accNumber, pin);
                if (acc != null) {
                    bank.removeAccount(acc);
                    DBManager.deleteAccount(acc);
                    JOptionPane.showMessageDialog(AccountManagementGUI.this, "Аккаунт удалён.");
                } else {
                    JOptionPane.showMessageDialog(AccountManagementGUI.this, "Аккаунт не найден или неверный PIN.");
                }
            }
        });

        tabbedPane.addTab("Добавить", addPanel);
        tabbedPane.addTab("Удалить", deletePanel);

        add(tabbedPane);
    }
}
