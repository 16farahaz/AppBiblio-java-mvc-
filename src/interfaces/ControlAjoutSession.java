package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formation;
import PFA.Salle;
import PFA.Session;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import oracle.sql.DATE;

public class ControlAjoutSession implements Initializable{

    @FXML
    private TextField numSession;

    @FXML
    private Button btnann;

    @FXML
    private Button btnconf;

    @FXML
    private Label conf;

    @FXML
    private TextField nomSession;

    @FXML
    private TextField idFormation;

    @FXML
    private Button btnCher;

    @FXML
    private TableView<Formation> tableFormation;

    @FXML
    private TableColumn<Formation, String> colNom;

    @FXML
    private TableColumn<Formation, String> colDomaine;

    @FXML
    private TableColumn<Formation, String> colType;

    @FXML
    private TableColumn<Formation, String> colNiveau;

    @FXML
    private DatePicker dateDeb;

    @FXML
    private DatePicker dateFin;
    
    @FXML
    private Slider slHeurDeb;
    
    @FXML
    private Slider slHeurFin;
    
    @FXML
    private Label heurDeb;
    
    @FXML
    private Label heurFin;
    
    @FXML
    private TextField numSalle;

    @FXML
    private CheckBox lun;

    @FXML
    private CheckBox mar;

    @FXML
    private CheckBox mer;

    @FXML
    private CheckBox jeu;

    @FXML
    private CheckBox ven;

    @FXML
    private CheckBox sam;
    
    private Formation form;
    private Salle salle;
    
    ObservableList<Formation> formlist = FXCollections.observableArrayList();
    @FXML
    void Chercher(ActionEvent event) {
    	String id=idFormation.getText(); 
    	tableFormation.getItems().clear();
    	formlist.clear();
		
    	Connect ct=new Connect();
		try { 
			ResultSet rs = ct.conn.createStatement().executeQuery("select * from Formation where ID="+id) ;
				
			while(rs.next()) { 
				Formation mt=new Formation(rs.getInt("ID"), rs.getString("NOM"), rs.getString("TYPE"),rs.getString("DOMAINE"),rs.getInt("DUREE"),rs.getString("CONTENUE"),rs.getString("CERTIFICATION"),rs.getFloat("TARIF"),rs.getString("NIVEAU"));
				form=mt;
				formlist.add(mt);
				}
			} catch (SQLException e) {
				System.out.println("Vide");
			}
		
		colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
		colDomaine.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
		colNiveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
		
		tableFormation.setItems(formlist);
}
    

    private boolean testCheckBoxJour() {
    	if(!lun.isSelected() && !mer.isSelected() && !mar.isSelected() && !jeu.isSelected() && !ven.isSelected() && !sam.isSelected())
    		return true;
    	return false;
    }
    
    private String lesJours() {
    	String j="";
    	if(lun.isSelected())
    		j+="L";
    	if(mar.isSelected())
    		j+="M";
    	if(mer.isSelected())
    		j+="E";
    	if(jeu.isSelected())
    		j+="J";
    	if(ven.isSelected())
    		j+="V";
    	if(sam.isSelected())
    		j+="S";
    	return j;
    }
    
    int heur(String hr) {
    	return Integer.parseInt(hr.substring(0,2));
    }
    
    
    private boolean testDispo(String jr) {
    	try {
    		int ns=Integer.parseInt(numSalle.getText());
    		Connect ct= new Connect();
        	ResultSet rs=ct.conn.createStatement().executeQuery("Select * from Sessions where NUMÉRO="+ns);
        	while(rs.next()) {
        		
        		LocalDate rsdd =(rs.getDate("DATE_DE_DEBUT")).toLocalDate();
        		LocalDate rsdf =(rs.getDate("DATE_DE_FIN")).toLocalDate();
        		System.out.println(rsdf.isEqual(dateDeb.getValue()));
        		if(  (rsdd.isAfter(dateDeb.getValue()) && rsdd.isBefore(dateFin.getValue()) ) ||  ( dateDeb.getValue().isAfter(rsdd) && dateDeb.getValue().isBefore(rsdf) )  || ( dateFin.getValue().isAfter(rsdd) && dateFin.getValue().isBefore(rsdf)	) ||  ( rsdf.isAfter(dateDeb.getValue()) && rsdf.isBefore(dateFin.getValue())	)	|| rsdd.isEqual(dateDeb.getValue()) || rsdf.isEqual(dateDeb.getValue())	|| dateFin.getValue().isEqual(rsdd)		) {
        			System.out.println("1");
        			String rsjour=rs.getString("JOUR");
        			if((rsjour.contains("L") && jr.contains("L")) || (rsjour.contains("M") && jr.contains("M")) || rsjour.contains("E") && jr.contains("E") || rsjour.contains("J") && jr.contains("J") || rsjour.contains("V") && jr.contains("V") || rsjour.contains("S") && jr.contains("S")) {
        				System.out.println("2");
        				int rshd=heur(rs.getString("HEURE_DEBUT"));
        				int rshf=heur(rs.getString("HEURE_FIN"));
        				int hd=heur(heurDeb.getText());
        				int hf=heur(heurFin.getText());
        				if((rshd>=hd && rshd<=hf ) || (rshf>=hd && rshf<=hf) || (hd>=rshd && hd<=rshf) || (hf>=rshd && hf<=rshf) ) {
        					conf.setText("Il y'a une autre session dans la même salle, la même heure et le même jour");
        					return false;
        				}
        			}
       			}
        	}
        	
        	return true;
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    		conf.setText("Salle Invalid");
    	return false;
    	}
    	
    }
    
    
    @FXML
    void TestSalle(KeyEvent event){
    	try {
    		Connect ct= new Connect();
        	ResultSet rsTestSalle=ct.conn.createStatement().executeQuery("Select * from Salle where NUMÉRO="+numSalle.getText());
        	
        	if(rsTestSalle.next()) {
        		conf.setText("");
        		salle=new Salle(rsTestSalle.getInt("Numéro"),rsTestSalle.getString("Nom"),rsTestSalle.getString("Etat"),rsTestSalle.getString("Type"),rsTestSalle.getString("Capacite"));
        	}
    	}catch(Exception ex) {
        		conf.setText("Cette salle n'existe pas");
    	}
    }
    
