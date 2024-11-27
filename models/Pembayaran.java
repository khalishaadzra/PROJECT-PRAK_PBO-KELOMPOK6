package models;

// superclass untuk berbagai metode pembayaran
abstract class Pembayaran {
    protected String id;  

    // Konstruktor untuk inisialisasi ID pembayaran
    public Pembayaran(String id) {
        this.id = id;  
    }

    // Getter untuk mendapatkan ID pembayaran
    public String getId() {
        return id;  
    }

    // Metode abstrak untuk memproses pembayaran, harus diimplementasikan oleh subclass
    public abstract void prosesPembayaran();

    // Metode abstrak untuk mendapatkan nama metode pembayaran
    public abstract String getMetodePembayaran();
    
    }

// Subkelas QRIS yang mengimplementasikan metode pembayaran QRIS
class QRIS extends Pembayaran {
    
    // Konstruktor untuk inisialisasi ID QRIS
    public QRIS(String id) {
        super(id);  
    }

    // Implementasi metode untuk memproses pembayaran menggunakan QRIS
    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran menggunakan QRIS berhasil diproses.");  
    }

    // Implementasi metode untuk mendapatkan nama metode pembayaran QRIS
    @Override
    public String getMetodePembayaran() {
        return "QRIS";  
    }
}

// Subkelas Bank yang mengimplementasikan metode pembayaran melalui Bank Transfer
class Bank extends Pembayaran {
    // Konstruktor untuk inisialisasi ID Bank Transfer
    public Bank(String id) {
        super(id); 
    }

    // Implementasi metode untuk memproses pembayaran melalui Bank Transfer
    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran melalui Bank berhasil diproses.");  
    }

    // Implementasi metode untuk mendapatkan nama metode pembayaran Bank Transfer
    @Override
    public String getMetodePembayaran() {
        return "Bank Transfer";  
    }
}

// Subkelas COD yang mengimplementasikan metode pembayaran Cash on Delivery
class COD extends Pembayaran {
    // Konstruktor untuk inisialisasi ID COD
    public COD(String id) {
        super(id);  
    }

    // Implementasi metode untuk memproses pembayaran secara COD
    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran secara COD berhasil diproses.");  
    }

    // Implementasi metode untuk mendapatkan nama metode pembayaran COD
    @Override
    public String getMetodePembayaran() {
        return "Cash on Delivery (COD)";  
    }

}
