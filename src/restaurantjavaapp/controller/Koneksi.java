package restaurantjavaapp.controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Kelas untuk menangani koneksi ke database
 */
public class Koneksi {

    public Connection con;

    // Method untuk konfigurasi koneksi ke database
    public void config() {
        try {
            String url = "jdbc:mysql://127.0.0.1/db_restoran";
            String user = "root";
            String pass = "";

            // Memuat driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi ke database berhasil.");
        } catch (Exception e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }
}
