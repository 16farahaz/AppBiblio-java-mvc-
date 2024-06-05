package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Formation;
import PFA.Salle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlSupprimerSalle {
    @FXML
    private Label num;

    @FXML
    private Label nom;

    @FXML
    private Button retour;

    @FXML
    private Button supp;
    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSalle.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) {
    	try {
    		String sql = "delete  from Salle  where Numéro = '" +num.getText().substring(0, num.getText().length()-1)+"'" ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 
    	    	System.out.println(num.getText()+" / "+num.getText().substring(0, num.getText().length()-1));
    	   	
    	   		st.executeQuery(sql);
    	      	 

    	 // message(); 
    	
    		System.out.println("salle deleted ...");
    		retour(event);

    		} catch (Exception e) {
    			System.err.println(e.getMessage());
    		}
    }
    
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppSalleRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDon(Salle S) {
    	num.setText(Integer.toString(S.getNuméro())+"?");
    	nom.setText(S.getNom());
    }
}
