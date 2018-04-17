/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package exceptions;

@SuppressWarnings("serial")
public class InvalidKeyException extends Exception {

	public InvalidKeyException() {
		super("The key you have entered is invalid.");
	}
	
	public InvalidKeyException(String message) {
		super(message);
	}
	
}
