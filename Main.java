
import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Admin> adminList = new ArrayList<>();
    private static List<Customer> customerList = new ArrayList<>();
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
            scanner.nextLine(); // Membersihkan buffer

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
        // Baca data admin dari file
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                adminList.add(new Admin(data[0], data[1]));
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data admin: " + e.getMessage());
        }

        // Baca data customer dari file
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

}
