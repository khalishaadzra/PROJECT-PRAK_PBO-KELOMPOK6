package com.onlineshopping.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Akun {
    private Keranjang keranjang;
    private List<Invoice> invoiceSelesai;

    public Customer(String id, String password) {
        super(id, password);
        this.keranjang = new Keranjang();
        this.invoiceSelesai = new ArrayList<>();
    }

    // Mendapatkan keranjang customer
    public Keranjang getKeranjang() {
        return keranjang;
    }

    // Melakukan checkout
    public void checkout(Admin admin, Pembayaran pembayaran) {
    if (keranjang.getBarang().isEmpty()) {
        System.out.println("Keranjang kosong. Tidak dapat melakukan checkout.");
        return;
    }

    // Membuat transaksi
    Transaksi transaksi = new Transaksi(this, keranjang.getBarang());

    // Simpan transaksi ke file `transaksi.txt`
    simpanTransaksiKeFile(transaksi);

    // Membuat invoice dan menambahkannya ke riwayat
    Invoice invoice = new Invoice(transaksi, pembayaran);
    invoiceSelesai.add(invoice);

    // Mengosongkan keranjang
    keranjang.kosongkanKeranjang();
    System.out.println("Checkout berhasil. Transaksi Anda telah disimpan.");
}

private void simpanTransaksiKeFile(Transaksi transaksi) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi.txt", true))) {
        writer.write(transaksi.getCustomer().getId()); // Menyimpan ID customer
        writer.newLine();
        for (Barang barang : transaksi.getBarang()) {
            writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
            writer.newLine();
        }
        writer.write("---"); // Penanda akhir transaksi
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Terjadi kesalahan saat menyimpan transaksi: " + e.getMessage());
    }
}


    
    // Mendapatkan riwayat belanja
    public List<Invoice> getHistoryBelanja() {
        return invoiceSelesai;
    }

    // Menambah barang ke keranjang
    public boolean tambahBarangKeKeranjang(String namaBarang, double harga, int jumlah) {
        Barang barang = new Barang(namaBarang, harga, jumlah);
        keranjang.tambahBarang(barang);
        return true; // Menunjukkan barang berhasil ditambahkan
    }

    // Melihat daftar barang dari file
    public List<Barang> lihatDaftarBarang(String namaFile) {
        List<Barang> daftarBarang = new ArrayList<>();
        try (var reader = new java.io.BufferedReader(new java.io.FileReader(namaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int stok = Integer.parseInt(data[2]);
                daftarBarang.add(new Barang(nama, harga, stok));
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat membaca daftar barang: " + e.getMessage());
        }
        return daftarBarang;
    }

    // Kosongkan keranjang
    public void kosongkanKeranjang() {
        keranjang.kosongkanKeranjang();
    }

    // Menampilkan isi keranjang
    public void lihatKeranjang() {
        if (keranjang.getBarang().isEmpty()) {
            System.out.println("Keranjang kosong.");
        } else {
            System.out.println("Isi Keranjang:");
            for (Barang barang : keranjang.getBarang()) {
                System.out.println("- " + barang.getNama() + ": Rp " + barang.getHarga());
            }
        }
    }
}
