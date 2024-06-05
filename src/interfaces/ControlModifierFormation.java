package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Formateur;
import PFA.Formation;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControlModifierFormation implements Initializable{

    @FXML
    private TextField nom;

    @FXML
    private TextField contenue;

    @FXML
    private TextField duree;

    @FXML
    private TextField certification;

    @FXML
    private TextField tarif;
    
    private ToggleGroup typegrp;
    
    @FXML
    private RadioButton tyac;

    @FXML
    private RadioButton tynac;

    @FXML
    private Button btnrtr;

    @FXML
    private Button btnconf;

    @FXML
    private Label conf;

    @FXML
    private ComboBox<String> domaine;

    @FXML
    private ComboBox<String	> niveau;

    @FXML
    private Label id;

    @FXML
    void Conf(ActionEvent event) {
    	System.out.println();
        if(id.getText().isBlank() || nom.getText().isBlank() || contenue.getText().isBlank() || duree.getText().isBlank() || certification.getText().isBlank() || tarif.getText().isEmpty() || niveau.getSelectionModel().getSelectedIndex()==-1 || typegrp.getSelectedToggle()==null || domaine.getSelectionModel().getSelectedIndex()==-1)
        	conf.setText("Remplissez tous les champs");
        else 
        	try{
        		System.out.println(Integer.parseInt(id.getText()));
        		try {
        			System.out.println(Integer.parseInt(duree.getText()));
        			try {
        				System.out.println(Float.parseFloat(tarif.getText()));
        				updateDataFormation();
        				
        			}catch(Exception e){
        				System.out.println("Tarif doit être une relle");
        				conf.setText("Tarif doit être une relle");
        			}
        		}catch(Exception e) {
        			System.out.println("Duree doit être un entier");
        			conf.setText("Duree doit être un entier");
        		}
        	}catch(Exception e) {
        		System.out.println("ID doit être un entier");
        		conf.setText("ID doit être un entier");
        	}
        	
    }
    
    private void updateDataFormation() {
    	try{
    		String type;
    		if(typegrp.getSelectedToggle().equals(tyac))type="Accéléré";
    		else type="Non accéléré";
    		
    		String sql="UPDATE FORMATION SET NOM='"+nom.getText()+"',TYPE='"+type+"',DOMAINE='"+domaine.getSelectionModel().getSelectedItem()+"',DUREE="+Math.abs(Integer.parseInt(duree.getText()))+",CONTENUE='"+contenue.getText()+"',CERTIFICATION='"+certification.getText()+"',TARIF="+Math.abs(Float.parseFloat(tarif.getText()))+",NIVEAU='"+niveau.getSelectionModel().getSelectedItem()+"' WHERE ID="+Math.abs(Integer.parseInt(id.getText()));
    		Connect ct=new Connect();
    		Statement st = ct.conn.createStatement(); 
    		st.executeQuery(sql);
    		conf.setText("Mise à jour avec avec succès"); 
	}catch(Exception  e) {
		if(e.getMessage().toLowerCase().contains("unique"))
        	conf.setText("Une formation de même ID exist deja");
        else conf.setText("Error dans la connextion aven la base de données"); 
	}
    }
    
    
    ObservableList<String> domaineList = FXCollections.observableArrayList("Informatique","Commerce","Langue");
    ObservableList<String> niveauList = FXCollections.observableArrayList("Avec bac","Sans bac");
    
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeFormation.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	typegrp= new ToggleGroup();
    	tyac.setToggleGroup(typegrp);
    	tynac.setToggleGroup(typegrp);
    	domaine.setItems(domaineList);
    	niveau.setItems(niveauList);
    }
    
    public void initDon(Formation f) {	
    	id.setText(Integer.toString(f.getID()));
    	nom.setText(f.getNom());
    	duree.setText(Integer.toString(f.getDuree()));
    	contenue.setText(f.getContenue());
    	certification.setText(f.getCertification());
    	tarif.setText(Float.toString(f.getTarif()));
    	if(f.getType().toUpperCase().charAt(0)=='A')typegrp.selectToggle(tyac);
    	else typegrp.selectToggle(tynac);   
    	domaine.setValue(f.getDomaine());
    	niveau.setValue(f.getNiveau());
    }
}
