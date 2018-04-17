/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package keys;

public class SubstitutionAlphabet implements Key {
	
	private String substitutionAlphabet;

	public SubstitutionAlphabet(String substitutionAlphabet) {
		this.substitutionAlphabet = substitutionAlphabet;
	}
	
	@Override
	public Object getValue() {
		return substitutionAlphabet;
	}

}
