package com.mycompany.cloudgaming;

import java.util.ArrayList;

public class MemberVIP extends Member implements RiwayatProgres, ModeLiveStreaming {

    private static final String RESOLUSI_TETAP = "4K";
    private static final int BIAYA_BULANAN = 199000;
    private static final int MENIT_PER_POIN = 10;

    private int poinLoyalitas;
    private final ArrayList<String> riwayatProgres = new ArrayList<>();

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
    public void tampilkanFiturPaket() {
        System.out.println("   [VIP] Fitur paket: streaming " + getResolusi()
                + ", server prioritas, tanpa batas waktu, simpan progres ke cloud, live streaming, beta game.");
    }

    @Override
    public void simpanProgresCloud(String namaGame) {
        System.out.println("   [VIP] " + getUsername() + " menyimpan progres '" + namaGame
                + "' ke cloud dengan cadangan otomatis (dapat dilanjutkan dari perangkat mana pun).");

        riwayatProgres.add(namaGame + " (waktu main hari ini: "
                + getDurasiBermainHariIni() + " menit)");
    }

    @Override
    public void tampilkanRiwayatProgres() {
        System.out.println("   [VIP] Riwayat progres cloud " + getUsername() + " (tanpa batas):");

        if (riwayatProgres.isEmpty()) {
            System.out.println("   (belum ada progres tersimpan)");
        }

        for (int i = 0; i < riwayatProgres.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + riwayatProgres.get(i));
        }
    }

    @Override
    public void mulaiLiveStreaming(String namaGame) {
        System.out.println("   [VIP] " + getUsername() + " memulai live streaming '"
                + namaGame + "' lewat server prioritas dengan latensi rendah (kualitas "
                + getResolusi() + ").");
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
        return "Member VIP tidak memiliki batas waktu harian. Poin loyalitas: "
                + poinLoyalitas + ".";
    }

    @Override
    public int hitungBiaya() {
        return BIAYA_BULANAN;
    }

    @Override
    public void mulaiSesi(String namaGame) {
        System.out.printf("   [VIP] %s terhubung ke server PRIORITAS (latensi rendah, tanpa antrean).%n",
                getUsername());
        System.out.printf("   [VIP] Game '%s' berjalan di %s + akses beta game eksklusif.%n",
                namaGame, getResolusi());
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("         Tipe: VIP | Batas waktu: Tidak terbatas | Resolusi: %s | Poin: %d%n",
                getResolusi(), poinLoyalitas);
    }
}