
package restaurantjavaapp.model;

public class Session {

    private static int lvlsekarang;
    private static String usSkrg;
    private static int idskrg;

    public static int getLvlsekarang() {
        return lvlsekarang;
    }

    public static String getUsSkrg() {
        return usSkrg;
    }

    public static void setUsSkrg(String usSkrg) {
        Session.usSkrg = usSkrg;
    }

    public static int getIdskrg() {
        return idskrg;
    }

    public static void setIdskrg(int idskrg) {
        Session.idskrg = idskrg;
    }

    public static void setLvlsekarang(int lvlsekarang) {
        Session.lvlsekarang = lvlsekarang;
    }

    public void clearSession() {
        this.lvlsekarang = 4;
    }
}
