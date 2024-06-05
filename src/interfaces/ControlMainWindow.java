package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlMainWindow implements Initializable{

    @FXML
    private Button btngf;

    @FXML
    private Button btngc;
    
    @FXML
    private Button btngfr;
    
    @FXML
    private Button btngi;
    
    @FXML
    private Button btngs;
    
    @FXML
    private Button btngu;
    
    @FXML
    private Button btngsl;
    
    @FXML
    private Button btndec;
    
    @FXML
    private Button btngr;
    
    @FXML
    private Button btngd;
    
    @FXML
    private Button btngst;
    
    @FXML
    private Label nom;
    

    @FXML
    void GestionClient(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeClient.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void GestionFormateur(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/List.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionFormation(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeFormation.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void Logout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/LogIn.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionInscription(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeInscription.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionSession(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionSalle(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSalle.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionUsers(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeUsers.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GestionRevenue(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeRevenue.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void GestionDepense(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeDepense.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
    void GenerationStat(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/GenererStat.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {

    	try {
			initUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void grants(String role) {
    	switch (role) {
    		case "Manager":
    			btngf.setDisable(false);
    			btngfr.setDisable(true);
    			btngc.setDisable(true);
    			btngi.setDisable(true);
    			btngs.setDisable(true);
    			btngu.setDisable(true);
    			btngr.setDisable(true);
    			btngd.setDisable(true);
    			btngst.setDisable(true);break;
    		case "Secrétaire":
    			btngf.setDisable(true);
    			btngfr.setDisable(false);
    			btngc.setDisable(false);
    			btngi.setDisable(false);
    			btngs.setDisable(false);
    			btngu.setDisable(true);
    			btngr.setDisable(true);
    			btngd.setDisable(true);
    			btngst.setDisable(true);break;
    		case "Financier":
    			btngf.setDisable(true);
    			btngfr.setDisable(true);
    			btngc.setDisable(true);
    			btngi.setDisable(true);
    			btngs.setDisable(true);
    			btngu.setDisable(true);
    			btngsl.setDisable(true);
    			btngr.setDisable(false);
    			btngd.setDisable(false);
    			btngst.setDisable(true);break;
    		case "Admin":
    			btngf.setDisable(false);
    			btngfr.setDisable(false);
    			btngc.setDisable(false);
    			btngi.setDisable(false);
    			btngs.setDisable(false);
    			btngu.setDisable(false);
    			btngsl.setDisable(false);
    			btngr.setDisable(false);
    			btngd.setDisable(false);
    			btngst.setDisable(false);break;
    	}
    }
    
    private void initUser() throws SQLException {
    	Connect ct=new Connect();
    	ResultSet rs = ct.conn.createStatement().executeQuery("select * from Users where Active=1");
    	if(rs.next()) {
    		nom.setText(rs.getString("NOM"));
    		String role=rs.getString("ROLE");
    		
    		grants(role);
    	}
    }
}
