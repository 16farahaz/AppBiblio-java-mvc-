package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControlLogIn implements Initializable{

    @FXML
    private TextField nomf;

    @FXML
    private Button btncon;

    @FXML
    private PasswordField mdpf;
    
    @FXML
    private ComboBox<String> rolef;
    
    @FXML 
    private Label conf;
    
    @FXML
    void confirmer(ActionEvent event) throws SQLException, IOException {
    	Users user=new Users(rolef.getSelectionModel().getSelectedItem(),nomf.getText(),mdpf.getText());
    	
    	if(test(user)) {
    		Connect ct=new Connect();
        	ResultSet rs = ct.conn.createStatement().executeQuery("Update Users set Active=1 where nom='"+user.getName()+"' and role='"+user.getRole()+"' and mdp='"+user.getMDP()+"'");
        	
    		
    		Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
        	Scene rcScene= new Scene(root);
        	
        	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
        	window.setScene(rcScene);
        	window.show();
        	
    	}else conf.setText("Cet user n'existe pas");
    	
    }
    
    boolean test(Users user) throws SQLException {
    	Connect ct=new Connect();
    	ResultSet rs = ct.conn.createStatement().executeQuery("select * from Users where nom='"+user.getName()+"' and role='"+user.getRole()+"' and mdp='"+user.getMDP()+"'");
    	if(rs.next()) {
    		return true;
    	}
    	return false;
    	
    }
    
    private void setActive() throws SQLException {
    	Connect ct=new Connect();
    	ResultSet rs = ct.conn.createStatement().executeQuery("Update Users set Active=0");
    }
    
    ObservableList<String> roleList = FXCollections.observableArrayList("Admin","Manager","Secrétaire","Financier");
    
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("JJJJ");
    	rolef.setItems(roleList);
    	try {
			setActive();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
