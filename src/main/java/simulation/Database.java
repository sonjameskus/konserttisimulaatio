package simulation;

import java.sql.*;

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

    public static void getServiceAnalysis() {

        if (conn == null) {
            System.out.println("Tietokantayhteys on null!");
            return;
        }

        try {

            ResultSet rs1 = conn.prepareStatement(
                    "SELECT AVG(security_time) FROM Customer").executeQuery();

            ResultSet rs2 = conn.prepareStatement(
                    "SELECT AVG(cloakroom_time) FROM Customer WHERE cloakroom=1").executeQuery();

            ResultSet rs3 = conn.prepareStatement(
                    "SELECT AVG(merch_time) FROM Customer WHERE merch=1").executeQuery();


            if (rs1.next())
                System.out.println("Turvatarkastuksen keskiarvo: " + rs1.getDouble(1) + " sekuntia.");

            if (rs2.next())
                System.out.println("Narikkakäynnin keskiarvo: " + rs2.getDouble(1) + " sekuntia.");

            if (rs3.next())
                System.out.println("Oheistuotteiden myynnin keskiarvo: " + rs3.getDouble(1) + " sekuntia.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDatabase() {

        if (conn == null) {
            System.out.println("Tietokantayhteys on null!");
            return;
        }

        try {

            PreparedStatement stmt1 = conn.prepareStatement(
                    "DELETE FROM Customer");

            stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement(
                    "ALTER TABLE Customer AUTO_INCREMENT = 1");

            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
