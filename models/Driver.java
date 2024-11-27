package models;

public abstract class Driver {
    protected Akun akun;

    public Driver(Akun akun) {
        this.akun = akun;
    }

    // Abstract method untuk menu
    public abstract void menu();
}
