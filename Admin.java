package com.onlineshopping.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Akun {

     // Metode untuk menyimpan daftar barang ke file
     public void simpanBarangKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            for (Barang barang : listBarang) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
            System.out.println("Daftar barang berhasil disimpan ke file " + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file: " + e.getMessage());
        }
    }
    
    private List<Barang> listBarang = new ArrayList<>();
    private List<Transaksi> listTransaksi = new ArrayList<>();
    public Admin(String id, String password) {
        super(id, password);
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
                System.out.println("Barang berhasil diperbarui.");
                return;
            }
        }
        System.out.println("Barang tidak ditemukan.");
    }

    public List<Transaksi> getListTransaksi() {
        return listTransaksi;
    }

    public void terimaTransaksi(Transaksi transaksi) {
        if (transaksi != null) {
            System.out.println("Transaksi diterima dari customer: " + transaksi.getCustomer().getId());
            System.out.println("Detail Transaksi:");
            for (Barang barang : transaksi.getBarang()) {
                System.out.println("- " + barang.getNama() + ": Rp " + barang.getHarga());
            }
        } else {
            System.out.println("Transaksi kosong. Tidak ada data yang diterima.");
        }
    }
    
    
    public void terimaTransaksiDariFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("transaksi.txt"))) {
        String line;
        String customerId = null;
        List<Barang> barangList = new ArrayList<>();

        System.out.println("Daftar Transaksi:");
        while ((line = reader.readLine()) != null) {
            if (line.equals("---")) {
                // Transaksi selesai, buat invoice
                if (customerId != null && !barangList.isEmpty()) {
                    Customer dummyCustomer = new Customer(customerId, "dummyPassword");
                    Transaksi transaksi = new Transaksi(dummyCustomer, barangList);
                    Invoice invoice = new Invoice(transaksi, new COD("COD123"));
                    invoice.cetakInvoice();
                }
                // Reset data untuk transaksi berikutnya
                customerId = null;
                barangList.clear();
            } else if (customerId == null) {
                customerId = line; // Baris pertama adalah ID customer
            } else {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int jumlah = Integer.parseInt(data[2]);
                barangList.add(new Barang(nama, harga, jumlah));
            }
        }
    } catch (IOException e) {
        System.out.println("Terjadi kesalahan saat membaca transaksi: " + e.getMessage());
    }
}

    

    public List<Barang> getListBarang() {
        return listBarang;
    }

}
