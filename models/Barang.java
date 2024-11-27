package models;

// Inisialisasi class Barang
public class Barang {
    private String nama;
    private double harga;
    private int stok;

    // Konstruktor untuk inisialisasi objek Barang dengan nama, harga, dan stok
    public Barang(String nama, double harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter untuk mendapatkan nama barang
    public String getNama() {
        return nama;
    }

    // Setter untuk mengubah nama barang
    public void setNama(String nama) {
        this.nama = nama; 
    }

    // Getter untuk mendapatkan harga barang
    public double getHarga() {
        return harga;
    }

    // Setter untuk mengubah harga barang
    public void setHarga(double harga) {
        this.harga = harga; 
    }

    // Getter untuk mendapatkan jumlah stok barang
    public int getStok() {
        return stok;
    }

    // Setter untuk mengubah jumlah stok barang
    public void setStok(int stok) {
        this.stok = stok;
    }
}
