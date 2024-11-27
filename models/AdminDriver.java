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

        public void hapusBarang() {
        List<Barang> listBarang = bacaDariFile(); // Baca daftar barang dari file
        System.out.print("Masukkan Nama Barang yang akan dihapus: ");
        String nama = scanner.nextLine();
    
        boolean barangDihapus = false;
        Iterator<Barang> iterator = listBarang.iterator();
        while (iterator.hasNext()) {
            Barang barang = iterator.next();
            if (barang.getNama().equalsIgnoreCase(nama)) {
                iterator.remove(); // Hapus barang dari daftar
                barangDihapus = true;
                break;
            }
        }
    
        if (barangDihapus) {
            simpanKeFile(listBarang); // Simpan daftar barang yang diperbarui ke file
            System.out.println("\nBarang berhasil dihapus.");
        } else {
            System.out.println("\nBarang tidak ditemukan.");
        }
    }

        public void editBarang() {
        List<Barang> listBarang = bacaDariFile(); // Baca daftar barang dari file
        System.out.print("Masukkan Nama Barang yang akan diedit: ");
        String nama = scanner.nextLine();
    
        boolean barangDiedit = false;
        for (Barang barang : listBarang) {
            if (barang.getNama().equalsIgnoreCase(nama)) {
                System.out.print("Masukkan Nama Baru Barang: ");
                String namaBaru = scanner.nextLine();
                System.out.print("Masukkan Harga Baru Barang: ");
                double hargaBaru = scanner.nextDouble();
                System.out.print("Masukkan Stok Baru Barang: ");
                int stokBaru = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer
    
                // Update data barang
                barang.setNama(namaBaru);
                barang.setHarga(hargaBaru);
                barang.setStok(stokBaru);
                barangDiedit = true;
                break;
            }
        }
    
        if (barangDiedit) {
            simpanKeFile(listBarang); // Simpan daftar barang yang diperbarui ke file
            System.out.println("\nBarang berhasil diedit.");
        } else {
            System.out.println("\nBarang tidak ditemukan.");
        }
    }

            private void simpanKeFile(List<Barang> listBarang) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("barang.txt"))) {
            for (Barang barang : listBarang) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error menyimpan daftar barang ke file: " + e.getMessage());
        }
    }
}
