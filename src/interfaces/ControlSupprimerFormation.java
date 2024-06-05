package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Formateur;
import PFA.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlSupprimerFormation {

    @FXML
    private Label id;

    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;

    @FXML
    private Label nom;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeFormation.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) {
    	try {
    		String sql = "delete  from Formation  where ID = '" +id.getText().substring(0, id.getText().length()-1)+"'" ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 
    	    	System.out.println(id.getText()+" / "+id.getText().substring(0, id.getText().length()-1));
    	   	
    	   		st.executeQuery(sql);
    	      	 

    	 // message(); 
    	
    		System.out.println("Formation deleted ...");
    		retour(event);

    		} catch (Exception e) {
    			System.err.println(e.getMessage());
    		}
    }
    
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppFormationRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDon(Formation f) {
    	id.setText(Integer.toString(f.getID())+"?");
    	nom.setText(f.getNom());
    }
}
