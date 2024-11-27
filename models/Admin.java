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

    // Method untuk menambahkan barang baru ke dalam daftar barang (listBarang)
    public void tambahBarang(Barang barang) {
        listBarang.add(barang);
    }

    // Method untuk menghapus barang dari daftar berdasarkan nama barang
    public void hapusBarang(String namaBarang) {
        listBarang.removeIf(barang -> barang.getNama().equalsIgnoreCase(namaBarang));
    }

    // Method untuk mengedit detail barang berdasarkan nama barang lama,
    // seperti mengganti nama, harga, dan stok barang
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

    public List<Barang> getListBarang() {
        return listBarang;
    }

    // Method untuk menyimpan daftar barang ke dalam file dengan format tertentu
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

    // Method untuk menerima dan memproses transaksi dari file "transaksi.txt"
    // Memproses setiap transaksi dengan membaca data transaksi dari file dan menambahkannya ke daftar transaksi baru
    public void terimaTransaksiDariFile() {
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
    
        // Menampilkan daftar transaksi yang tersedia untuk diproses oleh admin
        System.out.println("\nDaftar Transaksi yang Tersedia:");
        int index = 1;
        for (Transaksi transaksi : transaksiBaru) {
            System.out.println(index + ". Customer ID: " + transaksi.getCustomer().getId());
            for (Barang barang : transaksi.getBarang()) {
                System.out.println("   - " + barang.getNama() + ": " + barang.getStok() + " pcs");
            }
            index++;
        }
    
        // Meminta admin memilih transaksi yang akan diproses
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
    
        // Memindahkan transaksi yang diproses ke file "transaksi_diterima.txt"
        pindahkanKeFileDiproses(transaksiDipilih);
    
        // Menghapus transaksi yang sudah diproses dari daftar transaksi baru
        transaksiBaru.remove(pilihan - 1);
    
        // Menyimpan transaksi yang belum diproses kembali ke file transaksi.txt
        simpanTransaksiKeFile(transaksiBaru);
    }
    
    // Method untuk memindahkan transaksi yang sudah diproses ke dalam file "transaksi_diterima.txt"
    private void pindahkanKeFileDiproses(Transaksi transaksi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_diterima.txt", true))) {
            writer.write("Customer ID: " + transaksi.getCustomer().getId());
            writer.newLine();
            for (Barang barang : transaksi.getBarang()) {
                writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                writer.newLine();
            }
            writer.write("---");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Kesalahan menulis ke file transaksi_diterima.txt: " + e.getMessage());
        }
    }

    // Method untuk menyimpan transaksi yang belum diproses kembali ke dalam file "transaksi.txt"
    private void simpanTransaksiKeFile(List<Transaksi> transaksiBaru) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi.txt"))) {
            for (Transaksi transaksi : transaksiBaru) {
                writer.write(transaksi.getCustomer().getId());
                writer.newLine();
                for (Barang barang : transaksi.getBarang()) {
                    writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
                    writer.newLine();
                }
                writer.write("---");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Kesalahan saat memperbarui file transaksi.txt: " + e.getMessage());
        }
    }
}
