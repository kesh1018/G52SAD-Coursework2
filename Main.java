package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Photo Album");
        
		try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ImageView.fxml"));
            AnchorPane imageOverview = (AnchorPane) loader.load();
            
            ImageViewController controller = loader.getController();
            controller.setPrevStage(primaryStage);
            
            Scene scene = new Scene(imageOverview, 1200, 800);
            primaryStage.setScene(scene);
				
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
