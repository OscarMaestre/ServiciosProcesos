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

Una posibilidad (quizá incorrecta) sería esta:

.. code-block:: java

	package com.ies;

	import java.util.Random;

	class CalculadorAreas implements Runnable{
		int base, altura;
		public CalculadorAreas(int base, int altura){
			this.base=base;
			this.altura=altura;
		}
		@Override
		public void run() {
			float area=this.base*this.altura/2;
			System.out.print("Base:"+this.base);
			System.out.print("Altura:"+this.altura);
			System.out.println("Area:"+area);
		}
		
	}
	public class AreasEnParalelo {

		public static void main(String[] args) {
			Random generador=new Random();
			int numHilos=10000;
			int baseMaxima=3;
			int alturaMaxima=5;
			for (int i=0; i<numHilos; i++){
				//Sumamos 1 para evitar casos como base=0
				int base=1+generador.nextInt(baseMaxima);
				int altura=1+generador.nextInt(alturaMaxima);
				CalculadorAreas ca=
						new CalculadorAreas(base, altura);
				Thread hiloAsociado=new Thread(ca);
				hiloAsociado.start();
			}
		}
	}
	
Las secciones siguientes sirven como resumen de como crear una aplicación multihilo


Creación, inicio y finalización.
--------------------------------------------------------------------------

* Podemos heredar de Thread o implementar ``Runnable``. Usaremos el segundo recordando implementar el método ``public void run()``.

* Para crear un hilo asociado a un objeto usaremos algo como:

.. code-block:: java

	Thread hilo=new Thread(objetoDeClase)
	
Lo más habitual es guardar en un vector todos los hilos que hagan algo, y no en un objeto suelto.

* Cada objeto que tenga un hilo asociado debe iniciarse así:

.. code-block:: java

	hilo.start();
	
	
* Todo programa multihilo tiene un "hilo principal", el cual deberá esperar a que terminen los hilos asociados ejecutando el método ``join()``	.


Sincronización de hilos.
--------------------------------------------------------------------------

Cuando un método acceda a una variable miembro que esté compartida deberemos proteger dicha sección crítica, usando ``synchronized``. Se puede poner todo el método ``synchronized`` o marcar un trozo de código más pequeño.

Información entre hilos.
------------------------------------------------------------------------

Todos los hilos comparten todo, así que obtener información es tan sencillo como consultar un miembro.
En realidad podemos comunicar los hilos con otro mecanismo llamado ``sockets de red``, pero se ve en el tema siguiente.



Prioridades de los hilos.
--------------------------------------------------------------------------

Podemos asignar distintas prioridades a los hilos usando los campos estáticos ``MAX_PRIORITY`` y ``MIN_PRIORITY``. Usando valores entre estas dos constantes podemos hacer que un hilo reciba más procesador que otro (se hace en contadas ocasiones).

Para ello se usa el método ``setPriority(valor)``


Gestión de prioridades.
--------------------------------------------------------------------------

En realidad un sistema operativo no está obligado a respetar las prioridades, sino que se lo tomará como "recomendaciones". En general hasta ahora todos respetan hasta cierto punto las prioridades que pone el programador pero no debe tomarse como algo absoluto.




Programación de aplicaciones multihilo.
--------------------------------------------------------------------------

La estructura típica de un programa multihilo es esta:

.. code-block:: java

	class TareaCompleja implements Runnable{
		@Override
		public void run() {
			for (int i=0; i<100;i++){
				int a=i*3;
			}
			Thread hiloActual=Thread.currentThread();
			String miNombre=hiloActual.getName();
			System.out.println(
					"Finalizado el hilo"+miNombre);
		}
	}
	public class LanzadorHilos {
		public static void main(String[] args) {
			int NUM_HILOS=100;
			Thread[] hilosAsociados;
			
			hilosAsociados=new Thread[NUM_HILOS];
			for (int i=0;i<NUM_HILOS;i++){
				TareaCompleja t=new TareaCompleja();
				Thread hilo=new Thread(t);
				hilo.setName("Hilo: "+i);
				hilo.start();
				hilosAsociados[i]=hilo;
			}
			
			/* Despues de crear todo, nos aseguramos
			 * de esperar que todos los hilos acaben. */
			
			for (int i=0; i<NUM_HILOS; i++){
				Thread hilo=hilosAsociados[i];
				try {
					//Espera a que el hilo acabe
					hilo.join();
				} catch (InterruptedException e) {
					System.out.print("Algun hilo acabó ");
					System.out.println(" antes de tiempo!");
				}
			}
			System.out.println("El principal ha terminado");
		}
	}
	
