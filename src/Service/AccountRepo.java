package Service;

import Entity.Account;
import Entity.Customer;
import Entity.ECurrency;
import Generic.IServiceBank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AccountRepo implements IServiceBank<Account> {
    public static List<Account> accounts;
    public static CustomerRepo customerRepo;

    public AccountRepo() {;}

    @Override
    public Optional<Account> findById(int id) {
        return Optional.empty();
    }
    public Optional<Account> findAccountById(String id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst();
    }

    public List<Account> fetchAccount(String accountsPath){
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
        return accounts;
    }
}
