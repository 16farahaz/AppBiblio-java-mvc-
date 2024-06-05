package interfaces;

import java.io.IOException;
import java.sql.Statement;
import java.time.LocalDate;

import PFA.Connect;
import PFA.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlModifierSession {

    @FXML
    private Label numS;

    @FXML
    private TextField nomS;

    @FXML
    private Button btnann;

    @FXML
    private Button btnconf;

    @FXML
    private Label conf;

    @FXML
    private DatePicker dateDeb;

    @FXML
    private DatePicker dateFin;

    @FXML
    private Label nomF;
    
    @FXML
    private Slider slHeurDeb;
    
    @FXML
    private Slider slHeurFin;
    
    @FXML
    private Label heurDeb;
    
    @FXML
    private Label heurFin;

    @FXML
    void Conf(ActionEvent event) {
    	if(nomS.getText().isBlank())
    		conf.setText("Donner le nom de la session");
    	else 
    		try {
    			 if(!(dateDeb.getValue().toString().isBlank() || dateFin.getValue().toString().isBlank()))
    							 if(dateDeb.getValue().isBefore(dateFin.getValue()))
    								 saveDataSession();
    							 else
    								 conf.setText("Le date de debut doit être avant le date de fin");
    		}catch(Exception ex) {
    			conf.setText("Remplissez tous les champs");
    		}
    }

    
    
    
    private void saveDataSession() {
    	try {
    		String sql="UPDATE SESSIONS SET NOM_SESSION='"+nomS.getText()+"',DATE_DE_DEBUT='"+ dateDeb.getValue().toString() +"',DATE_DE_FIN='"+dateFin.getValue().toString()+"' where NUM_SESSION="+numS.getText();    		
    		Connect ct=new Connect();
    		
    		Statement st = ct.conn.createStatement(); 
       		st.executeQuery(sql);
       		
       		conf.setText("Mise à jour avec avec succès");

    	}catch(Exception ex) {
    		conf.setText(ex.getMessage());
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
    		}
    }
    
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    public void initDon(Session s) {
    	System.out.println("1");
    	numS.setText(Integer.toString(s.getNumSession()));
    	System.out.println("2");
    	nomS.setText(s.getNomSession());
    	System.out.println("3");
    	dateDeb.setValue(s.getDateDebut().toLocalDate());
    	System.out.println("4");
    	dateFin.setValue(s.getDateFin().toLocalDate());
    	nomF.setText(Integer.toString(s.getForm().getID()));
    }

}
