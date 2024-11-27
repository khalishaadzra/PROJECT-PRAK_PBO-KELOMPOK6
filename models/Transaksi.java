package models;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private Customer customer;  
    private List<Barang> barang;  // Daftar barang yang dibeli dalam transaksi

    // Konstruktor untuk inisialisasi transaksi dengan customer dan daftar barang
    public Transaksi(Customer customer, List<Barang> barang) {
        this.customer = customer;  
        this.barang = new ArrayList<>(barang);  // Menyalin daftar barang untuk menjaga integritas data
    }

    // Getter untuk mendapatkan customer yang melakukan transaksi
    public Customer getCustomer() {
        return customer;  
    }

    // Getter untuk mendapatkan daftar barang yang dibeli dalam transaksi
    public List<Barang> getBarang() {
        return barang;  
    }

    // Mengimplementasikan metode toString untuk mencetak informasi transaksi
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();  // Menggunakan StringBuilder untuk merangkai string
        sb.append("Customer ID: ").append(customer.getId()).append("\n");  // Menambahkan ID customer
        sb.append("Barang yang dibeli:\n");  
        for (Barang b : barang) {  // Iterasi untuk menambahkan setiap barang yang dibeli
            sb.append("- ").append(b.getNama()).append(": Rp ").append(b.getHarga()).append("\n"); 
        }
        return sb.toString();  // Mengembalikan string yang telah dibangun
    }
}
