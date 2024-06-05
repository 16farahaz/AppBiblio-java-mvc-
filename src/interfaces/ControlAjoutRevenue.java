package interfaces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import PFA.Connect;
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

public class ControlAjoutRevenue {

    @FXML
    private TextField montant;

    @FXML
    private TextField ref;

    @FXML
    private Button btnAj;

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
    	if(ref.getText().isBlank() || dateRecu.getValue().toString().isBlank() || montant.getText().isBlank() || desc.getText().isBlank())
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
	    				
    		String sql="Insert into Revenue (Ref,MONTANT,DATE_DE_RECU,DESCRIPTION) values("+ref.getText()+","+montant.getText()+",to_date('"+dateRecu.getValue()+"','YYYY-MM-DD'),'"+desc.getText()+"')";
			st.executeQuery(sql);
			
			conf.setText("La revenue ajoutée");
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

}
