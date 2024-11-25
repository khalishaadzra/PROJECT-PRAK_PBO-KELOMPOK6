package com.onlineshopping.models;

class Invoice {
    private Transaksi transaksi;
    private Pembayaran pembayaran;

    public Invoice(Transaksi transaksi, Pembayaran pembayaran) {
        this.transaksi = transaksi;
        this.pembayaran = pembayaran;
    }

    public void cetakInvoice() {
        System.out.println("Invoice:");
        System.out.println("Customer: " + transaksi.getCustomer().getId());
        System.out.println("Daftar Barang:");
        for (Barang barang : transaksi.getBarang()) {
            System.out.println("- " + barang.getNama() + ": Rp " + barang.getHarga());
        }
        pembayaran.prosesPembayaran();
        System.out.println("Pembayaran berhasil diproses.");
        System.out.println("Invoice selesai dicetak.");
    }
    
}
