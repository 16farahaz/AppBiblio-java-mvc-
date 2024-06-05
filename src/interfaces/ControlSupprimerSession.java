package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Formation;
import PFA.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlSupprimerSession {

    @FXML
    private Label numS;

    @FXML
    private Label nomS;

    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) throws IOException {
    	try {
    		String sql = "delete  from Sessions  where NUM_SESSION = '" +numS.getText().substring(0, numS.getText().length()-1)+"'" ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement();
    	   		st.executeQuery(sql);
    		System.out.println("Session deleted ...");
    		retour(event);

    		} catch (Exception e) {
    		System.err.println(e.getMessage());
    		}
    }

    public void initDon(Session s) {
    	nomS.setText(s.getNomSession());
    	numS.setText(Integer.toString(s.getNumSession())+"?");
    }
    
    void retour(ActionEvent event) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/SuppSessionRetour.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
}
