package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formation;
import PFA.Salle;
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

public class ControlModifierSalle implements Initializable {
	  @FXML
	    private Button retour;

	    @FXML
	    private Button confi;

	    @FXML
	    private Label num;

	    @FXML
	    private TextField nom;

	    @FXML
	    private ComboBox<String> type;

	    @FXML
	    private ComboBox<String> etat;

	    @FXML
	    private Label conf;
	    @FXML
	    void Conf(ActionEvent event) {
	        if(nom.getText().isBlank()  || type.getSelectionModel().getSelectedIndex()==-1 ||  etat.getSelectionModel().getSelectedIndex()==-1)
	        	conf.setText("Remplissez tous les champs");
	        else 
	        	try{
	        		System.out.println(Integer.parseInt(num.getText()));
	        		updateDataFormation();
	        		
	        	}catch(Exception e) {
	        		System.out.println("num doit être un entier");
	        		conf.setText("num doit être un entier");
	        	}
	    }
		
	    
	    private void updateDataFormation() {
	    	try{
	    		
	    		String sql="UPDATE Salle SET NOM='"+nom.getText()+"',TYPE='"+type.getSelectionModel().getSelectedItem()+"',ETAT='"+etat.getSelectionModel().getSelectedItem()+"' WHERE NUMÉRO="+Math.abs(Integer.parseInt(num.getText()));
	    		Connect ct=new Connect();
	    		Statement st = ct.conn.createStatement(); 
	    		st.executeQuery(sql);
	    		conf.setText("Mise à jour avec avec succès"); 
		}catch(Exception  e) {
			if(e.getMessage().toLowerCase().contains("unique"))
	        	conf.setText("Une salle de même num exist deja");
	        else conf.setText("Error dans la connextion aven la base de données"); 
		}
	    }
	    ObservableList<String> typeList = FXCollections.observableArrayList("Normale","Informatique","Laboratoire","Langue","Technique");
	    ObservableList<String> etatList = FXCollections.observableArrayList("Bien","En panne ");
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
	    
	    public void initDon(Salle S) {	
	    	num.setText(Integer.toString(S.getNuméro()));
	    	nom.setText(S.getNom());
	    	   
	    	etat.setValue(S.getEtat());
	    	type.setValue(S.getType());
	    }


}
