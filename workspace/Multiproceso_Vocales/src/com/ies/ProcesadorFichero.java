package com.ies;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.utilidades.UtilidadesFicheros;




public class ProcesadorFichero {
	
	// Dado un fichero de entrada y una letra
	// contamos cuantas veces aparece dicha letra
	// y dejamos el recuento en un fichero de salida
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
		//Mientras no queden lineas....
		while (lineaLeida!=null){
			//...recorremos la linea...
			for (int i=0; 
					i<lineaLeida.length();
					i++){
				char letraLeida=
						lineaLeida.charAt(i);
				char letraPasada=
						letra.charAt(0);
				//...y si la encontramos
				// incrementamos el contador
				if (letraLeida==letraPasada){
					totalVocales++;
				} //Fin del if
			} //Fin del for
			// Pasamos a la siguiente linea
			lineaLeida=br.readLine();
		}
		//Escribimos el total de vocales
		//en el fichero de salida
		pw.println(totalVocales);
		pw.flush();
		//Y cerramos los ficheros
		pw.close();
		br.close();
		
	} //Fin del m�todo hacerRecuento
	/**
	 * Dado un fichero pasado como
	 * argumento, contar� cuantas
	 * apariciones hay de una cierta
	 * vocal (pasada como argumento)
	 * y dejar� la cantidad
	 * en otro fichero (tambi�n pasado como
	 * argumento
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String nombreFicheroEntrada		=	args[0];
		String letra					=	args[1];
		String nombreFicheroResultado	=	args[2];
		hacerRecuento(nombreFicheroEntrada,
				letra,
				nombreFicheroResultado);
	} //Fin del main

}
