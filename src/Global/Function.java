package Global;

import Entity.Account;
import Entity.EStatus;
import Entity.EType;
import Entity.Transaction;
import Service.AccountRepo;
import Service.TransactionRepo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Function {
    public static AccountRepo accountRepo;
    public static TransactionRepo transactionRepo;
    public static List<Transaction> transactions;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void inputTransaction(){
        String accountNumber;
        float amount;
        boolean flag = false;
        Account account;
        Optional<Account> opAccount;

        do {
            try {
                System.out.print("Enter your Account Number: ");
                accountNumber = br.readLine();
                opAccount = accountRepo.findAccountById(accountNumber);
                if(opAccount.isEmpty()) throw new Exception("Account Not Found");
                account = opAccount.get();
                System.out.print("Enter the amount: ");
                amount = Float.parseFloat(br.readLine());
                if(amount % 10 != 0) throw new Exception("Amount must be divisible by 10");
                if(accountRepo.isEnoughBalance(account, amount)){
                    Transaction transaction = new Transaction(
                            transactions.size()+1,
                            account,
                            amount,
                            EType.WITHDRAWAL,
                            LocalDateTime.now(),
                            EStatus.P
                    );
                    System.out.println(transactionRepo.createTransaction(transaction));
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                flag = true;
            }
        } while (!flag);
    }

    public static void displayBalance(){
        boolean flag = false;
        String accountNumber;
        Account account = null;
        Optional<Account> opAccount;
        do {
            try {
                System.out.print("Enter your Account Number: ");
                accountNumber = br.readLine();
                opAccount = accountRepo.findAccountById(accountNumber);
                if(opAccount.isEmpty()) throw new Exception("Account Not Found");
                account = opAccount.get();
                System.out.println("Your balance: " + account.getBalance());
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                flag = true;
            }
        }while (!flag);
    }

    public Account getAccount() {

    }
}
