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
    
    public void cetakInvoice() {
        StringBuilder sb = new StringBuilder();

        sb.append(formatHeader());
        sb.append("Customer ID   : ").append(transaksi.getCustomer().getId()).append("\n");
        sb.append("Daftar Barang : ");
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
}
