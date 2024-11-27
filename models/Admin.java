package models;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Akun {
    private Akun akun;
    private List<Barang> listBarang = new ArrayList<>();
    private List<Transaksi> listTransaksi = new ArrayList<>();
    public Admin(String id, String password) {
        super(id, password);
    }

    public Akun getAkun() {
        return akun;
    }
    public void tambahBarang(Barang barang) {
        listBarang.add(barang);
    }
    public void hapusBarang(String namaBarang) {
        listBarang.removeIf(barang -> barang.getNama().equalsIgnoreCase(namaBarang));
    }
    public void editBarang(String namaBarang, String namaBaru, double hargaBaru, int stokBaru) {
        for (Barang barang : listBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                barang.setNama(namaBaru);
                barang.setHarga(hargaBaru);
                barang.setStok(stokBaru);
                System.out.println("\nBarang berhasil diperbarui.");
                return;
            }
        }
    }
    
    public List<Transaksi> getListTransaksi() {
        return listTransaksi;
    }

        public List<Transaksi> getListTransaksi() {
        return listTransaksi;
    }

     // Metode untuk menyimpan daftar barang ke file
     public void simpanBarangKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            for (Barang barang : listBarang) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
            System.out.println("Daftar barang berhasil disimpan ke file " + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file: " + e.getMessage());
        }
    }   

    public void terimaTransaksiDariFile() {
    // Memuat transaksi dari file
    List<Transaksi> transaksiBaru = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi.txt"))) {
            String line;
            String customerId = null;
            List<Barang> barangList = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    if (customerId != null && !barangList.isEmpty()) {
                        Customer dummyCustomer = new Customer(customerId, "dummyPassword");
                        Transaksi transaksi = new Transaksi(dummyCustomer, barangList);
                        transaksiBaru.add(transaksi);
                    }
                    customerId = null;
                    barangList.clear();
                } else if (customerId == null) {
                    customerId = line.trim();
                } else {
                    String[] data = line.split(",");
                    if (data.length < 3) continue;
                    String nama = data[0].trim();
                    double harga = Double.parseDouble(data[1].trim());
                    int jumlah = Integer.parseInt(data[2].trim());
                    barangList.add(new Barang(nama, harga, jumlah));
                }
            }
            
            if (customerId != null && !barangList.isEmpty()) {
                Customer dummyCustomer = new Customer(customerId, "dummyPassword");
                Transaksi transaksi = new Transaksi(dummyCustomer, barangList);
                transaksiBaru.add(transaksi);
            }
    
        } catch (IOException e) {
            System.err.println("Kesalahan membaca file transaksi: " + e.getMessage());
            return;
        }
    
        if (transaksiBaru.isEmpty()) {
            System.out.println("\nTidak ada transaksi yang dapat diproses.");
            return;
        }
    
        // Tampilkan daftar transaksi untuk admin memilih transaksi

        System.out.println("\nDaftar Transaksi yang Tersedia:");
        int index = 1;
        for (Transaksi transaksi : transaksiBaru) {
            System.out.println(index + ". Customer ID: " + transaksi.getCustomer().getId());
            // Menampilkan barang yang dibeli dan jumlahnya
            for (Barang barang : transaksi.getBarang()) {
                System.out.println("   - " + barang.getNama() + ": " + barang.getStok() + " pcs");
            }
            index++;
        }
    
        // Admin memilih transaksi untuk diproses
        Scanner scanner = new Scanner(System.in);
        System.out.print("Pilih nomor transaksi untuk diproses: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
    
        if (pilihan < 1 || pilihan > transaksiBaru.size()) {
            System.out.println("Pilihan tidak valid. Transaksi tidak ditemukan.");
            return;
        }
    
        Transaksi transaksiDipilih = transaksiBaru.get(pilihan - 1);
        System.out.println("\nMemproses transaksi untuk Customer: " + transaksiDipilih.getCustomer().getId());
    
        // Pindahkan transaksi yang sudah diproses ke file transaksi_diterima.txt
        pindahkanKeFileDiproses(transaksiDipilih);
    
        // Hapus transaksi yang sudah diproses dari listTransaksi yang baru
        transaksiBaru.remove(pilihan - 1);
    
        // Simpan transaksi yang belum diproses kembali ke file transaksi.txt
        simpanTransaksiKeFile(transaksiBaru);
    }
    
}
