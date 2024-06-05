package interfaces;

import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Inscription;
import PFA.Revenue;
import PFA.client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControlListeRevenue implements Initializable{
	
	


	@FXML
    private TableView<Revenue> tableRev;
	
	@FXML
	private TableColumn<Revenue, String> colRef;
	
	@FXML
    private TableColumn<Revenue, String> colDate;

    @FXML
    private TableColumn<Revenue, String> colMon;
    
    @FXML
    private TableColumn<Revenue, String> colDesc;
    
    @FXML
    private DatePicker deb;
     
    @FXML
    private DatePicker fin; 
   
    @FXML
    private Label conf;
    
    @FXML
    private Button btnRP;
    
    @FXML
    private Button btncher;

    @FXML
    private Button btnAj;

    @FXML
    private Button btnMod;
    
    @FXML
    private Button btnSup;

    @FXML
    private Button btnRet;

   
    @FXML
    void chercher(ActionEvent event) {
    	initialize(null,null);
    }
    
    
    ObservableList<Revenue> listRev = FXCollections.observableArrayList();
 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		tableRev.getItems().clear();
		Connect ct=new Connect();
		LocalDate dateDeb=deb.getValue(),dateFin=fin.getValue();
		int test=0;
		
		if(dateDeb!=null)
			test++;
		if(dateFin!=null)
			test+=2;
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("Select * from REVENUE") ;
				
				while(rs.next()) { 
					Revenue r=new Revenue(rs.getInt("Ref"),rs.getFloat("MONTANT"),rs.getDate("DATE_DE_RECU"),rs.getString("DESCRIPTION"));
					LocalDate daterc=r.getDate_de_recu().toLocalDate();
				switch(test) {
				case 0:listRev.add(r);break;
				case 1:
					if(dateDeb.isBefore(daterc) || dateDeb.equals(daterc))
						listRev.add(r);
					break;
				case 2:
					if(dateFin.isAfter(daterc) || dateFin.equals(daterc))
						listRev.add(r);
					break;
				case 3:
					if((dateDeb.isBefore(daterc) && dateFin.isAfter(daterc) )|| dateDeb.equals(daterc) || dateFin.equals(daterc))
						listRev.add(r);
					break;
				}
			}
		 }catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		
		colRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
		colMon.setCellValueFactory(new PropertyValueFactory<>("montant"));
		colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("date_de_recu"));
	    
		
		
		tableRev.setItems(listRev);
		
	}

	@FXML
    void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutRevenue.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
	
	@FXML
    void Modifier(ActionEvent event) throws IOException {
    	try{
    		Revenue r=tableRev.getSelectionModel().getSelectedItem();
    		
    		 FXMLLoader loader=new FXMLLoader();
 	        loader.setLocation(getClass().getResource("/graphique/ModifierRevenue.fxml"));
 	        
 	        Parent rcParent =loader.load();
 	        
 	        Scene rcScene= new Scene(rcParent);
 	        
 	        //Accessing the destined controller
 	        ControlModifierRevenue cont=loader.getController();
 	        cont.initDon(r);
 	      
 	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
 	    	window.setScene(rcScene);
 	    	window.show();
 	    	}catch(Exception ex) {
    		conf.setText("Selectionnez un revenue");
    	}
    }
	
	@FXML
    void Supprimer(ActionEvent event) throws IOException {
		try{
    		Revenue r=tableRev.getSelectionModel().getSelectedItem();
    		
    		 FXMLLoader loader=new FXMLLoader();
 	        loader.setLocation(getClass().getResource("/graphique/SupprimerRevenue.fxml"));
 	        
 	        Parent rcParent =loader.load();
 	        
 	        Scene rcScene= new Scene(rcParent);
 	        
 	        //Accessing the destined controller
 	        ControlSupprimerRevenue cont=loader.getController();
 	        cont.initDon(r);
 	      
 	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
 	    	window.setScene(rcScene);
 	    	window.show();
 	    	}catch(Exception ex) {
    		conf.setText("Selectionnez un revenue");
    		System.out.println(ex.getMessage());
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

    @FXML
    void RapportFin(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AnneeDeRapport.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage stage=new Stage();
    	stage.setScene(rcScene);
    	stage.show();
    }
    
}
