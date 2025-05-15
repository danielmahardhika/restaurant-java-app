package restaurantjavaapp.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import restaurantjavaapp.model.Menu;
import restaurantjavaapp.model.MenuResto;

public class ControllerMenuResto {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    public String sql;

    DefaultTableModel dtm = new DefaultTableModel();

    public ControllerMenuResto() {
        Koneksi db = new Koneksi();
        db.config();
        this.con = db.con;
    }

    public DefaultTableModel buatTabel() {
        dtm.addColumn("ID MENU");
        dtm.addColumn("NAMA");
        dtm.addColumn("HARGA");
        dtm.addColumn("STATUS");
        dtm.addColumn("KATEGORI");
        return dtm;
    }

    public void tampilkanData() {
        try {
            //persipan tabel - clear area dtm
            dtm.getDataVector().removeAllElements();
            dtm.fireTableDataChanged();

            //Query
            this.sql = "SELECT * FROM tbmenuresto";

            //jalankan query
            // Menyiapkan PreparedStatement
            pst = con.prepareStatement(sql);

            // Menjalankan query
            rs = pst.executeQuery();

            //unboxing data ke dalam array/Object
            while (rs.next()) {
                Object[] obj = new Object[5];
                //namanya harus sama dengan yang di db
                obj[0] = rs.getString("id_menu");
                obj[1] = rs.getString("nama");
                obj[2] = rs.getString("harga");
                obj[3] = rs.getString("status");
                obj[4] = rs.getString("kategori");

                //masukkan objek ke dtm
                dtm.addRow(obj);
            }
        } catch (Exception e) {
            System.out.println("Gagal Query..." + e);
        }
    }

    public boolean tambahData(String a, int b, String c, String d) {
        MenuResto mnr = new MenuResto();
        mnr.setNama(a);
        mnr.setHarga(b);
        mnr.setStatus(c);
        mnr.setKategori(d);

        try {
            String sqlMenu = "INSERT INTO tbmenu (id_menu) VALUES (DEFAULT)";
            pst = con.prepareStatement(sqlMenu, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();

            ResultSet generatedKeys = pst.getGeneratedKeys();
            int idMenu = 0;
            if (generatedKeys.next()) {
                idMenu = generatedKeys.getInt(1);
            } else {
                throw new Exception("Gagal mendapatkan id_menu otomatis dari tbmenu.");
            }

            this.sql = "INSERT INTO tbmenuresto(id_menu, nama, harga, status, kategori) VALUES (?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idMenu);
            pst.setString(2, mnr.getNama());
            pst.setDouble(3, mnr.getHarga());
            pst.setString(4, mnr.getStatus());
            pst.setString(5, mnr.getKategori());
            pst.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Gagal menambahkan data: " + e.getMessage());
            return false;
        }
    }

    public boolean ubahData(int a, String b, int c, String d, String e) {
        MenuResto mr = new MenuResto();
        mr.setId_menuresto(a);
        mr.setNama(b);
        mr.setHarga(c);
        mr.setStatus(d);
        mr.setKategori(e);

        try {
            // Pastikan query memiliki spasi yang benar
            sql = "UPDATE tbmenuresto SET nama = ?, harga = ?, status = ?, kategori = ? WHERE id_menu = ?";
            // Menjalankan query
            pst = con.prepareStatement(sql);
            pst.setString(1, mr.getNama());
            pst.setDouble(2, mr.getHarga());
            pst.setString(3, mr.getStatus());
            pst.setString(4, mr.getKategori());
            pst.setInt(5, mr.getId_menuresto());

            pst.executeUpdate();
            return true;
        } catch (Exception err) {
            System.out.println("Gagal mengubah data: " + err.getMessage());
            return false;
        }
    }

    public boolean hapusData(int a) {
        Menu mn = new Menu();
        mn.setId(a);

        try {
            this.sql = "DELETE FROM tbmenu WHERE id_menu = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, mn.getId());
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
            return false;
        }
    }
}
