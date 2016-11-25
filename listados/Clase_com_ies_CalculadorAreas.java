class CalculadorAreas implements Runnable {

    float base, altura, area;

    public int contador = 0;

    public CalculadorAreas(float b, float a) {
        this.base = b;
        this.altura = a;
    }

    public synchronized void incrementarContador() {
        contador = contador + 1;
    }

    @Override
    public void run() {
        Random generador;
        generador = new Random();
        area = base * altura / 2;
        try {
            Thread.sleep(generador.nextInt(650));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.incrementarContador();
    }
}
