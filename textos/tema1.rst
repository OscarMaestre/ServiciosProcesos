=========================
Programación multiproceso
=========================


Ejecutables. Procesos. Servicios.
---------------------------------

Ejecutables
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Un ejecutable es un archivo con la estructura necesaria para que el sistema operativo pueda poner en marcha el programa que hay dentro. En Windows, los ejecutables suelen ser archivos con la extension .EXE.

Sin embargo, Java genera ficheros .JAR o .CLASS. Estos ficheros *no son ejecutables* sino que son archivos que el intérprete de JAVA (el archivo ``java.exe``) leerá y ejecutará.

El intérprete toma el programa y lo traduce a instrucciones del microprocesador en el que estemos, que puede ser x86 o un x64 o lo que sea. Ese proceso se hace "al instante" o JIT (Just-In-Time).


Un EXE puede que no contenga las instrucciones de los microprocesadores más modernos. Como todos son compatibles no es un gran problema, sin embargo, puede que no aprovechemos al 100% la capacidad de nuestro micro.


Procesos
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Es un archivo que está en ejecución y bajo el control del sistema operativo. Un proceso puede atravesar diversas etapas en su "ciclo de vida". Los estados en los que puede estar son:

* En ejecución: está dentro del microprocesador.
* Pausado/detenido/en espera: el proceso tiene que seguir en ejecución pero en ese momento el S.O tomó la decisión de dejar paso a otro.
* Interrumpido: el proceso tiene que seguir en ejecución pero *el usuario* ha decidido interrumpir la ejecución.
* Existen otros estados pero ya son muy dependientes del sistema operativo concreto.


Servicios
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Un servicio es un proceso que no muestra ninguna ventana ni gráfico en pantalla porque no está pensado para que el usuario lo maneje directamente.


Habitualmente, un servicio es un programa que atiende a otro programa.


Hilos.
------
Un hilo es un concepto más avanzado que un proceso: al hablar de procesos cada uno tiene su propio espacio en memoria. Si abrimos 20 procesos cada uno de ellos consume 20x de memoria RAM. Un hilo es un proceso mucho más ligero, en el que el código y los datos se comparten de una forma distinta.

Un proceso no tiene acceso a los datos de otro procesos. Sin embargo un hilo sí accede a los datos de otro hilo. Esto complicará algunas cuestiones a la hora de programar.



Programación concurrente.
-------------------------

La programación concurrente es la parte de la programación que se ocupa de crear programas que pueden tener varios procesos/hilos que colaboran para ejecutar un trabajo y aprovechar al máximo el rendimiento de sistemas multinúcleo.




Programación paralela y distribuida.
------------------------------------
Dentro de la programación concurrente tenemos la paralela y la distribuida:

* En general se denomina "programación paralela" a la creación de software que se ejecuta siempre en un solo ordenador (con varios núcleos o no)
* Se denomina "programación distribuida" a la creación de software que se ejecuta en ordenadores distintos y que se comunican a través de una red.

Creación de procesos.
---------------------

En Java es posible crear procesos utilizando algunas clases que el entorno ofrece para esta tarea. En este tema, veremos en profundidad la clase ProcessBuilder.

El ejemplo siguiente muestra como lanzar un proceso de Acrobat Reader:

.. code-block:: java

	public class LanzadorProcesos {
		public void ejecutar(String ruta){
			
			ProcessBuilder pb;
			try {
				pb = new ProcessBuilder(ruta);
				pb.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			String ruta=
				"C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe";
			LanzadorProcesos lp=new LanzadorProcesos();
			lp.ejecutar(ruta);
			System.out.println("Finalizado");
		}

	}	

Supongamos que necesitamos crear un programa que aproveche al máximo el número de CPUs para realizar alguna tarea intensiva. Supongamos que dicha tarea consiste en sumar números.

Enunciado: crear una clase Java que sea capaz de sumar todos los números comprendidos entre dos valores incluyendo ambos valores.


Para resolverlo crearemos una clase ``Sumador`` que tenga un método que acepte dos números ``n1`` y ``n2`` y que devuelva la suma de todo el intervalor.

Además, incluiremos un método ``main`` que ejecute la operación de suma tomando los números de la línea de comandos (es decir, se pasan como argumentos al main).

El código de dicha clase podría ser algo así:

.. code-block:: java

	package com.ies;

	public class Sumador {
		public int sumar(int n1, int n2){
			int resultado=0;
			for (int i=n1;i<=n2;i++){
				resultado=resultado+i;
			}
			return resultado;
		}
		public static void main(String[] args){
			Sumador s=new Sumador();
			int n1=Integer.parseInt(args[0]);
			int n2=Integer.parseInt(args[1]);
			int resultado=s.sumar(n1, n2);
			System.out.println(resultado);
		}
	}	

Para ejecutar este programa desde dentro de Eclipse es necesario indicar que deseamos enviar *argumentos* al programa. Por ejemplo, si deseamos sumar los números del 2 al 10, deberemos ir a la venta "Run configuration" y en la pestaña "Arguments" indicar los argumentos (que en este caso son los dos números a indicar).

.. figure:: ../imagenes/configuraciones.png
   :figwidth: 50%
   :align: center
   
   Modificando los argumentos del programa

   
Una vez hecha la prueba de la clase sumador, le quitamos el main, y crearemos una clase que sea capaz de lanzar varios procesos. La clase ``Sumador`` se quedará así:

.. code-block:: java

	public class Sumador {
		public int sumar(int n1, int n2){
			int resultado=0;
			for (int i=n1;i<=n2;i++){
				resultado=resultado+i;
			}
			return resultado;
		}
	}
	
   

Y ahora tendremos una clase que lanza procesos de esta forma:

.. code-block:: java

	package com.ies;

	public class Lanzador {
		public void lanzarSumador(Integer n1, 
				Integer n2){
			String clase="com.ies.Sumador";
			ProcessBuilder pb;
			try {
				pb = new ProcessBuilder(
						"java",clase, 
						n1.toString(), 
						n2.toString());
				pb.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static void main(String[] args){
			Lanzador l=new Lanzador();
			l.lanzarSumador(1, 51);
			l.lanzarSumador(51, 100);
			System.out.println("Ok");
		}
	}
	







	
	
	
Comunicación entre procesos.
----------------------------


Gestión de procesos.
--------------------

Comandos para la gestión de procesos en sistemas libres y propietarios.
-----------------------------------------------------------------------

Sincronización entre procesos.
------------------------------


Programación de aplicaciones multiproceso.
-------------------------------------------


Documentación
-------------

Depuración.
-----------