/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/

package gui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import breakers.Breaker;
import breakers.CaesarBreaker;
import breakers.HillBreaker;
import breakers.SimpleSubstitutionBreaker;
import breakers.VigenereBreaker;
import exceptions.CharacterNotSupportedException;
import gui.Dialogs;
import gui.Logger;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import utils.AppProperties;
import utils.Languages;
import utils.Normalizer;
import utils.TextStatistics;
import utils.WordBreaker;

public class BreakerController {
	
	// Breakers
	private CaesarBreaker caesarBreaker;
	private SimpleSubstitutionBreaker simpleSubBreaker;
	private VigenereBreaker vigenereBreaker;
	private HillBreaker hillBreaker;
	
	// Other
	private WordBreaker wordBreaker;
	private boolean dictionaryLoaded;

	// References to GUI elements
	@FXML private TextArea ciphertextArea;
	@FXML private TextArea cleartextArea;
	@FXML private TextArea logTextArea;
        @FXML private TabPane optionsPane;
        @FXML private Tab logTab;
        
        @FXML private ChoiceBox languageBox;
	@FXML private CheckBox caesarEnabled;
	@FXML private CheckBox simpleSubEnabled;
	@FXML private CheckBox randomRestarts;
	@FXML private CheckBox vigenereEnabled;
	@FXML private CheckBox hillEnabled;
	@FXML private CheckBox knownPlaintextAttack;
        
        
        private ArrayList<String> langList = new ArrayList<>();
        private String[] langArray = Languages.getLanguageOptionsLong();
	
	@FXML
	public void initialize() {
            // Initialize breakers
            caesarBreaker = new CaesarBreaker();
            simpleSubBreaker = new SimpleSubstitutionBreaker();
            vigenereBreaker = new VigenereBreaker();
            hillBreaker = new HillBreaker();

            //Initialize Language Choicebox
            Arrays.sort(langArray);
            for (int i = 0; i < langArray.length; i++){
                langList.add(langArray[i]);
            }
            ObservableList<String> languages = FXCollections.observableArrayList(langList);
            languageBox.setItems(languages);

            // Load dictionary
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
            // Clear previous logs
            Logger.clear();
            if (getCiphertext() == null || getCiphertext().equals("")){
                Dialogs.showErrorDialog("No encrypted text loaded.");
            }
            else{
                // Normalize ciphertext
                String normalized = null;

                try {
                        normalized = Normalizer.normalize(ciphertextArea.getText());
                } catch (CharacterNotSupportedException e) {
                        Dialogs.showErrorDialog(e.getMessage());

                        return;
                }

                ciphertextArea.setText(normalized);

                // Run breakers
                Logger.log("Started");
                Logger.log("Ciphertext is " + normalized.length() + " characters long");

                List<String> results = new ArrayList<>();

                // Compute index of coincidence
                double indexOfCoincidence = TextStatistics.computeIndexOfCoincidence(normalized);

                Logger.log(String.format("Index of coincidence: %.3f", indexOfCoincidence));

                if (TextStatistics.isCloseToExpectedIC(indexOfCoincidence)) {
                        Logger.log("A monoalphabetic cipher has been used");

                        // Caesar breaker
                        if (caesarEnabled.isSelected()) {
                                Logger.log("Assuming that a Caesar cipher has been used");

                                runBreaker(caesarBreaker, normalized, results);
                        }

                        // Simple substitution breaker
                        if (simpleSubEnabled.isSelected()) {
                                Logger.log("Assuming that a simple substitution cipher has been used");

                                simpleSubBreaker.enableRandomRestarts(randomRestarts.isSelected());

                                runBreaker(simpleSubBreaker, normalized, results);
                        }

                } else {
                        Logger.log("A polyalphabetic cipher has been used");

                        // Vigenere breaker
                        if (vigenereEnabled.isSelected()) {
                                Logger.log("Assuming that a Vigenère cipher has been used");

                                runBreaker(vigenereBreaker, normalized, results);
                        }

                        // Hill breaker
                        if (hillEnabled.isSelected()) {
                                Logger.log("Assuming that a Hill cipher has been used");

                                // Set crib
                                String crib = null;

                                if (knownPlaintextAttack.isSelected()) {
                                        crib = Dialogs.showTextInputDialog("Known-plaintext attack", "Enter a 5-letter crib:");

                                        // If input is invalid
                                        if (crib == null || crib.length() < 5)
                                                crib = hillBreaker.getDefaultCrib();

                                } else {
                                        crib = hillBreaker.getDefaultCrib();
                                }

                                // Crib is not null
                                try {
                                        String normalizedCrib = Normalizer.normalize(crib);

                                        hillBreaker.setCrib(normalizedCrib);

                                        runBreaker(hillBreaker, normalized, results);
                                } catch (CharacterNotSupportedException e) {
                                        Dialogs.showErrorDialog(e.getMessage());
                                }
                        }

                }

                // Find best candidate
                String bestCandidate = TextStatistics.findBestCandidate(results);

                cleartextArea.setText(bestCandidate);

                Logger.log("Finished");
            }
    }

    private void runBreaker(Breaker breaker, String message, List<String> results) {
            String result = breaker.recover(message);

            if (result != null)
                    results.add(result);
    }

    public void addSpaces() {
        if (!dictionaryLoaded) {
                Dialogs.showErrorDialog("Something went wrong when loading the dictionary.");

                return;
        }

        String cleartext = this.cleartextArea.getText();

        if (cleartext != null){
            this.cleartextArea.setText(wordBreaker.addSpaces(cleartext));
        }
    }

    public void pasteCiphertext(String ciphertext) {
            ciphertextArea.setText(ciphertext);
    }

    public String getCleartext() {
            return cleartextArea.getText();
    }

    public void setCleartext(String cleartext) {
            cleartextArea.setText(cleartext);
    }

    public String getCiphertext() {
            return ciphertextArea.getText();
    }

    public void setCiphertext(String ciphertext) {
            ciphertextArea.setText(ciphertext);
    }
	
}
