package com.ies;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.utilidades.UtilidadesFicheros;




public class ProcesadorFichero {

	public static void hacerRecuento(
		String fichEntrada,String letra,
		String fichSalida
			) throws 
				FileNotFoundException,
				IOException
	{
		BufferedReader br;
		br=UtilidadesFicheros.getBufferedReader(
				fichEntrada);
		PrintWriter pw;
		pw=UtilidadesFicheros.getPrintWriter(
				fichSalida);
		String lineaLeida;
		lineaLeida=br.readLine();
		int totalVocales=0;
		while (lineaLeida!=null){
			for (int i=0; 
					i<lineaLeida.length();
					i++){
				char letraLeida=
						lineaLeida.charAt(i);
				char letraPasada=
						letra.charAt(0);
				if (letraLeida==letraPasada){
					totalVocales++;
				}
			}
			lineaLeida=br.readLine();
		}
		//Escribimos el total de vocales
		//en el fichero de salida
		pw.println(totalVocales);
		pw.flush();
		pw.close();
		br.close();
		
	} //Fin del método hacerRecuento
	/**
	 * Dado un fichero pasado como
	 * argumento, contará cuantas
	 * apariciones hay de una cierta
	 * vocal (pasada como argumento)
	 * y dejará la cantidad
	 * en otro fichero (también pasado como
	 * argumento
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String nombreFicheroEntrada=
				args[0];
		System.out.println(
				"Fichero entrada:"+
				nombreFicheroEntrada);
		String letra=args[1];
		System.out.println("Letra:"+letra);
		String nombreFicheroResultado=
				args[2];
		System.out.println("Resultados en:"+
				nombreFicheroResultado);
		String fichEntrada;
		hacerRecuento(
				nombreFicheroEntrada,
				letra,
				nombreFicheroResultado);

	}

}
