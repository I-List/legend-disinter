/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package gui.controllers;

import java.io.File;
import java.io.IOException;

import exceptions.CharacterNotSupportedException;
import gui.Dialogs;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.AppProperties;
import utils.FileLoader;
import utils.FileSaver;
import utils.Normalizer;

public class MainController {
    private final String about = AppProperties.getProperty("about");

    private final FileChooser fileChooser = new FileChooser();

    // Views and dialogs
    private Parent breakerView;
    private Parent digiView;
    private Parent tranView;
    private Stage statisticsDialog;

    // Controllers
    private DigiController digiController;
    private TranController tranController;
    private BreakerController breakerController;
    private StatisticsController statisticsController;

    // References to GUI elements
    @FXML private BorderPane root;
    @FXML private Button digiButton;
    @FXML private Button breakerButton;
    @FXML private Button tranButton;

    @FXML
    public void initialize() {
        digiButton.setDisable(true);
        // Load FXML files
        try {	
            System.out.println("initialize");

            // Digitize view
            FXMLLoader digiLoader = new FXMLLoader(getClass().getResource("/gui/DigiView.fxml"));
            digiView = digiLoader.load();
            digiController = digiLoader.getController();

            // Breaker view
            FXMLLoader breakerLoader = new FXMLLoader(getClass().getResource("/gui/BreakerView.fxml"));
            breakerView = breakerLoader.load();
            breakerController = breakerLoader.getController();

            // Translate view
            FXMLLoader tranLoader = new FXMLLoader(getClass().getResource("/gui/TranView.fxml"));
            tranView = tranLoader.load();
            tranController = tranLoader.getController();

            // Statistics dialog
            loadStatisticsDialog();

            // Configure file chooser
            configureFileChooser();
        } catch (IOException e) {
                Dialogs.showErrorDialog(e.getMessage());
        }

        // Set initial view
        root.setCenter(digiView);
    }

    private void loadStatisticsDialog() throws IOException {
            // Load FXML file
            FXMLLoader statisticsLoader = new FXMLLoader(getClass().getResource("/gui/StatisticsDialog.fxml"));
            Parent statisticsParent = statisticsLoader.load();
            statisticsController = statisticsLoader.getController();

            // Set up stage
            statisticsDialog = new Stage();

            statisticsDialog.setTitle(AppProperties.getProperty("statistics.title"));

            int minWidth = Integer.parseInt(AppProperties.getProperty("statistics.minWidth"));
            int minHeight = Integer.parseInt(AppProperties.getProperty("statistics.minHeight"));

            statisticsDialog.setMinWidth(minWidth);
            statisticsDialog.setMinHeight(minHeight);

            statisticsDialog.setScene(new Scene(statisticsParent));
    }

    private void configureFileChooser() {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plain Text", "*.txt"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", 
                    "*.jpg","*.png", "*.tiff", "*.gif", "*.bmp", "*.jpeg"));
    }

    @FXML
    private void showDigiView() {
        digiButton.setDisable(true);
        tranButton.setDisable(false);
        breakerButton.setDisable(false);
        root.setCenter(digiView);
    }

    @FXML
    private void showBreakerView() {
        breakerButton.setDisable(true);
        digiButton.setDisable(false);
        tranButton.setDisable(false);
        //If the view being switched from is the 
        //Digitize View, paste the text into Breaker View
        String text;
        if(root.getCenter().equals(digiView)){
            text = digiController.getCleartext();
            breakerController.setCiphertext(text);
        }
        
        root.setCenter(breakerView);
    }

    @FXML
    private void showTranView() {
        tranButton.setDisable(true);
        digiButton.setDisable(false);
        breakerButton.setDisable(false);
        
        //If the view being switched from is the 
        //Breaker View, paste the text into Translate View
        String text;
        if(root.getCenter().equals(breakerView)){
            text = breakerController.getCleartext();
            tranController.setCiphertext(text);
        }
        
        root.setCenter(tranView);
    }

    @FXML
    private void loadCleartext() {
        File file = showOpenDialog();

        if (file != null) {
                try {
                    String text = FileLoader.readTextFile(file);
                    if(root.getCenter().equals(breakerView)){
                        breakerController.setCiphertext(text);
                    }
                    else if(root.getCenter().equals(tranView)){
                        tranController.setCiphertext(text);
                    }
                } catch (IOException e) {
                        Dialogs.showErrorDialog(e.getMessage());
                }
        }
    }

    @FXML
    private void loadImage() {
        File file = showOpenDialog();

        if (file != null) {
                try {
                    Image image = FileLoader.readImageFile(file);
                    digiController.setImage(image);
                    digiController.activate();
                } catch (IOException e) {
                        Dialogs.showErrorDialog(e.getMessage());
                }
        }
    }

    @FXML
    private void saveCleartext() {
        File file = showSaveDialog();

        if (file != null) {
            try {
                String text;
                if(root.getCenter().equals(breakerView)){
                    text = breakerController.getCleartext();
                }
                else if(root.getCenter().equals(tranView)){
                    text = tranController.getCleartext();
                }
                else if(root.getCenter().equals(digiView)){
                    text = digiController.getCleartext();
                }
                else{
                    text = "";
                }


                    FileSaver.saveTextFile(file, text);
            } catch (IOException e) {
                    Dialogs.showErrorDialog(e.getMessage());
            }
        }
    }

    @FXML
    private void saveCiphertext() {
        File file = showSaveDialog();

        if (file != null) {
            try {

                String text;
                if(root.getCenter().equals(breakerView)){
                    text = breakerController.getCiphertext();
                }
                else if(root.getCenter().equals(tranView)){
                    text = tranController.getCiphertext();
                }
                else{
                    text = "";
                }

                    FileSaver.saveTextFile(file, text);
            } catch (IOException e) {
                    Dialogs.showErrorDialog(e.getMessage());
            }
        }
    }

    private File showOpenDialog() {
        return fileChooser.showOpenDialog(root.getScene().getWindow());
    }

    private File showSaveDialog() {
        return fileChooser.showSaveDialog(root.getScene().getWindow());
    }

    @FXML
    private void addSpaces() {
        breakerController.addSpaces();
    }

    @FXML
    private void copyCiphertext() {
        String ciphertext = breakerController.getCiphertext();

        breakerController.pasteCiphertext(ciphertext);
    }

    @FXML
    private void showFrequencies() {
        String cleartext = breakerController.getCleartext();
        String ciphertext = breakerController.getCiphertext();

        try {
                cleartext = Normalizer.normalize(cleartext);
                ciphertext = Normalizer.normalize(ciphertext);
        } catch (CharacterNotSupportedException e) {
                Dialogs.showErrorDialog(e.getMessage());

                return;
        }

        statisticsController.updateStatistics(cleartext, ciphertext);

        statisticsDialog.show();
    }

    @FXML
    private void showAboutDialog() {
        Dialogs.showInformationDialog("About", about);
    }

    @FXML
    private void quit() {
        Platform.exit();
    }
}