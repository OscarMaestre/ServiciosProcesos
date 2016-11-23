/**
	 * Dado un fichero pasado como argumento, contará cuantas
	 * apariciones hay de una cierta vocal (pasada como argumento)
	 * y dejará la cantidad en otro fichero (también pasado como
	 * argumento)
	 * @throws IOException 
	 * @throws FileNotFoundException  */
public static void main(String[] args) throws FileNotFoundException, IOException {
    String nombreFicheroEntrada = args[0];
    String letra = args[1];
    String nombreFicheroResultado = args[2];
    hacerRecuento(nombreFicheroEntrada, letra, nombreFicheroResultado);
//Fin del main
}
