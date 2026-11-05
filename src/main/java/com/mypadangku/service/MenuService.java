package com.mypadangku.service;

import com.mypadangku.repository.MenuRepository;
import com.mypadangku.model.Menu;
import com.mypadangku.errors.MenuException;

import java.sql.SQLException;
import java.util.List;

public class MenuService {
    
    private MenuRepository menuRepository;

    public  MenuService() {
        this.menuRepository = new MenuRepository();
    }

    private void validateMenu(Menu menu) throws MenuException {

        // validasi object
        if (menu == null) {
            throw new MenuException("Data menu tidak boleh kosong!");
        }

        // validasi nama menu
        
        if (menu.getNamaMenu() == null || menu.getNamaMenu().trim().isEmpty()) {
            throw new MenuException("Nama menu tidak boleh kosong!");
        }
        
        if (menu.getNamaMenu().trim().length() > 100) {
            throw new MenuException("Nama menu maksimal 100 Karakter!");
        }
        
        // validasi harga
        if (menu.getHarga() <= 0) {
            throw new MenuException("Harga harus lebih dari 0!");
        }


        // validasi kategori
        if (menu.getKategori() == null || !(menu.getKategori().trim().equalsIgnoreCase("Makanan") || menu.getKategori().trim().equalsIgnoreCase("Minuman"))) {
            throw new MenuException("Kategori hanya boleh Makanan atau Minuman!");
        }

        // validasi stok
        if (menu.getStok() < 0) {
            throw new MenuException("Stok tidak boleh negatif!");
        }
    }

    private void validateMenuForInsert(Menu menu) throws SQLException, MenuException {
        validateMenu(menu);

        if (menuRepository.existsByNamaMenu(menu.getNamaMenu().trim())) {
            throw new MenuException("Nama menu sudah terdaftar!");
        }
    }

    private void validateMenuForUpdate(Menu menu) throws SQLException, MenuException {
        validateMenu(menu);

        if (menu.getIdMenu() <= 0) {
            throw new MenuException("ID menu tidak valid!");
        }

        if (menuRepository.existsByNamaMenuAndIdMenuNot(menu.getNamaMenu().trim(), menu.getIdMenu())) {
            throw new MenuException("Nama menu sudah digunakan menu lain!");
        }
    }

    public void insert(Menu menu) throws SQLException, MenuException {
        validateMenuForInsert(menu);
        menuRepository.insert(menu);
    }

    public void update(Menu menu) throws SQLException, MenuException {
        validateMenuForUpdate(menu);
        menuRepository.update(menu);
    }

    public void delete(int idMenu) throws SQLException, MenuException {
        
        if (idMenu <= 0) {
            throw new MenuException("ID menu tidak valid!");
        }

        menuRepository.delete(idMenu);
    }

    public List<Menu> getAll() throws SQLException {
        return menuRepository.getAll();
    }

}
