/*
*Original author: Luca Campese
*Modified with permission by Isaiah List
*/
package keys;

public class Keyword implements Key {

	private String keyword;
	
	public Keyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public Object getValue() {
		return keyword;
	}

}
