package Service;

import Entity.Account;
import Generic.IServiceBank;


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

    public boolean isEnoughBalance(Account account, float amount) {
        return account.getBalance() >= amount;
    }
}
