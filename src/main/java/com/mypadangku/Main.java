package com.mypadangku;

import java.sql.SQLException;
import java.util.List;

import com.mypadangku.model.Menu;
import com.mypadangku.repository.MenuRepository;

public class Main {
    public static void main(String[] args) {

        MenuRepository mr = new MenuRepository();
        Menu m1 = new Menu("Soto Rongawi", 20000, "Makanan", 10);
        Menu m2 = new Menu(1, "Rendang", 20000, "Makanan", 50);
        

        try {
            List<Menu> daftarMenu = mr.getAll();

            for(Menu menu : daftarMenu) {
                System.out.println("ID :M-" + menu.getIdMenu());
                System.out.println("Nama :" + menu.getNamaMenu());
                System.out.println("Harga :" + menu.getHarga());
                System.out.println("Kategori :" + menu.getKategori());
                System.out.println("Stok :" + menu.getStok());
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Gagal!");
            e.printStackTrace();
        }

        try {
            mr.delete(10);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Gagal!");
        }

        try {
            List<Menu> daftarMenu = mr.getAll();

            for(Menu menu : daftarMenu) {
                System.out.println("ID :M-" + menu.getIdMenu());
                System.out.println("Nama :" + menu.getNamaMenu());
                System.out.println("Harga :" + menu.getHarga());
                System.out.println("Kategori :" + menu.getKategori());
                System.out.println("Stok :" + menu.getStok());
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Gagal!");
            e.printStackTrace();
        }

    }
}
