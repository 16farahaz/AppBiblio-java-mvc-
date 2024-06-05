package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Enseigne;
import PFA.Session;
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

public class ControlListeEnseigne implements Initializable{

    @FXML
    private Button btnann;

    @FXML
    private Button btnajou;
    
    @FXML
    private Label alert;
    
    @FXML
    private Label numS;

    @FXML
    private Label conf;

    @FXML
    private TextField nomch;

    @FXML
    private Button btnCher;

    @FXML
    private TableView<Enseigne> tableEnseigne;

    @FXML
    private TableColumn<Enseigne, String> colcin;

    @FXML
    private TableColumn<Enseigne, String> colnom;

    @FXML
    private TableColumn<Enseigne, String> colprenom;

    @FXML
    private TableColumn<Enseigne, String> colsal;

    @FXML
    private Button btnsupp;

    @FXML
    void Ajouter(ActionEvent event) throws IOException {
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/AjoutEnseigne.fxml"));
    	Parent rcParent =loader.load();
    	
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlAjoutEnseigne cont=loader.getController();
    	cont.initDonSession(numS.getText());
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    public void Chercher(ActionEvent event) {
    	tableEnseigne.getItems().clear();
    	Connect ct=new Connect();
		 String nomE=nomch.getText();
		 int test=0;
		 if(nomE!="")test++;
		
		 try { 
			 System.out.println(nomE);	
			 ResultSet rs = ct.conn.createStatement().executeQuery("select * from Enseigne where NUM_SESSION="+Integer.parseInt(numS.getText()));
				
				while(rs.next()) { 
					ResultSet rs1 = ct.conn.createStatement().executeQuery("select nom,prenom from Formateur where CIN="+rs.getInt("CIN"));
					Enseigne mt=null;
					if(rs1.next())
						mt=new Enseigne(rs.getInt("CIN"),rs.getInt("NUM_SESSION"),rs.getInt("SALAIRE"),rs1.getString("NOM"),rs1.getString("PRENOM"));
					switch(test) {
		    		case 0:
		    			oblistens.add(mt);break;
		    		case 1:
		    			if(mt.getNom().toUpperCase().startsWith(nomE.toUpperCase())) {
		    				oblistens.add(mt);
		    			}break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		colcin.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("cin"));
		colnom.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("nom"));
		colprenom.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("prenom"));
		colsal.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("salaire"));
				
		tableEnseigne.setItems(oblistens);
    }

    @FXML
    void Suprimer(ActionEvent event) throws IOException {
    	try {
    	Enseigne e;
    	e=(tableEnseigne.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerEnseigne.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerEnseigne cont=loader.getController();
    	cont.initDon(e);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner un enseigne");
    		alert.setText("Selectionner une enseigne");
    	}
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    ObservableList<Enseigne> oblistens = FXCollections.observableArrayList();
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    
    }
    public void initDonSession(int s) {
    	numS.setText(Integer.toString(s));
    	ActionEvent e=null;
    	Chercher(e);
    }

}
