package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Formation;
import PFA.Inscription;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControlListeInscription implements Initializable {

    @FXML
    private TextField cincli;

    @FXML
    private Button btncher;

    @FXML
    private Label alert;
    
    @FXML
    private TableView<Inscription> table;

    @FXML
    private TableColumn<Inscription, String> colnum_sess;

    @FXML
    private TableColumn<Inscription, String> colcin;

    @FXML
    private TableColumn<Inscription, String> colnom;

    @FXML
    private TableColumn<Inscription, String> colprenom;

    @FXML
    private TableColumn<Inscription, String> colpai;

    @FXML
    private Button btnajo;

    @FXML
    private Button btnsupp;

    @FXML
    private Button btnret;
    
    @FXML
    private Button btnmod;
    
    @FXML
    void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutInscription.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void Modifier(ActionEvent event) {
    	
    	try {
    		Inscription in=table.getSelectionModel().getSelectedItem();
    		String paiement="";
    		
    		if(in.getPaiement().equals("Non")) {
    			paiement="Oui";
    			Formation form=in.getS().getForm();

        		System.out.println(in.getS().getNumSession());
    			Connect ct=new Connect();
        		Statement st = ct.conn.createStatement(); 
    			
        		ResultSet rs=ct.conn.createStatement().executeQuery("Select count (*) from Revenue");
        		rs.next();
        		String sql2="Insert into Revenue (Ref,MONTANT,DATE_DE_RECU,CIN) values("+(rs.getInt(1)+1)+","+form.getTarif()+",to_date('"+LocalDate.now()+"','YYYY-MM-DD'),"+in.getCl().getCIN()+")";
        		st.executeQuery(sql2);
        		String sql="Update Inscription set paiement='"+paiement+"'";
        		st.executeQuery(sql);
        		initialize(null,null);
    		}
    	}catch(Exception ex) {
    		System.out.println("Selectionner une inscription");
    		alert.setText("Selectionner une inscription");
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
    void Supprimer(ActionEvent event) throws IOException {
    	try {
    	Inscription i;
    	i=(table.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerInscription.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerInscription cont=loader.getController();
    	cont.initDon(i);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une inscription");
    		alert.setText("Selectionner une inscription");
    	}
    }

    @FXML
    void chercher(ActionEvent event) {
    	initialize(null, null);
    }
    
    ObservableList<Inscription> oblist = FXCollections.observableArrayList();
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	table.getItems().clear();
		Connect ct=new Connect();
		 String cin=cincli.getText();
		 int test=0;
		 if(cin!="")test++;
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from Inscription") ;
				
				while(rs.next()) { 
					ResultSet rscl =ct.conn.createStatement().executeQuery("select * from Client where CIN="+rs.getInt("CIN"));
					ResultSet rsse =ct.conn.createStatement().executeQuery("select * from Sessions where NUM_SESSION="+rs.getInt("NUM_SESSION"));
					
					client cl=null;
					Session s=null;
					Formation f=null;
					Inscription mt = null;
					if(rscl.next() && rsse.next()) {
						cl=new client (rscl.getInt("CIN"),rscl.getString("NOM"),rscl.getString("PRENOM"));
						ResultSet rsfr =ct.conn.createStatement().executeQuery("select * from Formation where ID="+rsse.getInt("ID"));
						rsfr.next();
						f=new Formation(rsfr.getInt("ID"),rsfr.getFloat("Tarif"));
						s=new Session(rsse.getInt("NUM_SESSION"),f);
						System.out.println(rsse.getInt("NUM_SESSION"));
					}
						
					mt=new Inscription(cl,s,rs.getString("PAIEMENT"));
					switch(test) {
		    		case 0:
						oblist.add(mt);break;
		    		case 1:
		    			String mtCIN=Integer.toString(mt.getCl().getCIN());
		    			if(mtCIN.startsWith(cin.toUpperCase())) {
		    				oblist.add(mt);
		    			}break;
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		colpai.setCellValueFactory(new PropertyValueFactory<>("paiement"));

		colcin.setCellValueFactory(new PropertyValueFactory<Inscription, String>("cl"));
		colcin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscription, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getCl().getCIN()));
		    }
		});
		
		colnum_sess.setCellValueFactory(new PropertyValueFactory<Inscription, String>("s"));
		colnum_sess.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscription, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getS().getNumSession()));
		    }
		});
		
		colnom.setCellValueFactory(new PropertyValueFactory<Inscription, String>("cl"));
		colnom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscription, String> p) {
		        return new SimpleStringProperty(p.getValue().getCl().getNOM());
		    }
		});
		
		colprenom.setCellValueFactory(new PropertyValueFactory<Inscription, String>("cl"));
		colprenom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Inscription, String> p) {
		        return new SimpleStringProperty(p.getValue().getCl().getPRENOM());
		    }
		});
		
		table.setItems(oblist);
}
    
}
