package interfaces;

import java.io.IOException; 
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class ControlTable implements Initializable { 

    @FXML
    private Button ajo;

    @FXML
    private Button retourr;
    @FXML
    private TableView<Formateur> table;


    @FXML
    private TableColumn<Formateur,String > colcin;

    @FXML
    private TableColumn<Formateur, String > colnom;

    @FXML
    private TableColumn<Formateur, String > colprenom;
    @FXML
    private TableColumn<Formateur, String > colsexe;

    @FXML
    private TableColumn<Formateur, String > coladrs;

    @FXML
    private TableColumn<Formateur, String > coldate;

    @FXML
    private TableColumn<Formateur, String > colmail;

    @FXML
    private TableColumn<Formateur, String > coldip;

    @FXML
    private TableColumn<Formateur, String > colnum;
    @FXML
    private TableColumn<Formateur, String > colexpertise;

    @FXML
    private TextField chernom;

    @FXML
    private Button chercher;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private Button view;
    
    @FXML
    private Label alert;
    
    
    public void Modifier(ActionEvent event) throws IOException {
    	try {
    	Formateur f;
        f=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
        //Change the scene
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphique/Modifier.fxml"));
        Parent rcParent =loader.load();
        
        Scene rcScene= new Scene(rcParent);
    	
        //Accessing the destined controller
        ControlModifier cont=loader.getController();
        cont.initDon(f);//Initializer les données de formateur
        
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Choisire un formateur");
    		alert.setText("Selectionner un formateur");
    	}
    }
    
    PreparedStatement preparedStatement;
    Connection connection;
    Connect  conn=new Connect();   
    @FXML 
	public void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/Ajout.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
	}

    @FXML 
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }  
    
    @FXML 
    public void Edit (ActionEvent event) throws IOException {
    	Modifier(event);
    }
    @FXML 
    public void chercher (ActionEvent event) throws IOException {
    	initialize(null, null);
	}
    @FXML 
    public void delete (ActionEvent event) throws IOException {
    	try {
    	Formateur f;
    	f=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerFormateur.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerFormateur cont=loader.getController();
    	cont.initDon(f);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Choisire un formateur");
    		alert.setText("Selectionner un formateur");
    	}
    }
	
    
    
    
	ObservableList<Formateur> oblist = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		table.getItems().clear();
		Connect ct=new Connect();
		 String nom=chernom.getText();
		 int test=0;
		 if(nom!="")test++;
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formateur");
				
				
				while(rs.next()) { 
					Date daten=rs.getDate("DATE_DE_NAISSANCE");
					Formateur mt=new Formateur(rs.getInt("CIN"), rs.getString("NOM"), rs.getString("PRENOM"),rs.getString("SEXE"),rs.getString("Adress"),daten,rs.getString("email"),rs.getString("DIPLÔME"),rs.getString("EXPERTISE"),rs.getInt("NUM_TELEPHONE"));
					switch(test) {
		    		case 0:
		    			 oblist.add(mt);break;
		    		case 1:
		    			if(mt.getNOM().toUpperCase().startsWith(nom.toUpperCase())) {
		    				oblist.add(mt);
		    			}break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		colcin.setCellValueFactory(new PropertyValueFactory<Formateur, String>("CIN"));
		colnom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("NOM"));
		colprenom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("PRENOM"));
		colsexe.setCellValueFactory(new PropertyValueFactory<Formateur, String>("SEXE"));
		coladrs.setCellValueFactory(new PropertyValueFactory<Formateur, String>("ADRESS"));
		coldip.setCellValueFactory(new PropertyValueFactory<Formateur, String>("DIPLÔME"));
		colexpertise.setCellValueFactory(new PropertyValueFactory<Formateur, String>("EXPERTISE"));

		coldate.setCellValueFactory(new PropertyValueFactory<Formateur, String>("DATE_DE_NAISSANCE"));
		colmail.setCellValueFactory(new PropertyValueFactory<Formateur, String>("EMAIL"));
		colnum.setCellValueFactory(new PropertyValueFactory<Formateur, String>("NUM_TELEPHONE"));
		
		table.setItems(oblist);
	}

}
