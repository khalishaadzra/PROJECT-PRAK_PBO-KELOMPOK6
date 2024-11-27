package models;

import java.io.*;
import java.util.*;

public class CustomerDriver extends Driver {
    private Customer customer;
    private Scanner scanner;

    public CustomerDriver(Customer customer, Scanner scanner) {
        super(customer.getAkun());
        this.customer = customer;
        this.scanner = scanner;
    }

    @Override
    public void menu() {
        menuCustomer();
    }

    public void menuCustomer() {
        int pilihan;
        do {
            System.out.println("\n=============== MENU CUSTOMER ===============\n");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Tambah Barang ke Keranjang");
            System.out.println("3. Lihat Keranjang");
            System.out.println("4. Checkout");
            System.out.println("5. Lihat Riwayat Belanja");
            System.out.println("6. Logout");
            System.out.println("\n============================================\n");
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
                    System.out.println("\nLogout berhasil. Kembali ke menu utama.");
                    break;
                default:
                    System.out.println("\nPilihan tidak valid. Silakan coba lagi.");
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
                            System.out.println("\nBarang berhasil ditambahkan ke keranjang.");
                            barangDitemukan = true;
                        } else {
                            System.out.println("\nStok tidak mencukupi. Tersedia hanya " + stok + " barang.");
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
                System.out.println("\nBarang tidak ditemukan dalam daftar.");
            }
        } catch (IOException e) {
            System.out.println("\nTerjadi kesalahan saat membaca atau menulis file: " + e.getMessage());
        }
    }

    private void simpanKeranjang(Barang barang) {
        File file = new File("keranjang.txt");
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("\nTerjadi kesalahan saat menyimpan ke file keranjang: " + e.getMessage());
        }
    }
    public void lihatKeranjang() {
        File file = new File("keranjang.txt");
    
        if (!file.exists()) {
            System.out.println("\nKeranjang kosong.");
            System.out.println("\nTidak ada barang di file keranjang.txt.");
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\nIsi Keranjang:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                double harga = Double.parseDouble(data[1]);
                int jumlah = Integer.parseInt(data[2]);
                System.out.println("- " + nama + ": Rp " + harga + " x " + jumlah + " pcs");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file keranjang.txt: " + e.getMessage());
        }
    }
    
}
