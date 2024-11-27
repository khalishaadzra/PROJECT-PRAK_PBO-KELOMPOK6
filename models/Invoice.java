package models;

import java.util.List;

class Invoice {
    private Transaksi transaksi;
    private Pembayaran pembayaran;

    public Invoice(Transaksi transaksi, Pembayaran pembayaran) {
        this.transaksi = transaksi;
        this.pembayaran = pembayaran;
    }
    private String formatHeader() {
        return "============= INVOICE =============\n";
    }

    private String formatFooter() {
        return "===================================\n";
    }

    private String formatTotalHarga(List<Barang> barangList) {
        double totalHarga = 0;
        for (Barang barang : barangList) {
            totalHarga += barang.getHarga() * barang.getStok(); // Harga x Jumlah Barang
        }
        return String.format("Total Harga   : Rp %.2f\n", totalHarga);
    }

}
