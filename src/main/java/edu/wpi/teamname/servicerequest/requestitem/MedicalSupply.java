package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class MedicalSupply extends RequestItem{
    @Getter @Setter String type;
    @Getter @Setter int accessLvl;

    /**
     * Creates medical supply object
     *
     * @param supplyID
     * @param name
     * @param price
     * @param type
     * @param accessLvl
     */
    public MedicalSupply(int supplyID, String name, float price, String type, int accessLvl){
        super(supplyID, name, price);
        this.type = type;
        this.accessLvl = accessLvl;
    }

    public MedicalSupply(int id) throws SQLException{
        super(id);
        Connection connection = DataManager.DbConnection();
        String query = "SELECT * FROM \"Flowers\" WHERE \"flowerID\" = ?;";

        String name = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                super.setItemID(id);
                super.setName(rs.getString("Name"));
                setPrice(rs.getFloat("Price"));
                setType(rs.getString("Type"));
                setAccessLvl(rs.getInt("AccessLevel"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving medical supply data: " + e.getMessage());
            System.out.println(id);
        }
    }

    /**
     * A string of the item
     *
     * @return String in format [ <itemID>, <name>, <price>, <type>, <accessLvl>]
     */
    public String toString() {
        return "["
                + this.getItemID()
                + ", "
                + this.getName()
                + ", "
                + this.getPrice()
                + ", "
                + type
                + ", "
                + accessLvl
                +  "]";
    }
}
