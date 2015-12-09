package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewController implements Initializable {
	
	//Intialize previous stage
	Stage prevStage;

	private int rotate;
	private double brightValue;
	private double contrastValue;
	private double saturationValue;
	
	@FXML
	private ScrollPane scroll  = new ScrollPane();
	
	@FXML
	private GridPane grid;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private BorderPane borderpane;
	
	@FXML
	private CheckBox check1;
	
	@FXML
	private CheckBox check2;
	
	@FXML
	private CheckBox check3;
	
	
	//Load scrollPane settings before program begins
	public void initialize(URL url, ResourceBundle resource) {
		initScrollPane();
	}
	
	
	//To open a new image
	@FXML
	private void handleNew() throws IOException{
		
		int imageCol = 0;
		int imageRow = 0;
		
		TextField directoryField = new TextField();
		grid.getChildren().clear();
		
		FileChooser fileChooser = new FileChooser();
		
		//Set extension filter
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg", "*.tif", "*.bmp");
		
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Using list variable
		List<File> list = fileChooser.showOpenMultipleDialog(null);
		ObservableList<File> imageFiles = FXCollections.observableArrayList();
		
		if(list != null){
			for(File file : list){
				
				//Image image = new Image(file.toURI().toString());
				final Image image = new Image(new FileInputStream(file), 150, 150, true, false);
        		ImageView imageView = new ImageView();
        		imageView = createImageView(image);
        		
        		//Vbox is used to display over GridPane
        		VBox vb = new VBox();
        		vb.getChildren().addAll(imageView);
        		
        		//Is List checkbox is selected
        		if(check1.isSelected()){	
        	
        			//Convert image into list
            		ListView<File> imageFilesList = new ListView<>(imageFiles);
            		imageFilesList.setCellFactory(listview -> new ListCell<File>(){
            			private final ImageView imageView = new ImageView();
                        {
                            imageView.setFitHeight(0);
                            imageView.setFitWidth(80);
                            imageView.setPreserveRatio(true);
                        }
                        
                        //To obtain image path
                        @Override
                        public void updateItem(File file, boolean empty) {
                            super.updateItem(file, empty);
                            if (empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(file.getAbsolutePath().toString());
                                imageView.setImage(new Image(file.getAbsolutePath().toString(), true));
                                setGraphic(imageView);
                            }
                        }
                       
                        });
            		
            		//Add settings onto grid
            		grid.add(vb, imageCol, imageRow);
            	
            	
            		imageCol++;
                	
            		if(imageCol > 0){
            			imageCol = 0;
            			imageRow++;
            		}
            			
            		
        		}else{
        			//Grid settings for Thumbnail sized 
        			 grid.setAlignment(Pos.CENTER);
        		     grid.setPadding(new Insets(150, 15, 15, 15));

        		     grid.setHgap(300);
        		     grid.setVgap(300);
        		     
        		  
        		     ColumnConstraints columnConstraints = new ColumnConstraints();
        		     columnConstraints.setFillWidth(true);
        		     columnConstraints.setHgrow(Priority.ALWAYS);
        		     grid.getColumnConstraints().add(columnConstraints);
        		     
        		     imageView.setFitHeight(250);
        		     imageView.setFitWidth(250);
        		     
        		     grid.add(vb, imageCol, imageRow);
        		    
        		     
        		 	imageCol++;
                	//To ensure that the boundary is not over the defined window size
            		if(imageCol > 1){
            			imageCol = 0;
            			imageRow++;
            		}
        		}
			}
		}
	}
	
	//Function to create Image and Click through
	private ImageView createImageView(Image image) throws IOException {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		
		
		imageView = new ImageView(image);
		
		//If image is clicked
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent mouseEvent) {

		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

		            if(mouseEvent.getClickCount() == 1){
		        	
		            	try {
		            		//New settings is shown
		            		rotate = 0;
		            		ColorAdjust colorAdjust = new ColorAdjust();
		            		brightValue = 0;
		            		saturationValue = 0;
		            		contrastValue = 0;
		            
		            		//New stage is created
		            		Stage newStage = new Stage();
		            		newStage.setTitle("Picture Gallery");
		            		newStage.setWidth(prevStage.getWidth());
		            		newStage.setHeight(prevStage.getHeight());
		            		
		                    //BorderPane is made to show ImageView on it
		                    BorderPane borderpane = new BorderPane();
		                    ImageView imageView = new ImageView();
		                    
		                    MenuBar menuBar = new MenuBar();
		                    Menu menu1 = new Menu("Edit");
		                    Menu menu2 = new Menu("Brighten");
		                    Menu menu3 = new Menu("Saturation");
		                    Menu menu4 = new Menu("Contrast");
		                 
		                    //Initialize menu
		                    MenuItem RotateImage = new MenuItem("Rotate");
		                    MenuItem SaveImage = new MenuItem("Save Image");
		                    
		                    MenuItem menuItem2 = new MenuItem("Increase");
		                    MenuItem menuItem3 = new MenuItem("Decrease");
		                
		                    MenuItem menuItem4 = new MenuItem("Increase");
		                    MenuItem menuItem5 = new MenuItem("Decrease");
		                    
		                    MenuItem menuItem6 = new MenuItem("Increase");
		                    MenuItem menuItem7 = new MenuItem("Decrease");
		                    
		                    menu1.getItems().addAll(RotateImage, SaveImage);
		                    menu2.getItems().addAll(menuItem2,menuItem3);
		                    menu3.getItems().addAll(menuItem4,menuItem5);
		                    menu4.getItems().addAll(menuItem6,menuItem7);
		                    
		                    menuBar.getMenus().addAll(menu1,menu2,menu3,menu4);
		                    
		                    //Settings for menu - show fullscreen
		                    
		                    borderpane.setTop(menuBar);
		            		imageView.setImage(image);
		            		imageView.setStyle("-fx-background-color: BLACK");
		            		imageView.setFitHeight(prevStage.getHeight() - 10);
		            		imageView.setPreserveRatio(true);
		            		imageView.setSmooth(false);
		            	
		            		imageView.setCache(true);
		            		borderpane.setCenter(imageView);
		            		borderpane.setStyle("-fx-background-color: BLACK");
		            		
		            		//Save Image function
		            		SaveImage.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
		            				//saveToFile(image);
		            				 FileChooser fileChooser = new FileChooser();
		            	                fileChooser.setTitle("Save Image");
		            	                 
		            	                File file = fileChooser.showSaveDialog(null);
		            	                if (file != null) {
		            	                    try {
		            	                        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
		            	                                null), "png", file);
		            	                    } catch (IOException ex) {
		            	                        Logger.getLogger(
		            	                            ImageViewController.class.getName()).log(Level.SEVERE, null, ex);
		            	                    }
		            	                }
		            			}
		            		});
		            		
		            		//Rotate image function
		            		RotateImage.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
		            				rotate += 90;
		            				imageView.setRotate(rotate);
		            			}
		            		});
		            		
		            		//Increase brightness
		            		menuItem2.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			brightValue += 0.1;
				            			colorAdjust.setBrightness(brightValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		//Reduce brightness
		            		menuItem3.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
					            		brightValue -= 0.1;
				            			colorAdjust.setBrightness(brightValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		//Increase saturation
		            		menuItem4.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			saturationValue += 0.1;
				            			colorAdjust.setSaturation(saturationValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		//Decrease saturation
		            		menuItem5.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
					            		saturationValue -= 0.1;
				            			colorAdjust.setSaturation(saturationValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		//Increase contrast
		            		menuItem6.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			contrastValue += 0.1;
				            			colorAdjust.setContrast(contrastValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		
		            		//Decrease contrast
		            		menuItem7.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
					            		contrastValue -= 0.1;
				            			colorAdjust.setContrast(contrastValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
			                    
		                    Scene scene = new Scene(borderpane, 700, 600);
		                    newStage.setScene(scene);
		            		newStage.show();
							
		        		} catch(Exception e) {
		        			e.printStackTrace();
		        		}
		            }
		        }
		    }
		});
		return imageView;
	}
	
	public void initScrollPane(){
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setContent(grid);
	}
	
	
	//Exit function
	@FXML
	private void handleExit(){
		System.exit(0);
	}
	
	
	//About software 
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

	