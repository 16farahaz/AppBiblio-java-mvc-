package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Salle;
import PFA.Users;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControlListeUsers implements Initializable{

    @FXML
    private Button btnann;

    @FXML
    private Button btnajou;

    @FXML
    private Label conf;

    @FXML
    private Button btnCher;

    @FXML
    private TableView<Users> tableUsers;

    @FXML
    private TableColumn<Users, String> colid;

    @FXML
    private TableColumn<Users, String> colnom;

    @FXML
    private TableColumn<Users, String> colrole;

    @FXML
    private TableColumn<Users, String> colmdp;

    @FXML
    private Button btnsupp;

    @FXML
    private ComboBox<String> rolech;

    @FXML
    private Button btnmod;
    
    ObservableList<String> roleList = FXCollections.observableArrayList("Manager","Secrétaire","Financier");
    ObservableList<Users> oblistuser = FXCollections.observableArrayList();
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	rolech.setItems(roleList);
    	tableUsers.getItems().clear();
    	Connect ct=new Connect();
		String role=rolech.getSelectionModel().getSelectedItem();
		if(role==null)
			role="0";
		try {
			ResultSet rs = ct.conn.createStatement().executeQuery("select * from Users ") ;
			//where ID<>'A1'
			while(rs.next()) {
				Users user=new Users(rs.getInt("ID"),rs.getString("Role"),rs.getString("Nom"),rs.getString("MDP"));

				if(role=="0") {
					oblistuser.add(user);
				}else if (role.equals(user.getRole())) {
					oblistuser.add(user);
				}
			}
		}catch(Exception ex) {
			
		}
		
		colid.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colnom.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colrole.setCellValueFactory(new PropertyValueFactory<>("Role"));
		colmdp.setCellValueFactory(new PropertyValueFactory<>("MDP"));
		
		tableUsers.setItems(oblistuser);
    }
    
    @FXML
    void Ajouter(ActionEvent event) throws IOException { 
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutUser.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();

    }

    @FXML
    void Chercher(ActionEvent event) {
    	initialize(null,null);
    }

    @FXML
    void Modifier(ActionEvent event) throws IOException {
    	try {
    	Users user;
        user=(tableUsers.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
        //Change the scene
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphique/ModifierUser.fxml"));
        Parent rcParent =loader.load();
        
        Scene rcScene= new Scene(rcParent);
    	
        //Accessing the destined controller
        ControlModifierUser cont=loader.getController();
        cont.initDon(user);//Initializer les données de formateur
        
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		conf.setText("Selectionner un utilisateur");
    	}
    }

    @FXML
    void Supprimer(ActionEvent event) throws IOException  {
    	try {
    	Users user;
    	user=(tableUsers.getSelectionModel().getSelectedItem());//Avoir les données de formateur sélectionné
    	
    	if(user.getRole().equals("Admin")) {
    		try{
    			Connect ct=new Connect();
    			ResultSet rs=ct.conn.createStatement().executeQuery("select * from Users where Active= 1");
    			rs.next();
    			if(rs.getInt("ID")!=user.getID()) {
    				ResultSet rs1 = ct.conn.createStatement().executeQuery("select count (*) from Users where Role= 'Admin'");
    				rs1.next();
    				if(rs1.getInt(1)==1) {
    					conf.setText("Au moins un seul admin doit être dans la base");
    					return ;
    				}
    			}else {
    				conf.setText("Vous ne pouvez pas vous suppmrimer");
    				return;
    			}
    				
    		}catch(Exception ex) {
    			System.out.println(ex.getMessage());
    		}
    	}
    	
    	
    	//Change the scene
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("/graphique/SupprimerUser.fxml"));
    	Parent rcParent =loader.load();
    
    	Scene rcScene= new Scene(rcParent);
	
    	//Accessing the destined controller
    	ControlSupprimerUser cont=loader.getController();
    	cont.initDon(user);
    
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		conf.setText("Selectionner un utilisateur");
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

}
