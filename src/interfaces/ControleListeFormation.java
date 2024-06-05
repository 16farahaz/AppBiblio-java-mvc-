package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Formation;
import PFA.client;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControleListeFormation implements Initializable {

    @FXML
    private TextField chernomcli;

    @FXML
    private Button chercher;

    @FXML
    private TableView<Formation> table;

    @FXML
    private TableColumn<Formation, String> id;

    @FXML
    private TableColumn<Formation, String> nom;

    @FXML
    private TableColumn<Formation, String> type;

    @FXML
    private TableColumn<Formation, String> domaine;

    @FXML
    private TableColumn<Formation, String> duree;

    @FXML
    private TableColumn<Formation, String> contenue;

    @FXML
    private TableColumn<Formation, String> certification;

    @FXML
    private TableColumn<Formation, String> niveau;

    @FXML
    private TableColumn<Formation, String> tarif;
    
    @FXML
    private Label alert;

    @FXML
    private Button btnRet;

    @FXML
    private Button btnSupp;

    @FXML
    private Button btnMod;

    @FXML
    private Button btnAj;

    @FXML
    void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutFormation.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void Modifier(ActionEvent event){
    	try {
    	Formation f;
        f=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
        //Change the scene
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphique/ModifierFormation.fxml"));
        Parent rcParent =loader.load();
        
        Scene rcScene= new Scene(rcParent);
    	
        //Accessing the destined controller
        ControlModifierFormation cont=loader.getController();
        cont.initDon(f);//Initializer les données de formateur
        
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une Formation");
    		alert.setText("Selectionner une Formation");
    	}
    }

    @FXML
    void Supprimer(ActionEvent event) {
    	try {
    	Formation f;
    	f=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerFormation.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerFormation cont=loader.getController();
    	cont.initDon(f);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une Formation");
    		alert.setText("Selectionner une Formation");
    	}
    }

    @FXML
    void chercher(ActionEvent event) {
    	initialize(null, null);
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    ObservableList<Formation> formlist = FXCollections.observableArrayList();
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	table.getItems().clear();
		Connect ct=new Connect();
		 String nomch=chernomcli.getText();
		 int test=0;
		 if(nomch!="")test++;
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formation") ;
				
				while(rs.next()) { 
					Formation mt=new Formation(rs.getInt("ID"), rs.getString("NOM"), rs.getString("TYPE"),rs.getString("DOMAINE"),rs.getInt("DUREE"),rs.getString("CONTENUE"),rs.getString("CERTIFICATION"),rs.getFloat("TARIF"),rs.getString("NIVEAU"));
					
					switch(test) { //recherche
		    		case 0:
		    			 formlist.add(mt);break;
		    		case 1:
		    			if(mt.getNom().toUpperCase().startsWith(nomch.toUpperCase())) {
		    				formlist.add(mt);
		    			}break;
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	//mil code values lil SQL 	
		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		type.setCellValueFactory(new PropertyValueFactory<>("Type"));
		domaine.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
		duree.setCellValueFactory(new PropertyValueFactory<>("Duree"));
		contenue.setCellValueFactory(new PropertyValueFactory<>("Contenue"));
		certification.setCellValueFactory(new PropertyValueFactory<>("Certification"));
		tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
		niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
		
		table.setItems(formlist);
}
}
