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