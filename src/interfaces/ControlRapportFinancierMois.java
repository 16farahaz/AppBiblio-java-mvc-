package interfaces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import PFA.Connect;
import PFA.RapportAnne;
import PFA.RapportMois;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class ControlRapportFinancierMois {

    @FXML
    private AnchorPane toImprimer;

    @FXML
    private TableView<RapportMois> tableMois;

    @FXML
    private TableColumn<RapportMois, String> colDescRev;

    @FXML
    private TableColumn<RapportMois, String> colRev;

    @FXML
    private TableColumn<RapportMois, String> colDescDep;

    @FXML
    private TableColumn<RapportMois, String> colDep;

    @FXML
    private TableColumn<RapportMois, String> colpp;

    @FXML
    private Label mois;

    @FXML
    private Label conf;

    @FXML
    private Button btnRet;

    @FXML
    private Button btnImp;
    
    private int indexMois;
    
    
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
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/RapportFinancierMois.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    private void ResultRev(ResultSet rsRev) throws SQLException {
		while(rsRev.next()) {
			if(rsRev.getDate("DATE_DE_RECU").ge) {
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
	
	 ObservableList<RapportMois> list = FXCollections.observableArrayList();
	
	private void RapportDonnee() {
		try {
			initArrayRP(arrayRM);
				
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
		
		colDescRev.setCellValueFactory(new PropertyValueFactory<>("DescrRevenue"));
		colRev.setCellValueFactory(new PropertyValueFactory<>("revenue"));
		colDescDep.setCellValueFactory(new PropertyValueFactory<>("DescrDepense"));
		colDep.setCellValueFactory(new PropertyValueFactory<>("depense"));
		colpp.setCellValueFactory(new PropertyValueFactory<>("difference"));
		
		tableMois.setItems(list);
		
	
	}
    
    
    void initDon(RapportAnne ra,int index) {
    	mois.setText(ra.getMois());
    	indexMois=index;
    }
}
