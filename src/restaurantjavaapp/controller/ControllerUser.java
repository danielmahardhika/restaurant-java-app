package restaurantjavaapp.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import restaurantjavaapp.model.User;

public class ControllerUser {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    public String sql;
    private User IdLevelSekarang;
    private User IdUserSekarang;
    private User NamaUserSekarang;

    DefaultTableModel dtm = new DefaultTableModel();
    

    public ControllerUser() {
        Koneksi db = new Koneksi();
        db.config();
        this.con = db.con;
    }

    public boolean cekLogin(String username, String password) {
        User us = new User();
        us.setUsername(username);
        us.setPassword(password);

        boolean status = false;
        try {
            this.sql = "SELECT * FROM tbuser WHERE username = ? AND password = ?";

            // Menyiapkan PreparedStatement
            pst = con.prepareStatement(sql);

            pst.setString(1, us.getUsername());
            pst.setString(2, us.getPassword());

            // Menjalankan query
            rs = pst.executeQuery();

            if (rs.next()) {
                status = true;
                us.setId_level(rs.getInt("id_level"));
                IdLevelSekarang = us;
                us.setId_user(rs.getInt("id_user"));
                IdUserSekarang = us;
                us.setNama_user(rs.getString("nama_user"));
                NamaUserSekarang = us;
            } else {
                status = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return status;
    }

    public User getIdLevelSekarang() {
        return IdLevelSekarang;
    }

    public User getIdUserSekarang() {
        return IdUserSekarang;
    }

    public User getNamaUserSekarang() {
        return NamaUserSekarang;
    }

    public DefaultTableModel buatTabel() {
        dtm.addColumn("ID USER");
        dtm.addColumn("USERNAME");
        dtm.addColumn("PASSWORD");
        dtm.addColumn("NAMA USER");
        dtm.addColumn("ID LEVEL");
        return dtm;
    }

    public void tampilkanData() {
        try {
            //persipan tabel - clear area dtm
            dtm.getDataVector().removeAllElements();
            dtm.fireTableDataChanged();

            //Query
            this.sql = "SELECT * FROM tbuser";

            //jalankan query
            // Menyiapkan PreparedStatement
            pst = con.prepareStatement(sql);

            // Menjalankan query
            rs = pst.executeQuery();

            //unboxing data ke dalam array/Object
            while (rs.next()) {
                Object[] obj = new Object[5];
                //namanya harus sama dengan yang di db
                obj[0] = rs.getString("id_user");
                obj[1] = rs.getString("username");
                obj[2] = rs.getString("password");
                obj[3] = rs.getString("nama_user");
                obj[4] = rs.getString("id_level");

                //masukkan objek ke dtm
                dtm.addRow(obj);
            }
        } catch (Exception e) {
            System.out.println("Gagal Query..." + e);
        }
    }

    public boolean tambahData(String username, String password, String nama, int idlevel) {
        //hubungkan dengan model
        User us = new User();
        us.setUsername(username);
        us.setPassword(password);
        us.setNama_user(nama);
        us.setId_level(idlevel);

        try {
            this.sql = "INSERT INTO tbuser (username, password, nama_user, id_level) VALUES (?, ?, ?, ?)";

            pst = con.prepareStatement(sql);

            pst.setString(1, us.getUsername());
            pst.setString(2, us.getPassword());
            pst.setString(3, us.getNama_user());
            pst.setInt(4, us.getId_level());

            // menjalankan query
            this.pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ubahData(int iduser, String username, String password, String nama, int idlevel) {
        User us = new User();
        us.setId_user(iduser);
        us.setUsername(username);
        us.setPassword(password);
        us.setNama_user(nama);
        us.setId_level(idlevel);

        try {
            this.sql = "UPDATE tbuser SET username = ?, password = ?, nama_user = ?, id_level = ? WHERE id_user = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, us.getUsername());
            pst.setString(2, us.getPassword());
            pst.setString(3, us.getNama_user());
            pst.setInt(4, us.getId_level()); // Assuming id_level is an integer
            pst.setInt(5, us.getId_user());

            // Menjalankan query
            this.pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean hapusData(int iduser) {
        User us = new User();
        us.setId_user(iduser);

        try {
            this.sql = "DELETE FROM tbuser WHERE id_user = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, us.getId_user());

            // Menjalankan query
            this.pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
