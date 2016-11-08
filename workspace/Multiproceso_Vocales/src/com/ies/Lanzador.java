package com.ies;

import java.io.File;
import java.io.IOException;

public class Lanzador {
	public static 
		void main(String[] args) 
				throws IOException{
		String ficheroEntrada;
		ficheroEntrada=args[0];
		String[] vocales={
		  "A", "E", "I" , "O", "U"
		};
		File dir;
		dir =new File(
		"C:\\Users\\ogomez\\workspace\\Multiproceso_Vocales\\bin"
				);
		/* Se lanzan los procesos*/
		for (int i=0;
				i<vocales.length;
				i++)
		{
		  ProcessBuilder pb;
		  pb=new ProcessBuilder(
    	    "java",
    	  "com.ies.ProcesadorFichero",
		    ficheroEntrada,
		    vocales[i], 
		    vocales[i]+".txt");
		  pb.directory(dir);
		  pb.start();			
		} //Fin del for
		
		/* Esperamos un poco*/
		
		/* Recogemos los resultados */
		
	}
}
