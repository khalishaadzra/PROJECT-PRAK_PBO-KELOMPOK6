package com.onlineshopping.models;

class Invoice {
    private Transaksi transaksi;
    private Pembayaran pembayaran;

    public Invoice(Transaksi transaksi, Pembayaran pembayaran) {
        this.transaksi = transaksi;
        this.pembayaran = pembayaran;
    }

    public void cetakInvoice() {
        // Header Invoice
        System.out.println("========= INVOICE =========");
        
        // Customer ID
        System.out.println("Customer ID   : " + transaksi.getCustomer().getId());
        
        // Daftar Barang
        System.out.println("Daftar Barang :");
        
        double totalHarga = 0;  // Inisialisasi untuk menghitung total harga
        
        // Menampilkan Daftar Barang dan menghitung total harga
        for (Barang barang : transaksi.getBarang()) {
            System.out.printf(" %s: Rp %.2f%n", barang.getNama(), barang.getHarga());
            totalHarga += barang.getHarga();  // Menambah harga barang ke total
        }
        
        // Menampilkan garis pemisah
        System.out.println("---------------------------");
        
        // Total Harga
        System.out.printf("Total Harga   : Rp %.2f%n", totalHarga);
        
        // Metode Pembayaran
        System.out.println("Metode Bayar  : " + pembayaran.getMetodePembayaran());
        
        // Proses Pembayaran
        pembayaran.prosesPembayaran();
        System.out.println("Pembayaran berhasil diproses.");
        
        // Footer Invoice
        System.out.println("========= END =========");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Header Invoice
        sb.append("========== INVOICE ==========\n");
        
        // Customer ID
        sb.append("Customer ID   : ").append(transaksi.getCustomer().getId()).append("\n");
        
        // Daftar Barang
        sb.append("Daftar Barang :\n");
        
        double totalHarga = 0; // Inisialisasi untuk menghitung total harga
        
        // Menampilkan Daftar Barang dan menghitung total harga
        for (Barang barang : transaksi.getBarang()) {
            sb.append("").append(barang.getNama()).append(": Rp ").append(String.format("%.2f", barang.getHarga())).append("\n");
            totalHarga += barang.getHarga(); // Menambah harga barang ke total
        }
        
        // Menambahkan garis pemisah
        sb.append("------------------------------\n");
        
        // Menambahkan total harga
        sb.append("Total Harga   : Rp ").append(String.format("%.2f", totalHarga)).append("\n");
        
        // Metode Pembayaran
        sb.append("Metode Bayar  : ").append(pembayaran.getMetodePembayaran()).append("\n");
        
        sb.append("Pembayaran berhasil diproses.\n");
        sb.append("==============================\n");

        return sb.toString();
    }
    
}
