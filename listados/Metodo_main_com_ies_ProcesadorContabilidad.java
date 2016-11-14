public static void main(String[] args) throws
IOException {
    String nombreFichero = args[0];
    String nombreFicheroResultado = args[1];
    ArrayList<String> cantidades;
    long total = 0;
    try {
        //Extraemos las cantidades
        cantidades = UtilidadesFicheros.getLineasFichero(nombreFichero);
        //Y las sumamos una por una
        for (String lineaCantidad : cantidades) {
            long cantidad = Long.parseLong(lineaCantidad);
            total = total + cantidad;
            //Fin del for que recorre las cantidades
        }
        //Almacenamos el total en un fichero
        PrintWriter pw;
        pw = UtilidadesFicheros.getPrintWriter(nombreFicheroResultado);
        pw.println(total);
        pw.close();
    }//Fin del try
    catch (IOException e) {
        System.err.println("No se pudo procesar el fichero "
        + nombreFichero);
        e.printStackTrace();
    }
//Fin del main
}
