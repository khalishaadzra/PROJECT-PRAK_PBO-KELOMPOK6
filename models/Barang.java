package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Akun {
    private Akun akun;
    private Keranjang keranjang;
    private List<Invoice> invoiceSelesai;

    public Customer(String id, String password) {
        super(id, password);
        this.keranjang = new Keranjang();
        this.invoiceSelesai = new ArrayList<>();
    }

    public Akun getAkun() {
        return akun;
    }
    // Mendapatkan keranjang customer
    public Keranjang getKeranjang() {
        return keranjang;
    }
    // Melakukan checkout
    public void checkout(Admin admin, Pembayaran pembayaran) {
        if (keranjang.getBarang().isEmpty()) {
        System.out.println("\nKeranjang kosong. Tidak dapat melakukan checkout.");
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
        System.out.println("\nCheckout berhasil.");
    }

}
