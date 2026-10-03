package com.mycompany.cloudgaming;

public class MemberPremium extends Member {

    private static final String[] PILIHAN_RESOLUSI = {"720p", "1080p", "1440p", "4K"};

    private String resolusi;

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
        System.out.printf("[Premium] %s langsung terhubung ke server (tanpa iklan).%n", getUsername());
        System.out.printf("[Premium] Game '%s' berjalan di %s tanpa batas waktu.%n", namaGame, resolusi);
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("Tipe: Premium | Batas waktu: Tidak terbatas | Resolusi: %s%n", resolusi);
    }
}