public class AreasEnParalelo {

    public static void main(String[] args) throws
    InterruptedException {
        CalculadorAreas ca = new CalculadorAreas(1, 2);
        final int MAX_HILOS = 10000;
        Thread[] hilos = new Thread[MAX_HILOS];
        for (int i = 0; i < MAX_HILOS; i++) {
            hilos[i] = new Thread(ca);
            hilos[i].start();
        }
        for (int i = 0; i < MAX_HILOS; i++) {
            hilos[i].join();
        }
        System.out.println("Total de calculos:" + ca.contador);
    }
}
