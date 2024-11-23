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
}

// Subkelas Pembayaran
class QRIS extends Pembayaran {
    public QRIS(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
    }
}

class Bank extends Pembayaran {
    public Bank(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
    }
}

class COD extends Pembayaran {
    public COD(String id) {
        super(id);
    }

    @Override
    public void prosesPembayaran() {
    }
}
