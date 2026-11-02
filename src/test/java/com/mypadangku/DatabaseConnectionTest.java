package com.mypadangku;

import com.mypadangku.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() throws SQLException {

        try (Connection connection = DatabaseConnection.getConnection()) {

            assertNotNull(connection);

            System.out.println("Koneksi ke MySQL berhasil!");
        }
    }
}
