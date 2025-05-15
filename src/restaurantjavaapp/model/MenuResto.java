
package restaurantjavaapp.model;

public class MenuResto extends Menu {

    private int id_menuresto;
    private String nama;
    private int harga;
    private String status;
    private String kategori;

//    public MenuResto(int idmenuresto, int idmenu, String nama, int harga, String status, String kategori){
//        super(idmenu);
//        this.id_menuresto = idmenuresto;
//        this.nama = nama;
//        this.harga = harga;
//        this.status = status;
//        this.kategori = kategori;
//    }
    public int getId_menuresto() {
        return id_menuresto;
    }

    public void setId_menuresto(int id_menuresto) {
        this.id_menuresto = id_menuresto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

}
