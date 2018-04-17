/**
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package utils;

import gui.Dialogs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.image.Image;

public final class FileLoader {
	
	// Prevents instantiation
	private FileLoader() { }
	
	public static String readTextFile(File file) throws IOException {
		StringBuilder result = new StringBuilder();
		
		try (BufferedReader input = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = input.readLine()) != null) {
				result.append(String.format("%s%n", line));
			}
		}
		
		return result.toString();
	}
        
        public static Image readImageFile(File file) throws IOException {
            Image inPic = null;
            try  
            {
                FileInputStream inFile = new FileInputStream(file);
                inPic = new Image(inFile);
            } catch(IOException e) {
			Dialogs.showErrorDialog(e.getMessage());
            }
		
		return inPic;
	}
	
}
