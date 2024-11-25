package com.onlineshopping.models;

import java.io.*;
import java.util.*;

public class AdminMenu {
    private Admin admin;
    private Scanner scanner;

    public AdminMenu(Admin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    public void menuAdmin() {
        int pilihan;
        do {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Hapus Barang");
            System.out.println("3. Edit Barang");
            System.out.println("4. Tampilkan Daftar Barang");
            System.out.println("5. Terima Transaksi");
            System.out.println("6. Logout");
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
                    terimaTransaksi();
                    break;
                case 6:
                    System.out.println("Logout berhasil. Keluar dari sistem.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
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
            System.out.println("Barang berhasil ditambahkan.");
        } catch (IOException e) {
            System.err.println("Error menyimpan daftar barang ke file: " + e.getMessage());
        }
    }

    public void hapusBarang() {
        System.out.print("Masukkan Nama Barang yang akan dihapus: ");
        String nama = scanner.nextLine();
        admin.hapusBarang(nama);
        simpanKeFile(); // Simpan perubahan ke file
        System.out.println("Barang berhasil dihapus.");
    }

    public void editBarang() {
        System.out.print("Masukkan Nama Barang yang akan diedit: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Nama Baru Barang: ");
        String namaBaru = scanner.nextLine();
        System.out.print("Masukkan Harga Baru Barang: ");
        double hargaBaru = scanner.nextDouble();
        System.out.print("Masukkan Stok Baru Barang: ");
        int stokBaru = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer

        admin.editBarang(nama, namaBaru, hargaBaru, stokBaru);
        simpanKeFile(); // Simpan perubahan ke file
        System.out.println("Barang berhasil diedit.");
    }

    public void tampilkanBarang() {
        System.out.println("Daftar Barang:");
        List<Barang> listBarang = bacaDariFile();
        if (listBarang.isEmpty()) {
            System.out.println("Tidak ada barang yang tersedia.");
        } else {
            for (Barang barang : listBarang) {
                System.out.println("-> " + barang.getNama() + ": Rp " + barang.getHarga() + " (Stok: " + barang.getStok() + ")");
            }
        }
    }

public void terimaTransaksi() {
    System.out.println("Daftar Transaksi yang diterima:");
    if (admin.getListTransaksi().isEmpty()) {
        System.out.println("- Belum ada transaksi yang diterima.");
        return;
    }

    for (Transaksi transaksi : admin.getListTransaksi()) {
        System.out.println(transaksi);
    }
}

    private void simpanKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("barang.txt"))) {
            for (Barang barang : admin.getListBarang()) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error menyimpan daftar barang ke file: " + e.getMessage());
        }
    }

    private List<Barang> bacaDariFile() {
        List<Barang> listBarang = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("barang.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int stok = Integer.parseInt(data[2]);
                listBarang.add(new Barang(nama, harga, stok));
            }
        } catch (IOException e) {
            System.err.println("Error membaca daftar barang dari file: " + e.getMessage());
        }
        return listBarang;
    }
}
