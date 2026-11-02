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
        
        try (Connection connection = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM menu";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

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
}
