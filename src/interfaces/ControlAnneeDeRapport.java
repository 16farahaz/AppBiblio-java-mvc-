package interfaces;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import PFA.Revenue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ControlAnneeDeRapport implements Initializable{

    @FXML
    private Button btnRet;

    @FXML
    private Button btnGen;

    @FXML
    private Spinner<Integer> anne;

    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	SpinnerValueFactory<Integer> gradesValueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, LocalDate.now().getYear());
    	
    	anne.setValueFactory(gradesValueFactory);
    	
    	
    	
    }
    
    
    @FXML
    void GenererRapport(ActionEvent event) {
    	try{
    		
    		 FXMLLoader loader=new FXMLLoader();
 	        loader.setLocation(getClass().getResource("/graphique/RapportFinancierAnne.fxml"));
 	        
 	        Parent rcParent =loader.load();
 	        
 	        Scene rcScene= new Scene(rcParent);
 	        
 	        //Accessing the destined controller
 	        ControlRapportFinancierAnne cont=loader.getController();
 	        cont.initDon(anne.getValue());
 	      
 	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
 	    	window.setScene(rcScene);
 	    	window.show();
 	    	}catch(Exception ex) {
 	    		System.out.println(ex.getMessage());
    	}

    }

    @FXML
    void Retour(ActionEvent event) {
    	Stage stage = (Stage) btnRet.getScene().getWindow();
        stage.close();
    }

}
