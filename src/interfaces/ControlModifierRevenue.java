package interfaces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import PFA.Connect;
import PFA.Revenue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControlModifierRevenue {

    @FXML
    private TextField montant;

    @FXML
    private Label ref;

    @FXML
    private Button btnConf;

    @FXML
    private Button btnRet;

    @FXML
    private Label conf;

    @FXML
    private DatePicker dateRecu;

    @FXML
    private TextArea desc;

    @FXML
    void Conf(ActionEvent event) {
    	if(dateRecu.getValue().toString().isBlank() || montant.getText().isBlank() || desc.getText().isBlank())
    		conf.setText("Rempliez tous les champs");
    	else if(desc.getText().length()>60)
    		conf.setText("La description doit être de 70 lettres maximum");
    	else
    		try {
    			if(Float.parseFloat(montant.getText())<=0)
    				conf.setText("Le montant doit être un réel positive");
    			else
    				saveData();
    		}catch(Exception ex) {
    			conf.setText("Le montant doit être un réel positive");
    		}
    }

    void saveData() {
    	try {
    		Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	    	
			
    		String sql="Update Revenue Set MONTANT="+montant.getText()+",DATE_DE_RECU=to_date('"+dateRecu.getValue()+"','YYYY-MM-DD'), DESCRIPTION='"+desc.getText()+"' where REF="+ref.getText();
			st.executeQuery(sql);
			
			conf.setText("Mis à jour avec succés");
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeRevenue.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    public void initDon(Revenue r) {
    	ref.setText(Integer.toString(r.getRef()));
    	montant.setText(Float.toString(r.getMontant()));
    	dateRecu.setValue(r.getDate_de_recu().toLocalDate());
    	desc.setText(r.getDescription());
    	
    }
}
