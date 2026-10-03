package com.mycompany.cloudgaming;

public class MemberGratis extends Member {
    private static final int BATAS_WAKTU_HARIAN = 30;
    private static final String RESOLUSI_TETAP = "720p";

    public MemberGratis(String username, String email) {
        super(username, email);
    }

    public int getBatasWaktuHarian() {
        return BATAS_WAKTU_HARIAN;
    }

    public String getResolusi() {
        return RESOLUSI_TETAP;
    }

    public boolean tambahWaktuBermain(int menit) {
        if (menit <= 0) {
            return false;
        }

        if (getDurasiBermainHariIni() + menit <= BATAS_WAKTU_HARIAN) {
            super.tambahDurasiBermain(menit);
            return true;
        }

        return false;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(
            "         Tipe: Gratis | Batas: %d menit/hari | Resolusi: %s%n",
            BATAS_WAKTU_HARIAN, RESOLUSI_TETAP
        );
    }
}
