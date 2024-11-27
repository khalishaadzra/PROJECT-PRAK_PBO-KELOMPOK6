import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);  // Scanner untuk input pengguna
    private static List<Admin> adminList = new ArrayList<>();  // Daftar untuk menyimpan data admin
    private static List<Customer> customerList = new ArrayList<>();  // Daftar untuk menyimpan data customer
    private static final String ADMIN_FILE = "admin_data.txt"; 
    private static final String CUSTOMER_FILE = "customer_data.txt";  

    public static void main(String[] args) {
        loadData(); // Baca data dari file saat program dimulai
        int pilihan;
        do {
            System.out.println("\n======== Sistem Perbelanjaan Online ========\n");
            System.out.println("1. Sign Up");
            System.out.println("2. Login sebagai Admin");
            System.out.println("3. Login sebagai Customer");
            System.out.println("4. Keluar");
            System.out.println("\n============================================\n");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt(); 
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    signUp();  
                    break;
                case 2:
                    loginAdmin();  
                    break;
                case 3:
                    loginCustomer();  
                    break;
                case 4:
                    System.out.println("\nTerima kasih telah menggunakan sistem ini^^\n");  
                    break;
                default:
                    System.out.println("\nPilihan tidak valid. Silakan coba lagi.");  
            }
        } while (pilihan != 4); 
    }

    private static void loadData() {
        // Membaca data admin dari file
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");  
                adminList.add(new Admin(data[0], data[1])); 
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data admin: " + e.getMessage());  
        }

        // Membaca data customer dari file
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");  
                customerList.add(new Customer(data[0], data[1]));  
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data customer: " + e.getMessage()); 
        }
    }

    private static void saveData() {
        // Menyimpan data admin ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (Admin admin : adminList) {
                writer.write(admin.getId() + "," + admin.getPassword());  
                writer.newLine();  
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data admin: " + e.getMessage());  
        }

        // Menyimpan data customer ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer customer : customerList) {
                writer.write(customer.getId() + "," + customer.getPassword());  
                writer.newLine();  
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data customer: " + e.getMessage()); 
        }
    }

    // Metode untuk sign up (mendaftar)
    public static void signUp() {
        System.out.println("\n================== SIGN IN =================\n");
        System.out.print("Pilih jenis akun (1: Admin, 2: Customer): ");
        int jenisAkun = scanner.nextInt(); 
        scanner.nextLine(); // Membersihkan buffer

        // Membaca ID dan Password
        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        // Menambahkan akun baru sesuai jenis akun yang dipilih
        if (jenisAkun == 1) {
            adminList.add(new Admin(id, password)); 
            saveData(); 
            System.out.println("\nAkun Admin berhasil dibuat!");
        } else if (jenisAkun == 2) {
            customerList.add(new Customer(id, password));  
            saveData();
            System.out.println("\nAkun Customer berhasil dibuat!");
        } else {
            System.out.println("\nPilihan tidak valid."); 
        }
    }

    // Metode untuk login sebagai Admin
    public static void loginAdmin() {
        System.out.println("\n================ LOGIN ADMIN ================\n");
        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        // Mencari admin yang sesuai dengan ID dan password
        Admin admin = adminList.stream()
                .filter(a -> a.getId().equals(id) && a.getPassword().equals(password))  // Mencocokkan ID dan password
                .findFirst()
                .orElse(null);

        // Jika login berhasil
        if (admin != null) {
            System.out.println("\nLogin berhasil sebagai Admin.\n");
            AdminDriver adminMenu = new AdminDriver(admin, scanner);  // Membuat objek AdminDriver untuk menu admin
            adminMenu.menuAdmin();  
        } else {
            System.out.println("\nID atau Password salah. Silakan coba lagi.\n");  
        }
    }

    // Metode untuk login sebagai Customer
    public static void loginCustomer() {
        System.out.println("\n============== LOGIN CUSTOMER ==============\n");
        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        // Mencari customer yang sesuai dengan ID dan password
        Customer customer = customerList.stream()
                .filter(c -> c.getId().equals(id) && c.getPassword().equals(password)) 
                .findFirst()
                .orElse(null);

        // Jika login berhasil
        if (customer != null) {
            System.out.println("\nLogin berhasil sebagai Customer.\n");
            CustomerDriver customerMenu = new CustomerDriver(customer, scanner);  // Membuat objek CustomerDriver untuk menu customer
            customerMenu.menuCustomer();  
        } else {
            System.out.println("ID atau Password salah. Silakan coba lagi.\n"); 
        }
    }
}
