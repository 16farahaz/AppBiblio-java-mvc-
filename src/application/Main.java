package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private Stage primaryStage;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage=primaryStage;
			this.primaryStage.setTitle("PFA");
			System.out.println("llll");
			
			//LogInWin();
			
			//MainWin();
			
			//ListWin();
			
			//LCWin();
			
			//LFWin();
			
			//LIWin();
			
			//LSWin();
			
			LSLWin();
			
			//LDWin();
			
			//LRWin();
			
			//GSWin();
			
		//	ChangeWin();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void ChangeWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/List.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void MainWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/MainWindow.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ListWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/List.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LFWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeFormation.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LCWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/listeClient.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LIWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeInscription.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LSWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSession.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LSLWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeSalle.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LDWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeDepense.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LRWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/ListeRevenue.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void GSWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/GenererStat.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void LogInWin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/graphique/LogIn.fxml")) ;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
