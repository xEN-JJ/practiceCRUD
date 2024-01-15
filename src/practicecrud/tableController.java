package practicecrud;

import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;


/**
 *
 * @author JJ
 */
public class tableController implements Initializable {
    
    @FXML
    private TableView<users> table_users;
    
    @FXML
    private TableColumn<users, String> col_id;
    
    @FXML
    private TableColumn<users, String> col_email;

    @FXML
    private TableColumn<users, String> col_pw;

    @FXML
    private TableColumn<users, String> col_type;

    @FXML
    private TableColumn<users, String> col_un;
    
    @FXML
    private TextField txt_id;
    
    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_pw;

    @FXML
    private TextField txt_type;

    @FXML
    private TextField txt_un;
    
    ObservableList<users> listM;
    int index = -1;
    
    Connection connect = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    
    public void Add_users() {
        
        connect = (Connection) mysqlconnect.ConnectDB();
        String sql = "INSERT INTO `users` (username, password, email , type)values(?,?,?,?)";
        
        try {
            ps = connect.prepareStatement(sql);
                ps.setString(1 , txt_un.getText());
                ps.setString(2 , txt_pw.getText());
                ps.setString(3 , txt_email.getText());
                ps.setString(4 , "Active");
            ps.execute();
            
            JOptionPane.showMessageDialog(null,"Added New User");
            updateTable();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    @FXML
    void getSelected (MouseEvent event) {
        
        index = table_users.getSelectionModel().getSelectedIndex();
        
        if(index <= -1) {
            return;
        }
            txt_id.setText(col_id.getCellData(index).toString());
            txt_un.setText(col_un.getCellData(index).toString());
            txt_pw.setText(col_pw.getCellData(index).toString());
            txt_email.setText(col_email.getCellData(index).toString());
            txt_type.setText(col_type.getCellData(index).toString());
    }
    
    public void Edit() {
        
        try {
            connect = mysqlconnect.ConnectDB();
            
            String val1 = txt_id.getText();
            String val2 = txt_un.getText();
            String val3 = txt_pw.getText();
            String val4 = txt_email.getText();
            String val5 = txt_type.getText();
            
            String sql = "UPDATE users SET 'id = '" + val1 + "',username = '" + val2 + "',password = '"
                    + val3 + "',email = '" + val4 + "', type = '" + val5 + "' WHERE id = '" + val1 + "' ";
            
            ps = connect.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Successfully Updated");
            updateTable();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void updateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<users, String>("id"));
        col_un.setCellValueFactory(new PropertyValueFactory<users, String>("username"));
        col_pw.setCellValueFactory(new PropertyValueFactory<users, String>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<users, String>("email"));
        col_type.setCellValueFactory(new PropertyValueFactory<users, String>("type"));
        
        listM = mysqlconnect.getDatausers();
        table_users.setItems(listM);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
    }    
    
}
