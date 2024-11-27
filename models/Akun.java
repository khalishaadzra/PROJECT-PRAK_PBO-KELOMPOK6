package models;

public class Akun {
    private String id;
    private String password;

    public Akun(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }    
}
