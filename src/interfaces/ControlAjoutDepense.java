package interfaces;

import java.awt.TextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import oracle.sql.DATE;

public class ControlAjoutDepense implements Initializable {
	@FXML
	private Button retour;

	@FXML
	private Button confi;

	@FXML
	private TextField montantDep;

	@FXML
	private TextField nomDep;

	@FXML
    private DatePicker dateDep;
	
	@FXML
	private TextField typeDep;
	
	@FXML
	private TextArea descDep ;

	@FXML
	private Label conf;
	
	
	@FXML
	private void Conf(ActionEvent event) {
	
		if (nomDep.getText().isBlank() || montantDep.getText().isBlank()|| typeDep.getText().isBlank() || descDep.getText().isBlank()) 
			conf.setText("Remplir tous les champs");
		else
			try {
				if (Float.parseFloat(montantDep.getText()) < 0)
					conf.setText("Le montant doit être un reel positive");
				else
					saveDataDepense();
			} catch (Exception ex) {
				conf.setText("Repmlir tous les champs");
			}
	}

		
	void saveDataDepense() {
		try {
			LocalDate now= LocalDate.now();
			Date dateDep=Date.valueOf(now);
			Depense d=new Depense(nomDep.getText(),typeDep.getText(),descDep.getText(),Float.parseFloat(montantDep.getText()),dateDep);
		
			String sql="INSERT INTO Depense (Nom,Type,Datedep,Montant,Description) VALUES('"+d.getNom()+"','"+d.getType()+"',to_date('"+d.getDatedep()+"','YYYY-MM-DD'),"+d.getMontant()+d.getDescription()+")";
			

		    Connect ct=new Connect();
		    Statement st = ct.conn.createStatement(); 
		   	st.executeQuery(sql);
		   	
		   	conf.setText("Les données sont ajoutées");
		   		
		}catch(Exception ex) {
			System.out.println(ex.getMessage()); 
            if(ex.getMessage().toLowerCase().contains("unique")) {
            	conf.setText("Une depense de même nom exist deja");
            }
		}
	}
	
	@FXML
	void exit(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeDepense.fxml"));
		Scene rcScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rcScene);
		window.show();
	}
	
	@Override
	public void initialize(URL u, ResourceBundle rb) {
		
	}
}
