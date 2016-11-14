public static void main(String[] args) throws
IOException {
    String nombreFichero = args[0];
    ArrayList<String> cantidades;
    try {
        //Extraemos las cantidades
        cantidades = UtilidadesFicheros.getLineasFichero(nombreFichero);
        //Y las sumamos una por una
        for (String lineaCantidad : cantidades) {
        }
    }//Fin del try
    catch (IOException e) {
        System.err.println("No se pudo procesar el fichero "
        + nombreFichero);
    }
//Fin del main
}
