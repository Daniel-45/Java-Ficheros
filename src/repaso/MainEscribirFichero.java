package repaso;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import daw.com.Teclado;

public class MainEscribirFichero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> texto = new ArrayList<String>();
		String linea;

		do {

			linea = Teclado.leerString(">");

			if(!linea.equals("fin")) {
				// Guarda en memoria la línea introducida por el usuario
				texto.add(linea);
			}

		}while(!linea.equalsIgnoreCase("Fin"));

		try(FileWriter bruto = new FileWriter("files/aristofanes.txt");
				BufferedWriter buffer = new BufferedWriter(bruto);
				PrintWriter escritor = new PrintWriter(buffer);) {

			for(String l : texto) {

				escritor.println(l);

			}

		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}

	}
}
