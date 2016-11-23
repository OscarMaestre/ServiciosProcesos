package com.examenes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContadorAparicionesPalabra {
	private static String EXTENSION_RESULTADOS=".res";
	
	/* Dada una subcadena, nos dice cuantas veces aparece la subcadena
	 * en dicha cadena. Usaremos el metodo String.indexOf para
	 * ir buscando todas las apariciones de la subcadena
	 */
	public static long contarApariciones(String cadena, String subcadena){
		int posBusqueda=0;
		int posAparicion;
		long apariciones=0;
		posBusqueda=0;
		while (posBusqueda<cadena.length()){
			posAparicion=cadena.indexOf(subcadena, posBusqueda);
			if (posAparicion!=-1){
				apariciones++;
				posBusqueda=posAparicion+1;
			}
			else {
				posBusqueda++;
			}
		}
		return apariciones;
	}
	/* Dados una palabra (argumento 0) 
	 * y un fichero (argumento 1) se 
	 * contabiliza el total de apariciones
	 * de esa palabra en el fichero y se almacena
	 * en un fichero con extensión ".res". Observar
	 * que la extensión se añade al fichero, el cual
	 * puede acabar teniendo 2 extensiones (como
	 * por ejemplo "fichero1.txt.res" */
	public static void main(String[] args) throws IOException {
		long aparicionesDeLaPalabra	=0;
		String palabraParaBuscar	=args[0];
		String nombreFichero		=args[1];
		//Recuperamos las líneas del fichero
		ArrayList<String> lineas;
		lineas=UtilidadesFicheros.getLineasFichero(nombreFichero);
		//Vamos recorriendo las líneas...
		for (String linea : lineas) {

			//Y si encontramos la palabra...
			if ( linea.contains( palabraParaBuscar ) ) {
				//..incrementamos el contador
				aparicionesDeLaPalabra++;
			}
			//Fin del for que recorre las lineas
		}
		//Ya tenemos la cantidad de apariciones
		//así que almacenamos los resultados
		PrintWriter pw;
		pw=UtilidadesFicheros.getPrintWriter(
				nombreFichero + EXTENSION_RESULTADOS);
		
		pw.println(nombreFichero+":"+
					palabraParaBuscar+":"+
					aparicionesDeLaPalabra);
		pw.close();
		

	}

}
