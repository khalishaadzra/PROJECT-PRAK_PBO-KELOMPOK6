package models;

import java.io.*;
import java.util.*;

public class AdminDriver extends Driver {
    private Admin admin;
    private Scanner scanner;

    public AdminDriver(Admin admin, Scanner scanner) {
        super(admin.getAkun());
        this.admin = admin;
        this.scanner = scanner;
    }
    
    @Override
    public void menu() {
        menuAdmin();
    }

    public void menuAdmin() {
        int pilihan;
        do {
            System.out.println("\n================ MENU ADMIN ================\n");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Hapus Barang");
            System.out.println("3. Edit Barang");
            System.out.println("4. Tampilkan Daftar Barang");
            System.out.println("5. Terima Transaksi dari File");
            System.out.println("6. Logout");
            System.out.println("\n============================================\n");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (pilihan) {
                case 1:
                    tambahBarang();
                    break;
                case 2:
                    hapusBarang();
                    break;
                case 3:
                    editBarang();
                    break;
                case 4:
                    tampilkanBarang();
                    break;
                case 5:
                    admin.terimaTransaksiDariFile();
                    break;
                case 6:
                    System.out.println("\nLogout berhasil. Keluar dari sistem.");
                    break;
                default:
                    System.out.println("\nPilihan tidak valid.");
            }
        } while (pilihan != 6);
    }
}
