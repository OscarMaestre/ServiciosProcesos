//Fin del m�todo hacerRecuento
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
    String nombreFicheroEntrada = args[0];
    String letra = args[1];
    String nombreFicheroResultado = args[2];
    hacerRecuento(nombreFicheroEntrada, letra, nombreFicheroResultado);
}
