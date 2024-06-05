package interfaces;


	import java.io.IOException;
	import java.net.URL;      
	import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.sql.Date;
	import java.time.LocalDate;
	import java.time.Period;
	import java.util.ResourceBundle;
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
    import javafx.scene.control.TextField;
	import javafx.scene.control.ToggleGroup;
	import javafx.scene.layout.AnchorPane;
	import javafx.stage.Stage;
	import jdk.javadoc.doclet.Reporter;
	import javax.swing.JOptionPane;
	import com.sun.glass.events.MouseEvent;
	import com.sun.prism.paint.Color;	
	import PFA.Connect;
	import PFA.Formateur;


public class controlAjout implements Initializable { 
	  @FXML
	    private TextField nom;

	    @FXML
	    private TextField prenom;

	    @FXML
	    private TextField CIN;

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
	    private TextField expertise;

	    @FXML
	    private Button btnann;

	    @FXML
	    private Button btnconf;

	    @FXML
	    private RadioButton sexef ;
	    @FXML 
	    private RadioButton sexeh ; 
	    
	    private ToggleGroup sexegrp;
	    
	    @FXML
	    private Label conf;
	    
	    
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	  
	    	sexegrp= new ToggleGroup();
	    	sexef.setToggleGroup(sexegrp);
	    	sexeh.setToggleGroup(sexegrp);
	    
	    }
	    @FXML
	    private  void Conf (ActionEvent event) {
	        if(nom.getText().isBlank() || prenom.getText().isBlank() || CIN.getText().isBlank() || diplome.getText().isBlank() || expertise.getText().isBlank() || email.getText().isBlank() || numero_telephone.getText().isBlank() || sexegrp.getSelectedToggle().equals(null))
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
	        						saveData();
	        					else
	        						conf.setText("Formateur doit être 18+");
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
 //requette SQL 
	   
	    private void saveData() {

	     
	        try {
	        String sexe;
	        
	        if(sexegrp.getSelectedToggle().equals(sexef))sexe="Femme";
	        else sexe="Homme";
	     
	        Date daten=Date.valueOf(date_de_naissance.getValue());
	        Formateur f=new Formateur(Integer.parseInt(CIN.getText()),nom.getText(),prenom.getText(),adress.getText(),sexe,daten,email.getText(),diplome.getText(),expertise.getText(),Integer.parseInt(numero_telephone.getText()));
	        String sql ="INSERT INTO FORMATEUR (CIN,NOM,PRENOM,SEXE,DATE_DE_NAISSANCE,ADRESS,EMAIL,DIPLÔME,EXPERTISE,NUM_TELEPHONE)VALUES("+f.getCIN()+",'"+f.getNOM()+"','"+f.getPRENOM()+"','"+f.getSEXE()+"' , to_date('"+  f.getDATE_DE_NAISSANCE()+"','YYYY-MM-DD') ,'"+adress.getText()+"','"+email.getText()+"','"+diplome.getText()+"','"+expertise.getText()+"',"+numero_telephone.getText() +")" ;

	       Connect ct=new Connect();
	    	Statement st = ct.conn.createStatement(); 
	    	st.executeQuery(sql);
	    	
	   		conf.setText("Le formateur est ajouté"); 

	        } catch (Exception ex) {
	        	
	            
	            if(ex.getMessage().toLowerCase().contains("unique")) {
	            	conf.setText("Un formateur de meme CIN exist deja");
	            }else if(ex.getMessage().toLowerCase().contains("date")) {
	            	conf.setText("Date invalid");
	            } else
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
	   
	}

	    

