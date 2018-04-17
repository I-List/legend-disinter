/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package breakers;

import gui.Logger;

public abstract class Breaker {

	abstract public String recover(String message);
	
	protected void log(String message) {
		Logger.log(String.format("[%s]%n%s", toString(), message));
	}
	
}
