package com.mypadangku;

import com.mypadangku.model.Menu;
import com.mypadangku.service.MenuService;
import com.mypadangku.errors.MenuException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MenuService menuService = new MenuService();

        try {
            Menu menu = new Menu(1, "Dendeng Balado", 35000, "Makanan", 15);
            menuService.update(menu);

            System.out.println("Menu berhasil ditambahkan!");

        } catch (SQLException | MenuException e) {
            System.out.println("Gagal menambahkan menu: " + e.getMessage());
        }

        try {
            List<Menu> daftarMenu = menuService.getAll();

            for (Menu menu : daftarMenu) {
                System.out.println(
                    menu.getIdMenu() + " | " +
                    menu.getNamaMenu() + " | " +
                    menu.getHarga() + " | " +
                    menu.getKategori() + " | " +
                    menu.getStok()
                );
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }

    }
}
