package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Menginisialisasi class Customer yang meng-extends class Akun
public class Customer extends Akun {
    private Akun akun;
    private Keranjang keranjang;
    private List<Invoice> invoiceSelesai;

    // Konstruktor untuk membuat objek Customer dengan id dan password
    public Customer(String id, String password) {
        super(id, password);
        this.keranjang = new Keranjang();
        this.invoiceSelesai = new ArrayList<>();
    }

    public Akun getAkun() {
        return akun;
    }

    // Mendapatkan objek keranjang customer
    public Keranjang getKeranjang() {
        return keranjang;
    }

    // Melakukan proses checkout untuk pelanggan
    public void checkout(Admin admin, Pembayaran pembayaran) {
        // Mengecek apakah keranjang kosong, jika ya, tidak bisa checkout
        if (keranjang.getBarang().isEmpty()) {
            System.out.println("\nKeranjang kosong. Tidak dapat melakukan checkout.");
            return;
         }

    // Membuat objek transaksi dengan barang-barang dalam keranjang
    Transaksi transaksi = new Transaksi(this, keranjang.getBarang());

    // Simpan transaksi ke file `transaksi.txt`
    simpanTransaksiKeFile(transaksi);

    // Membuat objek invoice untuk transaksi dan pembayaran, lalu menambahkannya ke riwayat invoice
    Invoice invoice = new Invoice(transaksi, pembayaran);
    invoiceSelesai.add(invoice);

    // Mengosongkan keranjang setelah checkout
    keranjang.kosongkanKeranjang();
    System.out.println("\nCheckout berhasil.");
}

    // Menyimpan data transaksi ke file `transaksi.txt`
    private void simpanTransaksiKeFile(Transaksi transaksi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi.txt", true))) {
            writer.write(transaksi.getCustomer().getId()); // Menyimpan ID customer
            writer.newLine();
            // Menulis informasi setiap barang yang dibeli dalam transaksi
            for (Barang barang : transaksi.getBarang()) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
            writer.write("------------------------------------------------"); // Penanda akhir transaksi
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan transaksi: " + e.getMessage());
        }
    }
    
    // Mendapatkan riwayat belanja yang telah selesai
    public List<Invoice> getHistoryBelanja() {
        return invoiceSelesai;
    }

    // Menambah barang ke keranjang belanja
    public boolean tambahBarangKeKeranjang(String namaBarang, double harga, int jumlah) {
        Barang barang = new Barang(namaBarang, harga, jumlah); // Membuat objek barang baru
        keranjang.tambahBarang(barang);
        return true; // Menunjukkan barang berhasil ditambahkan
    }

    // Melihat daftar barang dari file dan mengembalikannya dalam bentuk list
    public List<Barang> lihatDaftarBarang(String namaFile) {
        List<Barang> daftarBarang = new ArrayList<>(); // Daftar barang yang dibaca dari file
        try (var reader = new java.io.BufferedReader(new java.io.FileReader(namaFile))) {
            String line;
            // Membaca file baris demi baris
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
        return daftarBarang; // Mengembalikan daftar barang yang berhasil dibaca
    }

    // Kosongkan keranjang belanja
    public void kosongkanKeranjang() {
        keranjang.kosongkanKeranjang();
    }   
}
