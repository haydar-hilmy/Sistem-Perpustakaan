public class Anggota {
    int idAnggota;
    String nama, alamat, instansi;

    public Anggota(int idAnngota, String nama, String alamat, String instansi) {
        this.idAnggota = idAnngota;
        this.nama = nama;
        this.alamat = alamat;
        this.instansi = instansi;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
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

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }
}