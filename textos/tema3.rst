Programación de comunicaciones en red
=======================================
Comunicación entre aplicaciones.
-----------------------------------------------------------------------


En Java toda la comunicación vista en primer curso de DAM consiste en dos cosas

* Entrada/salida por consola: con las clases ``System.in`` o ``System.out``.

* Lectura/escritura en ficheros: con las clases File y similares.


Se puede avanzar un paso más utilizando Java para enviar datos a través de Internet a otro programa Java remoto, que es lo que haremos en este capítulo.

Roles cliente y servidor.
-----------------------------------------------------------------------

Cuando se hacen programas Java que se comuniquen lo habitual es que uno o varios actúen de cliente y uno o varios actúen de servidores.

* Servidor: espera peticiones, recibe datos de entrada y devuelve respuestas.


* Cliente: genera peticiones, las envía a un servidor y espera respuestas.

Un factor fundamental en los servidores es que tienen que ser capaces de procesar varias peticiones a la vez: **deben ser multihilo**.

Su arquitectura típica es la siguiente:

.. code-block:: java

	while (true){
		peticion=esperarPeticion();
		hiloAsociado=new Hilo();
		hiloAsociado.atender(peticion);
	}
		


Recordatorio de los flujos en Java
------------------------------------------------------

InputStreams y OutputStreams
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Manejan bytes a secas. Por ejemplo, si queremos leer un fichero byte a byte usaremos ``FileInputStream`` y si queremos escribir usaremos ``FileOutputStream``.

Son operaciones a muy bajo nivel que usaremos muy pocas veces (por ejemplo, solo si quisiéramos cambiar el primer byte de un archivo). En general usaremos otras clases más cómodas de usar.

Readers y Writers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En lugar de manejar *bytes* manejan *caracteres* (recordemos que hoy en día y con Unicode una letra como la *ñ* en realidad podría ocupar más de un byte).

Así, cuando queramos leer letras de un archivo usaremos clases como ``FileReader`` y ``FileWriter``.

Las clases ``Readers`` y ``Writers`` en realidad se apoyan sobre las ``InputStreams`` y ``OutputStreams``.

A veces nos interesará mezclar conceptos y por ejemplo poder tener una clase que use caracteres cuando a lo mejor Java nos ha dado una clase que usa bytes. Así, por ejemplo ``InputStreamReader`` puede coger un objeto que lea bytes y nos devolverá caracteres. De la misma forma ``OutputStreamWriter`` coge letras y devuelve los bytes que la componen.

``BufferedReaders`` y ``PrintWriters``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Cuando trabajamos con caracteres (que recordemos pueden tener varios bytes) normalmente no trabajamos de uno en uno. Es más frecuente usar **líneas** que se leen y escriben de una sola vez. Así por ejemplo, la clase ``PrintWriter`` tiene un método ``print(ln)`` que puede imprimir elementos complejos como ``floats`` o cadenas largas.

Además, Java ofrece clases que gestionan automáticamente los *buffers* por nosotros lo que nos da más comodidad y eficiencia. Por ello es muy habitual hacer cosas como esta:

.. code-block:: java

	lectorEficiente = new 
		BufferedReader(new FileReader("fich1.txt"));
	escritorEficiente = new 
		BufferedWriter(new FileWriter("fich2.txt"));	

En el primer caso creamos un objeto ``FileReader`` que es capaz de leer caracteres de ``fich1.txt``. Como esto nos parece poco práctico creamos otro objeto a partir del primero de tipo ``BufferedReader`` que nos permitirá leer bloques enteros de texto.

De hecho, si se comprueba la ayuda de la clase ``FileReader`` se verá que solo hay un método ``read`` que devuelve un ``int``, es decir el siguiente **carácter** disponible, lo que hace que el método sea muy incómodo. Sin embargo ``BufferedReader``

Elementos de programación de aplicaciones en red. Librerías.
-----------------------------------------------------------------------

En Java toda la infraestructura de clases para trabajar con redes está en el paquete ``java.net``.

En muchos casos nuestros programas empezarán con la sentencia ``import java.net.*`` pero muchos entornos (como Eclipse) son capaces de importar automáticamente las clases necesarias.



La clase URL
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

La clase URL permite gestionar accesos a URLs del tipo ``http://marca.com/fichero.html`` y descargar cosas con bastante sencillez.

Al crear un objeto URL se debe capturar la excepción ``MalformedURLException`` que sucede cuando hay algún error en la URL (como escribir ``htp://marca.com``).

La clase URL nos ofrece un método ``openStream`` que nos devuelve un flujo básico de bytes. Podemos crear objetos más sofisticados para leer bloques como muestra el programa siguiente:

.. code-block:: java

	public class GestorDescargas {
		public void descargarArchivo(
				String url_descargar){
			System.out.println("Descargando "
					+url_descargar);
			try {
				URL laUrl=new URL(url_descargar);
				InputStream is=laUrl.openStream();
				InputStreamReader reader=
						new InputStreamReader(is);
				BufferedReader bReader=
						new BufferedReader(reader);
				String linea;
				while ((linea=bReader.readLine())!=null){
					System.out.println(linea);
				}
				bReader.close();
				reader.close();
				is.close();
			} catch (MalformedURLException e) {
				System.out.println("URL mal escrita!");
				return ;
			} catch (IOException e) {
				System.out.println(
						"Fallo en la lectura del fichero");
				return ;
			}
		}
		public static void main (String[] argumentos){
			GestorDescargas gd=new GestorDescargas();
			String base=
				"http://10.13.0.20:8000"+
						"/ServiciosProcesos/textos/";
			for (int i=1; i<=5; i++){
				String url=base+"tema"+i+".rst";
				gd.descargarArchivo(url);
			}
		}
	}
	


Funciones y objetos de las librerías.
-----------------------------------------------------------------------

Sockets.
-----------------------------------------------------------------------

Tipos de sockets. Características.
-----------------------------------------------------------------------

Creación de sockets.
-----------------------------------------------------------------------

Enlazado y establecimiento de conexiones.
-----------------------------------------------------------------------

Utilización de sockets para la transmisión y recepción de información.
-----------------------------------------------------------------------

Programación de aplicaciones cliente y servidor.
-----------------------------------------------------------------------

Utilización de hilos en la programación de aplicaciones en red.
-----------------------------------------------------------------------

Depuración.
-----------------------------------------------------------------------
