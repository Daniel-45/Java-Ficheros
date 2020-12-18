package repaso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainLeerFichero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> contenido = new ArrayList<String>();

		try(FileReader bruto = new FileReader("files/aristofanes.txt");
				BufferedReader buffer = new BufferedReader(bruto);) {
			
			while(buffer.ready()) {
				contenido.add(buffer.readLine());
			}

		}catch(IOException e) {
			System.out.println(e.getMessage());
		}

		for(String l : contenido)
			System.out.println(l + "\n");

	}
}
