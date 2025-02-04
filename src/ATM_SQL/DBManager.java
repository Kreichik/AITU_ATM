package ATM_SQL;

import java.sql.*;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/atm_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "qwerty";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS bank_account (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "account_number VARCHAR(50) NOT NULL, " +
                "pin_code VARCHAR(10) NOT NULL, " +
                "balance DOUBLE NOT NULL" +
                ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Таблица bank_account создана или уже существует.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertAccount(Bank_account account) {
        String sql = "INSERT INTO bank_account (account_number, pin_code, balance) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setString(2, account.getPinCode());
            pstmt.setDouble(3, account.getBalance());
            pstmt.executeUpdate();
            System.out.println("Счет вставлен: " + account.getAccountNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateAccount(Bank_account account) {
        String sql = "UPDATE bank_account SET balance = ? WHERE account_number = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, account.getBalance());
            pstmt.setString(2, account.getAccountNumber());
            pstmt.executeUpdate();
            System.out.println("Счет обновлён: " + account.getAccountNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAccount(Bank_account account) {
        String sql = "DELETE FROM bank_account WHERE account_number = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, account.getAccountNumber());
            pstmt.executeUpdate();
            System.out.println("Счет удалён: " + account.getAccountNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void selectAccounts() {
        String sql = "SELECT * FROM bank_account";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Список счетов в базе данных:");
            while (rs.next()){
                System.out.println("ID: " + rs.getInt("id") +
                        ", Account Number: " + rs.getString("account_number") +
                        ", PIN: " + rs.getString("pin_code") +
                        ", Balance: " + rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
