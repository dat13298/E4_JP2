import Entity.*;
import Global.Format;
import Global.Function;
import Service.AccountRepo;
import Service.CustomerRepo;
import Service.TransactionRepo;
import com.sun.jdi.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String selected;
        boolean flag = false;

        BufferedReader accountReader;
        BufferedReader transactionReader;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Customer> customers = new ArrayList<Customer>();
        List<Account> accounts = new ArrayList<Account>();
        List<Transaction> transactions = new ArrayList<Transaction>();

        CustomerRepo customerRepo = new CustomerRepo();
        CustomerRepo.customers = customers;
        AccountRepo accountRepo = new AccountRepo();
        AccountRepo.accounts = accounts;
        AccountRepo.customerRepo = customerRepo;
        TransactionRepo transactionRepo = new TransactionRepo();
        TransactionRepo.transactions = transactions;
        TransactionRepo.accountRepo = accountRepo;

        Function.accountRepo = accountRepo;
        Function.transactionRepo = transactionRepo;
        Function.transactions = transactions;

        String rootPath = System.getProperty("user.dir");
        String customersPath = rootPath + "/data/Customer.txt";
        String accountsPath = rootPath + "/data/Account.txt";
        String transactionsPath = rootPath + "/data/Transaction.txt";

        String customerOutPath = rootPath + "/data/CustomerOut.txt";
//23232534 11-11-2022 11-11-2024

        customerRepo.getAllCustomer(customersPath);
        accountRepo.fetchAccount(accountsPath);
        Function.fetchTransactions(transactionsPath);

        customerRepo.writeAllCustomer(customerOutPath);

        do {
            try {
                System.out.println("===========MENU");
                System.out.println("1. Transaction");
                System.out.println("2. Balance");
                System.out.println("3. Transaction History");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                selected = br.readLine();
                switch (selected) {
                    case "1":
                        System.out.println("------- Transaction -------");
                        Function.inputTransaction();
                        break;
                    case "2":
                        System.out.println("------- Display Balance -------");
                        Function.displayBalance();
                        break;
                    case "3":
                        System.out.println("------- Transaction History -------");
                        Function.displayTransactionsByDate();
                        break;
                    case "4":
                        System.out.println("Thank you for using our service");
                        flag = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);
    }
}