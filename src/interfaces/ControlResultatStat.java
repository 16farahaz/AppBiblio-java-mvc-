package interfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import PFA.Connect;
import PFA.Stat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlResultatStat implements Initializable{

    @FXML
    private PieChart chart;

    @FXML
    private Button btnRetour;
    
    @FXML
    private Label alert;
    
    private int countCher=0;
    private int countNCher=0;
    
    ObservableList<PieChart.Data> chartList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }
    
    
    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/graphique/GenererStat.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    
    void initTypeStat(Stat stat) throws SQLException {
    	if(stat.getAgeCli()==-1)
    		initDonNiveau(stat);
    	else if(stat.getNiveauCli()==null)
    		initDonAge(stat);
    	else initDon(stat);
    }
    
    
    void initDonAge(Stat stat) throws SQLException {
    	ResultSet rs=DonnesBase(stat.getForm().getID());
    	
    	while(rs.next() && rs.getDate("DATE_DE_DEBUT").after(stat.getDateDeb()) && rs.getDate("DATE_DE_FIN").before(stat.getDateFin())) {
    		LocalDate daten=rs.getDate("DATE_DE_NAISSANCE").toLocalDate();
    		LocalDate now=LocalDate.now();
    		if(Period.between(daten, now).getYears()==21)
    			countCher++;
    		else countNCher++;
    		
    		
    	}
    	if(countCher==countNCher && countCher==0)
    		alert.setText("Aucune session dans l'intervalle donné existe");
    	else {
    	chartList.add(new PieChart.Data("Clients d'âge "+stat.getAgeCli(),countCher));
    	chartList.add(new PieChart.Data("Le rest",countNCher));
    	
    	chart.setData(chartList);
    	}
    }
    
    
    void initDonNiveau(Stat stat) throws SQLException {
    	ResultSet rs=DonnesBase(stat.getForm().getID());
    	
    	while(rs.next()  && rs.getDate("DATE_DE_DEBUT").after(stat.getDateDeb()) && rs.getDate("DATE_DE_FIN").before(stat.getDateFin())) {

    		if(rs.getString("Niveau").equals(stat.getNiveauCli()))
    			countCher++;
    		else countNCher++;
    		
    	}
    	if(countCher==countNCher && countCher==0)
    		alert.setText("Aucune session dans l'intervalle donné existe");
    	else {
    		
    		String niveau;
    		if(stat.getNiveauCli().contains("Avec"))
    			niveau="sans bac";
    		else 
    			niveau="Avec bac";
    	
    		chartList.add(new PieChart.Data("Clients "+stat.getNiveauCli().toLowerCase(),countCher));
    		chartList.add(new PieChart.Data("Clients "+niveau,countNCher));
    	
    		chart.setData(chartList);
    	}
    }
    
    void initDon(Stat stat) throws SQLException {
    	ResultSet rs=DonnesBase(stat.getForm().getID() );
    	
    	while(rs.next()  && rs.getDate("DATE_DE_DEBUT").after(stat.getDateDeb()) && rs.getDate("DATE_DE_FIN").before(stat.getDateFin())) {
    		LocalDate daten=rs.getDate("DATE_DE_NAISSANCE").toLocalDate();
    		LocalDate now=LocalDate.now();
    		if(Period.between(daten, now).getYears()==21 && rs.getString("Niveau").equals(stat.getNiveauCli()))
    			countCher++;
    		else countNCher++;
    		
    	}
    	if(countCher==countNCher && countCher==0)
    		alert.setText("Aucune session dans l'intervalle donné existe");
    	else {
    		chartList.add(new PieChart.Data("Client d'âge "+stat.getAgeCli()+" et "+stat.getNiveauCli().toLowerCase(),countCher));
    		chartList.add(new PieChart.Data("Le rest",countNCher));
    		
    		chart.setData(chartList);
    	}
    }
    
    
    //Obtenir les données de la base
    ResultSet DonnesBase(int id) {
    	ResultSet rs=null;
    	try {
    		Connect ct=new Connect();
    		String where="WHERE S.ID="+id;
    		rs=ct.conn.createStatement().executeQuery("Select * from Inscription I inner join Sessions S on S.Num_Session=I.num_Session INNER JOIN Client C on C.CIN=I.CIN "+where);	
		
    	} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
    }
}
