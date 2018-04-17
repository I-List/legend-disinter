/*
 * Created for a student project at Penn State
 */
package gui.controllers;

import java.io.IOException;
import breakers.CaesarBreaker;
import breakers.HillBreaker;
import breakers.SimpleSubstitutionBreaker;
import breakers.VigenereBreaker;
import exceptions.CharacterNotSupportedException;
import gui.Dialogs;
import gui.Logger;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.AppProperties;
import utils.WordBreaker;
import net.sourceforge.tess4j.*;
import utils.Languages;

public class DigiController {
	
	// Breakers
	private CaesarBreaker caesarBreaker;
	private SimpleSubstitutionBreaker simpleSubBreaker;
	private VigenereBreaker vigenereBreaker;
	private HillBreaker hillBreaker;
	
	// Other
	private WordBreaker wordBreaker;
	private boolean dictionaryLoaded;

	// References to GUI elements
        @FXML private ImageView sourceImage;
        @FXML private TextArea logTextArea;
	@FXML private TextArea cleartextArea;
	@FXML private ChoiceBox languageBox;
        @FXML private Button runButton;
        @FXML private TabPane optionsPane;
        @FXML private Tab logTab;
        
        private ArrayList<String> langList = new ArrayList<>();
        private String[] langArray = Languages.getLanguageOptionsLong();
	
	@FXML
	public void initialize() {
            this.runButton.setDisable(true);
            //Initialize Language List
            Arrays.sort(langArray);
            for (int i = 0; i < langArray.length; i++){
                langList.add(langArray[i]);
            }
            
            ObservableList<String> languages = FXCollections.observableArrayList(langList);
            languageBox.setItems(languages);
            languageBox.setValue("English");

            // Initialize breakers
            caesarBreaker = new CaesarBreaker();
            simpleSubBreaker = new SimpleSubstitutionBreaker();
            vigenereBreaker = new VigenereBreaker();
            hillBreaker = new HillBreaker();

            // Load dictionary
            //TODO SELECT THE DICTIONARY BASED ON THE LANGUAGE
            try {
                    wordBreaker = new WordBreaker(AppProperties.getProperty("dictionary_eng"));

                    dictionaryLoaded = true;
            } catch (CharacterNotSupportedException | IOException e) {
                    Dialogs.showErrorDialog(e.getMessage());

                    dictionaryLoaded = false;
            }
	}
	
    @FXML
    private void run() {
        optionsPane.getSelectionModel().select(logTab);
        // Initialize Logger
        Logger.setTextArea(logTextArea);
        // Clear any previous logs
        Logger.clear();
        if (getImage() == null){
            Dialogs.showErrorDialog("No source image loaded.");
        }
        else{
            ITesseract instance = new Tesseract();
            Logger.log("Tesseract initialized");
            String lang = null;
            lang = languageBox.getValue().toString();
            instance.setLanguage(Languages.get3Code(lang));
            Logger.log("Language selected: " + lang);
            BufferedImage sample = SwingFXUtils.fromFXImage(getImage(), null);
            Logger.log("Image loaded");
            try{
                Logger.log("Beginning digitization");
                String result = instance.doOCR(sample);
                Logger.log("Digitization complete. Output loading...");
                setCleartext(result);
            } catch (TesseractException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }
        }
    }
	
    public void addSpaces() {
            if (!dictionaryLoaded) {
                    Dialogs.showErrorDialog("Something went wrong when loading the dictionary.");

                    return;
            }

            String cleartext = cleartextArea.getText();

            if (cleartext != null)
                    cleartextArea.setText(wordBreaker.addSpaces(cleartext));
    }

    public Image getImage() {
            return sourceImage.getImage();
    }

    public void setImage(Image image) {
            sourceImage.setImage(image);
    }

    public String getCleartext() {
            return cleartextArea.getText();
    }

    public void setCleartext(String cleartext) {
            cleartextArea.setText(cleartext);
    }
    
    public void activate(){
        this.runButton.setDisable(false);
    }
	
}
