package com.onlineshopping.models;

import java.util.ArrayList;
import java.util.List;

abstract class Driver {
    protected String id;

    public Driver(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // Method abstrak untuk diimplementasikan oleh turunan
    public abstract void execute();
}

// Driver untuk Admin
class AdminDriver extends Driver {
    private Admin admin;
    private List<Barang> listBarang = new ArrayList<>();
    private List<Transaksi> listTransaksi = new ArrayList<>();

    public AdminDriver(String id, Admin admin) {
        super(id);
        this.admin = admin;
    }

    public void tambahBarang(Barang barang) {
        listBarang.add(barang);
        admin.tambahBarang(barang);
    }

    public void hapusBarang(String namaBarang) {
        admin.hapusBarang(namaBarang);
    }

    public void editBarang(String namaBarang, String namaBaru, double hargaBaru, int stokBaru) {
        admin.editBarang(namaBarang, namaBaru, hargaBaru, stokBaru);
    }

    public void terimaTransaksi(Transaksi transaksi) {
        admin.terimaTransaksi(transaksi);
    }

    public void lihatTransaksi() {
        System.out.println("Daftar Transaksi yang Diterima:");
        for (int i = 0; i < listTransaksi.size(); i++) {
            System.out.println((i + 1) + ". Transaksi dari Customer: " + listTransaksi.get(i).getCustomer().getId());
        }
    }

    @Override
    public void execute() {
        System.out.println("AdminDriver: Menjalankan operasi admin.");
    }
}

// Drive untuk Customer
class CustomerDriver extends Driver {
    private Customer customer;
    private List<Transaksi> listTransaksi = new ArrayList<>();

    public CustomerDriver(String id, Customer customer) {
        super(id);
        this.customer = customer;
    }

    public void lihatBarang(List<Barang> barangTersedia) {
        System.out.println("Daftar Barang yang Tersedia:");
        for (int i = 0; i < barangTersedia.size(); i++) {
            System.out.println((i + 1) + ". " + barangTersedia.get(i).getNama() + " - Rp " + barangTersedia.get(i).getHarga());
        }
    }

    public void tambahKeKeranjang(Barang barang) {
        customer.getKeranjang().tambahBarang(barang);
    }

    public void checkout(Admin admin, Pembayaran pembayaran) {
        // Proses checkout
        List<Barang> barangDibeli = customer.getKeranjang().getBarang();  // Ambil barang dari keranjang
        Transaksi transaksi = new Transaksi(customer, barangDibeli);
        listTransaksi.add(transaksi);  // Menambahkan transaksi ke dalam list

        // Membuat dan mencetak invoice
        Invoice invoice = new Invoice(transaksi, pembayaran);
        invoice.cetakInvoice(); // Cetak invoice

        // Mengosongkan keranjang setelah checkout
        customer.getKeranjang().kosongkanKeranjang();
        
        // Proses pembayaran
        pembayaran.prosesPembayaran();
        System.out.println("Checkout berhasil. Transaksi baru ditambahkan.");
        
    }

    public void lihatHistory() {
        System.out.println("Riwayat Belanja:");
        // for (Invoice invoice : customer.getHistoryBelanja()) {
        //     invoice.cetakInvoice();
        // }
        for (Transaksi transaksi : listTransaksi) {
            System.out.println(transaksi);
        }
    }

    @Override
    public void execute() {
        System.out.println("CustomerDriver: Menjalankan operasi customer.");
    }
}