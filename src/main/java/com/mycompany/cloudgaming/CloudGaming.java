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
                    System.out.println("\nProgram selesai. Terima kasih.");
                    break;
                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }
        } while (pilihan != 4);
    }

    private static void tampilkanMenu() {
        System.out.println("\n============================================");
        System.out.println("           SISTEM CLOUD GAMING");
        System.out.println("============================================");
        System.out.println("1. Tambah Data Baru");
        System.out.println("2. Tampilkan Seluruh Data");
        System.out.println("3. Pencarian ");
        System.out.println("4. Keluar");
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
        int tipe = bacaInteger("Pilihan: ");

        try {
            if (tipe == 1) {
                MemberGratis member = new MemberGratis(username, email);
                tambahKeArray(member);
                System.out.println("Member Gratis berhasil ditambahkan.");
                System.out.println("Batas waktu: 30 menit/hari");
                System.out.println("Resolusi: 720p (tetap)");
            } else if (tipe == 2) {
                String resolusi = pilihResolusiPremium();
                MemberPremium member = new MemberPremium(username, email, resolusi);
                tambahKeArray(member);
                System.out.println("Member Premium berhasil ditambahkan.");
                System.out.println("Batas waktu: Tidak terbatas");
                System.out.println("Resolusi: " + resolusi);
            } else {
                System.out.println("Tipe member tidak tersedia.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Data tidak valid: " + e.getMessage());
        }
    }

    private static String pilihResolusiPremium() {
        String[] pilihan = MemberPremium.getPilihanResolusi();
        System.out.println("\nPilih resolusi Premium:");
        for (int i = 0; i < pilihan.length; i++) {
            System.out.println((i + 1) + ". " + pilihan[i]);
        }

        int pilihanResolusi;
        do {
            pilihanResolusi = bacaInteger("Pilihan resolusi: ");
            if (pilihanResolusi < 1 || pilihanResolusi > pilihan.length) {
                System.out.println("Pilihan resolusi tidak tersedia.");
            }
        } while (pilihanResolusi < 1 || pilihanResolusi > pilihan.length);

        return pilihan[pilihanResolusi - 1];
    }

    private static void tampilkanSemuaData() {
        System.out.println("\n============= SELURUH DATA MEMBER =============");

        if (jumlahMember == 0) {
            System.out.println("Belum ada data member.");
            return;
        }

        for (int i = 0; i < jumlahMember; i++) {
            daftarMember[i].tampilkanInfo();
            System.out.println("------------------------------------------------");
        }

        System.out.println("Total data: " + jumlahMember);
        System.out.println("Total objek member: " + Member.getTotalMemberTerdaftar());
    }

    private static void aksiMember() {
        System.out.println("\n============= PENCARIAN / AKSI =============");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan Username");
        System.out.println("3. Tambah waktu bermain");
        System.out.println("4. Ubah resolusi Premium");
        int pilihan = bacaInteger("Pilih aksi: ");

        switch (pilihan) {
            case 1:
                int id = bacaInteger("Masukkan ID: ");
                Member berdasarkanId = cariMember(id);
                tampilkanHasilPencarian(berdasarkanId);
                break;
            case 2:
                String username = bacaString("Masukkan username: ");
                Member berdasarkanUsername = cariMember(username);
                tampilkanHasilPencarian(berdasarkanUsername);
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
        int id = bacaInteger("Masukkan ID member: ");
        Member member = cariMember(id);

        if (member == null) {
            System.out.println("Member tidak ditemukan.");
            return;
        }

        int menit = bacaInteger("Tambahkan waktu bermain (menit): ");
        if (menit <= 0) {
            System.out.println("Waktu harus lebih dari 0 menit.");
            return;
        }

        if (member instanceof MemberGratis) {
            MemberGratis gratis = (MemberGratis) member;
            if (gratis.tambahWaktuBermain(menit)) {
                System.out.println("Waktu berhasil ditambahkan.");
                System.out.println("Sisa waktu hari ini: "
                        + (gratis.getBatasWaktuHarian() - gratis.getDurasiBermainHariIni())
                        + " menit.");
            } else {
                System.out.println("Gagal menambahkan waktu.");
                System.out.println("Member Gratis hanya memiliki batas 30 menit per hari.");
            }
        } else if (member instanceof MemberPremium) {
            MemberPremium premium = (MemberPremium) member;
            premium.tambahWaktuBermain(menit);
            System.out.println("Waktu berhasil ditambahkan.");
            System.out.println("Member Premium tidak memiliki batas waktu harian.");
        }
    }

    private static void ubahResolusiPremium() {
        int id = bacaInteger("Masukkan ID member Premium: ");
        Member member = cariMember(id);

        if (member instanceof MemberPremium) {
            MemberPremium premium = (MemberPremium) member;
            String resolusiBaru = pilihResolusiPremium();
            premium.setResolusi(resolusiBaru);
            System.out.println("Resolusi berhasil diubah menjadi " + resolusiBaru + ".");
        } else if (member instanceof MemberGratis) {
            System.out.println("Member Gratis tidak dapat mengubah resolusi.");
            System.out.println("Resolusi Member Gratis tetap 720p.");
        } else {
            System.out.println("Member tidak ditemukan.");
        }
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
