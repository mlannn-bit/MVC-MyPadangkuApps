package com.mypadangku.model;

public class Menu {

    //attribute
    private int idMenu;
    private String namaMenu;
    private int harga;
    private String kategori;
    private int stok;

    // constructor
    public Menu(int idMenu, String namaMenu, int harga, String kategori, int stok) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.kategori = kategori;
        this.stok = stok;
    }

    public Menu(String namaMenu, int harga, String kategori, int stok) {
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.kategori = kategori;
        this.stok = stok;
    }

    // getter
    public int getIdMenu() {
        return idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public int getStok() {
        return stok;
    }

    // setter
    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
