package Model.BankAcount.Logs;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger{

    @Override
    public void log(String message){
        File log = new File("Log.txt");
        File crashLog = new File("CrashLog.txt");
        System.out.println("ЗАПИСЬ В ФАЙЛ:" + message);
        try(BufferedWriter br = new BufferedWriter(new FileWriter(log, true))) {
            br.append(message);
            br.newLine();
        } catch (IOException e) {

            System.out.println("Ошибка fileLogger: " + e);
            try(BufferedWriter crl = new BufferedWriter(new FileWriter(crashLog, true))) {
                crl.append("Ошибка fileLogger" + e);
                crl.newLine();
            } catch (IOException ex) {
                System.err.println("ну тут уже нечего не поделаешь");
            }
        }

    }
}
