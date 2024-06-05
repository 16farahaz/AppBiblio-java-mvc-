package interfaces;

import java.awt.TextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import PFA.Connect;
import PFA.Depense;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class ControlModifierDepense  implements Initializable {
	
	@FXML
    private Button retour;

    @FXML
    private Button confi;

    @FXML
    private TextField montantDep;

    @FXML
    private Label nomDep;

    @FXML
    private TextField typeDep;
     
    @FXML 
     private DatePicker dateDep ;
    
    @FXML
    private TextArea descDep;
  
    @FXML
	private Label conf;
   
    
    @FXML
   
    void Conf(ActionEvent event) {
    	if (nomDep.getText().isBlank() || montantDep.getText().isBlank()|| typeDep.getText().isBlank() || descDep.getText().isBlank()) 
    	conf.setText("Repmlir tous les champs");
    	else
    		try {
    			if (Float.parseFloat(montantDep.getText()) < 0)
					conf.setText("Le montant doit être un reel positive");
    			else
    				saveDataDepense();
    		}catch(Exception ex) {
    			conf.setText("Le montant doit être un reel positive");
    		}
    }
    //mise à jours 
    private void saveDataDepense() {
    	try {
    		LocalDate now= LocalDate.now();
    		Date dateDep=Date.valueOf(now);
			Depense d=new Depense(nomDep.getText(),typeDep.getText(),descDep.getText(),Integer.parseInt(montantDep.getText()),dateDep);
		
    		String sql="UPDATE DEPENSE SET DATEDEP=to_date('"+ d.getDatedep() +"','YYYY-MM-DD'),Description="+d.getDescription()+",Montant="+d.getMontant()+" where NOM='"+d.getNom()+"'";    		
    		Connect ct=new Connect();
    		
    		Statement st = ct.conn.createStatement(); 
       		st.executeQuery(sql);
       		
       		conf.setText("Mise à jour avec avec succès");

    	}catch(Exception ex) {
    		conf.setText(ex.getMessage());
    	}
    }
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeDepense.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	typeDep.setDisable(true);
    }
    
    
	public void initDonDep(Depense d) {
		nomDep.setText(d.getNom());
		montantDep.setText(Float.toString(d.getMontant()));
    	typeDep.setText(d.getDescription());
	}
}
