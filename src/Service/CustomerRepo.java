package Service;

import Entity.Customer;
import Generic.IServiceBank;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class CustomerRepo implements IServiceBank<Customer> {
    public static List<Customer> customers;
    public CustomerRepo() {;}

    @Override
    public Optional<Customer> findById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst();
    }
}
