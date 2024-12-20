package models;

import java.io.*;
import java.util.*;

public class AdminDriver extends Driver {
    private Admin admin;
    private Scanner scanner;

    // Konstruktor untuk inisialisasi Admin dan Scanner
    public AdminDriver(Admin admin, Scanner scanner) {
        super(admin.getAkun());
        this.admin = admin;
        this.scanner = scanner;
    }
    
    // Menampilkan menu untuk admin dan menangani pilihan menu
    @Override
    public void menu() {
        menuAdmin(); 
    }

    // Menampilkan menu admin dan memproses pilihan menu yang dimasukkan admin
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

    // Menambahkan barang baru ke dalam daftar barang dan menyimpannya ke file
    public void tambahBarang() {
        List<Barang> listBarang = bacaDariFile(); 
    
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

    // Menghapus barang berdasarkan nama dari daftar dan memperbarui file
    public void hapusBarang() {
        List<Barang> listBarang = bacaDariFile(); 
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
            simpanKeFile(listBarang);
            System.out.println("\nBarang berhasil dihapus.");
        } else {
            System.out.println("\nBarang tidak ditemukan.");
        }
    }

    // Mengedit data barang berdasarkan nama dan memperbarui file
    public void editBarang() {
        List<Barang> listBarang = bacaDariFile(); 
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
            simpanKeFile(listBarang); 
            System.out.println("\nBarang berhasil diedit.");
        } else {
            System.out.println("\nBarang tidak ditemukan.");
        }
    }

    // Menyimpan daftar barang yang diperbarui ke file
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

    // Menampilkan daftar barang yang ada dalam file
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

    // Menampilkan daftar transaksi yang diterima
    public void terimaTransaksi() {
        System.out.println("\nDaftar Transaksi yang diterima:");
        if (admin.getListTransaksi().isEmpty()) {
            System.out.println("- Belum ada transaksi yang diterima.");
            return;
        }
    
        for (Transaksi transaksi : admin.getListTransaksi()) {
            System.out.println(transaksi);
        }
    }

    // Membaca daftar barang dari file
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
