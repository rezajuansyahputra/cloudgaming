package com.mycompany.cloudgaming;

import java.util.ArrayList;

public class MemberPremium extends Member implements RiwayatProgres, ModeLiveStreaming {

    private static final String[] PILIHAN_RESOLUSI = {"720p", "1080p", "1440p", "4K"};

    private static final int MAKS_RIWAYAT = 5;

    private String resolusi;
    private final ArrayList<String> riwayatProgres = new ArrayList<>();

    public MemberPremium(String username, String email, String resolusi) {
        super(username, email);
        setResolusi(resolusi);
    }

    @Override
    public String getResolusi() {
        return resolusi;
    }

    public void setResolusi(String resolusi) {
        if (resolusiDiizinkan(resolusi)) {
            this.resolusi = resolusi.trim();
        } else {
            throw new IllegalArgumentException("Resolusi premium tidak tersedia.");
        }
    }

    public static String[] getPilihanResolusi() {
        return PILIHAN_RESOLUSI.clone();
    }

    private boolean resolusiDiizinkan(String resolusi) {
        if (resolusi == null) {
            return false;
        }
        for (String pilihan : PILIHAN_RESOLUSI) {
            if (pilihan.equalsIgnoreCase(resolusi.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tampilkanFiturPaket() {
        System.out.println("   [Premium] Fitur paket: streaming " + resolusi
                + ", tanpa batas waktu, tanpa iklan, simpan progres ke cloud, live streaming.");
    }

    @Override
    public void simpanProgresCloud(String namaGame) {
        System.out.println("   [Premium] " + getUsername() + " menyimpan progres '" + namaGame
                + "' ke cloud (dapat dilanjutkan dari perangkat mana pun).");
        if (riwayatProgres.size() == MAKS_RIWAYAT) {
            System.out.println("   [Premium] Riwayat penuh (" + MAKS_RIWAYAT + "), catatan terlama dihapus: " + riwayatProgres.remove(0));
        }
        riwayatProgres.add(namaGame + " (waktu main hari ini: " + getDurasiBermainHariIni() + " menit)");
    }

    @Override
    public void tampilkanRiwayatProgres() {
        System.out.println("   [Premium] Riwayat progres cloud " + getUsername() + " (maks " + MAKS_RIWAYAT + " terakhir):");
        if (riwayatProgres.isEmpty()) {
            System.out.println("   (belum ada progres tersimpan)");
        }
        for (int i = 0; i < riwayatProgres.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + riwayatProgres.get(i));
        }
    }

    @Override
    public void mulaiLiveStreaming(String namaGame) {
        System.out.println("   [Premium] " + getUsername() + " memulai live streaming '" + namaGame
                + "' ke platform siaran (kualitas " + resolusi + ").");
    }

    @Override
    public String getTipe() {
        return "Premium";
    }

    
    @Override
    public boolean tambahWaktuBermain(int menit) {
        super.tambahDurasiBermain(menit);
        return true;
    }

    @Override
    public String getKeteranganKuota() {
        return "Member Premium tidak memiliki batas waktu harian.";
    }

    @Override
    public int hitungBiaya() {
        switch (resolusi.toLowerCase()) {
            case "720p":
                return 49000;
            case "1080p":
                return 79000;
            case "1440p":
                return 99000;
            default:
                return 129000;
        }
    }

    @Override
    public void mulaiSesi(String namaGame) {
        System.out.printf("   [Premium] %s langsung terhubung ke server (tanpa iklan).%n", getUsername());
        System.out.printf("   [Premium] Game '%s' berjalan di %s tanpa batas waktu.%n", namaGame, resolusi);
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("         Tipe: Premium | Batas waktu: Tidak terbatas | Resolusi: %s%n", resolusi);
    }
}