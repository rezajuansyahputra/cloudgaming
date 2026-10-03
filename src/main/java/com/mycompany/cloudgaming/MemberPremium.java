package com.mycompany.cloudgaming;

public class MemberPremium extends Member {
    private static final String[] PILIHAN_RESOLUSI = {"720p", "1080p", "1440p", "4K"};
    private String resolusi;

    public MemberPremium(String username, String email, String resolusi) {
        super(username, email);
        setResolusi(resolusi);
    }

    public String getResolusi() {
        return resolusi;
    }

    public void setResolusi(String resolusi) {
        if (resolusi != null && resolusiDiizinkan(resolusi)) {
            this.resolusi = resolusi;
        } else {
            throw new IllegalArgumentException("Resolusi premium tidak tersedia.");
        }
    }

    public static String[] getPilihanResolusi() {
        return PILIHAN_RESOLUSI.clone();
    }

    private boolean resolusiDiizinkan(String resolusi) {
        for (String pilihan : PILIHAN_RESOLUSI) {
            if (pilihan.equalsIgnoreCase(resolusi)) {
                return true;
            }
        }
        return false;
    }

    public void tambahWaktuBermain(int menit) {
        if (menit > 0) {
            super.tambahDurasiBermain(menit);
        }
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(
            "         Tipe: Premium | Batas waktu: Tidak terbatas | Resolusi: %s%n",
            resolusi
        );
    }
}