Supongamos que la tarea es más compleja y que el bucle se ejecuta un número al azar de veces. Esto significaría que nuestro bucle es algo como esto:

.. code-block:: java

		Random generador= new Random();
		int numAzar=(1+generador.nextInt(5))*100;
		for (int i=0; i<numAzar;i++){
			int a=i*3;
		}	

¿Como podríamos modificar el programa para que podamos saber cuantas multiplicaciones se han hecho en total entre todos los hilos?

Aquí entra el problema de la sincronización. Supongamos una clase contador muy simple como esta:

.. code-block:: java

	class Contador{
		int cuenta;
		public Contador(){
			cuenta=0;
		}
		public void incrementar(){
			cuenta=cuenta+1;
		}
		public int getCuenta(){
			return cuenta;
		}
	}
	
**SI EL OBJETO CONTADOR SE COMPARTE ENTRE VARIOS HILOS LA CUENTA FINAL RESULTANTE ES MUY POSIBLE QUE ESTÉ MAL***

Esta clase debería tener protegidas sus secciones críticas

.. code-block:: java

	class Contador{
		int cuenta;
		public Contador(){
			cuenta=0;
		}
		public synchronized void incrementar(){
			cuenta=cuenta+1;
		}
		public synchronized int getCuenta(){
			return cuenta;
		}
	}
	
Se puede aprovechar todavía más rendimiento si en un método marcamos como sección crítica (o sincronizada) exclusivamente el código peligroso:

.. code-block:: java

	public  void incrementar(){
		System.out.println("Otras cosas");
		synchronized(this){
			cuenta=cuenta+1;
		}
		System.out.println("Mas cosas...");
		synchronized(this){
			if (cuenta>300){
				System.out.println("Este hilo trabaja mucho");
			}
		}	
	}

Problema
------------------------------------------------------

En una mesa hay procesos que simulan el comportamiento de unos filósofos que intentan comer de un plato. Cada filósofo tiene un cubierto a su izquierda y uno a su derecha y para poder comer tiene que conseguir los dos. Si lo consigue, mostrará un mensaje en pantalla que indique "Filosofo 2 comiendo".

Despues de comer, soltará los cubiertos y esperará al azar un tiempo entre 1000 y 5000 milisegundos, indicando por pantalla "El filósofo 2 está pensando".

En general todos los objetos de la clase Filósofo está en un bucle infinito dedicándose a comer y a pensar.

Simular este problema en un programa Java que muestre el progreso de todos sin caer en problemas de sincronización ni de inanición.

.. figure:: ../imagenes/Filosofos.png
   :figwidth: 50%
   :align: center
   
   Esquema de los filósofos
   
   
Boceto de solución
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	import java.util.Random;


	public class Filosofo  implements Runnable{
		public void run(){
			String miNombre=Thread.currentThread().getName();
			Random generador=new Random();
			while (true){
				/* Comer*/		
				/* Intentar coger palillos*/
				/* Si los coge:*/
				System.out.println(miNombre+" comiendo...");
				int milisegs=(1+generador.nextInt(5))*1000;
				esperarTiempoAzar(miNombre, milisegs);
				/* Pensando...*/
				//Recordemos soltar los palillos
				System.out.println(miNombre+"  pensando...");				milisegs=(1+generador.nextInt(5))*1000;
				esperarTiempoAzar(miNombre, milisegs);
			}
		}

	private void esperarTiempoAzar(String miNombre, int milisegs) {
			try {
				Thread.sleep(milisegs);
			} catch (InterruptedException e) {
				System.out.println(
						miNombre+" interrumpido!!. Saliendo...");
				return ;
			}
		}
	}
	
   


