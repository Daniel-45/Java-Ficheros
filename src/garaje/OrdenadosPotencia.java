package garaje;

import java.util.Comparator;

public class OrdenadosPotencia implements Comparator<Vehiculo> {

	@Override
	public int compare(Vehiculo v1, Vehiculo v2) {
		// TODO Auto-generated method stub
		return v1.getPotencia() - v2.getPotencia();
	}
}
