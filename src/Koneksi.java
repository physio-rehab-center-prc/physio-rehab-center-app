package koneksi;
import java.sql.*;

public class Koneksi {
    public static Connection sambung() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_physiorehab", "root", "");
        } catch (Exception e) {
            return null;
        }
    }
}