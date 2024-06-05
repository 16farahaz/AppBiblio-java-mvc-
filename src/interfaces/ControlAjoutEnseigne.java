package interfaces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import PFA.Connect;
import PFA.Enseigne;
import PFA.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class ControlAjoutEnseigne {

    @FXML
    private Button btnann;

    @FXML
    private Button btnajou;

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
    private TableColumn<Enseigne, String> coldip;
    
    @FXML
    private TextField salaire;

    @FXML
    private Label numS;

    @FXML
    void Ajouter(ActionEvent event) {
    	try {
    		Enseigne e=tableEnseigne.getSelectionModel().getSelectedItem();
    		try{
    			String s=salaire.getText();
    			e.setSalaire(Integer.parseInt(s));
    			e.setNumSession(Integer.parseInt(numS.getText()));
    			saveData(e);
    		}catch(Exception ex){
    			conf.setText("Saisire la salaire");
    		}
    		
    	}catch(Exception ex) {
    		conf.setText("Choisir un formateur");
    	}
    }
    
    private void saveData(Enseigne e) {
    	try {
    		
    		String sql="Insert into Enseigne (CIN,NUM_Session,Salaire) values ("+e.getCin()+","+e.getNumSession()+","+e.getSalaire()+")";
    		System.out.println(e.getNumSession());
    		Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	    	st.executeQuery(sql);
	    	
	   		conf.setText("Le formateur est ajouté"); 
    		
	   		ActionEvent event=null;
	   		Chercher(event);
    	}catch(Exception ex) {
    		conf.setText(ex.getMessage());
    		System.out.println(ex.getMessage());
    	}
    }
    
    ObservableList<Enseigne> oblistens = FXCollections.observableArrayList();
    ObservableList<Enseigne> oblisttest = FXCollections.observableArrayList();
    @FXML
    void Chercher(ActionEvent event) {
    	tableEnseigne.getItems().clear();
    	Connect ct=new Connect();
		 String nomE=nomch.getText();
		 int test=0;
		 if(nomE!="")test++;
		 
		 try { 	
			 ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formateur f Left JOIN Enseigne e on f.CIN=e.CIN");
			 //ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formateur f Left JOIN Enseigne e on f.CIN=e.CIN where (e.NUM_SESSION=null or e.NUM_SESSION <> "+Integer.parseInt(numS.getText())+")");
				while(rs.next()) { 
					Enseigne mt=null;
					mt=new Enseigne(rs.getInt("CIN"),rs.getInt("NUM_SESSION"),rs.getString("diplôme"),rs.getString("NOM"),rs.getString("PRENOM"));
					
					
					if(rs.getInt("NUM_SESSION")!=Integer.parseInt(numS.getText())) {
						switch(test) {
		    		case 0:
		    			oblistens.add(mt);
		    			break;
		    		case 1:
		    			if(mt.getNom().toUpperCase().startsWith(nomE.toUpperCase())) {
		    				oblistens.add(mt);
		    			}break;
					}
					}else {
						oblisttest.add(mt);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		colcin.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("cin"));
		colnom.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("nom"));
		colprenom.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("prenom"));
		coldip.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("diplome"));
		
		for(int i=0;i<oblisttest.size();i++) {
			Enseigne mt2=oblisttest.get(i);
			for(int j=0;j<oblistens.size();j++) {
				if(oblistens.get(j).getCin()==mt2.getCin())
					oblistens.remove(j);
			}
		}
		
		tableEnseigne.setItems(oblistens);
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/ListeEnseigne.fxml"));
    	Parent rcParent =loader.load();
    	
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlListeEnseigne cont=loader.getController();
    	cont.initDonSession(Integer.parseInt(numS.getText()));
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDonSession(String numS) {
    	this.numS.setText(numS);
    	ActionEvent e=null;
    	Chercher(e);
    }

}
