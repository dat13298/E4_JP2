package Service;

import Entity.Account;
import Entity.EStatus;
import Entity.EType;
import Entity.Transaction;
import Generic.IServiceBank;
import Global.Format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TransactionRepo implements IServiceBank<TransactionRepo> {
    public static List<Transaction> transactions;
    public static AccountRepo accountRepo;

    public TransactionRepo() {;}

    @Override
    public Optional<TransactionRepo> findById(int id) {
        return Optional.empty();
    }

    public List<Transaction> fetchTransactions(String transactionPath) {
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
        return transactions;
    }

}
