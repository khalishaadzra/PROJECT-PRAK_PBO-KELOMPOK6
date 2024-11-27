package models;

import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private List<Barang> barang = new ArrayList<>();  // Menyimpan daftar barang yang ada di keranjang

    // Metode untuk menambahkan barang ke dalam keranjang
    public void tambahBarang(Barang barang) {
        this.barang.add(barang); 
    }

    // Metode untuk mengosongkan keranjang
    public void kosongkanKeranjang() {
        barang.clear();
    }

    // Metode untuk mendapatkan daftar barang dalam keranjang
    public List<Barang> getBarang() {
        return barang;
    }
}
