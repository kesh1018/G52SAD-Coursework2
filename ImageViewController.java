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
	
	public void initialize(URL url, ResourceBundle resource) {
		initScrollPane();
	}
	
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
		
		// Show save file dialog
		List<File> list = fileChooser.showOpenMultipleDialog(null);
		ObservableList<File> imageFiles = FXCollections.observableArrayList();
		
		if(list != null){
			for(File file : list){
				
				//Image image = new Image(file.toURI().toString());
				final Image image = new Image(new FileInputStream(file), 150, 150, true, false);
        		ImageView imageView = new ImageView();
        		imageView = createImageView(image);
        		
        		directoryField.setText(list.toString());
        		VBox vb = new VBox();
        		vb.getChildren().addAll(imageView);
        		
        		
        		if(check1.isSelected()){	
        	
        			
            		ListView<File> imageFilesList = new ListView<>(imageFiles);
            		imageFilesList.setCellFactory(listview -> new ListCell<File>(){
            			private final ImageView imageView = new ImageView();
                        {
                            imageView.setFitHeight(0);
                            imageView.setFitWidth(80);
                            imageView.setPreserveRatio(true);
                        }
                        
                        
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

            		grid.add(vb, imageCol, imageRow);
            	
            	
            		imageCol++;
                	
            		if(imageCol > 0){
            			imageCol = 0;
            			imageRow++;
            		}
            			
            		
        		}else{
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
                	
            		if(imageCol > 1){
            			imageCol = 0;
            			imageRow++;
            		}
        		}
			}
		}
	}
	
	private ImageView createImageView(Image image) throws IOException {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		
		
		imageView = new ImageView(image);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent mouseEvent) {

		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

		            if(mouseEvent.getClickCount() == 1){
		        	
		            	try {
		            		rotate = 0;
		            		ColorAdjust colorAdjust = new ColorAdjust();
		            		brightValue = 0;
		            
		            	
		            		Stage newStage = new Stage();
		            		newStage.setTitle("Picture Gallery");
		            		newStage.setWidth(prevStage.getWidth());
		            		newStage.setHeight(prevStage.getHeight());
		            		
		                    
		                    BorderPane borderpane = new BorderPane();
		                    ImageView imageView = new ImageView();
		                    
		                    MenuBar menuBar = new MenuBar();
		                    Menu menu1 = new Menu("Edit");
		                    Menu menu2 = new Menu("Brighten");
		                    Menu menu3 = new Menu("Saturation");
		                    Menu menu4 = new Menu("Contrast");
		                    Menu menu5 = new Menu("Save");
		                    
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
		                    
		                    borderpane.setTop(menuBar);
		            		imageView.setImage(image);
		            		imageView.setStyle("-fx-background-color: BLACK");
		            		imageView.setFitHeight(prevStage.getHeight() - 10);
		            		imageView.setPreserveRatio(true);
		            		imageView.setSmooth(false);
		            	
		            		imageView.setCache(true);
		            		borderpane.setCenter(imageView);
		            		borderpane.setStyle("-fx-background-color: BLACK");
		            		
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
		            		
		            		RotateImage.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
		            				rotate += 90;
		            				imageView.setRotate(rotate);
		            			}
		            		});
		            		
		            		menuItem2.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			brightValue += 0.1;
				            			colorAdjust.setBrightness(brightValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		menuItem3.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
					            		brightValue -= 0.1;
				            			colorAdjust.setBrightness(brightValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		menuItem4.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			saturationValue += 0.1;
				            			colorAdjust.setSaturation(saturationValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		menuItem5.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
					            		saturationValue -= 0.1;
				            			colorAdjust.setSaturation(saturationValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
		            		menuItem6.setOnAction(new EventHandler<ActionEvent>(){
		            			@Override
		            			public void handle(ActionEvent event){
				            			contrastValue += 0.1;
				            			colorAdjust.setContrast(contrastValue);
				            			imageView.setEffect(colorAdjust);
		            			}
		            		});
		            		
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
	
	public static void saveToFile(Image image) {
	    File outputFile = new File("C:/Users/dhurk/Documents/workspace/PictureGallery/src/application/images/");
	    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
	    try {
	      ImageIO.write(bImage, "png", outputFile);
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	}
	
	public void initScrollPane(){
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setContent(grid);
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

	