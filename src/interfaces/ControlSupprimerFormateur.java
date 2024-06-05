package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Formateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlSupprimerFormateur {

    @FXML
    private Label CIN;

    @FXML
    private Label np;
    
    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/List.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) {
    	try {
    		String sql = "delete  from Formateur  where CIN = '" +CIN.getText().substring(0, CIN.getText().length()-1)+"'" ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 
    	    	System.out.println(CIN.getText()+" / "+CIN.getText().substring(0, CIN.getText().length()-1));
    	   	
    	   		st.executeQuery(sql);
    	      	 

    	 // message(); 
    	
    		System.out.println("Client deleted ...");
    		retour(event);

    		} catch (Exception e) {
    			System.err.println(e.getMessage());
    		}
    }
    
    public void initDon(Formateur f) {
    	CIN.setText(Integer.toString(f.getCIN())+"?");
    	np.setText(f.getNOM()+" "+f.getPRENOM());
    }
    
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppFormateurRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
}
