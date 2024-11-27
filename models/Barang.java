package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Akun {
    private Akun akun;
    private Keranjang keranjang;
    private List<Invoice> invoiceSelesai;

    public Customer(String id, String password) {
        super(id, password);
        this.keranjang = new Keranjang();
        this.invoiceSelesai = new ArrayList<>();
    }
}
