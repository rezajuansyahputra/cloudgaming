package com.mycompany.cloudgaming;

import java.util.Scanner;

public class CloudGaming {
    private static final int MAKS_DATA = 100;
    private static final Member[] daftarMember = new Member[MAKS_DATA];
    private static int jumlahMember = 0;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        isiDataAwal();
        jalankanMenu();
        scanner.close();
    }

    private static void isiDataAwal() {
        tambahKeArray(new MemberGratis("Reza", "reza@gmail.com"));
        tambahKeArray(new MemberPremium("Farel", "farel@gmail.com", "1080p"));
        tambahKeArray(new MemberGratis("Reval", "reval@gmail.com"));
        tambahKeArray(new MemberPremium("Dava", "dava@gmail.com", "4K"));
        tambahKeArray(new MemberGratis("Aldi", "aldi@gmail.com"));
        tambahKeArray(new MemberVIP("juan", "juan@gmail.com"));
        tambahKeArray(new MemberVIP("ando", "ando@gmail.com"));
    }

    private static void jalankanMenu() {
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaInteger("Pilih menu: ");
            switch (pilihan) {
                case 1:
                    tambahDataBaru();
                    break;
                case 2:
                    tampilkanSemuaData();
                    break;
                case 3:
                    aksiMember();
                    break;
                case 4:
                    menuLayananStreaming();
                    break;
                case 5:
                    System.out.println("\nProgram selesai. Terima kasih.");
                    break;
                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }
        } while (pilihan != 5);
    }

    private static void tampilkanMenu() {
        System.out.println("\n============================================");
        System.out.println("            SISTEM CLOUD GAMING");
        System.out.println("============================================");
        System.out.println("1. Tambah Data Baru");
        System.out.println("2. Tampilkan Seluruh Data");
        System.out.println("3. Pencarian member");
        System.out.println("4. Layanan Streaming Game");
        System.out.println("5. Keluar");
        System.out.println("============================================");
    }

    private static void tambahDataBaru() {
        if (jumlahMember >= MAKS_DATA) {
            System.out.println("Penyimpanan data sudah penuh.");
            return;
        }
        System.out.println("\n------------- TAMBAH MEMBER ---------------");
        String username = bacaString("Username: ");
        while (username.isEmpty()) {
            System.out.println("Username tidak boleh kosong.");
            username = bacaString("Username: ");
        }
        String email = bacaString("Email: ");
        while (!email.contains("@")) {
            System.out.println("Email harus mengandung '@'.");
            email = bacaString("Email: ");
        }

        System.out.println("\nPilih tipe member:");
        System.out.println("1. Member Gratis");
        System.out.println("2. Member Premium");
        System.out.println("3. Member VIP");
        int tipe = bacaInteger("Pilihan: ");

        try {
            Member baru; 
            switch (tipe) {
                case 1:
                    baru = new MemberGratis(username, email);    
                    break;
                case 2:
                    baru = new MemberPremium(username, email, pilihResolusiPremium()); 
                    break;
                case 3:
                    baru = new MemberVIP(username, email);       
                    break;
                default:
                    System.out.println("Tipe member tidak tersedia.");
                    return;
            }
            tambahKeArray(baru);
            System.out.println("Member " + baru.getTipe() + " berhasil ditambahkan.");
            System.out.println(baru.getKeteranganKuota());   
            System.out.println("Resolusi: " + baru.getResolusi());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambahkan: " + e.getMessage());
        }
    }

    private static String pilihResolusiPremium() {
        String[] pilihan = MemberPremium.getPilihanResolusi();
        System.out.println("\nPilih resolusi Premium:");
        for (int i = 0; i < pilihan.length; i++) {
            System.out.println((i + 1) + ". " + pilihan[i]);
        }
        int nomor = bacaInteger("Pilihan resolusi: ");
        while (nomor < 1 || nomor > pilihan.length) {
            System.out.println("Pilihan resolusi tidak tersedia.");
            nomor = bacaInteger("Pilihan resolusi: ");
        }
        return pilihan[nomor - 1];
    }

    private static void tampilkanSemuaData() {
        System.out.println("\n============= SELURUH DATA MEMBER =============");
        if (jumlahMember == 0) {
            System.out.println("Belum ada data member.");
            return;
        }
        int gratis = 0, premium = 0, vip = 0;
        for (int i = 0; i < jumlahMember; i++) {
            
            daftarMember[i].tampilkanInfo();
            System.out.println("------------------------------------------------");
            switch (daftarMember[i].getTipe()) {
                case "Gratis":
                    gratis++;
                    break;
                case "Premium":
                    premium++;
                    break;
                default:
                    vip++;
            }
        }
        System.out.println("Jumlah data pada array : " + jumlahMember);
        System.out.println("Total member terdaftar : " + Member.getTotalMemberTerdaftar());
        System.out.printf("Komposisi              : Gratis=%d | Premium=%d | VIP=%d%n", gratis, premium, vip);
    }

    private static void aksiMember() {
        System.out.println("\n============= PENCARIAN / AKSI =============");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan Username");
        System.out.println("3. Tambah waktu bermain");
        System.out.println("4. Ubah resolusi Premium");
        int aksi = bacaInteger("Pilih aksi: ");
        switch (aksi) {
            case 1:
                tampilkanHasilPencarian(cariMember(bacaInteger("Masukkan ID: ")));
                break;
            case 2:
                tampilkanHasilPencarian(cariMember(bacaString("Masukkan username: ")));
                break;
            case 3:
                tambahWaktuMember();
                break;
            case 4:
                ubahResolusiPremium();
                break;
            default:
                System.out.println("Pilihan aksi tidak tersedia.");
        }
    }

    private static Member cariMember(int id) {
        for (int i = 0; i < jumlahMember; i++) {
            if (daftarMember[i].cariMember(id)) {
                return daftarMember[i];
            }
        }
        return null;
    }

    private static Member cariMember(String username) {
        for (int i = 0; i < jumlahMember; i++) {
            if (daftarMember[i].cariMember(username)) {
                return daftarMember[i];
            }
        }
        return null;
    }

    private static void tampilkanHasilPencarian(Member member) {
        if (member != null) {
            System.out.println("\nData ditemukan:");
            member.tampilkanInfo();
        } else {
            System.out.println("Member tidak ditemukan.");
        }
    }

    private static void tambahWaktuMember() {
        Member member = cariMember(bacaInteger("Masukkan ID member: "));
        if (member == null) {
            System.out.println("Member tidak ditemukan.");
            return;
        }
        int menit = bacaInteger("Tambahkan waktu bermain (menit): ");
        if (menit <= 0) {
            System.out.println("Waktu harus lebih dari 0 menit.");
            return;
        }

        if (member.tambahWaktuBermain(menit)) {
            System.out.println("Waktu berhasil ditambahkan.");
        } else {
            System.out.println("Gagal menambahkan waktu.");
        }
        System.out.println(member.getKeteranganKuota());
    }

    private static void ubahResolusiPremium() {
        Member member = cariMember(bacaInteger("Masukkan ID member Premium: "));
        if (member instanceof MemberPremium) {
            MemberPremium premium = (MemberPremium) member; 
            premium.setResolusi(pilihResolusiPremium());
            System.out.println("Resolusi berhasil diubah menjadi " + premium.getResolusi());
        } else if (member instanceof MemberGratis) {
            System.out.println("Member Gratis tidak dapat mengubah resolusi.");
            System.out.println("Resolusi Member Gratis tetap 720p.");
        } else if (member instanceof MemberVIP) {
            System.out.println("Member VIP tidak dapat mengubah resolusi.");
            System.out.println("Resolusi Member VIP tetap " + member.getResolusi() + ".");
        } else {
            System.out.println("Member tidak ditemukan.");
        }
    }

    private static void menuLayananStreaming() {
        System.out.println("\n========== LAYANAN STREAMING GAME ==========");
        System.out.println("1. sesi bermain satu member");
        System.out.println("2. sesi bermain seluruh member");
        System.out.println("3. Tagihan langganan bulanan seluruh member");
        int pilihan = bacaInteger("Pilih layanan: ");
        switch (pilihan) {
            case 1:
                Member member = cariMember(bacaInteger("Masukkan ID member: "));
                if (member == null) {
                    System.out.println("Member tidak ditemukan.");
                    break;
                }
                String game = bacaString("Nama game: ");
                int menit = bacaInteger("Durasi bermain (menit): ");
                mulaiStreaming(member, game, menit);  
                break;
            case 2:
                streamingSemuaMember();
                break;
            case 3:
                tampilkanTagihan();
                break;
            default:
                System.out.println("Pilihan layanan tidak tersedia.");
        }
    }

    
    private static void mulaiStreaming(Member member, String namaGame, int menit) {
        System.out.println("\n>> Member      : " + member.getUsername() + " (ID " + member.getIdMember() + ")");
        System.out.println("   Tipe referensi (compile-time) : Member");
        System.out.println("   Wujud asli objek (runtime)    : " + member.getClass().getSimpleName());
        
        member.mulaiSesi(namaGame, menit);
    }

    
    private static void mulaiStreaming(Member member, String namaGame) {
        mulaiStreaming(member, namaGame, 10);
    }

    private static void streamingSemuaMember() {
        if (jumlahMember == 0) {
            System.out.println("Belum ada data member.");
            return;
        }
        String game = bacaString("Nama game yang dimainkan semua member: ");
        int menit = bacaInteger("Durasi bermain tiap member (menit): ");
        System.out.println("\n====== SESI BERMAIN SELURUH MEMBER ======");
        for (int i = 0; i < jumlahMember; i++) {
            
            mulaiStreaming(daftarMember[i], game, menit);
        }
        System.out.println("\nCatatan: hasil tiap member berbeda karena dynamic binding.");
    }

    private static void tampilkanTagihan() {
        if (jumlahMember == 0) {
            System.out.println("Belum ada data member.");
            return;
        }
        int bulan = bacaInteger("Jumlah bulan langganan: ");
        if (bulan <= 0) {
            System.out.println("Jumlah bulan harus lebih dari 0.");
            return;
        }
        System.out.println("\n================ TAGIHAN LANGGANAN ================");
        System.out.printf("%-4s %-10s %-8s %-8s %12s %14s%n",
                "ID", "Username", "Tipe", "Resolusi", "Per Bulan", "Total " + bulan + " bln");
        System.out.println("---------------------------------------------------");
        int totalSemua = 0;
        for (int i = 0; i < jumlahMember; i++) {
            Member m = daftarMember[i];
            
            int total = m.hitungBiaya(bulan);
            totalSemua += total;
            System.out.printf("%-4d %-10s %-8s %-8s %,12d %,14d%n",
                    m.getIdMember(), m.getUsername(), m.getTipe(), m.getResolusi(),
                    m.hitungBiaya(), total);
        }
        System.out.println("---------------------------------------------------");
        System.out.printf("Total pendapatan %d bulan: Rp %,d%n", bulan, totalSemua);
        
        Member contoh = daftarMember[jumlahMember - 1];
        System.out.printf("Contoh diskon 10%% untuk %s (%d bulan): Rp %,d%n",
                contoh.getUsername(), bulan, contoh.hitungBiaya(bulan, 10.0));
    }

    private static void tambahKeArray(Member member) {
        if (jumlahMember < MAKS_DATA) {
            daftarMember[jumlahMember] = member; 
            jumlahMember++;
        }
    }

    private static String bacaString(String pesan) {
        System.out.print(pesan);
        return scanner.nextLine().trim();
    }

    private static int bacaInteger(String pesan) {
        while (true) {
            try {
                System.out.print(pesan);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }
}