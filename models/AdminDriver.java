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

        public void tambahBarang() {
        List<Barang> listBarang = bacaDariFile(); // Membaca barang yang sudah ada di file
    
        System.out.print("Masukkan Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Harga Barang: ");
        double harga = scanner.nextDouble();
        System.out.print("Masukkan Stok Barang: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
    
        // Menambahkan barang baru ke daftar
        Barang barangBaru = new Barang(nama, harga, stok);
        listBarang.add(barangBaru);
    
        // Simpan daftar barang kembali ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("barang.txt"))) {
            for (Barang barang : listBarang) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
            System.out.println("\nBarang berhasil ditambahkan.");
        } catch (IOException e) {
            System.err.println("\nError menyimpan daftar barang ke file: " + e.getMessage());
        }
    }
}
