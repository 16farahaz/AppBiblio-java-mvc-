package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Inscription;
import PFA.Revenue;
import PFA.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlSupprimerRevenue {

    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;

    @FXML
    private Label ref;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeRevenue.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) {
    	try {
    		String sql = "delete  from Revenue  where ref = " +ref.getText().substring(0, ref.getText().length()-1) ;
    		System.out.println(ref.getText().substring(0, ref.getText().length()-1));
    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 
    	    	
    	   		st.executeQuery(sql);
    	      	 

    	 // message(); 
    	
    		System.out.println("Inscription deleted ...");
    		retour(event);

    		} catch (Exception e) {
    		System.err.println("Problem in deleting ...");
    		System.out.println(e.getMessage());
    		}
    }
    
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppRevenueRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDon(Revenue r) {
    	ref.setText(Integer.toString(r.getRef())+"?");
    }

}
