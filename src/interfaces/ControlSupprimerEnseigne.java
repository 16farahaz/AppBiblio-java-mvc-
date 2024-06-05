package interfaces;

import java.io.IOException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Enseigne;
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

public class ControlSupprimerEnseigne {

    @FXML
    private Label numS;

    @FXML
    private Label npe;

    @FXML
    private Button btnann;

    @FXML
    private Button btnsupp;
    
    int cin;
    
    int numSession;

    @FXML
    void annuler(ActionEvent event) throws IOException {
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/ListeEnseigne.fxml"));
    	Parent rcParent =loader.load();
    	
    	Scene rcScene= new Scene(rcParent);
    	//Accessing the destined controller
    	ControlListeEnseigne cont=loader.getController();
    	cont.initDonSession(numSession);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void supp(ActionEvent event) throws IOException {
    	try {
    		String sql = "delete  from Enseigne  where CIN = " +cin+" AND NUM_SESSION="+numS.getText().substring(0, numS.getText().length()-1) ;

    	       Connect ct=new Connect();
    	    	Statement st = ct.conn.createStatement(); 

    	   		st.executeQuery(sql);
    		System.out.println("Formation deleted ...");
    		
    		
    		retour(event);

    		} catch (Exception e) {
    			System.err.println(e.getMessage());
    		}
    }

    public void initDon(Enseigne e) {
    	npe.setText(e.getNom()+" "+e.getPrenom());
    	numS.setText(e.getNumSession()+"?");
    	numSession=e.getNumSession();
    	cin=e.getCin();
    }
    
    private void session(int numSession) {
    	this.numSession=numSession;
    }
    
    private void retour(ActionEvent event) throws IOException {
    	Enseigne e= new Enseigne(cin, numSession, cin, null, null);
        System.out.println(e.getNumSession());
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SuppEnseigneRetour.fxml"));
    	Parent rcParent =loader.load();

    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerEnseigne cont=loader.getController();
    	cont.session(numSession);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
}
