package escalada;

import java.util.Comparator;

import escalada.Via;

/**
 * 
 * @author Daniel
 *
 */

public class OrdenaGrado implements Comparator<Via> {

	@Override
	public int compare(Via v1, Via v2) {
		return Integer.compare(v1.getGrado(), v2.getGrado());
	}

}
