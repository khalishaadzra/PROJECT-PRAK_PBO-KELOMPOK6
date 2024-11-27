package models;

public abstract class Driver {
    protected Akun akun; // Menyimpan informasi akun pengguna

    // Konstruktor untuk inisialisasi objek Driver dengan akun
    public Driver(Akun akun) {
        this.akun = akun; // Menghubungkan objek Driver dengan akun yang diberikan
    }

    // Abstract method untuk menu
    public abstract void menu();
}
