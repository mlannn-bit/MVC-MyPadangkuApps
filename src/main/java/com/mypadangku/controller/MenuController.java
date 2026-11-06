package com.mypadangku.controller;

import com.mypadangku.errors.MenuException;
import com.mypadangku.model.Menu;
import com.mypadangku.service.MenuService;

import java.sql.SQLException;
import java.util.List;

public class MenuController {
    private final MenuService menuService;

    public MenuController() {
        this.menuService = new MenuService();
    }

    public List<Menu> getAllMenu() throws SQLException {
        return menuService.getAll();
    }

    public void tambahMenu(Menu menu) throws SQLException, MenuException {
        menuService.insert(menu);
    }

    public void ubahMenu(Menu menu) throws SQLException, MenuException {
        menuService.update(menu);
    }

    public void hapusMenu(int idMenu) throws SQLException, MenuException {
        menuService.delete(idMenu);
    }


}
