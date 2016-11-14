public static ArrayList<String> getLineasFichero(
    String nombreFichero) throws IOException {
    ArrayList<String> lineas = new ArrayList<String>();
    BufferedReader bfr = getBufferedReader(nombreFichero);
    //Leemos líneas del fichero...
    String linea = bfr.readLine();
    while (linea != null) {
        //Y las añadimos al array
        lineas.add(linea);
        linea = bfr.readLine();
    }
    //Fin del bucle que lee líneas
    return lineas;
}
