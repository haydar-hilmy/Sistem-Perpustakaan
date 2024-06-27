package com.minim.library.pbo_perpus.models;

public class Buku {
    private int idBuku, tahun_terbit;
    private String judul, penerbit, penulis;
    public Buku(int idBuku, String judul, String penerbit, String penulis,int tahun_terbit) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.tahun_terbit = tahun_terbit;
    }
    public int getIdBuku() {
        return idBuku;
    }
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public String getPenerbit() {
        return penerbit;
    }
    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }
    public String getPenulis() {
        return penulis;
    }
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    public int getTahun_terbit() {
        return tahun_terbit;
    }
    public void setTahun_terbit(int tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }
}