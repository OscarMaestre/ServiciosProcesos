public class UtilidadesFicheros {

    public static BufferedReader getBufferedReader(
        String nombreFichero) throws
    FileNotFoundException {
        FileReader lector;
        lector = new FileReader(nombreFichero);
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(lector);
        return bufferedReader;
    }

    public static PrintWriter getPrintWriter(
        String nombreFichero) throws IOException {
        PrintWriter printWriter;
        FileWriter fileWriter;
        fileWriter = new FileWriter(nombreFichero);
        printWriter = new PrintWriter(fileWriter);
        return printWriter;
        //Fin de getPrintWriter
    }

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
    //Fin de getLineasFichero
}
