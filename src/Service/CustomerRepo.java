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
    public List<Customer> getAllCustomer(String customersPath) {
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
        return customers;
    }
    public List<Customer> writeAllCustomer(String customersPath) {
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
        return customers;
    }
}
