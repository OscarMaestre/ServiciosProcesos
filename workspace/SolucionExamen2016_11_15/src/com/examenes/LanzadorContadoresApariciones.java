package com.examenes;

import java.io.File;
import java.io.IOException;

public class LanzadorContadoresApariciones {
	private static String EXTENSION_ERRORES=".err";
	public static void main(String[] args) throws IOException{
		String palabraParaBuscar=args[0];
		
		//Se lanzan los procesos...
		ProcessBuilder[] constructores;
		constructores=new ProcessBuilder[args.length-1];
		//..pero saltando la posición 0 (la palabra)
		for (int i=1; i<args.length; i++){
			
			
			String ficheroDondeBuscar;
			ficheroDondeBuscar=args[i];
			
			String ficheroDeErrores;
			ficheroDeErrores=ficheroDondeBuscar + EXTENSION_ERRORES;
			
			//Se prepara lo necesario para lanzar el proceso...
			constructores[i-1]=new ProcessBuilder();
			constructores[i-1].command(
					"java","com.examenes.ContadorAparicionesPalabra",
					palabraParaBuscar, ficheroDondeBuscar);
			constructores[i-1].redirectError(
					new File(ficheroDeErrores) );
			//..y se ejecuta
			constructores[i-1].start();
		}
		//Procesar los ficheros de resultados.
		
	}
}
