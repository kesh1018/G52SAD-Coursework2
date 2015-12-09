package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ImageEditController {
	
	Stage prevStage;
	
	@FXML
	private BorderPane borderpane;
	
	@FXML
	private ImageView imageView;
	
	public ImageEditController() {
		
	}
	
	 public void setBP(BorderPane borderpane) {
	       this.borderpane = borderpane;
	    }

	    public BorderPane getBP() {
	       return borderpane;
	    }
	    
	public void setImage(ImageView imageView) {
		  this.imageView = imageView;
	}

	public ImageView getImage() {
		  return imageView;
	}

	public void setPrevStage(Stage stage) {
		// TODO Auto-generated method stub
		this.prevStage = stage;
	}	

}
