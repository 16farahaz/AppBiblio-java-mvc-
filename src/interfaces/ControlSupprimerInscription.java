package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Inscription;
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

public class ControlSupprimerInscription {

    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;

    @FXML
    private Label numS;

    @FXML
    private Label CIN;

    @FXML
    private Label np;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeInscription.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) {
    	try {
    		String sql = "delete  from Inscription  where CIN = '" +CIN.getText()+"' and NUM_SESSION="+numS.getText().substring(0, numS.getText().length()-1) ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 
    	    	System.out.println(CIN.getText()+" / "+CIN.getText().substring(0, CIN.getText().length()-1));
    	   	
    	   		st.executeQuery(sql);
    	      	 

    	 // message(); 
    	
    		System.out.println("Inscription deleted ...");
    		retour(event);

    		} catch (Exception e) {
    		System.err.println("Problem in deleting ...");
    		}
    }
    
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppInscriptionRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDon(Inscription i) {
    	CIN.setText(Integer.toString(i.getCl().getCIN()));
    	np.setText(i.getCl().getNOM()+" "+i.getCl().getPRENOM());
    	numS.setText(i.getS().getNumSession()+"?");
    }

}
