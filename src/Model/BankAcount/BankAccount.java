package Model.BankAcount;

import Model.BankAcount.Logs.InsufficientFundsException;
import Model.BankAcount.Logs.Logger;

import java.io.*;

public class BankAccount {
    File serverBalance = new File("balance.txt");
    private String accountHolderName;
    private double balance;
    private Logger logger;

    public BankAccount(String name, Logger logger){
        this.accountHolderName = name;
        this.logger = logger;
        loadFromFile();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            logger.log("Ошибка, сумма пополнения не должна быть меньше или равна 0.");
        } else {
            balance += amount;
            logger.log("Вы успешно пополнили баланс на " + amount + "₽.");
        }
        saveToFile();
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            logger.log("Ошибка, сумма снятия не должна быть меньше или равна 0.");
        } else if (amount > balance) {
            throw new InsufficientFundsException("Недостаточно средств. Попытка снять " + amount + ", но на балансе только " + balance);
        } else {
            balance -= amount;
            logger.log("Вы успешно сняли со счёта " + amount + "₽.");
        }
        saveToFile();
    }

    private void saveToFile(){
        try (PrintWriter pw = new PrintWriter(new FileWriter(serverBalance))) {
            pw.println(this.balance);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private void loadFromFile(){
        if(!serverBalance.exists()){
            this.balance = 0.0;
            System.out.println("Файл с балансом не найдем. ваш баланс сброшен до 0");
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(serverBalance))) {
                String tempBalance = br.readLine();
                this.balance = Double.parseDouble(tempBalance);
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }
        }
    }

    public void printBalance() {
        logger.log("Ваш баланс: " + balance + "₽");
    }
}