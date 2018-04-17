/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package keys;

public class Shift implements Key {

	private int shift;
	
	public Shift(int shift) {
		this.shift = shift;
	}
	
	@Override
	public Object getValue() {
		return new Integer(shift);
	}

}
