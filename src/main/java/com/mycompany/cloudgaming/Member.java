package com.mycompany.cloudgaming;

public class Member {
    private int idMember;
    private String username;
    private String email;
    private int durasiBermainHariIni;

    private static int totalMemberTerdaftar = 0;

    public Member(String username, String email) {
        setUsername(username);
        setEmail(email);
        this.durasiBermainHariIni = 0;

        totalMemberTerdaftar++;
        this.idMember = totalMemberTerdaftar;
    }

    public int getIdMember() {
        return idMember;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getDurasiBermainHariIni() {
        return durasiBermainHariIni;
    }

    public static int getTotalMemberTerdaftar() {
        return totalMemberTerdaftar;
    }

    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username.trim();
        } else {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }
    }

    public void setEmail(String email) {
        if (email != null && email.trim().contains("@")) {
            this.email = email.trim();
        } else {
            throw new IllegalArgumentException("Email harus memiliki format yang benar.");
        }
    }

    public void setDurasiBermainHariIni(int durasiBermainHariIni) {
        if (durasiBermainHariIni >= 0) {
            this.durasiBermainHariIni = durasiBermainHariIni;
        } else {
            throw new IllegalArgumentException("Durasi bermain tidak boleh negatif.");
        }
    }

    public void tambahDurasiBermain(int menit) {
        if (menit > 0) {
            this.durasiBermainHariIni += menit;
        }
    }

    public void tampilkanInfo() {
        System.out.printf(
            "ID: %-3d | Username: %-10s | Email: %-22s | Main hari ini: %d menit%n",
            idMember, username, email, durasiBermainHariIni
        );
    }

    public boolean cariMember(int id) {
        return this.idMember == id;
    }

    public boolean cariMember(String username) {
        return this.username.equalsIgnoreCase(username);
    }
}
