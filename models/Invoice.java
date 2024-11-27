package models;

import java.util.List;

class Invoice {
    private Transaksi transaksi;  
    private Pembayaran pembayaran; 

    // Konstruktor untuk inisialisasi objek Invoice dengan transaksi dan pembayaran
    public Invoice(Transaksi transaksi, Pembayaran pembayaran) {
        this.transaksi = transaksi;  
        this.pembayaran = pembayaran; 
    }

    // Format header untuk mencetak invoice
    private String formatHeader() {
        return "============= INVOICE =============\n";
    }

    // Format footer untuk mencetak invoice
    private String formatFooter() {
        return "===================================\n";
    }

    // Menghitung dan format total harga berdasarkan daftar barang
    private String formatTotalHarga(List<Barang> barangList) {
        double totalHarga = 0;
        // Menghitung total harga berdasarkan harga dan stok barang
        for (Barang barang : barangList) {
            totalHarga += barang.getHarga() * barang.getStok(); 
        }
        return String.format("Total Harga   : Rp %.2f\n", totalHarga); 
    }

    // Metode untuk mencetak invoice ke konsol
    public void cetakInvoice() {
        StringBuilder sb = new StringBuilder();

        sb.append(formatHeader()); 
        sb.append("Customer ID   : ").append(transaksi.getCustomer().getId()).append("\n"); 
        sb.append("Daftar Barang : ");
        // Menampilkan daftar barang yang dibeli
        for (Barang barang : transaksi.getBarang()) {
            sb.append(" ").append(barang.getNama()).append(": Rp ")
              .append(String.format("%.2f", barang.getHarga())).append("\n");
        }
        sb.append("---------------------------------\n");
        sb.append(formatTotalHarga(transaksi.getBarang())); 
        sb.append("Metode Bayar  : ").append(pembayaran.getMetodePembayaran()).append("\n"); 
        sb.append("Pembayaran berhasil diproses.\n"); 
        sb.append(formatFooter()); 

        System.out.println(sb); 
    }

    // Override toString untuk mengembalikan format invoice sebagai string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(formatHeader()); 
        sb.append("Customer ID   : ").append(transaksi.getCustomer().getId()).append("\n"); 
        // Menampilkan daftar barang dengan jumlah dan total harga per barang
        for (Barang barang : transaksi.getBarang()) {
            double totalBarang = barang.getHarga() * barang.getStok();
            sb.append(" ").append(barang.getNama()).append(": Rp ")
              .append(String.format("%.2f", barang.getHarga())).append(" x ")
              .append(barang.getStok()).append(" = Rp ")
              .append(String.format("%.2f", totalBarang)).append("\n");
        }
        sb.append("---------------------------\n");
        sb.append(formatTotalHarga(transaksi.getBarang())); 
        sb.append("Metode Bayar  : ").append(pembayaran.getMetodePembayaran()).append("\n"); 
        sb.append(formatFooter()); 

        return sb.toString(); 
    }
}
