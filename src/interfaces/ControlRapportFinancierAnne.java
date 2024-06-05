package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.RapportAnne;
import PFA.Revenue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControlRapportFinancierAnne implements Initializable{

	@FXML
    private AnchorPane toImprimer;
	
    @FXML
    private TableView<RapportAnne> tableAnne;

    @FXML
    private TableColumn<RapportAnne, String> colMois;

    @FXML
    private TableColumn<RapportAnne, String> colRev;

    @FXML
    private TableColumn<RapportAnne, String> colDep;

    @FXML
    private TableColumn<RapportAnne, String> colpp;

    @FXML
    private Button btnRet;

    @FXML
    private Button btnImp;

    @FXML
    private Button btnMois;
    
    @FXML
    private Label anne;
    
    @FXML
    private Label conf;
    
    RapportAnne[] arrayRP=new RapportAnne[13];

    @FXML
    void Imprimer(ActionEvent event) {
    	    final PrinterJob printerJob = PrinterJob.createPrinterJob();    
    	    if (printerJob.showPrintDialog(toImprimer.getScene().getWindow())) {
    	      if (printerJob.printPage(toImprimer)) {
    	        printerJob.endJob();
    	      }
    	    }
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/AnneeDeRapport.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void VoirMois(ActionEvent event) {
    	try{
    		RapportAnne ra=tableAnne.getSelectionModel().getSelectedItem();
    		System.out.println(tableAnne.getSelectionModel().getSelectedIndex());
    		 FXMLLoader loader=new FXMLLoader();
 	        loader.setLocation(getClass().getResource("/graphique/RapportFinancierMois.fxml"));
 	        
 	        Parent rcParent =loader.load();
 	        
 	        Scene rcScene= new Scene(rcParent);
 	        
 	        //Accessing the destined controller
 	        ControlRapportFinancierMois cont=loader.getController();
 	        cont.initDon(ra,tableAnne.getSelectionModel().getSelectedIndex());
 	      
 	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
 	    	window.setScene(rcScene);
 	    	window.show();
 	    	}catch(Exception ex) {
    		conf.setText("Selectionnez un revenue");
    	}

    }
    
    void initArrayRP(RapportAnne[] arrayRP) {
    	for(int i=0;i<13;i++) {
    		String mois=null;
			switch(i) {
				case 0:mois="Janvier";break;
				case 1:mois="Février";break;
				case 2:mois="Mars";break;
				case 3:mois="Avril";break;
				case 4:mois="May";break;
				case 5:mois="Juin";break;
				case 6:mois="Juillet";break;
				case 7:mois="Août";break;
				case 8:mois="Septembre";break;
				case 9:mois="Octobre";break;
				case 10:mois="Novembre";break;
				case 11:mois="Décembre";break;
				case 12:mois="Total";break;
			}
			arrayRP[i]=new RapportAnne(mois, 0, 0);
    	}
    }
    
    
    
    ObservableList<RapportAnne> list = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	private void ResultRev(ResultSet rsRev) throws SQLException {
		while(rsRev.next()) {
			if(rsRev.getDate("DATE_DE_RECU").getYear()+1900==Integer.parseInt(anne.getText())) {
					int mois=rsRev.getDate("DATE_DE_RECU").getMonth();
					arrayRP[mois].setRevenue(arrayRP[mois].getRevenue()+rsRev.getFloat("MONTANT"));
			}
		}
	}
	
	private void ResultDep(ResultSet rsDep) throws SQLException {
		while(rsDep.next()) {
			if(rsDep.getDate("DATEDEP").getYear()+1900==Integer.parseInt(anne.getText())){
				int mois=rsDep.getDate("DATEDEP").getMonth();
				arrayRP[mois].setDepense(arrayRP[mois].getDepense()+rsDep.getFloat("MONTANT"));
				arrayRP[mois].setDifference(arrayRP[mois].getRevenue()-arrayRP[mois].getDepense());
			}
		}
	}
	
	private void RapportDonnee() {
		try {
			initArrayRP(arrayRP);
				
			Connect ct=new Connect();
			ResultSet rsRev = ct.conn.createStatement().executeQuery("Select * from REVENUE") ;
			ResultSet rsDep = ct.conn.createStatement().executeQuery("Select * from DEPENSE") ;
			 
			
			ResultRev(rsRev);
			ResultDep(rsDep);
			
			for(int i=0;i<12;i++) {
				arrayRP[12].setRevenue(arrayRP[12].getRevenue()+arrayRP[i].getRevenue());
				arrayRP[12].setDepense(arrayRP[12].getDepense()+arrayRP[i].getDepense());
			}
			arrayRP[12].setDifference(arrayRP[12].getRevenue()-arrayRP[12].getDepense());
			
			for(int i=0;i<13;i++)
				list.add(arrayRP[i]);
			
			
			
			
		}catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		
		colMois.setCellValueFactory(new PropertyValueFactory<>("mois"));
		colRev.setCellValueFactory(new PropertyValueFactory<>("revenue"));
		colDep.setCellValueFactory(new PropertyValueFactory<>("depense"));
		colpp.setCellValueFactory(new PropertyValueFactory<>("difference"));
		
		tableAnne.setItems(list);
		
	
	}
	
	public void initDon(int annee) {
		anne.setText(Integer.toString(annee));
		RapportDonnee();
	}
	
	
}
