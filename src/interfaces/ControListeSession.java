package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Formation;
import PFA.Salle;
import PFA.Session;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControListeSession implements Initializable{

    @FXML
    private TextField numS;

    @FXML
    private Button btnch;

    @FXML
    private TableView<Session> tableSession;

    @FXML
    private TableColumn<Session, String> colNumSession;

    @FXML
    private TableColumn<Session, String> colNomSession;

    @FXML
    private TableColumn<Session, String> colID;

    @FXML
    private TableColumn<Session, String> colDateDeb;

    @FXML
    private TableColumn<Session, String> colDateFin;
    
    @FXML
    private TableColumn<Session, String> colNE;
    
    @FXML
    private TableColumn<Session, String> colHd;
    
    @FXML
    private TableColumn<Session, String> colHf;
    
    @FXML
    private TableColumn<Session, String> colnumS;
    
    @FXML
    private TableColumn<Session, String> colJour;
    
    @FXML
    private TableColumn<Session, String> colL;
    
    @FXML
    private TableColumn<Session, String> colM;
    
    @FXML
    private TableColumn<Session, String> colE;
    
    @FXML
    private TableColumn<Session, String> colJ;
    
    @FXML
    private TableColumn<Session, String> colV;
    
    @FXML
    private TableColumn<Session, String> colS;

    @FXML
    private Button btnret;

    @FXML
    private Button btnsupp;

    @FXML
    private Button btnmod;
    
    @FXML
    private Button btnve;

    @FXML
    private Button btnaj;
    
    @FXML
    private Label alert;
    
    @FXML
    void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    
    @FXML
    void Chercher(ActionEvent event) {
    	initialize(null, null);
    }

    @FXML
    void Modifier(ActionEvent event) throws IOException {
    	try {
    	Session s;
        s=(tableSession.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
        //Change the scene
        System.out.println("10");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphique/ModifierSession.fxml"));
        Parent rcParent =loader.load();
        System.out.println("11");
        Scene rcScene= new Scene(rcParent);
    	System.out.println("13");
        //Accessing the destined controller
        ControlModifierSession cont=loader.getController();
        cont.initDon(s);//Initializer les données de formateur
        System.out.println("17");
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une session");
    		alert.setText("Selectionner une session");
    		System.out.println(ex.getMessage());
    	}
    }

    @FXML
    void Supprimer(ActionEvent event) throws IOException {
    	try {
    	Session s;
    	s=(tableSession.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerSession.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerSession cont=loader.getController();
    	cont.initDon(s);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une session");
    		alert.setText("Selectionner une session");
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
    
    @FXML
    void voirEns(ActionEvent event) throws IOException {
    	try {
    	Session s;
    	s=(tableSession.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/ListeEnseigne.fxml"));
    	Parent rcParent =loader.load();
    	
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlListeEnseigne cont=loader.getController();
    	cont.initDonSession(s.getNumSession());
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println("Selectionner une session");
    		alert.setText("Selectionner une session");
    	}
    }
    
    
    ObservableList<Session> oblistSession = FXCollections.observableArrayList();
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableSession.getItems().clear();
		Connect ct=new Connect();
		 String numSession=numS.getText();
		 int test=0;
		 if(numSession!="")test++;
		 try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from sessions");
				while(rs.next()) {
					ResultSet rsform =ct.conn.createStatement().executeQuery("Select * from Formation where ID="+rs.getInt("ID"));
					ResultSet rssalle =ct.conn.createStatement().executeQuery("Select * from Salle where NUMÉRO="+rs.getInt("NUMÉRO"));
					
					Formation f=null;
					Salle s=null;
					if(rsform.next() && rssalle.next()) {
						f=new Formation(rsform.getInt("ID"),rsform.getString("NOM"),rsform.getString("TYPE"),rsform.getString("Domaine"),rsform.getInt("Duree"),rsform.getString("Contenue"),rsform.getString("Certification"),rsform.getFloat("Tarif"),rsform.getString("Niveau"));
						
						s=new Salle(rssalle.getInt("NUMÉRO"),rssalle.getString("NOM"),rssalle.getString("ETAT"),rssalle.getString("TYPE"),rssalle.getString("CAPACITE"));
					}
					
					Session mt=new Session(rs.getInt("NUM_SESSION"),rs.getString("NOM_SESSION"),f,rs.getDate("DATE_DE_DEBUT"),rs.getDate("DATE_DE_FIN"),rs.getString("HEURE_DEBUT"),rs.getString("HEURE_FIN"),rs.getString("JOUR"),s);
					ResultSet rs1 = ct.conn.createStatement().executeQuery("select count (CIN) from Enseigne where NUM_SESSION="+rs.getInt("NUM_SESSION"));
					if(rs1.next())
						mt.setNbrEnseigne(rs1.getInt(1));
					switch(test) {
		    		case 0:
						oblistSession.add(mt);break;
		    		case 1:
		    			String mtNS=Integer.toString(mt.getNumSession());
		    			if(mtNS.startsWith(numSession.toUpperCase())) {
		    				oblistSession.add(mt);
		    			}break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		colNumSession.setCellValueFactory(new PropertyValueFactory<Session, String>("numSession"));
		colNomSession.setCellValueFactory(new PropertyValueFactory<Session, String>("nomSession"));
		colDateDeb.setCellValueFactory(new PropertyValueFactory<Session, String>("dateDebut"));
		colDateFin.setCellValueFactory(new PropertyValueFactory<Session, String>("dateFin"));
		colNE.setCellValueFactory(new PropertyValueFactory<Session, String>("nbrEnseigne"));
		colHd.setCellValueFactory(new PropertyValueFactory<Session, String>("heurDeb"));
		colHf.setCellValueFactory(new PropertyValueFactory<Session, String>("heurFin"));
		colJour.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		
		colnumS.setCellValueFactory(new PropertyValueFactory<Session, String>("salle"));
		colnumS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getSalle().getNuméro()));
		    }
		});
		
		
		colID.setCellValueFactory(new PropertyValueFactory<Session, String>("form"));
		colID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getForm().getID()));
		    }
		});
		
		colL.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colL.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("L"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});
		
		colL.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colL.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("L"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});
		
		colM.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colM.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("M"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});
		
		colE.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colE.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("E"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});
		
		colJ.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colJ.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("J"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});

		colV.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colV.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("V"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});

		colS.setCellValueFactory(new PropertyValueFactory<Session, String>("jour"));
		colS.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Session, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Session, String> p) {
		    	if(p.getValue().getJour().contains("S"))
		    		return new SimpleStringProperty("X");
		    	return new SimpleStringProperty("");
		    }
		});
		
		tableSession.setItems(oblistSession);
	}

}
