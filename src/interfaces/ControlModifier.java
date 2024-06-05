package interfaces;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ControlModifier implements Initializable{

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private Label CIN;

    @FXML
    private TextField email;

    @FXML
    private TextField adress;

    @FXML
    private TextField numero_telephone;

    @FXML
    private DatePicker date_de_naissance;

    @FXML
    private TextField diplome;

    @FXML
    private Button btnann;

    @FXML
    private Button btnconf;
    
    @FXML
    private Label conf;
    
    @FXML
    private TextField expertise;

    private ToggleGroup sexegrp;

    @FXML
    private RadioButton sexeh;

    @FXML
    private RadioButton sexef;

    @FXML
    private  void Conf (ActionEvent event) throws IOException {
    	if(nom.getText().isBlank() || prenom.getText().isBlank()  || diplome.getText().isBlank() || expertise.getText().isBlank() || email.getText().isBlank() || numero_telephone.getText().isBlank() || sexegrp.getSelectedToggle().equals(null))
        	conf.setText("Repmlir tous les champs");
        else 
        	try {
        		System.out.println(date_de_naissance.getValue().toString().isBlank());
        		if( numero_telephone.getText().length()!=8) {
        			conf.setText("CIN et N° de telephone doivent être de 8 chiffres");
        		}else
        			try {
        				if(Integer.parseInt(CIN.getText())<0 || Integer.parseInt(numero_telephone.getText())<0)
        					conf.setText("N° de telephone doivent être de entier positive");
        				else if( (email.getText().contains("@"))!=true ||(email.getText().substring(email.getText().indexOf("@")).contains("."))!=true || email.getText().lastIndexOf('.')==email.getText().length()-1){
        					conf.setText("Email invalide");
        				}else 
        					try{LocalDate now=LocalDate.now();
        					int age= Period.between(date_de_naissance.getValue(), now).getYears();
        					if(age>=18)
        						saveData();
        					else
        						conf.setText("Formateur doit être 18+");
        					}catch(Exception ex) {
        						System.out.println(ex.getMessage());
        					}
        					
        					
        			}catch(Exception ex) {
        				conf.setText("N° de telephone doit être un entier positive");
        			}
        		
        	}catch(Exception ex) {
        		conf.setText("Repmlir tous les champs");
        	}
    }
        
        
        private void saveData() {

   	     
	        try { 
	        String sexe;
	        
	        if(sexegrp.getSelectedToggle().equals(sexef))sexe="Femme";
	        else sexe="Homme";
	        
	        Date daten=Date.valueOf(date_de_naissance.getValue());
	        Formateur f=new Formateur(Integer.parseInt(CIN.getText()),nom.getText(),prenom.getText(),adress.getText(),sexe,daten,email.getText(),diplome.getText(),expertise.getText(),Integer.parseInt(numero_telephone.getText()));
	      
	        String sql ="UPDATE FORMATEUR SET NOM='"+nom.getText()+"',PRENOM='"+prenom.getText()+"',SEXE='"+sexe+"',DATE_DE_NAISSANCE=to_date('"+  f.getDATE_DE_NAISSANCE()+"','YYYY-MM-DD'),ADRESS='"+adress.getText()+"',EMAIL='"+email.getText()+"',DIPLÔME='"+diplome.getText()+"',EXPERTISE='"+expertise.getText()+"',NUM_TELEPHONE="+numero_telephone.getText() +"WHERE CIN="+CIN.getText();
	        
	        

	        
	        Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement();
	   	
	   		st.executeQuery(sql);
	   		conf.setText("Mise à jour avec avec succès");

	        } catch (Exception ex) {
	            System.out.println(ex.getMessage()); 
	           // label1.setText(Color.RED);
	            conf.setText(ex.getMessage());
	        }
	    }
        
        
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/List.fxml")) ;
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
    }
    
  //Remplacer les libs par les données de formateur
    public void initDon(Formateur f) {
    	
    	nom.setText(f.getNOM());
    	prenom.setText(f.getPRENOM());
    	CIN.setText(Integer.toString(f.getCIN()));
    	email.setText(f.getEMAIL());
    	adress.setText(f.getADRESS());
    	numero_telephone.setText(Integer.toString(f.getNUM_TELEPHONE()));
    	date_de_naissance.setValue(f.getDATE_DE_NAISSANCE().toLocalDate());
    	diplome.setText(f.getDIPLÔME());
    	expertise.setText(f.getEXPERTISE());
    	String sexe=f.getSEXE();
    	if(sexe.toUpperCase().charAt(0)=='F')sexegrp.selectToggle(sexef);
    	else sexegrp.selectToggle(sexeh);
    }
}