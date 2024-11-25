package com.onlineshopping.models;

import java.io.*;
import java.util.*;

public class CustomerMenu {
    private Customer customer;
    private Scanner scanner;

    public CustomerMenu(Customer customer, Scanner scanner) {
        this.customer = customer;
        this.scanner = scanner;
    }

    public void menuCustomer() {
        int pilihan;
        do {
            System.out.println("\n=== Menu Customer ===");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Tambah Barang ke Keranjang");
            System.out.println("3. Lihat Keranjang");
            System.out.println("4. Checkout");
            System.out.println("5. Lihat Riwayat Belanja");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (pilihan) {
                case 1:
                    lihatDaftarBarang();
                    break;
                case 2:
                    tambahKeKeranjang();
                    break;
                case 3:
                    lihatKeranjang();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    lihatRiwayatBelanja();
                    break;
                case 6:
                    System.out.println("Logout berhasil. Kembali ke menu utama.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 6);
    }

    public void lihatDaftarBarang() {
        File file = new File("barang.txt");
        if (!file.exists()) {
            System.out.println("File barang.txt tidak ditemukan. Silakan hubungi admin.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("Daftar Barang yang Tersedia:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int stok = Integer.parseInt(data[2]);
                System.out.println("- " + nama + ": Rp " + harga + " (Stok: " + stok + ")");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
    public void tambahKeKeranjang() {
        System.out.print("Masukkan Nama Barang: ");
        String namaBarang = scanner.nextLine();
        System.out.print("Jumlah Barang yang Ingin Dibeli: ");
        int jumlahBarang = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
    
        File file = new File("barang.txt");
    
        if (!file.exists()) {
            System.out.println("File barang.txt tidak ditemukan. Silakan hubungi admin.");
            return;
        }
    
        try {
            List<Barang> barangList = new ArrayList<>();
            boolean barangDitemukan = false;
    
            // Membaca file barang dan memperbarui stok
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    String nama = data[0];
                    double harga = Double.parseDouble(data[1]);
                    int stok = Integer.parseInt(data[2]);
    
                    if (nama.equalsIgnoreCase(namaBarang)) {
                        if (stok >= jumlahBarang) {
                            stok -= jumlahBarang; // Kurangi stok sesuai jumlah yang diminta
                            Barang barang = new Barang(nama, harga, jumlahBarang); // Tambah barang ke keranjang
                            customer.getKeranjang().tambahBarang(barang);
                            simpanKeranjang(barang); // Simpan ke file keranjang.txt
                            System.out.println("Barang berhasil ditambahkan ke keranjang.");
                            barangDitemukan = true;
                        } else {
                            System.out.println("Stok tidak mencukupi. Tersedia hanya " + stok + " barang.");
                        }
                    }
                    barangList.add(new Barang(nama, harga, stok)); // Simpan barang dengan stok yang diperbarui
                }
            }
    
            // Simpan kembali file barang dengan stok yang diperbarui
            if (barangDitemukan) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Barang barang : barangList) {
                        writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                        writer.newLine();
                    }
                }
            } else {
                System.out.println("Barang tidak ditemukan dalam daftar.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca atau menulis file: " + e.getMessage());
        }
    }
    
    private void simpanKeranjang(Barang barang) {
        File file = new File("keranjang.txt");
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file keranjang: " + e.getMessage());
        }
    }
    

    public void lihatKeranjang() {
        File file = new File("keranjang.txt");
    
        if (!file.exists()) {
            System.out.println("Keranjang kosong. Tidak ada barang di file keranjang.txt.");
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("Isi Keranjang:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int jumlah = Integer.parseInt(data[2]);
                System.out.println("- " + nama + ": Rp " + harga + " x " + jumlah + " unit");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file keranjang.txt: " + e.getMessage());
        }
    }
    
    public void checkout() {
        Admin adminMenu = new Admin("admin123", "password123");
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. QRIS");
        System.out.println("2. Bank Transfer");
        System.out.println("3. Cash On Delivery (COD)");
        System.out.print("Masukkan pilihan Anda (1/2/3): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer setelah input angka
    
        Pembayaran pembayaran = null;
    
        switch (pilihan) {
            case 1:
                pembayaran = new QRIS("QRIS123");
                break;
            case 2:
                pembayaran = new Bank("BANK123");
                break;
            case 3:
                pembayaran = new COD("COD123");
                break;
            default:
                System.out.println("Pilihan tidak valid. Pembayaran gagal.");
                return;
        }
    
        // Simpan transaksi dan proses pembayaran
        customer.checkout(adminMenu, pembayaran);
    
        if (pembayaran != null) {
            pembayaran.prosesPembayaran();
        }
    
        // Hapus isi file keranjang.txt setelah checkout selesai
        File file = new File("keranjang.txt");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Keranjang berhasil dikosongkan setelah checkout.");
            } else {
                System.out.println("Terjadi kesalahan saat mengosongkan keranjang.");
            }
        } else {
            System.out.println("File keranjang.txt tidak ditemukan.");
        }
    }
    

    public void lihatRiwayatBelanja() {
        System.out.println("Riwayat Belanja:");
        if (customer.getHistoryBelanja().isEmpty()) {
            System.out.println("- Tidak ada riwayat belanja.");
        } else {
            for (Invoice invoice : customer.getHistoryBelanja()) {
                System.out.println("- " + invoice);
            }
        }
    }
}
