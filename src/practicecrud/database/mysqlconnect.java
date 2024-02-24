package practicecrud.database;


import practicecrud.classDATA.users;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

        
/**
 *
 * @author JJ
 */
public class mysqlconnect {
    
    Connection connect = null;
    
    public static Connection ConnectDB() {
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/market" , "root" , "");
            JOptionPane.showMessageDialog(null, "ConnectionEstablished");
            return connect;
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
            return null;
            
        }
    }
    
    public static ObservableList<users> getDatausers() {
        
        Connection connect = ConnectDB();
        ObservableList<users> list = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps = connect.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new users(Integer.parseInt(rs.getString("id")), rs.getString("username"),
                    rs.getString("password"), rs.getString("email"), rs.getString("type")));
            }
            
        } catch (Exception e) {
            
        }
        return list;
    }
    
}
