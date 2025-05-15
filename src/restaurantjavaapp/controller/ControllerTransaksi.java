
package restaurantjavaapp.controller;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import restaurantjavaapp.model.Transaksi;
import java.text.SimpleDateFormat;
import restaurantjavaapp.model.User;
import restaurantjavaapp.view.MenuTransaksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import restaurantjavaapp.model.Session;

public class ControllerTransaksi {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    public String sql;

    DefaultTableModel dtm = new DefaultTableModel();

    public ControllerTransaksi() {
        Koneksi db = new Koneksi();
        db.config();
        this.con = db.con;
    }

    public void refreshCombo(MenuTransaksi form) {
        try {
            this.sql = "SELECT * from tbmenuresto WHERE status='TERSEDIA'";

            pst = con.prepareStatement(sql);

            // Menjalankan query
            rs = pst.executeQuery();

            while (rs.next()) {
                form.getCmbIdMenu().addItem(rs.getString("id_menu") + ":" + rs.getString("nama") + ":" + rs.getString("harga") + ":" + rs.getString("kategori"));
            }

        } catch (Exception e) {
            System.out.println("Gagal Query..." + e);
        }
    }

    public DefaultTableModel buatTabel() {
        dtm.addColumn("ID TRANSAKSI");
        dtm.addColumn("ID USER");
        dtm.addColumn("NAMA USER");
        dtm.addColumn("NAMA PELANGGAN");
        dtm.addColumn("ID MENU");
        dtm.addColumn("TANGGAL");
        dtm.addColumn("NAMA MENU");
        dtm.addColumn("KATEGORI");
        dtm.addColumn("HARGA");
        dtm.addColumn("JUMLAH BELI");
        dtm.addColumn("TOTAL BAYAR");

        return dtm;
    }

    public void tampilkanData(MenuTransaksi form) {
        try {
            this.sql = "SELECT * from tbmenuresto WHERE status='TERSEDIA'";

            pst = con.prepareStatement(sql);

            // Menjalankan query
            rs = pst.executeQuery();

            while (rs.next()) {
                form.getCmbIdMenu().addItem(rs.getString("id_menu") + ":" + rs.getString("nama") + ":" + rs.getString("harga") + ":" + rs.getString("kategori"));
            }
            //persipan tabel - clear area dtm
            dtm.getDataVector().removeAllElements();
            dtm.fireTableDataChanged();

            //Query
            this.sql = "SELECT * FROM tbtransaksi";

            //jalankan query
            // Menyiapkan PreparedStatement
            pst = con.prepareStatement(sql);

            // Menjalankan query
            rs = pst.executeQuery();

            //unboxing data ke dalam array/Object
            while (rs.next()) {
                Object[] obj = new Object[11];
                //namanya harus sama dengan yang di db
                obj[0] = rs.getString("id_transaksi");
                obj[1] = rs.getString("id_user");
                obj[2] = rs.getString("nama_user");
                obj[3] = rs.getString("nama_pelanggan");
                obj[4] = rs.getString("id_menu");
                obj[5] = rs.getString("tanggal");
                obj[6] = rs.getString("nama_menu");
                obj[7] = rs.getString("kategori");
                obj[8] = rs.getString("harga");
                obj[9] = rs.getString("jumlah_beli");
                obj[10] = rs.getString("total_bayar");

                //masukkan objek ke dtm
                dtm.addRow(obj);
            }
        } catch (Exception e) {
            System.out.println("Gagal Query..." + e);
        }
    }

