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

    @Override
    public String getTipe() {
        return "Gratis";
    }

    @Override
    public String getResolusi() {
        return RESOLUSI_TETAP;
    }

    @Override
    public boolean tambahWaktuBermain(int menit) {
        if (getDurasiBermainHariIni() + menit <= BATAS_WAKTU_HARIAN) {
            super.tambahDurasiBermain(menit);
            return true;
        }
        return false;
    }

    @Override
    public String getKeteranganKuota() {
        return "Member Gratis hanya memiliki batas " + BATAS_WAKTU_HARIAN
                + " menit per hari. Sisa kuota: "
                + (BATAS_WAKTU_HARIAN - getDurasiBermainHariIni()) + " menit.";
    }

    @Override
    public int hitungBiaya() {
        return 0;
    }

    @Override
    public void mulaiSesi(String namaGame) {
        System.out.printf("   [Gratis] %s masuk antrean server... menonton iklan 15 detik...%n", getUsername());
        System.out.printf("   [Gratis] Game '%s' berjalan di %s (kuota sisa %d menit).%n",
                namaGame, RESOLUSI_TETAP, BATAS_WAKTU_HARIAN - getDurasiBermainHariIni());
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("         Tipe: Gratis | Batas: %d menit/hari | Resolusi: %s%n",
                BATAS_WAKTU_HARIAN, RESOLUSI_TETAP);
    }
}