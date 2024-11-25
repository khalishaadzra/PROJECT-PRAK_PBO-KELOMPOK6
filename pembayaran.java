package com.onlineshopping.models;

abstract class Pembayaran {
    protected String id;

    public Pembayaran(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void prosesPembayaran();

    // Tambahkan metode untuk mendapatkan nama metode pembayaran
    public abstract String getMetodePembayaran();
}

// Subkelas Pembayaran
class QRIS extends Pembayaran {
    public QRIS(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran menggunakan QRIS berhasil diproses.");
    }

    @Override
    public String getMetodePembayaran() {
        return "QRIS";
    }
}

class Bank extends Pembayaran {
    public Bank(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran melalui Bank berhasil diproses.");
    }

    @Override
    public String getMetodePembayaran() {
        return "Bank Transfer";
    }
}

class COD extends Pembayaran {
    public COD(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran secara COD berhasil diproses.");
    }

    @Override
    public String getMetodePembayaran() {
        return "Cash on Delivery (COD)";
    }
}
