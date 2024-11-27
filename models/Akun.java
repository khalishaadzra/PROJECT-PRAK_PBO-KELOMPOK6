package models;

public class Akun {
    private String id;
    private String password;

    // Konstruktor untuk inisialisasi id dan password akun
    public Akun(String id, String password) {
        this.id = id;
        this.password = password; 
    }

    // Getter untuk mendapatkan id akun
    public String getId() {
        return id;
    }

    // Getter untuk mendapatkan password akun
    public String getPassword() {
        return password; 
    }
}
