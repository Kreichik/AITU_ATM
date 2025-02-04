package ATM_SQL;

import com.fazecast.jSerialComm.SerialPort;
import java.nio.charset.Charset;

public class ThermalPrinter {
    private static final String COM_PORT = "COM38";
    private static final int BAUD_RATE = 115200;

    public void printReceipt(String bankName, String atm_name,String accountNumber, double amount, double balance) {
        SerialPort printer = SerialPort.getCommPort(COM_PORT);
        printer.setBaudRate(BAUD_RATE);
        printer.setNumDataBits(8);
        printer.setNumStopBits(1);
        printer.setParity(SerialPort.NO_PARITY);

        if (!printer.openPort()) {
            System.out.println("Не удалось открыть COM порт: " + COM_PORT);
            return;
        }

        try {
            Thread.sleep(1000);
            sendCommand(printer, new byte[]{0x1B, 0x40});
            sendCommand(printer, new byte[]{0x1B, 0x74, 0x3B});
            sendCommand(printer, new byte[]{0x1D, 0x21, 0x11});
            sendCommand(printer, new byte[]{0x1B, 0x61, 0x01});

            String receipt = "== ЧЕК ==\n" +
                    "Банк: " + bankName + "\n" +
                    "ATM: " + atm_name + "\n" +
                    "Счет: " + accountNumber + "\n" +
                    "Снято: " + amount + " KZT\n" +
                    "Баланс: " + balance + " KZT\n" +
                    "===============";

            sendText(printer, receipt, "cp866");
            sendCommand(printer, new byte[]{0x0A, 0x0A, 0x0A});

            System.out.println("Чек напечатан.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            printer.closePort();
        }
    }

    private void sendCommand(SerialPort printer, byte[] command) {
        printer.writeBytes(command, command.length);
    }

    private void sendText(SerialPort printer, String text, String charset) {
        try {
            byte[] data = text.getBytes(Charset.forName(charset));
            printer.writeBytes(data, data.length);
        } catch (Exception e) {
            System.out.println("Ошибка кодировки: " + e.getMessage());
        }
    }
}
