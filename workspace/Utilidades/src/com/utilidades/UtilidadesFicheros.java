package com.utilidades;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UtilidadesFicheros {
	public static BufferedReader
		getBufferedReader(
			String nombreFichero) 
				throws 
				FileNotFoundException
	{
		FileReader lector;
		lector=new FileReader(
				nombreFichero);
		
		BufferedReader bfr;
		bfr=new BufferedReader(
				lector);
		return bfr;
	} //Fin del método
	public static PrintWriter
		getPrintWriter(
				String nombreFichero) 
					throws IOException
	{
		PrintWriter pw;
		FileWriter fw;
		fw=new FileWriter(nombreFichero);
		pw=new PrintWriter(fw);
		return pw;
		
	} //Fin de getPrintWriter
		
}
