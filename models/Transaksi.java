package models;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private Customer customer;
    private List<Barang> barang;

    public Transaksi(Customer customer, List<Barang> barang) {
        this.customer = customer;
        this.barang = new ArrayList<>(barang); // Salin daftar barang untuk menjaga integritas data
    }
}
