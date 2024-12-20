package models;

import java.io.*;
import java.util.*;

// Menginisialisasi class CustomerDriver yang meng-extends class Driver
public class CustomerDriver extends Driver {
    private Customer customer;
    private Scanner scanner;

    // Konstruktor untuk inisialisasi objek CustomerDriver dengan customer dan scanner
    public CustomerDriver(Customer customer, Scanner scanner) {
        super(customer.getAkun());
        this.customer = customer;
        this.scanner = scanner;
    }

    // Override metode menu dari kelas Driver untuk menampilkan menu customer
    @Override
    public void menu() {
        menuCustomer();
    }

    // Menampilkan menu pilihan untuk customer
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
            System.out.println("\n=============================================\n");
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

    // Menampilkan daftar barang yang tersedia di toko
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

    // Menambahkan barang ke keranjang belanja
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
                            stok -= jumlahBarang;
                            Barang barang = new Barang(nama, harga, jumlahBarang);
                            customer.getKeranjang().tambahBarang(barang);
                            simpanKeranjang(barang);
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

    // Menyimpan barang yang ditambahkan ke keranjang ke dalam file keranjang.txt
    private void simpanKeranjang(Barang barang) {
        File file = new File("keranjang.txt");
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(barang.getNama() + "," + barang.getHarga() + "," + barang.getStok());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("\nTerjadi kesalahan saat menyimpan ke file keranjang: " + e.getMessage());
        }
    }
    
    // Menampilkan barang yang ada dalam keranjang belanja
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

    // Proses checkout dan memilih metode pembayaran
    public void checkout() {
        Admin adminMenu = new Admin("admin123", "password123");
        System.out.println("\nPilih Metode Pembayaran:");
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
            } else {
                System.out.println("Terjadi kesalahan saat mengosongkan keranjang.");
            }
        } else {
            System.out.println("File keranjang.txt tidak ditemukan.");
        }
    }
    
    // Menampilkan riwayat belanja customer, baik barang yang belum diterima maupun yang sudah diterima
    public void lihatRiwayatBelanja() {
            System.out.println("\n============== RIWAYAT BELANJA ==============\n");
            
        // Membaca barang yang belum diterima dari file transaksi.txt
        File fileTransaksi = new File("transaksi.txt");
        if (fileTransaksi.exists()) {
            System.out.println("Barang Belum Diterima:");
            try (BufferedReader reader = new BufferedReader(new FileReader(fileTransaksi))) {
                String line;
                String currentCustomer = "";
                boolean isCustomer = false;
                
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("cust")) {
                        currentCustomer = line;
                        isCustomer = true;
                        System.out.println("ID Customer: " + currentCustomer);
                        System.out.println("Daftar Barang:");
                    } else if (!line.equals("---")) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            String namaBarang = parts[0];
                            double harga = Double.parseDouble(parts[1]);
                            int jumlah = Integer.parseInt(parts[2]);
                            System.out.printf("  - %s : %.1f (%d pcs)%n", namaBarang, harga, jumlah);
                        }
                    } else { 
                        if (isCustomer) {
                            System.out.println();
                        }
                        isCustomer = false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat membaca file transaksi.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Tidak ada barang yang belum diterima.");
        }
                
            // Membaca barang yang sudah diterima dari file transaksi_diterima.txt
            System.out.println("\n============== RIWAYAT BELANJA ==============\n");
            System.out.println("Barang Yang Diterima:");
        
            File fileDiproses = new File("transaksi_diterima.txt");
            if (!fileDiproses.exists()) {
                System.out.println("Tidak ada barang yang sudah diterima.");
                return;
            }
        
            // Map untuk menggabungkan barang berdasarkan Customer ID
            Map<String, List<String>> transaksiMap = new LinkedHashMap<>();
        
            try (BufferedReader reader = new BufferedReader(new FileReader(fileDiproses))) {
                String line;
                String currentCustomerID = null;
        
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("---")) {
                        if (currentCustomerID == null) {
                            currentCustomerID = line;
                            transaksiMap.putIfAbsent(currentCustomerID, new ArrayList<>());
                        } else {
                            transaksiMap.get(currentCustomerID).add(line);
                        }
                    } else {
                        currentCustomerID = null; 
                    }
                }
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat membaca file transaksi_diproses.txt: " + e.getMessage());
            }
        
            // Cetak invoice berdasarkan Customer ID
            for (Map.Entry<String, List<String>> entry : transaksiMap.entrySet()) {
                String customerID = entry.getKey();
                List<String> daftarBarang = entry.getValue();
        
                System.out.println("\n============= INVOICE =============");
                System.out.println("Customer ID : " + customerID);
                System.out.println("Daftar Barang :");
        
                double totalHarga = 0.0;
                for (String barang : daftarBarang) {
                    String[] data = barang.split(",");
                    String namaBarang = data[0];
                    double harga = Double.parseDouble(data[1]);
                    int jumlah = Integer.parseInt(data[2]);
                    totalHarga += harga * jumlah;
        
                    System.out.printf("  %s: Rp %.2f x %d pcs%n", namaBarang, harga, jumlah);
                }
        
                System.out.println("-----------------------------------");
                System.out.printf("Total Harga : Rp %.2f%n", totalHarga);
                System.out.println("Pembayaran berhasil diproses.");
                System.out.println("==================================\n");
            }
    }

}
