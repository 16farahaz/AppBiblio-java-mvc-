package interfaces;

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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formation;
import PFA.Salle;

public class ControlListeSalle implements Initializable {
		
		@FXML
    	private Label alert;
	
		@FXML
	    private TableView<Salle> table;

		@FXML
	    private TableColumn<Salle, String> colnum;

	    @FXML
	    private TableColumn<Salle, String> colnom;

	    @FXML
	    private TableColumn<Salle, String> coltype;

	    @FXML
	    private TableColumn<Salle, String> coletat;

	    @FXML
	    private TableColumn<Salle, String> colmat;

	    @FXML
	    private TextField chernom;

	    @FXML
	    private Button chercher;

	    @FXML
	    private Button retour;

	    @FXML
	    private Button supp;

	    @FXML
	    private Button modifier;

	    @FXML
	    private Button ajouter;

	    
	    @FXML
	    void Ajout(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjouterSalle.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }
	    @FXML
	    void chercher(ActionEvent event) {
	    	initialize(null, null);
	    }
	    @FXML
	    void Modifier(ActionEvent event) throws IOException {
	    	try {
	    	Salle S;
	        S=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
	        //Change the scene
	        FXMLLoader loader=new FXMLLoader();
	        loader.setLocation(getClass().getResource("/graphique/ModifierSalle.fxml"));
	        Parent rcParent =loader.load();
	        
	        Scene rcScene= new Scene(rcParent);
	    	
	        //Accessing the destined controller
	        ControlModifierSalle cont=loader.getController();
	        cont.initDon(S);//Initializer les données de formateur
	        
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    	}catch(Exception ex) {
	    		System.out.println("Selectionner une salle");
	    		alert.setText("Selectionner une salle");
	    	}
	    }
	    @FXML
	    void Supprimer(ActionEvent event) throws IOException {
	    	try {
	    	Salle S;
	    	S=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
	    	//Change the scene
	    	FXMLLoader loader=new FXMLLoader();
	    	loader.setLocation(getClass().getResource("/graphique/SupprimerSalle.fxml"));
	    	Parent rcParent =loader.load();
	    
	    	Scene rcScene= new Scene(rcParent);
		
	    	//Accessing the destined controller
	    	ControlSupprimerSalle cont=loader.getController();
	    	cont.initDon(S);
	    
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    	}catch(Exception ex) {
	    		System.out.println("Selectionner une salle");
	    		alert.setText("Selectionner une salle");
	    	}
	    }
	    @FXML
	    void exit(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }
	    
	    ObservableList<Salle> formlist = FXCollections.observableArrayList();
	    @Override 
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    	table.getItems().clear();
			Connect ct=new Connect();
			 String nomch=chernom.getText();
			 int test=0;
			 if(nomch!="")test++;
			 try { 
					ResultSet rs = ct.conn.createStatement().executeQuery("select * from SALLE") ;
					
					while(rs.next()) { 
						Salle s=new Salle(rs.getInt("numéro"), rs.getString("nom"), rs.getString("etat"),rs.getString("type"),rs.getString("capacite"));

						System.out.println(s.getNom());
						switch(test) {
			    	 	case 0:
			    			 formlist.add(s);break;
			    		case 1:
			    			if(s.getNom().toUpperCase().startsWith(nomch.toUpperCase())) {
			    				formlist.add(s);
			    			}break;
						}
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			colnum.setCellValueFactory(new PropertyValueFactory<>("numéro"));
			colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
			coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
			coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));
			colmat.setCellValueFactory(new PropertyValueFactory<>("capacite"));
			
			
			table.setItems(formlist);
	}
	    
	    
	    
	    
	}