    @FXML
    void Conf(ActionEvent event) {
    	if(numSession.getText().isBlank() || nomSession.getText().isBlank() || formlist.isEmpty() || testCheckBoxJour() || numSalle.getText().isBlank() || testCheckBoxJour())
    		conf.setText("Remplissez tous les champs");
    	else 
    		try {
    			 if(!(dateDeb.getValue().toString().isBlank() || dateFin.getValue().toString().isBlank()))
    				 try {
    					 if(Integer.parseInt(numSession.getText())>0 && Integer.parseInt(numSalle.getText())>0)
    					 try {
    						 if(Integer.parseInt(idFormation.getText())>0 && Integer.parseInt(numSalle.getText())>0)
    							 if(dateDeb.getValue().isBefore(dateFin.getValue()))
    			        					try{LocalDate now=LocalDate.now();
    			        					int period= Period.between(now, dateDeb.getValue()).getDays();
    			        					
    			        					if(dateDeb.getValue().isAfter(now) && period>=7)
    			        						if( heur(heurDeb.getText())<heur(heurFin.getText()) )
    			        							saveDataSession();
    			        						else
    			        							conf.setText("Heur de debut doit être avant heur de fin");
    			        					else
    			        						conf.setText("La session doit enregistrée au moins une semaine avant sa debut");
    			        					}catch(Exception ex) {
    			        						System.out.println(ex.getMessage());
    			        					}
    			        					
    							 else
    								 conf.setText("Le date de debut doit être avant le date de fin");
    					 }catch(Exception ex)
    					 {
    						 conf.setText("Choisir une formation");
    					 }
    					 else conf.setText("Le N° du session et le N° del la salle doivent être des entiers positive");
    				 }catch(Exception ex) {
    					 conf.setText("Le N° du session et le N° del la salle doivent être des entiers positive");
    				 }
    		}catch(Exception ex) {
    			conf.setText("Remplissez tous les champs");
    		}
    }
    
    
    @FXML
    void changeDateDeb(MouseEvent event) {
    		int d=(int)slHeurDeb.getValue();
    		switch(d) {
    		case 0:heurDeb.setText("08:00");break;
    		case 1:heurDeb.setText("09:00");break;
    		case 2:heurDeb.setText("10:00");break;
    		case 3:heurDeb.setText("11:00");break;
    		case 4:heurDeb.setText("12:00");break;
    		case 5:heurDeb.setText("13:00");break;
    		case 6:heurDeb.setText("14:00");break;
    		case 7:heurDeb.setText("15:00");break;
    		}
    }
    
    
    @FXML
    void changeDateFin(MouseEvent event) {
    		int d=(int)slHeurFin.getValue();
    		switch(d) {
    		case 0:heurFin.setText("09:00");break;
    		case 1:heurFin.setText("10:00");break;
    		case 2:heurFin.setText("11:00");break;
    		case 3:heurFin.setText("12:00");break;
    		case 4:heurFin.setText("13:00");break;
    		case 5:heurFin.setText("14:00");break;
    		case 6:heurFin.setText("15:00");break;
    		case 7:heurFin.setText("16:00");break;
    		}
    }
    
    
    private void saveDataSession() {
    	if(testDispo(lesJours())) {
    	try {
    		Date dDeb=Date.valueOf(dateDeb.getValue());
    		Date dFin=Date.valueOf(dateFin.getValue());
    		
    		Session s= new Session(Integer.parseInt(numSession.getText()), nomSession.getText(), form, dDeb, dFin, heurDeb.getText(), heurFin.getText(), lesJours(),salle);
    		
    		String sql="INSERT INTO SESSIONS (NUM_SESSION,NOM_SESSION,ID,DATE_DE_DEBUT,DATE_DE_FIN,HEURE_DEBUT,HEURE_FIN,JOUR,NUMÉRO) values("+s.getNumSession()+",'"+s.getNomSession()+"',"+s.getForm().getID()+",to_date('" + s.getDateDebut() + "','YYYY-MM-DD'), to_date('" + s.getDateFin() +"','YYYY-MM-DD'),'"+ s.getHeurDeb() +"','"+ s.getHeurFin() +"','"+s.getJour()+"',"+s.getSalle().getNuméro()+")";
    		Connect ct=new Connect();
    		
    		Statement st = ct.conn.createStatement(); 
       		st.executeQuery(sql);
       		
       		conf.setText("La session s'est ajoutée");

    	}catch(Exception ex) {
    		if(ex.getMessage().toLowerCase().contains("unique"))
            	conf.setText("Une session de meme N° exist deja");
            	else if(ex.getMessage().toLowerCase().contains("parent key"))
            		conf.setText("Cette salle n'existe pas");
            	else
            		conf.setText(ex.getMessage());
    	}}
    }
    
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    	
    }

}
