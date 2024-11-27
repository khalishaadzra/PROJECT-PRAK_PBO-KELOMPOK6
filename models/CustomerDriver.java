package models;

import java.io.*;
import java.util.*;

public class CustomerDriver extends Driver {
    private Customer customer;
    private Scanner scanner;

    public CustomerDriver(Customer customer, Scanner scanner) {
        super(customer.getAkun());
        this.customer = customer;
        this.scanner = scanner;
    }
}
