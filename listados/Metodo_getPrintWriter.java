public static PrintWriter getPrintWriter(
    String nombreFichero) throws IOException {
    PrintWriter printWriter;
    FileWriter fileWriter;
    fileWriter = new FileWriter(nombreFichero);
    printWriter = new PrintWriter(fileWriter);
    return printWriter;
}