    public boolean tambahTransaksi(Transaksi transaksi) {
        try {
            int idusnow = Session.getIdskrg();
            String nmnow = Session.getUsSkrg();
            transaksi.setId_user(idusnow);
            transaksi.setNama_user(nmnow);
            sql = "INSERT INTO tbtransaksi ("
                    + "id_transaksi, id_user, nama_user, nama_pelanggan, "
                    + "id_menu, tanggal, nama_menu, kategori, "
                    + "harga, jumlah_beli, total_bayar) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Gunakan PreparedStatement untuk keamanan dan efisiensi
            pst = con.prepareStatement(sql);

            // Set parameter dengan data dari objek Transaksi
            pst.setInt(1, transaksi.getId_transaksi());
            pst.setInt(2, transaksi.getId_user());
            pst.setString(3, transaksi.getNama_user());
            pst.setString(4, transaksi.getNama_pelanggan());
            pst.setInt(5, transaksi.getId_menu());
            pst.setString(6, transaksi.getTanggal());
            pst.setString(7, transaksi.getNama_menu());
            pst.setString(8, transaksi.getKategori());
            pst.setInt(9, transaksi.getHarga());
            pst.setInt(10, transaksi.getJumlah_beli());
            pst.setInt(11, transaksi.getTotal_bayar());
            if (transaksi.getJumlah_beli() > 0) {
                pst.executeUpdate();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "INPUT TIDAK BOLEH MINUS");
                return false;
            }
        } catch (Exception e) {
            // Log error dengan lebih detail
            System.out.println(transaksi.getNama_user());
            System.out.println(transaksi.getId_user());
            System.err.println("Gagal menambahkan transaksi: " + e.getMessage());
            return false;
        }
    }

    // Contoh penggunaan di form atau method lain
    public void prosesTransaksi(MenuTransaksi form) {
        // Buat objek Transaksi dari form
        Transaksi transaksi = new Transaksi();

        // Set data dari input form
        transaksi.setNama_pelanggan(form.getTxtNamaPelanggan().getText());

        // Parse data dari combo box
        String selectedItem = (String) form.getCmbIdMenu().getSelectedItem();
        String[] itemParts = selectedItem.split(":");
        transaksi.setId_menu(Integer.parseInt(itemParts[0]));
        transaksi.setNama_menu(itemParts[1]);
        transaksi.setHarga(Integer.parseInt(itemParts[2]));
        transaksi.setKategori(itemParts[3]);

        // Set tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transaksi.setTanggal(sdf.format(form.getTxtTanggal().getDate()));

        // Set jumlah beli dan total bayar
        int jumlahBeli = Integer.parseInt(form.getTxtJmlBeli().getText());
        transaksi.setJumlah_beli(jumlahBeli);
        transaksi.setTotal_bayar(transaksi.getHarga() * jumlahBeli);
        transaksi.setId_user(form.getIdUser());
        transaksi.setNama_user(form.getNamaUser());

        // Tambahkan transaksi
        int option = JOptionPane.showConfirmDialog(form, "Tanggal: " + transaksi.getTanggal()
                + "\nNama Pelanggan: " + transaksi.getNama_pelanggan()
                + "\nPembelian: " + transaksi.getJumlah_beli() + " " + transaksi.getNama_menu()
                + "\nTotal Bayar: " + transaksi.getTotal_bayar() + "\n",
                "Tambah Transaksi?",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            if (tambahTransaksi(transaksi)) {
                form.refreshTable();
                form.getTxtTotalBayar().setText("" + transaksi.getTotal_bayar());
                JOptionPane.showMessageDialog(form, "Transaksi berhasil");
            } else {
                JOptionPane.showMessageDialog(form, "TERDAPAT KESALAHAN",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            form.refreshTable();
            JOptionPane.showMessageDialog(form, "Proses transaksi dibatalkan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean ubahTransaksi(Transaksi transaksi) {
        try {
            User us = new User();
            transaksi.setNama_user(us.getNama_user());
            transaksi.setId_user(us.getId_user());
            // Query untuk insert transaksi
            sql = "UPDATE tbtransaksi SET "
                    + "nama_pelanggan = ?, id_menu = ?, "
                    + "tanggal = ?, nama_menu = ?, kategori = ?, harga = ?, "
                    + "jumlah_beli = ?, total_bayar = ? "
                    + "WHERE id_transaksi = ?";

            // Gunakan PreparedStatement untuk keamanan dan efisiensi
            pst = con.prepareStatement(sql);

            // Set parameter dengan data dari objek Transaksi
            pst.setString(1, transaksi.getNama_pelanggan());
            pst.setInt(2, transaksi.getId_menu());
            pst.setString(3, transaksi.getTanggal());
            pst.setString(4, transaksi.getNama_menu());
            pst.setString(5, transaksi.getKategori());
            pst.setInt(6, transaksi.getHarga());
            pst.setInt(7, transaksi.getJumlah_beli());
            pst.setInt(8, transaksi.getTotal_bayar());
            pst.setInt(9, transaksi.getId_transaksi());
            if (transaksi.getJumlah_beli() > 0) {
                pst.executeUpdate();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "INPUT TIDAK BOLEH MINUS");
                return false;
            }
        } catch (Exception e) {
            // Log error dengan lebih detail
            System.err.println("Gagal menambahkan transaksi: " + e.getMessage());
            return false;
        }
    }

    public void prosesUbahTransaksi(MenuTransaksi form) {
        // Buat objek Transaksi dari form
        Transaksi transaksi = new Transaksi();

        // Set data dari input form
        transaksi.setId_transaksi(Integer.parseInt(form.getTxtIdTransaksi().getText()));
        transaksi.setNama_pelanggan(form.getTxtNamaPelanggan().getText());

        // Parse data dari combo box
        String selectedItem = (String) form.getCmbIdMenu().getSelectedItem();
        String[] itemParts = selectedItem.split(":");
        transaksi.setId_menu(Integer.parseInt(itemParts[0]));
        transaksi.setNama_menu(itemParts[1]);
        transaksi.setHarga(Integer.parseInt(itemParts[2]));
        transaksi.setKategori(itemParts[3]);

        // Set tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transaksi.setTanggal(sdf.format(form.getTxtTanggal().getDate()));

        // Set jumlah beli dan total bayar
        int jumlahBeli = Integer.parseInt(form.getTxtJmlBeli().getText());
        transaksi.setJumlah_beli(jumlahBeli);
        transaksi.setTotal_bayar(transaksi.getHarga() * jumlahBeli);
        //form.getTxtTotalBayar().setText("" + transaksi.getTotal_bayar());

        int option = JOptionPane.showConfirmDialog(form, "Tanggal: " + transaksi.getTanggal()
                + "\nNama Pelanggan: " + transaksi.getNama_pelanggan()
                + "\nPembelian: " + transaksi.getJumlah_beli() + " " + transaksi.getNama_menu()
                + "\nTotal Bayar: " + transaksi.getTotal_bayar() + "\n",
                "Ubah Transaksi?",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            if (ubahTransaksi(transaksi)) {
                form.refreshTable();
                //form.getTxtTotalBayar().setText("" + transaksi.getTotal_bayar());
                JOptionPane.showMessageDialog(form, "Transaksi berhasil diubah");
            } else {
                JOptionPane.showMessageDialog(form, "Terjadi kesalahan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            form.refreshTable();
            JOptionPane.showMessageDialog(form, "Proses ubah transaksi dibatalkan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean deleteTransaksi(Transaksi transaksi) {
        try {
            // query untuk delete transaksi
            sql = "DELETE FROM tbtransaksi WHERE id_transaksi = ?";

            //pke PreparedStatement untuk keamanan dan efisiensi
            pst = con.prepareStatement(sql);

            // Set parameter id_transaksi untuk query DELETE
            pst.setInt(1, transaksi.getId_transaksi());

            // eksekusi query
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Gagal menghapus transaksi: " + e.getMessage());
            return false;
        }

    }

    public void prosesDeleteTransaksi(MenuTransaksi form) {
        //constuktor
        Transaksi transaksi = new Transaksi();
        // ambil ID transaksi dari form
        transaksi.setId_transaksi(Integer.parseInt(form.getTxtIdTransaksi().getText()));
        transaksi.setNama_pelanggan(form.getTxtNamaPelanggan().getText());

        // parse data dari combo box -- unboxing
        String selectedItem = (String) form.getCmbIdMenu().getSelectedItem();
        String[] itemParts = selectedItem.split(":");
        transaksi.setId_menu(Integer.parseInt(itemParts[0]));
        transaksi.setNama_menu(itemParts[1]);
        transaksi.setHarga(Integer.parseInt(itemParts[2]));
        transaksi.setKategori(itemParts[3]);

        // set tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transaksi.setTanggal(sdf.format(form.getTxtTanggal().getDate()));

        // set jumlah beli dan total bayar
        int jumlahBeli = Integer.parseInt(form.getTxtJmlBeli().getText());
        transaksi.setJumlah_beli(jumlahBeli);
        transaksi.setTotal_bayar(transaksi.getHarga() * jumlahBeli);

        // tampilkan konfirmasi kepada pengguna sebelum menghapus
        int option = JOptionPane.showConfirmDialog(form, "Tanggal: " + transaksi.getTanggal()
                + "\nNama Pelanggan: " + transaksi.getNama_pelanggan()
                + "\nPembelian: " + transaksi.getJumlah_beli() + " " + transaksi.getNama_menu()
                + "\nTotal Bayar: " + transaksi.getTotal_bayar() + "\n",
                "Yakin untuk Delete Transaksi?",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Jika konfirmasi 'YES', panggil metode hapusTransaksi
            if (deleteTransaksi(transaksi)) {
                form.refreshTable();
                JOptionPane.showMessageDialog(form, "Transaksi berhasil dihapus");
            } else {
                JOptionPane.showMessageDialog(form, "Gagal menghapus data", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            form.refreshTable();
            JOptionPane.showMessageDialog(form, "Proses hapus transaksi dibatalkan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cetakLaporan() {
        try {
            Koneksi k = new Koneksi();
            File namafile = new File("src/restaurantjavaapp/laporan/LaporanResto.jasper");
            JasperPrint jp = JasperFillManager.fillReport(namafile.getPath(), null, this.con);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL MENCETAK LAPORAN" + e.getMessage());
        }

    }

}
