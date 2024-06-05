package interfaces;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ControlModifierClient implements Initializable{

	  @FXML
	    private TextField nomcli;

	    @FXML
	    private Label cincli;

	    @FXML
	    private TextField adrcli;

	    @FXML
	    private DatePicker datecli;

	    @FXML
	    private TextField prenomcli;

	    @FXML
	    private TextField emailcli;

	    @FXML
	    private TextField telcli;

	    @FXML
	    private ComboBox<String> niveau;

	    @FXML
	    private Label sexeclient;

	    @FXML
	    private Button annulcli;

	    
	    private ToggleGroup sexegrp;
	    @FXML
	    private RadioButton sexeh;

	    @FXML
	    private RadioButton sexef;

	    @FXML
	    private Label conf;
	    
	    ObservableList<String> niveauList = FXCollections.observableArrayList("Avec bac","Sans bac");
	    
	    @FXML
	    private  void Conf (ActionEvent event) {
	    	if(nomcli.getText().isBlank() || prenomcli.getText().isBlank() || niveau.getSelectionModel().getSelectedIndex()==-1 || emailcli.getText().isBlank() || telcli.getText().isBlank() || sexegrp.getSelectedToggle().equals(null))
	        	conf.setText("Repmlir tous les champs");
	        else 
	        	try {
	        		System.out.println(datecli.getValue().toString().isBlank());
	        		if(telcli.getText().length()!=8) {
	        			conf.setText("CIN et N° de telephone doivent être de 8 chiffres");
	        		}else
	        			try {
	        				if(Integer.parseInt(telcli.getText())<0)
	        					conf.setText("CIN et N° de telephone doivent être des entiers positive");
	        				else if( (emailcli.getText().contains("@"))!=true ||(emailcli.getText().substring(emailcli.getText().indexOf("@")).contains("."))!=true || emailcli.getText().lastIndexOf('.')==emailcli.getText().length()-1){
	        					conf.setText("Email invalide");
	        				}else 
	        					try{LocalDate now=LocalDate.now();
	        					int age= Period.between(datecli.getValue(), now).getYears();
	        					if(age>=18)
	        						saveData();
	        					else
	        						conf.setText("Client doit être 18+");
	        					}catch(Exception ex) {
	        						System.out.println(ex.getMessage());
	        					}
	        					
	        					
	        			}catch(Exception ex) {
	        				conf.setText("CIN doit être un entier positive");
	        			}
	        		
	        	}catch(Exception ex) {
	        		conf.setText("Repmlir tous les champs");
	        	}
	    }
        
        
        private void saveData() {

   	     
	        try { System.out.println("11111");
	        String sexe;
	        
	        if(sexegrp.getSelectedToggle().equals(sexef))sexe="Femme";
	       else sexe="Homme";
	     
	        Date daten=Date.valueOf(datecli.getValue());
	        String sql ="UPDATE CLIENT SET NOM='"+nomcli.getText()+"',PRENOM='"+prenomcli.getText()+"',SEXE='"+sexe+"',DATE_DE_NAISSANCE=to_date('" + daten + "','YYYY-MM-DD'),ADRESS='"+adrcli.getText()+"',NIVEAU='"+niveau.getSelectionModel().getSelectedItem()+"',EMAIL='"+emailcli.getText()+"',NUM_TELEPHONE="+telcli.getText() +"WHERE CIN="+cincli.getText();

	       Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	   	
	   		st.executeQuery(sql);
	   	

	        conf.setText("Updated successfully");

	        } catch (Exception ex) {
	            System.out.println(ex.getMessage()); 
	            conf.setText(ex.getMessage());
	        }
	    }
        
        
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeClient.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	sexegrp= new ToggleGroup();
    	sexef.setToggleGroup(sexegrp);
    	sexeh.setToggleGroup(sexegrp);
    	niveau.setItems(niveauList);
    }
    
  //Remplacer les libs par les données de client
    public void initDoncli(client cli) {
    	
    	nomcli.setText(cli.getNOM());
    	prenomcli.setText(cli.getPRENOM());
    	cincli.setText(Integer.toString(cli.getCIN()));
    	emailcli.setText(cli.getEMAIL());
    	adrcli.setText(cli.getADRESS());
    	telcli.setText(Integer.toString(cli.getNUM_TELEPHONE()));
    	datecli.setValue(cli.getDATE_DE_NAISSANCE().toLocalDate());
    	niveau.setValue(cli.getNIVEAU());
    	String sexe=cli.getSEXE();
    	if(sexe.toUpperCase().charAt(0)=='F')sexegrp.selectToggle(sexef);
    	else sexegrp.selectToggle(sexeh);
    }


}
