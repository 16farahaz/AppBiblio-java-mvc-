package interfaces;

import java.io.IOException;  
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlistClient implements Initializable {
	 @FXML
	    private TextField chernomcli;

	    @FXML
	    private Button cherchercli;

	    @FXML
	    private TableView<client> tablecli;

	    @FXML
	    private TableColumn<client, String> colcincli;

	    @FXML
	    private TableColumn<client, String> colnomcli;

	    @FXML
	    private TableColumn<client, String> colprenomcli;

	    @FXML
	    private TableColumn<client, String> colsexecli;

	    @FXML
	    private TableColumn<client, String> coladrscli;

	    @FXML
	    private TableColumn<client, String> coldatecli;

	    @FXML
	    private TableColumn<client, String> colmailcli;

	    @FXML
	    private TableColumn<client, String> coldipcli;

	    @FXML
	    private TableColumn<client, String> colnumcli;

	    @FXML
	    private Button retourrcli;

	    @FXML
	    private Button deletecli;

	    @FXML
	    private Button editcli;

	    @FXML
	    private Button ajocli;
	   
	    @FXML
	    private Label alert;
	    
	    @FXML 
		public void Ajout(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutClient.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
		}
	    
	    
	    public void Modifier(ActionEvent event) {
	    	try {
	    	client cli;
	        cli=(tablecli.getSelectionModel().getSelectedItem());//Avoir les données de client sélectionné

	        //Change the scene
	        FXMLLoader loader=new FXMLLoader();
	        loader.setLocation(getClass().getResource("/graphique/modifierClient.fxml"));
	        
	        Parent rcParent =loader.load();
	        
	        Scene rcScene= new Scene(rcParent);
	        
	        //Accessing the destined controller
	        	
	        ControlModifierClient cont=loader.getController();
	        	cont.initDoncli(cli); //pour afficher les attibus avant la modification 
	      
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    	}catch(Exception ex) {
	    		System.out.println("Selectionner un client");
	    		alert.setText("Selectionner un client");
	    	}
	    }
	    
	    @FXML 
	    public void delete (ActionEvent event) throws IOException {
	    	try {
	    	client c;
	        c=(tablecli.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
	        //Change the scene
	        FXMLLoader loader=new FXMLLoader();
	        loader.setLocation(getClass().getResource("/graphique/SupprimerClient.fxml"));
	        Parent rcParent =loader.load();
	        
	        Scene rcScene= new Scene(rcParent);
	    	
	        //Accessing the destined controller
	        ControlSupprimerClient cont=loader.getController();
	        cont.initDon(c);
	        
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    	}catch(Exception ex) {
	    		System.out.println("Selectionner un client");
	    		alert.setText("Selectionner un client");
	    	}
	    }
	    
	    @FXML 
	    public void cherchercli (ActionEvent event) throws IOException {
	    	initialize(null, null);
	    	}
	    
	    @FXML 
	    public void Edit (ActionEvent event) throws IOException {
	    	Modifier(event);
	    }
	    
	       
	    @FXML 
	    void exit(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }  
	    
	    ObservableList<client> oblistcli = FXCollections.observableArrayList();
	    @Override 
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    	tablecli.getItems().clear();
			Connect ct=new Connect();
			String nom=chernomcli.getText();
			int test=0;
			if(nom!="")test++;
			try { 
					ResultSet rs = ct.conn.createStatement().executeQuery("select * from Client") ;
					
					while(rs.next()) { 
						client mt=new client(rs.getInt("CIN"), rs.getString("NOM"), rs.getString("PRENOM"),rs.getString("SEXE"),rs.getString("Adress"),rs.getDate("date_de_naissance"),rs.getString("email"),rs.getString("NIVEAU"),rs.getInt("NUM_TELEPHONE"));
						
						switch(test) {//pour la recherche 
			    		case 0:
			    			 oblistcli.add(mt);
			    			 break;
			    		case 1:
			    			if(mt.getNOM().toUpperCase().startsWith(nom.toUpperCase())) {
			    				oblistcli.add(mt);
			    			}break;
						}
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			colcincli.setCellValueFactory(new PropertyValueFactory<>("CIN"));
			colnomcli.setCellValueFactory(new PropertyValueFactory<>("NOM"));
			colprenomcli.setCellValueFactory(new PropertyValueFactory<>("PRENOM"));
			colsexecli.setCellValueFactory(new PropertyValueFactory<>("SEXE"));
			coladrscli.setCellValueFactory(new PropertyValueFactory<>("ADRESS"));
			coldipcli.setCellValueFactory(new PropertyValueFactory<>("NIVEAU"));
			coldatecli.setCellValueFactory(new PropertyValueFactory<>("DATE_DE_NAISSANCE"));
			colmailcli.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
			colnumcli.setCellValueFactory(new PropertyValueFactory<>("NUM_TELEPHONE"));
			
			tablecli.setItems(oblistcli);
	}
}
