package simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Connection conn;

    public static void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/simulaatio",
                    "konsertti",
                    "simulaatio"
            );
        } catch (SQLException e) {
            System.out.println("Yhdistäminen ei onnistu.");
            e.printStackTrace();
        }
    }

    public static void saveCustomer(Customer c) {
        if (conn == null) {
            System.out.println("Tietokantayhteys on null!");
            return;
        }

        String sql = "INSERT INTO Customer (ticket_type, quantity_of_clothes_etc, security_time, cloakroom, cloakroom_time, merch, merch_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getLippu());
            stmt.setInt(2, c.getTavaraMäärä());
            stmt.setInt(3, c.getSecurityTime());
            stmt.setBoolean(4, c.isKäyNarikassa());
            stmt.setInt(5, c.getCloakroomTime());
            stmt.setBoolean(6, c.isOstaako());
            stmt.setInt(7, c.getMerchTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}