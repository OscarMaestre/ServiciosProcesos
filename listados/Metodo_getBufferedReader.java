public static BufferedReader getBufferedReader(
    String nombreFichero) throws FileNotFoundException {
    FileReader lector;
    lector = new FileReader(nombreFichero);
    BufferedReader bufferedReader;
    bufferedReader = new BufferedReader(lector);
    return bufferedReader;
}
