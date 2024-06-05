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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControlAjoutSalle implements Initializable{
    @FXML
    private Button retour;

    @FXML
    private Button confi;

    @FXML
    private TextField num;

    @FXML
    private TextField nom;

    @FXML
    private ComboBox<String> type;

    @FXML
    private ComboBox<String> etat;
    @FXML
    private Label msg;
    @FXML
    private TextField nbr;


    ObservableList<String> typeList = FXCollections.observableArrayList("Normale","Informatique","Laboratoire","Langue","Technique");
    ObservableList<String> etatList = FXCollections.observableArrayList("Bien","En panne");
    @FXML
    void Conf(ActionEvent event) {
    	        
        System.out.println();
        if(num.getText().isBlank() || nom.getText().isBlank() || nbr.getText().isBlank() || type.getSelectionModel().getSelectedIndex()==-1 || etat.getSelectionModel().getSelectedIndex()==-1)
        	msg.setText("Remplissez tous les champs");
        	//System.out.println("enter all details");
        else 
        	try{
        		System.out.println(Integer.parseInt(num.getText()));
        	
        		try {
    				System.out.println(Integer.parseInt(nbr.getText()));
    				saveDataFormation();
    				
    			}catch(Exception e){
    				msg.setText("nombre d'élèves doit être un entier");
    			}
        				
        		
        	}catch(Exception e) {
        		System.out.println("numero doit être un entier");
        		msg.setText("numero doit être un entier");
        	}
        	
        }
        
    private void saveDataFormation() {
    	try {
    	
    		
    		String sql="INSERT INTO SALLE (Numéro,NOM,TYPE,Etat,CAPACITE) VALUES("+Math.abs(Integer.parseInt(num.getText()))+",'"+nom.getText()+"','"+type.getSelectionModel().getSelectedItem()+"','"+etat.getSelectionModel().getSelectedItem()+"',"+Math.abs(Integer.parseInt(nbr.getText()))+")";
    		
    		Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	   	
	   		st.executeQuery(sql);
	       msg.setText("La salle est ajoutée"); 
    	}catch(Exception  e) {
    		if(e.getMessage().toLowerCase().contains("unique"))
    			//System.out.println("salle d meme num existe deja");
            	msg.setText("Une salle de même num exist deja");
            else msg.setText("Error dans la connextion aven la base de données"); 
            	//System.out.println("probleme de connexion");
    	}
    	
    	
    }
    
    

    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSalle.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	type.setItems(typeList);
    	etat.setItems(etatList);
    }

}
