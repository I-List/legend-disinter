/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package exceptions;

@SuppressWarnings("serial")
public class CharacterNotInAlphabetException extends RuntimeException {

	public CharacterNotInAlphabetException(char ch) {
		super("The alphabet in use does not contain " + ch + ".");
	}
	
}
