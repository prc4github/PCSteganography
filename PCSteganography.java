package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*; 
import javafx.scene.input.*;
import javafx.scene.layout.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import javafx.scene.canvas.*;
import javafx.concurrent.Task;
 
// Start class PVDE
public class PCSteganography extends Application {
	//// For GUI Debugging
	VBox rootPane;
	Scene scene;
	TabPane tabPane;
	Tab tabEmbedding;
	Tab tabExtraction;  
	Tab tabWeightMatrix;
	Tab tabWeightMatrixMIF;
	Tab tabHammingCode; 
	Tab tabLogging;
	Tab tabTools;
	Tab tabImageCompare;
	Tab tabICCDC;
	Tab tabPVD5Nxt;
	Tab tabSubSampleClass;
	
	//// Controls for Embedding Tab		
	GridPane gridPane;
	VBox vboxPane;
	FlowPane filePathFlowPane;
	Label labelFilePath;
	TextField textFilePath;
	Button buttonFilePath;
	Image image;
	ImageView imageView;
	ScrollPane imageScrollPane;
	File file;
	BufferedImage bufferedImage;
	Label labelSecretMessage;
	Label labelSecretKey;
	TextField textSecretMessage;
	TextField textSecretKey;
	Button buttonProcess;
	Label labelReferenceTable;
	String strSecretMessageBits;
	String strSecretKeyBits;
	int strSecretMessageBitsPosition;
	CheckBox checkBoxDebugMain;
	CheckBox checkBoxDebugMsgBox;
	
	////Controls for Extraction Tab
	BufferedImage bufferedImageSM;
	BufferedImage bufferedImageSA;
	Image imageSM;
	Image imageSA;
	ScrollPane imageSMScrollPane;
	ScrollPane imageSAScrollPane;
	Label labelSharedSecretKeyForExtraction;
	TextField textSharedSecretKeyForExtraction;
	ImageView imageViewSM;
	ImageView imageViewSA;
	Button buttonExtraction;
	
	
	////Controls for Weight Matrix Tab
	VBox vboxWMPane;
	FlowPane paneCoverFilePathWM;
	Label labelCoverFilePathWM;
	TextField textCoverFilePathWM;
	Button buttonBrowseCoverFilePathWM; 
	FlowPane paneStegoFilePathWM;
	Label labelStegoFilePathWM;
	TextField textStegoFilePathWM;
	Button buttonBrowseStegoFilePathWM; 	
	ScrollPane imageScrollPaneWM;
	ScrollPane inputScrollPaneWM;
	Image imageWM;
	ImageView imageViewWM;
	File fileWM5x5;
	BufferedImage bufferedImageWM;
	Button buttonEmbedWM;
	Button buttonExtractWM;	
	TextField[][] textWMNumbers;	
	Label labelOutputImageSize;
	Label labelWidth;
	TextField textWidth;
	Label labelHeight;
	TextField textHeight;	
	Label labelRotation;
	ComboBox<String> comboRotation;
	Label labelColumn;
	ComboBox<String> comboColumn;
	Label labelRow;
	ComboBox<String> comboRow;
	CheckBox checkBoxLogWM;	
	String strSecretFileType;
	SelectionMatrix selectionMatrix;
	VBox selectionBoardVBox;
	int COL;
	int ROW;
	ProjectWM projectWMObject;
	static String[] CommandLineArguments;
	
	
	////Controls for Weight Matrix With MIF Tab
	VBox vboxWMPaneMIF;
	FlowPane paneCoverFilePathWMMIF;
	Label labelCoverFilePathWMMIF;
	TextField textCoverFilePathWMMIF;
	Button buttonBrowseCoverFilePathWMMIF; 
	FlowPane paneStegoFilePathWMMIF;
	Label labelStegoFilePathWMMIF;
	TextField textStegoFilePathWMMIF;
	Button buttonBrowseStegoFilePathWMMIF; 	
	ScrollPane imageScrollPaneWMMIF;
	ScrollPane inputScrollPaneWMMIF;
	Image imageWMMIF;
	ImageView imageViewWMMIF;
	File fileWM5x5MIF;
	BufferedImage bufferedImageWMMIF;
	Button buttonEmbedWMMIF;
	Button buttonExtractWMMIF;	
	TextField[][] textWMNumbersMIF;	
	Label labelOutputImageSizeMIF;
	Label labelWidthMIF;
	TextField textWidthMIF;
	Label labelHeightMIF;
	TextField textHeightMIF;	
	Label labelRotationMIF;
	ComboBox<String> comboRotationMIF;
	Label labelColumnMIF;
	ComboBox<String> comboColumnMIF;
	Label labelRowMIF;
	ComboBox<String> comboRowMIF;
	CheckBox checkBoxLogWMMIF;	
	String strSecretFileTypeMIF;
	SelectionMatrix selectionMatrixMIF;
	VBox selectionBoardVBoxMIF;
	int COLMIF;
	int ROWMIF;
	File coverFileNameMIF;
	CheckBox checkBoxMIFMIF;
	ProjectMIF projectWMObjectMIF;

	
	////Controls for Hamming Code Tab
	VBox vboxPaneHamming;
	FlowPane paneCoverFilePathHammingCode;
	Label labelCoverFilePathHammingCode;
	TextField textCoverFilePathHammingCode;
	Button buttonBrowseCoverFilePathHammingCode; 
	FlowPane paneSecretFilePathHammingCode;
	Label labelSecretFilePathHammingCode;
	TextField textSecretFilePathHammingCode;
	Button buttonBrowseSecretFilePathHammingCode;
	BufferedImage bufferedImageHammingCode;
	Image imageHammingCode;
	ImageView imageViewCoverImageHammingCode;
	ScrollPane imageScrollPaneHammingCode;
	ScrollPane inputScrollPaneHammingCode;
	FlowPane paneLSBSelectionHammingCode;
	Label labelLSBSelectionHammingCode;
	CheckBox checkboxLSBHammingCode;
	CheckBox checkboxLSBPlus1HammingCode;
	CheckBox checkboxLSBPlus2HammingCode;
	int bitSelection;
	FlowPane paneTypeOfHammingCode;
	Label labelTypeOfHammingCode;
	ComboBox<String> comboTypeOfHammingCode;
	FlowPane paneRowColumnOfHammingCode;
	Label labelBlockColumnHammingCode;
	ComboBox<String> comboBlockColumnHammingCode;
	Label labelBlockRowHammingCode;
	ComboBox<String> comboBlockRowHammingCode;
	FlowPane paneForEmbedExtractButtonHammingCode; 
	FlowPane paneParityCheck;
	Label labelParityCheck;
	ComboBox<String> comboParityCheck;
	CheckBox checkboxLogHC;
	Button buttonEmbedHammingCode;
	Button buttonExtractHammingCode;
	
		
	////Controls for ICCDC Tab
	BufferedImage coverFileBufferedImageICCDC;
	BufferedImage secretFileBufferedImageICCDC;
	BufferedImage stegoFileBufferedImageICCDC;
	
	//// Controls for Logging
	TextArea textAreaLogging;
	
	final Label labelProgress=new Label("Processing Status");
	final ProgressBar progressBar=new ProgressBar();
	Thread threadExtract;
	Thread threadEmbed;
	File coverFileName;
	
	
	////Controls for Tools Tab
	boolean flag=false;
	String imageFilePathTools;
	Label labelFilePathTools;
	TextField textFilePathTools;
	Label labelXTools;
	TextField textXTools;
	Label labelYTools;
	TextField textYTools;
	Label labelWidthTools;
	TextField textWidthTools;
	Label labelHeightTools;
	TextField textHeightTools;
	Button buttonOKTools;
	Button buttonSaltAndPepperTools;
	String imageSuitableFilePathTools;
	Label labelSuitableFilePathTools;
	TextField textSuitableFilePathTools;
	Label labelSuitableMaxTools;
	ComboBox<String> comboSuitableMaxTools;
	Label labelSuitableMinTools;
	ComboBox<String> comboSuitableMinTools;
	Button buttonSuitableOKTools;
	MenuItem menuItemSizes[];
		
	////Controls for Image Compare Tab
	BufferedImage bufferedImageOriginal;
	BufferedImage bufferedImageChanged;
	Image imageCompareOriginal;
	Image imageCompareChanged;
	ScrollPane imageOriginalScrollPane;
	ScrollPane imageChangedScrollPane;
	ImageView imageViewOriginal;
	ImageView imageViewChanged;
	Label labelOriginalFilePath;
	Label labelChangedFilePath;
	Button buttonImageCompare;
	Button buttonPSNR;
	Button buttonSD;
	Button buttonRSAnalysis;
	Button buttonClearList;
	Button buttonSSIM;
	ListView<String> listViewImg1;
	ListView<String> listViewImg2;
	
	@Override
	public void start(Stage primaryStage) {
		
		labelProgress.setPrefWidth(800);		
		progressBar.setPrefWidth(960);
		progressBar.setProgress(0);
		////progressBar.setStyle("-fx-accent: green");
		
		threadExtract=null;
		threadEmbed=null;
		
		strSecretMessageBits="";
		strSecretMessageBitsPosition=0;
		
		//Add a Tabbed pane
        tabPane = new TabPane();
        //Add the first tab for Encryption
        tabEmbedding = new Tab();
        tabEmbedding.setText("Embedding");
        tabEmbedding.setClosable(false);
        tabPane.getTabs().add(tabEmbedding);
        
        //Add the second tab for Decryption
        tabExtraction = new Tab(); 
        tabExtraction.setText("Extraction");
        tabExtraction.setClosable(false);
        tabPane.getTabs().add(tabExtraction);
      	
        tabExtraction.setOnSelectionChanged((e)->{
        	textSharedSecretKeyForExtraction.setText(textSecretKey.getText());        	
        });
        
        //// Weight Matrix 5x5
        tabWeightMatrix = new Tab(); 
        tabWeightMatrix.setText("Weight Matrix");
        tabWeightMatrix.setClosable(false);
        tabPane.getTabs().add(tabWeightMatrix);        
        
        //// Weight Matrix For MIF
        tabWeightMatrixMIF = new Tab(); 
        tabWeightMatrixMIF.setText("Weight Matrix (MIF)");
        tabWeightMatrixMIF.setClosable(false);
        tabPane.getTabs().add(tabWeightMatrixMIF);    
        
        //// Hamming Code
        tabHammingCode = new Tab(); 
        tabHammingCode.setText("Hamming Code");
        tabHammingCode.setClosable(false);
        tabPane.getTabs().add(tabHammingCode);   
        
        //// Logging Tab
        tabLogging = new Tab(); 
        tabLogging.setText("Logging");
        tabLogging.setClosable(false);
        tabPane.getTabs().add(tabLogging);
      	
        //// Tools Tab
        tabTools = new Tab();
        tabTools.setText("Tools");
        tabTools.setClosable(false);
        tabPane.getTabs().add(tabTools);
        
        //// Image Compare Tab
        tabImageCompare = new Tab();
        tabImageCompare.setText("Image Compare");
        tabImageCompare.setClosable(false);
        tabPane.getTabs().add(tabImageCompare);
        
        //// ICCDC Conference Paper
        tabICCDC = new Tab();
        tabICCDC.setText("PVD5_60 (ICCDC 2017)");
        tabICCDC.setClosable(false);
        tabPane.getTabs().add(tabICCDC);
        
        //// PVD5Nxt
        tabPVD5Nxt = new Tab();
        tabPVD5Nxt.setText("PVD5 Next");
        tabPVD5Nxt.setClosable(false);
        tabPane.getTabs().add(tabPVD5Nxt);
        
        //// SubSample Tab        
        tabSubSampleClass = new Tab();
        tabSubSampleClass.setText("SubSample");
        tabSubSampleClass.setClosable(false);
        tabPane.getTabs().add(tabSubSampleClass);
        
        //// Initialize the controls of the Embedding Tab
        InitializePVDEEmbedTab();
        //// Initialize the controls of the Extraction Tab
        InitializePVDEExtractionTab(); 
        //// Initialize the controls of the Weight Matrix 5x5 Tab
        InitializeWeightMatrixTab(); 
        //// Initialize the controls of the Weight Matrix 5x5 Tab
        InitializeWeightMatrixMIFTab(); 
        //// Initialize the controls of the Hamming Code Tab
        InitializeHammingCodeTab(); 
        //// Initialize the controls of the ICCDC Tab
        InitializeICCDCTab(); 
        //// Initialize the controls of the PVD5Nxt
        InitializePVD5NxtTab();
        //// Initialize the controls of the SubSample
        InitializeSubSampleClassTab(); 
        //// Initialize the controls of the Logging Tab
        InitializeLoggingTab();     
        //// Set the Popup Menu
        InitializePopupMenu();
        //// Initialize the controls of Tools Tab
        InitializeToolsTab();
        //// Initialize the Image Compare Tab
        InitializeImageCompareTab();
        
        
        //Add the tabPane in the window
        //rootPane = new StackPane();
        rootPane = new VBox();
        rootPane.getChildren().add(tabPane);
        rootPane.getChildren().add(labelProgress);
        rootPane.getChildren().add(progressBar);
        rootPane.getChildren().add(new Label(""));
        
        //// Select this Tab for the first time
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tabSubSampleClass);
        
        //// Create the scene and set the scene in the stage
		scene = new Scene(rootPane,970,620);		
		primaryStage.setScene(scene);
		//// Set the icon of the application
		primaryStage.getIcons().add(new Image("file:icon.jpg"));
		primaryStage.setTitle("PCSteganography");
		primaryStage.setX(380);
		primaryStage.setY(25);
		primaryStage.show(); 
	
	}//// End start
	
	void InitializePVDEEmbedTab(){
		///////////// START:- Top Row For File Path ////////////////
	    vboxPane = new VBox();
	    vboxPane.setPadding(new Insets(10));
	    vboxPane.setSpacing(2);
	    vboxPane.setPrefWidth(1000);
	    vboxPane.setAlignment(Pos.TOP_CENTER);
	    
	    filePathFlowPane = new FlowPane();
	    filePathFlowPane.setPadding(new Insets(10));
	    filePathFlowPane.setHgap(10);
	    filePathFlowPane.setPrefWidth(1000);
	    filePathFlowPane.setAlignment(Pos.CENTER); 
	    
	    labelFilePath = new Label("File Path");
		textFilePath = new TextField();
		textFilePath.setPrefColumnCount(60);
		buttonFilePath = new Button("Browse...");
		
		checkBoxDebugMain=new CheckBox("Debug");
		checkBoxDebugMain.setSelected(true);
		
		checkBoxDebugMain.setOnAction((e)->{
			if(checkBoxDebugMain.isSelected()){
				checkBoxDebugMsgBox.setSelected(true);				
				checkBoxLogWM.setSelected(true);
			}else{
				checkBoxDebugMsgBox.setSelected(false);
				checkBoxLogWM.setSelected(false);
			}
		});
		checkBoxDebugMsgBox=new CheckBox("Debug");
		checkBoxDebugMsgBox.setSelected(true);
		
		checkBoxDebugMsgBox.setOnAction((e)->{
			if(checkBoxDebugMsgBox.isSelected()){
				
				checkBoxDebugMain.setSelected(true);
			}else{
				
				checkBoxDebugMain.setSelected(false);
			}
		});
		
		buttonFilePath.setOnAction((event) -> {
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	          
	        ////Show open file dialog
	        file = fileChooser.showOpenDialog(null);
	        ////Display the selected file in the image view           
	        try {
	            bufferedImage = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
	            imageView.setImage(image);
	            textFilePath.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
	
	    });
			
	    filePathFlowPane.getChildren().addAll(labelFilePath,textFilePath,buttonFilePath,checkBoxDebugMain);
	    vboxPane.getChildren().add(filePathFlowPane);
	    
	    tabEmbedding.setContent(vboxPane);
	    ///////////// END:- Top Row For File Path ////////////////////////
	    
	    ///////////// START:- Middle Row For File Path ////////////////////////
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(0, 10, 0, 10));
	    vboxPane.getChildren().add(gridPane);
	    
	    
	    imageView = new ImageView();
	    imageView.setPreserveRatio(true);
	    imageView.setSmooth(true);
	    imageView.setCache(true);
	    imageView.autosize();
	    imageScrollPane = new ScrollPane();
	    imageScrollPane.setPrefSize(700, 450);
	    imageScrollPane.setContent(imageView);
	    
	    ////Add image view to the left scroll pane
	    GridPane.setConstraints(imageScrollPane, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPane,1);
	    
	    ScrollPane inputScrollPane = new ScrollPane();
	    inputScrollPane.setPrefSize(250, 450);
	    VBox inputPane = new VBox();
	   
	    //// Message Part
	    labelSecretMessage = new Label("Secret Message or File");
		textSecretMessage = new TextField();
		textSecretMessage.setPrefColumnCount(20);
		//// Set Tooltip to the Secret Message File Textbox
		final Tooltip tooltipSecretMessage = new Tooltip();
		tooltipSecretMessage.setText("Empty");
		textSecretMessage.setTooltip(tooltipSecretMessage);
		textSecretMessage.setOnMouseEntered((e)->{
			if(textSecretMessage.getText().trim().length()>0){
				tooltipSecretMessage.setText(textSecretMessage.getText());
			}else{
				tooltipSecretMessage.setText("Empty");
			}			
		});
		labelSecretKey = new Label("Shared Secret Key");
		textSecretKey = new TextField();
		textSecretKey.setPrefColumnCount(20);
		
		inputPane.getChildren().addAll(labelSecretMessage,textSecretMessage);
		//// Use this empty label as a gap between two text boxes
		inputPane.getChildren().addAll(new Label(""));
		inputPane.getChildren().addAll(labelSecretKey,textSecretKey);
		//// Reference Part
		
		inputScrollPane.setContent(inputPane);
		////Add input view to the left scroll pane
	    GridPane.setConstraints(inputScrollPane, 1, 0); // column=1 row=0
	    GridPane.setColumnSpan(inputScrollPane,1);
		
	    gridPane.getChildren().addAll(imageScrollPane,inputScrollPane);
	    
	    FlowPane buttonPane = new FlowPane();
	    buttonPane.setPadding(new Insets(10));
	    buttonPane.setHgap(10);
	    buttonPane.setPrefWidth(1000);
	    buttonPane.setAlignment(Pos.CENTER);
		buttonProcess = new Button("Data Embedding");
		buttonProcess.setOnAction((event) -> {
			try{
				//ProcessEmbedding(bufferedImage);
				ProcessPVDEEmbedding();
			}catch(IOException ix){
				PCUtility.MessageBox("Exception occured during embed processing.");
				return;
			}
			
	    });
		buttonPane.getChildren().add(buttonProcess);
		vboxPane.getChildren().add(buttonPane);
		
	    //////// START:-  Drag And Drop in ImageView ////////////////////
	    textFilePath.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    textSecretMessage.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    imageScrollPane.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    textFilePath.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	                textFilePath.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    bufferedImage = ImageIO.read(file);
	                    image = SwingFXUtils.toFXImage(bufferedImage, null);
	                    imageView.setImage(image);	                   
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }
	                                 
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
	    textSecretMessage.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String secretFilePath = file.getAbsolutePath();
	            	textSecretMessage.setText(secretFilePath);
	            	tooltipSecretMessage.setText(secretFilePath);
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
	    imageScrollPane.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	                textFilePath.setText(saveFilePath);
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    bufferedImage = ImageIO.read(file);
	                    image = SwingFXUtils.toFXImage(bufferedImage, null);
	                    imageView.setImage(image);
	                    textSecretMessage.setText("abcdefghijklmnopqrstuvwxyz0123456789+-%*");
	                    textSecretKey.setText("a");
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }                                     
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });//////// END:-  Drag And Drop in ImageView ////////////////////
		
	}//// End InitializeFirstTab
	
	void InitializePVDEExtractionTab(){
		
		labelSharedSecretKeyForExtraction=new Label("Shared Secret Key");
		textSharedSecretKeyForExtraction=new TextField();
		textSharedSecretKeyForExtraction.setPrefColumnCount(50);
		imageViewSM=new ImageView();
		imageViewSA=new ImageView();
		buttonExtraction=new Button("Extraction");
		buttonExtraction.setOnAction((e)->{
			ProcessPVDEExtraction();			
		});
		
		VBox vboxExtraction=new VBox();
		//// Shared Key input box
		FlowPane flowPaneSecretKey=new FlowPane();
		flowPaneSecretKey.setPadding(new Insets(10));
		flowPaneSecretKey.setHgap(10);
		flowPaneSecretKey.setPrefWidth(1000);
		flowPaneSecretKey.setAlignment(Pos.CENTER);
		flowPaneSecretKey.getChildren().addAll(labelSharedSecretKeyForExtraction,
				                               textSharedSecretKeyForExtraction);
		vboxExtraction.getChildren().add(flowPaneSecretKey);
		
		//// Image Views
		GridPane gridPaneImageViews = new GridPane();
		gridPaneImageViews.setHgap(10);
		gridPaneImageViews.setVgap(10);
		gridPaneImageViews.setPadding(new Insets(0, 10, 0, 10));
	    	    
	    //// Scroll view for SM image
	    imageViewSM.setPreserveRatio(true);
	    imageViewSM.setSmooth(true);
	    imageViewSM.setCache(true);
	    imageViewSM.autosize();
	    imageSMScrollPane = new ScrollPane();
	    imageSMScrollPane.setPrefSize(500, 450);
	    imageSMScrollPane.setContent(imageViewSM);
	    ////Add SM image view to the left scroll pane
	    GridPane.setConstraints(imageSMScrollPane, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageSMScrollPane,1);
	    
	    //// Scroll view for SM image
	    imageViewSA.setPreserveRatio(true);
	    imageViewSA.setSmooth(true);
	    imageViewSA.setCache(true);
	    imageViewSA.autosize();
	    imageSAScrollPane = new ScrollPane();
	    imageSAScrollPane.setPrefSize(500, 450);
	    imageSAScrollPane.setContent(imageViewSA);
	    ////Add SM image view to the left scroll pane
	    GridPane.setConstraints(imageSAScrollPane, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageSAScrollPane,1);
	    //// Add the iamge views to the grid pane
	    gridPaneImageViews.getChildren().addAll(imageSMScrollPane,imageSAScrollPane);
	    
		//// Add the grid pane containing image views to the vboxExtraction
	    vboxExtraction.getChildren().add(gridPaneImageViews);
	    
	    //// Add the Extraction button
	    FlowPane flowPaneButton=new FlowPane();
	    flowPaneButton.setPadding(new Insets(10));
	    flowPaneButton.setHgap(200);
	    flowPaneButton.setPrefWidth(1000);
	    flowPaneButton.setAlignment(Pos.CENTER);
	    
	    flowPaneButton.getChildren().add(new Label("SM Image"));
	    flowPaneButton.getChildren().add(buttonExtraction);
	    flowPaneButton.getChildren().add(new Label("SA Image"));
	    //// Add the button to the vbox	    
		vboxExtraction.getChildren().add(flowPaneButton);		
		
		//// Add the vboxExtraction to the Extraction Tab
		tabExtraction.setContent(vboxExtraction);
		
		
		imageSMScrollPane.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    
		imageSAScrollPane.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		imageSMScrollPane.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String filePathSM = file.getAbsolutePath();
	                try{
	                	//// Set the image in the SM image view
	                    file=new File(filePathSM);
	                    bufferedImageSM = ImageIO.read(file);
	                    imageSM = SwingFXUtils.toFXImage(bufferedImageSM, null);
	                    imageViewSM.setImage(imageSM);	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }                                     
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
		imageSAScrollPane.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String filePathSA = file.getAbsolutePath();
	                try{
	                	//// Set the image in the SA image view
	                    file=new File(filePathSA);
	                    bufferedImageSA = ImageIO.read(file);
	                    imageSA = SwingFXUtils.toFXImage(bufferedImageSA, null);
	                    imageViewSA.setImage(imageSA);	                   
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }                                     
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
	}//// End InitializeExtractionTab

	//// This function initializes Weight Matrix project tab
	
	void InitializeWeightMatrixTab()	{		
		
		vboxWMPane=new VBox();
		//// Create a flow pane for cover file path controls
		paneCoverFilePathWM=new FlowPane();
		paneCoverFilePathWM.setPadding(new Insets(10));
		paneCoverFilePathWM.setHgap(10);
		paneCoverFilePathWM.setAlignment(Pos.CENTER);		
		//// Create and add the file path controls in the flow pane
		labelCoverFilePathWM=new Label("Cover File Path");
		textCoverFilePathWM=new TextField();
		textCoverFilePathWM.setPrefColumnCount(60);
		buttonBrowseCoverFilePathWM=new Button("Browse");
		
		//// Create a flow pane for cover file path controls
		paneStegoFilePathWM=new FlowPane();
		paneStegoFilePathWM.setPadding(new Insets(10));
		paneStegoFilePathWM.setHgap(10);
		paneStegoFilePathWM.setAlignment(Pos.CENTER);
		
		labelStegoFilePathWM=new Label("Stego File Path");
		textStegoFilePathWM=new TextField();
		textStegoFilePathWM.setPrefColumnCount(60);
		buttonBrowseStegoFilePathWM=new Button("Browse");
		
		checkBoxLogWM=new CheckBox("LOG");
		checkBoxLogWM.setSelected(false);
				
		paneCoverFilePathWM.getChildren().addAll(labelCoverFilePathWM,
												 textCoverFilePathWM,
												 buttonBrowseCoverFilePathWM);
		paneStegoFilePathWM.getChildren().addAll(labelStegoFilePathWM,
												 textStegoFilePathWM,
												 buttonBrowseStegoFilePathWM);
		vboxWMPane.getChildren().addAll(paneCoverFilePathWM, paneStegoFilePathWM);
	
		//// Create a grid pane and two columns. 
		//// one for image view and another for 3x3 text matrix. 
		GridPane gridPaneWM=new GridPane();
		gridPaneWM.setHgap(10);
		gridPaneWM.setVgap(10);
		gridPaneWM.setPadding(new Insets(0, 10, 0, 10));
		imageViewWM=new ImageView();
		imageViewWM.setPreserveRatio(true);
		imageViewWM.setSmooth(true);
		imageViewWM.setCache(true);
		imageViewWM.autosize();
		imageScrollPaneWM=new ScrollPane();
		imageScrollPaneWM.setPrefSize(550, 550);
		imageScrollPaneWM.setContent(imageViewWM);
	    GridPane.setConstraints(imageScrollPaneWM, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneWM,1);	    
	    gridPaneWM.getChildren().add(imageScrollPaneWM);	    
	    vboxWMPane.getChildren().add(gridPaneWM);
		
	    //////////////////////////////////////////////////////
	    HBox hboxForWMAndRowCol=new HBox();
	    
	    ////Pane for Weight Matrix text boxes.
	    GridPane gridPaneTextFieldWM=new GridPane();
	    gridPaneTextFieldWM.setHgap(10);
	    gridPaneTextFieldWM.setVgap(10);
	    gridPaneTextFieldWM.setPadding(new Insets(0, 10, 0, 10));
	    //// Create 9 text boxes here
		textWMNumbers=new TextField[5][5];		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				textWMNumbers[j][i]=new TextField();
				textWMNumbers[j][i].setPrefColumnCount(2);
			}			
		}
		
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++){
				GridPane.setConstraints(textWMNumbers[row][col], col, row); // column=0 row=0
			    GridPane.setColumnSpan(textWMNumbers[row][col],1);	    
			    gridPaneTextFieldWM.getChildren().add(textWMNumbers[row][col]);
			}
		}
				 
		////Pane for output image width and height
	    GridPane gridPaneForWidthHeight=new GridPane();
	    gridPaneForWidthHeight.setHgap(10);
	    gridPaneForWidthHeight.setVgap(10);
	    gridPaneForWidthHeight.setPadding(new Insets(0, 10, 0, 10));
	    
	    labelOutputImageSize=new Label("O/P Image");
	    labelWidth=new Label("Width");
		textWidth=new TextField();
		textWidth.setPrefColumnCount(1);
		labelHeight=new Label("Height");
		textHeight=new TextField();
		textHeight.setPrefColumnCount(1);
		
		GridPane.setConstraints(labelOutputImageSize, 0, 0); // column=0 row=0
		GridPane.setColumnSpan(labelOutputImageSize, 1);
		
		GridPane.setConstraints(labelWidth, 0, 1); // column=0 row=0
		GridPane.setConstraints(textWidth, 0, 2); // column=0 row=0
		GridPane.setConstraints(labelHeight, 0, 3); // column=0 row=0			
		GridPane.setConstraints(textHeight, 0, 4); // column=0 row=0
	    
		ScrollPane scrollPaneForWidthHeight=new ScrollPane();
		scrollPaneForWidthHeight.setPadding(new Insets(10,10,10,10));
		scrollPaneForWidthHeight.setContent(gridPaneForWidthHeight);
		
		gridPaneForWidthHeight.getChildren().addAll(labelOutputImageSize,labelWidth,
				                                    textWidth,labelHeight,textHeight);
	    		
	    hboxForWMAndRowCol.getChildren().addAll(gridPaneTextFieldWM,scrollPaneForWidthHeight);
	    //////////////////////////////////////////////////////
		//// Add the rotation label and Combo box
		FlowPane paneRotation=new FlowPane();
		labelRotation=new Label("Round");
		comboRotation=new ComboBox<String>();
		comboRotation.getItems().addAll("1","2","3","4","5","6","7","8",
				                           "9","10","11","12","13","14","15","16");
		comboRotation.setValue("1"); //// Set default to 16 
		
		labelColumn=new Label("Col");
		comboColumn=new ComboBox<String>();
		comboColumn.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
		comboColumn.setValue("5");
		labelRow=new Label("Row");;
		comboRow=new ComboBox<String>();
		comboRow.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
		comboRow.setValue("5");
		
		paneRotation.setHgap(5);
		paneRotation.getChildren().addAll(labelRotation,comboRotation,
										  labelColumn, comboColumn,
										  labelRow, comboRow, checkBoxLogWM);
				
		
		/////////////////////////////////////////////////////
		VBox vboxInputPaneWM=new VBox();
		
		Label labelMatrixWM=new Label("Matrix");
		
		//// Create a SelectionBoard object here.
		selectionBoardVBox=new VBox();
		selectionMatrix=new SelectionMatrix(this,selectionBoardVBox);
		selectionMatrix.createSelectionBoard();
				
		vboxInputPaneWM.getChildren().addAll(labelMatrixWM,
											 hboxForWMAndRowCol,
											 new Label(),
											 paneRotation,
											 new Label(),
											 new Label(),
											 selectionBoardVBox);
		
		inputScrollPaneWM=new ScrollPane();
		inputScrollPaneWM.setPadding(new Insets(10, 10, 10, 10));
		inputScrollPaneWM.setContent(vboxInputPaneWM);
		inputScrollPaneWM.setPrefSize(380, 550);
		inputScrollPaneWM.setFitToWidth(true);
		
		
		GridPane.setConstraints(inputScrollPaneWM, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(inputScrollPaneWM,1);	    
	    gridPaneWM.getChildren().add(inputScrollPaneWM);
	    
	    FlowPane flowPaneButtonsWM=new FlowPane();
	    flowPaneButtonsWM.setHgap(50);
	    flowPaneButtonsWM.setPadding(new Insets(10,0,0,0));
	    flowPaneButtonsWM.setAlignment(Pos.CENTER);
	    
	    buttonEmbedWM=new Button("Embed");
	    buttonEmbedWM.setOnAction((e)->{
	    	
	    	COL=Integer.parseInt(comboColumn.getValue());
	    	ROW=Integer.parseInt(comboRow.getValue());
	    	
	    	//// Initializes the ProjectWM object here. ///////////////////////////
	    	//// Populate maxRow and maxCol variables
	       	int[][] WeightMatrix=new int[ROW][COL];
			int ii,jj;		
			
			for(ii=0;ii<ROW;ii++){  
				for(jj=0;jj<COL;jj++){
					if(selectionMatrix.isDataPixel(jj,ii)){
						WeightMatrix[ii][jj]=Integer.parseInt(textWMNumbers[ii][jj].getText().trim());
					}else{
						WeightMatrix[ii][jj]=0;
					}			
				}			
			}
			
			//// Set the output file size
			if(textStegoFilePathWM.getText().trim().equals("")){
				setOutputImageSizeWM();
				return;
			}
					
			//// Create a Matrix object here and assign the initial matrix
			WTMatrix matrix1=new WTMatrix(WeightMatrix,ROW,COL,false);
		
			/////////// Creating a task for Embedding //////////////////////////////
			projectWMObject=new ProjectWM(this,coverFileName,bufferedImageWM,
					   Integer.parseInt(comboRotation.getValue()),
					   COL,
					   ROW,
					   textAreaLogging,this,selectionMatrix,checkBoxLogWM.isSelected());
			//// Call the embedding function
			projectWMObject.InitializeEmbeddingWM(textCoverFilePathWM.getText().trim(), 
	    			                           textStegoFilePathWM.getText().trim(), 
	    			                           matrix1);			
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(projectWMObject.progressProperty());
			labelProgress.textProperty().bind(projectWMObject.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(projectWMObject);
			}catch(Exception ex){
				MessageBox("Exception Occured During Embedding Process!!!");
				return;
			}			
			//////////////////////////////////////////////////////////////////////////
			
	    });
	    
		buttonExtractWM=new Button("Extract");				
		buttonExtractWM.setOnAction((e)->{
			
			COL=Integer.parseInt(comboColumn.getValue());
			ROW=Integer.parseInt(comboRow.getValue());			
			
			//// Initializes the ProjectWM object here. //////////////////////////
			
			//////////////////////////////////////////////////////////////////////
           	           	
			//// Prepare the weight matrix			
			int ii,jj;
			int[][] WeightMatrix=new int[ROW][COL];
			
			for(ii=0;ii<ROW;ii++){
				for(jj=0;jj<COL;jj++){
					if(selectionMatrix.isDataPixel(jj,ii)){
						WeightMatrix[ii][jj]=Integer.parseInt(textWMNumbers[ii][jj].getText().trim());
					}else{
						WeightMatrix[ii][jj]=0;
					} 		
				}			
			}
			
			//// Create a Matrix object here and assign the initial matrix
			WTMatrix matrix2=new WTMatrix(WeightMatrix,ROW,COL,false);
			
			///////// Create a task for Extraction process ///////////////////////
			projectWMObject=new ProjectWM(this,coverFileName,bufferedImageWM,
					   Integer.parseInt(comboRotation.getValue()),
					   COL,
					   ROW,
					   textAreaLogging,this,selectionMatrix,checkBoxLogWM.isSelected());
			//// Calling Extraction Process
			projectWMObject.InitializeExtractionWM(textCoverFilePathWM.getText().trim(),matrix2);
			//// Start the embedding thread ////////////
			progressBar.progressProperty().bind(projectWMObject.progressProperty());
			labelProgress.textProperty().bind(projectWMObject.messageProperty());
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(projectWMObject);
			}catch(Exception ex){
				MessageBox("Exception Occured During Extraction Process!!!");
				return;
			}			
			////////////////////////////////////////////////////////////////////////
					
		});
				
		flowPaneButtonsWM.getChildren().addAll(buttonEmbedWM,buttonExtractWM);
		vboxWMPane.getChildren().add(flowPaneButtonsWM);		
		
		buttonBrowseCoverFilePathWM.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	          
	        ////Show open file dialog
	        file = fileChooser.showOpenDialog(null);
	        ////Display the selected file in the image view           
	        try {
	        	bufferedImageWM = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImageWM, null);
	            imageViewWM.setImage(image);
	            textCoverFilePathWM.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		});
		buttonBrowseStegoFilePathWM.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	          
	        ////Show open file dialog
	        file = fileChooser.showOpenDialog(null);
	        ////Display the selected file in the image view           
	        try {
	        	bufferedImageWM = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImageWM, null);
	            imageViewWM.setImage(image);
	            textCoverFilePathWM.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		});
		////////// START: textCoverFilePathWM and imageScrollPaneWM Drag and Drop   ////////////////////

		inputScrollPaneWM.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
				
		textCoverFilePathWM.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textCoverFilePathWM.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathWM.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    bufferedImageWM = ImageIO.read(file);
	                    image = SwingFXUtils.toFXImage(bufferedImageWM, null);
	                    imageViewWM.setImage(image);	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		imageScrollPaneWM.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		imageScrollPaneWM.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            int count=0;
	            for (File file:db.getFiles()) {	            	
	            	if(count==0){
	            		String saveFilePath = file.getAbsolutePath();
		                textFilePath.setText(saveFilePath);
		                try{
		                	//// Set the image in the image view
		                    file=new File(saveFilePath);
		                    bufferedImageWM = ImageIO.read(file);
		                    image = SwingFXUtils.toFXImage(bufferedImageWM, null);
		                    imageViewWM.setImage(image);
		                    textCoverFilePathWM.setText(file.getAbsolutePath());
		                    
		                    //strOutputFilePath=file.getParent();
		                    coverFileName=file;
		                }catch(Exception ex){
		                	ex.printStackTrace();
		                	return;
		                }
	            		count++;
	            	}else if(count==1){  
	            		String secretFilePath = file.getAbsolutePath();
		            	textStegoFilePathWM.setText(secretFilePath);	            	
		               	try{
			            	BufferedImage image = ImageIO.read(new File(textStegoFilePathWM.getText().trim()));
			               	if(Integer.parseInt(textHeight.getText().trim()) * Integer.parseInt(textWidth.getText().trim()) == 
			            	   image.getHeight() * image.getWidth()){
			            		textWidth.setText(""+image.getWidth());
								textHeight.setText(""+image.getHeight());
								image=null;
			            	}						
		            	}catch(Exception ex){}
	            		count++;
	            	}	            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		inputScrollPaneWM.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String secretFilePath = file.getAbsolutePath();
	            	textStegoFilePathWM.setText(secretFilePath);	            	
	            	///////////////////////////////////////////////////
	            	try{
		            	BufferedImage image = ImageIO.read(new File(textStegoFilePathWM.getText().trim()));
		               	if(Integer.parseInt(textHeight.getText().trim()) * Integer.parseInt(textWidth.getText().trim()) == 
		            	   image.getHeight() * image.getWidth()){
		            		textWidth.setText(""+image.getWidth());
							textHeight.setText(""+image.getHeight());
							image=null;
		            	}						
	            	}catch(Exception ex){}	              	
	            	///////////////////////////////////////////////////
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		//////////END: textCoverFilePathWM and imageScrollPaneWM Drag and Drop   /////////////
		    
		tabWeightMatrix.setContent(vboxWMPane);
	
		COL=0;
		ROW=0;
		//strOutputFilePath="";
		coverFileName=null;
				
		//// Initialize Cover And Stego file
		if( CommandLineArguments.length == 2 ){
			//// Initialize Cover And Stego File
			String coverFilePath="";
			String stegoFilePath="";
			
			if(CommandLineArguments.length==2){			
				coverFilePath=CommandLineArguments[0];	
				stegoFilePath=CommandLineArguments[1];
			}else{
				return;
			}
			
			try{
	        	//// Set the image in the image view
	            file=new File(coverFilePath);
	            bufferedImageWM = ImageIO.read(file);
	            image = SwingFXUtils.toFXImage(bufferedImageWM, null);
	            imageViewWM.setImage(image);
	            textCoverFilePathWM.setText(file.getAbsolutePath());  
	           	
	            //// Initialize stego file in the secret message pane
	    		textStegoFilePathWM.setText(stegoFilePath);
	    		//// Initialize cover file
	    		coverFileName=file;
	    		
	        }catch(Exception ex){
	        	ex.printStackTrace();  
	        	return;
	        }
		}				
		
	}//// End InitializeWeightMatrix5x5Tab	
		
	
	////This function initializes Weight Matrix MIF Project tab
	void InitializeWeightMatrixMIFTab()	{		
		
		vboxWMPaneMIF=new VBox();
		//// Create a flow pane for cover file path controls
		paneCoverFilePathWMMIF=new FlowPane();
		paneCoverFilePathWMMIF.setPadding(new Insets(10));
		paneCoverFilePathWMMIF.setHgap(10);
		paneCoverFilePathWMMIF.setAlignment(Pos.CENTER);		
		//// Create and add the file path controls in the flow pane
		labelCoverFilePathWMMIF=new Label("Cover File Path");
		textCoverFilePathWMMIF=new TextField();
		textCoverFilePathWMMIF.setPrefColumnCount(60);
		buttonBrowseCoverFilePathWMMIF=new Button("Browse");
		
		//// Create a flow pane for cover file path controls
		paneStegoFilePathWMMIF=new FlowPane();
		paneStegoFilePathWMMIF.setPadding(new Insets(10));
		paneStegoFilePathWMMIF.setHgap(10);
		paneStegoFilePathWMMIF.setAlignment(Pos.CENTER);
		
		labelStegoFilePathWMMIF=new Label("Stego File Path");
		textStegoFilePathWMMIF=new TextField();
		textStegoFilePathWMMIF.setPrefColumnCount(60);
		buttonBrowseStegoFilePathWMMIF=new Button("Browse");
		
		checkBoxLogWMMIF=new CheckBox("LOG");
		checkBoxLogWMMIF.setSelected(false);
		
		
		checkBoxLogWMMIF.setOnAction((e)->{
			if(checkBoxLogWMMIF.isSelected()){
				
				checkBoxDebugMsgBox.setSelected(true);
				checkBoxDebugMain.setSelected(true);
			}else{
				
				checkBoxDebugMsgBox.setSelected(false);
				checkBoxDebugMain.setSelected(false);
			}
		});
		
		paneCoverFilePathWMMIF.getChildren().addAll(labelCoverFilePathWMMIF,
													textCoverFilePathWMMIF,
													buttonBrowseCoverFilePathWMMIF);
		paneStegoFilePathWMMIF.getChildren().addAll(labelStegoFilePathWMMIF,
													textStegoFilePathWMMIF,
													buttonBrowseStegoFilePathWMMIF);

		vboxWMPaneMIF.getChildren().addAll(paneCoverFilePathWMMIF, paneStegoFilePathWMMIF);
	
		//// Create a grid pane and two columns. 
		//// one for image view and another for 3x3 text matrix. 
		GridPane gridPaneWMMIF=new GridPane();
		gridPaneWMMIF.setHgap(10);
		gridPaneWMMIF.setVgap(10);
		gridPaneWMMIF.setPadding(new Insets(0, 10, 0, 10));
		imageViewWMMIF=new ImageView();
		imageViewWMMIF.setPreserveRatio(true);
		imageViewWMMIF.setSmooth(true);
		imageViewWMMIF.setCache(true);
		imageViewWMMIF.autosize();
		imageScrollPaneWMMIF=new ScrollPane();
		imageScrollPaneWMMIF.setPrefSize(550, 550);
		imageScrollPaneWMMIF.setContent(imageViewWMMIF);
	    GridPane.setConstraints(imageScrollPaneWMMIF, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneWMMIF,1);	    
	    gridPaneWMMIF.getChildren().add(imageScrollPaneWMMIF);	    
	    vboxWMPaneMIF.getChildren().add(gridPaneWMMIF);
		
	    //////////////////////////////////////////////////////
	    HBox hboxForWMAndRowColMIF=new HBox();
	    
	    ////Pane for Weight Matrix text boxes.
	    GridPane gridPaneTextFieldWMMIF=new GridPane();
	    gridPaneTextFieldWMMIF.setHgap(10);
	    gridPaneTextFieldWMMIF.setVgap(10);
	    gridPaneTextFieldWMMIF.setPadding(new Insets(0, 10, 0, 10));
	    //// Create 9 text boxes here
		textWMNumbersMIF=new TextField[5][5];		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				textWMNumbersMIF[j][i]=new TextField();
				textWMNumbersMIF[j][i].setPrefColumnCount(2);
			}			
		}
		
		for(int row=0;row<5;row++){
			for(int col=0;col<5;col++){
				GridPane.setConstraints(textWMNumbersMIF[row][col], col, row); // column=0 row=0
			    GridPane.setColumnSpan(textWMNumbersMIF[row][col],1);	    
			    gridPaneTextFieldWMMIF.getChildren().add(textWMNumbersMIF[row][col]);
			}
		}
				
		////Pane for output image width and height
	    GridPane gridPaneForWidthHeightMIF=new GridPane();
	    gridPaneForWidthHeightMIF.setHgap(10);
	    gridPaneForWidthHeightMIF.setVgap(10);
	    gridPaneForWidthHeightMIF.setPadding(new Insets(0, 10, 0, 10));
	    
	    labelOutputImageSizeMIF=new Label("O/P Image");
	    labelWidthMIF=new Label("Width");
		textWidthMIF=new TextField();
		textWidthMIF.setPrefColumnCount(1);
		labelHeightMIF=new Label("Height");
		textHeightMIF=new TextField();
		textHeightMIF.setPrefColumnCount(1);
		
		GridPane.setConstraints(labelOutputImageSizeMIF, 0, 0); // column=0 row=0
		GridPane.setColumnSpan(labelOutputImageSizeMIF, 1);
		
		GridPane.setConstraints(labelWidthMIF, 0, 1); // column=0 row=0
		GridPane.setConstraints(textWidthMIF, 0, 2); // column=0 row=0
		GridPane.setConstraints(labelHeightMIF, 0, 3); // column=0 row=0			
		GridPane.setConstraints(textHeightMIF, 0, 4); // column=0 row=0
	    
		ScrollPane scrollPaneForWidthHeightMIF=new ScrollPane();
		scrollPaneForWidthHeightMIF.setPadding(new Insets(10,10,10,10));
		scrollPaneForWidthHeightMIF.setContent(gridPaneForWidthHeightMIF);
		
		gridPaneForWidthHeightMIF.getChildren().addAll(labelOutputImageSizeMIF,
													   labelWidthMIF,
													   textWidthMIF,
													   labelHeightMIF,
													   textHeightMIF);
	    		
	    hboxForWMAndRowColMIF.getChildren().addAll(gridPaneTextFieldWMMIF,
	    		                                   scrollPaneForWidthHeightMIF);
	    //////////////////////////////////////////////////////
		//// Add the rotation label and Combo box
		FlowPane paneRotationMIF=new FlowPane();
		labelRotationMIF=new Label("Round");
		comboRotationMIF=new ComboBox<String>();
		comboRotationMIF.getItems().addAll("1","2","3","4","5","6","7","8",
				                           "9","10","11","12","13","14","15","16");
		comboRotationMIF.setValue("1"); //// Set default to 16 
		
		labelColumnMIF=new Label("Col");
		comboColumnMIF=new ComboBox<String>();
		comboColumnMIF.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
		comboColumnMIF.setValue("5");
		labelRowMIF=new Label("Row");;
		comboRowMIF=new ComboBox<String>();
		comboRowMIF.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
		comboRowMIF.setValue("5");
		
		paneRotationMIF.setHgap(5);
		paneRotationMIF.getChildren().addAll(labelRotationMIF,comboRotationMIF,
										  	 labelColumnMIF, comboColumnMIF,
										  	 labelRowMIF, comboRowMIF, checkBoxLogWMMIF);
				
		
		/////////////////////////////////////////////////////
		checkBoxMIFMIF=new CheckBox("Matrix Index File");
		checkBoxMIFMIF.setSelected(false);	
		
		checkBoxMIFMIF.setOnAction((e)->{
			
			//// If MIF is selected
			if(checkBoxMIFMIF.isSelected()){
				//// Check if image file exits
				if(bufferedImageWMMIF==null){
					MessageBox("Select an image file first.");
					checkBoxMIFMIF.setSelected(false);
					return;
				}	
				//// Clear the data and info pixels
				selectionMatrixMIF.clearDataPixels();
				selectionMatrixMIF.clearInfoPixels();	
							
				//// select the 3x3 data matrix for MIF
				selectionMatrixMIF.setPixelAsDataPixel(0,0);
				selectionMatrixMIF.setPixelAsDataPixel(0,1);
				selectionMatrixMIF.setPixelAsDataPixel(0,2);
				selectionMatrixMIF.setPixelAsDataPixel(1,0);
				selectionMatrixMIF.setPixelAsDataPixel(1,1);
				selectionMatrixMIF.setPixelAsDataPixel(1,2);
				selectionMatrixMIF.setPixelAsDataPixel(2,0);
				selectionMatrixMIF.setPixelAsDataPixel(2,1);
				selectionMatrixMIF.setPixelAsDataPixel(2,2);
								
				//// Set rounds to 6
				setRoundWMMIF(6);  
				//// Set the maximum row and column
				selectionMatrixMIF.setMaxColAndRow();
				
				//// Set the output file size
				setOutputImageSizeWMMIF();
				
				if(selectionMatrixMIF.getMaxColumn() != selectionMatrixMIF.getMaxRow()){
					selectionMatrixMIF.clearDataPixels();
				}
				
				//// Call the pixel event occurs
				int wmValue=1;
				for(Pixel pxl:selectionMatrixMIF.dataPixelMatrix){			
					textWMNumbersMIF[pxl.getY()][pxl.getX()].setText(""+wmValue);
					wmValue++;
					if(wmValue>8){
						wmValue=5;
					}
				}				
				
				
			}else{ //// If MIF is not selected
				
				//// Clear the WM text fields 
				for(Pixel pxl:selectionMatrixMIF.dataPixelMatrix){			
					textWMNumbersMIF[pxl.getY()][pxl.getX()].setText("");
				}	
				
				//// Clear the 3x3 data matrix for MIF
				selectionMatrixMIF.clearPixelAsDataPixel(0,0);
				selectionMatrixMIF.clearPixelAsDataPixel(0,1);
				selectionMatrixMIF.clearPixelAsDataPixel(0,2);
				selectionMatrixMIF.clearPixelAsDataPixel(1,0);
				selectionMatrixMIF.clearPixelAsDataPixel(1,1);
				selectionMatrixMIF.clearPixelAsDataPixel(1,2);
				selectionMatrixMIF.clearPixelAsDataPixel(2,0);
				selectionMatrixMIF.clearPixelAsDataPixel(2,1);
				selectionMatrixMIF.clearPixelAsDataPixel(2,2);
				
				//// Clear the data and info pixels
				selectionMatrixMIF.clearDataPixels();
				selectionMatrixMIF.clearInfoPixels();				
				//// Clear the output file size
				textWidthMIF.setText("0");
				textHeightMIF.setText("0");
				
				if(selectionMatrixMIF.getMaxColumn() != selectionMatrixMIF.getMaxRow()){
					selectionMatrixMIF.clearDataPixels();
				}				
				//// Set rounds to 0
				setRoundWMMIF(0);
				//// Set the maximum row and column
				comboColumnMIF.setValue("0");
				comboRowMIF.setValue("0");
			}
		});  
		////////////////////////////////////////////////////////

		VBox vboxInputPaneWMMIF=new VBox();
		
		Label labelMatrixWMMIF=new Label("Matrix");
		
		//// Create a SelectionBoard object here.
		selectionBoardVBoxMIF=new VBox();
		selectionMatrixMIF=new SelectionMatrix(this,selectionBoardVBoxMIF);
		selectionMatrixMIF.createSelectionBoard();
				
		vboxInputPaneWMMIF.getChildren().addAll(labelMatrixWMMIF,
											 hboxForWMAndRowColMIF,
											 new Label(),
											 paneRotationMIF,
											 new Label(),
											 checkBoxMIFMIF,
											 new Label(),
											 selectionBoardVBoxMIF);
		
		inputScrollPaneWMMIF=new ScrollPane();
		inputScrollPaneWMMIF.setPadding(new Insets(10, 10, 10, 10));
		inputScrollPaneWMMIF.setContent(vboxInputPaneWMMIF);
		inputScrollPaneWMMIF.setPrefSize(380, 550);
		inputScrollPaneWMMIF.setFitToWidth(true);
		
		
		GridPane.setConstraints(inputScrollPaneWMMIF, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(inputScrollPaneWMMIF,1);	    
	    gridPaneWMMIF.getChildren().add(inputScrollPaneWMMIF);
	    
	    FlowPane flowPaneButtonsWMMIF=new FlowPane();
	    flowPaneButtonsWMMIF.setHgap(50);
	    flowPaneButtonsWMMIF.setPadding(new Insets(10,0,0,0));
	    flowPaneButtonsWMMIF.setAlignment(Pos.CENTER);
	    
	    buttonEmbedWMMIF=new Button("Embed");
	    buttonEmbedWMMIF.setOnAction((e)->{
	    	
	    	COLMIF=Integer.parseInt(comboColumnMIF.getValue());
	    	ROWMIF=Integer.parseInt(comboRowMIF.getValue());
	    	
	    	//// Initializes the ProjectWM object here. ///////////////////////////
	    	//// Populate maxRow and maxCol variables
	       	int[][] WeightMatrix=new int[ROWMIF][COLMIF];
			int ii,jj;		
			
			for(ii=0;ii<ROWMIF;ii++){  
				for(jj=0;jj<COLMIF;jj++){
					if(selectionMatrixMIF.isDataPixel(jj,ii)){
						WeightMatrix[ii][jj]=Integer.parseInt(textWMNumbersMIF[ii][jj].getText().trim());
					}else{
						WeightMatrix[ii][jj]=0;
					}			
				}			
			}
			
			//// Create a Matrix object here and assign the initial matrix
			WTMatrix matrix1=new WTMatrix(WeightMatrix,ROWMIF,COLMIF,checkBoxMIFMIF.isSelected());
		
			/////////// Creating a task for Embedding //////////////////////////////
			projectWMObjectMIF=new ProjectMIF(this,coverFileNameMIF,bufferedImageWMMIF,
					   Integer.parseInt(comboRotationMIF.getValue()),
					   COLMIF,
					   ROWMIF,
					   textAreaLogging,this,selectionMatrixMIF,
					   checkBoxMIFMIF.isSelected(),checkBoxLogWMMIF.isSelected());
			//// Call the embedding function
			projectWMObjectMIF.InitializeEmbeddingMIF(textCoverFilePathWMMIF.getText().trim(), 
	    			                           textStegoFilePathWMMIF.getText().trim(), 
	    			                           matrix1);			
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(projectWMObjectMIF.progressProperty());
			labelProgress.textProperty().bind(projectWMObjectMIF.messageProperty());
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(projectWMObjectMIF);
			}catch(Exception ex){
				MessageBox("Exception Occured During Embedding Process!!!");
				return;
			}			
			//////////////////////////////////////////////////////////////////////////
			
	    });
	    
		buttonExtractWMMIF=new Button("Extract");				
		buttonExtractWMMIF.setOnAction((e)->{
			
			COLMIF=Integer.parseInt(comboColumnMIF.getValue());
			ROWMIF=Integer.parseInt(comboRowMIF.getValue());			
			
			//// Initializes the ProjectWM object here. //////////////////////////
			
			//////////////////////////////////////////////////////////////////////
          	           	
			//// Prepare the weight matrix			
			int ii,jj;
			int[][] WeightMatrix=new int[ROWMIF][COLMIF];
			
			for(ii=0;ii<ROWMIF;ii++){
				for(jj=0;jj<COLMIF;jj++){
					if(selectionMatrixMIF.isDataPixel(jj,ii)){
						WeightMatrix[ii][jj]=Integer.parseInt(textWMNumbersMIF[ii][jj].getText().trim());
					}else{
						WeightMatrix[ii][jj]=0;
					} 		
				}			
			}
			
			//// Create a Matrix object here and assign the initial matrix
			WTMatrix matrix2=new WTMatrix(WeightMatrix,ROWMIF,COLMIF,checkBoxMIFMIF.isSelected());
			
			///////// Create a task for Extraction process ///////////////////////
			projectWMObjectMIF=new ProjectMIF(this,coverFileNameMIF,bufferedImageWMMIF,
					   Integer.parseInt(comboRotationMIF.getValue()),
					   COLMIF,
					   ROWMIF,
					   textAreaLogging,this,selectionMatrixMIF,
					   checkBoxMIFMIF.isSelected(),checkBoxLogWMMIF.isSelected());
			
			//// Calling Extraction Process
			projectWMObjectMIF.InitializeExtractionMIF(textCoverFilePathWMMIF.getText().trim(),matrix2);
			//// Start the embedding thread ////////////
			progressBar.progressProperty().bind(projectWMObjectMIF.progressProperty());
			labelProgress.textProperty().bind(projectWMObjectMIF.messageProperty());
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(projectWMObjectMIF);
			}catch(Exception ex){
				MessageBox("Exception Occured During Extraction Process!!!");
				return;
			}			
			////////////////////////////////////////////////////////////////////////
					
		});
				
		flowPaneButtonsWMMIF.getChildren().addAll(buttonEmbedWMMIF,buttonExtractWMMIF);
		vboxWMPaneMIF.getChildren().add(flowPaneButtonsWMMIF);		
		
		buttonBrowseCoverFilePathWMMIF.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	          
	        ////Show open file dialog
	        file = fileChooser.showOpenDialog(null);
	        ////Display the selected file in the image view           
	        try {
	        	bufferedImageWMMIF = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImageWMMIF, null);
	            imageViewWMMIF.setImage(image);
	            textCoverFilePathWMMIF.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		});
		buttonBrowseStegoFilePathWMMIF.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	          
	        ////Show open file dialog
	        file = fileChooser.showOpenDialog(null);
	        ////Display the selected file in the image view           
	        try {
	        	bufferedImageWMMIF = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImageWMMIF, null);
	            imageViewWMMIF.setImage(image);
	            textCoverFilePathWMMIF.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		});
		////////// START: textCoverFilePathWM and imageScrollPaneWM Drag and Drop   ////////////////////

		inputScrollPaneWMMIF.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
				
		textCoverFilePathWMMIF.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textCoverFilePathWMMIF.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathWMMIF.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    bufferedImageWMMIF = ImageIO.read(file);
	                    image = SwingFXUtils.toFXImage(bufferedImageWMMIF, null);
	                    imageViewWMMIF.setImage(image);	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		imageScrollPaneWMMIF.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		imageScrollPaneWMMIF.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            int count=0;
	            for (File file:db.getFiles()) {	            	
	            	if(count==0){
	            		String saveFilePath = file.getAbsolutePath();
		                textFilePath.setText(saveFilePath);
		                try{
		                	//// Set the image in the image view
		                    file=new File(saveFilePath);
		                    bufferedImageWMMIF = ImageIO.read(file);
		                    image = SwingFXUtils.toFXImage(bufferedImageWMMIF, null);
		                    imageViewWMMIF.setImage(image);
		                    textCoverFilePathWMMIF.setText(file.getAbsolutePath());
		                  
		                    coverFileNameMIF=file;
		                }catch(Exception ex){
		                	ex.printStackTrace();
		                	return;
		                }
	            		count++;
	            	}else if(count==1){  
	            		String secretFilePath = file.getAbsolutePath();
		            	textStegoFilePathWMMIF.setText(secretFilePath);	            	
		               	try{
			            	BufferedImage image = ImageIO.read(new File(textStegoFilePathWMMIF.getText().trim()));
			               	if(Integer.parseInt(textHeightMIF.getText().trim()) * Integer.parseInt(textWidthMIF.getText().trim()) == 
			            	   image.getHeight() * image.getWidth()){
			            		textWidthMIF.setText(""+image.getWidth());
								textHeightMIF.setText(""+image.getHeight());
								image=null;
			            	}						
		            	}catch(Exception ex){}
	            		count++;
	            	}	            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		inputScrollPaneWMMIF.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String secretFilePath = file.getAbsolutePath();
	            	textStegoFilePathWMMIF.setText(secretFilePath);	            	
	            	///////////////////////////////////////////////////
	            	try{
		            	BufferedImage image = ImageIO.read(new File(textStegoFilePathWMMIF.getText().trim()));
		               	if(Integer.parseInt(textHeightMIF.getText().trim()) * Integer.parseInt(textWidthMIF.getText().trim()) == 
		            	   image.getHeight() * image.getWidth()){
		            		textWidthMIF.setText(""+image.getWidth());
							textHeightMIF.setText(""+image.getHeight());
							image=null;
		            	}						
	            	}catch(Exception ex){}	              	
	            	///////////////////////////////////////////////////
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		//////////END: textCoverFilePathWM and imageScrollPaneWM Drag and Drop   /////////////
		    
		tabWeightMatrixMIF.setContent(vboxWMPaneMIF);
	
		COLMIF=0;
		ROWMIF=0;
		//strOutputFilePath="";
		coverFileNameMIF=null;
	
	}//// End InitializeWeightMatrixMIFTab
	
	
	void InitializeHammingCodeTab(){
		
		vboxPaneHamming=new VBox();
		//// Cover Image Text Field
		paneCoverFilePathHammingCode=new FlowPane();
		paneCoverFilePathHammingCode.setPadding(new Insets(10));
		paneCoverFilePathHammingCode.setHgap(10);
		paneCoverFilePathHammingCode.setAlignment(Pos.CENTER);
		labelCoverFilePathHammingCode=new Label("Cover File Path");
		textCoverFilePathHammingCode=new TextField();
		textCoverFilePathHammingCode.setPrefColumnCount(60);
		buttonBrowseCoverFilePathHammingCode=new Button("Browse"); 
		buttonBrowseCoverFilePathHammingCode.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);	          
	        ////Show open file dialog
	        if((file = fileChooser.showOpenDialog(null))==null){
	        	return;
	        }
	        ////Display the selected file in the image view           
	        try {
	        	bufferedImageHammingCode = ImageIO.read(file);
	            Image image = SwingFXUtils.toFXImage(bufferedImageHammingCode, null);
	            imageViewCoverImageHammingCode.setImage(image);
	            textCoverFilePathHammingCode.setText(file.getAbsolutePath());
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		});
		
		paneCoverFilePathHammingCode.getChildren().addAll(labelCoverFilePathHammingCode,
													  textCoverFilePathHammingCode,
													  buttonBrowseCoverFilePathHammingCode);
		//// Stego Image Text Field
		paneSecretFilePathHammingCode=new FlowPane();
		paneSecretFilePathHammingCode.setPadding(new Insets(10));
		paneSecretFilePathHammingCode.setHgap(10);
		paneSecretFilePathHammingCode.setAlignment(Pos.CENTER);
		labelSecretFilePathHammingCode=new Label("Stego File Path");
		textSecretFilePathHammingCode=new TextField();
		textSecretFilePathHammingCode.setPrefColumnCount(60);
		buttonBrowseSecretFilePathHammingCode=new Button("Browse");
		buttonBrowseSecretFilePathHammingCode.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	       	ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	        ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	        fileChooser.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);	          
	        ////Show open file dialog
	        if((file = fileChooser.showOpenDialog(null))==null){
	        	return;
	        }
	        ////Display the selected file in the image view  
	        textSecretFilePathHammingCode.setText(file.getAbsolutePath());
	    });
		
		paneSecretFilePathHammingCode.getChildren().addAll(labelSecretFilePathHammingCode,
				textSecretFilePathHammingCode, buttonBrowseSecretFilePathHammingCode);
		
		vboxPaneHamming.getChildren().addAll(paneCoverFilePathHammingCode, 
											 paneSecretFilePathHammingCode);
		
		//// Split View: one for the cover image and other for the settings pane
		GridPane gridPaneSplitView=new GridPane();
		gridPaneSplitView.setHgap(10);
		gridPaneSplitView.setVgap(10);
		gridPaneSplitView.setPadding(new Insets(0, 10, 0, 10));
		vboxPaneHamming.getChildren().add(gridPaneSplitView);
		
		
		imageViewCoverImageHammingCode=new ImageView();
		imageViewCoverImageHammingCode.setPreserveRatio(true);
		imageViewCoverImageHammingCode.setSmooth(true);
		imageViewCoverImageHammingCode.setCache(true);
		imageViewCoverImageHammingCode.autosize();
		imageScrollPaneHammingCode=new ScrollPane();
		imageScrollPaneHammingCode.setPrefSize(550, 550);
		imageScrollPaneHammingCode.setContent(imageViewCoverImageHammingCode);
	    GridPane.setConstraints(imageScrollPaneHammingCode, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneHammingCode,1);	    
	    gridPaneSplitView.getChildren().add(imageScrollPaneHammingCode);	    
	    
	    inputScrollPaneHammingCode=new ScrollPane();
	    inputScrollPaneHammingCode.setPrefSize(450, 550);
	    inputScrollPaneHammingCode.setPadding(new Insets(10,10,10,10));
	    GridPane.setConstraints(inputScrollPaneHammingCode, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(inputScrollPaneHammingCode,1);	    
	    gridPaneSplitView.getChildren().add(inputScrollPaneHammingCode);
	    
	    //// LSB Selection CheckBoxes
	    VBox vboxInputPaneHammingCode=new VBox();
	    vboxInputPaneHammingCode.setPrefWidth(400);
	    
	    paneLSBSelectionHammingCode=new FlowPane();
	    paneLSBSelectionHammingCode.setPadding(new Insets(10));
	    paneLSBSelectionHammingCode.setHgap(10);
	    paneLSBSelectionHammingCode.setAlignment(Pos.CENTER);
	    paneLSBSelectionHammingCode.setStyle("-fx-border-color: rgb(180,180,180)");
	    
		labelLSBSelectionHammingCode=new Label("LSB Selection");
		checkboxLSBHammingCode=new CheckBox("LSB");
		checkboxLSBHammingCode.setSelected(true);
		checkboxLSBPlus1HammingCode=new CheckBox("LSB+1");
		checkboxLSBPlus2HammingCode=new CheckBox("LSB+2");
		paneLSBSelectionHammingCode.getChildren().addAll(labelLSBSelectionHammingCode,
														 checkboxLSBPlus2HammingCode,
														 checkboxLSBPlus1HammingCode,
														 checkboxLSBHammingCode);	
		
		vboxInputPaneHammingCode.getChildren().add(paneLSBSelectionHammingCode);
				
		//// different Hamming Codes
		paneTypeOfHammingCode=new FlowPane();
		paneTypeOfHammingCode.setPadding(new Insets(10));
		paneTypeOfHammingCode.setHgap(10);
		paneTypeOfHammingCode.setAlignment(Pos.CENTER);
		paneTypeOfHammingCode.setStyle("-fx-border-color: rgb(180,180,180)");
	    
		labelTypeOfHammingCode=new Label("Hamming Code");
		comboTypeOfHammingCode=new ComboBox<String>();
		comboTypeOfHammingCode.getItems().addAll("( 3 , 1 )","( 7 , 4 )","( 15 , 11 )","( 31 , 26 )");
		comboTypeOfHammingCode.setValue("( 7 , 4 )");
		
		comboTypeOfHammingCode.setOnAction((e)->{			
			String comboValue=comboTypeOfHammingCode.getValue();
			comboValue=comboValue.replaceAll(" ","").replaceAll("\\(","").replaceAll("\\)","");
			String[] values=comboValue.split(",");
			
			comboBlockColumnHammingCode.setValue(""+values[0]);
			comboBlockRowHammingCode.setValue(""+values[0]);
			comboBlockColumnHammingCode.setDisable(true);
		});
		
		paneTypeOfHammingCode.getChildren().addAll(labelTypeOfHammingCode,comboTypeOfHammingCode);
		vboxInputPaneHammingCode.getChildren().addAll(new Label(""), paneTypeOfHammingCode);
		
		paneRowColumnOfHammingCode=new FlowPane();
		paneRowColumnOfHammingCode.setPadding(new Insets(10));
		paneRowColumnOfHammingCode.setHgap(10);
		paneRowColumnOfHammingCode.setAlignment(Pos.CENTER);
		paneRowColumnOfHammingCode.setStyle("-fx-border-color: rgb(180,180,180)");
		labelBlockColumnHammingCode=new Label("Block Column");
		comboBlockColumnHammingCode=new ComboBox<String>();
		comboBlockColumnHammingCode.getItems().addAll("1","2","3","4","5","6","7","8","9","10",
		"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27",
		"28","29","30","31");
		comboBlockColumnHammingCode.setValue("7");
		labelBlockRowHammingCode=new Label("Block Row");
		comboBlockRowHammingCode=new ComboBox<String>();
		comboBlockRowHammingCode.getItems().addAll("1","2","3","4","5","6","7","8","9","10",
				"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27",
				"28","29","30","31");
		comboBlockRowHammingCode.setValue("7");
		paneRowColumnOfHammingCode.getChildren().addAll(labelBlockColumnHammingCode,
														comboBlockColumnHammingCode,
														labelBlockRowHammingCode,
														comboBlockRowHammingCode);												
		vboxInputPaneHammingCode.getChildren().addAll(new Label(""), paneRowColumnOfHammingCode);
		
		
		//////////////////   Parity Check Controls //////////////////////////////////
		paneParityCheck=new FlowPane();
		paneParityCheck.setPadding(new Insets(10));
		paneParityCheck.setHgap(10);
		paneParityCheck.setAlignment(Pos.CENTER);
		paneParityCheck.setStyle("-fx-border-color: rgb(180,180,180)");
		labelParityCheck=new Label("Parity Check");
		comboParityCheck=new ComboBox<String>();
		comboParityCheck.getItems().addAll("Odd Parity","Even Parity");
		comboParityCheck.setValue("Odd Parity");
		checkboxLogHC=new CheckBox("Log");
		checkboxLogHC.setSelected(false);		
		paneParityCheck.getChildren().addAll(labelParityCheck,comboParityCheck,checkboxLogHC);												
		vboxInputPaneHammingCode.getChildren().addAll(new Label(""), paneParityCheck);
		///////////////////////////////////////////////////////////////////////////////

		
		paneForEmbedExtractButtonHammingCode=new FlowPane();
		paneForEmbedExtractButtonHammingCode.setPadding(new Insets(10));
		paneForEmbedExtractButtonHammingCode.setHgap(50);
		paneForEmbedExtractButtonHammingCode.setAlignment(Pos.CENTER);
		
		buttonEmbedHammingCode=new Button("Embed");
		buttonExtractHammingCode=new Button("Extract"); 
		
		buttonEmbedHammingCode.setOnAction((e)->{
			
			bitSelection=0;
			bitSelection += (checkboxLSBHammingCode.isSelected())?1:0;
			bitSelection += (checkboxLSBPlus1HammingCode.isSelected())?2:0;
			bitSelection += (checkboxLSBPlus2HammingCode.isSelected())?4:0;		
			
			//// Create an ProjectHammingCode object for embedding
			ProjectHammingCode embedHammingCodeObj=
			new ProjectHammingCode("Embed",
					               textCoverFilePathHammingCode.getText().trim(),
					               textSecretFilePathHammingCode.getText().trim(),
					               comboTypeOfHammingCode.getValue(),
					               Integer.parseInt(comboBlockRowHammingCode.getValue()),
					               Integer.parseInt(comboBlockColumnHammingCode.getValue()),
					               bitSelection,
					               comboParityCheck.getValue(),
					               checkboxLogHC.isSelected()?true:false);
			
			//// Start the embedding thread ////////////
			progressBar.progressProperty().bind(embedHammingCodeObj.progressProperty());
			labelProgress.textProperty().bind(embedHammingCodeObj.messageProperty());
			textWidthTools.textProperty().bind(embedHammingCodeObj.secretImageSizeProperty());
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			   public Thread newThread(Runnable runnable) {
			      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			      thread.setDaemon(true);
			      return thread;
			   }  
			});
			//// Run the executor
			try{
				executor.execute(embedHammingCodeObj);				
			}catch(Exception ex){
				MessageBox("Exception Occured During Embedding Process!!!");
				return;
			}
			
		});
		
		buttonExtractHammingCode.setOnAction((e)->{
			
			bitSelection=0;
			bitSelection  = (checkboxLSBHammingCode.isSelected())?1:0;
			bitSelection += (checkboxLSBPlus1HammingCode.isSelected())?2:0;
			bitSelection += (checkboxLSBPlus2HammingCode.isSelected())?4:0;		
			
			//// Create an ProjectHammingCode object for embedding
			ProjectHammingCode extractHammingCodeObj=
			new ProjectHammingCode("Extract",
								   textCoverFilePathHammingCode.getText().trim(),
								   textSecretFilePathHammingCode.getText().trim(),
						           comboTypeOfHammingCode.getValue(),
						           Integer.parseInt(comboBlockRowHammingCode.getValue()),
						           Integer.parseInt(comboBlockColumnHammingCode.getValue()),
						           bitSelection,
						           comboParityCheck.getValue(),
						           checkboxLogHC.isSelected()?true:false);

			//// Start the embedding thread ////////////
			progressBar.progressProperty().bind(extractHammingCodeObj.progressProperty());
			labelProgress.textProperty().bind(extractHammingCodeObj.messageProperty());
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			   public Thread newThread(Runnable runnable) {
			      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			      thread.setDaemon(true);
			      return thread;
			   }  
			});
			//// Run the executor
			try{
				executor.execute(extractHammingCodeObj);
			}catch(Exception ex){
				MessageBox("Exception Occured During Extraction Process!!!");
				return;
			}	
		});
		
		paneForEmbedExtractButtonHammingCode.getChildren().addAll(buttonEmbedHammingCode, new Label(""), buttonExtractHammingCode);
		vboxPaneHamming.getChildren().add(paneForEmbedExtractButtonHammingCode);
			
		textSecretFilePathHammingCode.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
	    inputScrollPaneHammingCode.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
	    textSecretFilePathHammingCode.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {	            	
	            	String saveFilePath = file.getAbsolutePath();
	            	textSecretFilePathHammingCode.setText(saveFilePath);	                            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
			 
	    inputScrollPaneHammingCode.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {	            	
	            	String saveFilePath = file.getAbsolutePath();
	            	textSecretFilePathHammingCode.setText(saveFilePath);	                            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
	    textCoverFilePathHammingCode.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    
	    imageScrollPaneHammingCode.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
	    imageScrollPaneHammingCode.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            int count=0;
	            for (File file:db.getFiles()) {	            	
	            	if(count==0){
	            		String saveFilePath = file.getAbsolutePath();
		                textFilePath.setText(saveFilePath);
		                try{
		                	//// Set the image in the image view
		                    file=new File(saveFilePath);
		                    bufferedImageHammingCode = ImageIO.read(file);
		                    image = SwingFXUtils.toFXImage(bufferedImageHammingCode, null);
		                    imageViewCoverImageHammingCode.setImage(image);
		                    textCoverFilePathHammingCode.setText(file.getAbsolutePath());		                    
		                    coverFileName=file;
		                }catch(Exception ex){
		                	ex.printStackTrace();
		                	return;
		                }
	            		count++;
	            	}else if(count==1){  
	            		String secretFilePath = file.getAbsolutePath();
	            		textSecretFilePathHammingCode.setText(secretFilePath);	            	
	            		count++;
	            	}	            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
	    textCoverFilePathHammingCode.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            int count=0;
	            for (File file:db.getFiles()) {	            	
	            	if(count==0){
	            		String saveFilePath = file.getAbsolutePath();
		                textFilePath.setText(saveFilePath);
		                try{
		                	//// Set the image in the image view
		                    file=new File(saveFilePath);
		                    bufferedImageHammingCode = ImageIO.read(file);
		                    image = SwingFXUtils.toFXImage(bufferedImageHammingCode, null);
		                    imageViewCoverImageHammingCode.setImage(image);
		                    textCoverFilePathHammingCode.setText(file.getAbsolutePath());		                    
		                    coverFileName=file;
		                }catch(Exception ex){
		                	ex.printStackTrace();
		                	return;
		                }
	            		count++;
	            	}else if(count==1){  
	            		String secretFilePath = file.getAbsolutePath();
	            		textSecretFilePathHammingCode.setText(secretFilePath);	            	
	            		count++;
	            	}	            	 	                	
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
	    inputScrollPaneHammingCode.setContent(vboxInputPaneHammingCode);
		tabHammingCode.setContent(vboxPaneHamming);
	}
	  	
	
	//// Initialize ICCDC Tab Components
	void InitializeICCDCTab(){
		
		VBox vboxICCDCPane=new VBox();
		//// Create a flow pane for cover file path controls
		FlowPane paneCoverFilePathICCDC=new FlowPane();
		paneCoverFilePathICCDC.setPadding(new Insets(10));
		paneCoverFilePathICCDC.setHgap(10);
		paneCoverFilePathICCDC.setAlignment(Pos.CENTER);		
		//// Create and add the file path controls in the flow pane
		Label labelCoverFilePathICCDC=new Label("Cover File Path");
		TextField textCoverFilePathICCDC=new TextField();
		textCoverFilePathICCDC.setPrefColumnCount(40);
		Label labelCoverFileWidthICCDC=new Label("Width");
		TextField textCoverFileWidthICCDC=new TextField();
		textCoverFileWidthICCDC.setPrefColumnCount(5);
		Label labelCoverFileHeightICCDC=new Label("Height");
		TextField textCoverFileHeightICCDC=new TextField();
		textCoverFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for secret file path controls
		FlowPane paneSecretFilePathICCDC=new FlowPane();
		paneSecretFilePathICCDC.setPadding(new Insets(10));
		paneSecretFilePathICCDC.setHgap(10);
		paneSecretFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelSecretFilePathICCDC=new Label("Secret File Path  ");
		TextField textSecretFilePathICCDC=new TextField();
		textSecretFilePathICCDC.setPrefColumnCount(40);
		Label labelSecretFileWidthICCDC=new Label("Width");
		TextField textSecretFileWidthICCDC=new TextField();
		textSecretFileWidthICCDC.setPrefColumnCount(5);
		Label labelSecretFileHeightICCDC=new Label("Height");
		TextField textSecretFileHeightICCDC=new TextField();
		textSecretFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for stego file path controls
		FlowPane paneStegoFilePathICCDC=new FlowPane();
		paneStegoFilePathICCDC.setPadding(new Insets(10));
		paneStegoFilePathICCDC.setHgap(10);
		paneStegoFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelStegoFilePathICCDC=new Label("Stego File Path   ");
		TextField textStegoFilePathICCDC=new TextField();
		textStegoFilePathICCDC.setPrefColumnCount(40);
		Label labelStegoFileWidthICCDC=new Label("Width");
		TextField textStegoFileWidthICCDC=new TextField();
		textStegoFileWidthICCDC.setPrefColumnCount(5);
		Label labelStegoFileHeightICCDC=new Label("Height");
		TextField textStegoFileHeightICCDC=new TextField();
		textStegoFileHeightICCDC.setPrefColumnCount(5);
						
		paneCoverFilePathICCDC.getChildren().addAll(labelCoverFilePathICCDC,
												 textCoverFilePathICCDC,
												 labelCoverFileWidthICCDC,
												 textCoverFileWidthICCDC,
												 labelCoverFileHeightICCDC,
												 textCoverFileHeightICCDC);
		paneSecretFilePathICCDC.getChildren().addAll(labelSecretFilePathICCDC,
												 textSecretFilePathICCDC,
												 labelSecretFileWidthICCDC,
												 textSecretFileWidthICCDC,
												 labelSecretFileHeightICCDC,
												 textSecretFileHeightICCDC);
		paneStegoFilePathICCDC.getChildren().addAll(labelStegoFilePathICCDC,
				 								 textStegoFilePathICCDC,
				 								 labelStegoFileWidthICCDC,
												 textStegoFileWidthICCDC,
												 labelStegoFileHeightICCDC,
												 textStegoFileHeightICCDC);

		vboxICCDCPane.getChildren().addAll(paneCoverFilePathICCDC, 
				                           paneSecretFilePathICCDC,
				                           paneStegoFilePathICCDC);
	
		//// Create a grid pane and two columns. 
		//// one for cover image view and another for output image view. 
		GridPane gridPaneICCDC=new GridPane();
		gridPaneICCDC.setHgap(10);
		gridPaneICCDC.setVgap(10);
		gridPaneICCDC.setPadding(new Insets(0, 10, 0, 10));
		
		ImageView imageViewCoverFileICCDC=new ImageView();
		imageViewCoverFileICCDC.setPreserveRatio(true);
		imageViewCoverFileICCDC.setSmooth(true);
		imageViewCoverFileICCDC.setCache(true);
		imageViewCoverFileICCDC.autosize();
		ScrollPane imageScrollPaneICCDC=new ScrollPane();
		imageScrollPaneICCDC.setPrefSize(950, 550);
		imageScrollPaneICCDC.setContent(imageViewCoverFileICCDC);
	    GridPane.setConstraints(imageScrollPaneICCDC, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneICCDC,1);	    
	    gridPaneICCDC.getChildren().add(imageScrollPaneICCDC);	    
	    vboxICCDCPane.getChildren().add(gridPaneICCDC);
		
	    FlowPane paneForEmbedExtractButtonICCDC=new FlowPane();
	    paneForEmbedExtractButtonICCDC.setPadding(new Insets(10));
	    paneForEmbedExtractButtonICCDC.setHgap(50);
	    paneForEmbedExtractButtonICCDC.setAlignment(Pos.CENTER);
	    Button buttonEmbedICCDC=new Button("Embed");
		Button buttonExtractICCDC=new Button("Extract");
		paneForEmbedExtractButtonICCDC.getChildren().addAll(buttonEmbedICCDC,buttonExtractICCDC);
		vboxICCDCPane.getChildren().add(paneForEmbedExtractButtonICCDC);
	    
		tabICCDC.setContent(vboxICCDCPane);
		
		////////////////////   START: Drag and Drop //////////////////////////////  
		textCoverFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textCoverFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);
	                    
	                    textCoverFileWidthICCDC.setText(""+coverFileBufferedImageICCDC.getWidth());
	                    textCoverFileHeightICCDC.setText(""+coverFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textSecretFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textSecretFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textSecretFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    secretFileBufferedImageICCDC = ImageIO.read(file);
	                    
	                    textSecretFileWidthICCDC.setText(""+secretFileBufferedImageICCDC.getWidth());
	                    textSecretFileHeightICCDC.setText(""+secretFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textStegoFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textStegoFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textStegoFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    stegoFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(stegoFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);
	                    
	                    textStegoFileWidthICCDC.setText(""+stegoFileBufferedImageICCDC.getWidth());
	                    textStegoFileHeightICCDC.setText(""+stegoFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		imageScrollPaneICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		imageScrollPaneICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		////////////////////////   END: Drag And Drop  ///////////////////////////
		    
		buttonEmbedICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			PVD5 PVD5Object1=new PVD5(coverFileBufferedImageICCDC,
					              secretFileBufferedImageICCDC,
					              null,
					              textCoverFilePathICCDC.getText().trim(),
					              "embed",0,0);
			//// StartEmbeddingICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(PVD5Object1.progressProperty());
			labelProgress.textProperty().bind(PVD5Object1.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(PVD5Object1);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Embedding Process!!!");
				return;
			}		
		});
		
		buttonExtractICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			PVD5 PVD5Object2=new PVD5(coverFileBufferedImageICCDC,
										 secretFileBufferedImageICCDC,
								  		 stegoFileBufferedImageICCDC,
								  		 textStegoFilePathICCDC.getText().trim(),
					              		 "extract",
					              		 Integer.parseInt(textSecretFileWidthICCDC.getText().trim()),
					              		 Integer.parseInt(textSecretFileHeightICCDC.getText().trim())
					              		 );
			//// StartExtractionICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(PVD5Object2.progressProperty());
			labelProgress.textProperty().bind(PVD5Object2.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor2 = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor2.execute(PVD5Object2);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Extraction Process!!!");
				return;
			}		
		});
		
		final ContextMenu contextMenuICCDC = new ContextMenu();		
		MenuItem itemBinaryFormatICCDC = new MenuItem("Binary Format");
		itemBinaryFormatICCDC.setOnAction((e)-> {
		    showImageInTextFormat(coverFileBufferedImageICCDC,false);
		});		
		contextMenuICCDC.getItems().addAll(itemBinaryFormatICCDC);		
		imageScrollPaneICCDC.setContextMenu(contextMenuICCDC);	
	    
	}//// End InitializeICCDCTab
	
	////Initialize PVD5Nxt Tab Components
	void InitializePVD5NxtTab(){
		
		VBox vboxICCDCPane=new VBox();
		//// Create a flow pane for cover file path controls
		FlowPane paneCoverFilePathICCDC=new FlowPane();
		paneCoverFilePathICCDC.setPadding(new Insets(10));
		paneCoverFilePathICCDC.setHgap(10);
		paneCoverFilePathICCDC.setAlignment(Pos.CENTER);		
		//// Create and add the file path controls in the flow pane
		Label labelCoverFilePathICCDC=new Label("Cover File Path");
		TextField textCoverFilePathICCDC=new TextField();
		textCoverFilePathICCDC.setPrefColumnCount(40);
		Label labelCoverFileWidthICCDC=new Label("Width");
		TextField textCoverFileWidthICCDC=new TextField();
		textCoverFileWidthICCDC.setPrefColumnCount(5);
		Label labelCoverFileHeightICCDC=new Label("Height");
		TextField textCoverFileHeightICCDC=new TextField();
		textCoverFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for secret file path controls
		FlowPane paneSecretFilePathICCDC=new FlowPane();
		paneSecretFilePathICCDC.setPadding(new Insets(10));
		paneSecretFilePathICCDC.setHgap(10);
		paneSecretFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelSecretFilePathICCDC=new Label("Secret File Path  ");
		TextField textSecretFilePathICCDC=new TextField();
		textSecretFilePathICCDC.setPrefColumnCount(40);
		Label labelSecretFileWidthICCDC=new Label("Width");
		TextField textSecretFileWidthICCDC=new TextField();
		textSecretFileWidthICCDC.setPrefColumnCount(5);
		Label labelSecretFileHeightICCDC=new Label("Height");
		TextField textSecretFileHeightICCDC=new TextField();
		textSecretFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for stego file path controls
		FlowPane paneStegoFilePathICCDC=new FlowPane();
		paneStegoFilePathICCDC.setPadding(new Insets(10));
		paneStegoFilePathICCDC.setHgap(10);
		paneStegoFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelStegoFilePathICCDC=new Label("Stego File Path   ");
		TextField textStegoFilePathICCDC=new TextField();
		textStegoFilePathICCDC.setPrefColumnCount(40);
		Label labelStegoFileWidthICCDC=new Label("Width");
		TextField textStegoFileWidthICCDC=new TextField();
		textStegoFileWidthICCDC.setPrefColumnCount(5);
		Label labelStegoFileHeightICCDC=new Label("Height");
		TextField textStegoFileHeightICCDC=new TextField();
		textStegoFileHeightICCDC.setPrefColumnCount(5);
						
		paneCoverFilePathICCDC.getChildren().addAll(labelCoverFilePathICCDC,
												 textCoverFilePathICCDC,
												 labelCoverFileWidthICCDC,
												 textCoverFileWidthICCDC,
												 labelCoverFileHeightICCDC,
												 textCoverFileHeightICCDC);
		paneSecretFilePathICCDC.getChildren().addAll(labelSecretFilePathICCDC,
												 textSecretFilePathICCDC,
												 labelSecretFileWidthICCDC,
												 textSecretFileWidthICCDC,
												 labelSecretFileHeightICCDC,
												 textSecretFileHeightICCDC);
		paneStegoFilePathICCDC.getChildren().addAll(labelStegoFilePathICCDC,
				 								 textStegoFilePathICCDC,
				 								 labelStegoFileWidthICCDC,
												 textStegoFileWidthICCDC,
												 labelStegoFileHeightICCDC,
												 textStegoFileHeightICCDC);

		vboxICCDCPane.getChildren().addAll(paneCoverFilePathICCDC, 
				                           paneSecretFilePathICCDC,
				                           paneStegoFilePathICCDC);
	
		//// Create a grid pane and two columns. 
		//// one for cover image view and another for output image view. 
		GridPane gridPaneICCDC=new GridPane();
		gridPaneICCDC.setHgap(10);
		gridPaneICCDC.setVgap(10);
		gridPaneICCDC.setPadding(new Insets(0, 10, 0, 10));
		
		ImageView imageViewCoverFileICCDC=new ImageView();
		imageViewCoverFileICCDC.setPreserveRatio(true);
		imageViewCoverFileICCDC.setSmooth(true);
		imageViewCoverFileICCDC.setCache(true);
		imageViewCoverFileICCDC.autosize();
		ScrollPane imageScrollPaneICCDC=new ScrollPane();
		imageScrollPaneICCDC.setPrefSize(950, 550);
		imageScrollPaneICCDC.setContent(imageViewCoverFileICCDC);
	    GridPane.setConstraints(imageScrollPaneICCDC, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneICCDC,1);	    
	    gridPaneICCDC.getChildren().add(imageScrollPaneICCDC);	    
	    vboxICCDCPane.getChildren().add(gridPaneICCDC);
		
	    FlowPane paneForEmbedExtractButtonICCDC=new FlowPane();
	    paneForEmbedExtractButtonICCDC.setPadding(new Insets(10));
	    paneForEmbedExtractButtonICCDC.setHgap(50);
	    paneForEmbedExtractButtonICCDC.setAlignment(Pos.CENTER);
	    Button buttonEmbedICCDC=new Button("Embed");
		Button buttonExtractICCDC=new Button("Extract");
		paneForEmbedExtractButtonICCDC.getChildren().addAll(buttonEmbedICCDC,buttonExtractICCDC);
		vboxICCDCPane.getChildren().add(paneForEmbedExtractButtonICCDC);
	    
		tabPVD5Nxt.setContent(vboxICCDCPane);
		
		////////////////////   START: Drag and Drop //////////////////////////////  
		textCoverFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {  
	            event.consume(); 
	        }
	    });
		textCoverFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);
	                    
	                    textCoverFileWidthICCDC.setText(""+coverFileBufferedImageICCDC.getWidth());
	                    textCoverFileHeightICCDC.setText(""+coverFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textSecretFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textSecretFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textSecretFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    secretFileBufferedImageICCDC = ImageIO.read(file);
	                    
	                    textSecretFileWidthICCDC.setText(""+secretFileBufferedImageICCDC.getWidth());
	                    textSecretFileHeightICCDC.setText(""+secretFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textStegoFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textStegoFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textStegoFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    stegoFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(stegoFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);
	                    
	                    textStegoFileWidthICCDC.setText(""+stegoFileBufferedImageICCDC.getWidth());
	                    textStegoFileHeightICCDC.setText(""+stegoFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		imageScrollPaneICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		imageScrollPaneICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		////////////////////////   END: Drag And Drop  ///////////////////////////
		    
		buttonEmbedICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			PVD5Nxt PVD5Object1=new PVD5Nxt(textCoverFilePathICCDC.getText().trim(),
								  textSecretFilePathICCDC.getText().trim(),
					              null,"embed");
			//// StartEmbeddingICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(PVD5Object1.progressProperty());
			labelProgress.textProperty().bind(PVD5Object1.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(PVD5Object1);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Embedding Process!!!");
				return;
			}		
		});
		
		buttonExtractICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			PVD5 PVD5Object2=new PVD5(coverFileBufferedImageICCDC,
										 secretFileBufferedImageICCDC,
								  		 stegoFileBufferedImageICCDC,
								  		 textStegoFilePathICCDC.getText().trim(),
					              		 "extract",
					              		 Integer.parseInt(textSecretFileWidthICCDC.getText().trim()),
					              		 Integer.parseInt(textSecretFileHeightICCDC.getText().trim())
					              		 );
			//// StartExtractionICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(PVD5Object2.progressProperty());
			labelProgress.textProperty().bind(PVD5Object2.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor2 = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor2.execute(PVD5Object2);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Extraction Process!!!");
				return;
			}		
		});
		
		final ContextMenu contextMenuICCDC = new ContextMenu();		
		MenuItem itemBinaryFormatICCDC = new MenuItem("Binary Format");
		itemBinaryFormatICCDC.setOnAction((e)-> {
		    showImageInTextFormat(coverFileBufferedImageICCDC,false);
		});		
		contextMenuICCDC.getItems().addAll(itemBinaryFormatICCDC);		
		imageScrollPaneICCDC.setContextMenu(contextMenuICCDC);	
	    
	}//// End InitializePVD5NxtTab
	
	void InitializeSubSampleClassTab(){
		
		VBox vboxICCDCPane=new VBox();
		//// Create a flow pane for cover file path controls
		FlowPane paneCoverFilePathICCDC=new FlowPane();
		paneCoverFilePathICCDC.setPadding(new Insets(10));
		paneCoverFilePathICCDC.setHgap(10);
		paneCoverFilePathICCDC.setAlignment(Pos.CENTER);		
		//// Create and add the file path controls in the flow pane
		Label labelCoverFilePathICCDC=new Label("Cover File Path");
		TextField textCoverFilePathICCDC=new TextField();
		textCoverFilePathICCDC.setPrefColumnCount(40);
		Label labelCoverFileWidthICCDC=new Label("Width");
		TextField textCoverFileWidthICCDC=new TextField();
		textCoverFileWidthICCDC.setPrefColumnCount(5);
		Label labelCoverFileHeightICCDC=new Label("Height");
		TextField textCoverFileHeightICCDC=new TextField();
		textCoverFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for secret file path controls
		FlowPane paneSecretFilePathICCDC=new FlowPane();
		paneSecretFilePathICCDC.setPadding(new Insets(10));
		paneSecretFilePathICCDC.setHgap(10);
		paneSecretFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelSecretFilePathICCDC=new Label("Secret File Path  ");
		TextField textSecretFilePathICCDC=new TextField();
		textSecretFilePathICCDC.setPrefColumnCount(40);
		Label labelSecretFileWidthICCDC=new Label("Width");
		TextField textSecretFileWidthICCDC=new TextField();
		textSecretFileWidthICCDC.setPrefColumnCount(5);
		Label labelSecretFileHeightICCDC=new Label("Height");
		TextField textSecretFileHeightICCDC=new TextField();
		textSecretFileHeightICCDC.setPrefColumnCount(5);
		
		//// Create a flow pane for stego file path controls
		FlowPane paneStegoFilePathICCDC=new FlowPane();
		paneStegoFilePathICCDC.setPadding(new Insets(10));
		paneStegoFilePathICCDC.setHgap(10);
		paneStegoFilePathICCDC.setAlignment(Pos.CENTER);
		
		Label labelStegoFilePathICCDC=new Label("Stego File Path   ");
		TextField textStegoFilePathICCDC=new TextField();
		textStegoFilePathICCDC.setPrefColumnCount(40);
		Label labelStegoFileWidthICCDC=new Label("Width");
		TextField textStegoFileWidthICCDC=new TextField();
		textStegoFileWidthICCDC.setPrefColumnCount(5);
		Label labelStegoFileHeightICCDC=new Label("Height");
		TextField textStegoFileHeightICCDC=new TextField();
		textStegoFileHeightICCDC.setPrefColumnCount(5);
						
		paneCoverFilePathICCDC.getChildren().addAll(labelCoverFilePathICCDC,
												 textCoverFilePathICCDC,
												 labelCoverFileWidthICCDC,
												 textCoverFileWidthICCDC,
												 labelCoverFileHeightICCDC,
												 textCoverFileHeightICCDC);
		paneSecretFilePathICCDC.getChildren().addAll(labelSecretFilePathICCDC,
												 textSecretFilePathICCDC,
												 labelSecretFileWidthICCDC,
												 textSecretFileWidthICCDC,
												 labelSecretFileHeightICCDC,
												 textSecretFileHeightICCDC);
		paneStegoFilePathICCDC.getChildren().addAll(labelStegoFilePathICCDC,
				 								 textStegoFilePathICCDC,
				 								 labelStegoFileWidthICCDC,
												 textStegoFileWidthICCDC,
												 labelStegoFileHeightICCDC,
												 textStegoFileHeightICCDC);

		vboxICCDCPane.getChildren().addAll(paneCoverFilePathICCDC, 
				                           paneSecretFilePathICCDC,
				                           paneStegoFilePathICCDC);
	
		//// Create a grid pane and two columns. 
		//// one for cover image view and another for output image view. 
		GridPane gridPaneICCDC=new GridPane();
		gridPaneICCDC.setHgap(10);
		gridPaneICCDC.setVgap(10);
		gridPaneICCDC.setPadding(new Insets(0, 10, 0, 10));
		
		ImageView imageViewCoverFileICCDC=new ImageView();
		imageViewCoverFileICCDC.setPreserveRatio(true);
		imageViewCoverFileICCDC.setSmooth(true);
		imageViewCoverFileICCDC.setCache(true);
		imageViewCoverFileICCDC.autosize();
		ScrollPane imageScrollPaneICCDC=new ScrollPane();
		imageScrollPaneICCDC.setPrefSize(950, 550);
		imageScrollPaneICCDC.setContent(imageViewCoverFileICCDC);
	    GridPane.setConstraints(imageScrollPaneICCDC, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageScrollPaneICCDC,1);	    
	    gridPaneICCDC.getChildren().add(imageScrollPaneICCDC);	    
	    vboxICCDCPane.getChildren().add(gridPaneICCDC);
		
	    FlowPane paneForEmbedExtractButtonICCDC=new FlowPane();
	    paneForEmbedExtractButtonICCDC.setPadding(new Insets(10));
	    paneForEmbedExtractButtonICCDC.setHgap(50);
	    paneForEmbedExtractButtonICCDC.setAlignment(Pos.CENTER);
	    Button buttonEmbedICCDC=new Button("Embed");
		Button buttonExtractICCDC=new Button("Extract");
		paneForEmbedExtractButtonICCDC.getChildren().addAll(buttonEmbedICCDC,buttonExtractICCDC);
		vboxICCDCPane.getChildren().add(paneForEmbedExtractButtonICCDC);
	    
		tabSubSampleClass.setContent(vboxICCDCPane);
		
		////////////////////   START: Drag and Drop //////////////////////////////  
		textCoverFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {  
	            event.consume(); 
	        }
	    });
		textCoverFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);
	                    
	                    //// FIXME: Set maximum secret image size here   ////////
	                    double width=coverFileBufferedImageICCDC.getWidth();
	                    double height=coverFileBufferedImageICCDC.getHeight();  
	                    width=(width/4)*3;
	                    height=(height/4)*3;
	                    
	                    double totalBlocks=((width*height)/9);
	                    double totalBits=totalBlocks*12;
	                    double totalPixels=totalBits/24;
	                    textSecretFilePathICCDC.setPromptText("Number of bits = "+totalBits+", Number of pixels = "+totalPixels);
	                    //////////////////////////////////////////////////
	                    
	                    textCoverFileWidthICCDC.setText(""+coverFileBufferedImageICCDC.getWidth());
	                    textCoverFileHeightICCDC.setText(""+coverFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textSecretFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textSecretFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textSecretFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    secretFileBufferedImageICCDC = ImageIO.read(file);
	                    
	                    textSecretFileWidthICCDC.setText(""+secretFileBufferedImageICCDC.getWidth());
	                    textSecretFileHeightICCDC.setText(""+secretFileBufferedImageICCDC.getHeight());
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		textStegoFilePathICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		textStegoFilePathICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            String droppedFiles="";
	            
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	droppedFiles+=saveFilePath+";";	            	                               
	            } 
	            
	            textStegoFilePathICCDC.setText(droppedFiles);
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		imageScrollPaneICCDC.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		imageScrollPaneICCDC.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textCoverFilePathICCDC.setText(saveFilePath);
	              
	                try{
	                	//// Set the image in the image view
	                    file=new File(saveFilePath);
	                    coverFileBufferedImageICCDC = ImageIO.read(file);
	                    Image tempImage = SwingFXUtils.toFXImage(coverFileBufferedImageICCDC, null);
	                    imageViewCoverFileICCDC.setImage(tempImage);	
	                    
	                    //// FIXME: Set maximum secret image size here   ////////
	                    double width=coverFileBufferedImageICCDC.getWidth();
	                    double height=coverFileBufferedImageICCDC.getHeight();  
	                    width=(width/4)*3;
	                    height=(height/4)*3;
	                    
	                    double totalBlocks=((width*height)/9);
	                    double totalBits=totalBlocks*12;
	                    double totalPixels=totalBits/24;
	                    textSecretFilePathICCDC.setPromptText("Number of bits = "+totalBits+", Number of pixels = "+totalPixels);
	                    //////////////////////////////////////////////////
	                    
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                } 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		////////////////////////   END: Drag And Drop  ///////////////////////////
		    
		buttonEmbedICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			SubSampleClass Object1=new SubSampleClass(textCoverFilePathICCDC.getText().trim(),
								  textSecretFilePathICCDC.getText().trim(),
					              null,"embed",0,0);
			//// StartEmbeddingICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(Object1.progressProperty());
			labelProgress.textProperty().bind(Object1.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(Object1);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Embedding Process!!!");
				return;
			}		
		});
		
		buttonExtractICCDC.setOnAction((e)->{
	    	/////////// Creating a ICCDC task for Embedding //////////////////////////////
			SubSampleClass Object2=new SubSampleClass(textCoverFilePathICCDC.getText().trim(),
					  						textSecretFilePathICCDC.getText().trim(),
					  						textStegoFilePathICCDC.getText().trim(),
					  						"extract",
					  						Integer.parseInt(textSecretFileWidthICCDC.getText().trim()),
						              		Integer.parseInt(textSecretFileHeightICCDC.getText().trim()));
			//// StartExtractionICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(Object2.progressProperty());
			labelProgress.textProperty().bind(Object2.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor2 = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor2.execute(Object2);
			}catch(Exception ex){
				MessageBox("Exception in ICCDC Extraction Process!!!");
				return;
			}		
		});
		
		final ContextMenu contextMenuICCDC = new ContextMenu();		
		MenuItem itemBinaryFormatICCDC = new MenuItem("Binary Format");
		itemBinaryFormatICCDC.setOnAction((e)-> {
		    showImageInTextFormat(coverFileBufferedImageICCDC,false);
		});		
		contextMenuICCDC.getItems().addAll(itemBinaryFormatICCDC);		
		imageScrollPaneICCDC.setContextMenu(contextMenuICCDC);	
	}//// End InitializeSubSampleClassTab
	
	
	void setComboRowColWM(int col,int row){
		comboColumn.setValue(""+col);
		comboRow.setValue(""+row);
	}
	
	void setComboRowColWMMIF(int col,int row){
		comboColumnMIF.setValue(""+col);
		comboRowMIF.setValue(""+row);
	}
	
	void setRoundWM(int round){		
		comboRotation.setValue(""+round);
	}
	
	void setRoundWMMIF(int round){		
		comboRotationMIF.setValue(""+round);
	}
	
	int getRoundWM(){
		return (Integer.parseInt(comboRotation.getValue()));
	}
	
	int getRoundWMMIF(){
		return (Integer.parseInt(comboRotationMIF.getValue()));
	}
	
	int getOutputImageWidth(){
		return (Integer.parseInt(textWidth.getText().trim()));
	}
	
	int getOutputImageWidthMIF(){
		return (Integer.parseInt(textWidthMIF.getText().trim()));
	}
	
	int getOutputImageHeight(){
		return (Integer.parseInt(textHeight.getText().trim()));
	}
	
	int getOutputImageHeightMIF(){
		return (Integer.parseInt(textHeightMIF.getText().trim()));
	}
	
	//// This method initializes the controls of the Logging Tab
	void InitializeLoggingTab(){
		textAreaLogging = new TextArea();
		textAreaLogging.setPrefSize(980, 550);
		
		ScrollPane textAreaScrollPane = new ScrollPane();
		textAreaScrollPane.setContent(textAreaLogging);
		
		tabLogging.setContent(textAreaScrollPane);
		
	}//// End InitializeThirdTab
		
	////This method displays context menu
	void InitializePopupMenu(){
		final ContextMenu contextMenuOrg = new ContextMenu();
		final ContextMenu contextMenuSM = new ContextMenu();
		final ContextMenu contextMenuSA = new ContextMenu();
		final ContextMenu contextMenuWM = new ContextMenu();
		
		MenuItem itemBinaryFormatOrg = new MenuItem("Binary Format");
		MenuItem itemBinaryFormatSM = new MenuItem("Binary Format");
		MenuItem itemBinaryFormatSA = new MenuItem("Binary Format");
		MenuItem itemBinaryFormatWM = new MenuItem("Binary Format");
		MenuItem itemBinaryFormatDetailWM = new MenuItem("Binary Format (Detail)");
		
		MenuItem itemChangePixelOrg = new MenuItem("Change Pixel");
		MenuItem itemChangePixelSM = new MenuItem("Change Pixel");
		MenuItem itemChangePixelSA = new MenuItem("Change Pixel");
		MenuItem itemChangePixelWM = new MenuItem("Change Pixel");
		
		MenuItem itemExitOrg = new MenuItem("Exit");
		MenuItem itemExitSM = new MenuItem("Exit");
		MenuItem itemExitSA = new MenuItem("Exit");
		MenuItem itemExitWM = new MenuItem("Exit");
			
		itemBinaryFormatOrg.setOnAction((e)-> {
		    showImageInTextFormat(bufferedImage,false);
		});
		itemBinaryFormatSM.setOnAction((e)-> {
		    showImageInTextFormat(bufferedImageSM,false);
		});
		itemBinaryFormatSA.setOnAction((e)-> {
		    showImageInTextFormat(bufferedImageSA,false);
		});
		itemBinaryFormatWM.setOnAction((e)-> {
		    showImageInTextFormat(bufferedImageWM,false);
		});		
		itemBinaryFormatDetailWM.setOnAction((e)-> {
		    showImageInTextFormat(bufferedImageWM,true);
		});
		
		itemChangePixelOrg.setOnAction((e)-> {
		    changePixelValue(bufferedImage,"");
		});
		itemChangePixelSM.setOnAction((e)-> {
			changePixelValue(bufferedImageSM,"");
		});
		itemChangePixelSA.setOnAction((e)-> {
			changePixelValue(bufferedImageSA,"");
		});
		itemChangePixelWM.setOnAction((e)-> {
			changePixelValue(bufferedImageWM, textCoverFilePathWM.getText().trim());
		});
		
		itemExitOrg.setOnAction((e)-> {
			System.exit(0);
		});
		itemExitSM.setOnAction((e)-> {
			System.exit(0);
		});
		itemExitSA.setOnAction((e)-> {
			System.exit(0);
		});
		itemExitWM.setOnAction((e)-> {
			System.exit(0);
		});
		
		contextMenuOrg.getItems().addAll(itemBinaryFormatOrg, itemChangePixelOrg, 
				                         itemExitOrg);
		contextMenuSM.getItems().addAll(itemBinaryFormatSM, itemChangePixelSM, 
										itemExitSM);
		contextMenuSA.getItems().addAll(itemBinaryFormatSA, itemChangePixelSA,
										itemExitSA);
		contextMenuWM.getItems().addAll(itemBinaryFormatWM, itemBinaryFormatDetailWM, 
				                        itemChangePixelWM, itemExitWM);

		//contextMenuSize.getItems().addAll(itemEmpty);
		
		imageScrollPane.setContextMenu(contextMenuOrg);
		imageSMScrollPane.setContextMenu(contextMenuSM);
		imageSAScrollPane.setContextMenu(contextMenuSA);
		imageScrollPaneWM.setContextMenu(contextMenuWM);
		//textHeightTools.setContextMenu(contextMenuSize);
				
	}//// End InitializeThirdTab
	
	//// Initialize Tools Tab
	void InitializeToolsTab(){
		
		VBox vboxMainTools=new VBox();
		
		Label labelNumber=new Label("Enter a number");
		TextField textNumber1=new TextField();
		textNumber1.setPrefColumnCount(4);
		TextField textNumber2=new TextField();
		textNumber2.setPrefColumnCount(4);
		Button buttonMul1=new Button("MUL>");	
		Button buttonMul2=new Button("<MUL");
		TextField textOutput=new TextField();
		textOutput.setPrefColumnCount(5);
		
		buttonMul1.setOnAction((e)->{
			int n1,n2;
			try{
				n1=Integer.parseInt(textNumber1.getText().trim());
				n2=Integer.parseInt(textNumber2.getText().trim());
				if(flag==false){
					textOutput.setText(""+(n1*n2));
					flag=true;
				}else{
					textNumber2.setText(""+(n2+1));
					n2=Integer.parseInt(textNumber2.getText().trim());
					textOutput.setText(""+(n1*n2));
				}				
				
			}catch(Exception ex){
				MessageBox("Please enter a number");
				return;
			}
		});		
		buttonMul2.setOnAction((e)->{
			int n1,n2;
			try{
				n1=Integer.parseInt(textNumber1.getText().trim());
				n2=Integer.parseInt(textNumber2.getText().trim());
				if(flag==false){
					textOutput.setText(""+(n1*n2));
					flag=true;
				}else{
					textNumber2.setText(""+(n2-1));
					n2=Integer.parseInt(textNumber2.getText().trim());
					textOutput.setText(""+(n1*n2));
				}				
				
			}catch(Exception ex){
				MessageBox("Please enter a number");
				return;
			}
		});	
		
		FlowPane flowPaneTools=new FlowPane();
		flowPaneTools.setPadding(new Insets(10));
		flowPaneTools.setHgap(10);
		flowPaneTools.setPrefWidth(800);
		flowPaneTools.setAlignment(Pos.TOP_CENTER);
		flowPaneTools.getChildren().addAll(labelNumber,textNumber1,textNumber2,
										   buttonMul1,buttonMul2,textOutput);		
		
		//// Panes for Image Cropping Tools
		FlowPane pane1=new FlowPane();
		pane1.setHgap(10);
		pane1.setPrefWidth(800);
        pane1.setAlignment(Pos.CENTER);
        labelFilePathTools=new Label("Image Effect");
    	textFilePathTools=new TextField();
    	textFilePathTools.setPrefColumnCount(55);
    	pane1.getChildren().addAll(labelFilePathTools,textFilePathTools);
    	
    	FlowPane pane2=new FlowPane();
    	pane2.setHgap(10);
    	pane2.setAlignment(Pos.CENTER);
    	labelXTools=new Label("X");
    	textXTools=new TextField("0");
    	textXTools.setPrefColumnCount(3);
    	labelYTools=new Label("Y");
    	textYTools=new TextField("0");
    	textYTools.setPrefColumnCount(3);
    	pane2.getChildren().addAll(labelXTools,textXTools,labelYTools,textYTools);
    	
    	FlowPane pane3=new FlowPane();
    	pane3.setHgap(10);
    	pane3.setAlignment(Pos.CENTER);
    	labelWidthTools=new Label("Width/Salt");
    	textWidthTools=new TextField();
    	textWidthTools.setPrefColumnCount(5);
    	labelHeightTools=new Label("Height/Pepper");;
    	textHeightTools=new TextField();
    	textHeightTools.setPrefColumnCount(5);
    	
		////////////////////////////////////////////////////
    	final ContextMenu contextMenuSize=new ContextMenu();
		MenuItem itemEmpty=new MenuItem("Empty");
		contextMenuSize.getItems().add(itemEmpty);
		textHeightTools.setContextMenu(contextMenuSize);
		////////////////////////////////////////////////////

    	///////////////////////////////////////////////////
		textWidthTools.setOnMouseClicked((e)->{
			textWidthTools.textProperty().unbind();
		});		
		textWidthTools.setOnKeyPressed((e)->{
			textWidthTools.textProperty().unbind();
		});
    	textHeightTools.setOnMouseClicked((e)->{
    		int number=0;
    		try{
    			number=Integer.parseInt(textWidthTools.getText().trim());
    		}catch(Exception ex){
    			return;
    		}  
    		
    		contextMenuSize.getItems().removeAll();
    		contextMenuSize.getItems().clear();
    		
    		//// Populate the context menu
    		menuItemSizes=new MenuItem[(number/2)+1];
    		int i=1;
    		int index=0;
    		for(i=1;i<=number;i++){    			
    			if((number%i)==0){
    				menuItemSizes[index] = new MenuItem(""+i+"x"+(number/i));
        			contextMenuSize.getItems().add(menuItemSizes[index]);
        			final int n=i;   
        			final int num=(number/n);
        			menuItemSizes[index].setOnAction((ee)-> {    
        				textWidthTools.textProperty().unbind();
        				textWidthTools.setText(""+n);
        				textHeightTools.setText(""+num);
        				contextMenuSize.getItems().removeAll();
        	    		contextMenuSize.getItems().clear(); 
        	    		//// Set the same file path if width and height is
        	    		//// greater than the crop width and height.
        	    		/*
        	    		try{
        	    			BufferedImage image = ImageIO.read(new File(textSecretFilePathHammingCode.getText().trim()));
        	    			if(image.getWidth()>=n && image.getHeight()>=num){
        	    				textFilePathTools.setText(textSecretFilePathHammingCode.getText().trim());
        	    			}
        	    			image=null;
        	    		}catch(Exception ex){
        	    			ex.printStackTrace();
        	    		}   
        	    		*/
            		});        			
        			index++;
    			}    			
    		}//// End for loop
    		
    	});
    	
    	/////////////////////////////////////////////////
    	pane3.getChildren().addAll(labelWidthTools,textWidthTools,labelHeightTools,textHeightTools);
    	
    	FlowPane pane4=new FlowPane();
    	pane4.setAlignment(Pos.CENTER);  
    	buttonOKTools=new Button("Crop Image");
    	
    	buttonOKTools.setOnAction((event)->{  
    		int x,y,width,height;
    		try{
    			x=Integer.parseInt(textXTools.getText().trim());
    			y=Integer.parseInt(textYTools.getText().trim());
    			width=Integer.parseInt(textWidthTools.getText().trim());
    			height=Integer.parseInt(textHeightTools.getText().trim());
    		}catch(Exception ex){
    			MessageBox("Please, insert a number in the textboxes");
    			return;
    		}
			
			String imageFilePath=textFilePathTools.getText().trim();
			//// Crop the image to a specific size
			File fileCrop=new File(imageFilePath);
			String outputImageFilePath=fileCrop.getParent()+"\\"+width+"x"+height+".png";
					
			PCUtility.CropImage(imageFilePath,outputImageFilePath,x,y,width,height);
		});
    	
    	buttonSaltAndPepperTools=new Button("Salt & Pepper Effect");
    	buttonSaltAndPepperTools.setOnAction((event)->{ 
    		String imageFilePath=textFilePathTools.getText().trim();
			//// Crop the image to a specific size
			File fileSaltAndPepper=new File(imageFilePath);
			String outputFileName=PCUtility.GetFileNameWithoutExtension(fileSaltAndPepper);
			
			String outputImageFilePath=fileSaltAndPepper.getParent()+"\\"+outputFileName+"-SaltAndPepper"+".png";
			
			int nSalt,nPepper;
			try{
				nSalt=Integer.parseInt(textWidthTools.getText().trim());
				nPepper=Integer.parseInt(textHeightTools.getText().trim());
			}catch(Exception ex){
				MessageBox("Please, insert a number in the textboxes");
    			return;
			}
			
			PCUtility.SaltAndPepperEffect(imageFilePath,outputImageFilePath,nSalt,nPepper);			
		});
    	   
    	pane4.getChildren().addAll(buttonOKTools, new Label("   "),buttonSaltAndPepperTools);  
    	
    	VBox vboxTools=new VBox();
    	vboxTools.setAlignment(Pos.CENTER);
    	vboxTools.setPadding(new Insets(10,10,10,10));
    	vboxTools.getChildren().addAll(pane1,new Label(),pane2,new Label(),pane3,new Label(),pane4);
    	////////////////////////////////////////////////////////////////////////////////////////////
    	
    	//// Panes for Suitable Image Testing Tools
   		FlowPane paneSuitable1=new FlowPane();
   		paneSuitable1.setHgap(10);
   		paneSuitable1.setPrefWidth(800);
   		paneSuitable1.setAlignment(Pos.CENTER);
        labelSuitableFilePathTools=new Label("Suitable Image File Path");
       	textSuitableFilePathTools=new TextField();
       	textSuitableFilePathTools.setPrefColumnCount(55);
       	paneSuitable1.getChildren().addAll(labelSuitableFilePathTools,textSuitableFilePathTools);
       	
       	FlowPane paneSuitable2=new FlowPane();
       	paneSuitable2.setHgap(10);
       	paneSuitable2.setAlignment(Pos.CENTER);
       	labelSuitableMaxTools=new Label("Max");
       	comboSuitableMaxTools=new ComboBox<String>();
       	comboSuitableMaxTools.getItems().addAll("0","1","2","3","4","5","6","7","8",
                "9","10","11","12","13","14","15","16");
       	comboSuitableMaxTools.setValue("1");
       	labelSuitableMinTools=new Label("Min");
       	comboSuitableMinTools=new ComboBox<String>();
       	comboSuitableMinTools.getItems().addAll("0","1","2","3","4","5","6","7","8",
                "9","10","11","12","13","14","15","16");
       	comboSuitableMinTools.setValue("1");
       	paneSuitable2.getChildren().addAll(labelSuitableMaxTools,comboSuitableMaxTools,
       									   labelSuitableMinTools,comboSuitableMinTools);
       	
       	
       	FlowPane paneSuitable3=new FlowPane();
       	paneSuitable3.setAlignment(Pos.CENTER);  
       	
       	buttonSuitableOKTools=new Button("Test");       	
       	buttonSuitableOKTools.setOnAction((event)->{    	    	   			
   			int max=Integer.parseInt(comboSuitableMaxTools.getValue());
   			int min=Integer.parseInt(comboSuitableMinTools.getValue()); 
   			String imageFilePath=textSuitableFilePathTools.getText().trim();
   			//// Test whether an image is suitable or not 
   			int ret=PCUtility.IsSuitableImage(imageFilePath, min, max);
   			if(ret==-1){
   				PCUtility.MessageBox("Error Reading Image File");
   			}else if(ret==1){
   				PCUtility.MessageBox("Image is suitable");
   			}else{
   				PCUtility.MessageBox("Image is NOT a suitable image");
   			}
   		});
       	
       	paneSuitable3.getChildren().add(buttonSuitableOKTools);  
       	
       	VBox vboxSuitableTools=new VBox();
       	vboxSuitableTools.setAlignment(Pos.CENTER);
       	vboxSuitableTools.setPadding(new Insets(10,10,10,10));
       	vboxSuitableTools.getChildren().addAll(paneSuitable1,new Label(),paneSuitable2,
       			                               new Label(),paneSuitable3);
       	////////////////////////////////////////////////////////////////////////////////////////////
       	
       	
    	vboxMainTools.setPadding(new Insets(50,50,50,50));    	
    	
    	ScrollPane sp1=new ScrollPane();
    	sp1.setContent(flowPaneTools);
    	
    	ScrollPane sp2=new ScrollPane();
    	sp2.setContent(vboxTools);
    	
    	ScrollPane sp3=new ScrollPane();
    	sp3.setContent(vboxSuitableTools);
    	
    	vboxMainTools.getChildren().addAll(sp1,new Label(""),sp2,new Label(""),sp3);
    	
		tabTools.setContent(vboxMainTools);
		//// Changed Here Replace textFilePathTools with vboxTools
		vboxTools.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		vboxTools.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textFilePathTools.setText(saveFilePath);	            	
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		//// Replaced textSuitableFilePathTools with vboxSuitableTools
		vboxSuitableTools.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		vboxSuitableTools.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String saveFilePath = file.getAbsolutePath();
	            	textSuitableFilePathTools.setText(saveFilePath); 	                                 
	            } 
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
		
		
	}	
	
	////Initialize Image Compare Tab
	void InitializeImageCompareTab(){
		
		imageViewOriginal=new ImageView();
		imageViewChanged=new ImageView();
		
		listViewImg1 = new ListView<String>();
		listViewImg2 = new ListView<String>();
		
		buttonImageCompare=new Button("Compare");
		buttonImageCompare.setOnAction((e)->{
			PCUtility.ImageCompareFunction(bufferedImageOriginal,bufferedImageChanged);			
		});
		
		buttonPSNR=new Button("PSNR");
		buttonPSNR.setOnAction((e)->{
			
			/////////// Creating a ImageUtility object  /////////////////////
			ImageUtility imgUtilObject1=new ImageUtility("PSNR",listViewImg1,listViewImg2);
			//// StartEmbeddingICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(imgUtilObject1.progressProperty());
			labelProgress.textProperty().bind(imgUtilObject1.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(imgUtilObject1);
			}catch(Exception ex){
				MessageBox("Exception in RS Analysis Calculation!!!");
				return;
			}	
				
		});
		
		buttonSD=new Button("Standard Deviation");
		buttonSD.setOnAction((e)->{			
				/////////// Creating a ImageUtility object  /////////////////////
				ImageUtility imgUtilObject2=new ImageUtility("SDCC",listViewImg1,listViewImg2);
				//// StartEmbeddingICCDC
				//////////// Start the embedding thread.  //////////////////////////////   
				progressBar.progressProperty().bind(imgUtilObject2.progressProperty());
				labelProgress.textProperty().bind(imgUtilObject2.messageProperty());
						
				//// Set the ExecutorService as a daemon thread.
				final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
					   public Thread newThread(Runnable runnable) {
					      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
					      thread.setDaemon(true);
					      return thread;
					   }  
					});
				//// Run the executor
				try{
					executor.execute(imgUtilObject2);
				}catch(Exception ex){
					MessageBox("Exception in SD And CC Calculation!!!");
					return;
				}	
				
		});
		
		buttonRSAnalysis=new Button("RS Analysis");
		buttonRSAnalysis.setOnAction((e)->{
			/////////// Creating a ImageUtility object  /////////////////////
			ImageUtility imgUtilObject3=new ImageUtility("RSAnalysis",listViewImg1,listViewImg2);
			//// StartEmbeddingICCDC
			//////////// Start the embedding thread.  //////////////////////////////   
			progressBar.progressProperty().bind(imgUtilObject3.progressProperty());
			labelProgress.textProperty().bind(imgUtilObject3.messageProperty());
					
			//// Set the ExecutorService as a daemon thread.
			final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
				   public Thread newThread(Runnable runnable) {
				      Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				      thread.setDaemon(true);
				      return thread;
				   }  
				});
			//// Run the executor
			try{
				executor.execute(imgUtilObject3);
			}catch(Exception ex){
				MessageBox("Exception in RS Analysis Calculation!!!");
				return;
			}	
			
		});
		
		buttonClearList=new Button("Clear List");
		buttonClearList.setOnAction((e)->{
			listViewImg1.getItems().clear();
			listViewImg2.getItems().clear();
		});
		
		buttonSSIM=new Button("SSIM");
		buttonSSIM.setOnAction((e)->{
			
		});
		
		VBox vboxImageCompare=new VBox();
		//// Shared Key input box
		GridPane gridPaneImageCompareLabels = new GridPane();
		gridPaneImageCompareLabels.setHgap(10);
		gridPaneImageCompareLabels.setVgap(10);
		gridPaneImageCompareLabels.setPadding(new Insets(0, 10, 0, 10));
	    
		labelOriginalFilePath=new Label("Original File Path Here");
		labelOriginalFilePath.setPrefSize(500, 30);
		labelChangedFilePath=new Label("Changed File Path Here");
		labelChangedFilePath.setPrefSize(500, 30);
		
		GridPane.setConstraints(labelOriginalFilePath, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(labelOriginalFilePath,1);
	    
	    GridPane.setConstraints(labelChangedFilePath, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(labelChangedFilePath,1);
	    
	    gridPaneImageCompareLabels.getChildren().addAll(labelOriginalFilePath,labelChangedFilePath);
	    
		vboxImageCompare.getChildren().add(gridPaneImageCompareLabels);
		
		//// Image Views
		GridPane gridPaneImageViews = new GridPane();
		gridPaneImageViews.setHgap(10);
		gridPaneImageViews.setVgap(10);
		gridPaneImageViews.setPadding(new Insets(0, 10, 0, 10));
	    	    
	    //// Scroll view for SM image
		imageViewOriginal.setPreserveRatio(true);
		imageViewOriginal.setSmooth(true);
		imageViewOriginal.setCache(true);
		imageViewOriginal.autosize();
		
		listViewImg1.autosize();
		listViewImg1.setPrefSize(800,450);
		imageOriginalScrollPane = new ScrollPane();
		imageOriginalScrollPane.setPrefSize(500, 450);
		imageOriginalScrollPane.setContent(listViewImg1);
	    ////Add SM image view to the left scroll pane
	    GridPane.setConstraints(imageOriginalScrollPane, 0, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageOriginalScrollPane,1);
	    
	    //// Scroll view for SM image
	    imageViewChanged.setPreserveRatio(true);
	    imageViewChanged.setSmooth(true);
	    imageViewChanged.setCache(true);
	    imageViewChanged.autosize();
	    
	    listViewImg2.autosize();
	    listViewImg2.setPrefSize(800,450);
	    imageChangedScrollPane = new ScrollPane();
	    imageChangedScrollPane.setPrefSize(500, 450);
	    imageChangedScrollPane.setContent(listViewImg2);
	    ////Add SM image view to the left scroll pane
	    GridPane.setConstraints(imageChangedScrollPane, 1, 0); // column=0 row=0
	    GridPane.setColumnSpan(imageChangedScrollPane,1);
	    //// Add the image views to the grid pane
	    gridPaneImageViews.getChildren().addAll(imageOriginalScrollPane,imageChangedScrollPane);
	    	    
		//// Add the grid pane containing image views to the vboxExtraction
	    vboxImageCompare.getChildren().add(gridPaneImageViews);
	    
	    //// Add the Extraction button
	    FlowPane flowPaneButton=new FlowPane();
	    flowPaneButton.setPadding(new Insets(10));
	    flowPaneButton.setHgap(100);
	    flowPaneButton.setPrefWidth(1000);
	    flowPaneButton.setAlignment(Pos.CENTER);
	    
	    //flowPaneButton.getChildren().add(new Label("SM Image"));
	    flowPaneButton.getChildren().add(buttonImageCompare);
	    flowPaneButton.getChildren().add(buttonClearList);
	    flowPaneButton.getChildren().add(buttonPSNR);
	    flowPaneButton.getChildren().add(buttonSD);
	    flowPaneButton.getChildren().add(buttonRSAnalysis);
	    flowPaneButton.getChildren().add(buttonSSIM);
	    //flowPaneButton.getChildren().add(new Label("SA Image"));
	    //// Add the button to the vBox	    
	    vboxImageCompare.getChildren().add(flowPaneButton);		
		
		//// Add the vboxExtraction to the Extraction Tab
		tabImageCompare.setContent(vboxImageCompare);		
		
		imageOriginalScrollPane.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
	    
		imageChangedScrollPane.setOnDragOver((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        if (db.hasFiles()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        } else {
	            event.consume(); 
	        }
	    });
		
		imageOriginalScrollPane.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            
	            for (File file:db.getFiles()) { 
	            	
	            	String filePathOriginal = file.getAbsolutePath();
	                try{
	                	listViewImg1.getItems().add(filePathOriginal);
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }	                                               
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
	    
		imageChangedScrollPane.setOnDragDropped((DragEvent event) -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	            success = true;
	            for (File file:db.getFiles()) {
	            	String filePathChanged = file.getAbsolutePath();
	                try{
	                	listViewImg2.getItems().add(filePathChanged);
	                }catch(Exception ex){
	                	ex.printStackTrace();
	                }                                     
	            }
	        }
	        event.setDropCompleted(success);
	        event.consume();
	    });
				
	}//// End InitializeImageCompareTab
	
	//// This function returns log of a value
	public static double log10(double x) {
	    return Math.log(x)/Math.log(10);
	}	
		
	//// This function converts a binary string to a character
	String binaryStringToCharacter(String strBinary){
		
		int value=Integer.parseInt(strBinary,2);
		return Character.toString((char)value);
		
	}
	
	//// Writes the log in the textAreaLogging in Logging Tab
	void writeLog(String strLog){
		textAreaLogging.appendText("\t" + strLog);		
	}//// End writeLog
	
	//// Clears the log in the textAreaLogging in Logging Tab
	void clearLog(String strLog){
		textAreaLogging.setText("");	
	}//// End writeLog
	
	////This function shows the image in text format
	void showImageInTextFormat(BufferedImage bufferedImageArg, boolean detail){
		
		StringBuilder logBuilder=new StringBuilder();
		
		try{
			
			int pixelValue,redPixel,greenPixel,bluePixel;			
				
			if(bufferedImageArg==bufferedImage){
				 logBuilder.append("ImageOriginal.png\n");
			}else if(bufferedImageArg==bufferedImageSM){
				 logBuilder.append("ImageSM.png\n");
			}else if(bufferedImageArg==bufferedImageSA){
				 logBuilder.append("ImageSA.png\n");
			}
			
			int imageHeight=bufferedImageArg.getHeight();
			int imageWidth=bufferedImageArg.getWidth();
			
			outerLoop:
			for (int readY = 0; readY <imageHeight ; readY++) {
				for (int readX = 0; readX <imageWidth ; readX++) {
					
					if(readY >= 9)
					{
						break outerLoop;
					}
					if( readX >= 9 )
					{
						break;
					}
					
	            	//// Get two pixel values
	            	pixelValue = bufferedImageArg.getRGB(readX, readY);
	            	Color pixelColor=new Color(pixelValue);
	               	redPixel = pixelColor.getRed();
	            	greenPixel = pixelColor.getGreen();
	            	bluePixel = pixelColor.getBlue();
	            	//// Block the pixel value here
	            	if(detail==true){
	            		logBuilder.append("["+pixelValue+" ("+redPixel+","+greenPixel+","+bluePixel+")] ");
	            	}else{
	            		logBuilder.append(" ("+redPixel+","+greenPixel+","+bluePixel+") ");
	            	}
	            	            	
				}
				logBuilder.append("\n");							
			}
		}catch(Exception e)
		{
			DebugMessageBox("Exception Occured in showImageInTextFormat function: "+e.toString());
		}
		finally
		{
			textAreaLogging.appendText(logBuilder.toString()+"\n");
			PCUtility.MessageBox("Logging Completed"); 			
		}
		
	}//// End showImageInTextFormat
	
	//// This function does the embedding process
	void ProcessPVDEEmbedding() throws IOException{	
		 
		StringBuilder log=new StringBuilder();
		StringBuilder logM1M2=new StringBuilder("Embed = ");
		StringBuilder logSecretKeyBit = new StringBuilder();
		//logM1M2.append(arg0);
		
		////Create two iamges
		BufferedImage ImageStegoMajor = new BufferedImage(bufferedImage.getWidth(), 
				                                          bufferedImage.getHeight(), 
				                                          BufferedImage.TYPE_INT_RGB);
		BufferedImage ImageStegoAuxiliary = new BufferedImage(bufferedImage.getWidth(), 
														  bufferedImage.getHeight(), 
														  BufferedImage.TYPE_INT_RGB);

		byte[] bytesSecretMessage;
		byte[] bytesSecretKey;
		
		//// Get the secret message
		String strSecretMessage=textSecretMessage.getText().trim();
		String strSecretKey=textSecretKey.getText().trim();
		//// Check if the secret message is a file
		File file = new File(strSecretMessage);
		if (file.exists()){
		    if(file.isFile()){
		    	Path path = Paths.get(strSecretMessage);
		    	try {
					bytesSecretMessage = Files.readAllBytes(path);
				} catch (IOException e) {
					PCUtility.MessageBox("Error reading secret message file");
					e.printStackTrace();
					return;
				}
		    }else{
		    	PCUtility.MessageBox("Not a valid secret message file");
		    	return;
		    }
		}else{
	    	bytesSecretMessage = strSecretMessage.getBytes();
	    }
				
		
		bytesSecretKey = strSecretKey.getBytes();
		
		strSecretMessageBits="";
		for(int i=0;i<bytesSecretMessage.length;i++){
			int temp=bytesSecretMessage[i]; 
			strSecretMessageBits = strSecretMessageBits + PCUtility.ConvertToNBitBinaryString(temp,10);
			//Integer.toBinaryString(temp);
		}
			
		//// Backup the secret message bits
		String strSecretMessageBitsBkp=strSecretMessageBits;
		
		strSecretKeyBits="";
		for(int i=0;i<bytesSecretKey.length;i++){
			int temp=bytesSecretKey[i]; 
			strSecretKeyBits = strSecretKeyBits + PCUtility.ConvertToNBitBinaryString(temp,8);
		}
		
		DebugMessageBox(strSecretMessageBitsBkp);
				
		int d,dd,ddd,h,hd,delta, delta1, gamma, gamma1;
		int A,k;
		int i=0;
		int xi, xi1, xid, xi1d, xidd, xi1dd;
		int rgb1,rgb2;
		int firstPixelValue,firstRedPixel,firstGreenPixel,firstBluePixel;
		int secondPixelValue,secondRedPixel,secondGreenPixel,secondBluePixel;
		
		int pixelArraySM[][];
		int pixelArraySA[][];
		pixelArraySM = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
		pixelArraySA = new int[bufferedImage.getHeight()][bufferedImage.getWidth()];
		
		log.append("ImageOriginal.png"+"\n");
		
		int count=0;
		//// Determine the color of each pixel in the image
        for (int readY = 0; readY < bufferedImage.getHeight(); readY++) {
        	strSecretMessageBitsPosition=0;
            for (int readX = 0; readX < bufferedImage.getWidth(); readX+=2) {
            	
            	//// Get the first pixel value
            	firstPixelValue = bufferedImage.getRGB(readX, readY);
            	firstRedPixel = (firstPixelValue >> 16) & 0xFF;
            	firstGreenPixel = (firstPixelValue >>8 ) & 0xFF;
            	firstBluePixel = (firstPixelValue >>0) & 0xFF;
            	
            	//// Get the second pixel value
            	secondPixelValue = bufferedImage.getRGB(readX+1,readY);
            	secondRedPixel = (secondPixelValue >> 16) & 0xFF;
            	secondGreenPixel = (secondPixelValue >>8 ) & 0xFF;
            	secondBluePixel = (secondPixelValue >>0 ) & 0xFF;
            	
            	xi  = firstRedPixel;
            	xi1 = secondRedPixel;
            	
        		//// d = xi - xi+1
        		d = Math.abs(xi - xi1);
        		DebugMessageBox("d = "+d);
        		
        		//// d' = m1 + lb 
        		//String m1=getFirstNBits(4);  
        		String m1=getFirstNBits(strSecretMessageBits, 4, count);
        		count+=4;
        		
        		dd=binaryStringToDecimal(m1)+getLowerBoundOfRange(Math.abs(d));
        		
        		logM1M2.append(m1+"->");
        		DebugMessageBox("m1 = "+m1);
        		DebugMessageBox("m1 = "+binaryStringToDecimal(m1));
        		DebugMessageBox("dd = "+dd);
        		
        		//// d'' = d' - d
        		ddd = dd - d;  
        		DebugMessageBox("ddd = "+ddd);
        		
        		//// Compute delta = ceiling(d''/2) and gamma = = floor(d''/2)
        		double temp=ddd;
        		temp=temp/2;
        		delta = (int)Math.ceil(temp);
        		DebugMessageBox("temp = "+temp);
        		DebugMessageBox("delta = "+delta);
        		
        		gamma = (int)Math.floor(temp);
        		DebugMessageBox("gamma = "+gamma);
        		
        		if ( xi > xi1 ){
        			xid  = xi + gamma;
        			xi1d = xi1 - delta;
        		}else{
        			xid  = xi - delta;
        			xi1d = xi1 + gamma; 
        		}        		
        		DebugMessageBox("xid = "+xid+", xi1d = "+xi1d);
        		
        		//// h = ( d - lb )
        		h = d - getLowerBoundOfRange(Math.abs(d));
        		DebugMessageBox("h = "+h);
        		
        		//// h' = 2h + m2
        		//String m2=getFirstNBits(1);
        		String m2=getFirstNBits(strSecretMessageBits, 1, count);
        		count+=1;
        		
        		hd = 2*h + binaryStringToDecimal(m2);
        		logM1M2.append(m2+"  ");
        		DebugMessageBox("m2 = "+m2);
        		DebugMessageBox("hd = "+hd);
        		
        		//// Average A = floor((xi' + xi+1')/2)
        		double tempx=(xid + xi1d);
        		tempx=tempx/2;
        		A = (int)Math.floor(tempx);
        		DebugMessageBox("A = "+A);
        		
        		//// delta1 = ceil( h'/2 ) , gamma1 = ceil(h'/2)
        		double temphd=hd;
        		temphd=temphd/2;
        		delta1 = (int)Math.ceil( temphd );
        		DebugMessageBox("delta1 = "+delta1);
        		
        		gamma1 = (int)Math.floor( temphd );
        		DebugMessageBox("gamma1 = "+gamma1);
        		
        		
        		if(xid > xi1d){
        			xidd  = A + delta1;
        			xi1dd = A - gamma1;
        		}else{
        			xidd  = A - gamma1;
        			xi1dd = A + delta1;
        		}
        		DebugMessageBox("xidd = "+xidd+", xi1dd = "+xi1dd);    
        		        		
        		//// If key bit is finished, start it from the begining
        		if(i>=strSecretKeyBits.length()){
        			i=0;
        		}
        		k=Integer.parseInt(strSecretKeyBits.substring((int)i, (int)i+1));  
        		i++;
        		
        		logSecretKeyBit.append(k);
        		
    			if(k==1){ 
    				rgb1 = prepareRGB(xid,firstGreenPixel,firstBluePixel);
    				pixelArraySM[readX][readY]=rgb1;
    	    	
    				rgb2 = prepareRGB(xi1d,secondGreenPixel,secondBluePixel);
    				pixelArraySM[readX+1][readY]=rgb2;
    			
    				rgb1 = prepareRGB(xidd,firstGreenPixel,firstBluePixel);
    				pixelArraySA[readX][readY]=rgb1;
    			
    				rgb2 = prepareRGB(xi1dd,secondGreenPixel,secondBluePixel);
    			 	pixelArraySA[readX+1][readY]=rgb2;
    			}else if(k==0){
    				rgb1 = prepareRGB(xid,firstGreenPixel,firstBluePixel);
    				pixelArraySA[readX][readY]=rgb1;
    				
    				rgb2 = prepareRGB(xi1d,secondGreenPixel,secondBluePixel);
    				pixelArraySA[readX+1][readY]=rgb2;
    	    				
    				rgb1 = prepareRGB(xidd,firstGreenPixel,firstBluePixel);
    				pixelArraySM[readX][readY]=rgb1;
    			    				
    				rgb2 = prepareRGB(xi1dd,secondGreenPixel,secondBluePixel);
    				pixelArraySM[readX+1][readY]=rgb2;
    			}
    			
    			if(readY<10 && readX <10){
    				////log.append("xid = "+xid+",xi1d = "+xi1d+",xidd = "+xidd+",xi1dd = "+xi1dd);
    						
    				log.append("("+firstPixelValue+"-> "+firstRedPixel+","+firstGreenPixel+","+firstBluePixel+")");
    				log.append("\t("+secondPixelValue+"-> "+secondRedPixel+","+secondGreenPixel+","+secondBluePixel+")\t");
    			}   
    			
            }//// End Second For Loop
            if(readY<=10){
            	log.append("\n");
            }
        	
        }//// End First For Loop     
        
        //// Restore the original message bits
        strSecretMessageBits=strSecretMessageBitsBkp;
        textAreaLogging.appendText(logM1M2.toString()+"\n"); 
        textAreaLogging.appendText("Secret Key = "+logSecretKeyBit.toString()+"\n");         
        textAreaLogging.appendText(log.toString());   
        
        //// Create SM and SA image files
        createImageFile(ImageStegoMajor,"ImageSM.png",pixelArraySM,bufferedImage.getHeight(),bufferedImage.getWidth());
        createImageFile(ImageStegoAuxiliary,"ImageSA.png",pixelArraySA,bufferedImage.getHeight(),bufferedImage.getWidth());
     
        
	} //// End ProcessPVDEEmbedding
		
	////This function does the extraction process
	void ProcessPVDEExtraction(){
		
		StringBuilder logBuilder = new StringBuilder();
		StringBuilder logM1M2 = new StringBuilder("Extract = ");
		StringBuilder logSecretKeyBit = new StringBuilder();
		StringBuilder strHiddenMessage=new StringBuilder();
		
		
		//// Check if the SM and SA images exists
		if(bufferedImageSM==null || bufferedImageSA==null){
			PCUtility.MessageBox("Please provide valid SM and SA images here");
			return;
		}
		
		////Create original image
		BufferedImage imageOriginal = new BufferedImage(bufferedImageSM.getWidth(), 
														bufferedImageSM.getHeight(), 
				                                        BufferedImage.TYPE_INT_RGB);
				
		String strSharedSecretKey=textSharedSecretKeyForExtraction.getText().trim();
		byte[] bytesSharedSecretKey = strSharedSecretKey.getBytes();
		String strSharedSecretKeyBits="";
	
		for(int i=0;i<bytesSharedSecretKey.length;i++){
			int temp=bytesSharedSecretKey[i]; 
			strSharedSecretKeyBits = strSharedSecretKeyBits + PCUtility.ConvertToNBitBinaryString(temp,8);
		}
		
		int i=0,k,xi,xi1,xid,xi1d,xidd,xi1dd,d,dd,ddd,m1,h,hd;
		String m2;
		int delta,gamma;
		int rgb1,rgb2;
		int firstRedPixelSM,firstGreenPixelSM,firstBluePixelSM; 
		int firstRedPixelSA,firstGreenPixelSA,firstBluePixelSA;    	
		int secondRedPixelSM,secondGreenPixelSM,secondBluePixelSM;
		int secondRedPixelSA,secondGreenPixelSA,secondBluePixelSA;
    	
		for (int readY = 0; readY < bufferedImageSM.getHeight(); readY++) {
            for (int readX = 0; readX < bufferedImageSM.getWidth(); readX+=2) {
            			
            	//// If key bit is finished, start it from the beginning
        		if(i>=strSharedSecretKeyBits.length()){
        			i=0;
        		}
        		k=Integer.parseInt(strSharedSecretKeyBits.substring(i, i+1));  
        		i++;
        
        		logSecretKeyBit.append(k);
        		
    			if(k==1){ 
    				xid  = bufferedImageSM.getRGB(readX, readY);
                	firstRedPixelSM = (xid & 0x00ff0000) >> 16;
                	firstGreenPixelSM = (xid & 0x0000ff00) >> 8;
                	firstBluePixelSM = xid & 0x000000ff;
                	xid=firstRedPixelSM;
                	
    				xi1d = bufferedImageSM.getRGB(readX+1, readY);
                	secondRedPixelSM = (xi1d & 0x00ff0000) >> 16;
                	secondGreenPixelSM = (xi1d & 0x0000ff00) >> 8;
                	secondBluePixelSM = xi1d & 0x000000ff;
                	xi1d=secondRedPixelSM;
    				
    				xidd  = bufferedImageSA.getRGB(readX, readY);
                	firstRedPixelSA = (xidd & 0x00ff0000) >> 16;
                	firstGreenPixelSA = (xidd & 0x0000ff00) >> 8;
                	firstBluePixelSA = xidd & 0x000000ff;
                	xidd=firstRedPixelSA;
                	
    				xi1dd = bufferedImageSA.getRGB(readX+1, readY);
                	secondRedPixelSA = (xi1dd & 0x00ff0000) >> 16;
                	secondGreenPixelSA = (xi1dd & 0x0000ff00) >> 8;
                	secondBluePixelSA = xi1dd & 0x000000ff;
                	xi1dd=secondRedPixelSA;
    			}else if(k==0){
    				xid  = bufferedImageSA.getRGB(readX, readY);
                	firstRedPixelSA = (xid & 0x00ff0000) >> 16;
                	firstGreenPixelSA = (xid & 0x0000ff00) >> 8;
                	firstBluePixelSA = xid & 0x000000ff;
                	xid=firstRedPixelSA;
                	
    				xi1d = bufferedImageSA.getRGB(readX+1, readY);
                	secondRedPixelSA = (xi1d & 0x00ff0000) >> 16;
                	secondGreenPixelSA = (xi1d & 0x0000ff00) >> 8;
                	secondBluePixelSA = xi1d & 0x000000ff;
                	xi1d=secondRedPixelSA;
                	
    				xidd  = bufferedImageSM.getRGB(readX, readY);
                	firstRedPixelSM = (xidd & 0x00ff0000) >> 16;
                	firstGreenPixelSM = (xidd & 0x0000ff00) >> 8;
                	firstBluePixelSM = xidd & 0x000000ff;
                	xidd=firstRedPixelSM;
                	
    				xi1dd = bufferedImageSM.getRGB(readX+1, readY);
                	secondRedPixelSM = (xi1dd & 0x00ff0000) >> 16;
                	secondGreenPixelSM = (xi1dd & 0x0000ff00) >> 8;
                	secondBluePixelSM = xi1dd & 0x000000ff;
                	xi1dd=secondRedPixelSM;
    			}else{
    				PCUtility.MessageBox("Error in processing. Cannot Proceed.");
    				return;
    			}
    			DebugMessageBox("xid = "+xid+", xi1d = "+xi1d);
    			DebugMessageBox("xidd = "+xidd+", xi1dd = "+xi1dd);
    			
    			//// d' = |xi' - xi+1'| 
    			dd = Math.abs(xid - xi1d);
    			DebugMessageBox("dd = "+dd);
    			
    			//// m1 = d' - lb
    			m1 = dd - getLowerBoundOfRange(dd);
    			strHiddenMessage.append(PCUtility.ConvertToNBitBinaryString(m1,4));
    			//// First 4 bit of the secret message
    			logM1M2.append(PCUtility.ConvertToNBitBinaryString(m1,4)+"->");
    			DebugMessageBox("m1 = "+m1); 
    			
    			//// h' = xi'' - xi+1''
    			hd = Math.abs(xidd - xi1dd);
    			DebugMessageBox("hd = "+hd);
    			
    			//// Extract secret message bit m2 from LSB of h'
    			m2 = getLSB(hd);
    			strHiddenMessage.append(m2);
    			//// Add this bit to the secretMessage
    			
    			logM1M2.append(m2+"  ");
    			DebugMessageBox("m2 = "+m2);
    			
    			//// h = floor( h' / 2 )
    			double temphd=hd;
    			temphd=temphd/2;
    			h = (int)Math.floor(temphd);
    			DebugMessageBox("h = "+h);
    			
    			//// d = h + lb
    			d = h + getLowerBoundOfRange(dd);
    			DebugMessageBox("d = "+d);
    			
    			//// ddd = dd - d
    			ddd = dd - d;
    			DebugMessageBox("ddd = "+ddd);
    			
    			//// delta = ceil(ddd/2)
    			double tempdg=ddd;
    			tempdg=tempdg/2;
    			delta = (int)Math.ceil( tempdg );
    			DebugMessageBox("delta = "+delta);
    			
    			//// gamma = floor(ddd/2)
    			gamma = (int)Math.floor( tempdg );
    			DebugMessageBox("gamma = "+gamma);
    			
    			if( xid > xi1d ){
    				xi  = xid  - gamma;
    				rgb1 = (xi << 16 | firstGreenPixelSM << 8 | firstBluePixelSM);
    				xi1 = xi1d + delta;
    				rgb2 = (xi1 << 16 | secondGreenPixelSA << 8 | secondBluePixelSA);
    			}else{
    				xi  = xid  + delta;
    				rgb1 = (xi << 16 | firstGreenPixelSA << 8 | firstBluePixelSA);
    				xi1 = xi1d - gamma;
    				rgb2 = (xi1 << 16 | secondGreenPixelSM << 8 | secondBluePixelSM);
    			}
    			DebugMessageBox("xi = "+xi+", xi1 = "+xi1);
    			DebugMessageBox("rgb1 = "+rgb1+", rgb2 = "+rgb2);
    			
    			//// Set the pixels to the originalImage
    			imageOriginal.setRGB(readX, readY, rgb1);
    			imageOriginal.setRGB(readX+1, readY, rgb2);
            }
		}
				
		//// Saving the original image file
        try {
        	File file_original = new File(System.getProperty("user.home") + 
	                   "\\Desktop\\ImageOriginal.png");
			ImageIO.write(imageOriginal, "PNG", file_original);
				
			PCUtility.MessageBox("File Created Successfully");
		
		} catch (IOException e) {
			PCUtility.MessageBox("Files Creation Error!!!");
			e.printStackTrace();
		}   
        
        
        textAreaLogging.setText(logM1M2.toString()+"\n");
        textAreaLogging.appendText("Secret Message = "+strHiddenMessage.toString());
        textAreaLogging.appendText("Secret Key = "+logSecretKeyBit.toString());
        textAreaLogging.appendText(logBuilder.toString());
        
        String strMessage=binaryArrayToCharacter(strHiddenMessage.toString(),10);
        textSharedSecretKeyForExtraction.setText(strMessage);
        PCUtility.MessageBox("Secret Message Extracted");
        
                
	}//// End ProcessExtraction	
	
	//// This function converts binary string to character string
	//// The second parameter, nBit, is the number of characters
	//// it reads to get the character
	String binaryArrayToCharacter(String strBinary,int nBit){
		StringBuilder strCharacters=new StringBuilder();
		
		for(int i=0;i<strBinary.length()/nBit;i++){
			String strChar="";
			try{
				strChar=binaryStringToCharacter(strBinary.substring(i*nBit,i*nBit+nBit));
			}catch(Exception ex){
				strChar="";
				break;
			}
			strCharacters.append(strChar);
		}
		
		return strCharacters.toString();
	}
	
	//// This function changes the color value 
	//// of a particular pixel of a given image
	void changePixelValue(BufferedImage bufferedImage,String filePath){
				
		Label labelPosX=new Label("X");
		TextField textPosX = new TextField();
		textPosX.setPrefColumnCount(2);
		Label labelPosY=new Label("Y");
		TextField textPosY = new TextField();
		textPosY.setPrefColumnCount(2);
		
		Label ColorPixel=new Label("Color");
		TextField textRedColor = new TextField();
		textRedColor.setPrefColumnCount(2);
		textRedColor.setPromptText("Red");
		TextField textGreenColor = new TextField();
		textGreenColor.setPrefColumnCount(2);
		textGreenColor.setPromptText("Green");
		TextField textBlueColor = new TextField();
		textBlueColor.setPrefColumnCount(2);
		textBlueColor.setPromptText("Blue");
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("PVDE");
        alert.setHeaderText(null);
        alert.setContentText("Change The Pixel Value");
                      
        GridPane imageContent = new GridPane();
		imageContent.setMaxWidth(Double.MAX_VALUE);
		imageContent.add(labelPosX, 0, 0); // column=0, row=0	
		imageContent.add(textPosX, 1, 0); 
		imageContent.add(labelPosY, 2, 0); 
		imageContent.add(textPosY, 3, 0); 
		
		imageContent.add(ColorPixel, 0, 1); 
		imageContent.add(textRedColor, 1, 1); 
		imageContent.add(textGreenColor, 2, 1); 
		imageContent.add(textBlueColor, 3, 1); 
		
		
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(imageContent);
		alert.getDialogPane().setExpanded(true);
				
		Optional<ButtonType> result = alert.showAndWait();
		
		//// If OK is clicked in the dialog box
		if (result.get() == ButtonType.OK){
			
			int redColor=Integer.parseInt(textRedColor.getText().trim());
			int greenColor=Integer.parseInt(textGreenColor.getText().trim());
			int blueColor=Integer.parseInt(textBlueColor.getText().trim());
			
			int rgb = (redColor << 16 | greenColor << 8 | blueColor);
			bufferedImage.setRGB(Integer.parseInt(textPosX.getText().trim()), 
					             Integer.parseInt(textPosY.getText().trim()), 
					             rgb);
			
			//// Saving the image files
			String fileName="";
			File file=null;
			
			if( filePath.equals("")){
				fileName="changedFile.png";
				file = new File(System.getProperty("user.home") + "\\Desktop\\"+fileName);
			}else{
				Path p = Paths.get(filePath);
				fileName = p.getFileName().toString();
				file = new File(filePath);
			}
			        
	        try {
				ImageIO.write(bufferedImage, "PNG", file);
				PCUtility.MessageBox(fileName + " File Created Successfully");
			
			} catch (IOException e) {
				PCUtility.MessageBox(fileName + " File Creation Error!!!");
				e.printStackTrace();
			}
			
		} else {
		    // ... user chose CANCEL or closed the dialog
		}        
	
	}//// End changePixelValue

	//// prepareRGB
	int prepareRGB(int x,int greenPixel,int bluePixel){
		return (x << 16 | greenPixel << 8 | bluePixel);
	}//// End prepareRGB	
	
	////Writes an image from 2D color array
	void createImageFile(BufferedImage bufferedImageFile, String fileName, int pixels[][],int width,int height){
		int readX,readY;
        int pixelValue,redPixel,greenPixel,bluePixel;
        StringBuilder log=new StringBuilder();
        
        log.append(fileName+"\n");
        		
        for (readY = 0; readY < height; readY++) {
			for (readX = 0; readX < width; readX++) {
				pixelValue=pixels[readX][readY];
				redPixel = (pixelValue >> 16) & 0x000000FF;
            	greenPixel = (pixelValue >> 8) & 0x000000FF;
            	bluePixel = (pixelValue >> 0) & 0x000000FF;
            	
				//Color pixelColor=new Color(redPixel,greenPixel,bluePixel);
				bufferedImageFile.setRGB(readX, readY, pixelValue);
								            	
            	if(readY<10 && readX<10){
            		log.append("("+pixelValue+"-> "+redPixel+","+greenPixel+","+bluePixel+")"+"\t");  
            	}
			}
			if(readY<=10){
            	log.append("\n");
            }
		}
        
        //// Saving the image files
        File file = new File(System.getProperty("user.home") + "\\Desktop\\"+fileName);
        try {
			ImageIO.write(bufferedImageFile, "PNG", file);
			PCUtility.MessageBox(fileName + " File Created Successfully");
		
		} catch (IOException e) {
			PCUtility.MessageBox(fileName + " File Creation Error!!!");
			e.printStackTrace();
		}     
        ////textAreaLogging.setText(stringWriter.toString().replaceAll("(?m)^[ \t]*\r?\n", ""));
        textAreaLogging.appendText(log.toString());      
        
	}
	
	//// get red, green, blue color from the pixel
	int getRed(int pixel){
		return (pixel >> 16) & 0xFF;
	}
	int getGreen(int pixel){
		return (pixel >>8 ) & 0xFF;
	}
	int getBlue(int pixel){
    	return (pixel >>0 ) & 0xFF;
	}
	
	//// Get the LSB of a number
	String getLSB(int n){
		String number;
		number = PCUtility.ConvertToNBitBinaryString(n,8);
		return ""+number.charAt(number.length()-1);
	}
	
	void DebugMessageBox(String message){
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("PVDE");
        alert.setHeaderText(null);
        alert.setContentText(message);
                      
        GridPane imageContent = new GridPane();
		imageContent.setMaxWidth(Double.MAX_VALUE);
		imageContent.add(checkBoxDebugMsgBox, 0, 0);		
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(imageContent);
		
        alert.showAndWait();		
	}//// End MessageBox
	
	//// Get first N bits of a binary string
	String getFirstNBits(String inputString, int nBits, int count){
		String strTemp;
		try{
			//// Strip first N bits from the string
			strTemp = inputString.substring(count, count + nBits);
			return strTemp;
		}catch(Exception ex){
			if(nBits==8)
				return "00100000"; //// ASCII value of space character
			else if(nBits==4)
				return "0000";
			else if(nBits==3)  
				return "000";
			else
				return "0";
		}
	}//// End getFirstNBits
	
	//// Returns decimal value of a binary string
	int binaryStringToDecimal(String strBinary){
		int decimalValue;
		try{
			decimalValue=Integer.parseInt(strBinary, 2);
			return decimalValue;
		}catch(Exception ex){
			PCUtility.MessageBox("Error in binaryStringToDecimal function ");
			return -1;
		}
	}//// End binaryStringToDecimal
	
	int getLowerBoundOfRange(int d){
		//// Ranges are 0-15, 16-31, 32-47, 48-63,....., 240-255
		int lowerBound=-1;
		lowerBound=16*(d/16);
		return lowerBound;	
	}//// End getLowerBoundOfRange
	
	////This function sets the pixel size of the output image. 
	void setOutputImageSizeWM(){
		
		int row=Integer.parseInt(comboRow.getValue());
		int col=Integer.parseInt(comboColumn.getValue());
		
		int nBits=(int) (Math.log(2*row*col) / Math.log(2));			
		
		//// Otherwise do the following
		int round=getRoundWM();
		int coverImageSize=(bufferedImageWM.getWidth())*(bufferedImageWM.getHeight());
		
		int rowCount=Integer.parseInt(comboRow.getValue());
		int colCount=Integer.parseInt(comboColumn.getValue());
		
		int pixelSize=0;
		pixelSize=(round*(3*nBits)*(coverImageSize/(rowCount*colCount)))/24;
				
		textStegoFilePathWM.setPromptText("Stego Image Pixel Number = "+pixelSize);
		textHeight.setText("1");
		textWidth.setText(""+(pixelSize));
		
	}//// End setOutputImageSizeWM
	
	
	////This function sets the pixel size of the output image for WM MIF. 
	void setOutputImageSizeWMMIF(){
		
		int row=Integer.parseInt(comboRowMIF.getValue());
		int col=Integer.parseInt(comboColumnMIF.getValue());
		
		int nBits=(int) (Math.log(2*row*col) / Math.log(2));		
		
		//// For Matrix Index File do this /////////////////////////
		if(checkBoxMIFMIF.isSelected() && bufferedImageWMMIF != null ){	
				
			int coverImageSize=(bufferedImageWMMIF.getWidth())*(bufferedImageWMMIF.getHeight());
			int blockCount=(coverImageSize)/(row*col); 

			int pixelCount=(blockCount*(nBits)*18)/24;
			textStegoFilePathWMMIF.setPromptText("Stego Image Pixel Number = "+pixelCount);
			
			if(!textStegoFilePathWMMIF.getText().trim().isEmpty()){
				try{
					BufferedImage image = ImageIO.read(new File(textStegoFilePathWMMIF.getText().trim()));
					textWidthMIF.setText(""+image.getWidth());
					textHeightMIF.setText(""+image.getHeight());
					image=null;
				}catch(Exception ex){
					textHeightMIF.setText("1");
					textWidthMIF.setText(""+(pixelCount));	
				}					
			}else{
				textHeightMIF.setText("1");
				textWidthMIF.setText(""+(pixelCount));		
			}
				
			return;
		}
		////////////////////////////////////////////////////////////  
		
		//// Otherwise do the following
		int round=getRoundWMMIF();
		int coverImageSize=(bufferedImageWMMIF.getWidth())*(bufferedImageWMMIF.getHeight());
		
		int rowCount=Integer.parseInt(comboRowMIF.getValue());
		int colCount=Integer.parseInt(comboColumnMIF.getValue());
		
		int pixelSize=0;
		pixelSize=(round*(3*nBits)*(coverImageSize/(rowCount*colCount)))/24;
				
		textStegoFilePathWMMIF.setPromptText("Stego Image Pixel Number = "+pixelSize);
		textHeightMIF.setText("1");
		textWidthMIF.setText(""+(pixelSize));
		
	}//// End setOutputImageSizeWMMIF
	
	
	
	////Displays MessageBox
	void MessageBox(String message){
		MessageBox(message,"ERROR");			
	}
	void MessageBox(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox
		
	public void run(){
		
	}
	
	public static void main(String[] args) {
		
		CommandLineArguments=args;
		Application.launch(args);
	}//// End main
	
}//// End class PVDE
///////////////////////////////////////////////////////////////////////////////////////


///////////////////////////// Class for Hamming Code //////////////////////////////////
class ProjectHammingCode extends Task<Void>
{
	String typeEmbedExtract;
	String pathCoverFile;
	String pathSecretFile;
	String strFileDirectoryHC;
	BufferedImage bufferedImageCoverFile;
	BufferedImage bufferedImageSecretFile;
	String typeOfHammingCode;
	int selectedEmbeddingBits;
	String secretImageDataBits;
	StringBuilder extractedImageDataBits;
	int blockWidth;
	int blockHeight;
	int kappa;
	StringBuilder sbLog;
	int pixelBlockRED[][];
	int pixelBlockGREEN[][];
	int pixelBlockBLUE[][];
	int pixelBinaryBlock[][];
	int countDataBit;
	String strParityCheck;
	String logFile;
	String strDetails;
	boolean logEnabled;
	private final SimpleStringProperty secretImageSize;
	
	ProjectHammingCode(String type,String cFilePath,String sFilePath,String typeHammingCode,
			           int row,int col,int bitSelection,String parity,boolean logSelected){
		typeEmbedExtract=type;
		pathCoverFile=cFilePath;
		pathSecretFile=sFilePath;
		typeOfHammingCode=typeHammingCode.replaceAll(" ", "");
		blockHeight=row;
		blockWidth=col;
		selectedEmbeddingBits=bitSelection;
		secretImageDataBits="";
		extractedImageDataBits=new StringBuilder();
		bufferedImageCoverFile=null;
		bufferedImageSecretFile=null;
		strFileDirectoryHC="";
		kappa=2;
		sbLog=new StringBuilder();
		pixelBinaryBlock=new int[blockHeight][blockWidth];
		countDataBit=0;
		strParityCheck=parity;		
		strDetails=" [ Width = "+blockWidth+", Height = "+blockHeight+
				   ", Parity = "+strParityCheck+", Selected Bit = "+
				   selectedEmbeddingBits+" ]";
		logFile="";
		logEnabled=logSelected;
		secretImageSize = new SimpleStringProperty("");
	}
	
	void StartEmbeddingHammingCode(){
		updateMessage("Embedding process started....."+strDetails);
		long start = System.nanoTime();
		int coverImageHeight=0;
		int coverImageWidth=0;
		int totalProgress=0;
		int progressIndicator=0;
		
		//// Check the initial checking.
		try{
			initialCheckForHammingCode();
		}catch(Exception ex){
			return;
		}		
		
		try{		
			/////// Get data from stego file. /////////////////////////
			File fileStegoImage = new File(pathSecretFile);
			bufferedImageSecretFile = ImageIO.read(fileStegoImage);
			secretImageDataBits=getDataFromSecretImageFile(bufferedImageSecretFile);
			///////////////////////////////////////////////////////////
			
			///////////////////////////////////////////////////////////
			File fileCoverImage = new File(pathCoverFile);
			bufferedImageCoverFile = ImageIO.read(fileCoverImage);
			strFileDirectoryHC=fileCoverImage.getParent();
			//////////////////////////////////////////////////////////
			
			//// Create the log file here.
			CreateLogFile("Embed.txt");
			
			coverImageHeight=bufferedImageCoverFile.getHeight();
			coverImageWidth=bufferedImageCoverFile.getWidth();
			pixelBlockRED=new int[coverImageHeight][coverImageWidth];
			pixelBlockGREEN=new int[coverImageHeight][coverImageWidth];
			pixelBlockBLUE=new int[coverImageHeight][coverImageWidth];
			
			////// Read all pixel values in the pixel arrays for each color component.///////
			totalProgress=coverImageHeight*coverImageWidth;
			progressIndicator=0;
			updateMessage("Getting Pixel Data in Arrays...."+strDetails);
			for(int y=0;y<coverImageHeight;y++){
				for(int x=0;x<coverImageWidth;x++){				
					int pixelValue=bufferedImageCoverFile.getRGB(x, y);
					Color color=new Color(pixelValue); 
					
					pixelBlockRED[x][y]=color.getRed();
					pixelBlockGREEN[x][y]=color.getGreen();
					pixelBlockBLUE[x][y]=color.getBlue();	
					
					updateProgress(progressIndicator++,totalProgress);
				}			
			}	
			///////////////////////////////////////////////////////////////////////////////
			
			///////// Process each block for Hamming Code for each color component. ///////	
			int blockCount=1;		
			updateMessage("Processing each pixel block with Bit Selection "+
			              selectedEmbeddingBits+strDetails);
			int coverImageHeightBlock=(coverImageHeight/blockHeight);
			int coverImageWidthBlock=(coverImageWidth/blockWidth); 
			totalProgress=coverImageHeightBlock*coverImageWidthBlock;
			progressIndicator=0;
			
			for(int y=0;y<coverImageHeightBlock;y++){
				for(int x=0;x<coverImageWidthBlock;x++){
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockRED,"RED",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockRED,"RED",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockRED,"RED",4);
					}
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockGREEN,"GREEN",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockGREEN,"GREEN",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockGREEN,"GREEN",4);
					}
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockBLUE,"BLUE",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockBLUE,"BLUE",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockEmbed(blockCount,x,y,pixelBlockBLUE,"BLUE",4);
					}					
					
					blockCount++;
					updateProgress(progressIndicator++,totalProgress);
				}			
			}
			///////////////////////////////////////////////////////////////////////////////
			
			////////////// Create the output image file. //////////////////////////////////
			File fileImage = new File(pathCoverFile);
			String fileName = GetCoverFileNameWithoutExtension(fileImage);
			fileName=strFileDirectoryHC+"\\"+fileName+"-Stego.png";
			File file = new File(fileName);
			CreateImageFileFromPixelArrayHC(file,pixelBlockRED,pixelBlockGREEN,
										        pixelBlockBLUE,coverImageWidth,coverImageHeight);
			///////////////////////////////////////////////////////////////////////////////
			
			
			//// Append the input data string.
			Log(secretImageDataBits);
			//// Write the log in the log file.
			WriteLogHC(sbLog);		
			long stop = System.nanoTime();
	    	long duration =(stop-start) ;             
	    	updateMessage("Embedding Process Completed. Time Taken = "+
	    	              TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds."+strDetails);
		}catch(Exception ex){
			ex.printStackTrace();
			updateMessage("Exception in Embedding Process "+ex.toString()+strDetails);
			updateProgress(100,100);
			return;
		}
		
	}//// End StartEmbeddingHammingCode
	
	////Extraction process
	void StartExtractionHammingCode(){
		
		updateMessage("Extraction process started.....");
		long start = System.nanoTime();
		int imgHeight=0;
		int imgWidth=0;
		int totalProgress=0;
		int progressIndicator=0;
		
		try{
			
			File coverFileImage = new File(pathCoverFile);
			bufferedImageCoverFile = ImageIO.read(coverFileImage);
			strFileDirectoryHC=coverFileImage.getParent();
			
			/////// Get data from stego file. /////////////////////////
			File fileStegoImage = new File(pathSecretFile);
			bufferedImageSecretFile = ImageIO.read(fileStegoImage);
			///////////////////////////////////////////////////////////
					
			//// Create the log file here.
			CreateLogFile("Extract.txt");
			
			imgHeight=bufferedImageCoverFile.getHeight();
			imgWidth=bufferedImageCoverFile.getWidth();
			pixelBlockRED=new int[imgHeight][imgWidth];
			pixelBlockGREEN=new int[imgHeight][imgWidth];
			pixelBlockBLUE=new int[imgHeight][imgWidth];
			
			////// Read all pixel values in the pixel arrays for each color component.///////
			totalProgress=imgHeight*imgWidth;
			progressIndicator=0;
			updateMessage("Getting Pixel Data in Arrays....");
			for(int y=0;y<imgHeight;y++){
				for(int x=0;x<imgWidth;x++){				
					int pixelValue=bufferedImageCoverFile.getRGB(x, y);
					Color color=new Color(pixelValue); 
					
					pixelBlockRED[x][y]=color.getRed();
					pixelBlockGREEN[x][y]=color.getGreen();
					pixelBlockBLUE[x][y]=color.getBlue();	
					
					updateProgress(progressIndicator++,totalProgress);
				}			
			}	
			///////////////////////////////////////////////////////////////////////////////
			
			///////// Process each block to extract data from each color component. ///////	
			int blockCount=1;		
			updateMessage("Processing each pixel block with Bit Selection "+selectedEmbeddingBits);
			
			int imgHeightBlock=(imgHeight/blockHeight);
			int imgWidthBlock=(imgWidth/blockWidth); 
			totalProgress=imgHeightBlock*imgWidthBlock;
			progressIndicator=0;
			
			for(int y=0;y<imgHeightBlock;y++){
				for(int x=0;x<imgWidthBlock;x++){	
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockRED,"RED",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockRED,"RED",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockRED,"RED",4);
					}
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockGREEN,"GREEN",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockGREEN,"GREEN",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockGREEN,"GREEN",4);
					}
					//// LSB is selected
					if((selectedEmbeddingBits & 1)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockBLUE,"BLUE",1);
					}
					//// LSB+1 is selected
					if((selectedEmbeddingBits & 2)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockBLUE,"BLUE",2);
					}
					//// LSB+2 is selected
					if((selectedEmbeddingBits & 4)>0){
						processPixelBlockExtract(blockCount,x,y,pixelBlockBLUE,"BLUE",4);
					}
					
					blockCount++;
					updateProgress(progressIndicator++,totalProgress);
				}			
			}
			///////////////////////////////////////////////////////////////////////////////
			
			////////////// Create the output image file. //////////////////////////////////
			File fileImage = new File(pathCoverFile);
			String fileName = GetCoverFileNameWithoutExtension(fileImage);
			fileName=fileName.replace("-Stego", "");
			fileName=strFileDirectoryHC+"\\"+fileName+"-Cover.png";
			File file = new File(fileName); 
			CreateImageFileFromPixelArrayHC(file,pixelBlockRED,pixelBlockGREEN,
										        pixelBlockBLUE,imgWidth,imgHeight);
			///////////////////////////////////////////////////////////////////////////////
			
			/////////// Create output image file from the extracted data string ///////////
			updateMessage("Extracted Bit Length = "+extractedImageDataBits.length());
			
			String fileNameStego=GetCoverFileNameWithoutExtension(fileImage);
			fileNameStego=fileNameStego.replace("-Stego", "");
			fileNameStego=strFileDirectoryHC+"\\"+fileNameStego+"-Secret.png";
			ConvertBinaryStringToImageHC(fileNameStego,extractedImageDataBits,selectedEmbeddingBits,
					bufferedImageSecretFile.getWidth(),bufferedImageSecretFile.getHeight());
			///////////////////////////////////////////////////////////////////////////////
			
			//// Append the input data string.
			Log(extractedImageDataBits.toString());
			//// Write the log in the log file.
			WriteLogHC(sbLog);		
			long stop = System.nanoTime();
	    	long duration =(stop-start) ;             
	    	updateMessage("Embedding Process Completed. Time Taken = "+TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds.");
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
		
		
	}//// End StartExtractionHammingCode
	
	////This function reads a pixel block for each color component.
	void processPixelBlockEmbed(int blockCount,int startColumnIndex, int startRowIndex, 
			        int[][] pixelBlock,String strColor,int bitPosition) throws Exception{
		try{
			//// Get the LSB from each pixel block.
			int binXX=0,binYY=0;
			int nextKappa, dataBit;
			int endRowIndex=(startRowIndex*blockHeight)+blockHeight;
			int endColumnIndex=(startColumnIndex*blockWidth)+blockWidth;
			
			//// Populate pixelBinaryBlock pixel array.
			for(int y=(startRowIndex*blockHeight);y<endRowIndex;y++){
				binXX=0;
				for(int x=(startColumnIndex*blockWidth);x<endColumnIndex;x++){
					pixelBinaryBlock[binXX][binYY]=((pixelBlock[x][y] & bitPosition)>0)?1:0;	
					binXX++;
				}
				binYY++;
			}
			
			Log("\n########################[ START ]#############################\n");
			Log("\n----Before: Pos "+bitPosition+" ---Block "+blockCount+" ("+strColor+")----\n");
			Log(displayHammingCodeMatrix(pixelBlock,blockHeight,blockWidth));
			Log(displayHammingCodeMatrix(pixelBinaryBlock,blockHeight,blockWidth));
			Log("----------------------------------\n");
			
			//// Adjust the parity bits.
			adjustParity(pixelBinaryBlock);
			
			Log("\n----Parity: Pos "+bitPosition+" ---Block "+blockCount+" ("+strColor+")----\n");
			Log(displayHammingCodeMatrix(pixelBinaryBlock,blockHeight,blockWidth));
			Log("----------------------------------\n");
			
			
			//// Search each row for 0 or 1 from left side.
			//// Change the bit according to Kappa.
			for(int y=0;y<blockHeight;y++){				
				boolean bitChanged=false;
				
				nextKappa=getNextKappa();
				//// Complement the bit at kappa position.
				pixelBinaryBlock[nextKappa][y]=(pixelBinaryBlock[nextKappa][y]==1)?0:1;
				Log("Kappa = "+(nextKappa+1)+", ");
				
				dataBit=getNextDataBit(); 
				  
				for(int x=0;x<blockWidth;x++){
					//// Embed the data except the kappa position.
					if(x==nextKappa){
						continue;
					}					
					//// Toggle 0 or 1 value according to kappa.
					if(pixelBinaryBlock[x][y] == ((dataBit==1)?0:1)){
						pixelBinaryBlock[x][y]=dataBit;
						bitChanged=true;
						setNextKappa(x);						
						Log("Data = "+dataBit+", Bit Position = "+(x+1)+"\n");						
						break;
					} 					
				}
				//// If no suitable position found.
				if(bitChanged==false){
					if(dataBit==1){  
						pixelBinaryBlock[blockWidth-1][y]=1;
						setNextKappa(blockWidth-1);
						Log("Adjust: Kappa = "+(blockWidth)+", Data = "+dataBit+", Bit Position = "+(blockWidth)+"\n");
					}else{
						pixelBinaryBlock[0][y]=0;
						setNextKappa(0);
						Log("Adjust: Kappa = "+(1)+", Data = "+dataBit+", Bit Position = "+(1)+"\n");
					}
				}				
				
			}//// End outer for loop					
			 
			binYY=0;
			//// Adjust original pixel array with the pixelBinaryBlock
			for(int y=(startRowIndex*blockHeight);y<endRowIndex;y++){
				binXX=0;
				for(int x=(startColumnIndex*blockWidth);x<endColumnIndex;x++){
					////System.out.println("x= "+x+", y = "+y+"\n");
					//// Change the LSB according to the pixelBinaryBlock value.
					if(pixelBinaryBlock[binXX][binYY]==0){
						if((pixelBlock[x][y] & bitPosition)>0){
							pixelBlock[x][y] -= bitPosition;
						}
																		
					}else if(pixelBinaryBlock[binXX][binYY]==1){
						if((pixelBlock[x][y] & bitPosition)==0){
							pixelBlock[x][y] += bitPosition;
						}
					}	
					
					binXX++;
				}
				binYY++;
			}//// End Adjust original pixel for loop
			
			
			Log("\n----After: Pos "+bitPosition+" ---Block "+blockCount+" ("+strColor+")----\n");
			Log(displayHammingCodeMatrix(pixelBlock,blockHeight,blockWidth));
			Log(displayHammingCodeMatrix(pixelBinaryBlock,blockHeight,blockWidth));
			Log("----------------------------------\n");
			Log("\n##########################[ END ]#############################\n");
		}catch(Exception ex){
			ex.printStackTrace();
			updateMessage("Exception occured in processPixelBlock. "+ex.toString()+strDetails);
			throw new Exception();
		}
	}//// End processPixelBlock  
	
	////This function reads a pixel block for each color component.
	void processPixelBlockExtract(int blockCount,int startColumnIndex, int startRowIndex, 
			        int[][] pixelBlock,String strColor,int bitPosition) throws Exception{
		try{
			//// Get the LSB from each pixel block.
			int binXX=0,binYY=0;
			int nextKappa, dataBit=0;
			int endRowIndex=(startRowIndex*blockHeight)+blockHeight;
			int endColumnIndex=(startColumnIndex*blockWidth)+blockWidth;
			
			/////////////////  Populate pixelBinaryBlock pixel array.  ////////////////////
			for(int y=(startRowIndex*blockHeight);y<endRowIndex;y++){
				binXX=0;
				for(int x=(startColumnIndex*blockWidth);x<endColumnIndex;x++){
					pixelBinaryBlock[binXX][binYY]=((pixelBlock[x][y] & bitPosition)>0)?1:0;	
					binXX++;
				}
				binYY++;
			}
			///////////////////////////////////////////////////////////////////////////////
			
			Log("\n########################[ START ]#############################\n");
			Log("\n----Before: "+"Pos "+bitPosition+"--Block "+blockCount+" ("+strColor+")----\n");
			Log(displayHammingCodeMatrix(pixelBinaryBlock,blockHeight,blockWidth));
			Log("---------------------------------------\n");
				
			////////////// Toggle kappa position and get the embedded value. /////////////// 
			int errorPosition=0;
			for(int y=0;y<blockHeight;y++){	
				////////// Toggle the bit at kappa position.  ////////////////////////
				nextKappa=getNextKappa();				
				pixelBinaryBlock[nextKappa][y]=(pixelBinaryBlock[nextKappa][y]==1)?0:1;
				Log("Kappa = "+(nextKappa+1)+", ");
							
				///////////////// Get the error bit position. /////////////////////////				
				errorPosition=getHammingCodeErrorPosition(pixelBinaryBlock,y,blockWidth);					
				///////////////////////////////////////////////////////////////////////
				
				//// Get the data bit from error position. ////////////////////////////
				//// For No error found
				if( errorPosition == -1 ){	
					Log("No Error Found:");
					
					if(pixelBinaryBlock[getNextKappa()][y]==1){
						dataBit=0;
						setNextKappa(0);
						pixelBinaryBlock[blockWidth-1][y]=0;
					}else{
						dataBit=1;  
						setNextKappa(blockWidth-1);
						pixelBinaryBlock[0][y]=1;
					}
					Log("Error Position = "+(getNextKappa()+1)+", Data Extracted = "+dataBit+"\n");
					//// Append the data bit to the extracted string builder.
					extractedImageDataBits.append((dataBit==1)?"1":"0");						
					 						
				}else{
					dataBit=pixelBinaryBlock[errorPosition][y];
					Log("Error Position = "+(errorPosition+1)+", Data Extracted = "+dataBit+"\n");
					//// Set next kappa position.
					setNextKappa(errorPosition);
					//// Append the data bit to the extracted string builder.
					extractedImageDataBits.append((dataBit==1)?"1":"0");						
					//// Toggle the error bit.
					pixelBinaryBlock[errorPosition][y]=(pixelBinaryBlock[errorPosition][y]==1)?0:1;  						
				}				
							
			}//// End outer for loop					
			///////////////////////////////////////////////////////////////////////////
			
			
			Log("\n----After: "+"Pos "+bitPosition+"--Block "+blockCount+" ("+strColor+")----\n");
			Log(displayHammingCodeMatrix(pixelBinaryBlock,blockHeight,blockWidth));
			Log("----------------------------------\n");
			Log("\n##########################[ END ]#############################\n");
		}catch(Exception ex){
			ex.printStackTrace();
			updateMessage("Exception occured in processPixelBlock. "+ex.toString());
			throw new Exception();
		}
	}//// End processPixelBlockExtract  
	
	//// This function adjust the parity bits.
	void adjustParity(int[][] pixelBinaryBlock){
			/******************************
			The key to the Hamming Code is the use of extra parity bits to allow the identification of a single error. Create the code word as follows:

			Mark all bit positions that are powers of two as parity bits. (positions 1, 2, 4, 8, 16, 32, 64, etc.)
			All other bit positions are for the data to be encoded. (positions 3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 17, etc.)
			Each parity bit calculates the parity for some of the bits in the code word. The position of the parity bit determines the sequence of bits that it alternately checks and skips. 
			Position 1: check 1 bit, skip 1 bit, check 1 bit, skip 1 bit, etc. (1,3,5,7,9,11,13,15,...)
			Position 2: check 2 bits, skip 2 bits, check 2 bits, skip 2 bits, etc. (2,3,6,7,10,11,14,15,...)
			Position 4: check 4 bits, skip 4 bits, check 4 bits, skip 4 bits, etc. (4,5,6,7,12,13,14,15,20,21,22,23,...)
			Position 8: check 8 bits, skip 8 bits, check 8 bits, skip 8 bits, etc. (8-15,24-31,40-47,...)
			Position 16: check 16 bits, skip 16 bits, check 16 bits, skip 16 bits, etc. (16-31,48-63,80-95,...)
			Position 32: check 32 bits, skip 32 bits, check 32 bits, skip 32 bits, etc. (32-63,96-127,160-191,...) etc.
			Set a parity bit to 1 if the total number of ones in the positions it checks is odd. Set a parity bit to 0 if the total number of ones in the positions it checks is even.
			********************************/
		
		for(int y=0;y<blockHeight;y++){
			
			if(typeOfHammingCode.equalsIgnoreCase("(7,4)")){
				if(strParityCheck.equalsIgnoreCase("Odd Parity")){
					pixelBinaryBlock[0][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y])==0?1:0;
					pixelBinaryBlock[1][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y])==0?1:0;
					pixelBinaryBlock[3][y] = (pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y])==0?1:0;
				}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
					pixelBinaryBlock[0][y] = pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y];
					pixelBinaryBlock[1][y] = pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];
					pixelBinaryBlock[3][y] = pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];
				}				
			}else if(typeOfHammingCode.equalsIgnoreCase("(15,11)")){
				if(strParityCheck.equalsIgnoreCase("Odd Parity")){
					pixelBinaryBlock[0][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
							                  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
							                  pixelBinaryBlock[14][y])==0?1:0;
					pixelBinaryBlock[1][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
							                  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
							                  pixelBinaryBlock[14][y])==0?1:0;
					pixelBinaryBlock[3][y] = (pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
							                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
							                  pixelBinaryBlock[14][y])==0?1:0;
					pixelBinaryBlock[7][y] = (pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
							                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^  
							                  pixelBinaryBlock[14][y])==0?1:0;
				}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
					pixelBinaryBlock[0][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
			                  				  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
			                  				  pixelBinaryBlock[14][y]);
					pixelBinaryBlock[1][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
			                  				  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
			                  				  pixelBinaryBlock[14][y]);
					pixelBinaryBlock[3][y] = (pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
			                  				  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
			                  				  pixelBinaryBlock[14][y]);
					pixelBinaryBlock[7][y] = (pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
			                  				  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^  
			                  				  pixelBinaryBlock[14][y]);
				}	
				
			}else if(typeOfHammingCode.equalsIgnoreCase("(31,26)")){
				if(strParityCheck.equalsIgnoreCase("Odd Parity")){
					pixelBinaryBlock[0][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[18][y] ^
	                  pixelBinaryBlock[20][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[24][y] ^
	                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[28][y] ^ 
	                  pixelBinaryBlock[30][y])==0?1:0;
					pixelBinaryBlock[1][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
	                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[25][y] ^
	                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y] )==0?1:0;
					pixelBinaryBlock[3][y] = (pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^
	                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[27][y] ^
	                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y] )==0?1:0;
					pixelBinaryBlock[7][y] = (pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
	                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
	                  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
	                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y])==0?1:0;
					pixelBinaryBlock[15][y] = (pixelBinaryBlock[16][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
					  pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^ pixelBinaryBlock[21][y] ^
					  pixelBinaryBlock[22][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
					  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
					  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
					  pixelBinaryBlock[30][y])==0?1:0;
				}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
					pixelBinaryBlock[0][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[18][y] ^
	                  pixelBinaryBlock[20][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[24][y] ^
	                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[28][y] ^ 
	                  pixelBinaryBlock[30][y] );
					pixelBinaryBlock[1][y] = (pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
	                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[25][y] ^
	                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y] );
					pixelBinaryBlock[3][y] = (pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
	                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^
	                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[27][y] ^
	                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y] );
					pixelBinaryBlock[7][y] = (pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
	                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
	                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
	                  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
	                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
	                  pixelBinaryBlock[30][y] );
					pixelBinaryBlock[15][y] = (pixelBinaryBlock[16][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
					  pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^ pixelBinaryBlock[21][y] ^
					  pixelBinaryBlock[22][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
					  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
					  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
					  pixelBinaryBlock[30][y] );
				}	
				
			}
			
		}//// End for loop
		
	}//// End adjustParity
	
	//// Check initial settings are correct or not.
	void initialCheckForHammingCode() throws Exception{		
		
		BufferedImage coverFile = ImageIO.read(new File(pathCoverFile));
		BufferedImage secretFile = ImageIO.read(new File(pathSecretFile));
		
		int blockHeight1=(int)coverFile.getHeight()/blockHeight;
		int blockWidth1=(int)coverFile.getWidth()/blockWidth;
		int blockCount=blockHeight1*blockWidth1;
		int selectedBits=0;
		if(selectedEmbeddingBits==1||selectedEmbeddingBits==2||selectedEmbeddingBits==4){
			selectedBits=1;
		}else if(selectedEmbeddingBits==3||selectedEmbeddingBits==5||selectedEmbeddingBits==6){
			selectedBits=2;
		}else if(selectedEmbeddingBits==7){
			selectedBits=3;  
		}			
		
		int dataBitCount=blockCount*(blockHeight*3*selectedBits);
		int pixelCount=(dataBitCount/24);			
		if((secretFile.getWidth()*secretFile.getHeight()) != pixelCount){
			updateMessage("Total number of pixel in the secret file should be = "+
		                  pixelCount+strDetails);
			setSecretImageSize(""+pixelCount);
			updateProgress(100,100);
			throw new Exception();
		}
		
	}//// End initialCheckForHammingCode
	
	//// Returns next kappa position
	int getNextKappa(){ 
		return kappa; 
	}//// End nextKappa 
	
	void setNextKappa(int i){
		kappa=i;
	}
	
	//// Get the next stego image data bit.
	int getNextDataBit() throws Exception{
		try{
			int dataBit=0;
			dataBit=Integer.parseInt(secretImageDataBits.substring(countDataBit, countDataBit+1));
			countDataBit++;
			return dataBit;
		}catch(Exception ex){
			//ex.printStackTrace();
			//updateMessage("Exception occured in getNextDataBit. "+ex.toString()+strDetails);
			//throw new Exception();
			return 1;
		}		
	}
		
	////Create PNG file from pixel arrays
    void CreateImageFileFromPixelArrayHC(File imageFile, int[][] pixelMatrixRED,
    								int[][] pixelMatrixGREEN,int[][] pixelMatrixBLUE,
    								int imageWidth, int imageHeight) throws Exception {
	      
    	updateMessage("Saving Image File....."); 
    	
  	   	BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);
  	    	
  	    int progressCount=0;
  	    int totalProgress=imageHeight*imageWidth;
	   
  	    for(int y = 0; y < imageHeight; y++) {
  	    	for(int x = 0; x < imageWidth; x++) {
  	    		outputImage.setRGB(x, y, (pixelMatrixRED[x][y]<< 16) |(pixelMatrixGREEN[x][y] << 8) | (pixelMatrixBLUE[x][y]));
  	    		updateProgress(progressCount++,totalProgress);
  	    	}
  	    }  	   
  	   
  	    try {
			ImageIO.write(outputImage, "PNG", imageFile);						
		} catch (IOException ex) {
			ex.printStackTrace();
			updateMessage("Exception occured in CreateImageFileFromPixelArray. "+ex.toString());
			throw new Exception();
		} 
		
   }//// End CreateImageFileFromPixelArray
       		
	//// Get the error bit position from the array.
	int getHammingCodeErrorPosition(int[][] pixelBinaryBlock,int y,int width){
		
		int sum=0;
		int bitValue=0;
		
		if(typeOfHammingCode.equalsIgnoreCase("(7,4)")){
			if(strParityCheck.equalsIgnoreCase("Odd Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];				
				sum += bitValue*4;
							
				return ((width-1)-sum);
			}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y];
				sum += bitValue*4;	
				
				return (sum-1);
			}				
		}
		
		if(typeOfHammingCode.equalsIgnoreCase("(15,11)")){
			if(strParityCheck.equalsIgnoreCase("Odd Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*4;
				bitValue = pixelBinaryBlock[7][y] ^ pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
				   pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*8;
				
				return ((width-1)-sum);
			}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
				   pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*4;
				bitValue = pixelBinaryBlock[7][y] ^ pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
				   pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^ pixelBinaryBlock[14][y];
				sum += bitValue*8;
				
				return (sum-1);
			}			
		}
		
		if(typeOfHammingCode.equalsIgnoreCase("(31,26)")){
			if(strParityCheck.equalsIgnoreCase("Odd Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[18][y] ^
		                  pixelBinaryBlock[20][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[24][y] ^
		                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[28][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
		                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[25][y] ^
		                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^
		                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[27][y] ^
		                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*4;
				bitValue = pixelBinaryBlock[7][y] ^ pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
		                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
		                  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
		                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				 sum += bitValue*8;
				 bitValue = pixelBinaryBlock[15][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
						  pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^ pixelBinaryBlock[21][y] ^
						  pixelBinaryBlock[22][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
						  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
						  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
						  pixelBinaryBlock[30][y];
				 sum += bitValue*16;
				 
				return ((width-1)-sum);
			}else if(strParityCheck.equalsIgnoreCase("Even Parity")){
				bitValue = pixelBinaryBlock[0][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[8][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[12][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[18][y] ^
		                  pixelBinaryBlock[20][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[24][y] ^
		                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[28][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*1;
				bitValue = pixelBinaryBlock[1][y] ^ pixelBinaryBlock[2][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
		                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[25][y] ^
		                  pixelBinaryBlock[26][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*2;
				bitValue = pixelBinaryBlock[3][y] ^ pixelBinaryBlock[4][y] ^ pixelBinaryBlock[5][y] ^ pixelBinaryBlock[6][y] ^
		                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^
		                  pixelBinaryBlock[21][y] ^ pixelBinaryBlock[22][y] ^ pixelBinaryBlock[27][y] ^
		                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				sum += bitValue*4;
				bitValue = pixelBinaryBlock[7][y] ^ pixelBinaryBlock[8][y] ^ pixelBinaryBlock[9][y] ^ pixelBinaryBlock[10][y] ^
		                  pixelBinaryBlock[11][y] ^ pixelBinaryBlock[12][y] ^ pixelBinaryBlock[13][y] ^
		                  pixelBinaryBlock[14][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
		                  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
		                  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
		                  pixelBinaryBlock[30][y];
				 sum += bitValue*8;
				 bitValue = pixelBinaryBlock[15][y] ^ pixelBinaryBlock[16][y] ^ pixelBinaryBlock[17][y] ^ pixelBinaryBlock[18][y] ^
						  pixelBinaryBlock[19][y] ^ pixelBinaryBlock[20][y] ^ pixelBinaryBlock[21][y] ^
						  pixelBinaryBlock[22][y] ^ pixelBinaryBlock[23][y] ^ pixelBinaryBlock[24][y] ^
						  pixelBinaryBlock[25][y] ^ pixelBinaryBlock[26][y] ^ pixelBinaryBlock[27][y] ^
						  pixelBinaryBlock[28][y] ^ pixelBinaryBlock[29][y] ^ 
						  pixelBinaryBlock[30][y];
				 sum += bitValue*16;
				 
				 return (sum-1);
			}			
		}     		
		
		return 0;
	}//// End getHammingCodeErrorPosition
	
	////Get embedding data
	String getDataFromSecretImageFile(BufferedImage inputImage){
		updateMessage("Getting Embedded Data From Stego File ....");
		
		String strStegoImageBits="";
		
		//// Get the binary string from the input image 
		strStegoImageBits = ConvertImageToBinaryStringHC(inputImage);
		
		updateMessage("Getting Embedded Data Completed.");
		return strStegoImageBits;
	}//// End getEmbeddingDataFromStegoImageFile

	////Convert image to bit string
	String ConvertImageToBinaryStringHC(BufferedImage inputImage){
		int imageWidth=inputImage.getWidth();
		int imageHeight=inputImage.getHeight();
		StringBuilder sbImageString=new StringBuilder();
				
		updateMessage("Converting Image To Binary String...");
		int progressCount=0;
		int totalProgress=imageHeight*imageWidth;
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				
				Color c = new Color(inputImage.getRGB(x, y));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				
				sbImageString.append(ConvertToNBitBinaryString(r,8));
				sbImageString.append(ConvertToNBitBinaryString(g,8));
				sbImageString.append(ConvertToNBitBinaryString(b,8));	
				
				updateProgress(progressCount++,totalProgress);
			}  
		}
		updateMessage("Image To Binary String Conversion Completed.");
		return sbImageString.toString();
	}//// End ConvertImageToBinaryString
	
	////Convert to binary string of 8 bits
	String ConvertToNBitBinaryString(int n,int nBit){
		StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
		int padding = nBit - binaryStringBuilder.length();
		for(int i=0;i<padding;i++){
			binaryStringBuilder.insert(0, "0");
		}		
		return binaryStringBuilder.toString();		
	}//// End ConvertToBinaryString		
	
	////This function reads 3x3 pixel block from the image
	String displayHammingCodeMatrix(int[][] matrix,int row,int col){		
		StringBuilder strMatrix=new StringBuilder();
		String matrix1="";
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				matrix1=String.format("%4d", matrix[j][i]);
				strMatrix.append(matrix1);
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString();
	}//// End displayHammingCodeMatrix
	
	////Convert binary string to image
	void ConvertBinaryStringToImageHC(String fileName, StringBuilder binaryString, 
	           int bitSelection, int imageWidth, int imageHeight) throws Exception
	{
	
		updateMessage("Creating Image "+imageWidth+"x"+imageHeight+" From Binary String...");		
		try{
			
			int red=0,green=0,blue=0;
			int pixelColor=0;
			int[] pixels=new int[imageWidth * imageHeight];
			
			System.out.println("Data String Length = "+binaryString.length()+"\n");
			
			//// Create the empty output image file
			BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
			
			int totalProgress=imageHeight*imageWidth;
			int progressCount=0;
			int substringCount=0;
			
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){
				
					System.out.println("X= "+x+", Y= "+y+"\n");
														
					red=Integer.parseInt(binaryString.substring(substringCount, substringCount+8), 2);
					green=Integer.parseInt(binaryString.substring(substringCount+8, substringCount+16),2);
					blue=Integer.parseInt(binaryString.substring(substringCount+16, substringCount+24),2);
					
					//// Get the pixel color
					pixelColor=(red << 16) | (green << 8) | blue;
					pixels[x + y * imageWidth]=pixelColor;
			
					substringCount+=24;
					updateProgress(progressCount++,totalProgress);
					
				}
			}		
			
		    int[] bufferPixels = ((DataBufferInt) outputImage.getRaster().getDataBuffer()).getData();
		    for(int i = 0; i < pixels.length; i++) {
		    	bufferPixels[i] = pixels[i];
		    }
		    outputImage.flush();
		   
		    System.out.println("Length = "+bufferPixels.length+"\n");
		   
		    System.out.println("File = "+fileName+"\n");
			//// Create the output image file and save it in a file
			File imageFile = new File(fileName);
			ImageIO.write(outputImage, "PNG", imageFile);
			updateMessage("Image Extracted Successfully");
			
		}catch(Exception ex){
			ex.printStackTrace();
			updateProgress(100,100);
			updateMessage("Exception occured in ConvertBinaryStringToImageHC. "+ex.toString());
			System.out.println("Exception occured in ConvertBinaryStringToImageHC. "+ex.toString());
			throw new Exception();
		}
		
	}//// End ConvertBinaryStringToImage
	
	////Displays MessageBox
	void MessageBox(String message){
		MessageBox(message,"ERROR");			
	}
	void MessageBox(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox
	
	void Log(String strLog){
		if(logEnabled==false){
			return;
		}
		sbLog.append(strLog);
	}   
	
	////This function creates and writes in the matrix index file.
	void WriteLogHC(StringBuilder strContent){		
		try(FileWriter fw = new FileWriter(strFileDirectoryHC+"\\"+logFile, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw)){
			out.print(strContent);
		} catch (IOException e) {
		    MessageBox("Error Writing "+strFileDirectoryHC+"\\"+logFile);
		    return;
		}		
	}//// End WriteMatrixIndexFile
	
	////This function returns a file name without extension
    String GetCoverFileNameWithoutExtension(File file){
    	String fileName = file.getName();
    	int pos = fileName.lastIndexOf(".");
    	if (pos > 0) {
    	    fileName = fileName.substring(0, pos);
    	}
    	return fileName;
    }//// End GetFileNameWithoutExtension
    
	////Create a output file from a string
	boolean CreateLogFile(String logFileName){	
		
		logFile=logFileName;
		PrintWriter fw=null;
		BufferedWriter bw=null;		
		try{
			fw = new PrintWriter(strFileDirectoryHC+"\\"+logFileName);
			bw = new BufferedWriter(fw);
			//// Write the message to the output file		
			bw.write("");					
			
			bw.close();
			fw.close();
		}catch(Exception ex){
			return false;
		}				    
		return true;
	}//// End createWMOutputTextFile
	
	public String getSecretImageSize() {
        return secretImageSize.get();
    }

    public SimpleStringProperty secretImageSizeProperty() {
        return secretImageSize;
    }

    public void setSecretImageSize(String n) {
        secretImageSize.set(n);
    }
    
	@Override
	protected Void call() throws Exception {		
			
		if(typeEmbedExtract.equalsIgnoreCase("Embed")){
			StartEmbeddingHammingCode();
			Thread.currentThread().interrupt();
		}else if(typeEmbedExtract.equalsIgnoreCase("Extract")){
			StartExtractionHammingCode();
			Thread.currentThread().interrupt();
		}		
		return null;
	}	
	
}
///////////////////////////////////////////////////////////////////////////////////////

////////////////////////  Class for Weight Matrix ///////////////////////////////
class ProjectWM extends Task<Void>
{	
	BufferedImage coverImage;
	boolean DEBUG_MODE=true;
	int ROTATION;
	StringBuilder sbEmbeddingDataBits;
	StringBuilder txtLog;
	SelectionMatrix selectionMatrix;
	PCSteganography mainFrame;
	int maxRow;
	int maxCol;
	String strFileDirectory;
	public boolean IsLOG;
	StringBuilder sbMIFContentMain;
	StringBuilder sbMIFContentTemp;
	StringBuilder sbMIFFileData;
	String[] strMIFData;
	int countForMIF;
	BufferedImage coverImageExtracted;
	public int numberOfBitsWM;
	File coverFileName;
	String steganoType;
	WTMatrix matrix;
	String extractFilePath;	
	String coverFilePath;
	String stegoFilePath;
	int bitCount;
	
	ProjectWM(PCSteganography frame,File coverFile, BufferedImage cImage, int rotation, int col, int row, 
			  TextArea txtArea,PCSteganography pcs, SelectionMatrix sm, boolean logSelected){
		mainFrame=frame;	
		coverImage=null;
		steganoType="";
		extractFilePath="";
		coverImage=cImage;
		coverImageExtracted=new BufferedImage(cImage.getWidth(),cImage.getHeight(),
											  BufferedImage.TYPE_INT_RGB);
		strFileDirectory=coverFile.getParent(); 
		coverFileName=coverFile;
		ROTATION=rotation;
		txtLog=new StringBuilder();
		selectionMatrix=sm;
		mainFrame=pcs;
		maxRow=row;
		maxCol=col;
		IsLOG=logSelected;
		sbMIFContentMain=new StringBuilder();
		sbMIFContentTemp=new StringBuilder();
		sbMIFFileData=new StringBuilder();	
		countForMIF=0;
		numberOfBitsWM=4;//(int)(Math.log(2*maxRow*maxCol) / Math.log(2));
		bitCount=0;
	}
	
	////This function initializes the components.
	void InitializeProjectWM(File coverFile, BufferedImage cImage, int rotation, int col, int row, 
			  TextArea txtArea,PCSteganography pcs, SelectionMatrix sm,boolean mifSelected)
	{
		coverImage=cImage;
		coverImageExtracted=new BufferedImage(cImage.getWidth(),cImage.getHeight(),
											  BufferedImage.TYPE_INT_RGB);
		strFileDirectory=coverFile.getParent(); 
		coverFileName=coverFile;
		ROTATION=rotation;
		txtLog=new StringBuilder();
		selectionMatrix=sm;
		mainFrame=pcs;
		maxRow=row;   
		maxCol=col;
		sbMIFContentMain=new StringBuilder();
		sbMIFContentTemp=new StringBuilder();
		sbMIFFileData=new StringBuilder();	
		countForMIF=0;
		numberOfBitsWM=4;//(int)(Math.log(2*maxRow*maxCol) / Math.log(2));		
	}
	
	void setCoverImage(BufferedImage cImage){
		coverImage=cImage;
	}
	
	void WriteLogWM(String strLog){
		if(IsLOG){
			txtLog.append("\n"+strLog);
		}		
	}
	  
	////Get first N bits of a binary string
	String GetFirstNBitsWM(int nBit){
		String strTemp;		
		try{
			//// Strip first N bits from the string and return it
			strTemp = sbEmbeddingDataBits.substring(bitCount, bitCount+nBit);
			bitCount += nBit;
			return strTemp;
		}catch(Exception ex){
			return "0000";			
		}			
	}//// End getFirstNBits
		
	//// This function checks the file type and call the appropriate embed function
	void InitializeEmbeddingWM(String cFilePath,String sFilePath, WTMatrix mtrx){
	
		updateProgress(0,1000);
		updateMessage("Starting Embedding Process");
		
		matrix=mtrx;
		steganoType="Embed";
		
		coverFilePath=cFilePath;
		stegoFilePath=sFilePath;		
		
	}//// End processEmbeddingWM5x5
	
	//// This function embeds stegoImage bits in a coverImage
	void StartEmbeddingWM(WTMatrix matrix){			
		
		long start = System.nanoTime();
		updateMessage("Starting Embedding Process...");		
		
		if( coverImage==null ){
			MessageBox("Please select a valid image");
			return; 
		}
		
		String stegoFileType = PCUtility.getFileType(stegoFilePath); 
		 
		//// Get the Stego file type and the data it binary string
		String embeddingDataBits = getEmbeddingData(stegoFilePath); 
		sbEmbeddingDataBits=new StringBuilder();
		sbEmbeddingDataBits.append(embeddingDataBits); 
		
		//// Write inserted 4 bits per round in the log
		StringBuilder sbInsertedBits=new StringBuilder(embeddingDataBits);
		WriteLogWM(BitSeparation(sbInsertedBits,numberOfBitsWM));
		
		//// Return if input file is invalid
		if(embeddingDataBits.equalsIgnoreCase("")){
			return;
		}
		
		if(stegoFileType.equals("png")){
			updateMessage("Starting Setgo Image Processing");			
		}
		
		int imageWidth=coverImage.getWidth()/maxCol;
		int imageHeight=coverImage.getHeight()/maxRow;
		
		int totalProgress=imageWidth*imageHeight;
		
		int imageWidthAll=coverImage.getWidth();
		int imageHeightAll=coverImage.getHeight();
		Integer[][] pixelMatrixRED=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixGREEN=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixBLUE=new Integer[imageHeightAll][imageWidthAll];
		
		//// Copy the pixels into the matrix
		for(int y=0;y<imageHeightAll;y++){	
			for(int x=0;x<imageWidthAll;x++){ 
				int pixelValue=coverImage.getRGB(x, y);
				Color color=new Color(pixelValue); 
				
				pixelMatrixRED[y][x]=color.getRed();
				pixelMatrixGREEN[y][x]=color.getGreen();
				pixelMatrixBLUE[y][x]=color.getBlue();
			}
		}
		
		int[][] MultipliedMatrix=new int[maxRow][maxCol];
		int[][] WeightMatrix=new int[maxRow][maxCol];
		
		int progressIndicator=0;
		int block=1;
		
		updateMessage("Running Pixel Loop To Store Data...");
		//// Read the 3x3 block of the input image 
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){				
				
				WriteLogWM("####################   Block "+block+" ####################");
				
				WriteLogWM("-------------------   RED  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogWM("-------------------------------------------------");
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockWM("RED",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixRED,rotate,x,y);
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogWM("-------------------------------------------------");
				}
				sbMIFContentMain.append(sbMIFContentTemp);
				sbMIFContentTemp.setLength(0);
						
				WriteLogWM("-------------------   GREEN  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogWM("-------------------------------------------------");
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockWM("GREEN",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixGREEN,rotate,x,y);
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogWM("-------------------------------------------------");
				
				}
				sbMIFContentMain.append(sbMIFContentTemp);
				sbMIFContentTemp.setLength(0);
				
				WriteLogWM("-------------------   BLUE  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogWM("-------------------------------------------------");
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockWM("BLUE",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixBLUE,rotate,x,y);
					WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogWM("-------------------------------------------------");

				}	
				sbMIFContentMain.append(sbMIFContentTemp+"\n");
				sbMIFContentTemp.setLength(0);
				
				//// Update the progress with this thread's updateProgress property.
				updateProgress(progressIndicator++,totalProgress);
				
				WriteLogWM("##################################################");
				block++;
			}	
			//// Write the string in the MIF file  
			////WriteMatrixIndexFile(sbMIFContentMain);
			sbMIFContentMain.setLength(0);
		}							
							
		updateMessage("Completed Pixel Loop.");
		
		//// Saving the image files
		String fileName = PCUtility.GetFileNameWithoutExtension(coverFileName);
        File file = new File(strFileDirectory+"\\"+fileName+"-WM.png");
        
        if(CreateImageFileFromPixelArrayWM(file, pixelMatrixRED,pixelMatrixGREEN,
        		            pixelMatrixBLUE,imageWidthAll,imageHeightAll)==false){
        	MessageBox("Error creating output WM file!!!");
        }
           
    	PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+fileName+"-InLog.txt",txtLog.toString());
    	
    	long stop = System.nanoTime();
    	long duration =(stop-start) ;
             
    	updateMessage("Embedding Process Completed. Time Taken = "+TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds.");  	
		
	}//// End stegoImageEmbedding
	  
	//// This function stores 4 bits in a 3x3 pixel block
	void StoreDataBitsInPixelBlockWM(String pixelColor, WTMatrix matrix,int[][] MultipliedMatrix, 
			int[][] WeightMatrix, Integer pixelMatrix[][],int rotation, int x,int y)
	{
		//// Populate the matrix
		WeightMatrix=matrix.getNextMatrix();
		
		WriteLogWM("Weight Matrix:-\n"+DisplayMatrix(WeightMatrix,maxRow,maxCol,0,0));
				 
		boolean recalculate=false;
		
		String messageNBits = GetFirstNBitsWM(numberOfBitsWM);
		
		int modVal1= (int)Math.pow(2, numberOfBitsWM-1);
		int modVal2=(int)Math.pow(2, numberOfBitsWM);		
			
		do{
			recalculate=false;
			//// Multiply weight matrix with the data pixel matrix only   
			MultiplyWMAndPixelMatrixWM(MultipliedMatrix,pixelMatrix,WeightMatrix,x,y); 					
			//// Sum of all elements of the matrix
			int sumOfMatrixElements = SumOfMatrixElements(MultipliedMatrix,maxRow,maxCol);			
			//// Modulo of sumOfMatrixElements			
			int SUM = CalculateMod(sumOfMatrixElements, modVal2);
			int intNBits=Integer.parseInt(messageNBits, 2);  
			  
			//// Calculate d = (10011 - SUM)
			int d = intNBits - SUM;
			int modD=0;
			int sign=0;
			int originalD=d;
			
			if( d>modVal1 ){
				d=modVal2-d;	
				modD = (int)Math.abs(d);
				sign=-1;
			
			}else if(d>0){
				modD = (int)Math.abs(d);
				sign=+1;
			
			}else if(d<-modVal1){
				d=modVal2+d;	
				modD = (int)Math.abs(d);
				sign=+1;
			
			}else if(d<0){
				modD = (int)Math.abs(d);
				sign=-1;
		
			}
			
			//// Search position of modD in the weightMatrix,
			//// and add or subtract 1 with the element in the 
			//// same position in the pixel3x3Matrix matrix.
			int i=0,j=0;
			outerLoop:
				for( i=0; i<maxRow; i++ ){
					for( j=0; j<maxCol; j++ ){ 						
						if(selectionMatrix.isDataPixel(j,i)){								
							if( WeightMatrix[i][j] == modD ){						
								if( d>0 ){
									if(pixelMatrix[maxRow*y+i][maxCol*x+j]==255)
									{								  
										pixelMatrix[maxRow*y+i][maxCol*x+j]=254;
										recalculate=true;
										WriteLogWM("Trying For Another Pixel=255");										
										continue;										
									}else{
										pixelMatrix[maxRow*y+i][maxCol*x+j] += sign;										
										break outerLoop;
									}								
								}else if(d<0){
									if(pixelMatrix[maxRow*y+i][maxCol*x+j]==0){								
										pixelMatrix[maxRow*y+i][maxCol*x+j]=1;
										recalculate=true;
										WriteLogWM("Trying For Another Pixel=0");
										continue;										
									}else{
										pixelMatrix[maxRow*y+i][maxCol*x+j] += sign;
										break outerLoop;
									}		
								}
							}						
						}
						
					}//// End inner for loop
				}//// End outer for loop
			/////////////////////////////////////////////////////// 
			WriteLogWM(pixelColor+"::Message:- "+messageNBits+"::d = "+d+"::originalD = "+originalD);
			
			////Change the infoPixelMatrix according to d and modD
			//// Change the matrix for more than 1 round only
			ChangeInfoPixelMatrixWM(rotation,sign,modD-1,pixelMatrix);
			
		}while(recalculate==true);			

	}//// End StoreDataBitsInPixelBlock

	//// This function creates and writes in the matrix index file.
	void WriteMatrixIndexFileWM(StringBuilder strContent){
		int ret=PCUtility.AppendToTextFile(strFileDirectory+"\\"+"MIF.txt", strContent);
		if(ret==-1){
			updateMessage("Error Writing Matrix Index File!!!!!!!!");
			return;
		}
	}//// End WriteMatrixIndexFile
	
	////Change the last 3 bits according to the position of changed pixel
	void ChangeInfoPixelMatrixWM(int rotation, int sign, int modD, Integer pixelMatrix[][])
	{		
		int posX = selectionMatrix.infoPixelMatrix.get(rotation).getX();
		int posY = selectionMatrix.infoPixelMatrix.get(rotation).getY();
				
		int currentPixelValue=pixelMatrix[ posY ][ posX ];
		WriteLogWM("X = "+posX+", Y = "+posY+"::infoPixelValue change = "+currentPixelValue);
		
		//// Change last 3 bits value to store the position
		currentPixelValue=(currentPixelValue & 248) + modD;		   
		
		if(sign<0){
			currentPixelValue=(currentPixelValue & 231); // set bit 00
		}else if(sign==0){
			currentPixelValue=(currentPixelValue & 231) + 8; // set bit 01
		}else if(sign>0){
			currentPixelValue=(currentPixelValue & 231) + 16; // set bit 10
		}

		//// Set the changed pixel value
		pixelMatrix[posY][posX]=currentPixelValue;		
	}
	//// End ChangePixelMatrix 
	
	////This function writes pixel block from the image
	void WritePixelBlockWM(String pixelColor, int[][] pixelMatrix,int col,int row)
	{	
		
		for(int y=0;y<maxRow;y++){
			for(int x=0;x<maxCol;x++){		
				//// Change for data matrix
				if(selectionMatrix.isDataPixel(x,y)){
					
					Color c = new Color(coverImage.getRGB(maxCol*col+x, maxRow*row+y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					int pixelData=0;					
					
					if(pixelColor.equalsIgnoreCase("red")==true){
						pixelData=(pixelMatrix[y][x] << 16) | (g << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("green")==true){
						pixelData=(r << 16) | (pixelMatrix[y][x] << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("blue")==true){
						pixelData=(r << 16) | (g << 8) | pixelMatrix[y][x];
					}
					//// Change the pixel data in the original image
					coverImage.setRGB(maxCol*col+x, maxRow*row+y, pixelData);
					
				}//// Change for info matrix
				else if(selectionMatrix.isInfoPixel(x,y)){
					
					Color c = new Color(coverImage.getRGB(maxCol*col+x, maxRow*row+y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					int infoData=0;					
					
					if(pixelColor.equalsIgnoreCase("red")==true){
						infoData=(pixelMatrix[y][x] << 16) | (g << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("green")==true){
						infoData=(r << 16) | (pixelMatrix[y][x] << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("blue")==true){
						infoData=(r << 16) | (g << 8) | pixelMatrix[y][x];
					}
					//// Change the info data in the original image.
					coverImage.setRGB(maxCol*col+x, maxRow*row+y, infoData);
				}		
			}
		}  
		
	}//// End WritePixelBlock 
	
	////This function multiplies weight matrix and pixel data matrix
	void MultiplyWMAndPixelMatrixWM(int[][] multipliedMatrix,Integer[][] pixelMatrix,
			                        int[][] WeightMatrix,int x,int y){
			
		for(int i=0;i<maxRow;i++){
			for(int j=0;j<maxCol;j++){
				multipliedMatrix[i][j]=pixelMatrix[maxRow*y+i][maxCol*x+j] * WeightMatrix[i][j];
			}
		}			
	}//// End multiplyWMAndDataPixelMatrix	
		
	////This function is for extract in Weight Matrix
	void InitializeExtractionWM(String filePath,WTMatrix mtrx){
	
		updateMessage("Starting Extraction Process.....");
		
		extractFilePath=filePath;
		String stegoFileType = PCUtility.getFileType(filePath);
		
		if(stegoFileType.equals("png")){
			//// clear previous log
			txtLog=new StringBuilder();
			steganoType="Extract";
			matrix=mtrx;
		}else if(stegoFileType.equals("wav")){
			MessageBox("Starting Setgo Audio Processing");			
		}else if(stegoFileType.equals("mp4")){
			MessageBox("Starting Setgo Video Processing");
			
		}		
	}//// End extractWeightMatrix
	
	////Extraction of data from image
	void StartExtractionWM(WTMatrix matrix, String filePath){		
		
		try{
			updateMessage("Starting Extraction Process.....");
			long start = System.nanoTime();
			StringBuilder sbStegoMessage=new StringBuilder();		
			
			int imageWidthExt=coverImage.getWidth()/maxCol;
			int imageHeightExt=coverImage.getHeight()/maxRow;
			int totalProgress=imageWidthExt*imageHeightExt;
			
			
			int imageWidthAll=coverImage.getWidth();
			int imageHeightAll=coverImage.getHeight();
			Integer[][] pixelMatrixRED=new Integer[imageHeightAll][imageWidthAll];
			Integer[][] pixelMatrixGREEN=new Integer[imageHeightAll][imageWidthAll];
			Integer[][] pixelMatrixBLUE=new Integer[imageHeightAll][imageWidthAll];		
			
			//// Copy the pixels into the matrix
			for(int y=0;y<imageHeightAll;y++){	
				for(int x=0;x<imageWidthAll;x++){ 
					int pixelValue=coverImage.getRGB(x, y);
					Color color=new Color(pixelValue); 
					
					pixelMatrixRED[y][x]=color.getRed();
					pixelMatrixGREEN[y][x]=color.getGreen();
					pixelMatrixBLUE[y][x]=color.getBlue();
				}
			}
			
			updateMessage("Extracting Data From Image File....");
			int progressIndicator=0;
			//// Read the pixel block of the input image 
			for(int y=0;y<imageHeightExt;y++){	
				for(int x=0;x<imageWidthExt;x++){ 
	    			
					StringBuilder singlePixelData=new StringBuilder();
	
					for(int rotate=ROTATION-1;rotate>=0;rotate--)
					{
						WriteLogWM("------------------ RED Pixel -----------------------");
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
						GetPixelValueWM(rotate,singlePixelData,matrix,pixelMatrixRED,x,y);
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
						WriteLogWM("-------------------------------------------------");
					}
					sbStegoMessage.append(singlePixelData.toString());	 
					singlePixelData.setLength(0);
					
					for(int rotate=ROTATION-1;rotate>=0;rotate--)
					{
						WriteLogWM("------------------ GREEN Pixel -----------------------");
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
						GetPixelValueWM(rotate,singlePixelData,matrix,pixelMatrixGREEN,x,y);
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
						WriteLogWM("-------------------------------------------------");
					}
					sbStegoMessage.append(singlePixelData.toString());
					singlePixelData.setLength(0);
					
					for(int rotate=ROTATION-1;rotate>=0;rotate--)
					{
						WriteLogWM("------------------ BLUE Pixel -----------------------");
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
						GetPixelValueWM(rotate,singlePixelData,matrix,pixelMatrixBLUE,x,y);
						WriteLogWM(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
						WriteLogWM("-------------------------------------------------");
					}				
					sbStegoMessage.append(singlePixelData.toString());
									
					//// Update the progress with this thread's updateProgress property.
					updateProgress(progressIndicator++,totalProgress);	
				}			   
			}
			
			updateMessage("Extracted Data From Image File Successfully.");
			
			String fileName=PCUtility.GetFileNameWithoutExtension(coverFileName);
			fileName=fileName.replace("-WM", "");
			
			String strMessage="";
			String inputFileType = PCUtility.getFileType(filePath);
			
			long stop = System.nanoTime();
			
			//// Display the message in the secret message text box
			if(inputFileType.equalsIgnoreCase("txt")){
				MessageBox("Extracted File Type is TXT");
				strMessage = PCUtility.binaryArrayToCharacter(sbStegoMessage.toString(),8);
				PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+"wmOutput.txt", strMessage);
			}else if(inputFileType.equalsIgnoreCase("png")){  
				
				updateMessage("Creating Extracted Stego File.");
				try{
					ConvertBinaryStringToImageWM(strFileDirectory+"\\"+fileName+"-Stego.png", 
							 					 sbStegoMessage,
							 					 mainFrame.getOutputImageWidth(),
							 					 mainFrame.getOutputImageHeight());
					
				}catch(Exception ex){
					updateMessage("Error occured during ConvertBinaryStringToImageWM!"+
								  "(Bits="+sbStegoMessage.length()+")"+
								  ex.toString());
					System.out.println("Error saving output file ("+
					                   mainFrame.getOutputImageWidth()+"x"+
					                   mainFrame.getOutputImageHeight()+")");
					ex.printStackTrace();
					return;
				}
				updateMessage("Stego File Extracted Successfully.");
				
				PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+fileName+"-OutLog.txt",txtLog.toString());
				updateMessage("LOG File Created successfully.");
			}
			
			long duration =(stop-start) ;
			updateMessage("Extraction Process Completed. Time Taken = "+TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds.");
			
		}catch(Exception ex){
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
	}//// End StegoImageExtractionWM
	
	////Convert binary string to image
	void ConvertBinaryStringToImageWM(String fileName, StringBuilder binaryString, 
	        int imageWidth, int imageHeight) throws Exception
	{
	
		int red=0,green=0,blue=0;
		int pixelColor=0;
		int[] pixels=new int[imageWidth * imageHeight];
		
		//// Create the empty output image file
		BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight,
		                        BufferedImage.TYPE_INT_RGB);
			
		int totalProgress=imageHeight*imageWidth;
		int progressCount=0;  
		
		String strBinaryString=binaryString.toString();
		
		int substringCount=0;
		//outerLoop:
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				
				red=Integer.parseInt(strBinaryString.substring(substringCount, substringCount+8), 2);
				green=Integer.parseInt(strBinaryString.substring(substringCount+8, substringCount+16),2);
				blue=Integer.parseInt(strBinaryString.substring(substringCount+16, substringCount+24),2);
				
				//// Get the pixel color
				pixelColor=(red << 16) | (green << 8) | blue;
				pixels[x + y * imageWidth]=pixelColor;
		
				substringCount+=24;
				updateProgress(progressCount++,totalProgress);
			}
		}		
		
	   int[] bufferPixels = ((DataBufferInt) outputImage.getRaster().getDataBuffer()).getData();
	   for(int i = 0; i < pixels.length; i++) {
		   bufferPixels[i] = pixels[i];
	   }
	   outputImage.flush();
		   
		//// Create the output image file and save it in a file
		File imageFile = new File(fileName);
		try {
			ImageIO.write(outputImage, "PNG", imageFile);
			updateMessage("ExStego.png" + " Image Extracted Successfully");
			
		} catch (IOException e) {
			MessageBox("ExStego.png" + " File Creation Error!!!");
			e.printStackTrace();
		}       
	
	}//// End ConvertBinaryStringToImage
	
	////Create PNG file from pixel arrays
    boolean CreateImageFileFromPixelArrayWM(File imageFile, Integer[][] pixelMatrixRED,
      Integer[][] pixelMatrixGREEN,Integer[][] pixelMatrixBLUE, int imageWidth, int imageHeight)
   {
	      
	   ///////////////////////////////////////////////////////////////////
	   int[] pixels=new int[imageWidth * imageHeight];
	   
   	   //// Create the empty output image file
  	   BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);
  	    	
  	   int progressCount=0;
	   int totalProgress=imageHeight*imageWidth;
  	   for(int y = 0; y < imageHeight; y++) {
  		   for(int x = 0; x < imageWidth; x++) {
  			   pixels[x + y * imageWidth]=(pixelMatrixRED[y][x]<< 16) | 
  					   					  (pixelMatrixGREEN[y][x] << 8) | 
  					   					  (pixelMatrixBLUE[y][x]);
  			   updateProgress(progressCount++,totalProgress);
  		   }
  	   }
  	   
  	   int[] bufferPixels = ((DataBufferInt) outputImage.getRaster().getDataBuffer()).getData();

   	  
	   for(int i = 0; i < pixels.length; i++) {
		   bufferPixels[i] = pixels[i];
	   }

	   outputImage.flush();
	    		
  		////////////////////////////////////////////////////////////////////
		try {
			ImageIO.write(outputImage, "PNG", imageFile);						
		} catch (IOException e) {
			e.printStackTrace();
			updateMessage("Cover File Creation Error!!!!");
			return false;
		}   
	
		return true;
   }//// End CreateImageFileFromPixelArray
   
   	////Create a output file from a string
	boolean CreateWMOutputTextFileWM(String filePath, String message){
	
		updateMessage("Creating Text File......");
		
		PrintWriter fw=null;
		BufferedWriter bw=null;
		
		try{
			fw = new PrintWriter(filePath);
			bw = new BufferedWriter(fw);
			//// Write the message to the output file		
			bw.write(message);					
			
			bw.close();
			fw.close();
		}catch(Exception ex){
			return false;
		}		
		
		updateMessage("Text File Created successfully.");
		return true;
	}//// End createWMOutputTextFile
	
	//// This function saves red,green,blue pixels to extracted cover image by block
	void SavePixelArrayToExtractedCoverImageWM(Integer[][] pixelMatrixRED, Integer[][] pixelMatrixGREEN,
			Integer[][] pixelMatrixBLUE,int imageWidth,int imageHeight)
	{
		int pixelColor=0;
		
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				//// Set the pixel color
				pixelColor=(pixelMatrixRED[y][x] << 16) | (pixelMatrixGREEN[y][x] << 8) | pixelMatrixBLUE[y][x];
				coverImageExtracted.setRGB(x, y, pixelColor);
			}			
		}
				
	}//// End SaveExtractedCoverImage
	
	void GetPixelValueWM(int rotate, StringBuilder singlePixelData,
			             WTMatrix matrix, Integer[][] pixelMatrix,int x,int y)    
	{
		try{
			int[][] MultipliedMatrix=new int[maxRow][maxCol];
			int[][] WeightMatrix=new int[maxRow][maxCol];
			
			WeightMatrix=matrix.getPreviousMatrix();
			WriteLogWM("Weight Matrix:-\n"+DisplayMatrix(WeightMatrix,maxRow,maxCol,0,0));
			
			MultiplyWMAndPixelMatrixWM(MultipliedMatrix,pixelMatrix,WeightMatrix,x,y);
			//// Sum of all elements of the matrix
			int sumOfMatrixElements = PCUtility.SumOfMatrixElements(MultipliedMatrix,maxRow,maxCol); 
			//// Modulo of sumOfMatrixElements
			int modVal=(int)Math.pow(2, numberOfBitsWM);
			int SUM = PCUtility.CalculateMod(sumOfMatrixElements, modVal);
			//// Convert the number to N bit (4 bit,5 bit etc) binary string
			String strNumber = PCUtility.ConvertToNBitBinaryString(SUM, numberOfBitsWM);
					
			//// Append the number before the existing data
			singlePixelData.insert(0, strNumber);
			
			WriteLogWM("Extracted Bits: "+ strNumber);
				
			//// For only one round we don't need to get back the previous matrix
			GetPreviousDataMatrixWM(rotate, pixelMatrix, strNumber);
		}catch(Exception ex){
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
	}////End GetPixelValue

	////Get the pixel matrix in previous stage for Matrix Index File
	void GetPreviousDataMatrixMIFWM(Integer[][] pixelMatrix,int xx,int yy){
		
		//// mifData = (+11,-10,-00,)
		String mifData=strMIFData[countForMIF++];
		mifData=mifData.replace("(", "");
		
		if(mifData.length()<4){
			return;
		}
		
		String[] myData = mifData.split(",");
		//// Split the string with comma and iterate through every pixel change
		for( String signXY:myData ){
			
			String sign=signXY.substring(0, 1);
			int x=Integer.parseInt(signXY.substring(1, 2));
			int y=Integer.parseInt(signXY.substring(2, 3));
		
			WriteLogWM("Applying "+sign+" "+x+""+y);  
						
			if( sign.equalsIgnoreCase("0")){
				pixelMatrix[yy*maxRow+y][xx*maxCol+x] += 0;			
			}else if( sign.equalsIgnoreCase("+")){
				pixelMatrix[yy*maxRow+y][xx*maxCol+x] += 1;			
			}else if( sign.equalsIgnoreCase("-")){
				pixelMatrix[yy*maxRow+y][xx*maxCol+x] -= 1;				
			}	
		}
		
	}//// End GetPreviousDataMatrixMIFWM
	
	//// Get the matrix in previous stage
	void GetPreviousDataMatrixWM(int rotate, Integer[][] pixelMatrix,String bitsExtracted)
	{
		
		int posX = selectionMatrix.infoPixelMatrix.get(rotate).getX();
		int posY = selectionMatrix.infoPixelMatrix.get(rotate).getY();
				
		int currentValue=pixelMatrix[ posY ] [ posX ];
		//// Check the index of changed pixel and the change (0,+1, or -1)
	    //// Check last 3 bits for position
		int position = currentValue & 7;
		//// Check 3rd and 4th bit for +/-VE value
		int positivenegatve=currentValue & 24;
				
		String ve="";
		if(positivenegatve==0){
			ve="+1";
		}else if(positivenegatve==8){
			ve="No Change";
		}else if(positivenegatve==16){
			ve="-1";
		}
		
		WriteLogWM("Check InfoMatrix value = "+currentValue+", "+ve);
		WriteLogWM("Change dataMatrix Position = "+position+", Value = "+ pixelMatrix[posY][posX] + " with = " + ve);
		
		//// Change the value in data pixels
		posX = selectionMatrix.dataPixelMatrix.get(position).getX();
		posY = selectionMatrix.dataPixelMatrix.get(position).getY();
		
		if( positivenegatve == 0 )
		{
			pixelMatrix[posY][posX] += 1;			
		}else if ( positivenegatve == 8 )
		{
			pixelMatrix[posY][posX] += 0;
		}else if ( positivenegatve == 16 )  
		{
			pixelMatrix[posY][posX] -= 1;
		}
		  
	}//// End GetPreviousDataMatrixWM	
	
	void ChangeDebugModeWM(boolean change){
		DEBUG_MODE=change;
	}
	
	////Displays MessageBox only when in Debug Mode
	void DebugMessageBoxWM(String message){
		if(DEBUG_MODE==false)
			return;
		
	   Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("PVDE");
       alert.setHeaderText(null);
       alert.setContentText(message);
                     
       GridPane imageContent = new GridPane();
       CheckBox checkBoxDebug=new CheckBox("Debug");
       checkBoxDebug.setOnAction((e)->{
			if(checkBoxDebug.isSelected()){
				DEBUG_MODE=true;
				checkBoxDebug.setSelected(true);
			}else{
				DEBUG_MODE=false;
				checkBoxDebug.setSelected(false);
			}
		});
       imageContent.setMaxWidth(Double.MAX_VALUE);
       imageContent.add(checkBoxDebug, 0, 0);		
       // Set expandable Exception into the dialog pane.
       alert.getDialogPane().setExpandableContent(imageContent);
		
       alert.showAndWait();		
	}//// End MessageBox
	
	//// This function sets the pixel size of the output image. 
	void setOutputImageSizeWM(){
		
		if(coverImage==null){
			return;
		}
		
		int row=Integer.parseInt(mainFrame.comboRow.getValue());
		int col=Integer.parseInt(mainFrame.comboColumn.getValue());
		
		int nBits=(int) (Math.log(2*row*col) / Math.log(2));		
		
		//// Otherwise do the following
		int round=mainFrame.getRoundWM();
		int coverImageSize=(coverImage.getWidth())*(coverImage.getHeight());
		
		int rowCount=Integer.parseInt(mainFrame.comboRow.getValue());
		int colCount=Integer.parseInt(mainFrame.comboColumn.getValue());
		
		int pixelSize=0;
		pixelSize=(round*(3*nBits)*(coverImageSize/(rowCount*colCount)))/24;
				
		mainFrame.textStegoFilePathWM.setPromptText("Stego Image Pixel Number = "+pixelSize);
		mainFrame.textHeight.setText("1");
		mainFrame.textWidth.setText(""+(pixelSize));
		
	}//// End setOutputImageSizeWM
	
	//// Calculates modulus 
	int CalculateMod(int x, int y)
	{
		int result = x % y;
		if (result < 0)
		{
			result += y;
		}
		return result;
	}//// End calculateMod
	
	////Displays MessageBox
	void MessageBox(String message){
		MessageBox(message,"ERROR");			
	}
	void MessageBox(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox
		
	////Convert to binary string of 8 bits
	String ConvertToNBitBinaryString(int n,int nBit){
		StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
		int padding = nBit - binaryStringBuilder.length();
		for(int i=0;i<padding;i++){
			binaryStringBuilder.insert(0, "0");
		}		
		return binaryStringBuilder.toString();		
	}//// End ConvertToBinaryString		
	
	////Convert image to bit string
	String ConvertImageToBinaryString(BufferedImage inputImage){
		int imageWidth=inputImage.getWidth();
		int imageHeight=inputImage.getHeight();
		StringBuilder sbImageString=new StringBuilder();
				
		updateMessage("Converting Image To Binary String...");
		int progressCount=0;
		int totalProgress=imageHeight*imageWidth;
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				
				Color c = new Color(inputImage.getRGB(x, y));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				
				sbImageString.append(ConvertToNBitBinaryString(r,8));
				sbImageString.append(ConvertToNBitBinaryString(g,8));
				sbImageString.append(ConvertToNBitBinaryString(b,8));	
				
				updateProgress(progressCount++,totalProgress);
			}  
		}
		updateMessage("Image To Binary String Conversion Completed.");
		return sbImageString.toString();
	}//// End ConvertImageToBinaryString
	
	////Get embedding data
	String getEmbeddingData(String filePath){
		
		updateMessage("Getting Embedded Data....");
		//// Get the bits from the input file
		byte[] bytesSecretMessageWM;
		String strSecretMessageBitsWM="";
		Path path = Paths.get(filePath);
		
		String extension="";
		try {
			extension = filePath.substring(filePath.lastIndexOf(".") + 1);
		} catch (Exception e) {
			PCUtility.MessageBox("Invalid Input File");
			return "";
		}//// End getEmbeddingData

		//// Process according to file type
		if(extension.equalsIgnoreCase("txt"))
		{
			try{
				bytesSecretMessageWM = Files.readAllBytes(path);
			}catch(Exception ex){
				PCUtility.MessageBox("Invalid Input File");
				return "";
			}	 
		
			for(int i=0;i<bytesSecretMessageWM.length;i++){
				int temp=bytesSecretMessageWM[i]; 
				strSecretMessageBitsWM = strSecretMessageBitsWM + ConvertToNBitBinaryString(temp,8);
			}		
		}else if(extension.equalsIgnoreCase("png")){
		
			BufferedImage inputImage=null;
			try {
				File fileImage = new File(filePath);
				inputImage = ImageIO.read(fileImage);
			} catch (IOException ex) {
				ex.printStackTrace();
				PCUtility.MessageBox("Invalid Input File");
				return "";
			}
			//// Get the binary string from the input image 
			strSecretMessageBitsWM = ConvertImageToBinaryString(inputImage);
		}
		updateMessage("Getting Embedded Data Completed.");
		return strSecretMessageBitsWM;
	}//// End getEmbeddingData

	//// Separates bits
	String BitSeparation(StringBuilder sbOriginal,int interval){
		
		updateMessage("Running Bit Separation.....");
		//String separator = " ";		
		//StringBuilder sb = new StringBuilder(sbOriginal);
		StringBuilder sb = new StringBuilder();
		
		int totalProgress=sbOriginal.length() / interval;
		int currentProgress=0;
		for(int i = 0; i < sbOriginal.length() / interval; i=i+4) {
			//sb.insert(((i + 1) * interval) + i, separator);
			sb.append(sbOriginal.substring(i, i+4)+" ");
			updateProgress(currentProgress++,totalProgress);
		}
		updateMessage("Bit Separation Completed.");
		return sb.toString();
	}//// End BitSeparation
	
	////This function calculates sum of all matrix elements
	int SumOfMatrixElements(int[][] matrix,int row,int col){		
		int sum=0;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				sum+=matrix[i][j];
			}
		}		
		return sum;
	}//// End SumOfMatrixElements
	
	////This function reads 3x3 pixel block from the image
	String DisplayMatrix(int[][] matrix,int row,int col,int startRow,int startCol){
	
		StringBuilder strMatrix=new StringBuilder();
		String matrix1="";
		for(int i=startRow;i<startRow+row;i++){
			for(int j=startCol;j<startCol+col;j++){
				matrix1=String.format("%8d", matrix[i][j]);
				strMatrix.append(matrix1);
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString();
	}//// End displayMatrix
	
	
	@Override
	protected Void call() throws Exception {		
			
		if(steganoType.equalsIgnoreCase("Embed")){
			StartEmbeddingWM(matrix);
			Thread.currentThread().interrupt();
		}else if(steganoType.equalsIgnoreCase("Extract")){
			StartExtractionWM(matrix, extractFilePath);
			Thread.currentThread().interrupt();
		}		
		return null;
	}
	
}
////////////////// End class ProjectWM   ///////////////////////////////////////


/////////////////  Class For Weight Matrix MIF /////////////////////////////////
class ProjectMIF extends Task<Void>
{	
	BufferedImage coverImage;
	boolean DEBUG_MODE=true;
	int ROTATION;
	StringBuilder sbEmbeddingDataBits;
	StringBuilder txtLog;
	SelectionMatrix selectionMatrix;
	PCSteganography mainFrame;
	int maxRow;
	int maxCol;
	String strFileDirectory;
	public boolean IsMIF;
	public boolean IsLOG;
	StringBuilder sbMIFContentMain;
	StringBuilder sbMIFContentTemp;
	StringBuilder sbMIFFileData;
	String[] strMIFData;
	int countForMIF;
	BufferedImage coverImageExtracted;
	public int numberOfBits;
	File coverFileName;
	String steganoType;
	WTMatrix matrix;
	String extractFilePath;	
	String coverFilePath;
	String stegoFilePath;
	int bitCount;
	
	ProjectMIF(PCSteganography frame,File coverFile, BufferedImage cImage, int rotation, int col, int row, 
			  TextArea txtArea,PCSteganography pcs, SelectionMatrix sm,boolean mifSelected,boolean logSelected){
		mainFrame=frame;	
		coverImage=null;
		steganoType="";
		extractFilePath="";
		coverImage=cImage;
		coverImageExtracted=new BufferedImage(cImage.getWidth(),cImage.getHeight(),
											  BufferedImage.TYPE_INT_RGB);
		strFileDirectory=coverFile.getParent(); 
		coverFileName=coverFile;
		ROTATION=rotation;
		txtLog=new StringBuilder();
		selectionMatrix=sm;
		mainFrame=pcs;
		maxRow=row;
		maxCol=col;
		IsMIF=mifSelected;
		IsLOG=logSelected;
		sbMIFContentMain=new StringBuilder();
		sbMIFContentTemp=new StringBuilder();
		sbMIFFileData=new StringBuilder();	
		countForMIF=0;
		numberOfBits=(int)(Math.log(2*maxRow*maxCol) / Math.log(2));
		bitCount=0;
	}
	
	////This function initializes the components.
	void InitializeProjectMIF(File coverFile, BufferedImage cImage, int rotation, int col, int row, 
			  TextArea txtArea,PCSteganography pcs, SelectionMatrix sm,boolean mifSelected)
	{
		coverImage=cImage;
		coverImageExtracted=new BufferedImage(cImage.getWidth(),cImage.getHeight(),
											  BufferedImage.TYPE_INT_RGB);
		strFileDirectory=coverFile.getParent(); 
		coverFileName=coverFile;
		ROTATION=rotation;
		txtLog=new StringBuilder();
		selectionMatrix=sm;
		mainFrame=pcs;
		maxRow=row;   
		maxCol=col;
		IsMIF=mifSelected;
		sbMIFContentMain=new StringBuilder();
		sbMIFContentTemp=new StringBuilder();
		sbMIFFileData=new StringBuilder();	
		countForMIF=0;
		numberOfBits=(int)(Math.log(2*maxRow*maxCol) / Math.log(2));		
	}
	
	void setMIF(boolean value){
		IsMIF=value;
	}
	
	void setCoverImage(BufferedImage cImage){
		coverImage=cImage;
	}
	
	void WriteLogMIF(String strLog){
		if(IsLOG){
			txtLog.append("\n"+strLog);
		}		
	}
	  
	////Get first N bits of a binary string
	String GetFirstNBitsMIF(int nBit){
		String strTemp;		
		try{
			//// Strip first N bits from the string and return it
			strTemp = sbEmbeddingDataBits.substring(bitCount, bitCount+nBit);
			bitCount += nBit;
			return strTemp;
		}catch(Exception ex){
			return "STOP";			
		}			
	}//// End getFirstNBits
		
	//// This function checks the file type and call the appropriate embed function
	void InitializeEmbeddingMIF(String cFilePath,String sFilePath, WTMatrix mtrx){
	
		updateProgress(0,1000);
		updateMessage("Starting Embedding Process");
		
		matrix=mtrx;
		steganoType="Embed";
		
		coverFilePath=cFilePath;
		stegoFilePath=sFilePath;		
		
	}//// End processEmbeddingWM5x5
	
	//// This function embeds stegoImage bits in a coverImage
	void StartEmbeddingMIF(WTMatrix matrix){			
		
		long start = System.nanoTime();
		updateMessage("Starting Embedding Process...");		
		
		if( coverImage==null ){
			MessageBoxMIF("Please select a valid image");
			return; 
		}
		
		String stegoFileType = PCUtility.getFileType(stegoFilePath); 
		 
		//// Get the Stego file type and the data it binary string
		String embeddingDataBits = getEmbeddingDataMIF(stegoFilePath); 
		sbEmbeddingDataBits=new StringBuilder();
		sbEmbeddingDataBits.append(embeddingDataBits); 
		
		//// Write inserted 4 bits per round in the log
		StringBuilder sbInsertedBits=new StringBuilder(embeddingDataBits);
		WriteLogMIF(BitSeparationMIF(sbInsertedBits,numberOfBits));
		
		//// Return if input file is invalid
		if(embeddingDataBits.equalsIgnoreCase("")){
			return;
		}
		
		if(stegoFileType.equals("png")){
			updateMessage("Starting Setgo Image Processing");
			
			//// Create a matrix index file
			if(IsMIF){
				if(PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+"MIF.txt", "")){
					updateMessage("Matrix Index File Created.");
				}else{
					MessageBoxMIF("Matrix Index File Creation Error!!!.");
					return;
				}				
			}		
		}
		
		int imageWidth=coverImage.getWidth()/maxCol;
		int imageHeight=coverImage.getHeight()/maxRow;
		
		int totalProgress=imageWidth*imageHeight;
		
		int imageWidthAll=coverImage.getWidth();
		int imageHeightAll=coverImage.getHeight();
		Integer[][] pixelMatrixRED=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixGREEN=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixBLUE=new Integer[imageHeightAll][imageWidthAll];
		
		//// Copy the pixels into the matrix
		for(int y=0;y<imageHeightAll;y++){	
			for(int x=0;x<imageWidthAll;x++){ 
				int pixelValue=coverImage.getRGB(x, y);
				Color color=new Color(pixelValue); 
				
				pixelMatrixRED[y][x]=color.getRed();
				pixelMatrixGREEN[y][x]=color.getGreen();
				pixelMatrixBLUE[y][x]=color.getBlue();
			}
		}
		
		int[][] MultipliedMatrix=new int[maxRow][maxCol];
		int[][] WeightMatrix=new int[maxRow][maxCol];
		
		int progressIndicator=0;
		int block=1;
		
		updateMessage("Running Pixel Loop To Store Data...");
		//// Read the 3x3 block of the input image 
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){				
				
				WriteLogMIF("####################   Block "+block+" ####################");
				
				WriteLogMIF("-------------------   RED  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogMIF("-------------------------------------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockMIF("RED",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixRED,rotate,x,y);
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");
				}
				sbMIFContentMain.append(sbMIFContentTemp);
				sbMIFContentTemp.setLength(0);
						
				WriteLogMIF("-------------------   GREEN  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogMIF("-------------------------------------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockMIF("GREEN",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixGREEN,rotate,x,y);
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");
				
				}
				sbMIFContentMain.append(sbMIFContentTemp);
				sbMIFContentTemp.setLength(0);
				
				WriteLogMIF("-------------------   BLUE  -----------------------");
				for(int rotate=0;rotate<ROTATION;rotate++)
				{
					WriteLogMIF("-------------------------------------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					StoreDataBitsInPixelBlockMIF("BLUE",matrix,MultipliedMatrix,
							WeightMatrix,pixelMatrixBLUE,rotate,x,y);
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");

				}	
				sbMIFContentMain.append(sbMIFContentTemp+"\n");
				sbMIFContentTemp.setLength(0);
				
				//// Update the progress with this thread's updateProgress property.
				updateProgress(progressIndicator++,totalProgress);
				
				WriteLogMIF("##################################################");
				block++;
			}	
			//// Write the string in the MIF file  
			WriteMatrixIndexFile(sbMIFContentMain);
			sbMIFContentMain.setLength(0);
		}							
							
		updateMessage("Completed Pixel Loop.");
		
		//// Saving the image files
		String fileName = PCUtility.GetFileNameWithoutExtension(coverFileName);
        File file = new File(strFileDirectory+"\\"+fileName+"-Stego.png");
        
        if(CreateImageFileFromPixelArrayMIF(file, pixelMatrixRED,pixelMatrixGREEN,
        		            pixelMatrixBLUE,imageWidthAll,imageHeightAll)==false){
        	MessageBoxMIF("Error creating output WM file!!!");
        }
                   
    	PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+fileName+"-InLog.txt",txtLog.toString());
    	
    	long stop = System.nanoTime();
    	long duration =(stop-start) ;
             
    	updateMessage("Embedding Process Completed. Time Taken = "+TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds.");  	
		
	}//// End stegoImageEmbedding
	  
	//// This function stores 4 bits in a 3x3 pixel block
	void StoreDataBitsInPixelBlockMIF(String pixelColor, WTMatrix matrix,int[][] MultipliedMatrix, 
			int[][] WeightMatrix, Integer pixelMatrix[][],int rotation, int x,int y)
	{
		//// Populate the matrix
		WeightMatrix=matrix.getNextMatrix();
		
		WriteLogMIF("Weight Matrix:-\n"+DisplayMatrixMIF(WeightMatrix,maxRow,maxCol,0,0));
					 
		boolean recalculate=false;
		
		String messageNBits = GetFirstNBitsMIF(numberOfBits);
		
		/////// FIXME: When input data is finished //////// 
		if(messageNBits.equalsIgnoreCase("STOP")){
			return;
		}
		///////////////////////////////////////////////////
		
		String signForMIF="0"; 

		int modVal1= (int)Math.pow(2, numberOfBits-1);
		int modVal2=(int)Math.pow(2, numberOfBits);		
		
		if(IsMIF){
			sbMIFContentTemp.insert(0, "),");
		}
			
		do{
			recalculate=false;
			//// Multiply weight matrix with the data pixel matrix only   
			MultiplyWMAndPixelMatrixMIF(MultipliedMatrix,pixelMatrix,WeightMatrix,x,y); 					
			//// Sum of all elements of the matrix
			int sumOfMatrixElements = SumOfMatrixElementsMIF(MultipliedMatrix,maxRow,maxCol);			
			//// Modulo of sumOfMatrixElements			
			int SUM = CalculateModMIF(sumOfMatrixElements, modVal2);
			int intNBits=Integer.parseInt(messageNBits, 2);  
			  
			//// Calculate d = (10011 - SUM)
			int d = intNBits - SUM;
			int modD=0;
			int sign=0;
			int originalD=d;
			
			if( d>modVal1 ){
				d=modVal2-d;	
				modD = (int)Math.abs(d);
				sign=-1;
				signForMIF="+";
			}else if(d>0){
				modD = (int)Math.abs(d);
				sign=+1;
				signForMIF="-";
			}else if(d<-modVal1){
				d=modVal2+d;	
				modD = (int)Math.abs(d);
				sign=+1;
				signForMIF="-";
			}else if(d<0){
				modD = (int)Math.abs(d);
				sign=-1;
				signForMIF="+";
			}
			
			//// Search position of modD in the weightMatrix,
			//// and add or subtract 1 with the element in the 
			//// same position in the pixel3x3Matrix matrix.
			int i=0,j=0;
			outerLoop:
				for( i=0; i<maxRow; i++ ){
					for( j=0; j<maxCol; j++ ){ 						
						if(selectionMatrix.isDataPixel(j,i)){								
							if( WeightMatrix[i][j] == modD ){						
								if( d>0 ){
									if(pixelMatrix[maxRow*y+i][maxCol*x+j]==255)
									{								  
										pixelMatrix[maxRow*y+i][maxCol*x+j]=254;
										recalculate=true;
										WriteLogMIF("Trying For Another Pixel=255");
										if(IsMIF){
											sbMIFContentTemp.insert(0, "+"+j+""+i+",");
										}
										continue;										
									}else{
										pixelMatrix[maxRow*y+i][maxCol*x+j] += sign;
										if(IsMIF){
											sbMIFContentTemp.insert(0, signForMIF+""+j+""+i+",");
										}
										break outerLoop;
									}								
								}else if(d<0){
									if(pixelMatrix[maxRow*y+i][maxCol*x+j]==0){								
										pixelMatrix[maxRow*y+i][maxCol*x+j]=1;
										recalculate=true;
										WriteLogMIF("Trying For Another Pixel=0");
										if(IsMIF){
											sbMIFContentTemp.insert(0, "-"+j+""+i+",");
										}
										continue;										
									}else{
										pixelMatrix[maxRow*y+i][maxCol*x+j] += sign;
										if(IsMIF){
											sbMIFContentTemp.insert(0, signForMIF+""+j+""+i+",");
										}
										break outerLoop;
									}		
								}
							}						
						}
						
					}//// End inner for loop
				}//// End outer for loop
			/////////////////////////////////////////////////////// 
			WriteLogMIF(pixelColor+"::Message:- "+messageNBits+"::d = "+d+"::originalD = "+originalD);
			
			////Change the infoPixelMatrix according to d and modD
			if(ROTATION>1 && !IsMIF){
				//// Change the matrix for more than 1 round only
				ChangeInfoPixelMatrixMIF(rotation,sign,modD-1,pixelMatrix);
			}				
			if(d==0 && IsMIF){
				sbMIFContentTemp.insert(0, "000,");				
			}			
			
		}while(recalculate==true);			
		
		if(IsMIF){
			sbMIFContentTemp.insert(0, "(");
		}		
	
	}//// End StoreDataBitsInPixelBlock

	//// This function creates and writes in the matrix index file.
	void WriteMatrixIndexFile(StringBuilder strContent){
		int ret=PCUtility.AppendToTextFile(strFileDirectory+"\\"+"MIF.txt", strContent);
		if(ret==-1){
			updateMessage("Error Writing Matrix Index File!!!!!!!!");
			return;
		}
	}//// End WriteMatrixIndexFile
	
	////Change the last 3 bits according to the position of changed pixel
	void ChangeInfoPixelMatrixMIF(int rotation, int sign, int modD, Integer pixelMatrix[][])
	{		
		int posX = selectionMatrix.infoPixelMatrix.get(rotation).getX();
		int posY = selectionMatrix.infoPixelMatrix.get(rotation).getY();
				
		int currentPixelValue=pixelMatrix[ posY ][ posX ];
		WriteLogMIF("X = "+posX+", Y = "+posY+"::infoPixelValue change = "+currentPixelValue);
		
		//// Change last 3 bits value to store the position
		currentPixelValue=(currentPixelValue & 248) + modD;		   
		
		if(sign<0){
			currentPixelValue=(currentPixelValue & 231); // set bit 00
		}else if(sign==0){
			currentPixelValue=(currentPixelValue & 231) + 8; // set bit 01
		}else if(sign>0){
			currentPixelValue=(currentPixelValue & 231) + 16; // set bit 10
		}

		//// Set the changed pixel value
		pixelMatrix[posY][posX]=currentPixelValue;		
	}
	//// End ChangePixelMatrix 
	
	////This function writes pixel block from the image
	void WritePixelBlockMIF(String pixelColor, int[][] pixelMatrix,int col,int row)
	{	
		
		for(int y=0;y<maxRow;y++){
			for(int x=0;x<maxCol;x++){		
				//// Change for data matrix
				if(selectionMatrix.isDataPixel(x,y)){
					
					Color c = new Color(coverImage.getRGB(maxCol*col+x, maxRow*row+y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					int pixelData=0;					
					
					if(pixelColor.equalsIgnoreCase("red")==true){
						pixelData=(pixelMatrix[y][x] << 16) | (g << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("green")==true){
						pixelData=(r << 16) | (pixelMatrix[y][x] << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("blue")==true){
						pixelData=(r << 16) | (g << 8) | pixelMatrix[y][x];
					}
					//// Change the pixel data in the original image
					coverImage.setRGB(maxCol*col+x, maxRow*row+y, pixelData);
					
				}//// Change for info matrix
				else if(selectionMatrix.isInfoPixel(x,y)){
					
					Color c = new Color(coverImage.getRGB(maxCol*col+x, maxRow*row+y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					int infoData=0;					
					
					if(pixelColor.equalsIgnoreCase("red")==true){
						infoData=(pixelMatrix[y][x] << 16) | (g << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("green")==true){
						infoData=(r << 16) | (pixelMatrix[y][x] << 8) | b;
					}else if(pixelColor.equalsIgnoreCase("blue")==true){
						infoData=(r << 16) | (g << 8) | pixelMatrix[y][x];
					}
					//// Change the info data in the original image.
					coverImage.setRGB(maxCol*col+x, maxRow*row+y, infoData);
				}		
			}
		}  
		
	}//// End WritePixelBlock 
	
	////This function multiplies weight matrix and pixel data matrix
	void MultiplyWMAndPixelMatrixMIF(int[][] multipliedMatrix,Integer[][] pixelMatrix,
			                        int[][] WeightMatrix,int x,int y){
			
		for(int i=0;i<maxRow;i++){
			for(int j=0;j<maxCol;j++){
				multipliedMatrix[i][j]=pixelMatrix[maxRow*y+i][maxCol*x+j] * WeightMatrix[i][j];
			}
		}			
	}//// End multiplyWMAndDataPixelMatrix	
		
	////This function is for extract in Weight Matrix
	void InitializeExtractionMIF(String filePath,WTMatrix mtrx){
	
		updateMessage("Starting Extraction Process.....");
		System.out.println("Starting Extraction Process.....");
		
		extractFilePath=filePath;
		String stegoFileType = PCUtility.getFileType(filePath);
		
		if(stegoFileType.equals("png")){
			System.out.println("File type PNG.....");
			txtLog=new StringBuilder();
			steganoType="Extract";
			matrix=mtrx;
		}else if(stegoFileType.equals("jpg")){
			System.out.println("File type JPG.....");
			txtLog=new StringBuilder();
			steganoType="Extract";
			matrix=mtrx;
		}else if(stegoFileType.equals("wav")){
			MessageBoxMIF("Starting Setgo Audio Processing");			
		}else if(stegoFileType.equals("mp4")){
			MessageBoxMIF("Starting Setgo Video Processing");
			
		}		
	}//// End extractWeightMatrix
	
	////Extraction of data from image
	void StartExtractionMIF(WTMatrix matrix, String filePath){		
		
		try{
		updateMessage("Starting Extraction Process MIF.....");
		long start = System.nanoTime();
		StringBuilder sbStegoMessage=new StringBuilder();		
		
		int imageWidthExt=coverImage.getWidth()/maxCol;
		int imageHeightExt=coverImage.getHeight()/maxRow;
		int totalProgress=imageWidthExt*imageHeightExt;
		
		
		//// Copy the Matrix Index File Data to this sbMIFFileData string builder
		if(IsMIF){
			boolean ret=PCUtility.ReadFileByLines(strFileDirectory+"\\"+"MIF.txt",
					                              sbMIFFileData);
			// Split the string builder and save the data in a string array
			////strMIFData=sbMIFFileData.toString().split("\\s*,\\s*");
			strMIFData=sbMIFFileData.toString().split("\\),");
			if(ret==true){
				updateMessage("Matrix File Data Copied Successfully");
			}else{
				MessageBoxMIF("Matrix File Data Copy Error!!!");
				return;
			}
		}	
			
		int imageWidthAll=coverImage.getWidth();
		int imageHeightAll=coverImage.getHeight();
		Integer[][] pixelMatrixRED=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixGREEN=new Integer[imageHeightAll][imageWidthAll];
		Integer[][] pixelMatrixBLUE=new Integer[imageHeightAll][imageWidthAll];		
		
		//// Copy the pixels into the matrix
		for(int y=0;y<imageHeightAll;y++){	
			for(int x=0;x<imageWidthAll;x++){ 
				int pixelValue=coverImage.getRGB(x, y);
				Color color=new Color(pixelValue); 
				
				pixelMatrixRED[y][x]=color.getRed();
				pixelMatrixGREEN[y][x]=color.getGreen();
				pixelMatrixBLUE[y][x]=color.getBlue();
			}
		}
		
		System.out.println("Extracting Data From Image File MIF....");
		updateMessage("Extracting Data From Image File MIF....");
		int progressIndicator=0;
		//// Read the pixel block of the input image 
		outerloop:
		for(int y=0;y<imageHeightExt;y++){	
			for(int x=0;x<imageWidthExt;x++){ 
    			
				StringBuilder singlePixelData=new StringBuilder();

				for(int rotate=ROTATION-1;rotate>=0;rotate--)
				{
					WriteLogMIF("------------------ RED Pixel -----------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					int ret=GetPixelValueMIF(rotate,singlePixelData,matrix,pixelMatrixRED,x,y);
					if(ret==-1){
						break outerloop;
					}
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixRED, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");
				}
				sbStegoMessage.append(singlePixelData.toString());	 
				singlePixelData.setLength(0);
				
				for(int rotate=ROTATION-1;rotate>=0;rotate--)
				{
					WriteLogMIF("------------------ GREEN Pixel -----------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					int ret=GetPixelValueMIF(rotate,singlePixelData,matrix,pixelMatrixGREEN,x,y);
					if(ret==-1){
						break outerloop;
					}
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixGREEN, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");
				}
				sbStegoMessage.append(singlePixelData.toString());
				singlePixelData.setLength(0);
				
				for(int rotate=ROTATION-1;rotate>=0;rotate--)
				{
					WriteLogMIF("------------------ BLUE Pixel -----------------------");
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					int ret=GetPixelValueMIF(rotate,singlePixelData,matrix,pixelMatrixBLUE,x,y);
					if(ret==-1){
						break outerloop;
					}
					WriteLogMIF(PCUtility.displayMatrixInteger(pixelMatrixBLUE, maxRow, maxCol,maxRow*y,maxCol*x));
					WriteLogMIF("-------------------------------------------------");
				}				
				sbStegoMessage.append(singlePixelData.toString());
								
				//// Update the progress with this thread's updateProgress property.
				updateProgress(progressIndicator++,totalProgress);	
			}			   
		}
		
		updateMessage("Extracted MIF Data From Image File Successfully.");
		System.out.println("Extracted MIF Data From Image File Successfully.");
		
		String fileName=PCUtility.GetFileNameWithoutExtension(coverFileName);
		fileName=fileName.replace("-WM", "");
		
		
		String strMessage="";
		String inputFileType = PCUtility.getFileType(filePath);
		
		long stop = System.nanoTime();
		
		//// Display the message in the secret message text box
		if(inputFileType.equalsIgnoreCase("txt")){
			MessageBoxMIF("Extracted File Type is TXT");
			strMessage = PCUtility.binaryArrayToCharacter(sbStegoMessage.toString(),8);
			PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+"wmOutput.txt", strMessage);
		}else if(inputFileType.equalsIgnoreCase("png")){  
			
			updateMessage("Creating Extracted Stego File.");
						
			System.out.println("Number of bits extracted = "+sbStegoMessage.length());
			
			toImageFromBinaryStringMIF(fileName+"-Secret.png",strFileDirectory,
									   sbStegoMessage,
									   mainFrame.getOutputImageWidthMIF(),
					                   mainFrame.getOutputImageHeightMIF());
			
			updateMessage("Stego File Extracted Successfully.");
						
			PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+fileName+"-OutLog.txt",txtLog.toString());
			updateMessage("LOG File Created successfully.");
		}else if(inputFileType.equalsIgnoreCase("jpg")){  
			
			updateMessage("Creating Extracted Stego File.");
						
			System.out.println("Number of bits extracted = "+sbStegoMessage.length());
			
			toImageFromBinaryStringMIF(fileName+"-Secret.jpg",strFileDirectory,
									   sbStegoMessage,
									   mainFrame.getOutputImageWidthMIF(),
					                   mainFrame.getOutputImageHeightMIF());
			
			updateMessage("Stego File Extracted Successfully.");
						
			PCUtility.CreateWMOutputTextFile(strFileDirectory+"\\"+fileName+"-OutLog.txt",txtLog.toString());
			updateMessage("LOG File Created successfully.");
		}
		
		long duration =(stop-start) ;
		updateMessage("Extraction Process Completed. Time Taken = "+TimeUnit.NANOSECONDS.toSeconds(duration)+" seconds.");
		}catch(Exception ex){
			System.out.println("Error occured dusing extraction process!!!");
			ex.printStackTrace();  
		}
	}//// End StegoImageExtractionWM
		
	//// Create Image from binary string
	void toImageFromBinaryStringMIF(String fileName, String fileDirectory, 
			 StringBuilder binaryString,int imageWidth, int imageHeight){

		try{
		
			int red=0,green=0,blue=0;
			int pixelColor=0;
			//// Create the empty output image file
			BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
			int substringCount=0;
			
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){														
					red=Integer.parseInt(binaryString.substring(substringCount, substringCount+8), 2);
					green=Integer.parseInt(binaryString.substring(substringCount+8, substringCount+16),2);
					blue=Integer.parseInt(binaryString.substring(substringCount+16, substringCount+24),2);
					//// Get the pixel color
					pixelColor=(red << 16) | (green << 8) | blue;
					outputImage.setRGB(x, y, pixelColor);
			
					substringCount+=24;								
				}
			}					
			//// Create the output image file and save it in a file
			File imageFile = new File(fileDirectory+"\\"+fileName);
			ImageIO.write(outputImage, "PNG", imageFile);
			updateMessage("Image Extracted Successfully");
		
		}catch(Exception ex){
			updateMessage("SubSampleClass::toImageFromBinaryString:- "+ex.toString());
			ex.printStackTrace();
		    return;
		}
		
	}//// End toImageFromBinaryStringMIF		
	
	////Create PNG file from pixel arrays
    boolean CreateImageFileFromPixelArrayMIF(File imageFile, Integer[][] pixelMatrixRED,
      Integer[][] pixelMatrixGREEN,Integer[][] pixelMatrixBLUE, int imageWidth, int imageHeight)
   {
	      
	   ///////////////////////////////////////////////////////////////////
	   int[] pixels=new int[imageWidth * imageHeight];
	   
   	   //// Create the empty output image file
  	   BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);
  	    	
  	   int progressCount=0;
	   int totalProgress=imageHeight*imageWidth;
  	   for(int y = 0; y < imageHeight; y++) {
  		   for(int x = 0; x < imageWidth; x++) {
  			   pixels[x + y * imageWidth]=(pixelMatrixRED[y][x]<< 16) | 
  					   					  (pixelMatrixGREEN[y][x] << 8) | 
  					   					  (pixelMatrixBLUE[y][x]);
  			   updateProgress(progressCount++,totalProgress);
  		   }
  	   }
  	   
  	   int[] bufferPixels = ((DataBufferInt) outputImage.getRaster().getDataBuffer()).getData();

   	  
	   for(int i = 0; i < pixels.length; i++) {
		   bufferPixels[i] = pixels[i];
	   }

	   outputImage.flush();
	    		
  		////////////////////////////////////////////////////////////////////
		try {
			ImageIO.write(outputImage, "PNG", imageFile);						
		} catch (IOException e) {
			e.printStackTrace();
			updateMessage("Cover File Creation Error!!!!");
			return false;
		}   
	
		return true;
   }//// End CreateImageFileFromPixelArray
   
   	////Create a output file from a string
	boolean CreateWMOutputTextFileMIF(String filePath, String message){
	
		updateMessage("Creating Text File......");
		
		PrintWriter fw=null;
		BufferedWriter bw=null;
		
		try{
			fw = new PrintWriter(filePath);
			bw = new BufferedWriter(fw);
			//// Write the message to the output file		
			bw.write(message);					
			
			bw.close();
			fw.close();
		}catch(Exception ex){
			return false;
		}		
		
		updateMessage("Text File Created successfully.");
		return true;
	}//// End createWMOutputTextFile
	
	//// This function saves red,green,blue pixels to extracted cover image by block
	void SavePixelArrayToExtractedCoverImageMIF(Integer[][] pixelMatrixRED, Integer[][] pixelMatrixGREEN,
			Integer[][] pixelMatrixBLUE,int imageWidth,int imageHeight)
	{
		int pixelColor=0;
		
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				//// Set the pixel color
				pixelColor=(pixelMatrixRED[y][x] << 16) | (pixelMatrixGREEN[y][x] << 8) | pixelMatrixBLUE[y][x];
				coverImageExtracted.setRGB(x, y, pixelColor);
			}			
		}
				
	}//// End SaveExtractedCoverImage
	
	int GetPixelValueMIF(int rotate, StringBuilder singlePixelData,
			             WTMatrix matrix, Integer[][] pixelMatrix,int x,int y)    
	{
		try{
			System.out.println("Entering GetPixelValueMIF...");
			int[][] MultipliedMatrix=new int[maxRow][maxCol];
			int[][] WeightMatrix=new int[maxRow][maxCol];
			
			WeightMatrix=matrix.getPreviousMatrix();
			WriteLogMIF("Weight Matrix:-\n"+DisplayMatrixMIF(WeightMatrix,maxRow,maxCol,0,0));
			
			MultiplyWMAndPixelMatrixMIF(MultipliedMatrix,pixelMatrix,WeightMatrix,x,y);
			//// Sum of all elements of the matrix
			int sumOfMatrixElements = PCUtility.SumOfMatrixElements(MultipliedMatrix,maxRow,maxCol); 
			//// Modulo of sumOfMatrixElements
			int modVal=(int)Math.pow(2, numberOfBits);
			int SUM = PCUtility.CalculateMod(sumOfMatrixElements, modVal);
			//// Convert the number to N bit (4 bit,5 bit etc) binary string
			String strNumber = PCUtility.ConvertToNBitBinaryString(SUM, numberOfBits);
					
			//// Append the number before the existing data
			singlePixelData.insert(0, strNumber);
			
			WriteLogMIF("Extracted Bits: "+ strNumber);
				
			//// For only one round we don't need to get back the previous matrix
			int ret=GetPreviousDataMatrixMIF(pixelMatrix,x,y);
			if(ret==-1){
				return -1;
			}
			System.out.println("Exiting GetPixelValueMIF...");
			return 0;
		}catch(Exception ex){
			System.out.println(ex.toString());
			ex.printStackTrace();
			return -1;
		}
	}////End GetPixelValue

	////Get the pixel matrix in previous stage for Matrix Index File
	int GetPreviousDataMatrixMIF(Integer[][] pixelMatrix,int xx,int yy){
		
		try{
			//// mifData = (+11,-10,-00,)
			String mifData=strMIFData[countForMIF++];
			mifData=mifData.replace("(", "");
			
			if(mifData.length()<4){
				return -1;
			}
			
			String[] myData = mifData.split(",");
			//// Split the string with comma and iterate through every pixel change
			for( String signXY:myData ){
				
				String sign=signXY.substring(0, 1);
				int x=Integer.parseInt(signXY.substring(1, 2));
				int y=Integer.parseInt(signXY.substring(2, 3));
			
				WriteLogMIF("Applying "+sign+" "+x+""+y);  
							
				if( sign.equalsIgnoreCase("0")){
					pixelMatrix[yy*maxRow+y][xx*maxCol+x] += 0;			
				}else if( sign.equalsIgnoreCase("+")){
					pixelMatrix[yy*maxRow+y][xx*maxCol+x] += 1;			
				}else if( sign.equalsIgnoreCase("-")){
					pixelMatrix[yy*maxRow+y][xx*maxCol+x] -= 1;				
				}	
			}
			return 0;
		}catch(Exception ex){			
			return -1;
		}
	}//// End GetPreviousDataMatrixMIFWM
	
	//// Get the matrix in previous stage
	void GetPreviousDataMatrixMIF(int rotate, Integer[][] pixelMatrix,String bitsExtracted)
	{
		
		int posX = selectionMatrix.infoPixelMatrix.get(rotate).getX();
		int posY = selectionMatrix.infoPixelMatrix.get(rotate).getY();
				
		int currentValue=pixelMatrix[ posY ] [ posX ];
		//// Check the index of changed pixel and the change (0,+1, or -1)
	    //// Check last 3 bits for position
		int position = currentValue & 7;
		//// Check 3rd and 4th bit for +/-VE value
		int positivenegatve=currentValue & 24;
				
		String ve="";
		if(positivenegatve==0){
			ve="+1";
		}else if(positivenegatve==8){
			ve="No Change";
		}else if(positivenegatve==16){
			ve="-1";
		}
		
		WriteLogMIF("Check InfoMatrix value = "+currentValue+", "+ve);
		WriteLogMIF("Change dataMatrix Position = "+position+", Value = "+ pixelMatrix[posY][posX] + " with = " + ve);
		
		//// Change the value in data pixels
		posX = selectionMatrix.dataPixelMatrix.get(position).getX();
		posY = selectionMatrix.dataPixelMatrix.get(position).getY();
		
		if( positivenegatve == 0 )
		{
			pixelMatrix[posY][posX] += 1;			
		}else if ( positivenegatve == 8 )
		{
			pixelMatrix[posY][posX] += 0;
		}else if ( positivenegatve == 16 )  
		{
			pixelMatrix[posY][posX] -= 1;
		}
		  
	}//// End GetPreviousDataMatrixWM	
	
	void ChangeDebugModeMIF(boolean change){
		DEBUG_MODE=change;
	}
	
	////Displays MessageBox only when in Debug Mode
	void DebugMessageBoxMIF(String message){
		if(DEBUG_MODE==false)
			return;
		
	   Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("PVDE");
       alert.setHeaderText(null);
       alert.setContentText(message);
                     
       GridPane imageContent = new GridPane();
       CheckBox checkBoxDebug=new CheckBox("Debug");
       checkBoxDebug.setOnAction((e)->{
			if(checkBoxDebug.isSelected()){
				DEBUG_MODE=true;
				checkBoxDebug.setSelected(true);
			}else{
				DEBUG_MODE=false;
				checkBoxDebug.setSelected(false);
			}
		});
       imageContent.setMaxWidth(Double.MAX_VALUE);
       imageContent.add(checkBoxDebug, 0, 0);		
       // Set expandable Exception into the dialog pane.
       alert.getDialogPane().setExpandableContent(imageContent);
		
       alert.showAndWait();		
	}//// End MessageBox
	
	//// This function sets the pixel size of the output image. 
	void setOutputImageSizeMIF(){
		
		if(coverImage==null){
			return;
		}
		
		int row=Integer.parseInt(mainFrame.comboRowMIF.getValue());
		int col=Integer.parseInt(mainFrame.comboColumnMIF.getValue());
		
		int nBits=(int) (Math.log(2*row*col) / Math.log(2));		
		
		//// For Matrix Index File do this /////////////////////////
		if(IsMIF){	
				
			int coverImageSize=(coverImage.getWidth())*(coverImage.getHeight());
			int blockCount=(coverImageSize)/(row*col); 

			int pixelCount=(blockCount*(nBits)*18)/24;
			mainFrame.textStegoFilePathWM.setPromptText("Stego Image Pixel Number = "+pixelCount);
			mainFrame.textHeight.setText("1");
			mainFrame.textWidth.setText(""+(pixelCount));
			
			return;
		}
		////////////////////////////////////////////////////////////  
		
		//// Otherwise do the following
		int round=mainFrame.getRoundWMMIF();
		int coverImageSize=(coverImage.getWidth())*(coverImage.getHeight());
		
		int rowCount=Integer.parseInt(mainFrame.comboRow.getValue());
		int colCount=Integer.parseInt(mainFrame.comboColumn.getValue());
		
		int pixelSize=0;
		pixelSize=(round*(3*nBits)*(coverImageSize/(rowCount*colCount)))/24;
				
		mainFrame.textStegoFilePathWM.setPromptText("Stego Image Pixel Number = "+pixelSize);
		mainFrame.textHeight.setText("1");
		mainFrame.textWidth.setText(""+(pixelSize));
		
	}//// End setOutputImageSize
	
	//// Calculates modulus 
	int CalculateModMIF(int x, int y)
	{
		int result = x % y;
		if (result < 0)
		{
			result += y;
		}
		return result;
	}//// End calculateMod
	
	////Displays MessageBox
	void MessageBoxMIF(String message){
		MessageBoxMIF(message,"ERROR");			
	}
	void MessageBoxMIF(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox
		
	////Convert to binary string of 8 bits
	String ConvertToNBitBinaryStringMIF(int n,int nBit){
		StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
		int padding = nBit - binaryStringBuilder.length();
		for(int i=0;i<padding;i++){
			binaryStringBuilder.insert(0, "0");
		}		
		return binaryStringBuilder.toString();		
	}//// End ConvertToBinaryString		
	
	////Convert image to bit string
	String ConvertImageToBinaryStringMIF(BufferedImage inputImage){
		int imageWidth=inputImage.getWidth();
		int imageHeight=inputImage.getHeight();
		StringBuilder sbImageString=new StringBuilder();
				
		updateMessage("Converting Image To Binary String...");
		int progressCount=0;
		int totalProgress=imageHeight*imageWidth;
		for(int y=0;y<imageHeight;y++){
			for(int x=0;x<imageWidth;x++){
				
				Color c = new Color(inputImage.getRGB(x, y));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				
				sbImageString.append(ConvertToNBitBinaryStringMIF(r,8));
				sbImageString.append(ConvertToNBitBinaryStringMIF(g,8));
				sbImageString.append(ConvertToNBitBinaryStringMIF(b,8));	
				
				updateProgress(progressCount++,totalProgress);
			}  
		}
		updateMessage("Image To Binary String Conversion Completed.");
		return sbImageString.toString();
	}//// End ConvertImageToBinaryString
	
	////Get embedding data
	String getEmbeddingDataMIF(String filePath){
		
		updateMessage("Getting Embedded Data....");
		//// Get the bits from the input file
		byte[] bytesSecretMessageWM;
		String strSecretMessageBitsWM="";
		Path path = Paths.get(filePath);
		
		String extension="";
		try {
			extension = filePath.substring(filePath.lastIndexOf(".") + 1);
		} catch (Exception e) {
			PCUtility.MessageBox("Invalid Input File");
			return "";
		}//// End getEmbeddingData

		//// Process according to file type
		if(extension.equalsIgnoreCase("txt"))
		{
			try{
				bytesSecretMessageWM = Files.readAllBytes(path);
			}catch(Exception ex){
				PCUtility.MessageBox("Invalid Input File");
				return "";
			}	 
		
			for(int i=0;i<bytesSecretMessageWM.length;i++){
				int temp=bytesSecretMessageWM[i]; 
				strSecretMessageBitsWM = strSecretMessageBitsWM + ConvertToNBitBinaryStringMIF(temp,8);
			}		
		}else if(extension.equalsIgnoreCase("png")){
		
			BufferedImage inputImage=null;
			try {
				File fileImage = new File(filePath);
				inputImage = ImageIO.read(fileImage);
			} catch (IOException ex) {
				ex.printStackTrace();
				PCUtility.MessageBox("Invalid Input File");
				return "";
			}
			//// Get the binary string from the input image 
			strSecretMessageBitsWM = ConvertImageToBinaryStringMIF(inputImage);
		}
		updateMessage("Getting Embedded Data Completed.");
		return strSecretMessageBitsWM;
	}//// End getEmbeddingData

	//// Separates bits
	String BitSeparationMIF(StringBuilder sbOriginal,int interval){
		
		updateMessage("Running Bit Separation.....");
		//String separator = " ";		
		//StringBuilder sb = new StringBuilder(sbOriginal);
		StringBuilder sb = new StringBuilder();
		
		int totalProgress=sbOriginal.length() / interval;
		int currentProgress=0;
		for(int i = 0; i < sbOriginal.length() / interval; i=i+4) {
			//sb.insert(((i + 1) * interval) + i, separator);
			sb.append(sbOriginal.substring(i, i+4)+" ");
			updateProgress(currentProgress++,totalProgress);
		}
		updateMessage("Bit Separation Completed.");
		return sb.toString();
	}//// End BitSeparation
	
	////This function calculates sum of all matrix elements
	int SumOfMatrixElementsMIF(int[][] matrix,int row,int col){		
		int sum=0;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				sum+=matrix[i][j];
			}
		}		
		return sum;
	}//// End SumOfMatrixElements
	
	////This function reads 3x3 pixel block from the image
	String DisplayMatrixMIF(int[][] matrix,int row,int col,int startRow,int startCol){
	
		StringBuilder strMatrix=new StringBuilder();
		String matrix1="";
		for(int i=startRow;i<startRow+row;i++){
			for(int j=startCol;j<startCol+col;j++){
				matrix1=String.format("%8d", matrix[i][j]);
				strMatrix.append(matrix1);
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString();
	}//// End displayMatrix
	
	
	@Override
	protected Void call() throws Exception {		
			
		if(steganoType.equalsIgnoreCase("Embed")){
			StartEmbeddingMIF(matrix);
			Thread.currentThread().interrupt();
		}else if(steganoType.equalsIgnoreCase("Extract")){
			StartExtractionMIF(matrix, extractFilePath);
			Thread.currentThread().interrupt();
		}		
		return null;
	}  
	
}   
////////////////////////    End Class ProjectWMMIF //////////////////////////////

///////////////////////////// Class SelectionBoard //////////////////////////////
class SelectionMatrix {
    int size;
    VBox node;
	int row,col;
	int[][] selectionMatrix;
	public List<Pixel> pixelMatrix;
	public List<Pixel> dataPixelMatrix;
	public List<Pixel> infoPixelMatrix;
	public int maxCol;
	public int maxRow;
	PCSteganography mainFrame;
	
	SelectionMatrix(PCSteganography pcs, VBox root){		
		mainFrame=pcs;
		node=root;
		size=10;
		selectionMatrix=new int[10][10];		
		// Fill each matrix element with a -1
		for (int[] row: selectionMatrix)
		    Arrays.fill(row, -1);
		
		pixelMatrix=new ArrayList<>();
		dataPixelMatrix = new ArrayList<>();
		infoPixelMatrix = new ArrayList<>();
			
		maxCol=0;
		maxRow=0;
	}
	
	//// Changes the color of the selection board
	void changeColor(GraphicsContext gc, String color){
		
		if(color.equalsIgnoreCase("GREEN")){
			if(gc.getFill()==javafx.scene.paint.Color.GREEN){
				gc.setFill(javafx.scene.paint.Color.WHITE);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
			}else{
				gc.setFill(javafx.scene.paint.Color.GREEN);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
			}			
		}else if(color.equalsIgnoreCase("RED")){
			if(gc.getFill()==javafx.scene.paint.Color.RED){
				gc.setFill(javafx.scene.paint.Color.WHITE);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
			}else{
				gc.setFill(javafx.scene.paint.Color.RED);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
			}			
		}else if(color.equalsIgnoreCase("WHITE")){
			gc.setFill(javafx.scene.paint.Color.WHITE);
			gc.setStroke(javafx.scene.paint.Color.BLACK);
		}
		
		gc.fillRect(0, 0, 30, 30);
		gc.strokeRect(0, 0, 30, 30);		
	}//// End changeColor
	
	void createSelectionBoard(){			
		GridPane boardPane = new GridPane();
		boardPane.setPrefSize(50, 50);
		
        try{
        	for(row = 0; row < size; row++) {
                for(col = 0; col < size; col++) {
                	
                	Pixel pxl=new Pixel(this, col,row);    
                	//// Primarily set the board color to WHITE
                	changeColor(pxl.getGraphicsContext2D(),"WHITE");
                	//// Add the Pixel objects to the board                	
                	boardPane.add( pxl, col, row );
                	//// Add the pixel in the pixel matrix
                	pixelMatrix.add(pxl);
                }
            }	        	
        }catch(Exception ex){
        	PCUtility.MessageBox("Exception:- "+ex.toString());
        	return;
        }		
		node.getChildren().add(boardPane);
	}//// End createSelectionBoard		
	
	int getMaxColumn(){
		int n=0;
		for(Pixel pl:infoPixelMatrix){      
			if(pl.getX()>n){
				n=pl.getX();
			}
		}
		for(Pixel pl:dataPixelMatrix){
			if(pl.getX()>n){
				n=pl.getX();
			}
		}
		return n+1;
	}
	
	int getMaxRow(){
		int n=0;
		for(Pixel pl:infoPixelMatrix){
			if(pl.getY()>n){
				n=pl.getY();
			}
		}
		for(Pixel pl:dataPixelMatrix){
			if(pl.getY()>n){
				n=pl.getY();
			}
		}
		return n+1;
	}
	
	int getRound(){
		int n=infoPixelMatrix.size();
		return n;
	}
		
	boolean isDataPixel(int x,int y){		
		for(Pixel pl:dataPixelMatrix){
			if(pl.getX()==x && pl.getY()==y){
				return true;
			}
		}
		return false;
	}
	
	boolean isInfoPixel(int x,int y){
		for(Pixel pl:infoPixelMatrix){
			if(pl.getX()==x && pl.getY()==y){
				return true;
			}
		}
		return false;
	}
	
	void setMaxColAndRow(){
		mainFrame.setComboRowColWM(getMaxColumn(), getMaxRow());
		mainFrame.setComboRowColWMMIF(getMaxColumn(), getMaxRow());
	}
	 
	void setRound(){
		mainFrame.setRoundWM(getRound());
		mainFrame.setRoundWMMIF(getRound());
	}		
		
	void setPixelAsDataPixel(int row,int col){
		if(row>=size || col>=size){
			return;
		}		
		//// Get the pixel first and change its color to GREEN
		for(Pixel pl:pixelMatrix){
			if(pl.getX()==col && pl.getY()==row){
				pl.changeColor("GREEN");
			}
		}		
	}
	
	void clearPixelAsDataPixel(int row,int col){
		if(row>=size || col>=size){
			return;
		}		
		//// Get the pixel first and change its color to GREEN
		for(Pixel pl:pixelMatrix){
			if(pl.getX()==col && pl.getY()==row){
				pl.changeColor("WHITE");
			}
		}		
	}
	
	int getDataPixelCount(){
		return dataPixelMatrix.size();
	}
	
	int getInfoPixelCount(){
		return infoPixelMatrix.size();
	}
	
	void clearDataPixels(){
		for(Pixel pxl:dataPixelMatrix){
			pxl.changeColor("WHITE");
		}
		dataPixelMatrix.clear();
	}
	
	void clearInfoPixels(){
		for(Pixel pxl:infoPixelMatrix){
			pxl.changeColor("WHITE");
		}
		infoPixelMatrix.clear();
	}
	
	void pixelEventOccurs(){
		
		//// Set the round
		setRound();
		//// Set the maximum row and column
		setMaxColAndRow();
		
		//// Sort the data pixels using the Pixel comparator function
		Collections.sort(dataPixelMatrix);
				
		//// Clear all the WM text boxes
		for(int r=0;r<5;r++){
			for(int c=0;c<5;c++){
				mainFrame.textWMNumbers[r][c].setText("");
			}
		}
		
		//// Put the numbers in the WM text boxes according to pixel matrix selection
		int wmValue=1;
		int initX=dataPixelMatrix.get(0).getX();
		int initY=dataPixelMatrix.get(0).getY();
		
		if(dataPixelMatrix.size()>25){
			PCUtility.MessageBox("Data Matrix size exceeds 25");
			return;
		}
		int lastX=0;
		int lastY=0;
		
		for(Pixel pxl:dataPixelMatrix){			
			mainFrame.textWMNumbers[pxl.getY()-initY][pxl.getX()-initX].setText(""+wmValue);
			lastX=pxl.getX();
			lastY=pxl.getY();
			
			wmValue++;
		}
		//// Set the last box here.
		if(Integer.parseInt(mainFrame.comboColumn.getValue())*Integer.parseInt(mainFrame.comboRow.getValue())==9){
			mainFrame.textWMNumbers[lastY-initY][lastX-initX].setText(""+(wmValue/2));
		}
		
	}
}
/////////////////////////////  End SelectionMatrix ///////////////////////////////////

///////////////////////////////// Class Pixel ////////////////////////////////////////
class Pixel extends Canvas implements Comparable<Pixel>{
	
	public int x,y;
	GraphicsContext gc;
	SelectionMatrix selectionMatrix;
	
	Pixel(SelectionMatrix sm, int xx,int yy){
		x=xx;
		y=yy;
		selectionMatrix=sm;
		
		gc=this.getGraphicsContext2D();
		
		setWidth(30);
    	setHeight(30); 
    	
		setOnMouseClicked(event -> {
            //Left Click Event
    		if(event.getButton().equals(MouseButton.PRIMARY)){   
    			changeColor("GREEN");
       		}////Right Click Event
    		else{
    			changeColor("RED");    			
       		}   
    		
    		selectionMatrix.pixelEventOccurs();
        });
	}		
	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}
	
	////Changes the color of the selection board
	void changeColor(String color){
		
		if(color.equalsIgnoreCase("GREEN")){
			if(gc.getFill()==javafx.scene.paint.Color.GREEN){
				gc.setFill(javafx.scene.paint.Color.WHITE);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
				selectionMatrix.dataPixelMatrix.remove(this);
			}else{
				gc.setFill(javafx.scene.paint.Color.GREEN);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
				selectionMatrix.dataPixelMatrix.add(this);
			}			
		}else if(color.equalsIgnoreCase("RED")){
			if(gc.getFill()==javafx.scene.paint.Color.RED){
				gc.setFill(javafx.scene.paint.Color.WHITE);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
				selectionMatrix.infoPixelMatrix.remove(this);
			}else{
				gc.setFill(javafx.scene.paint.Color.RED);
				gc.setStroke(javafx.scene.paint.Color.BLACK);
				selectionMatrix.infoPixelMatrix.add(this);
			}			
		}else if(color.equalsIgnoreCase("WHITE")){
			gc.setFill(javafx.scene.paint.Color.WHITE);
			gc.setStroke(javafx.scene.paint.Color.BLACK);
		}
		
		gc.fillRect(0, 0, 30, 30);
		gc.strokeRect(0, 0, 30, 30);
	}//// End changeColor
	
	public int compareTo(Pixel pxl) {
        //let's sort the employee based on id in ascending order
        //returns a negative integer, zero, or a positive integer as this employee id
        //is less than, equal to, or greater than the specified object.
		if(this.y < pxl.y){
			return -1;
		}else if(this.y == pxl.y){
			if(this.x < pxl.x)
			return -1;
		}else if(this.y > pxl.y){
			return 1;
		}
		return 0;
    }
	
}
/////////////////////////////// END Pixel /////////////////////////////////////


/////////////////////////////   class PCUtility  //////////////////////////////
class PCUtility extends Task<Void>{	

	//// Displays MessageBox
	static void MessageBox(String message){
		MessageBox(message,"ERROR");			
	}
	static void MessageBox(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox

	////Convert to binary string of 8 bits
	static String ConvertToNBitBinaryString(int n,int nBit){
		StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
		int padding = nBit - binaryStringBuilder.length();
		for(int i=0;i<padding;i++){
			binaryStringBuilder.insert(0, "0");
		}		
		return binaryStringBuilder.toString();
	
	}//// End ConvertToBinaryString

	////This function calculates sum of all matrix elements
	static int SumOfMatrixElements(int[][] matrix,int row,int col){		
		int sum=0;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				sum+=matrix[i][j];
			}
		}		
		return sum;
	}

	////Calculate the mod operation, 
	//// The % operator gives only the remainder, not mod.
	static int CalculateMod(int x, int y)
	{
		int result = x % y;
		if (result < 0)
		{
			result += y;
		}
		return result;
	}//// End calculateMod

	////This function converts a binary string to a character
	static String binaryStringToCharacter(String strBinary){
	
		int value=Integer.parseInt(strBinary,2);
		return Character.toString((char)value);
	
	}//// End binaryStringToCharacter

	//// This function return a character from a binary array
	static String binaryArrayToCharacter(String strBinary,int nBit){
		StringBuilder strCharacters=new StringBuilder();
		
		for(int i=0;i<strBinary.length()/nBit;i++){
			String strChar="";
			try{
				strChar=binaryStringToCharacter(strBinary.substring(i*nBit,i*nBit+nBit));
			}catch(Exception ex){
				strChar="";
				break;
			}
			strCharacters.append(strChar);
		}

		return strCharacters.toString();
	}//// End binaryArrayToCharacter

	//// This file saves an image from BufferedImage
	static boolean SaveBufferedImageAsFile(BufferedImage outputImage, String filePath){
		//// Create the output image file and save it in a file
		File imageFile = new File(filePath);
		try {
			ImageIO.write(outputImage, "PNG", imageFile);
		} catch (IOException e) {
			PCUtility.MessageBox("SaveAsImage error!!!");
			e.printStackTrace();
			return false;
		} 
		return true;
	}//// End SaveBufferedImageAsFile
	
	////Create a output file from a string
	static boolean CreateWMOutputTextFile(String filePath, String message){
	
		PrintWriter fw=null;
		BufferedWriter bw=null;
		
		try{
			fw = new PrintWriter(filePath);
			bw = new BufferedWriter(fw);
			//// Write the message to the output file		
			bw.write(message);					
			
			bw.close();
			fw.close();
		}catch(Exception ex){
			return false;
		}				    
		return true;
	}//// End createWMOutputTextFile
	
	////Append to an existing text file
	static int AppendToTextFile(String filePath,StringBuilder sbContent){
		
		try(FileWriter fw = new FileWriter(filePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.print(sbContent);
			} catch (IOException e) {
			    PCUtility.MessageBox("Error Writing "+filePath);
			    return -1;
			}
		return 0;
		
	}//// End AppendToTextFile

	//// This function reads a file by line
	static boolean ReadFileByLines(String filePath,StringBuilder sbFileData){
		
		try{
			// Open the file
			FileInputStream fstream = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine="";			

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				sbFileData.append(strLine);
			}
			//Close the input stream
			br.close();
		}catch(Exception ex){
			PCUtility.MessageBox("Error Reading MIF file");
			return false;
		}		
		return true;
	}//// End ReadFileByLines
	
	////This function reads 3x3 pixel block from the image
	static String displayMatrix(int[][] matrix,int row,int col,int startRow,int startCol){
	
		StringBuilder strMatrix=new StringBuilder();
		String matrix1="";
		for(int i=startRow;i<startRow+row;i++){
			for(int j=startCol;j<startCol+col;j++){
				matrix1=String.format("%8d", matrix[i][j]);
				strMatrix.append(matrix1);
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString();
	}//// End displayMatrix

	////This function reads 3x3 pixel block from the image
	static String displayMatrixInteger(Integer[][] matrix,int row,int col,int startRow,int startCol){
	
		StringBuilder strMatrix=new StringBuilder();
		String matrix1="";
		for(int i=startRow;i<startRow+row;i++){
			for(int j=startCol;j<startCol+col;j++){
				matrix1=String.format("%8d", matrix[i][j]);
				strMatrix.append(matrix1);
			}
			strMatrix.append("\n");
		}
		return strMatrix.toString();
	}//// End displayMatrix
	
	////Get the input file type
	static String getFileType(String filePath){
	
		//// Check if the secret message is a file
		File file = new File(filePath);
		if (file.exists()){
			if(file.isFile()){
				//// Check file types
				String extension = "";
				int i = filePath.lastIndexOf('.');
				if (i > 0) {
					extension = filePath.substring(i+1);
				}
				//// Text file
				if(extension.equalsIgnoreCase("txt")){
					return "txt";
				}//// Audio file
				else if( extension.equalsIgnoreCase("wav")){
					return "wav";		    		
				}//// Video file
				else if( extension.equalsIgnoreCase("mp4")){
					return "mp4";
				}
				else if( extension.equalsIgnoreCase("png")){
					return "png";
				}
				else if( extension.equalsIgnoreCase("jpg")){
					return "jpg";
				}
			}		
		}		
		return "";			
	}//// End getFileType

	////This function rounds double value
	static double round(double value, int places) {
		if (places < 0) 
			throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}//// End round

	////This function returns the PSNR value of two images
	static void GetPSNR(File file, BufferedImage i1, BufferedImage i2)
	{	
		double mse,psnr;
		String strPSNR="";			
		try{
			// Image features
			int cc1 = i1.getColorModel().getNumComponents();
			double bpp1 = i1.getColorModel().getPixelSize()/cc1;
			int cc2 = i2.getColorModel().getNumComponents();
			double bpp2 = i2.getColorModel().getPixelSize()/cc2;
			int cc = 4;
			if( cc1 == 3 && cc2 == 3 ) {
				cc = 3;
			}
			// Set up the summations:
			KahanSummation tr = new KahanSummation();
			KahanSummation tg = new KahanSummation();
			KahanSummation tb = new KahanSummation();
			KahanSummation ta = new KahanSummation();
			BigInteger br = BigInteger.valueOf(0);
			BigInteger bg = BigInteger.valueOf(0);
			BigInteger bb = BigInteger.valueOf(0);
			BigInteger ba = BigInteger.valueOf(0);
						
			for (int i = 0; i < i1.getWidth(); i++) {
				for (int j = 0; j < i1.getHeight(); j++) {
					final Color c1 = new Color(i1.getRGB(i, j));
					final Color c2 = new Color(i2.getRGB(i, j));
					final int dr = c1.getRed() - c2.getRed();
					final int dg = c1.getGreen() - c2.getGreen();
					final int db = c1.getBlue() - c2.getBlue();
					final int da = c1.getAlpha() - c2.getAlpha();
					tr.add( dr*dr );
					tg.add( dg*dg );
					tb.add( db*db );
					ta.add( da*da );
					br = br.add( BigInteger.valueOf(dr*dr));
					bg = bg.add( BigInteger.valueOf(dg*dg));
					bb = bb.add( BigInteger.valueOf(db*db));
					ba = ba.add( BigInteger.valueOf(da*da));
				} 
			}
			// Compute the Mean Square Error(MSE):
			mse = (tr.getSum() + tg.getSum() + tb.getSum() + ta.getSum()) / (i1.getWidth() * i1.getHeight() * cc);
			
			if (mse == 0) {
				//// Set PSNR to Infinity
				psnr=99999.0;
			}else{
			
				BigDecimal bmse = new BigDecimal("0.00");
				bmse = bmse.add(new BigDecimal(br));
				bmse = bmse.add(new BigDecimal(bg));
				bmse = bmse.add(new BigDecimal(bb));
				bmse = bmse.add(new BigDecimal(ba));
				bmse = new BigDecimal( bmse.doubleValue() / (i1.getWidth() * i1.getHeight() * cc) );
				
				System.out.println("bmse = "+bmse);
				//mse = bmse.doubleValue();
				// Get the bits per pixel:
				
				if( bpp1 != bpp2 ) {
					//log.warning("Bits-per-pixel do not match up! bpp1 = "+bpp1+", bpp2 = "+bpp2);
					PCUtility.MessageBox("Bits-per-pixel do not match up! bpp1 = "+bpp1+", bpp2 = "+bpp2);
				}
				
				double bpp = bpp1;
				
				System.out.println("read bpp = "+bpp);
				System.out.println("colcomp = "+cc);
				
				bpp = 8;
				// The maximum is therefore:
				double max = Math.pow(2.0, bpp) - 1.0;
				// Compute the peak signal to noise ratio:
				psnr = 10.0 * StrictMath.log10( max*max / mse );
				
				strPSNR="";
				strPSNR += "MSE = " + mse + "\n" +
				"Peak signal to noise ratio (PSNR): " + psnr + "\n" +
				"Peak signal to noise ratio (BigDecimal): " + 10.0 * StrictMath.log10(max*max/bmse.doubleValue());
			}
			
			//// Save the result in a text file
			String strFileName = System.getProperty("user.home") + "\\Desktop\\PSNR_Result.txt";
			try(FileWriter fw = new FileWriter(strFileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw))
			{
				String fileName=PCUtility.GetFileNameWithoutExtension(file);
				if(psnr>=99999){
					pw.println(fileName + " -> " + "0" + ", " + "Infinity");
				}else{
					pw.println(fileName + " -> " + round(mse,4)+ ", " + round(psnr,4));
				}
			
			} catch (IOException e) {
				PCUtility.MessageBox("Error Saving PSNR Output File");
				return;
			}  		
			//// Display the message
			System.out.println(strPSNR);	
			
		}catch(Exception ex){
			MessageBox("PSNR Error!!!");
			return;
		}		
			
	}//// End GetPSNR

	////This function compares two images
	static void ImageCompareFunction(BufferedImage originalImage, BufferedImage changedImage)
	{		
		try{
			
			long start = System.currentTimeMillis();
			int q=0;
			StringBuilder strImageCompre=new StringBuilder();
			
			int width = originalImage.getWidth(null);
			int height = originalImage.getHeight(null);
			int[][] clr=  new int[width][height];
			
			int widthe = changedImage.getWidth(null);
			int heighte = changedImage.getHeight(null);
			int[][] clre=  new int[widthe][heighte]; 
			
			int smw=0;
			int smh=0;
			int p=0;
			//CALUCLATING THE SMALLEST VALUE AMONG WIDTH AND HEIGHT
			if(width>widthe)
			{ 
				smw =widthe;
			}
			else 
			{
				smw=width;
			}
			if(height>heighte)
			{
				smh=heighte;
			}
			else 
			{
				smh=height;
			}
			
			//CHECKING NUMBER OF PIXELS SIMILARITY
			List<String> unmatchedPixelsX=new ArrayList<>();
			List<String> unmatchedPixelsY=new ArrayList<>();
			
			for(int b=0;b<smh;b++){
				for(int a=0;a<smw;a++){
					clre[a][b]=changedImage.getRGB(a,b);
					clr[a][b]=originalImage.getRGB(a,b);
					if(clr[a][b]==clre[a][b]) 
					{
						p=p+1;
						strImageCompre.append("\t");
						//bw.write("\t");
						strImageCompre.append(Integer.toString(a));
						//bw.write(Integer.toString(a));
						strImageCompre.append("\t");
						//bw.write("\t");
						strImageCompre.append(Integer.toString(b));
						// bw.write(Integer.toString(b));
						strImageCompre.append("\n");
						//bw.write("\n");
					}
					else{
						q=q+1;
						unmatchedPixelsX.add(""+a);
						unmatchedPixelsY.add(""+b);
					}
				}
			}
			
			float s = (smw*smh);
			//CALUCLATING PERCENTAGE
			float x =(100*p)/s;
			
			long stop = System.currentTimeMillis();
			String strCompareResult="";
			
			strCompareResult+="THE PERCENTAGE SIMILARITY IS APPROXIMATELY = "+x+"%" + "\n"+
			"TIME TAKEN IS ="+(stop-start) + " Milliseconds"+"\n"+
			"NO OF PIXEL GETS MATCHED:="+p + "\n" +
			"NO OF PIXEL NOT MATCHED:="+q + "\n";		
			
			StringBuilder strUnmatchedPixels=new StringBuilder();
			int count=0;
			for(String posX:unmatchedPixelsX){
				strUnmatchedPixels.append("("+posX+",");
				strUnmatchedPixels.append(unmatchedPixelsY.get(count)+") ");
				count++;
			}
			
			strCompareResult+="\n"+strUnmatchedPixels.toString();
			
			//// Display the results 
			PCUtility.MessageBox(strCompareResult);
			
		}catch(Exception ex){
			MessageBox("Image Compare Error!!!");
			return;
		}
		
	}//// End ImageCompareFunction

	//// This function crops n image to a specific size
	static void CropImage(String inputFilePath,String outputFilePath,int x,int y,int width,int height){
		
		try{
			BufferedImage image = ImageIO.read(new File(inputFilePath));
	    	BufferedImage out = image.getSubimage(x, y, width, height);
		    	
	    	File imageFile = new File(outputFilePath);
	      	ImageIO.write(out, "png", imageFile);
		}catch(Exception ex){
			PCUtility.MessageBox("Error Occured = "+ex.toString());
			return;
		}	
		PCUtility.MessageBox("Image Created Successfully.","Information");
	}//// End CropImage
	
	////This function crops n image to a specific size
	static void SaltAndPepperEffect(String inputImageFilePath,String outputFilePath,int nSalt,int nPepper){
		
		
		int whiteColor=(255 << 16 | 255 << 8 | 255);
		int blackColor=(0 << 16 | 0 << 8 | 0);
		 
		try{
			
			BufferedImage inputImage = ImageIO.read(new File(inputImageFilePath));
			int height = inputImage.getHeight();
	        int width = inputImage.getWidth();			
			BufferedImage outputImage = inputImage.getSubimage(0, 0, width, height);
	        
	        int salt = (height * width * nSalt) / 100;    // Amount of salt
	        int pepper = (height * width * nPepper) / 100;  // Amount of pepper
	        
	        for( int i = 0; i < salt; i++ )
	        { 
	            int x = (int) (Math.random() * width); 
	            int y = (int) (Math.random() * height);
	      
	            outputImage.setRGB(x, y, whiteColor);
	       
	        }
	        
	        for( int i = 0; i < pepper; i++ )
	        {
	            int x = (int) (Math.random() * width); 
	            int y = (int) (Math.random() * height);
	            
	            outputImage.setRGB( x, y, blackColor );
	        }	
	    	
	    	File imageFile = new File(outputFilePath);
	      	ImageIO.write(outputImage, "png", imageFile);
	      	
		}catch(Exception ex){
			MessageBox("Salt And Pepper Effected Image Creation Error!!!");
		}
				
		PCUtility.MessageBox("Salt And Pepper Image Created Successfully.");
	}//// End CropImage
	
	////This function check if an image is suitable for recovery in WM
	static int IsSuitableImage(String inputFilePath, int limitMin, int limitMax){
		
		int pixelValue,R,G,B;
		
		try{
			BufferedImage image = ImageIO.read(new File(inputFilePath));
			
			int width=image.getWidth();
			int height=image.getHeight();
			
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){					
					//// Get two pixel values
	            	pixelValue = image.getRGB(x, y);
	            	Color pixelColor=new Color(pixelValue);
	               	R = pixelColor.getRed();
	            	G = pixelColor.getGreen();
	            	B = pixelColor.getBlue();
	            	
	            	//// Test 255 value pixel
	            	if((R+limitMax)>255 || (G+limitMax)>255 || (B+limitMax)>255 ){
	            		return 0;
	            	}
	            	//// Test 0 value pixel
	            	if((R-limitMin)<0 || (R-limitMin)<0 || (R-limitMin)<0 ){
	            		return 0;
	            	}
				}
			}
	      	
		}catch(Exception ex){
			return -1;  
		}			
		return 1;
		
	}//// End CropImage
	
	//// Calculate Mean of an image
	public static double meanValueOfImage(BufferedImage image) {
		
	   Raster raster = image.getRaster();
	   double sum = 0.0;

       for (int y = 0; y < image.getHeight(); ++y){
         for (int x = 0; x < image.getWidth(); ++x){
           sum += raster.getSample(x, y, 0);
         }
       }
       return sum / (image.getWidth() * image.getHeight());
      
    }//// End meanValue
	
	//// Calculate Standard Deviation and correlation coefficient of an image  
    public static void GetSDAndCoCOfImage(File file, BufferedImage imageOriginal, BufferedImage imageChanged){
    	
    	GetSDOfImageHorizontally(file, imageOriginal,imageChanged);
    	//strSDAndCoC+=GetSDOfImageVertically(imageOriginal,imageChanged)+"\n";
    	//strSDAndCoC+=GetSDOfImage45Degree(imageOriginal,imageChanged)+"\n";
    	//strSDAndCoC+=GetSDOfImage135Degree(imageOriginal,imageChanged)+"\n";
    	        
    }//// End GetSDAndCoCOfImage
    
    ////Calculate Standard Deviation of an image Horizontally 
    public static void GetSDOfImageHorizontally(File file, BufferedImage imageOriginal, BufferedImage imageChanged){
    	
    	double sdOriginal=0.0;   
    	double sdChanged=0.0;    	
    	double cc=0.0;
    	String strSD="";
    	
    	try{
    		
    		int widthOriginal=0;
        	int heightOriginal=0;
        	int widthChanged=0;
        	int heightChanged=0;
        	int numberOfPixels=0;;
        	        	
        	double sum1=0.0;
        	double sum2=0.0;
            double mean1=0.0;
            double mean2=0.0;
            double variance=0.0;
            double squareRoot = 0.0;
         
            int pixelRed;
            int pixelGreen;
            int pixelBlue;
            int pixelValue=0;
        	
        	//// Calculating SD of original image
        	if(imageOriginal != null){
        		widthOriginal=imageOriginal.getWidth();
            	heightOriginal=imageOriginal.getHeight();
        		sum1=0.0;
            	variance=0.0;
            	numberOfPixels=widthOriginal*heightOriginal;
                for (int y = 0; y < heightOriginal; y++){
                    for (int x = 0; x < widthOriginal; x++){
                    	pixelValue=imageOriginal.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum1+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean1=sum1/(numberOfPixels);
                
                sum1=0;
                for (int y = 0; y < heightOriginal; y++){
                     for (int x = 0; x < widthOriginal; x++){
                    	               	 
                    	 pixelValue=imageOriginal.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum1=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum1 - mean1, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdOriginal = Math.sqrt(squareRoot); 
        	}        	
                    	
            ///// Calculating SD of changed image
        	if(imageChanged != null){
        		widthChanged=imageChanged.getWidth();
            	heightChanged=imageChanged.getHeight();
        		sum2=0.0;
                variance=0.0;
                numberOfPixels=widthChanged*heightChanged;
                for (int y = 0; y < heightChanged; y++){
                    for (int x = 0; x < widthChanged; x++){
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum2+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean2=sum2/(numberOfPixels);
                
                sum2=0.0;
                for (int y = 0; y < heightChanged; y++){
                     for (int x = 0; x < widthChanged; x++){
                    	               	 
                    	 pixelValue=imageChanged.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum2=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum2 - mean2, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdChanged = Math.sqrt(squareRoot); 
        	}
        	
        	///////////    Calculating correlation coefficient    /////////////////
        	if(imageOriginal != null && imageChanged != null){
        		double sumTotal=0.0;
            	numberOfPixels=heightOriginal*widthOriginal;
            	sum1=0.0;
            	sum2=0.0;
            	for (int y = 0; y < heightOriginal; y++){
                    for (int x = 0; x < widthOriginal; x++){
               	
                    	pixelValue=imageOriginal.getRGB(x, y);  
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum1=(pixelRed+pixelGreen+pixelBlue);   
                    	 
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum2=(pixelRed+pixelGreen+pixelBlue);                	
                    	
                    	sumTotal+=((sum1-mean1)*(sum2-mean2));                	
                    }
            	}
            	
            	cc=(sumTotal/(numberOfPixels*sdOriginal*sdChanged));  
        	}        	
        	 
        	//// Display the SD of the image
            strSD="SD (Left) = "+round(sdOriginal,4)+
            	  ", SD (Right) = "+round(sdChanged,4)+  
        	      ", CC = "+round(cc,4);
            
    	}catch(Exception ex){
    		MessageBox("Standard Deviation Calculation Error !!!"); 
    		return;
    	}  
    	
    	//// Save the result in a text file
		String strFileName = System.getProperty("user.home") + "\\Desktop\\SDCC_Result.txt";
		try(FileWriter fw = new FileWriter(strFileName, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw))
		{
			String fileName=PCUtility.GetFileNameWithoutExtension(file);
			pw.println(fileName + " -> " + strSD);			
		} catch (IOException e) {
			PCUtility.MessageBox("Error Saving SDCC_Result.txt File");
			return;
		}  		
		//// Display the message
		System.out.println(strSD);
			
        
    }//// End GetSDOfImageHorizontally   
    
    ////Calculate Standard Deviation of an image Vertically
    public static String GetSDOfImageVertically(BufferedImage imageOriginal, BufferedImage imageChanged){
    
    	double sdOriginal=0.0;   
    	double sdChanged=0.0;    	
    	double cc=0.0;
    	 
    	try{
    		
    		int widthOriginal=0;
        	int heightOriginal=0;
        	int widthChanged=0;
        	int heightChanged=0;
        	int numberOfPixels=0;;
        	        	
        	double sum1=0.0;
        	double sum2=0.0;
            double mean1=0.0;
            double mean2=0.0;
            double variance=0.0;
            double squareRoot = 0.0;
         
            int pixelRed;
            int pixelGreen;
            int pixelBlue;
            int pixelValue=0;
        	
        	//// Calculating SD of original image
        	if(imageOriginal != null){
        		widthOriginal=imageOriginal.getWidth();
            	heightOriginal=imageOriginal.getHeight();
        		sum1=0.0;
            	variance=0.0;
            	numberOfPixels=widthOriginal*heightOriginal;
                for (int x = 0; x < widthOriginal; x++){
                    for (int y = 0; y < heightOriginal; y++){
                    	pixelValue=imageOriginal.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum1+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean1=sum1/(numberOfPixels);
                
                sum1=0;
                for (int x = 0; x < widthOriginal; x++){
                     for (int y = 0; y < heightOriginal; y++){
                    	               	 
                    	 pixelValue=imageOriginal.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum1=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum1 - mean1, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdOriginal = Math.sqrt(squareRoot); 
        	}        	
                    	
            ///// Calculating SD of changed image
        	if(imageChanged != null){
        		widthChanged=imageChanged.getWidth();
            	heightChanged=imageChanged.getHeight();
        		sum2=0.0;
                variance=0.0;
                numberOfPixels=widthChanged*heightChanged;
                for (int x = 0; x < widthChanged; x++){
                    for (int y = 0; y < heightChanged; y++){
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum2+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean2=sum2/(numberOfPixels);
                
                sum2=0.0;
                for (int x = 0; x < widthChanged; x++){
                     for (int y = 0; y < heightChanged; y++){
                    	               	 
                    	 pixelValue=imageChanged.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum2=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum2 - mean2, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdChanged = Math.sqrt(squareRoot); 
        	}
        	
        	///////////    Calculating correlation coefficient    /////////////////
        	if(imageOriginal != null && imageChanged != null){
        		double sumTotal=0.0;
            	numberOfPixels=heightOriginal*widthOriginal;
            	sum1=0.0;
            	sum2=0.0;
            	for (int x = 0; x < widthOriginal; x++){
                    for (int y = 0; y < heightOriginal; y++){
               	
                    	pixelValue=imageOriginal.getRGB(x, y);  
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum1=(pixelRed+pixelGreen+pixelBlue);   
                    	 
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum2=(pixelRed+pixelGreen+pixelBlue);                	
                    	
                    	sumTotal+=((sum1-mean1)*(sum2-mean2));                	
                    }
            	}            	
            	cc=(sumTotal/(numberOfPixels*sdOriginal*sdChanged));  
        	}        	
        	 
    	}catch(Exception ex){
    		MessageBox("Standard Deviation Calculation Error !!!"); 
    		return "";
    	}      	 
    	//// Display the SD of the image
        return "Standard Deviation (Left)   = "+sdOriginal+"\n"+
        	   "Standard Deviation (Right) = "+sdChanged+"\n"+
    	       "Correlation Coefficient = "+cc+"\n\n";        
    }
    
    ////Calculate Standard Deviation of an image 45Degree
    public static String GetSDOfImage45Degree(BufferedImage imageOriginal, BufferedImage imageChanged){
    
    	double sdOriginal=0.0;   
    	double sdChanged=0.0;    	
    	double cc=0.0;
    	 
    	try{
    		
    		int widthOriginal=0;
        	int heightOriginal=0;
        	int widthChanged=0;
        	int heightChanged=0;
        	int numberOfPixels=0;;
        	        	
        	double sum1=0.0;
        	double sum2=0.0;
            double mean1=0.0;
            double mean2=0.0;
            double variance=0.0;
            double squareRoot = 0.0;
         
            int pixelRed;
            int pixelGreen;
            int pixelBlue;
            int pixelValue=0;
        	
        	//// Calculating SD of original image
        	if(imageOriginal != null){
        		widthOriginal=imageOriginal.getWidth();
            	heightOriginal=imageOriginal.getHeight();
        		sum1=0.0;
            	variance=0.0;
            	numberOfPixels=widthOriginal*heightOriginal;
            	
            	for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z2; j >= z1; --j) {
                            //pixel[j][slice - j]);
                            pixelValue=imageOriginal.getRGB(j, block - j);
                        	pixelRed=(pixelValue >> 16) & 0xFF;
                        	pixelGreen=(pixelValue >> 8) & 0xFF;
                        	pixelBlue=(pixelValue >> 0) & 0xFF;
                        	
                        	sum1+=(pixelRed+pixelGreen+pixelBlue);
                    }                    
                }
                mean1=sum1/(numberOfPixels);
                
                sum1=0;                
                for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z2; j >= z1; --j) {
                            //pixel[j][slice - j]);
                            pixelValue=imageOriginal.getRGB(j, block - j);
                        	pixelRed=(pixelValue >> 16) & 0xFF;
                        	pixelGreen=(pixelValue >> 8) & 0xFF;
                        	pixelBlue=(pixelValue >> 0) & 0xFF;
                        	
                        	sum1=(pixelRed+pixelGreen+pixelBlue);                 	
                       	 	variance += Math.pow(sum1 - mean1, 2); 
                    }                    
                }
                squareRoot = variance / (numberOfPixels);            
                sdOriginal = Math.sqrt(squareRoot); 
        	}        	
                    	
            ///// Calculating SD of changed image
        	if(imageChanged != null){
        		widthChanged=imageChanged.getWidth();
            	heightChanged=imageChanged.getHeight();
        		sum2=0.0;
                variance=0.0;
                numberOfPixels=widthChanged*heightChanged;
                for (int block = 0; block < heightChanged + widthChanged - 1; ++block) {
                    int z1 = block < widthChanged ? 0 : block - widthChanged + 1;
                    int z2 = block < heightChanged ? 0 : block - heightChanged + 1;
                    for (int j = block - z2; j >= z1; --j) {
                    	pixelValue=imageChanged.getRGB(j, block - j);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum2+=(pixelRed+pixelGreen+pixelBlue);
                    }                    
                }                
                mean2=sum2/(numberOfPixels);
                
                sum2=0.0;
                for (int block = 0; block < heightChanged + widthChanged - 1; ++block) {
                    int z1 = block < widthChanged ? 0 : block - widthChanged + 1;
                    int z2 = block < heightChanged ? 0 : block - heightChanged + 1;
                    for (int j = block - z2; j >= z1; --j) {
                    	 pixelValue=imageChanged.getRGB(j, block - j);
                    	 pixelRed=(pixelValue >> 16) & 0xFF;
                    	 pixelGreen=(pixelValue >> 8) & 0xFF;
                    	 pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	 sum2=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum2 - mean2, 2);  
                    }                    
                }
                squareRoot = variance / (numberOfPixels);            
                sdChanged = Math.sqrt(squareRoot); 
        	}
        	
        	///////////    Calculating correlation coefficient    /////////////////
        	if(imageOriginal != null && imageChanged != null){
        		double sumTotal=0.0;
            	numberOfPixels=heightOriginal*widthOriginal;
            	sum1=0.0;
            	sum2=0.0;
            	for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z2; j >= z1; --j) {
                    	pixelValue=imageOriginal.getRGB(j, block - j);  
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum1=(pixelRed+pixelGreen+pixelBlue);   
                    	 
                    	pixelValue=imageChanged.getRGB(j, block - j);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum2=(pixelRed+pixelGreen+pixelBlue);                	
                    	
                    	sumTotal+=((sum1-mean1)*(sum2-mean2));
                    }                    
                }
            	cc=(sumTotal/(numberOfPixels*sdOriginal*sdChanged));  
        	}        	
        	 
    	}catch(Exception ex){
    		MessageBox("Standard Deviation Calculation Error !!!"); 
    		return "";
    	}  
    	 
    	//// Display the SD of the image
        return "Standard Deviation (Left)   = "+sdOriginal+"\n"+
        	   "Standard Deviation (Right) = "+sdChanged+"\n"+
    	       "Correlation Coefficient = "+cc+"\n\n";
        
    }
    
    ////Calculate Standard Deviation of an image 135Degree
    public static String GetSDOfImage135Degree(BufferedImage imageOriginal, BufferedImage imageChanged){
    
    	double sdOriginal=0.0;   
    	double sdChanged=0.0;    	
    	double cc=0.0;
    	 
    	try{
    		
    		int widthOriginal=0;
        	int heightOriginal=0;
        	int widthChanged=0;
        	int heightChanged=0;
        	int numberOfPixels=0;;
        	        	
        	double sum1=0.0;
        	double sum2=0.0;
            double mean1=0.0;
            double mean2=0.0;
            double variance=0.0;
            double squareRoot = 0.0;
         
            int pixelRed;
            int pixelGreen;
            int pixelBlue;
            int pixelValue=0;
        	
        	//// Calculating SD of original image
        	if(imageOriginal != null){
        		widthOriginal=imageOriginal.getWidth();
            	heightOriginal=imageOriginal.getHeight();
        		sum1=0.0;
            	variance=0.0;
            	numberOfPixels=widthOriginal*heightOriginal;
            	
            	for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z1; j >= z2; --j) {
                            //pixel[j][slice - j]);
                            pixelValue=imageOriginal.getRGB(block - j,j);
                        	pixelRed=(pixelValue >> 16) & 0xFF;
                        	pixelGreen=(pixelValue >> 8) & 0xFF;
                        	pixelBlue=(pixelValue >> 0) & 0xFF;
                        	
                        	sum1+=(pixelRed+pixelGreen+pixelBlue);
                    }                    
                }
                mean1=sum1/(numberOfPixels);
                
                sum1=0;                
                for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z1; j >= z2; --j) {
                            //pixel[j][slice - j]);
                            pixelValue=imageOriginal.getRGB(block - j,j);
                        	pixelRed=(pixelValue >> 16) & 0xFF;
                        	pixelGreen=(pixelValue >> 8) & 0xFF;
                        	pixelBlue=(pixelValue >> 0) & 0xFF;
                        	
                        	sum1=(pixelRed+pixelGreen+pixelBlue);                 	
                       	 	variance += Math.pow(sum1 - mean1, 2); 
                    }                    
                }
                squareRoot = variance / (numberOfPixels);            
                sdOriginal = Math.sqrt(squareRoot); 
        	}        	
                    	
            ///// Calculating SD of changed image
        	if(imageChanged != null){
        		widthChanged=imageChanged.getWidth();
            	heightChanged=imageChanged.getHeight();
        		sum2=0.0;
                variance=0.0;
                numberOfPixels=widthChanged*heightChanged;
                for (int block = 0; block < heightChanged + widthChanged - 1; ++block) {
                    int z1 = block < widthChanged ? 0 : block - widthChanged + 1;
                    int z2 = block < heightChanged ? 0 : block - heightChanged + 1;
                    for (int j = block - z1; j >= z2; --j) {
                    	pixelValue=imageChanged.getRGB(block - j,j);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum2+=(pixelRed+pixelGreen+pixelBlue);
                    }                    
                }                
                mean2=sum2/(numberOfPixels);
                
                sum2=0.0;
                for (int block = 0; block < heightChanged + widthChanged - 1; ++block) {
                    int z1 = block < widthChanged ? 0 : block - widthChanged + 1;
                    int z2 = block < heightChanged ? 0 : block - heightChanged + 1;
                    for (int j = block - z1; j >= z2; --j) {
                    	 pixelValue=imageChanged.getRGB(block - j,j);
                    	 pixelRed=(pixelValue >> 16) & 0xFF;
                    	 pixelGreen=(pixelValue >> 8) & 0xFF;
                    	 pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	 sum2=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum2 - mean2, 2);  
                    }                    
                }
                squareRoot = variance / (numberOfPixels);            
                sdChanged = Math.sqrt(squareRoot); 
        	}
        	
        	///////////    Calculating correlation coefficient    /////////////////
        	if(imageOriginal != null && imageChanged != null){
        		double sumTotal=0.0;
            	numberOfPixels=heightOriginal*widthOriginal;
            	sum1=0.0;
            	sum2=0.0;
            	for (int block = 0; block < heightOriginal + widthOriginal - 1; ++block) {
                    int z1 = block < widthOriginal ? 0 : block - widthOriginal + 1;
                    int z2 = block < heightOriginal ? 0 : block - heightOriginal + 1;
                    for (int j = block - z1; j >= z2; --j) {
                    	pixelValue=imageOriginal.getRGB(block - j,j);  
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum1=(pixelRed+pixelGreen+pixelBlue);   
                    	 
                    	pixelValue=imageChanged.getRGB(block - j,j);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum2=(pixelRed+pixelGreen+pixelBlue);                	
                    	
                    	sumTotal+=((sum1-mean1)*(sum2-mean2));
                    }                    
                }
            	cc=(sumTotal/(numberOfPixels*sdOriginal*sdChanged));  
        	}        	
        	 
    	}catch(Exception ex){
    		MessageBox("Standard Deviation Calculation Error !!!"); 
    		return "";
    	}  
    	 
    	//// Display the SD of the image
        return "Standard Deviation (Left)   = "+sdOriginal+"\n"+
        	   "Standard Deviation (Right) = "+sdChanged+"\n"+
    	       "Correlation Coefficient = "+cc+"\n\n";
        
    }
    
    //// This function reads first N lines from a file.
    void ReadNextNLines(Scanner reader, List<String> array,int linesToRead){    	
    	try{    	
            int counter = 0;
            while(reader.hasNextLine() && counter < linesToRead )
            {
                array.add(reader.nextLine());
                counter++;
            }    		
    	}catch(Exception ex){
    		PCUtility.MessageBox("Error in Reading files in ReadNextNLines()");
    		return;
    	}    	
    }//// End ReadNextNLines
    
    //// This function returns a file name without extension
    static String GetFileNameWithoutExtension(File file){
    	String fileName = file.getName();
    	int pos = fileName.lastIndexOf(".");
    	if (pos > 0) {
    	    fileName = fileName.substring(0, pos);
    	}
    	return fileName;
    }//// End GetFileNameWithoutExtension
     
    //// Calculated the RS Analysis
    static void RSAnalysis(File file,BufferedImage inputImage,int m1,int m2,int m3,int m4)
	{
		int row=inputImage.getHeight();
		int col=inputImage.getWidth();
		int sq=4;
		int i;
		double fx,fx1,fx_1;
		double RG1=0.0,SG1=0.0,UG1=0.0,RG_1=0.0,SG_1=0.0,UG_1=0.0;
		double Fblk[]=new double[4];
		double Fblk1[]=new double[4];
		double Fblk_1[]=new double[4];
		int M[]=new int[4];
		M[0]=m1;M[1]=m2;M[2]=m3;M[3]=m4;
		String result="";
		
		for(int p=0;p<row;p++){
			for(int q=0;q<(col/sq);q++){
			
				i=0;
		        for(int j=sq*q;j<sq*(q+1);j++){
		        	int pixelValue = inputImage.getRGB(j,p);
	            	Color pixelColor=new Color(pixelValue);
	            	Fblk[i] = pixelColor.getRed();
	            	System.out.println(Fblk[i]);
	               	i=i+1;
		        }  
		        
		        fx=0;
		        for(i=1;i<sq;i++){
		        	fx=fx + Math.abs(Fblk[i-1]-Fblk[i]);
		        }
		        
		        for(i=0;i<sq;i++){
		        	
		        	if(M[i]==1){
		        		
	                	Fblk_1[i]=Fblk[i]+1;
	                
		                if((Fblk[i]%2)==1){
		                    Fblk1[i]=Fblk[i]-1;
		                }else{
		                    Fblk1[i]=Fblk[i]+1;
		                }
		                
		                if((Fblk_1[i] % 2)==1){
		                    Fblk_1[i] = Fblk_1[i]-2;
		                }
		                
		        	}else{
	                	Fblk1[i]=Fblk[i];
	                	Fblk_1[i]=Fblk[i];
	                }
	                
		        }
		        
		        fx1=0;
		        fx_1=0;
		        for(i=1;i<sq;i++){
		            fx1=fx1 + Math.abs(Fblk1[i-1]-Fblk1[i]);
		            fx_1=fx_1 + Math.abs(Fblk_1[i-1]-Fblk_1[i]);
		        }
		        
		        if(fx1>fx){
	            	RG1=RG1+1;
		        }else{
		            if(fx1<fx){
		                SG1=SG1+1;
		            }else{
		                UG1=UG1+1;
		            }
		        }
		        
		        if(fx_1>fx){
	            	RG_1=RG_1+1;
		        }else{
		            if(fx_1<fx){
		                SG_1=SG_1+1;
		            }else{
		                UG_1=UG_1+1;
		            }
		        }		        
			}
		}
		
		double RSresult=Math.abs((Math.abs(RG1-RG_1)+Math.abs(SG1-SG_1))/(RG1+SG1));
		result="RSResult = "+round(RSresult,4);
		result+=", Rm = "+RG1+", Rm1 = "+RG_1;
		result+=", Sm = "+SG1+", Sm1 = "+SG_1;
		
		//// Save the result in a text file
		String strFileName = System.getProperty("user.home") + "\\Desktop\\RS_Analysis.txt";
		try(FileWriter fw = new FileWriter(strFileName, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw))
		{
			String fileName=PCUtility.GetFileNameWithoutExtension(file);
			pw.println(fileName + " -> " + result);		
		} catch (IOException e) {
			PCUtility.MessageBox("Error Saving RS Analysis File");
			return;
		}  		
		//// Display the message
		System.out.println(result); 
	}//// End RSAnalysis
    
	@Override
	protected Void call() throws Exception {		
		return null;
	}  
    
}//// End Class PCUtility
/////////////////////////////   End PCUtility  //////////////////////////////////

/////////////////////////////Class KahanSummation ///////////////////////////////
class KahanSummation {

	double value;
	double c;
	
	KahanSummation(){
		value=c=0;
	}
	
	void add( final double v ) {
		final double y = v - c;
		final double t = value + y;
		c = ( t - value ) - y;
		value = t;
	}
	
    double getSum() {
       return value;
    }
}
/////////////////////////////////////////////////////////////////////////////////

//////////////////// Class for Weight Matrix ////////////////////////////////////
class WTMatrix
{
	int[][] WeightMatrixChange;
	int[][] WeightMatrixFirst;
	int[][] WeightMatrixLast;
	int index3[]={1,2,2,8,5,5};	
	int index4[]={1,3,3,3,5,9};
	boolean MIF;
	int count;
	int ROW;
	int COL;
   	
	WTMatrix(int[][] matrix,int r,int c,boolean mif){
		WeightMatrixChange=new int[r][c];
		WeightMatrixFirst=new int[r][c];
		WeightMatrixLast=new int[r][c];
		
		ROW=r;
		COL=c;
		
		//// Initialize First Weight Matrix 
		for(int row=0;row<ROW;row++){
			for(int col=0;col<COL;col++){
				WeightMatrixChange[row][col]=matrix[row][col];
				WeightMatrixFirst[row][col]=matrix[row][col];
				WeightMatrixLast[row][col]=matrix[row][col];
			}
		}
		
		//// Initialize Last Weight Matrix
		count=0;
		while(count<6){
			for(int row=0;row<ROW;row++){
				for(int col=0;col<COL;col++){
					if(ROW*COL==9){
						WeightMatrixLast[row][col]=CalculateMod(WeightMatrixLast[row][col]*index3[count],9);
					}else if(ROW*COL==16){
						WeightMatrixLast[row][col]=CalculateMod(WeightMatrixLast[row][col]*index4[count],17);
					}
					
				}
			}
			count++;
		}		
		
		MIF=mif;
		count=0;		
	}
	
	int[][] getNextMatrix(){
		
		if(MIF==false){
			return WeightMatrixChange;	
		}else{	
			
			if(count>=6){
				count=0;
			}
			
			if(count==0){
				for(int row=0;row<ROW;row++){
					for(int col=0;col<COL;col++){
						WeightMatrixChange[row][col]=WeightMatrixFirst[row][col];
					}
				}  
				count++; 
			}else{
				for(int row=0;row<ROW;row++){
					for(int col=0;col<COL;col++){  
						if(ROW*COL==9){
							WeightMatrixChange[row][col]=CalculateMod(WeightMatrixChange[row][col]*index3[count],9);							
							
						}else if(ROW*COL==16){
							WeightMatrixChange[row][col]=CalculateMod(WeightMatrixChange[row][col]*index4[count],17);							
							
						}
					}
				}
				count++;
			}
			
		}			
		return WeightMatrixChange;
	}//// End getNextMatrix

	//// This function gets the previous weight matrix.
	int[][] getPreviousMatrix(){
		
		if(MIF==false){
			return WeightMatrixChange;	
		}else{	
			
			if(count>=6){
				count=0;
			}			
			if(count==0){
				for(int row=0;row<ROW;row++){
					for(int col=0;col<COL;col++){
						WeightMatrixChange[row][col]=WeightMatrixLast[row][col];
					}
				}  
				count++;
			}else{
				for(int row=0;row<ROW;row++){
					for(int col=0;col<COL;col++){
						if(ROW*COL==9){
							WeightMatrixChange[row][col]=CalculateMod(WeightMatrixChange[row][col]*index3[count],9);
						}else if(ROW*COL==16){
							WeightMatrixChange[row][col]=CalculateMod(WeightMatrixChange[row][col]*index4[count],17);
						}						
					}
				}
				count++;
			}
			return WeightMatrixChange;
		}			
		
	}//// End getPreviousMatrix
	
	int CalculateMod(int x, int y)
	{
		int result = x % y;
		if (result < 0)
		{
			result += y;
		}
		return result;
	}//// End calculateMod
	
}//// End class WTMatrix
//////////////////////////// End Matrix class //////////////////////////////////////

class ImageUtility extends Task<Void>
{
	String strOperation;
	ListView<String> listViewImg1;
	ListView<String> listViewImg2;
	
	ImageUtility(String operation,ListView<String> list1,ListView<String> list2){
		strOperation=operation;
		listViewImg1=new ListView<String>();
		listViewImg2=new ListView<String>();
		if(list1!=null || list2!=null){
			listViewImg1=list1;
			listViewImg2=list2;
		}		
	}
	/*
	String PrintImagePixelArray(int[][] pixelArrayRGB,int width,int height){
		StringBuilder sbPixel=new StringBuilder();
		
		try{
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {            	
	            	sbPixel.append("("+pixelArrayRGB[readX][readY][0]+",");
	            	sbPixel.append(pixelArrayRGB[readX][readY][1]+",");
	            	sbPixel.append(pixelArrayRGB[readX][readY][2]+") ");
	            }
				sbPixel.append("\n");
			}
		}catch(Exception ex){
			throw ex;
		}
		return sbPixel.toString();
		
	}
	*/
	void CreateImageMatricesFromImage(int[][] pixelArrayRED,int[][] pixelArrayGREEN,
									  int[][] pixelArrayBLUE,BufferedImage inputImage){ 
		try{
			int pixelValue;
			Color pixelColor;
			int height=inputImage.getHeight();
			int width=inputImage.getWidth();
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {            	
	            	pixelValue = inputImage.getRGB(readX, readY);
	            	pixelColor=new Color(pixelValue);
	            	pixelArrayRED[readX][readY]=pixelColor.getRed();
	            	pixelArrayGREEN[readX][readY]=pixelColor.getGreen();
	            	pixelArrayBLUE[readX][readY]=pixelColor.getBlue();            	
				}
			}
		}catch(Exception ex){
			throw ex;
		}
	}//// End CreateImageMatricesFromImage
		
	//// Create Image From Image Matrices
	void CreateImageFromImageMatrices(BufferedImage bufferedImageFile, String fileName, 
						String fileDirectory,int[][] pixelsRED,int[][] pixelsGREEN,
						int[][] pixelsBLUE, int width,int height){
		try{
			int pixelValue,redPixel,greenPixel,bluePixel;       	
			
	        for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {
					redPixel = pixelsRED[readX][readY];
	            	greenPixel = pixelsGREEN[readX][readY];
	            	bluePixel = pixelsBLUE[readX][readY];
	            	
	            	pixelValue=(redPixel << 16 | greenPixel << 8 | bluePixel);            	
					bufferedImageFile.setRGB(readY, readX, pixelValue);					
				}
			}	        
	        //// Saving the image files
	        File file = new File(fileDirectory + "\\"+fileName);
	        try {
				ImageIO.write(bufferedImageFile, "PNG", file);
				updateMessage(fileName + " File Created Successfully");			
			} catch (IOException e) {
				updateMessage(fileName + " File Creation Error!!!");
				e.printStackTrace();
			}     
	        updateMessage("Interpolated Image Created Successfully.");
		}catch(Exception ex){
			throw ex;
		}
	}//// End CreateImageFromImageMatrices	
	
	String ConvertToNBitBinaryString(int n,int nBit){
		try{
			StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
			int padding = nBit - binaryStringBuilder.length();
			for(int i=0;i<padding;i++){
				binaryStringBuilder.insert(0, "0");
			}		
			return binaryStringBuilder.toString();
		}catch(Exception ex){
			throw ex;
		}
	}//// End ConvertToBinaryString
	
	String CreateBinaryStringFromImage(BufferedImage inputImage){			
		try{
			int imageWidth=inputImage.getWidth();
			int imageHeight=inputImage.getHeight();
			StringBuilder sbImageString=new StringBuilder();

			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){					
					Color c = new Color(inputImage.getRGB(x, y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					
					sbImageString.append(ConvertToNBitBinaryString(r,8));
					sbImageString.append(ConvertToNBitBinaryString(g,8));
					sbImageString.append(ConvertToNBitBinaryString(b,8));	
				}  
			}
			return sbImageString.toString();
		}catch(Exception ex){
			throw ex;
		}
	}//// End CreateBinaryStringFromImage
	
	void CreateImageFromBinaryString(String fileName, String fileDirectory, 
		 StringBuilder binaryString,int imageWidth, int imageHeight) throws Exception{

		try{
		
			int red=0,green=0,blue=0;
			int pixelColor=0;
			//// Create the empty output image file
			BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
			int substringCount=0;
			
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){														
					red=Integer.parseInt(binaryString.substring(substringCount, substringCount+8), 2);
					green=Integer.parseInt(binaryString.substring(substringCount+8, substringCount+16),2);
					blue=Integer.parseInt(binaryString.substring(substringCount+16, substringCount+24),2);
					//// Get the pixel color
					pixelColor=(red << 16) | (green << 8) | blue;
					outputImage.setRGB(x, y, pixelColor);
			
					substringCount+=24;								
				}
			}					
			//// Create the output image file and save it in a file
			File imageFile = new File(fileDirectory+"\\"+fileName);
			ImageIO.write(outputImage, "PNG", imageFile);
			updateMessage("Image Extracted Successfully");
		
		}catch(Exception ex){
			throw ex;
		}
	}//// End CreateImageFromBinaryString
	
	////This function rounds double value
	double round(double value, int places) {
		if (places < 0) 
			throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}//// End round

	void GetRSAnalysisOfAllFiles(){
		File file2=null;
		String filePath2="";
		BufferedImage bufferedImageChanged=null;
		int totalProgress=listViewImg1.getItems().size();
		int currentProgress=1;
		
		try{		
			for(int i=0;i<listViewImg1.getItems().size();i++){
				
				filePath2=(String)listViewImg2.getItems().get(i);
				file2=new File(filePath2);
				bufferedImageChanged = ImageIO.read(file2);				
				RSAnalysis(file2,bufferedImageChanged,0,1,1,0);
				
				updateProgress(currentProgress++,totalProgress);
			}
			updateMessage("RS Analysis Completed Successfully.");
		}catch(Exception ex){
			updateMessage("Error in GetRSAnalysisOfAllFiles:- "+ex.toString());
			return;
		}	
	}
	////Calculated the RS Analysis
	void RSAnalysis(File file,BufferedImage inputImage,int m1,int m2,int m3,int m4)
	{
		int row=inputImage.getHeight();
		int col=inputImage.getWidth();
		int sq=4;
		int i;
		double fx,fx1,fx_1;
		double RG1=0.0,SG1=0.0,UG1=0.0,RG_1=0.0,SG_1=0.0,UG_1=0.0;
		double Fblk[]=new double[4];
		double Fblk1[]=new double[4];
		double Fblk_1[]=new double[4];
		int M[]=new int[4];
		M[0]=m1;M[1]=m2;M[2]=m3;M[3]=m4;
		String result="";
		
		for(int p=0;p<row;p++){
			for(int q=0;q<(col/sq);q++){
			
				i=0;
		        for(int j=sq*q;j<sq*(q+1);j++){
		        	int pixelValue = inputImage.getRGB(j,p);
	            	Color pixelColor=new Color(pixelValue);
	            	Fblk[i] = pixelColor.getRed();
	            	System.out.println(Fblk[i]);
	               	i=i+1;
		        }  
		        
		        fx=0;
		        for(i=1;i<sq;i++){
		        	fx=fx + Math.abs(Fblk[i-1]-Fblk[i]);
		        }
		        
		        for(i=0;i<sq;i++){
		        	
		        	if(M[i]==1){
		        		
	                	Fblk_1[i]=Fblk[i]+1;
	                
		                if((Fblk[i]%2)==1){
		                    Fblk1[i]=Fblk[i]-1;
		                }else{
		                    Fblk1[i]=Fblk[i]+1;
		                }
		                
		                if((Fblk_1[i] % 2)==1){
		                    Fblk_1[i] = Fblk_1[i]-2;
		                }
		                
		        	}else{
	                	Fblk1[i]=Fblk[i];
	                	Fblk_1[i]=Fblk[i];
	                }
	                
		        }
		        
		        fx1=0;
		        fx_1=0;
		        for(i=1;i<sq;i++){
		            fx1=fx1 + Math.abs(Fblk1[i-1]-Fblk1[i]);
		            fx_1=fx_1 + Math.abs(Fblk_1[i-1]-Fblk_1[i]);
		        }
		        
		        if(fx1>fx){
	            	RG1=RG1+1;
		        }else{
		            if(fx1<fx){
		                SG1=SG1+1;
		            }else{
		                UG1=UG1+1;
		            }
		        }
		        
		        if(fx_1>fx){
	            	RG_1=RG_1+1;
		        }else{
		            if(fx_1<fx){
		                SG_1=SG_1+1;
		            }else{
		                UG_1=UG_1+1;
		            }
		        }		        
			}
		}
		
		double RSresult=Math.abs((Math.abs(RG1-RG_1)+Math.abs(SG1-SG_1))/(RG1+SG1));
		result="RSResult = "+round(RSresult,4);
		result+=", Rm = "+RG1+", Rm1 = "+RG_1;
		result+=", Sm = "+SG1+", Sm1 = "+SG_1;
		
		//// Save the result in a text file
		String strFileName = System.getProperty("user.home") + "\\Desktop\\RS_Analysis.txt";
		try(FileWriter fw = new FileWriter(strFileName, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw))
		{
			String fileName=PCUtility.GetFileNameWithoutExtension(file);
			pw.println(fileName + " -> " + result);		
		} catch (IOException e) {
			PCUtility.MessageBox("Error Saving RS Analysis File");
			return;
		}  		
		//// Display the message
		System.out.println(result); 
	}//// End RSAnalysis
   
	void GetSSIMOfAllFiles(){
		File file1=null;
		File file2=null;
		String filePath1="";
		String filePath2="";
		BufferedImage bufferedImageOriginal=null;
		BufferedImage bufferedImageChanged=null;
		int totalProgress=listViewImg1.getItems().size();
		int currentProgress=1;
		
		try{		
			for(int i=0;i<listViewImg1.getItems().size();i++){
				
				filePath1=(String)listViewImg1.getItems().get(i);
				filePath2=(String)listViewImg2.getItems().get(i);
				
				file1=new File(filePath1);
				file2=new File(filePath2);
				
				bufferedImageOriginal=null;
				bufferedImageChanged=null;
				
				bufferedImageOriginal = ImageIO.read(file1);
				bufferedImageChanged = ImageIO.read(file2);
			
				GetSSIM(file1, bufferedImageOriginal, bufferedImageChanged);
				updateProgress(currentProgress++,totalProgress);
			}
			updateMessage("SSIM Completed Successfully.");
		}catch(Exception ex){
			updateMessage("Error in GetSSIMOfAllFiles:- "+ex.toString());
			return;
		}	
		
	}
	
	void GetSSIM(File file, BufferedImage image1, BufferedImage image2){
		try{
			////////////////////////////////////////////////////////////////////
			if(image1==null || image2==null)
				return;
			////int x=image1.getWidth(), y=image1.getHeight();
			
			
			
			
			////////////////////////////////////////////////////////////////////
			//// Save the result in a text file
			//// String fileName=PCUtility.GetFileNameWithoutExtension(file);
			String strFileName = System.getProperty("user.home") + "\\Desktop\\SSIM_Result.txt";
			try(FileWriter fw = new FileWriter(strFileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw))
			{
				//pw.println(fileName + " -> " + round(mse,4)+ ", " + round(psnr,4));			
			} catch (IOException e) {
				PCUtility.MessageBox("Error Saving PSNR Output File");
				return;
			}  		
			//// Display the message
			//System.out.println(strSSIM);
		}catch(Exception ex){
			
		}
	}
	
	
	void GetPSNROfAllFiles(){
		File file1=null;
		File file2=null;
		String filePath1="";
		String filePath2="";
		BufferedImage bufferedImageOriginal=null;
		BufferedImage bufferedImageChanged=null;
		int totalProgress=listViewImg1.getItems().size();
		int currentProgress=1;
		
		try{		
			for(int i=0;i<listViewImg1.getItems().size();i++){
				
				filePath1=(String)listViewImg1.getItems().get(i);
				filePath2=(String)listViewImg2.getItems().get(i);
				
				file1=new File(filePath1);
				file2=new File(filePath2);
				
				bufferedImageOriginal=null;
				bufferedImageChanged=null;
				
				bufferedImageOriginal = ImageIO.read(file1);
				bufferedImageChanged = ImageIO.read(file2);
			
				GetPSNR(file1, bufferedImageOriginal, bufferedImageChanged);
				updateProgress(currentProgress++,totalProgress);
			}
			updateMessage("PSNR Completed Successfully.");
		}catch(Exception ex){
			updateMessage("Error in GetPSNROfAllFiles:- "+ex.toString());
			return;
		}	
		
	}
	
	////This function returns the PSNR value of two images
	void GetPSNR(File file, BufferedImage i1, BufferedImage i2)
	{	
		double mse,psnr;
		String strPSNR="";			
		try{
			// Image features
			int cc1 = i1.getColorModel().getNumComponents();
			double bpp1 = i1.getColorModel().getPixelSize()/cc1;
			int cc2 = i2.getColorModel().getNumComponents();
			double bpp2 = i2.getColorModel().getPixelSize()/cc2;
			int cc = 4;
			if( cc1 == 3 && cc2 == 3 ) {
				cc = 3;
			}
			// Set up the summations:
			KahanSummation tr = new KahanSummation();
			KahanSummation tg = new KahanSummation();
			KahanSummation tb = new KahanSummation();
			KahanSummation ta = new KahanSummation();
			BigInteger br = BigInteger.valueOf(0);
			BigInteger bg = BigInteger.valueOf(0);
			BigInteger bb = BigInteger.valueOf(0);
			BigInteger ba = BigInteger.valueOf(0);
			
			for (int i = 0; i < i1.getWidth(); i++) {
				for (int j = 0; j < i1.getHeight(); j++) {
					final Color c1 = new Color(i1.getRGB(i, j));
					final Color c2 = new Color(i2.getRGB(i, j));
					final int dr = c1.getRed() - c2.getRed();
					final int dg = c1.getGreen() - c2.getGreen();
					final int db = c1.getBlue() - c2.getBlue();
					final int da = c1.getAlpha() - c2.getAlpha();
					tr.add( dr*dr );
					tg.add( dg*dg );
					tb.add( db*db );
					ta.add( da*da );
					br = br.add( BigInteger.valueOf(dr*dr));
					bg = bg.add( BigInteger.valueOf(dg*dg));
					bb = bb.add( BigInteger.valueOf(db*db));
					ba = ba.add( BigInteger.valueOf(da*da));
				} 
			}
			// Compute the Mean Square Error(MSE):
			mse = (tr.getSum() + tg.getSum() + tb.getSum() + ta.getSum()) / (i1.getWidth() * i1.getHeight() * cc);
			
			if (mse == 0) {
				//// Set PSNR to Infinity
				psnr=99999.0;
			}else{
			
				BigDecimal bmse = new BigDecimal("0.00");
				bmse = bmse.add(new BigDecimal(br));
				bmse = bmse.add(new BigDecimal(bg));
				bmse = bmse.add(new BigDecimal(bb));
				bmse = bmse.add(new BigDecimal(ba));
				bmse = new BigDecimal( bmse.doubleValue() / (i1.getWidth() * i1.getHeight() * cc) );
				
				System.out.println("bmse = "+bmse);
				//mse = bmse.doubleValue();
				// Get the bits per pixel:
				
				if( bpp1 != bpp2 ) {
					//log.warning("Bits-per-pixel do not match up! bpp1 = "+bpp1+", bpp2 = "+bpp2);
					PCUtility.MessageBox("Bits-per-pixel do not match up! bpp1 = "+bpp1+", bpp2 = "+bpp2);
				}
				
				double bpp = bpp1;
				
				System.out.println("read bpp = "+bpp);
				System.out.println("colcomp = "+cc);
				
				bpp = 8;
				// The maximum is therefore:
				double max = Math.pow(2.0, bpp) - 1.0;
				// Compute the peak signal to noise ratio:
				psnr = 10.0 * StrictMath.log10( max*max / mse );
				
				strPSNR="";
				strPSNR += "MSE = " + mse + "\n" +
				"Peak signal to noise ratio (PSNR): " + psnr + "\n" +
				"Peak signal to noise ratio (BigDecimal): " + 10.0 * StrictMath.log10(max*max/bmse.doubleValue());
			}
			
			//// Save the result in a text file
			String strFileName = System.getProperty("user.home") + "\\Desktop\\PSNR_Result.txt";
			try(FileWriter fw = new FileWriter(strFileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw))
			{
				String fileName=PCUtility.GetFileNameWithoutExtension(file);
				if(psnr>=99999){
					pw.println(fileName + " -> " + "(MSE) 0" + ", " + "(PSNR) Infinity");
					System.out.println(fileName + " -> " + "(MSE) 0" + ", " + "(PSNR) Infinity");
				}else{
					pw.println(fileName + " -> (MSE) " + round(mse,4)+ ", (PSNR)" + round(psnr,4));
					System.out.println(fileName + " -> (MSE) " + round(mse,4)+ ", (PSNR)" + round(psnr,4));
				}
			
			} catch (IOException e) {
				PCUtility.MessageBox("Error Saving PSNR Output File");
				return;
			}  		
			//// Display the message
			System.out.println(strPSNR);	
			
		}catch(Exception ex){
			return;
		}		
			
	}//// End GetPSNR

	void GetSDAndCoCOfImageOfAllFiles(){
		File file1=null;
		File file2=null;
		String filePath1="";
		String filePath2="";
		BufferedImage bufferedImageOriginal=null;
		BufferedImage bufferedImageChanged=null;
		int totalProgress=listViewImg1.getItems().size();
		int currentProgress=1;
		
		try{		
			for(int i=0;i<listViewImg1.getItems().size();i++){
				
				filePath1=(String)listViewImg1.getItems().get(i);
				filePath2=(String)listViewImg2.getItems().get(i);
				
				file1=new File(filePath1);
				file2=new File(filePath2);
				
				bufferedImageOriginal=null;
				bufferedImageChanged=null;
				
				bufferedImageOriginal = ImageIO.read(file1);
				bufferedImageChanged = ImageIO.read(file2);
			
				GetSDAndCoCOfImage(file1, bufferedImageOriginal, bufferedImageChanged);
	          
				updateProgress(currentProgress++,totalProgress);
			}
			updateMessage("SD And CC Completed Successfully.");
		}catch(Exception ex){
			updateMessage("Error in GetSDAndCoCOfImageOfAllFiles:- "+ex.toString());
			return;
		}
	}
	
	////Calculate Standard Deviation and correlation coefficient of an image  
	void GetSDAndCoCOfImage(File file, BufferedImage imageOriginal, BufferedImage imageChanged){
   
		double sdOriginal=0.0;   
    	double sdChanged=0.0;    	
    	double cc=0.0;
    	String strSD="";
    	
    	try{
    		
    		int widthOriginal=0;
        	int heightOriginal=0;
        	int widthChanged=0;
        	int heightChanged=0;
        	int numberOfPixels=0;;
        	        	
        	double sum1=0.0;
        	double sum2=0.0;
            double mean1=0.0;
            double mean2=0.0;
            double variance=0.0;
            double squareRoot = 0.0;
         
            int pixelRed;
            int pixelGreen;
            int pixelBlue;
            int pixelValue=0;
        	
        	//// Calculating SD of original image
        	if(imageOriginal != null){
        		widthOriginal=imageOriginal.getWidth();
            	heightOriginal=imageOriginal.getHeight();
        		sum1=0.0;
            	variance=0.0;
            	numberOfPixels=widthOriginal*heightOriginal;
                for (int y = 0; y < heightOriginal; y++){
                    for (int x = 0; x < widthOriginal; x++){
                    	pixelValue=imageOriginal.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum1+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean1=sum1/(numberOfPixels);
                
                sum1=0;
                for (int y = 0; y < heightOriginal; y++){
                     for (int x = 0; x < widthOriginal; x++){
                    	               	 
                    	 pixelValue=imageOriginal.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum1=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum1 - mean1, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdOriginal = Math.sqrt(squareRoot); 
        	}        	
                    	
            ///// Calculating SD of changed image
        	if(imageChanged != null){
        		widthChanged=imageChanged.getWidth();
            	heightChanged=imageChanged.getHeight();
        		sum2=0.0;
                variance=0.0;
                numberOfPixels=widthChanged*heightChanged;
                for (int y = 0; y < heightChanged; y++){
                    for (int x = 0; x < widthChanged; x++){
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;
                    	
                    	sum2+=(pixelRed+pixelGreen+pixelBlue);
                    }
                }            
                mean2=sum2/(numberOfPixels);
                
                sum2=0.0;
                for (int y = 0; y < heightChanged; y++){
                     for (int x = 0; x < widthChanged; x++){
                    	               	 
                    	 pixelValue=imageChanged.getRGB(x, y);
                     	 pixelRed=(pixelValue >> 16) & 0xFF;
                     	 pixelGreen=(pixelValue >> 8) & 0xFF;
                     	 pixelBlue=(pixelValue >> 0) & 0xFF;
                     	
                     	 sum2=(pixelRed+pixelGreen+pixelBlue);                 	
                    	 variance += Math.pow(sum2 - mean2, 2); 
                     }                   
                }                
                squareRoot = variance / (numberOfPixels);            
                sdChanged = Math.sqrt(squareRoot); 
        	}
        	
        	///////////    Calculating correlation coefficient    /////////////////
        	if(imageOriginal != null && imageChanged != null){
        		double sumTotal=0.0;
            	numberOfPixels=heightOriginal*widthOriginal;
            	sum1=0.0;
            	sum2=0.0;
            	for (int y = 0; y < heightOriginal; y++){
                    for (int x = 0; x < widthOriginal; x++){
               	
                    	pixelValue=imageOriginal.getRGB(x, y);  
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum1=(pixelRed+pixelGreen+pixelBlue);   
                    	 
                    	pixelValue=imageChanged.getRGB(x, y);
                    	pixelRed=(pixelValue >> 16) & 0xFF;
                    	pixelGreen=(pixelValue >> 8) & 0xFF;
                    	pixelBlue=(pixelValue >> 0) & 0xFF;                	
                    	sum2=(pixelRed+pixelGreen+pixelBlue);                	
                    	
                    	sumTotal+=((sum1-mean1)*(sum2-mean2));                	
                    }
            	}
            	
            	cc=(sumTotal/(numberOfPixels*sdOriginal*sdChanged));  
        	}        	
        	 
        	//// Display the SD of the image
            strSD="SD (Left) = "+round(sdOriginal,4)+
            	  ", SD (Right) = "+round(sdChanged,4)+  
        	      ", CC = "+round(cc,4);
            
    	}catch(Exception ex){
    		return;
    	}  
    	
    	//// Save the result in a text file
		String strFileName = System.getProperty("user.home") + "\\Desktop\\SDCC_Result.txt";
		try(FileWriter fw = new FileWriter(strFileName, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw))
		{
			String fileName=PCUtility.GetFileNameWithoutExtension(file);
			pw.println(fileName + " -> " + strSD);			
		} catch (IOException e) {
			PCUtility.MessageBox("Error Saving SDCC_Result.txt File");
			return;
		}  		
		//// Display the message
		System.out.println(strSD);			
   	        
    }//// End GetSDAndCoCOfImage
   
	protected Void call() throws Exception {
		if(strOperation.equalsIgnoreCase("SDCC")){
			GetSDAndCoCOfImageOfAllFiles();
			Thread.currentThread().interrupt();
		}else if(strOperation.equalsIgnoreCase("PSNR")){
			GetPSNROfAllFiles();
			Thread.currentThread().interrupt();
		}else if(strOperation.equalsIgnoreCase("RSAnalysis")){
			GetRSAnalysisOfAllFiles();
			Thread.currentThread().interrupt();
		}else if(strOperation.equalsIgnoreCase("SSIM")){
			GetSSIMOfAllFiles();
			Thread.currentThread().interrupt();
		}
		
		return null;
	}
	
}
//// End ImageUtility class

class PVD5 extends Task<Void>
{
	String steganoType;
	MyImage coverImage;
	MyImage secretImage;
	MyImage stegoImage;
	MyImage interpolatedImage;
	StringBuilder sbSecretImageDataBits;
	StringBuilder sbExtractedDataBits;
	int secretBitsConsumed;
	int widthCoverImage;
	int heightCoverImage;
	int totalProgress;
	int progressIndicator;
	int secretImageWidth;
	int secretImageHeight;
	StringBuilder sbLog;
	String fileDirectory;
	ImageUtility imageUtil;
	int totalBitsEmbedded;
	
	PVD5(BufferedImage coverFile,BufferedImage secretFile,BufferedImage stegoFile,
		  String filePath,String type,int width,int height){
		
		imageUtil=new ImageUtility("",null,null);
		coverImage=new MyImage(coverFile);
		secretImage=new MyImage(secretFile);
		steganoType=type;
		sbLog=new StringBuilder();
		sbSecretImageDataBits=new StringBuilder();
		sbExtractedDataBits=new StringBuilder();
		secretBitsConsumed=0;
		secretImageWidth=width;
		secretImageHeight=height;
		
		if(steganoType.equalsIgnoreCase("embed")){
			widthCoverImage=coverImage.getWidth();
			heightCoverImage=coverImage.getHeight();
			stegoImage = new MyImage(widthCoverImage+(widthCoverImage/2), heightCoverImage+(heightCoverImage/2));
		}else if(steganoType.equalsIgnoreCase("extract")){
			stegoImage=new MyImage(stegoFile);
			widthCoverImage=stegoImage.getWidth();
			heightCoverImage=stegoImage.getHeight();			
		}
		
		fileDirectory=filePath;
		File fileCoverImage = new File(filePath);
		fileDirectory=fileCoverImage.getParent();
		totalBitsEmbedded=0;
	}
	
	void StartEmbeddingICCDC(){
		updateMessage("Embedding process started....");
		sbLog.append("\nStartEmbeddingICCDC......\n");
		int bitsInserted=0;
		try{
			sbLog.append("interpolatedImage start \n");
			//// Create the Interpolated file.
			interpolatedImage=coverImage.toInterpolatedImage((coverImage.getWidth()*3)/2, (coverImage.getHeight()*3)/2);
			//// Save the interpolated file
			interpolatedImage.saveAsFile("Interpolated.png", fileDirectory);
			//// Create stego image from interpolated file
			stegoImage=interpolatedImage.cloneImage();
			//// Extract binary data from secret image 
			sbSecretImageDataBits.append(secretImage.toBinaryString());
			//// Embed Secret Image in stego file using 5 PVD
			bitsInserted=stegoImage.embed5PVD(sbSecretImageDataBits);
			//// Save the stego file.
			stegoImage.saveAsFile("Stego.png", fileDirectory);

			sbLog.append("Secret Data Bits:-\n"+sbSecretImageDataBits);
			WriteLogICCDC(sbLog);			
			updateProgress(100, 100);
		}catch(Exception ex){
			updateMessage("Embedding Error!!! "+ex.toString());
			sbLog.append("\nStartEmbeddingICCDC:- "+ex.toString());
			WriteLogICCDC(sbLog); 
			return;
		}		
		updateMessage("Embedding Process Finished Successfully. "+
					  "Total bits inserted = "+bitsInserted+
					  ", Total pixels = "+(double)(bitsInserted/24));
	}
	
	//// Extracts data bits from stego image	
	void StartExtractionICCDC(){
		try{
			sbLog.append("\nStarting Extraction Process....\n");
			//// Extract the data bits from stego image
			sbLog.append("\nExtracting data bits....\n");
			sbExtractedDataBits.append(stegoImage.extractDataBits());
			sbLog.append("\nExtraction Completed....\n");
			
			//// Save the extracted image
			stegoImage=new MyImage(sbExtractedDataBits,secretImageWidth, secretImageHeight);
			stegoImage.saveAsFile("Secret.png", fileDirectory);
			
			//CreateImageFromBinaryStringICCDC("Secret.png",sbExtractedDataBits,secretImageWidth,secretImageHeight);
			//imageUtil.CreateImageFromBinaryString("Secret.png", fileDirectory, sbExtractedDataBits, secretImageWidth, secretImageHeight);
			
			//ExtractOriginalImageFromStegoImageICCDC();
			sbLog.append("\nExtracted Bits:- "+sbExtractedDataBits.toString());
			WriteLogICCDC(sbLog);
			updateMessage("Extraction process completed.");
			updateProgress(100,100);
		}catch(Exception ex){
			sbLog.append("\nStartExtractionICCDC:- "+ex.toString());
			updateMessage("Error in StartExtractionICCDC!!!"+ex.toString());
			WriteLogICCDC(sbLog);
			return;
		} 
		
	}//// End StartExtractionICCDC
	
	
	void ExtractOriginalImageFromStegoImageICCDC() throws Exception{ 
		/*
		try{
			int width=stegoImage.getWidth();
			int height=stegoImage.getHeight();
			int pixelColor,row,col;
			BufferedImage originalImage=new BufferedImage((width*2)/3,(height*2)/3, BufferedImage.TYPE_INT_RGB);
			
			row=0;
			for(int y=0;y<height;y+=3){
				col=0;
				for(int x=0;x<width;x+=3){		
					pixelColor=stegoImage.getRGB(x+0, y+0);				
					originalImage.setRGB(col+0, row+0, pixelColor);	
					pixelColor=stegoImage.getRGB(x+2, y+0);				
					originalImage.setRGB(col+1, row+0, pixelColor);	
					pixelColor=stegoImage.getRGB(x+0, y+2);				
					originalImage.setRGB(col+0, row+1, pixelColor);	
					pixelColor=stegoImage.getRGB(x+2, y+2);				
					originalImage.setRGB(col+1, row+1, pixelColor);	
					
					col+=2;
				}
				row+=2;
			}	
			//// Create the output image file and save it in a file
			File imageFile = new File(fileDirectory+"\\"+"Original.png");
			ImageIO.write(originalImage, "PNG", imageFile);
		}catch(Exception ex){
			updateProgress(100,100);
			updateMessage("Error in CreateImageFromBinaryStringICCDC:- "+ex.toString());
			sbLog.append("Error in CreateImageFromBinaryStringICCDC:- "+ex.toString());
			throw ex;
		}
		*/
	}//// End ExtractOriginalImageFromStegoImageICCDC		
	
	
	////Get data bits from secret image
	void GetDataBitsFromSecretImageICCDC(){		
		try{			
			//// Get the binary string from the secret image 
			//sbSecretImageDataBits.append(ConvertImageToBinaryStringICCDC(secretImage));
		}catch(Exception ex){
			sbLog.append("\nGetDataBitsFromSecretImageICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End GetDataBitsFromSecretImageICCDC

	////Get first N bits from secret data bits
	String GetFirstNBitsICCDC(int nBits){
		try{
			//// Strip first N bits from the string
			String strTemp = sbSecretImageDataBits.substring(secretBitsConsumed, secretBitsConsumed + nBits);
			secretBitsConsumed+=4;
			return strTemp;
		}catch(Exception ex){
			sbLog.append("\nGetFirstNBitsICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End getFirstNBits
	
	////Convert image to bit string
	String ConvertImageToBinaryStringICCDC(BufferedImage inputImage){
		try{
			int imageWidth=inputImage.getWidth();
			int imageHeight=inputImage.getHeight();
			StringBuilder sbImageString=new StringBuilder();
					
			updateMessage("Converting Image To Binary String...");
			int progressCount=0;
			int totalProgress=imageHeight*imageWidth;
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){					
					Color c = new Color(inputImage.getRGB(x, y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					
					sbImageString.append(ConvertToNBitBinaryStringICCDC(r,8));
					sbImageString.append(ConvertToNBitBinaryStringICCDC(g,8));
					sbImageString.append(ConvertToNBitBinaryStringICCDC(b,8));	
					
					updateProgress(progressCount++,totalProgress);
				}  
			}
			updateMessage("Image To Binary String Conversion Completed.");
			return sbImageString.toString();
		}catch(Exception ex){
			sbLog.append("\nConvertImageToBinaryStringICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End ConvertImageToBinaryString
	
	////Convert to binary string of 8 bits
	String ConvertToNBitBinaryStringICCDC(int n,int nBit){
		try{
			StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
			int padding = nBit - binaryStringBuilder.length();
			for(int i=0;i<padding;i++){
				binaryStringBuilder.insert(0, "0");
			}		
			return binaryStringBuilder.toString();
		}catch(Exception ex){
			sbLog.append("\nConvertToNBitBinaryStringICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End ConvertToBinaryString
	
	////Returns decimal value of a binary string
	int BinaryStringToDecimalICCDC(String strBinary){
		try{
			return Integer.parseInt(strBinary, 2);
		}catch(Exception ex){
			sbLog.append("\nBinaryStringToDecimalICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End binaryStringToDecimal
	
	int GetLowerBoundOfRangeICCDC(int d){
		try{
			//// Ranges are 0-15, 16-31, 32-47, 48-63,....., 240-255
			int lowerBound=-1;
			lowerBound=16*(d/16);
			return lowerBound;
		}catch(Exception ex){
			sbLog.append("\nGetLowerBoundOfRangeICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End getLowerBoundOfRange
	
	int GetUpperBoundOfRangeICCDC(int d){
		try{
			//// Ranges are 0-15, 16-31, 32-47, 48-63,....., 240-255
			int lowerBound=-1;
			int upperBound=-1;
			
			lowerBound=16*(d/16);
			upperBound=lowerBound+15;
			
			return upperBound;
		}catch(Exception ex){
			sbLog.append("\nGetLowerBoundOfRangeICCDC:- "+ex.toString());
			throw ex;
		}
	}//// End getLowerBoundOfRange
	
	////Displays MessageBox
	void MessageBox(String message){
		MessageBox(message,"ERROR");			
	}
	void MessageBox(String message,String type){
		Alert alert=null;
		if(type.equalsIgnoreCase("information")){
			alert = new Alert(AlertType.INFORMATION);
		}else{
			alert = new Alert(AlertType.ERROR);
		}		
		alert.setTitle("PVDE");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}//// End MessageBox
	
	////This function creates and writes the log.
	void WriteLogICCDC(StringBuilder strContent){		
		//try(FileWriter fw = new FileWriter("logFile.txt", true);
		try(FileWriter fw = new FileWriter(fileDirectory+"\\logFile.txt",true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw)){
			out.print(strContent);
		} catch (IOException e) {
		    MessageBox("Error Writing "+"logFile.txt");
		    return;
		}		
	}//// End WriteMatrixIndexFile
	
	//// executor.execute(ICCDCObject);
	@Override
	protected Void call() throws Exception {		
			
		if(steganoType.equalsIgnoreCase("embed")){
			StartEmbeddingICCDC();
			Thread.currentThread().interrupt();
		}else if(steganoType.equalsIgnoreCase("extract")){
			StartExtractionICCDC();
			Thread.currentThread().interrupt();
		}		
		return null;
	}  

}
//////////////////// END PVD5 Class ////////////////////////////////////////////


class PVD5Nxt extends Task<Void>
{
	String steganoType;
	String coverFile;
	String secretFile;
	String stegoFile;
	String interpolatedFile; 
	int[][][] coverArray;
	int[][][] secretArray;
	int[][][] stegoArray;
	int[][][] interpolatedArray;
	String fileDirectory;
	
	PVD5Nxt(String cvrFile,String scrtFile,String stgFile, String type){
		steganoType=type;	
		coverFile=cvrFile;
		secretFile=scrtFile;
		stegoFile=stgFile;
		fileDirectory=getParentDirectory();		
	}
	
	void StartEmbeddingPVD5Nxt(){		
		try{
			coverArray=imread(coverFile);
			//imwrite(coverArray,fileDirectory+"\\"+"Stego.png");
			interpolatedArray=toInterpolatedArrayPVD5Nxt(coverArray);
			imwrite(interpolatedArray,fileDirectory+"\\"+"Interpolated.png");
		}catch(Exception ex){
			
		}		
	}
	
	//// Extracts data bits from stego image	
	void StartExtractionPVD5Nxt(){
		try{
			
		}catch(Exception ex){
			
		} 
		
	}//// End StartExtractionICCDC
	
	String getParentDirectory(){
		File file=new File(coverFile);		
		return file.getParent(); 
	}
	
	int[][][] imread(String filePath){
		BufferedImage inputImage=null;
		try{
			File file=new File(filePath);
			inputImage = ImageIO.read(file);
		}catch(Exception ex){
			updateMessage("MyImage::MyImage :- "+ex.toString());
		}	
		
		int[][][] pixelArray=new int[inputImage.getWidth()][inputImage.getHeight()][3];
		int pixelValue;
		Color pixelColor;
		int height=inputImage.getHeight();
		int width=inputImage.getWidth();
		for (int readY = 0; readY < height; readY++) {
			for (int readX = 0; readX < width; readX++) {            	
				pixelValue = inputImage.getRGB(readX, readY);
				pixelColor=new Color(pixelValue);
				pixelArray[readX][readY][0]=pixelColor.getRed();
				pixelArray[readX][readY][1]=pixelColor.getGreen();
				pixelArray[readX][readY][2]=pixelColor.getBlue();            	
			}
		}
		return pixelArray;
	}//// End imread
	
	void imwrite(int[][][] pixelArray,String fileName){
		int width=pixelArray.length;
		int height=pixelArray[1].length;
		BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		try{
			int pixelValue,redPixel,greenPixel,bluePixel;			
			
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {
					redPixel = pixelArray[readX][readY][0];
			    	greenPixel = pixelArray[readX][readY][1];
			    	bluePixel = pixelArray[readX][readY][2];
			    	
			    	pixelValue=(redPixel << 16 | greenPixel << 8 | bluePixel);            	
			    	outputImage.setRGB(readX, readY, pixelValue);					
				}
			}	        
			//// Saving the image files
			File file = new File(fileName);
			try {
				ImageIO.write(outputImage, "PNG", file);
				updateMessage(fileName + " File Created Successfully");			
				} catch (IOException e) {
					updateMessage(fileName + " File Creation Error!!!");
					e.printStackTrace();
				}     
			updateMessage("Interpolated Image Created Successfully.");
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}//// End imwrite
	
	int[][][] toInterpolatedArrayPVD5Nxt(int[][][] originalPixelArray){
		
		int width=originalPixelArray.length;
		int height=originalPixelArray[1].length;		
		
		int[][][] interpolatedPixelArray;
		
		try{		
			interpolatedPixelArray=new int[(width*3)/2][(height*3)/2][3];
				
			int row,col;	
    		int totalProgress=(((width*3)/2)*((height*3)/2))/9;
			int progressIndicator=0;
			int pix1RED,pix1GREEN,pix1BLUE;
			int pix2RED,pix2GREEN,pix2BLUE;
			int pix3RED,pix3GREEN,pix3BLUE;
			int pix4RED,pix4GREEN,pix4BLUE;
			
			row=0;
			for (int readY = 0; readY < height; readY+=2) {
				col=0;
	            for (int readX = 0; readX < width; readX+=2) {            	
	            	//// Get the first pixel value
	            	pix1RED = originalPixelArray[readX][readY][0];
	            	pix1GREEN = originalPixelArray[readX][readY][1];
	            	pix1BLUE = originalPixelArray[readX][readY][2];
	            	
	            	pix2RED = originalPixelArray[readX+1][readY][0];
	            	pix2GREEN = originalPixelArray[readX+1][readY][1];
	            	pix2BLUE = originalPixelArray[readX+1][readY][2];
	            	
	            	pix3RED = originalPixelArray[readX][readY+1][0];
	            	pix3GREEN = originalPixelArray[readX][readY+1][1];
	            	pix3BLUE = originalPixelArray[readX][readY+1][2];
	            	
	            	pix4RED = originalPixelArray[readX+1][readY+1][0];
	            	pix4GREEN = originalPixelArray[readX+1][readY+1][1];
	            	pix4BLUE = originalPixelArray[readX+1][readY+1][2];	            	
	            	/*
	            	interpolatedPixelArray[row+0][col+0][0]=pix1RED;
	            	interpolatedPixelArray[row+0][col+0][1]=pix1GREEN;
	            	interpolatedPixelArray[row+0][col+0][2]=pix1BLUE;
	            	
	            	interpolatedPixelArray[row+0][col+2][0]=pix2RED;
	            	interpolatedPixelArray[row+0][col+2][1]=pix2GREEN;
	            	interpolatedPixelArray[row+0][col+2][2]=pix2BLUE;																				
	            	
	            	interpolatedPixelArray[row+2][col+0][0]=pix3RED;
	            	interpolatedPixelArray[row+2][col+0][1]=pix3GREEN;
	            	interpolatedPixelArray[row+2][col+0][2]=pix3BLUE;
	            	
	            	interpolatedPixelArray[row+2][col+2][0]=pix4RED;
	            	interpolatedPixelArray[row+2][col+2][1]=pix4GREEN;
	            	interpolatedPixelArray[row+2][col+2][2]=pix4BLUE;
	            	
	            	interpolatedPixelArray[row+0][col+1][0]=(pix1RED+pix2RED)/2;
	            	interpolatedPixelArray[row+0][col+1][1]=(pix1GREEN+pix2GREEN)/2;
	            	interpolatedPixelArray[row+0][col+1][2]=(pix1BLUE+pix2BLUE)/2;
	            	
	            	interpolatedPixelArray[row+1][col+0][0]=(pix1RED+pix3RED)/2;
	            	interpolatedPixelArray[row+1][col+0][1]=(pix1GREEN+pix3GREEN)/2;
	            	interpolatedPixelArray[row+1][col+0][2]=(pix1BLUE+pix3BLUE)/2;
	            	
	            	interpolatedPixelArray[row+1][col+2][0]=(pix2RED+pix4RED)/2;
	            	interpolatedPixelArray[row+1][col+2][1]=(pix2GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[row+1][col+2][2]=(pix2BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[row+2][col+1][0]=(pix3RED+pix4RED)/2;
	            	interpolatedPixelArray[row+2][col+1][1]=(pix3GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[row+2][col+1][2]=(pix3BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[row+1][col+1][0]=(pix1RED+pix2RED+pix3RED+pix4RED)/4;
	            	interpolatedPixelArray[row+1][col+1][1]=(pix1GREEN+pix2GREEN+pix3GREEN+pix4GREEN)/4;
	            	interpolatedPixelArray[row+1][col+1][2]=(pix1BLUE+pix2BLUE+pix3BLUE+pix4BLUE)/4;
	            	*/
	            	interpolatedPixelArray[col+0][row+0][0]=pix1RED;
	            	interpolatedPixelArray[col+0][row+0][1]=pix1GREEN;
	            	interpolatedPixelArray[col+0][row+0][2]=pix1BLUE;
	            	
	            	interpolatedPixelArray[col+2][row+0][0]=pix2RED;
	            	interpolatedPixelArray[col+2][row+0][1]=pix2GREEN;
	            	interpolatedPixelArray[col+2][row+0][2]=pix2BLUE;																				
	            	
	            	interpolatedPixelArray[col+0][row+2][0]=pix3RED;
	            	interpolatedPixelArray[col+0][row+2][1]=pix3GREEN;
	            	interpolatedPixelArray[col+0][row+2][2]=pix3BLUE;
	            	
	            	interpolatedPixelArray[col+2][row+2][0]=pix4RED;
	            	interpolatedPixelArray[col+2][row+2][1]=pix4GREEN;
	            	interpolatedPixelArray[col+2][row+2][2]=pix4BLUE;
	            	
	            	interpolatedPixelArray[col+1][row+0][0]=(pix1RED+pix2RED)/2;
	            	interpolatedPixelArray[col+1][row+0][1]=(pix1GREEN+pix2GREEN)/2;
	            	interpolatedPixelArray[col+1][row+0][2]=(pix1BLUE+pix2BLUE)/2;
	            	
	            	interpolatedPixelArray[col+0][row+1][0]=(pix1RED+pix3RED)/2;
	            	interpolatedPixelArray[col+0][row+1][1]=(pix1GREEN+pix3GREEN)/2;
	            	interpolatedPixelArray[col+0][row+1][2]=(pix1BLUE+pix3BLUE)/2;
	            	
	            	interpolatedPixelArray[col+2][row+1][0]=(pix2RED+pix4RED)/2;
	            	interpolatedPixelArray[col+2][row+1][1]=(pix2GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[col+2][row+1][2]=(pix2BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[col+1][row+2][0]=(pix3RED+pix4RED)/2;
	            	interpolatedPixelArray[col+1][row+2][1]=(pix3GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[col+1][row+2][2]=(pix3BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[col+1][row+1][0]=(pix1RED+pix2RED+pix3RED+pix4RED)/4;
	            	interpolatedPixelArray[col+1][row+1][1]=(pix1GREEN+pix2GREEN+pix3GREEN+pix4GREEN)/4;
	            	interpolatedPixelArray[col+1][row+1][2]=(pix1BLUE+pix2BLUE+pix3BLUE+pix4BLUE)/4;
	            	
	            	col+=3;
	            	updateProgress(progressIndicator++,totalProgress);
	            }
	            row+=3;
			}
			
			return interpolatedPixelArray;
			
		}catch(Exception ex){
			updateMessage("MyImage::toInterpolatedImage:- "+ex.toString());
			throw ex;
		}
	}
	
	//// executor.execute(ICCDCObject);
	@Override
	protected Void call() throws Exception {		
			
		if(steganoType.equalsIgnoreCase("embed")){
			StartEmbeddingPVD5Nxt();
			Thread.currentThread().interrupt();
		}else if(steganoType.equalsIgnoreCase("extract")){
			StartExtractionPVD5Nxt();
			Thread.currentThread().interrupt();
		}		
		return null;
	}  

}
//////////////////// END PVD5Nxt Class ////////////////////////////////////////////

//////////////////// START SubSample Class ////////////////////////
class SubSampleClass extends Task<Void>{
	String steganoType;
	String coverFile;
	String secretFile;
	String stegoFileList;
	String interpolatedFile; 
	int[][][] coverImageArray;
	int[][][] secretImageArray;
	int[][][] stegoImageArray;
	String fileDirectory;
	StringBuilder sbSecretBits;
	StringBuilder sbExtractedBits;
	StringBuilder sbLog;
	int secretImageWidth;
	int secretImageHeight;
	int secretBitsConsumed;
	int a0,a1,a2;
	int blockCount;
	
	SubSampleClass(String cvrFile,String scrtFile,String stgFile, 
			       String type, int scrtWidth, int scrtHeight){
		steganoType=type;	
		coverFile=cvrFile;
		secretFile=scrtFile;
		stegoFileList=stgFile;
		secretImageWidth=scrtWidth;
		secretImageHeight=scrtHeight;
		File file=new File(coverFile);	
		fileDirectory=file.getParent(); 
		sbSecretBits=new StringBuilder();
		sbExtractedBits=new StringBuilder();
		sbLog=new StringBuilder();
		secretBitsConsumed=0;
		a1=5; a2=4; a0=0;
		blockCount=0;
	}
	
	void StartEmbeddingSubSample(){		
		try{
			coverImageArray=imread(coverFile);
			
			int[][][] subSampleArray1,subSampleArray2,subSampleArray3,subSampleArray4;
			int[][][] interpolatedArray1,interpolatedArray2,interpolatedArray3,interpolatedArray4;			
			//// Step 1: Create sub sampled images from the cover image and then 
			//// create interpolated images from those sub sampled images.
			subSampleArray1=toSubSampleArray(coverImageArray,4,4,2,2,0,2,8,10);
			interpolatedArray1=toInterpolatedArray(subSampleArray1,7);
			imwrite(subSampleArray1,fileDirectory+"\\"+"SubSampled1.png");
			imwrite(interpolatedArray1,fileDirectory+"\\"+"Interpolated1.png");
			
			subSampleArray2=toSubSampleArray(coverImageArray,4,4,2,2,1,3,9,11);
			interpolatedArray2=toInterpolatedArray(subSampleArray2,7);
			imwrite(subSampleArray2,fileDirectory+"\\"+"SubSampled2.png");
			imwrite(interpolatedArray2,fileDirectory+"\\"+"Interpolated2.png");
			
			subSampleArray3=toSubSampleArray(coverImageArray,4,4,2,2,4,6,12,14);
			interpolatedArray3=toInterpolatedArray(subSampleArray3,7);
			imwrite(subSampleArray3,fileDirectory+"\\"+"SubSampled3.png");
			imwrite(interpolatedArray3,fileDirectory+"\\"+"Interpolated3.png");
			
			subSampleArray4=toSubSampleArray(coverImageArray,4,4,2,2,5,7,13,15);
			interpolatedArray4=toInterpolatedArray(subSampleArray4,7);
			imwrite(subSampleArray4,fileDirectory+"\\"+"SubSampled4.png");
			imwrite(interpolatedArray4,fileDirectory+"\\"+"Interpolated4.png");	
			
			//// Step 2: Extract bits from secret color image.
			secretImageArray=imread(secretFile);
			sbSecretBits.append(toBinaryStringFromImageArray(secretImageArray));
			
			///////////////////////////////////////////////////////
			//// Step 3: get result from equation for 1,2,3,4
			int imageWidth=interpolatedArray1.length;
			imageWidth=imageWidth-(imageWidth%3);
			int imageHeight=interpolatedArray1[1].length;
			imageHeight=imageHeight-(imageHeight%3);
			int result=0;					
			String number;
			int n;
			
			outerloop:
			for(int y=0;y<imageHeight;y+=3){
				for(int x=0;x<imageWidth;x+=3){

					++blockCount;
					sbLog.append("\n-------- RED Block "+ blockCount + " ---------\n");
					
					/////////  FOR RED BLOCK    //////////////////////////
					number=getFirstNBitsSubSample(4);
					if(number.equalsIgnoreCase("STOP")){
						System.out.println("RED: Input data bits finished!");
						break outerloop;
					}
					
					n=toDecimalFromBinary(number);						
					//// Process 1st interpolated image
					result=getResultFromEquationFor(1,n);					
					embedDataBitsInImageBlock(interpolatedArray1,0,1,x,y,result);
					//// Process 2nd interpolated image
					result=getResultFromEquationFor(2,n);					
					embedDataBitsInImageBlock(interpolatedArray2,0,2,x,y,result);
					//// Process 3rd interpolated image
					result=getResultFromEquationFor(3,n);					
					embedDataBitsInImageBlock(interpolatedArray3,0,3,x,y,result);
					//// Process 4th interpolated image
					result=getResultFromEquationFor(4,n);					
					embedDataBitsInImageBlock(interpolatedArray4,0,4,x,y,result);					
					
					sbLog.append("\n-------- GREEN Block "+ blockCount + " ---------\n");
					/////////  FOR GREEN BLOCK    //////////////////////////
					number=getFirstNBitsSubSample(4);
					if(number.equalsIgnoreCase("STOP")){
						System.out.println("GREEN: Input data bits finished!");
						break outerloop;
					}
					
					n=toDecimalFromBinary(number);						
					//// Process 1st interpolated image
					result=getResultFromEquationFor(1,n);					
					embedDataBitsInImageBlock(interpolatedArray1,1,1,x,y,result);
					//// Process 2nd interpolated image
					result=getResultFromEquationFor(2,n);					
					embedDataBitsInImageBlock(interpolatedArray2,1,2,x,y,result);
					//// Process 3rd interpolated image
					result=getResultFromEquationFor(3,n);					
					embedDataBitsInImageBlock(interpolatedArray3,1,3,x,y,result);
					//// Process 4th interpolated image
					result=getResultFromEquationFor(4,n);					
					embedDataBitsInImageBlock(interpolatedArray4,1,4,x,y,result);	
								
					sbLog.append("\n-------- BLUE Block "+ blockCount + " ---------\n");
					/////////  FOR BLUE BLOCK    //////////////////////////
					number=getFirstNBitsSubSample(4);
					if(number.equalsIgnoreCase("STOP")){
						System.out.println("BLUE: Input data bits finished!");
						break outerloop;
					}
					
					n=toDecimalFromBinary(number);						
					//// Process 1st interpolated image
					result=getResultFromEquationFor(1,n);					
					embedDataBitsInImageBlock(interpolatedArray1,2,1,x,y,result);
					//// Process 2nd interpolated image
					result=getResultFromEquationFor(2,n);					
					embedDataBitsInImageBlock(interpolatedArray2,2,2,x,y,result);
					//// Process 3rd interpolated image
					result=getResultFromEquationFor(3,n);					
					embedDataBitsInImageBlock(interpolatedArray3,2,3,x,y,result);
					//// Process 4th interpolated image
					result=getResultFromEquationFor(4,n);					
					embedDataBitsInImageBlock(interpolatedArray4,2,4,x,y,result);
								
				}  
			}
			
			///////////////////////////////////////////////////////
			imwrite(interpolatedArray1,fileDirectory+"\\"+"Embedded1.png");
			imwrite(interpolatedArray2,fileDirectory+"\\"+"Embedded2.png");
			imwrite(interpolatedArray3,fileDirectory+"\\"+"Embedded3.png");
			imwrite(interpolatedArray4,fileDirectory+"\\"+"Embedded4.png");
				
			sbLog.append("\n"+sbSecretBits.toString()+"\n");
			
			WriteLogFileSubSample("EmbedLOG.txt",sbLog);
			
			updateMessage("Embedding Process Completed Successfully."+
			              " Number of bits embedded = "+secretBitsConsumed);
			updateProgress(100,100);
		}catch(Exception ex){
			updateMessage("SubSample::StartEmbeddingSubSample:- "+ex.toString());
			ex.printStackTrace();
			throw ex;
		}		
	}
	
	void embedDataBitsInImageBlock(int[][][] interpolatedArray, int color, int xVal, int posX,int posY,int result){
		
		try{
			String strNumber=toNBitBinaryString(result, 12);		
			int first3bits=toDecimalFromBinary(strNumber.substring(0, 3));
			int second3bits=toDecimalFromBinary(strNumber.substring(3, 6));
			int third3bits=toDecimalFromBinary(strNumber.substring(6, 9));
			int fourth3bits=toDecimalFromBinary(strNumber.substring(9, 12));			
			
			interpolatedArray[posX+1][posY+0][color]+=first3bits;
			interpolatedArray[posX+0][posY+1][color]+=second3bits;
			interpolatedArray[posX+2][posY+1][color]+=third3bits;
			interpolatedArray[posX+1][posY+2][color]+=fourth3bits;
			
			interpolatedArray[posX+1][posY+1][color]+=xVal;	
			
			sbLog.append("X= "+xVal+", F("+xVal+") = "+result+", ("+first3bits+","+
					 second3bits+","+third3bits+","+fourth3bits+")"+"\n");
		
		}catch(Exception ex){
			updateMessage("SubSample::embedDataBitsInImageBlock:- "+ex.toString());
			ex.printStackTrace();
			throw ex;
		}
	}//// End embedDataBitsInImageBlock
	
	//// Extracts data bits from stego image	
	void StartExtractionSubSample(){
		
		try{
			int[][][] stegoArray1=null;
			int[][][] stegoArray2=null;
			int[][][] stegoArray3=null;
			int[][][] stegoArray4=null;			
			blockCount=0;
			List<String> fileList = Arrays.asList(stegoFileList.split(";"));
			int stegoFileCount=fileList.size();
			int count=0;			
			
			if(stegoFileCount<3){
				return;
			}else{
				for(String fileName:fileList){
					if(count==0){
						stegoArray1=imread(fileName);
						count++;
					}else if(count==1){
						stegoArray2=imread(fileName);
						count++;
					}else if(count==2){
						stegoArray3=imread(fileName);
						count++;
					}else{
						stegoArray4=imread(fileName);
						count++;
					}
				}			
			}
			
			imwrite(stegoArray1,fileDirectory+"\\"+"stego1.png");
			imwrite(stegoArray2,fileDirectory+"\\"+"stego2.png");
			imwrite(stegoArray3,fileDirectory+"\\"+"stego3.png");
			if(stegoFileCount>=4){
				imwrite(stegoArray4,fileDirectory+"\\"+"stego4.png");
			}
			
			//// TODO complete the list
			int imageWidth=stegoArray1.length;
			imageWidth=imageWidth-(imageWidth%3);
			int imageHeight=stegoArray1[1].length;
			imageHeight=imageHeight-(imageHeight%3);
			StringBuilder sbNumber=new StringBuilder();
			int x1,x2,x3;
			int fx1,fx2,fx3;
			int extractedNumber=0;
			int totalProgress=(imageHeight/3)*(imageWidth/3);
			int currentProgress=0;
			
			int totalOutputBits=(secretImageWidth*secretImageHeight)*24;
			
			outerloop:
			for(int y=0;y<imageHeight;y+=3){
				for(int x=0;x<imageWidth;x+=3){
	
					if(sbExtractedBits.length() == totalOutputBits){
						break outerloop;
					}
					
					++blockCount;
					sbLog.append("\n-------- RED Block "+ blockCount + " ---------\n");
					////System.out.println("\n-------- RED Block "+ blockCount + " ---------\n");
					/////////  FOR RED BLOCK    //////////////////////////
					x1=extractDataBitsFromImageBlock(stegoArray1,0,x,y,sbNumber);
					fx1=toDecimalFromBinary(sbNumber.toString());
					x2=extractDataBitsFromImageBlock(stegoArray2,0,x,y,sbNumber);
					fx2=toDecimalFromBinary(sbNumber.toString());
					x3=extractDataBitsFromImageBlock(stegoArray3,0,x,y,sbNumber);
					fx3=toDecimalFromBinary(sbNumber.toString());
					
					extractedNumber=getValueFromLagrangeInterpolation(x1, fx1, x2, fx2, x3, fx3);
					sbExtractedBits.append(toNBitBinaryString(extractedNumber, 4));
					
					/////////  FOR GREEN BLOCK    //////////////////////////
					sbLog.append("\n-------- GREEN Block "+ blockCount + " ---------\n");
					////System.out.println("\n-------- GREEN Block "+ blockCount + " ---------\n");
					x1=extractDataBitsFromImageBlock(stegoArray1,1,x,y,sbNumber);
					fx1=toDecimalFromBinary(sbNumber.toString());
					x2=extractDataBitsFromImageBlock(stegoArray2,1,x,y,sbNumber);
					fx2=toDecimalFromBinary(sbNumber.toString());
					x3=extractDataBitsFromImageBlock(stegoArray3,1,x,y,sbNumber);
					fx3=toDecimalFromBinary(sbNumber.toString());
					
					extractedNumber=getValueFromLagrangeInterpolation(x1, fx1, x2, fx2, x3, fx3);
					sbExtractedBits.append(toNBitBinaryString(extractedNumber, 4));
					
					
					/////////  FOR BLUE BLOCK    //////////////////////////
					sbLog.append("\n-------- BLUE Block "+ blockCount + " ---------\n");
					////System.out.println("\n-------- BLUE Block "+ blockCount + " ---------\n");
					x1=extractDataBitsFromImageBlock(stegoArray1,2,x,y,sbNumber);
					fx1=toDecimalFromBinary(sbNumber.toString());
					x2=extractDataBitsFromImageBlock(stegoArray2,2,x,y,sbNumber);
					fx2=toDecimalFromBinary(sbNumber.toString());
					x3=extractDataBitsFromImageBlock(stegoArray3,2,x,y,sbNumber);
					fx3=toDecimalFromBinary(sbNumber.toString());
					
					extractedNumber=getValueFromLagrangeInterpolation(x1, fx1, x2, fx2, x3, fx3);
					sbExtractedBits.append(toNBitBinaryString(extractedNumber, 4));	
					
					updateProgress(currentProgress++, totalProgress);
					updateMessage("Extracting data bits from pixel block.......");
				} 				
			}
			
			sbLog.append("\n"+sbExtractedBits.toString()+"\n");
			
			System.out.println("Number of bits extracted = "+sbExtractedBits.length());
			
			//// Create secret image from extracted binary string
			toImageFromBinaryStringSubSample("SecretExtracted.png", fileDirectory, 
						sbExtractedBits,secretImageWidth, secretImageHeight);

			//// Write the log file
			WriteLogFileSubSample("ExtractLOG.txt",sbLog);
			
			updateProgress(100,100);
			updateMessage("Extraction Process Completed Successfully.");			
		}catch(Exception ex){
			updateMessage("SubSample::StartExtractionSubSample:- "+ex.toString());
			ex.printStackTrace();
			throw ex;
		}
		
	}//// End StartExtractionICCDC
		
	int extractDataBitsFromImageBlock(int[][][] stegoArray, int color, int posX,int posY,StringBuilder sbNumber){
		try{
			int firstNumber=0,secondNumber=0,thirdNumber=0,fourthNumber=0;
			int xVal=0;
			sbNumber.setLength(0);
				
			//////////////////////////////////////////////
			if(posX==309 && posY==0){
				System.out.println(stegoArray[posX+0][posY+0][color]+", "+stegoArray[posX+1][posY+0][color]+", "+stegoArray[posX+2][posY+0][color]+"\n");
				System.out.println(stegoArray[posX+0][posY+1][color]+", "+stegoArray[posX+1][posY+1][color]+", "+stegoArray[posX+2][posY+1][color]+"\n");
				System.out.println(stegoArray[posX+0][posY+2][color]+", "+stegoArray[posX+1][posY+2][color]+", "+stegoArray[posX+2][posY+2][color]+"\n");
			}			
			//////////////////////////////////////////////
			
			firstNumber =stegoArray[posX+1][posY+0][color] -(int)((stegoArray[posX+0][posY+0][color] + stegoArray[posX+2][posY+0][color])/2);
			secondNumber=stegoArray[posX+0][posY+1][color] -(int)((stegoArray[posX+0][posY+0][color] + stegoArray[posX+0][posY+2][color])/2);
			thirdNumber =stegoArray[posX+2][posY+1][color] -(int)((stegoArray[posX+2][posY+0][color] + stegoArray[posX+2][posY+2][color])/2);
			fourthNumber=stegoArray[posX+1][posY+2][color] -(int)((stegoArray[posX+0][posY+2][color] + stegoArray[posX+2][posY+2][color])/2);
						
			xVal = stegoArray[posX+1][posY+1][color] -(int)((stegoArray[posX+0][posY+0][color]+
					                                    stegoArray[posX+2][posY+0][color]+
					                                    stegoArray[posX+0][posY+2][color]+
					                                    stegoArray[posX+2][posY+2][color])/4);
					
			////System.out.println("x = "+posX+", y = "+posY+", ("+firstNumber+", "+secondNumber+", "+thirdNumber+", "+fourthNumber+")" +"\n");
			
			sbNumber.append(toNBitBinaryString(firstNumber, 3));
			sbNumber.append(toNBitBinaryString(secondNumber, 3));
			sbNumber.append(toNBitBinaryString(thirdNumber, 3));
			sbNumber.append(toNBitBinaryString(fourthNumber, 3));
			
			sbLog.append("X = "+xVal+", F("+xVal+") = "+toDecimalFromBinary(sbNumber.toString())+", "+
					"("+firstNumber+", "+secondNumber+", "+thirdNumber+", "+fourthNumber+")" +"\n");
			
			////System.out.println("X = "+xVal+", F("+xVal+") = "+toDecimalFromBinary(sbNumber.toString())+", ");
			////System.out.println("("+firstNumber+", "+secondNumber+", "+thirdNumber+", "+fourthNumber+")" +"\n");
						
			return xVal;		
			
		}catch(Exception ex){
			updateMessage("SubSample::extractDataBitsFromImageBlock:- "+ex.toString());
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
	int[][][] toSubSampleArray(int[][][] originalPixelArray,
			                   int blockWidth,int blockHeight, 
			                   int subSampleWidth, int subSampleHeight, 
			                   int pos1,int pos2,int pos3,int pos4){
		
		int width=originalPixelArray.length;
		int height=originalPixelArray[1].length;		
		
		int[][][] subsamplePixelArray;
		
		try{		
			subsamplePixelArray=new int[width/2][height/2][3];
			
			int row,col;	
    		int totalProgress=(width/4)*(height/4);
			int progressIndicator=0;
			int pix1RED,pix1GREEN,pix1BLUE;
			int pix2RED,pix2GREEN,pix2BLUE;
			int pix3RED,pix3GREEN,pix3BLUE;
			int pix4RED,pix4GREEN,pix4BLUE;
			int x1=pos1/4, y1=pos1%4;
			int x2=pos2/4, y2=pos2%4;
			int x3=pos3/4, y3=pos3%4;
			int x4=pos4/4, y4=pos4%4;
			 
			row=0;
			for (int readY = 0; readY < height; readY+=blockHeight) {
				col=0;
	            for (int readX = 0; readX < width; readX+=blockWidth) {            	
	            	//// Get the first pixel value
	            	pix1RED = originalPixelArray[readX+x1][readY+y1][0];
	            	pix1GREEN = originalPixelArray[readX+x1][readY+y1][1];
	            	pix1BLUE = originalPixelArray[readX+x1][readY+y1][2];
	            	
	            	pix2RED = originalPixelArray[readX+x2][readY+y2][0];
	            	pix2GREEN = originalPixelArray[readX+x2][readY+y2][1];
	            	pix2BLUE = originalPixelArray[readX+x2][readY+y2][2];
	            	
	            	pix3RED = originalPixelArray[readX+x3][readY+y3][0];
	            	pix3GREEN = originalPixelArray[readX+x3][readY+y3][1];
	            	pix3BLUE = originalPixelArray[readX+x3][readY+y3][2];
	            	
	            	pix4RED = originalPixelArray[readX+x4][readY+y4][0];
	            	pix4GREEN = originalPixelArray[readX+x4][readY+y4][1];
	            	pix4BLUE = originalPixelArray[readX+x4][readY+y4][2];	            	
	            	            	
	            	subsamplePixelArray[col+0][row+0][0]=pix1RED;
	            	subsamplePixelArray[col+0][row+0][1]=pix1GREEN;
	            	subsamplePixelArray[col+0][row+0][2]=pix1BLUE;
	            	
	            	subsamplePixelArray[col+1][row+0][0]=pix2RED;
	            	subsamplePixelArray[col+1][row+0][1]=pix2GREEN;
	            	subsamplePixelArray[col+1][row+0][2]=pix2BLUE;																				
	            	
	            	subsamplePixelArray[col+0][row+1][0]=pix3RED;
	            	subsamplePixelArray[col+0][row+1][1]=pix3GREEN;
	            	subsamplePixelArray[col+0][row+1][2]=pix3BLUE;
	            	
	            	subsamplePixelArray[col+1][row+1][0]=pix4RED;
	            	subsamplePixelArray[col+1][row+1][1]=pix4GREEN;
	            	subsamplePixelArray[col+1][row+1][2]=pix4BLUE;	            	
	            		            	
	            	col+=subSampleWidth;
	            	updateProgress(progressIndicator++,totalProgress);	            	
	            }
	            row+=subSampleHeight;
			}
			
			return subsamplePixelArray;
			
		}catch(Exception ex){
			updateMessage("SubSample::toInterpolatedArray:- "+ex.toString());
			ex.printStackTrace();
			throw ex;
		}
	}//// End toSubSampleArray
	
	
	int[][][] toInterpolatedArray(int[][][] originalPixelArray, int checkOverflow){
		
		int width=originalPixelArray.length;
		int height=originalPixelArray[1].length;		
		
		int[][][] interpolatedPixelArray;
		
		try{		
			interpolatedPixelArray=new int[(width*3)/2][(height*3)/2][3];
				
			int row,col;	
    		int totalProgress=(((width*3)/2)*((height*3)/2))/9;
			int progressIndicator=0;
			int pix1RED,pix1GREEN,pix1BLUE;
			int pix2RED,pix2GREEN,pix2BLUE;
			int pix3RED,pix3GREEN,pix3BLUE;
			int pix4RED,pix4GREEN,pix4BLUE;
			
			row=0;
			for (int readY = 0; readY < height; readY+=2) {
				col=0;
	            for (int readX = 0; readX < width; readX+=2) {            	
	            	//// Get the first pixel value	            	
	            	pix1RED = originalPixelArray[readX][readY][0];
	            	pix1GREEN = originalPixelArray[readX][readY][1];
	            	pix1BLUE = originalPixelArray[readX][readY][2];
	            	
	            	pix2RED = originalPixelArray[readX+1][readY][0];
	            	pix2GREEN = originalPixelArray[readX+1][readY][1];
	            	pix2BLUE = originalPixelArray[readX+1][readY][2];
	            	
	            	pix3RED = originalPixelArray[readX][readY+1][0];
	            	pix3GREEN = originalPixelArray[readX][readY+1][1];
	            	pix3BLUE = originalPixelArray[readX][readY+1][2];
	            	
	            	pix4RED = originalPixelArray[readX+1][readY+1][0];
	            	pix4GREEN = originalPixelArray[readX+1][readY+1][1];
	            	pix4BLUE = originalPixelArray[readX+1][readY+1][2];	            	
	            	
	            	////////// Manage overflow condition /////////////
	            	/////// RED Pixels //////////
	            	if((pix1RED+pix2RED)/2 > 248){
	            		pix1RED=248;
	            		pix2RED=248;
	            	}
	            	if((pix1RED+pix3RED)/2 > 248){
	            		pix1RED=248;
	            		pix3RED=248;
	            	}
	            	if((pix2RED+pix4RED)/2 > 248){
	            		pix2RED=248;
	            		pix4RED=248;
	            	}
	            	if((pix3RED+pix4RED)/2 > 248){
	            		pix3RED=248;
	            		pix4RED=248;
	            	}
	            	/////////// GREEN Pixels ///////////
	            	if((pix1GREEN+pix2GREEN)/2 > 248){
	            		pix1GREEN=248;
	            		pix2GREEN=248;
	            	}
	            	if((pix1GREEN+pix3GREEN)/2 > 248){
	            		pix1GREEN=248;
	            		pix3GREEN=248;
	            	}
	            	if((pix2GREEN+pix4GREEN)/2 > 248){
	            		pix2GREEN=248;
	            		pix4GREEN=248;
	            	}
	            	if((pix3GREEN+pix4GREEN)/2 > 248){
	            		pix3GREEN=248;
	            		pix4GREEN=248;
	            	}
	            	/////////// BLUE Pixels ///////////
	            	if((pix1BLUE+pix2BLUE)/2 > 248){
	            		pix1BLUE=248;
	            		pix2BLUE=248;
	            	}
	            	if((pix1BLUE+pix3BLUE)/2 > 248){
	            		pix1BLUE=248;
	            		pix3BLUE=248;
	            	}
	            	if((pix2BLUE+pix4BLUE)/2 > 248){
	            		pix2BLUE=248;
	            		pix4BLUE=248;
	            	}
	            	if((pix3BLUE+pix4BLUE)/2 > 248){
	            		pix3BLUE=248;
	            		pix4BLUE=248;
	            	}					
	            	///////////////////////////////////////////////////	
	            	
	            	interpolatedPixelArray[col+0][row+0][0]=pix1RED;
	            	interpolatedPixelArray[col+0][row+0][1]=pix1GREEN;
	            	interpolatedPixelArray[col+0][row+0][2]=pix1BLUE;
	            	
	            	interpolatedPixelArray[col+2][row+0][0]=pix2RED;
	            	interpolatedPixelArray[col+2][row+0][1]=pix2GREEN;
	            	interpolatedPixelArray[col+2][row+0][2]=pix2BLUE;																				
	            	
	            	interpolatedPixelArray[col+0][row+2][0]=pix3RED;
	            	interpolatedPixelArray[col+0][row+2][1]=pix3GREEN;
	            	interpolatedPixelArray[col+0][row+2][2]=pix3BLUE;
	            	
	            	interpolatedPixelArray[col+2][row+2][0]=pix4RED;
	            	interpolatedPixelArray[col+2][row+2][1]=pix4GREEN;
	            	interpolatedPixelArray[col+2][row+2][2]=pix4BLUE;
	            	
	            	//// Test overflow condition
	            	interpolatedPixelArray[col+1][row+0][0]=(pix1RED+pix2RED)/2;
	            	interpolatedPixelArray[col+1][row+0][1]=(pix1GREEN+pix2GREEN)/2;
	            	interpolatedPixelArray[col+1][row+0][2]=(pix1BLUE+pix2BLUE)/2;
	            	
	            	interpolatedPixelArray[col+0][row+1][0]=(pix1RED+pix3RED)/2;
	            	interpolatedPixelArray[col+0][row+1][1]=(pix1GREEN+pix3GREEN)/2;
	            	interpolatedPixelArray[col+0][row+1][2]=(pix1BLUE+pix3BLUE)/2;
	            	
	            	interpolatedPixelArray[col+2][row+1][0]=(pix2RED+pix4RED)/2;
	            	interpolatedPixelArray[col+2][row+1][1]=(pix2GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[col+2][row+1][2]=(pix2BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[col+1][row+2][0]=(pix3RED+pix4RED)/2;
	            	interpolatedPixelArray[col+1][row+2][1]=(pix3GREEN+pix4GREEN)/2;
	            	interpolatedPixelArray[col+1][row+2][2]=(pix3BLUE+pix4BLUE)/2;
	            	
	            	interpolatedPixelArray[col+1][row+1][0]=(pix1RED+pix2RED+pix3RED+pix4RED)/4;
	            	interpolatedPixelArray[col+1][row+1][1]=(pix1GREEN+pix2GREEN+pix3GREEN+pix4GREEN)/4;
	            	interpolatedPixelArray[col+1][row+1][2]=(pix1BLUE+pix2BLUE+pix3BLUE+pix4BLUE)/4;
	            	
	            	col+=3;
	            	updateProgress(progressIndicator++,totalProgress);
	            }
	            row+=3;
			}
			
			return interpolatedPixelArray;
			
		}catch(Exception ex){
			updateMessage("MyImage::toInterpolatedImage:- "+ex.toString());
			throw ex;
		}
	}//// End toInterpolatedImageSubSample	
	
	int[][][] imread(String filePath){
		BufferedImage inputImage=null;
		try{
			File file=new File(filePath);
			inputImage = ImageIO.read(file);
		}catch(Exception ex){
			updateMessage("SubSample::imread :- "+ex.toString());
		}	
		
		int[][][] pixelArray=new int[inputImage.getWidth()][inputImage.getHeight()][3];
		int pixelValue;
		Color pixelColor;
		int height=inputImage.getHeight();
		int width=inputImage.getWidth();
		for (int readY = 0; readY < height; readY++) {
			for (int readX = 0; readX < width; readX++) {            	
				pixelValue = inputImage.getRGB(readX, readY);
				pixelColor=new Color(pixelValue);
				pixelArray[readX][readY][0]=pixelColor.getRed();
				pixelArray[readX][readY][1]=pixelColor.getGreen();
				pixelArray[readX][readY][2]=pixelColor.getBlue();            	
			}
		}
		return pixelArray;
	}//// End imread
	
	void imwrite(int[][][] pixelArray,String fileName){
		int width=pixelArray.length;
		int height=pixelArray[1].length;
		BufferedImage outputImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		try{
			int pixelValue,redPixel,greenPixel,bluePixel;			
			
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {
					redPixel = pixelArray[readX][readY][0];
			    	greenPixel = pixelArray[readX][readY][1];
			    	bluePixel = pixelArray[readX][readY][2];
			    	
			    	pixelValue=(redPixel << 16 | greenPixel << 8 | bluePixel);            	
			    	outputImage.setRGB(readX, readY, pixelValue);					
				}
			}	        
			//// Saving the image files
			File file = new File(fileName);
			try {
				ImageIO.write(outputImage, "PNG", file);
				updateMessage(fileName + " File Created Successfully");			
				} catch (IOException e) {
					updateMessage(fileName + " File Creation Error!!!");
					e.printStackTrace();
				}     
			updateMessage("Interpolated Image "+fileName+" Created Successfully.");
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}//// End imwrite
	
	int getResultFromEquationFor(int x,int temp){
		int result=(a2*(int)Math.pow(x, 2))+(a1*x)+temp;
		return result;
	}
	////Convert image to bit string
	String toBinaryStringFromImageArray(int[][][] pixelArray){
		try{
			int imageWidth=pixelArray.length;
			int imageHeight=pixelArray[1].length;
			StringBuilder sbImageString=new StringBuilder();
			
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){	
					sbImageString.append(toNBitBinaryString(pixelArray[x][y][0], 8));
					sbImageString.append(toNBitBinaryString(pixelArray[x][y][1], 8));
					sbImageString.append(toNBitBinaryString(pixelArray[x][y][2], 8));
				}  
			}			
			return sbImageString.toString();
		}catch(Exception ex){
			updateMessage("Image To Binary String Conversion Completed.");
			ex.printStackTrace();
			throw ex;
		}
	}//// End toBinaryStringFromImageArray
	
	////Convert to binary string of 8 bits
	String toNBitBinaryString(int n,int nBit){
		try{
			StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
			int padding = nBit - binaryStringBuilder.length();
			for(int i=0;i<padding;i++){
				binaryStringBuilder.insert(0, "0");
			}		
			return binaryStringBuilder.toString();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}//// End toNBitBinaryString
	
	String getFirstNBitsSubSample(int nBits){
		try{
			String strTemp = sbSecretBits.substring(secretBitsConsumed, secretBitsConsumed + nBits);
			secretBitsConsumed+=nBits;
			return strTemp;
		}catch(Exception ex){
			updateMessage("SubSampleClass::GetFirstNBits:- "+ex.toString());
			ex.printStackTrace();
			return "STOP";
		}
	}//// End getFirstNBits
		
	////Returns decimal value of a binary string
	int toDecimalFromBinary(String strBinary){
		try{
			return Integer.parseInt(strBinary, 2);
		}catch(Exception ex){
			throw ex;
		}
	}//// End toDecimalFromBinary
	
	int getValueFromLagrangeInterpolation(int x1,int fx1,int x2,int fx2,int x3,int fx3){
		
		int a0=0;
		/*
		int px=0;
		int x=3;
		px=(((x-2)*(x-3))/((1-2)*(1-3)))*fx1+
		   (((x-1)*(x-3))/((2-1)*(2-3)))*fx2+
		   (((x-1)*(x-2))/((3-1)*(x-2)))*fx3;  
		*/
		a0=fx1-((a2*(int)Math.pow(x1, 2))+(a1*x1));
		
		return a0;
	}
	
	
	void toImageFromBinaryStringSubSample(String fileName, String fileDirectory, 
			 StringBuilder binaryString,int imageWidth, int imageHeight){

		try{
		
			int red=0,green=0,blue=0;
			int pixelColor=0;
			//// Create the empty output image file
			BufferedImage outputImage=new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
			int substringCount=0;
			
			for(int y=0;y<imageHeight;y++){
				for(int x=0;x<imageWidth;x++){														
					red=Integer.parseInt(binaryString.substring(substringCount, substringCount+8), 2);
					green=Integer.parseInt(binaryString.substring(substringCount+8, substringCount+16),2);
					blue=Integer.parseInt(binaryString.substring(substringCount+16, substringCount+24),2);
					//// Get the pixel color
					pixelColor=(red << 16) | (green << 8) | blue;
					outputImage.setRGB(x, y, pixelColor);
			
					substringCount+=24;								
				}
			}					
			//// Create the output image file and save it in a file
			File imageFile = new File(fileDirectory+"\\"+fileName);
			ImageIO.write(outputImage, "PNG", imageFile);
			updateMessage("Image Extracted Successfully");
		
		}catch(Exception ex){
			updateMessage("SubSampleClass::toImageFromBinaryStringSubSample:- "+ex.toString());
			ex.printStackTrace();
		    return;
		}
		
	}//// End toImageFromBinaryStringSubSample
		
	////This function creates and writes the log.
	void WriteLogFileSubSample(String fileName,StringBuilder strContent){		
		try(FileWriter fw = new FileWriter(fileDirectory+"\\"+fileName, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw)){
			out.print(strContent);
		} catch (IOException ex) {
			updateMessage("SubSampleClass::WriteLogFile:- "+ex.toString());
			ex.printStackTrace();
		    return;
		}		
	}//// End WriteMatrixIndexFile
	
	@Override
	protected Void call() throws Exception {		
			
		if(steganoType.equalsIgnoreCase("embed")){
			StartEmbeddingSubSample();
			Thread.currentThread().interrupt();
		}else if(steganoType.equalsIgnoreCase("extract")){
			StartExtractionSubSample();
			Thread.currentThread().interrupt();
		}		
		return null;
	}  
	
}
///////////////////////////   END SubSampleClass class //////////////////////////////


///////////////////////////   START MyImage Class //////////////////////////////
class MyImage extends Task<Void>{
	int width;
	int height;  
	BufferedImage inputImage;
	public int[][] pixelArrayRED;
	public int[][] pixelArrayGREEN;
	public int[][] pixelArrayBLUE;
	int blockSize;
	int totalBitsEmbedded;
	StringBuilder sbSecretImageDataBits;
	StringBuilder sbExtractedDataBits;
	int secretBitsConsumed;
	StringBuilder strDataBit;
	
	MyImage(int w,int h){
		width=w;
		height=h;
		inputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixelArrayRED=new int[height][width];
		pixelArrayGREEN=new int[height][width];
		pixelArrayBLUE=new int[height][width];
		blockSize=0;
		totalBitsEmbedded=0;
		sbSecretImageDataBits=new StringBuilder();
		sbExtractedDataBits=new StringBuilder();
		secretBitsConsumed=0;
		strDataBit=new StringBuilder();
		createImageMatrices();
	}
	
	MyImage(BufferedImage image){
		inputImage=image;
		width=inputImage.getWidth();
		height=inputImage.getHeight();
		pixelArrayRED=new int[height][width];
		pixelArrayGREEN=new int[height][width];
		pixelArrayBLUE=new int[height][width];	
		blockSize=0;
		totalBitsEmbedded=0;
		sbSecretImageDataBits=new StringBuilder();
		sbExtractedDataBits=new StringBuilder();
		secretBitsConsumed=0;	
		strDataBit=new StringBuilder();
		createImageMatrices();
	}
	
	MyImage(StringBuilder binaryString,int w, int h) {	
		try{			
			width=w;
			height=h;
			inputImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			pixelArrayRED=new int[height][width];
			pixelArrayGREEN=new int[height][width];
			pixelArrayBLUE=new int[height][width];	
			strDataBit=new StringBuilder();
			
			int substringCount=0;		
			int red=0,green=0,blue=0;
			int pixelColor=0;		
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){														
					red=Integer.parseInt(binaryString.substring(substringCount, substringCount+8), 2);
					green=Integer.parseInt(binaryString.substring(substringCount+8, substringCount+16),2);
					blue=Integer.parseInt(binaryString.substring(substringCount+16, substringCount+24),2);
					//// Get the pixel color
					pixelColor=(red << 16) | (green << 8) | blue;
					inputImage.setRGB(x, y, pixelColor);
			
					substringCount+=24;								
				}
			}	
			
			createImageMatrices();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	MyImage(String filePath){
		try{
			File file=new File(filePath);
			inputImage = ImageIO.read(file);
		}catch(Exception ex){
			inputImage = null;
			updateMessage("MyImage::MyImage :- "+ex.toString());
		}		
	}
		
	void setBlockSize(int n){
		blockSize=n;
	}
	
	int getWidth(){
		return width;
	}
	
	int getHeight(){
		return height;
	}

	int getRGB(int x,int y){
		return inputImage.getRGB(x, y);
	}
	
	String ConvertToNBitBinaryString(int n,int nBit){
		try{
			StringBuilder binaryStringBuilder=new StringBuilder(Integer.toBinaryString(n));
			int padding = nBit - binaryStringBuilder.length();
			for(int i=0;i<padding;i++){
				binaryStringBuilder.insert(0, "0");
			}		
			return binaryStringBuilder.toString();
		}catch(Exception ex){
			throw ex;
		}
	}
	
	String toBinaryString(){			
		try{
			StringBuilder sbImageString=new StringBuilder();
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){					
					Color c = new Color(inputImage.getRGB(x, y));
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();					
					sbImageString.append(ConvertToNBitBinaryString(r,8));
					sbImageString.append(ConvertToNBitBinaryString(g,8));
					sbImageString.append(ConvertToNBitBinaryString(b,8));	
				}  
			}
			return sbImageString.toString();
		}catch(Exception ex){
			throw ex;
		}
	}
	
	void getImageMatrices(int[][] pxlArrayRED,int[][] pxlArrayGREEN,int[][] pxlArrayBLUE)
	{ 
		try{
			int pixelValue;
			Color pixelColor;
			int height=inputImage.getHeight();
			int width=inputImage.getWidth();
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {            	
					pixelValue = inputImage.getRGB(readX, readY);
					pixelColor=new Color(pixelValue);
					pixelArrayRED[readX][readY]=pixelColor.getRed();
					pixelArrayGREEN[readX][readY]=pixelColor.getGreen();
					pixelArrayBLUE[readX][readY]=pixelColor.getBlue();            	
				}
			}
		}catch(Exception ex){
			throw ex;
		}
		
	}

	void createImageMatrices()
	{ 
		try{
			int pixelValue;
			Color pixelColor;
			
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {            	
					pixelValue = getRGB(readY, readX);
					pixelColor=new Color(pixelValue);
					pixelArrayRED[readX][readY]=pixelColor.getRed();
					pixelArrayGREEN[readX][readY]=pixelColor.getGreen();   
					pixelArrayBLUE[readX][readY]=pixelColor.getBlue();            	
				}
			}
		}catch(Exception ex){
			updateMessage("MyImage::createImageMatrices :- "+ex.toString());
			throw ex;
		}
		
	}  
	
	void saveAsFile(String fileName, String fileDirectory){
		try{
			int pixelValue,redPixel,greenPixel,bluePixel;			
			
			for (int readY = 0; readY < height; readY++) {
				for (int readX = 0; readX < width; readX++) {
					redPixel = pixelArrayRED[readX][readY];
			    	greenPixel = pixelArrayGREEN[readX][readY];
			    	bluePixel = pixelArrayBLUE[readX][readY];
			    	
			    	pixelValue=(redPixel << 16 | greenPixel << 8 | bluePixel);            	
			    	inputImage.setRGB(readY, readX, pixelValue);					
				}
			}	        
			//// Saving the image files
			File file = new File(fileDirectory + "\\"+fileName);
			try {
				ImageIO.write(inputImage, "PNG", file);
				updateMessage(fileName + " File Created Successfully");			
				} catch (IOException e) {
					updateMessage(fileName + " File Creation Error!!!");
					e.printStackTrace();
				}     
			updateMessage("Interpolated Image Created Successfully.");
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	MyImage cloneImage(){		
		BufferedImage newImage = inputImage.getSubimage(0, 0, width, height);
	    return new MyImage(newImage);
	}
	
	MyImage toInterpolatedImage(int newWidth,int newHeight){
		
		try{					
			BufferedImage interpolatedImageBuffer = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			MyImage interpolatedImage = new MyImage(interpolatedImageBuffer);
			
			int row,col;	
    		int totalProgress=height*width;
			int progressIndicator=0;
			int pix1,pix2,pix3,pix4;							
			Color pixelColor=null;
			
			row=0;
			for (int readY = 0; readY < height; readY+=2) {
				col=0;
	            for (int readX = 0; readX < width; readX+=2) {            	
	            	//// Get the first pixel value
	            	pix1 = getRGB(readX, readY);
	            	pix2 = getRGB(readX+1, readY);
	            	pix3 = getRGB(readX, readY+1);
	            	pix4 = getRGB(readX+1, readY+1);
	            	
	            	pixelColor=new Color(pix1);
	               	int redPixel1 = pixelColor.getRed();
	            	int greenPixel1 = pixelColor.getGreen();
	            	int bluePixel1 = pixelColor.getBlue();
	            	interpolatedImage.pixelArrayRED[row+0][col+0]=redPixel1;
	            	interpolatedImage.pixelArrayGREEN[row+0][col+0]=greenPixel1;
	            	interpolatedImage.pixelArrayBLUE[row+0][col+0]=bluePixel1;
	            	
	            	
	            	pixelColor=new Color(pix2);
	               	int redPixel2 = pixelColor.getRed();
	            	int greenPixel2 = pixelColor.getGreen();
	            	int bluePixel2 = pixelColor.getBlue();
	            	interpolatedImage.pixelArrayRED[row+0][col+2]=redPixel2;
	            	interpolatedImage.pixelArrayGREEN[row+0][col+2]=greenPixel2;
	            	interpolatedImage.pixelArrayBLUE[row+0][col+2]=bluePixel2;
	            	
	            	
	            	pixelColor=new Color(pix3);
	               	int redPixel3 = pixelColor.getRed();
	            	int greenPixel3 = pixelColor.getGreen();
	            	int bluePixel3 = pixelColor.getBlue();
	            	interpolatedImage.pixelArrayRED[row+2][col+0]=redPixel3;
	            	interpolatedImage.pixelArrayGREEN[row+2][col+0]=greenPixel3;
	            	interpolatedImage.pixelArrayBLUE[row+2][col+0]=bluePixel3;
	            	
	            	
	            	pixelColor=new Color(pix4);
	               	int redPixel4 = pixelColor.getRed();
	            	int greenPixel4 = pixelColor.getGreen();
	            	int bluePixel4 = pixelColor.getBlue();
	            	interpolatedImage.pixelArrayRED[row+2][col+2]=redPixel4;
	            	interpolatedImage.pixelArrayGREEN[row+2][col+2]=greenPixel4;
	            	interpolatedImage.pixelArrayBLUE[row+2][col+2]=bluePixel4;
	            	
	            	interpolatedImage.pixelArrayRED[row+0][col+1]=(redPixel1+redPixel1)/2;
	            	interpolatedImage.pixelArrayGREEN[row+0][col+1]=(greenPixel1+greenPixel2)/2;
	            	interpolatedImage.pixelArrayBLUE[row+0][col+1]=(bluePixel1+bluePixel2)/2;
	            	
	            	interpolatedImage.pixelArrayRED[row+1][col+0]=(redPixel1+redPixel3)/2;
	            	interpolatedImage.pixelArrayGREEN[row+1][col+0]=(greenPixel1+greenPixel3)/2;
	            	interpolatedImage.pixelArrayBLUE[row+1][col+0]=(bluePixel1+bluePixel3)/2;
	            	
	            	interpolatedImage.pixelArrayRED[row+1][col+2]=(redPixel2+redPixel4)/2;
	            	interpolatedImage.pixelArrayGREEN[row+1][col+2]=(greenPixel2+greenPixel4)/2;
	            	interpolatedImage.pixelArrayBLUE[row+1][col+2]=(bluePixel2+bluePixel4)/2;
	            	
	            	interpolatedImage.pixelArrayRED[row+2][col+1]=(redPixel3+redPixel4)/2;
	            	interpolatedImage.pixelArrayGREEN[row+2][col+1]=(greenPixel3+greenPixel4)/2;
	            	interpolatedImage.pixelArrayBLUE[row+2][col+1]=(bluePixel3+bluePixel4)/2;
	            	
	            	interpolatedImage.pixelArrayRED[row+1][col+1]=(redPixel1+redPixel2+redPixel3+redPixel4)/4;
	            	interpolatedImage.pixelArrayGREEN[row+1][col+1]=(greenPixel1+greenPixel2+greenPixel3+greenPixel4)/4;
	            	interpolatedImage.pixelArrayBLUE[row+1][col+1]=(bluePixel1+bluePixel2+bluePixel3+bluePixel4)/4;
	            	            	
	            	col+=3;
	            	updateProgress(progressIndicator++,totalProgress);
	            }
	            row+=3;
			}
			
			return interpolatedImage;
			
		}catch(Exception ex){
			updateMessage("MyImage::toInterpolatedImage:- "+ex.toString());
			throw ex;
		}
	}
	
	int embed5PVD(StringBuilder sbData){
		try{
			sbSecretImageDataBits.append(sbData.toString());
			secretBitsConsumed=0;
			int ret=0;
			outer:
			for(int x=0;x<height;x+=3){
				for(int y=0;y<width;y+=3){
					//// Do these for RED,GREEN and BLUE pixels
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,0);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,1);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,2);
					if(ret==-1) break outer;
					
					ret=EmbedDataBitsInPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,0);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,1);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,2);
					if(ret==-1) break outer;
					
					ret=EmbedDataBitsInPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,0);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,1);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,2);
					if(ret==-1) break outer;
					
					ret=EmbedDataBitsInPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,0);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,1);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,2);
					if(ret==-1) break outer;
					
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,0);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,1);
					if(ret==-1) break outer;
					ret=EmbedDataBitsInPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,2);
					if(ret==-1) break outer;
				}
			}
			
			return totalBitsEmbedded;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	int EmbedDataBitsInPixelXY(int x1,int y1,int x2,int y2,int xmid,int ymid,int color){
		try{			
			int d,dd,ddd,pixel1,pixel2,pixel1New,pixel2New;	
			
	    	////////////  START: PVD Portion /////////////////
			if(color==0){
				pixel1=pixelArrayRED[y1][x1];
				pixel2=pixelArrayRED[y2][x2];		  		
			}else if(color==1){
				pixel1=pixelArrayGREEN[y1][x1]++;
				pixel2=pixelArrayGREEN[y2][x2]++;				  
			}else{
				pixel1=pixelArrayBLUE[y1][x1]++;  
				pixel2=pixelArrayBLUE[y2][x2]++;				
			}
							
			//// Check FallingOfBoundary check for 0 or 255 /////
			d = Math.abs(pixel1 - pixel2);
			
			int m=(GetUpperBoundOfRange(d)-d);
			int floor=(int)Math.floor(m/2);
			int ceiling=(int)Math.ceil(m/2);
			 
			if((d % 2) == 0 ){
				pixel1New=pixel1-ceiling;
				pixel2New=pixel2+floor;
			}else{
				pixel1New=pixel1-floor;
				pixel2New=pixel2+ceiling;
			}
			if(pixel1New==0||pixel1New==255||pixel2New==0||pixel2New==255){
				return 1;
			}
			///////////////////////////////////////////////////////
			String strData=GetFirstNBits(4);
			if(strData=="END"){
				return -1;
			}
			int data=BinaryStringToDecimal(strData);
			int lb=GetLowerBoundOfRange(d);
			dd=data+lb;
			ddd=Math.abs(dd-d); 
			floor=(int)Math.floor((double)ddd/2);
			ceiling=(int)Math.ceil((double)ddd/2);
			
			if(color==0){
				pixelArrayRED[y1][x1]+=ceiling;
				pixelArrayRED[y2][x2]-=floor;
				//// Save ddd(m) in the middle pixel
				pixelArrayRED[ymid][xmid]=(pixelArrayRED[ymid][xmid] & 240) + ddd; 				
			}else if(color==1){
				pixelArrayGREEN[y1][x1]+=ceiling;
				pixelArrayGREEN[y2][x2]-=floor;
				//// Save ddd(m) in the middle pixel
				pixelArrayGREEN[ymid][xmid]=(pixelArrayGREEN[ymid][xmid] & 240) + ddd;
			}else{
				pixelArrayBLUE[y1][x1]+=ceiling;
				pixelArrayBLUE[y2][x2]-=floor;
				//// Save ddd(m) in the middle pixel
				pixelArrayBLUE[ymid][xmid]=(pixelArrayBLUE[ymid][xmid] & 240) + ddd;
			}		
			
			totalBitsEmbedded+=4;			
			//////////// END: PVD Portion //////////////////////////////////////////////////
						
		}catch(Exception ex){
			throw ex;			
		}
		return 0;	
	}//// End EmbedDataBitsInPixelXY
	 
	String extractDataBits(){
		sbExtractedDataBits.setLength(0);
		int totalProgress=(height/3)*(width/3);
		int progressIndicator=0;
		
		try{
			for(int y=0;y<height;y+=3){
				for(int x=0;x<width;x+=3){
					strDataBit.setLength(0);
					//// Do these for RED,GREEN and BLUE pixels
					ExtractDataBitsFromPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,2);
					ExtractDataBitsFromPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,1);
					ExtractDataBitsFromPixelXY(x+0,y+0,x+2,y+2,x+1,y+1,0);					
					
					ExtractDataBitsFromPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,2);
					ExtractDataBitsFromPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,1);
					ExtractDataBitsFromPixelXY(x+2,y+0,x+0,y+0,x+1,y+0,0);
					
					ExtractDataBitsFromPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,2);
					ExtractDataBitsFromPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,1);
					ExtractDataBitsFromPixelXY(x+2,y+2,x+2,y+0,x+2,y+1,0);
					
					ExtractDataBitsFromPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,2);
					ExtractDataBitsFromPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,1);
					ExtractDataBitsFromPixelXY(x+0,y+2,x+2,y+2,x+1,y+2,0);
					
					ExtractDataBitsFromPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,2);
					ExtractDataBitsFromPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,1);
					ExtractDataBitsFromPixelXY(x+0,y+0,x+0,y+2,x+0,y+1,0);
					
					System.out.println("Total progress:- "+progressIndicator +"/" + totalProgress);
					progressIndicator++;
				}
				sbExtractedDataBits.append(strDataBit);
			}
			
			updateProgress(100,100);
			return sbExtractedDataBits.toString(); 
		}catch(Exception ex){
			ex.printStackTrace();
			updateMessage("MyImage::extractDataBits :- "+ex.toString());
			throw ex;
		}
	}
	
	void ExtractDataBitsFromPixelXY(int x1,int y1,int x2,int y2, int xmid,int ymid,int color){
		int d,pixel1,pixel2,data;
		int pixel1New,pixel2New;
		
		try{
			if(color==0){
				pixel1=pixelArrayRED[y1][x1];
				pixel2=pixelArrayRED[y2][x2];	
			}else if(color==1){
				pixel1=pixelArrayGREEN[y1][x1];
				pixel2=pixelArrayGREEN[y2][x2];
			}else{
				pixel1=pixelArrayBLUE[y1][x1];
				pixel2=pixelArrayBLUE[y2][x2];
			}
						
			//// START: Check FallingOfBoundary check for 0 or 255 /////
			d = Math.abs(pixel1 - pixel2);
			
			int m=(GetUpperBoundOfRange(d)-d);
			int floor=(int)Math.floor((double)m/2);
			int ceiling=(int)Math.ceil((double)m/2);
			 
			if((d % 2) == 0 ){
				pixel1New=pixel1-ceiling; 
				pixel2New=pixel2+floor;
			}else{
				pixel1New=pixel1-floor;
				pixel2New=pixel2+ceiling;
			}
			if(pixel1New==0||pixel1New==255||pixel2New==0||pixel2New==255){
				return;
			}
			//// END: Check FallingOfBoundary check for 0 or 255 /////			
			d = Math.abs(pixel1 - pixel2);
			data=d - GetLowerBoundOfRange(Math.abs(d));			
			//////////////////////////////////////////////////////////////
			strDataBit.insert(0, ConvertToNBitBinaryString(data,4));
			////sbExtractedDataBits.insert(0,ConvertToNBitBinaryString(data,4));
			
		}catch(Exception ex){
			throw ex;	
		}
	}
 
	int GetLowerBoundOfRange(int d){
		try{
			//// Ranges are 0-15, 16-31, 32-47, 48-63,....., 240-255
			int lowerBound=-1;
			lowerBound=16*(d/16);
			return lowerBound;
		}catch(Exception ex){
			throw ex;
		}
	}//// End getLowerBoundOfRange
	
	int GetUpperBoundOfRange(int d){
		try{
			//// Ranges are 0-15, 16-31, 32-47, 48-63,....., 240-255
			int lowerBound=-1;
			int upperBound=-1;
			
			lowerBound=16*(d/16);
			upperBound=lowerBound+15;
			
			return upperBound;
		}catch(Exception ex){
			throw ex;  
		}
	}//// End getLowerBoundOfRange
	
	////Get first N bits from secret data bits
	String GetFirstNBits(int nBits){
		try{
			//// Strip first N bits from the string
			String strTemp = sbSecretImageDataBits.substring(secretBitsConsumed, secretBitsConsumed + nBits);
			secretBitsConsumed+=4;
			return strTemp;
		}catch(Exception ex){
			return "END";
		}
	}//// End getFirstNBits
	
	////Returns decimal value of a binary string
	int BinaryStringToDecimal(String strBinary){
		try{
			return Integer.parseInt(strBinary, 2);
		}catch(Exception ex){
			throw ex;
		}
	}//// End binaryStringToDecimal
	
	protected Void call() throws Exception {
		return null; 
	}
	
}
//////////////// END MyImage Class ////////////////////////////////////////////


