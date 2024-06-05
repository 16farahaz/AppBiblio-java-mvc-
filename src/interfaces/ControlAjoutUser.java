package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

import PFA.Connect;
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
import javafx.stage.Stage;

public class ControlAjoutUser implements Initializable{
	  @FXML
	    private Button btnann;

	    @FXML
	    private Button btnajou;

	    @FXML
	    private Label conf;

	    @FXML
	    private ComboBox<String> rolech;

	    @FXML
	    private TextField id;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField mdp;
	    ObservableList<String> roleList = FXCollections.observableArrayList("Manager","Secrétaire","Financier","Admin");

	    @FXML
	    void Conf(ActionEvent event) {
	    	        
	        System.out.println();
	        if(id.getText().isBlank() || nom.getText().isBlank() || mdp.getText().isBlank() || rolech.getSelectionModel().getSelectedIndex()==-1)
	        	conf.setText("Remplissez tous les champs");
	        	//System.out.println("enter all details");
	        else 
	        	if(mdp.getText().length()!=5)
	        		conf.setText("La mot de passe doit être de 5 caractères");
	        	else
	        	try{
	        		System.out.println(Integer.parseInt(id.getText()));
	        	
	        		saveDataFormation();
	        				
	        		
	        	}catch(Exception e) {
	        		System.out.println("ID doit être un entier");
	        		conf.setText("ID doit être un entier");
	        	}
	        	
	        }
	        
	    private void saveDataFormation() {
	    	try {
	    	
	    		String sql="INSERT INTO USERS (ID,NOM,Role,MDP) VALUES("+Math.abs(Integer.parseInt(id.getText()))+",'"+nom.getText()+"','"+rolech.getSelectionModel().getSelectedItem()+"','"+mdp.getText()+"')";
	    		

	    		Connect ct=new Connect();
		    	Statement st = ct.conn.createStatement(); 
		   	
		   		st.executeQuery(sql);
		       conf.setText("L'utilisateur est ajoutée"); 
	    	}catch(Exception  e) {
	    		if(e.getMessage().toLowerCase().contains("unique"))
	            	conf.setText("Un utilisateur de même id exist deja");
	            else conf.setText("Error dans la connextion aven la base de données"); 
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

	}

