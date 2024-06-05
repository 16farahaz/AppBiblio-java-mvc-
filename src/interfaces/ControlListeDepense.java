package interfaces;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import PFA.Connect;
import PFA.Depense;

public class ControlListeDepense implements Initializable {

	@FXML
	private Label conf;

	@FXML
    private TableView<Depense> table;

	@FXML
    private TableColumn<Depense, String> colnom;

    @FXML
    private TableColumn<Depense, String> colmontant;

    @FXML
    private TableColumn<Depense, String> coltype;

    @FXML
    private TableColumn<Depense, String> coldate;
    @FXML
    private  TableColumn<Depense, String> coldep;
    @FXML
    private DatePicker deb;
     
    @FXML
    private DatePicker fin; 

    @FXML
    private Button chercher;

    @FXML
    private Button retour;

    @FXML
    private Button rapport;

    @FXML
    private Button modifier;

    @FXML
    private Button ajouter;

    @FXML
    void Ajout(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AjoutDepense.fxml")) ;
    	Scene rcScene= new Scene(root);
       	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    @FXML
	public void Edit(ActionEvent event) throws IOException {
		Modifier(event);
	}
    
    @FXML
    void chercher(ActionEvent event) {
    	initialize(null, null);
    }
    
    @FXML
    void Modifier(ActionEvent event) throws IOException {
    	try {
    	Depense D=(table.getSelectionModel().getSelectedItem());
    	System.out.println("1");
        //Change the scene
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphique/ModifierDepense.fxml"));
        Parent rcParent =loader.load();
        
        Scene rcScene= new Scene(rcParent);
    	System.out.println("2");
        //Accessing the destined controller
        ControlModifierDepense cont=loader.getController();
        System.out.println("3");
        cont.initDonDep(D);
        System.out.println("4");
        Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    		conf.setText("Selectionner une depense");
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
	
    ObservableList<Depense> oblistdep = FXCollections.observableArrayList();
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	table.getItems().clear();
		Connect ct=new Connect();

		LocalDate dateDeb=deb.getValue(),dateFin=fin.getValue();
		int test=0;
		
		if(dateDeb!=null)
			test++;
		if(dateFin!=null)
			test+=2;
		try { 
				ResultSet rs = ct.conn.createStatement().executeQuery("select * from Depense") ;
				
				while(rs.next()) { 
					Depense dp=new Depense(rs.getString("NOM"), rs.getString("TYPE"),rs.getString("DESCRIPTION"),rs.getFloat("MONTANT"),rs.getDate("DATEDEP"));
					LocalDate daterc=dp.getDatedep().toLocalDate();
					switch(test) {
					case 0:oblistdep.add(dp);break;
					case 1:
						if(dateDeb.isBefore(daterc) || dateDeb.equals(daterc))
							oblistdep.add(dp);
						break;
					case 2:
						if(dateFin.isAfter(daterc) || dateFin.equals(daterc))
							oblistdep.add(dp);
						break;
					case 3:
						if((dateDeb.isBefore(daterc) && dateFin.isAfter(daterc) )|| dateDeb.equals(daterc) || dateFin.equals(daterc))
							oblistdep.add(dp);
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		colnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		coltype.setCellValueFactory(new PropertyValueFactory<>("Type"));
		coldate.setCellValueFactory(new PropertyValueFactory<>("datedep"));
		colmontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
		coldep.setCellValueFactory(new PropertyValueFactory<>("Description"));
		
		table.setItems(oblistdep);
}

}
