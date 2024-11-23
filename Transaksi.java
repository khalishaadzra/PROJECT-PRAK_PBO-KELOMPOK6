package com.onlineshopping.models;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private Customer customer;
    private List<Barang> barang;

    public Transaksi(Customer customer, List<Barang> barang) {
        this.customer = customer;
        this.barang = new ArrayList<>(barang); // Salin daftar barang untuk menjaga integritas data
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Barang> getBarang() {
        return barang;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer ID: ").append(customer.getId()).append("\n");
        sb.append("Barang yang dibeli:\n");
        for (Barang b : barang) {
            sb.append("- ").append(b.getNama()).append(": Rp ").append(b.getHarga()).append("\n");
        }
        return sb.toString();
    }
}
