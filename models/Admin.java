package models;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Akun {
    private Akun akun;
    private List<Barang> listBarang = new ArrayList<>();
    private List<Transaksi> listTransaksi = new ArrayList<>();
    public Admin(String id, String password) {
        super(id, password);
    }

    public Akun getAkun() {
        return akun;
    }
    public void tambahBarang(Barang barang) {
        listBarang.add(barang);
    }
    public void hapusBarang(String namaBarang) {
        listBarang.removeIf(barang -> barang.getNama().equalsIgnoreCase(namaBarang));
    }
    public void editBarang(String namaBarang, String namaBaru, double hargaBaru, int stokBaru) {
        for (Barang barang : listBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                barang.setNama(namaBaru);
                barang.setHarga(hargaBaru);
                barang.setStok(stokBaru);
                System.out.println("\nBarang berhasil diperbarui.");
                return;
            }
        }
    }
}
