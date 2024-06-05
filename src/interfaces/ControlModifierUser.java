package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Users;
import PFA.client;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControlModifierUser  implements Initializable {
	  @FXML
	    private Button btnann;

	    @FXML
	    private Button btnajou;

	    @FXML
	    private Label conf;

	    @FXML
	    private ComboBox<String> rolech;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField mdp;

	    @FXML
	    private Label id;
	    ObservableList<String> roleList = FXCollections.observableArrayList("Manager","Secrétaire","Financier","Admin");

	    
	    
	    @FXML
	    private  void Conf (ActionEvent event) {
	    	if(nom.getText().isBlank() || rolech.getSelectionModel().getSelectedIndex()==-1 || mdp.getText().isBlank() )
	        	conf.setText("Repmlir tous les champs");
        else if(mdp.getText().length()!=5)
    		conf.setText("La mot de passe doit être de 5 caractères");
        else
        		saveData();
	    }
        
        
        private void saveData() {

   	     System.out.println(id.getText());
	        try { 
	    	        String sql ="UPDATE USERS SET NOM='"+nom.getText()+"',ROLE='"+rolech.getSelectionModel().getSelectedItem()+"' ,MDP='"+mdp.getText()+"' WHERE ID="+id.getText();

	       Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	   	
	   		st.executeQuery(sql);
	   	

	        conf.setText("Updated successfully");

	        } catch (Exception ex) {
	            System.out.println(ex.getMessage()); 
	            conf.setText(ex.getMessage());
	        }
	    }
        
        
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeUsers.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	rolech.setItems(roleList);
    }
    
  //Remplacer les libs par les données de client
    public void initDon(Users user) {
    	
    	nom.setText(user.getName());
    	id.setText(Integer.toString(user.getID()));
    	mdp.setText(user.getMDP());
    	rolech.setValue(user.getRole());

    }




}
