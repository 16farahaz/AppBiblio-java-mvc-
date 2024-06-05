package interfaces;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import PFA.Connect;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControlAjoutClient implements Initializable{

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField adress;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> niveau;

    @FXML
    private TextField numero_telephone;

    @FXML
    private DatePicker date_de_naissance;

    private ToggleGroup sexegrp;
    
    @FXML
    private RadioButton sexeh;

    @FXML
    private RadioButton sexef;

    @FXML
    private Button btnann;

    @FXML
    private Button btnconf;

    @FXML
    private Label conf;

    @FXML
    private TextField CIN;
    
    ObservableList<String> niveauList = FXCollections.observableArrayList("Avec bac","Sans bac");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    	sexegrp= new ToggleGroup();
    	sexef.setToggleGroup(sexegrp);
    	sexeh.setToggleGroup(sexegrp);
    	niveau.setItems(niveauList);
    	
    }
    
    @FXML
    private  void Conf (ActionEvent event) {
    	if(nom.getText().isBlank() || prenom.getText().isBlank() || CIN.getText().isBlank() || niveau.getSelectionModel().getSelectedIndex()==-1 || email.getText().isBlank() || numero_telephone.getText().isBlank() || sexegrp.getSelectedToggle().equals(null))
        	conf.setText("Repmlir tous les champs");
        else 
        	try {
        		System.out.println(date_de_naissance.getValue().toString().isBlank());
        		if(CIN.getText().length()!=8 || numero_telephone.getText().length()!=8) {
        			conf.setText("CIN et N° de telephone doivent être de 8 chiffres");
        		}else
        			try {
        				if(Integer.parseInt(CIN.getText())<0 || Integer.parseInt(numero_telephone.getText())<0)
        					conf.setText("CIN et N° de telephone doivent être des entiers positive");
        				else if( (email.getText().contains("@"))!=true ||(email.getText().substring(email.getText().indexOf("@")).contains("."))!=true || email.getText().lastIndexOf('.')==email.getText().length()-1){
        					conf.setText("Email invalide");
        				}else 
        					try{LocalDate now=LocalDate.now();
        					int age= Period.between(date_de_naissance.getValue(), now).getYears();
        					if(age>=18)
        						saveDataClient();
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
    
    private void saveDataClient() {

        
        try { 
        String sexe;
        
        if(sexegrp.getSelectedToggle().equals(sexef))sexe="Femme";
        else sexe="Homme";
        
        
        Date daten=Date.valueOf(date_de_naissance.getValue());
        String sql ="INSERT INTO Client (CIN,NOM,PRENOM,SEXE,DATE_DE_NAISSANCE,ADRESS,EMAIL,NIVEAU,NUM_TELEPHONE)VALUES("+CIN.getText()+",'"+nom.getText()+"','"+prenom.getText()+"','"+sexe+"' ,to_date('" + daten + "','YYYY-MM-DD'),'"+adress.getText()+"','"+email.getText()+"','"+niveau.getSelectionModel().getSelectedItem()+"',"+numero_telephone.getText() +")" ;
     
       Connect ct=new Connect();
    	Statement st = ct.conn.createStatement(); 
   		st.executeQuery(sql);
   		
   		conf.setText("Le/La client(e) est ajouté(e)");

        } catch (Exception ex) {
        	System.out.println(ex.getMessage()); 
            if(ex.getMessage().toLowerCase().contains("unique")) {
            	conf.setText("Un client de meme CIN exist deja");
            }else if(ex.getMessage().toLowerCase().contains("date")) {
            	conf.setText("Date invalid");
            }
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

}
