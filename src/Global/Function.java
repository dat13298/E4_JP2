package Global;

import Entity.*;
import Service.AccountRepo;
import Service.CustomerRepo;
import Service.TransactionRepo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Function {
    public static AccountRepo accountRepo;
    public static TransactionRepo transactionRepo;
    public static CustomerRepo customerRepo;
    public static List<Transaction> transactions;
    public static List<Account> accounts;
    public static List<Customer> customers;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    /*GET FROM FILE*/

    public static void getAllCustomer(String customersPath) {
        try {
            BufferedReader customerReader = new BufferedReader(new FileReader(customersPath));
            String line;
            while ((line = customerReader.readLine()) != null) {
                String[] data = line.split(";");
                if (!line.isEmpty()) {
                    customers.add(new Customer(Integer.parseInt(data[0]), data[1], data[2]));
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void getAllTransactions(String transactionPath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(transactionPath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (!line.isEmpty()) {
                    Account accountInsert = accountRepo.findAccountById(data[1]).get();
                    EType type = EType.valueOf(data[3]);
                    EStatus status = EStatus.valueOf(data[5]);
                    transactions.add(new Transaction(
                            Integer.parseInt(data[0])
                            ,accountInsert
                            ,Float.parseFloat(data[2])
                            ,type
                            , Format.convertStringToLocalDateTime(data[4])
                            ,status));
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void getAllAccount(String accountsPath){
        try{
            BufferedReader accountReader = new BufferedReader(new FileReader(accountsPath));
            String line;
            while ((line = accountReader.readLine()) != null) {
                String[] data = line.split(";");
                if (!line.isEmpty()) {
                    Customer customerInsert = customerRepo.findById(Integer.parseInt(data[1])).get();
                    ECurrency status = ECurrency.valueOf(data[3]);
                    accounts.add(new Account(
                            data[0]
                            ,customerInsert
                            ,Float.parseFloat(data[2])
                            ,status));
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /*WRITE FILE*/

    public static void writeAllCustomer(String customersPath) {
        try {
            BufferedWriter customerWriter = new BufferedWriter(new FileWriter(customersPath));
            customers.forEach(c->{
                try {
                    customerWriter.write(c.toString() + "\n");
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            });
            customerWriter.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /*TRANSACTION*/

    public static Transaction inputTransaction(){
        String accountNumber;
        float amount;
        boolean flag = false;
        Account account;
        Transaction transaction = null;
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
                    transaction.setId(transactions.size()+1);
                    transaction.setAccount(account);
                    transaction.setAmount(amount);
                    transaction.setType(EType.WITHDRAWAL);
                    transaction.setDateTime(LocalDateTime.now());
                    transaction.setStatus(EStatus.P);
                } else {
                    transaction.setId(transactions.size());
                    transaction.setAccount(account);
                    transaction.setAmount(amount);
                    transaction.setType(EType.WITHDRAWAL);
                    transaction.setDateTime(LocalDateTime.now());
                    transaction.setStatus(EStatus.R);
                    throw new Exception("Not enough balance");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                flag = true;
            }
        } while (!flag);
        transactions.add(transaction);
        return transaction;
    }

    /*GET BALANCE BY CUSTOMER*/

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

    /*GET TRANSACTION BY DATE*/

    public static void displayTransactionsByDate(){
        boolean flag = false;
        String accountNumber, startDate, endDate, selected;
        Account account = null;
        Optional<Account> opAccount;
        do {
            try {
                System.out.print("Enter your Account Number: ");
                accountNumber = br.readLine();
                opAccount = accountRepo.findAccountById(accountNumber);
                if(opAccount.isEmpty()) throw new Exception("Account Not Found");
                account = opAccount.get();
                System.out.print("From Date(dd-MM-yyyy): ");
                startDate = br.readLine();
                System.out.print("To Date(dd-MM-yyyy): ");
                endDate = br.readLine();
                List<Map.Entry<Account, List<Transaction>>> listMapTransaction = transactionRepo
                                                        .getTransactionByDate(Format.convertStringToLocalDate(startDate)
                                                        ,Format.convertStringToLocalDate(endDate)
                                                        ,account);
                listMapTransaction.forEach(System.out::println);
                System.out.print("Save Transaction to file(y/n): ");
                selected = br.readLine();
                if(selected.equalsIgnoreCase("y")
                        || selected.equalsIgnoreCase("yes")) {
                    writeTransactionsToFile(listMapTransaction);
                } else if (!selected.equalsIgnoreCase("n")
                        && !selected.equalsIgnoreCase("no")) {
                    throw new Exception("Invalid Option");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                flag = true;
            }
        }while (!flag);
    }

    /*WRITE FILE IF SAVE*/

    public static void writeTransactionsToFile(List<Map.Entry<Account, List<Transaction>>> listMapTransaction){
        String rootPath = System.getProperty("user.dir");
        String transactionOutPath = rootPath + "/data/Account.id_transaction_history.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(transactionOutPath));
            listMapTransaction.forEach(lm->{
                try {
                    bw.write(lm.getValue() + "\n");
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            });
            bw.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
