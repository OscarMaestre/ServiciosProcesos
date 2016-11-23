//Fin de getLineasFichero
public static long getSuma(String[] listaNombresFichero) {
    long suma = 0;
    ArrayList<String> lineas;
    String lineaCantidad;
    long cantidad;
    for (String nombreFichero : listaNombresFichero) {
        try {
            //Recuperamos todas las lineas
            lineas = getLineasFichero(nombreFichero);
            //Pero solo nos interesa la primera
            lineaCantidad = lineas.get(0);
            //Convertimos la linea a número
            cantidad = Long.parseLong(lineaCantidad);
            //Y se incrementa la suma total
            suma = suma + cantidad;
        } catch (IOException e) {
            System.err.println("Fallo al procesar el fichero " + nombreFichero);
        //Fin del catch
        }
    //Fin del for que recorre los nombres de fichero
    }
    return suma;
}
