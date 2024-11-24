package com.onlineshopping.models;

import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private List<Barang> barang = new ArrayList<>();

    public void tambahBarang(Barang barang) {
        this.barang.add(barang);
        System.out.println("Barang " + barang.getNama() + " ditambahkan ke keranjang.");
    }

    public void kosongkanKeranjang() {
        barang.clear();
    }

    public List<Barang> getBarang() {
        return barang;
    }
}