Solución completa al problema de los filósofos
------------------------------------------------------

Gestor de recursos compartidos (palillos)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
.. code-block:: java

	public class GestorPalillos {
		/* False significa que no están cogidos*/
		private boolean[] palillos;
		public GestorPalillos(int num_filosofos){
			palillos=new boolean[num_filosofos];
			for (int i=0;i<palillos.length;i++){
				palillos[i]=false;
			}
		}
		public synchronized boolean 
			sePuedenCogerAmbosPalillos(
				int num1,int num2){
			if ( (palillos[num1]==false) &&
				(palillos[num2]==false) ) {
				palillos[num1]=true;
				palillos[num2]=true;
				System.out.println(
						"Alguien consiguio los palillos "+num1+" y "+num2);
				return true;
			}
			return false;
		}
		public synchronized void soltarPalillos(int num1, int num2){
			palillos[num1]=false;
			palillos[num2]=false;
			System.out.println(
					"Alguien liberó los palillos "+num1+" y "+num2);
		}	
	}
	
Simulación de un filósofo
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
.. code-block:: java

			
	import java.util.Random;
	public class Filosofo  implements Runnable{
		int num_palillo_izq;
		int num_palillo_der;
		GestorPalillos gestorPalillos;
		public Filosofo(GestorPalillos gp,
				int p_izq, int p_der){
			this.gestorPalillos=gp;
			this.num_palillo_der=p_der;
			this.num_palillo_izq=p_izq;
		}
		public void run(){
			String miNombre=Thread.currentThread().getName();
			Random generador=new Random();
			while (true){
				/* Comer*/		
				/* Intentar coger palillos*/
				while(!gestorPalillos.sePuedenCogerAmbosPalillos
						(
								num_palillo_izq, num_palillo_der
						))
				{
					
				}
				/* Si los coge:*/
				
				int milisegs=(1+generador.nextInt(5))*1000;
				esperarTiempoAzar(miNombre, milisegs);
				/* Pensando...*/
				//Recordemos soltar los palillos
				gestorPalillos.soltarPalillos(num_palillo_izq, num_palillo_der);
				
				milisegs=(1+generador.nextInt(5))*1000;
				esperarTiempoAzar(miNombre, milisegs);
			}
		}

	private void esperarTiempoAzar(String miNombre, int milisegs) {
			try {
				Thread.sleep(milisegs);
			} catch (InterruptedException e) {
				System.out.println(
						miNombre+" interrumpido!!. Saliendo...");
				return ;
			}
		}
	}

Lanzador de hilos
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
.. code-block:: java

	public class LanzadorFilosofos {
		public static void main(String[] args) {
			int MAX_FILOSOFOS=5;
			Filosofo[] filosofos=new Filosofo[MAX_FILOSOFOS];
			Thread[] hilosAsociados=new Thread[MAX_FILOSOFOS];
			GestorPalillos gestorCompartido=
					new GestorPalillos(MAX_FILOSOFOS);
			for (int i=0; i<MAX_FILOSOFOS; i++){
				if (i==0){
					filosofos[i]=new Filosofo(
							gestorCompartido, i,MAX_FILOSOFOS-1);
				}
				else {
					filosofos[i]=new Filosofo(
							gestorCompartido, i, i-1);
				}
				Thread hilo=new Thread(filosofos[i]);
				hilo.setName("Filosofo "+i);
				hilosAsociados[i]=hilo;
				hilo.start();
			}
			/* Un poco inútil*/
			for (int i=0; i<MAX_FILOSOFOS;i++){
				try {
					hilosAsociados[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

		
		
Documentación.
--------------------------------------------------------------------------

Depuración.
--------------------------------------------------------------------------
