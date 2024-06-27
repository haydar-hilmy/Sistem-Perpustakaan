package com.minim.library.pbo_perpus.models;

public class Pustakawan {
    int idPustakawan;
    String nama, alamat;

    public Pustakawan(int idPustakawan, String nama, String alamat) {
        this.idPustakawan = idPustakawan;
        this.nama = nama;
        this.alamat = alamat;
    }

    public int getIdPustakawan() {
        return idPustakawan;
    }

    public void setIdPustakawan(int idPustakawan) {
        this.idPustakawan = idPustakawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
