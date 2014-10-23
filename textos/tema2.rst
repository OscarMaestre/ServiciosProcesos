==========================
Programación multihilo
==========================

Recursos compartidos por los hilos.
----------------------------------------------------------------
Cuando creamos varios objetos de una clase, puede ocurrir que varios hilos de ejecución accedan a un objeto. Es importante recordar que **todos los campos del objeto son compartidos entre todos los hilos**.

Supongamos una clase como esta:

.. code-block:: java

	public class Empleado(){
		int numHorasTrabajadas=0;
		public void int incrementarHoras(){
			numHorasTrabajadas++;
		}
	}


Si varios hilos ejecutan sin querer el método ``incrementar`` ocurrirá que el número se incrementará tantas veces como procesos.


Estados de un hilo. Cambios de estado.
--------------------------------------------------------------------------


Aunque no lo vemos, un hilo cambia de estado: puede pasar de la nada a la ejecución. De la ejecución al estado "en espera". De ahí puede volver a estar en ejecución. De cualquier estado se puede pasar al estado "finalizado".

El programador no necesita controlar esto, lo hace el sistema operativo. Sin embargo un programa multihilo mal hecho puede dar lugar problemas como los siguientes:

* Interbloqueo. Se produce cuando las peticiones y las esperas se entrelazan de forma que ningún proceso puede avanzar.

* Inanición. Ningún proceso consigue hacer ninguna tarea útil y por lo tanto hay que esperar a que el administrador del sistema detecte el interbloqueo y mate procesos (o hasta que alguien reinicie el equipo).

Elementos relacionados con la programación de hilos. Librerías y clases.
--------------------------------------------------------------------------

Para crear programas multihilo en Java se pueden hacer dos cosas:

1. Heredar de la clase Thread.
2. Implementar la interfaz Runnable.

Los documentos de Java aconsejan el segundo. Lo único que hay que hacer es algo como esto.

.. code-block:: java


	class EjecutorTareaCompleja implements Runnable{
		private String nombre;
		int numEjecucion;
		public EjecutorTareaCompleja(String nombre){
			this.nombre=nombre;
		}
		@Override
		public void run() {
			String cad;
			while (numEjecucion<100){
				for (double i=0; i<4999.99; i=i+0.04)
				{
					Math.sqrt(i);			
				}
				cad="Soy el hilo "+this.nombre;
				cad+=" y mi valor de i es "+numEjecucion;
				System.out.println(cad);
				numEjecucion++;
			}		
		}
		
	}
	public class LanzaHilos {

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			int NUM_HILOS=500;
			EjecutorTareaCompleja op;
			for (int i=0; i<NUM_HILOS; i++)
			{
				op=new EjecutorTareaCompleja ("Operacion "+i);
				Thread hilo=new Thread(op);
				hilo.start();
			}
		}
	}



.. WARNING::
   Este código tiene un problema **muy grave** y es que no se controla el acceso a variables compartidas, es decir **HAY UNA SECCIÓN CRÍTICA QUE NO ESTÁ PROTEGIDA** por lo que el resultado de la ejecución no muestra ningún sentido aunque el programa esté bien.


Gestión de hilos.
--------------------------------------------------------------------------
Con los hilos se pueden efectuar diversas operaciones que sean de utilidad al programador (y al administrador de sistemas a veces).

Por ejemplo, un hilo puede tener un nombre. Si queremos asignar un nombre a un hilo podemos usar el método ``setName("Nombre que sea")``. También podemos obtener un objeto que represente el hilo de ejecución con ``getCurrentThread`` que nos devolverá un objeto de la clase ``Thread``.

Otra operación de utilidad al gestionar hilos es indicar la prioridad que queremos darle a un hilo. En realidad esta prioridad es indicativa, el sistema operativo no está obligado a respetarla aunque por lo general lo hacen. Se puede indicar la prioridad con ``setPriority(10)``. La máxima prioridad posible es ``MAX_PRIORITY``, y la mínima es ``MIN_PRIORITY``.

Cuando lanzamos una operación también podemos usar el método ``Thread.sleep(numero)`` y poner nuestro hilo "a dormir".

Cuando se trabaja con prioridades en hilos **no hay garantías de que un hilo termine cuando esperemos**.

Podemos terminar un hilo de ejecución llamando al método ``join``. Este método devuelve el control al hilo principal que lanzó el hilo secundario con la posibilidad de elegir un tiempo de espera en milisegundos.

El siguiente programa ilustra el uso de estos métodos:

.. code-block:: java

	class Calculador implements Runnable{
		@Override
		public void run() {
			int num=0;
			while(num<5){
				System.out.println("Calculando...");
				try {
					long tiempo=(long) (1000*Math.random()*10);
					if (tiempo>8000){
						Thread hilo=Thread.currentThread();
						System.out.println(
								"Terminando hilo:"+
										hilo.getName()
						);
						hilo.join();
					}
					Thread.sleep(tiempo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Calculado y reiniciando.");
				num++;
			}
			Thread hilo=Thread.currentThread();
			String miNombre=hilo.getName();
			System.out.println("Hilo terminado:"+miNombre);	
		}
	}

	public class LanzadorHilos {
		public static void main(String[] args) {
			Calculador vHilos[]=new Calculador[5];
			for (int i=0; i<5;i++){
				vHilos[i]=new Calculador();
				Thread hilo=new Thread(vHilos[i]);
				hilo.setName("Hilo "+i);
				if (i==0){
					hilo.setPriority(Thread.MAX_PRIORITY);
				}
				hilo.start();
			}
		}
	}
	
Ejercicio: crear un programa que lance 10 hilos de ejecución donde a cada hilo se le pasará la base y la altura de un triángulo, y cada hilo ejecutará el cálculo del área de dicho triángulo informando de qué base y qué altura recibió y cual es el área resultado.















Creación, inicio y finalización.
--------------------------------------------------------------------------

Sincronización de hilos.
--------------------------------------------------------------------------

Información entre hilos. Intercambio.
--------------------------------------------------------------------------

Prioridades de los hilos.
--------------------------------------------------------------------------

Gestión de prioridades.
--------------------------------------------------------------------------

Compartición de información entre hilos.
--------------------------------------------------------------------------

Programación de aplicaciones multihilo.
--------------------------------------------------------------------------

Documentación.
--------------------------------------------------------------------------

Depuración.
--------------------------------------------------------------------------
