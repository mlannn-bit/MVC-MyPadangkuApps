package com.mypadangku.repository;

import com.mypadangku.config.DatabaseConnection;
import com.mypadangku.model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class MenuRepository {
    
    public List<Menu> getAll() throws SQLException {

        List<Menu> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                int id = result.getInt("id_menu");
                String nama = result.getString("nama_menu");
                int harga = result.getInt("harga");
                String kategori = result.getString("kategori");
                int stok = result.getInt("stok");

                Menu menu = new Menu(id, nama, harga, kategori, stok);

                menuList.add(menu);
            }
        }

        return menuList;
    }

    public boolean existsByNamaMenu(String namaMenu) throws SQLException {

        String sql = "SELECT COUNT(*) " + "FROM menu " + "WHERE LOWER(nama_menu) = LOWER(?)";
        int jumlah;

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, namaMenu);
            try(ResultSet result = statement.executeQuery();) {
                result.next();
                jumlah = result.getInt(1);
            }
        }

        return jumlah > 0;
    }

    public boolean existsByNamaMenuAndIdMenuNot(String namaMenu, int idMenu) throws SQLException{
        String sql = "SELECT COUNT(*) " + "FROM menu " + "WHERE LOWER(nama_menu) = LOWER(?) " + "AND id_menu != ?";
        int jumlah;

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, namaMenu);
            statement.setInt(2, idMenu);
            try(ResultSet result = statement.executeQuery();) {
                result.next();
                jumlah = result.getInt(1);
            }
        }

        return jumlah > 0;
    }

    public void insert(Menu menu) throws SQLException {

        String sql = "INSERT INTO menu (nama_menu, harga, kategori, stok) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, menu.getNamaMenu());
            statement.setInt(2, menu.getHarga());
            statement.setString(3, menu.getKategori());
            statement.setInt(4, menu.getStok());

            statement.executeUpdate();
        }
    }

    public void update(Menu menu) throws SQLException {

        String sql = "UPDATE menu SET nama_menu = ?, harga = ?, kategori = ?, stok = ? WHERE id_menu = ?";

        try(Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, menu.getNamaMenu());
            statement.setInt(2, menu.getHarga());
            statement.setString(3, menu.getKategori());
            statement.setInt(4, menu.getStok());
            statement.setInt(5, menu.getIdMenu());

            statement.executeUpdate();
        }
    }

    public  void delete(int id_menu) throws SQLException {

        String sql = "DELETE FROM menu WHERE id_menu = ?";

        try(Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, id_menu);
            statement.executeUpdate();
        }
    }
}