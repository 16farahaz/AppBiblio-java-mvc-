package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formation;
import PFA.Salle;
import PFA.Session;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControlAjoutInscription implements Initializable{

    @FXML
    private Button btnann;

    @FXML
    private Button btnconf;

    @FXML
    private Label conf;

    @FXML
    private TextField numS;

    @FXML
    private Button cherS;

    @FXML
    private TableView<Session> tableSession;

    @FXML
    private TableColumn<Session, String> nomSession;

    @FXML
    private TableColumn<Session, String> formation;

    @FXML
    private TextField cin;

    @FXML
    private Button cherC;

    @FXML
    private TableView<client> tableClient;

    @FXML
    private TableColumn<client, String> nomClient;

    @FXML
    private TableColumn<client, String> prenomClient;
       
    private ToggleGroup paiementgrp;
    
    @FXML
    private RadioButton oui;

    @FXML
    private RadioButton non;

    private String numSession="";
    private String cinCl="";
    
    
    //Client
    
    ObservableList<client> oblistcli = FXCollections.observableArrayList();
    @FXML
    void ChercherClient(ActionEvent event) {
    	try
    	{cinCl=cin.getText();
    	tableClient.getItems().clear();
    	Connect ct=new Connect();
		 int cinch=Integer.parseInt(cin.getText());
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from Client where CIN="+cinch) ;
				
				while(rs.next()) { 
					
					client mt=new client(rs.getInt("CIN"), rs.getString("NOM"), rs.getString("PRENOM"),rs.getString("SEXE"),rs.getString("Adress"),rs.getDate("date_de_naissance"),rs.getString("email"),rs.getString("NIVEAU"),rs.getInt("NUM_TELEPHONE"));
					oblistcli.add(mt);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 nomClient.setCellValueFactory(new PropertyValueFactory<>("NOM"));
		 prenomClient.setCellValueFactory(new PropertyValueFactory<>("PRENOM"));
		 tableClient.setItems(oblistcli);
    	}catch(Exception ex) {
    		conf.setText("Remplir le champs");
    	}
    }
    
    
    
    //Session
    
    Formation form=null;
    
    ObservableList<Session> oblistsession = FXCollections.observableArrayList();
    @FXML
    void ChercherSession(ActionEvent event) {
    	try {
    	numSession=numS.getText();
    	tableSession.getItems().clear();
    	Connect ct=new Connect();
		 int ns=Integer.parseInt(numS.getText());
		 try { 
			 ResultSet rs = ct.conn.createStatement().executeQuery("select * from Sessions where num_session="+ns) ;
				while(rs.next()) { 
					ResultSet rsform =ct.conn.createStatement().executeQuery("Select * from Formation where ID="+rs.getInt("ID"));
					ResultSet rssalle =ct.conn.createStatement().executeQuery("Select * from Salle where NUMÉRO="+rs.getInt("NUMÉRO"));
					
					Formation f=null;
					Salle s=null;
					if(rsform.next() && rssalle.next()) {
						f=new Formation(rsform.getInt("ID"),rsform.getString("NOM"),rsform.getString("TYPE"),rsform.getString("Domaine"),rsform.getInt("Duree"),rsform.getString("Contenue"),rsform.getString("Certification"),rsform.getFloat("Tarif"),rsform.getString("Niveau"));
						form=f;
						s=new Salle(rssalle.getInt("NUMÉRO"),rssalle.getString("NOM"),rssalle.getString("ETAT"),rssalle.getString("TYPE"),rssalle.getString("CAPACITE"));
					}
					
					Session mt=new Session(rs.getInt("NUM_SESSION"),rs.getString("NOM_SESSION"),f,rs.getDate("DATE_DE_DEBUT"),rs.getDate("DATE_DE_FIN"),rs.getString("HEURE_DEBUT"),rs.getString("HEURE_FIN"),rs.getString("JOUR"),s);
					oblistsession.add(mt);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		nomSession.setCellValueFactory(new PropertyValueFactory<>("nomSession"));
		formation.setCellValueFactory(new PropertyValueFactory<Session, String>("form"));
		formation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
			    @Override
			    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
			        return new SimpleStringProperty((p.getValue().getForm().getNom()));
			    }
			});	
		 tableSession.setItems(oblistsession);
    	}catch(Exception ex) {
    		conf.setText("Remplir le champs");
    	}
    }

    @FXML
    void Conf(ActionEvent event) throws SQLException {
    	if(numSession=="" || oblistsession.isEmpty())
    		conf.setText("Choisir une session");
    	else if(cinCl=="" || oblistcli.isEmpty())
    		conf.setText("Choisir un client");
    	else if(paiementgrp.getSelectedToggle()==null)
    		conf.setText("Choisir le stat de paiement");
    	else saveDataInscription(); 		
    }
    
    private void saveDataInscription() {
    	try {
    		String paiement;
    		if(paiementgrp.getSelectedToggle().equals(oui)) paiement="Oui";
    		else paiement="Non";
    		
    		Connect ct=new Connect();
    		Statement st = ct.conn.createStatement(); 
    		
    		String sql="Insert into Inscription (CIN,Num_Session,Paiement) values("+cinCl+","+numSession+",'"+paiement+"')";
    		if(paiement=="Oui") {
    			ResultSet rs=ct.conn.createStatement().executeQuery("Select count (*) from Revenue");
    			rs.next();
    			String sql2="Insert into Revenue (Ref,MONTANT,DATE_DE_RECU,CIN) values("+(rs.getInt(1)+1)+","+form.getTarif()+",to_date('"+LocalDate.now()+"','YYYY-MM-DD'),"+cinCl+")";
    			st.executeQuery(sql2);
    			}
    		
    		st.executeQuery(sql);
   		
    		conf.setText("L'inscreption est ajoutée");
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage()); 
            if(ex.getMessage().toLowerCase().contains("unique")) {
            	conf.setText("Ce/cette client(e) s'est déjà inscrit à cete session");
            }
    	}
    }
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeInscription.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	tableClient.getItems().clear();
    	tableSession.getItems().clear();
    	paiementgrp=new ToggleGroup();
    	oui.setToggleGroup(paiementgrp);
    	non.setToggleGroup(paiementgrp);
    }
}
