/*
 * Created for a student project at Penn State
 */
package gui.controllers;

import breakers.CaesarBreaker;
import breakers.HillBreaker;
import breakers.SimpleSubstitutionBreaker;
import breakers.VigenereBreaker;
import gui.Dialogs;
import gui.Logger;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import utils.WordBreaker;
import utils.Translate;

public class TranController {
	
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
	
	
	@FXML
	public void initialize() {

	}
	
	@FXML
	private void run() {
            // Initialize Logger
            Logger.setTextArea(logTextArea);
            // Clear previous logs
            Logger.clear();
            
            if (getCiphertext() == null || getCiphertext().equals("")){
                Dialogs.showErrorDialog("No Source text loaded.");
            }
            else {
                try
                  {
                    Logger.log("Initializing translation.");
                    String transText = Translate.Translate(getCiphertext());
                    Logger.log("Translation complete.");
                    setCleartext(transText);
                  } catch ( Exception ex )
                  {
                    System.out.println(ex.getMessage());
                    java.util.logging.Logger.getLogger(TranController.class.getName()).log(Level.SEVERE, null, ex);
                  }
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
