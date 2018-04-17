/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package exceptions;

@SuppressWarnings("serial")
public class CharacterNotSupportedException extends Exception {

	public CharacterNotSupportedException(char ch) {
		super("The character " + ch + " is not supported.");
	}

}