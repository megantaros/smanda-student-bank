package com.example.tabungansiswamobileapp.model;

public class TransaksiModel {
    public TransaksiModel(String id_transaksi, String id_siswa, String tanggal, String keterangan, String nominal) {
        this.id_transaksi = id_transaksi;
        this.id_siswa = id_siswa;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String id_transaksi, id_siswa, tanggal, keterangan, nominal;
}
