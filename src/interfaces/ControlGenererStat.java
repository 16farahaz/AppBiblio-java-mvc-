package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formation;
import PFA.Stat;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControlGenererStat implements Initializable{

    @FXML
    private TextField idF;

    @FXML
    private Button btnCher;

    @FXML
    private TableView<Formation> tableFormation;

    @FXML
    private TableColumn<Formation, String> colNomFormation;

    @FXML
    private TableColumn<Formation, String> colDomaineFormation;

    @FXML
    private ComboBox<String> niveauCli;

    @FXML
    private TextField ageCli;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnGenerer;

    @FXML
    private DatePicker dateDeb;

    @FXML
    private DatePicker dateFin;
    
    @FXML
    private Label conf;
    
    
    private Formation form=null;
    
    ObservableList<String> niveauList = FXCollections.observableArrayList("Avec bac","Sans bac");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	niveauCli.setItems(niveauList);
    }
    
    
    ObservableList<Formation> formlist = FXCollections.observableArrayList();
    @FXML
    void ChercherFormation(ActionEvent event) {
    	String id=idF.getText(); 
    	tableFormation.getItems().clear();
    	formlist.clear();
		
    	Connect ct=new Connect();
		try { 
			ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formation where ID="+id) ;
				
			while(rs.next()) { 
				Formation mt=new Formation(rs.getInt("ID"), rs.getString("NOM"), rs.getString("TYPE"),rs.getString("DOMAINE"),rs.getInt("DUREE"),rs.getString("CONTENUE"),rs.getString("CERTIFICATION"),rs.getFloat("TARIF"),rs.getString("NIVEAU"));
				form=mt;
				formlist.add(mt);
				}
			} catch (SQLException e) {
				System.out.println("Vide");
			}
		
		colNomFormation.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		colDomaineFormation.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
		
		tableFormation.setItems(formlist);
}

    @FXML
    void Gener(ActionEvent event) throws IOException, SQLException {
    	if(Conf()) {
    		int test=0;
    		if(!(niveauCli.getSelectionModel().getSelectedIndex()==-1))
    			test++;
    		if(!ageCli.getText().isBlank())
    			test+=2;
    		
    		Date dateDebut=Date.valueOf(dateDeb.getValue());
    		Date dateF=Date.valueOf(dateFin.getValue());
    		Stat stat=null;
    		switch(test) {
    		case 1:stat=new Stat(form,niveauCli.getSelectionModel().getSelectedItem(),-1,dateDebut,dateF);break;
    		case 2:stat=new Stat(form,null,Integer.parseInt(ageCli.getText()),dateDebut,dateF);break;
    		case 3:stat=new Stat(form,niveauCli.getSelectionModel().getSelectedItem(),Integer.parseInt(ageCli.getText()),dateDebut,dateF);break;
    		}
    		
    		//Change the scene
	        FXMLLoader loader=new FXMLLoader();
	        loader.setLocation(getClass().getResource("/graphique/ResultatStat.fxml"));
	        Parent rcParent =loader.load();
	        
	        Scene rcScene= new Scene(rcParent);
	        
	        //Accessing the destined controller
	        ControlResultatStat cont=loader.getController();
	        cont.initTypeStat(stat);//Initializer les données de formateur

	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
    		
    	}
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    
    
    private boolean Conf() {
    	if(form==null || dateDeb.getValue().toString().isBlank() || dateFin.getValue().toString().isBlank()) {
    		conf.setText("Remplissez les champs nécessaires");
    		return false;
    	}else if(niveauCli.getSelectionModel().getSelectedIndex()==-1 && ageCli.getText().isBlank()) {
    		conf.setText("Donnez le niveau de client, l'âge de client ou les deux");
    		return false;
    	}else if(dateDeb.getValue().isAfter(dateFin.getValue())) {
    		conf.setText("Le date de debut doit être avant le date de fin");
    		return false;
    	}else if(!ageCli.getText().isBlank()) {
    		try {
    			if(Integer.parseInt(ageCli.getText())<0) {
        			conf.setText("L'âge doit être un entier positive");
        			return false;
    			}
    		}catch(Exception ex) {

    			conf.setText("L'âge doit être un entier positive");
    			return false;
    		}

    	}
		
    	return true;
    }
}
