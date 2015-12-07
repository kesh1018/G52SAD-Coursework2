package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewController implements Initializable {
	
	int imageCol = 0;
	int imageRow = 0;
	Stage prevStage;
	
	@FXML
	private ScrollPane scroll  = new ScrollPane();
	
	@FXML
	private GridPane grid;
	
	@FXML
	private ImageView imageView;
	
	public void initialize(URL url, ResourceBundle resource) {
		initGridPane();
		initScrollPane();
	}
	
	public void initGridPane(){
		 grid.setAlignment(Pos.CENTER);
	     grid.setPadding(new Insets(200, 100, 100, 100));

	     grid.setHgap(500);
	     grid.setVgap(300);

	     ColumnConstraints columnConstraints = new ColumnConstraints();
	     columnConstraints.setFillWidth(true);
	     columnConstraints.setHgrow(Priority.ALWAYS);
	     grid.getColumnConstraints().add(columnConstraints);
	}
	
	public void initScrollPane(){
		
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setContent(grid);
	}

	@FXML
	private void handleNew() throws IOException{
		
		FileChooser fileChooser = new FileChooser();
		
		//Set extension filter
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg");
		
		fileChooser.getExtensionFilters().add(extFilter);
		
		// Show save file dialog
		List<File> list = fileChooser.showOpenMultipleDialog(null);
		
		if(list != null){
			for(File file : list){
				//Image image = new Image(file.toURI().toString());
        		ImageView imageView = new ImageView();
        	
                imageView.setCache(true);
        		imageView = createImageView(file);
        		
        		grid.getChildren().remove(imageView);
        		VBox vb = new VBox();
        		vb.getChildren().addAll(imageView);
        
        		grid.add(vb, imageCol, imageRow);
        		GridPane.setMargin(imageView, new Insets(50, 50, 50, 50));
        		
        		imageCol++;
        	
        		if(imageCol > 1){
        			imageCol = 0;
        			imageRow++;
        		}
			}
		}
		
	}
	
	private ImageView createImageView(File file) {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		
		try {
            final Image image = new Image(new FileInputStream(file), 150, 0, true,true);
            imageView = new ImageView(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(450);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 1){
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(file));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitHeight(prevStage.getHeight() - 10);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(prevStage.getWidth());
                                newStage.setHeight(prevStage.getHeight());
                                newStage.setTitle(file.getName());
                                Scene scene = new Scene(borderPane,Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
		 } catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        }
		return imageView;
	}

	@FXML
	private void handleExit(){
		System.exit(0);
	}
	
	@FXML
    private void handleAbout() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Photo Album");
        alert.setHeaderText("About");
        alert.setContentText("Author: Dhurkesh Puspanathan.\nAll rights reserved.\nCopyright (C) 1989, 1991 Free Software Foundation, Inc.");

        alert.showAndWait();
    }

	public void setPrevStage(Stage stage) {
		// TODO Auto-generated method stub
		this.prevStage = stage;
	}	
	
}

	