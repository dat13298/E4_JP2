package Service;

import Entity.Account;
import Entity.EStatus;
import Entity.Transaction;
import Generic.IServiceBank;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionRepo implements IServiceBank<TransactionRepo> {
    public static List<Transaction> transactions;
    public static AccountRepo accountRepo;

    public TransactionRepo() {;}

    @Override
    public Optional<TransactionRepo> findById(int id) {
        return Optional.empty();
    }

    public Map.Entry<Transaction, Double> createTransaction(Transaction transaction) {
        transactions.add(transaction);
        Account account = transaction.getAccount();
        account.setBalance(account.getBalance() - transaction.getAmount());
        transaction.setStatus(EStatus.C);
        Map<Transaction, Double> transactionMap = transactions.stream()
                .collect(Collectors.toMap(
                        tm -> tm,
                        tm -> tm.getAccount().getBalance()
                ));
        return Map.entry(transaction, transactionMap.get(transaction));
    }

    public List<Map.Entry<Account, List<Transaction>>> getTransactionByDate(LocalDate startDate, LocalDate endDate, Account account) {
        return transactions.stream()
                .filter(t->t.getDateTime().isAfter(startDate.atStartOfDay())
                        && t.getDateTime().isBefore(endDate.atStartOfDay())
                        && t.getAccount().equals(account))
                .collect(Collectors.groupingBy(Transaction::getAccount))
                .entrySet().stream().toList();
    }


}
