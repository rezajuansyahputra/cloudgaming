package com.mycompany.cloudgaming;

public class MemberVIP extends Member {

    private static final String RESOLUSI_TETAP = "4K";
    private static final int BIAYA_BULANAN = 199000;
    private static final int MENIT_PER_POIN = 10;

    private int poinLoyalitas;

    public MemberVIP(String username, String email) {
        super(username, email);
        this.poinLoyalitas = 0;
    }

    public int getPoinLoyalitas() {
        return poinLoyalitas;
    }

    public void setPoinLoyalitas(int poinLoyalitas) {
        if (poinLoyalitas >= 0) {
            this.poinLoyalitas = poinLoyalitas;
        } else {
            throw new IllegalArgumentException("Poin loyalitas tidak boleh negatif.");
        }
    }

    @Override
    public String getTipe() {
        return "VIP";
    }

    @Override
    public String getResolusi() {
        return RESOLUSI_TETAP + " HDR";
    }

    @Override
    public boolean tambahWaktuBermain(int menit) {
        super.tambahDurasiBermain(menit);
        this.poinLoyalitas += menit / MENIT_PER_POIN;
        return true;
    }

    @Override
    public String getKeteranganKuota() {
        return "Member VIP tidak memiliki batas waktu harian. Poin loyalitas: " + poinLoyalitas + ".";
    }

    @Override
    public int hitungBiaya() {
        return BIAYA_BULANAN;
    }

    @Override
    public void mulaiSesi(String namaGame) {
        System.out.printf("   [VIP] %s terhubung ke server PRIORITAS (latensi rendah, tanpa antrean).%n", getUsername());
        System.out.printf("   [VIP] Game '%s' berjalan di %s + akses beta game eksklusif.%n", namaGame, getResolusi());
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("         Tipe: VIP | Batas waktu: Tidak terbatas | Resolusi: %s | Poin: %d%n",
                getResolusi(), poinLoyalitas);
    }
}