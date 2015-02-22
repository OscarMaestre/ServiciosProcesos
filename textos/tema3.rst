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
				String url_descargar,
				String nombreArchivo){
			System.out.println("Descargando "
					+url_descargar);
			try {
				URL laUrl=new URL(url_descargar);
				InputStream is=laUrl.openStream();
				InputStreamReader reader=
						new InputStreamReader(is);
				BufferedReader bReader=
						new BufferedReader(reader);
				FileWriter escritorFichero=
					new FileWriter(nombreArchivo);
				String linea;
				while ((linea=bReader.readLine())!=null){
					escritorFichero.write(linea);
				}
				escritorFichero.close();
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
La clase URL proporciona un mecanismo muy sencillo pero por desgracia completamente atado al protocolo de las URL.

Java ofrece otros objetos que permiten tener un mayor control sobre lo que se envía o recibe a través de la red. Por desgracia esto implica que en muchos casos tendremos solo flujos de bajo nivel (streams).

En concreto Java ofrece dos elementos fundamentales para crear programas que usen redes

* Sockets
* ServerSockets


Repaso de redes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En redes el protocolo IP es el responsable de dos cuestiones fundamentales:

* Establecer un sistema de direcciones universal (direcciones IP)
* Establecer los mecanismos de enrutado.

Como programadores el segundo no nos interesa, pero el primero será absolutamente fundamental para contactar con programas que estén en una ubicación remota.


Una ubicación remota *siempre* tendrá una dirección
IP pero *solo a veces tendrá un nombre DNS*. Para nosotros no habrá diferencia ya que si es necesario el sistema operativo traducirá de nombre DNS a IP.

Otro elemento necesario en la comunicación en redes es el uso de un puerto de un cierto protocolo:


* TCP: ofrece fiabilidad a los programas.
* UDP: ofrece velocidad sacrificando la fiabilidad.

A partir de ahora cuando usemos un número de puerto habrá que comprobar si ese número ya está usado.

Por ejemplo, es mala idea que nuestros programas usen el puerto 80 TCP, probablemente ya esté en uso.
Antes de usar un puerto en una aplicación comercial deberíamos consultar la lista de "IANA assigned ports".

En líneas generales se pueden usar los puertos desde 1024 TCP a 49151 TCP, pero deberíamos comprobar que el número que elegimos no sea un número usado por un puerto de alguna aplicación que haya en la empresa.


En las prácticas de clase usaremos el 9876 TCP. Si se desea conectar desde el instituto con algún programa ejecutado en casa se deberá "abrir el puerto 9876 TCP". Abrir un puerto consiste en configurar el router para que **SÍ ACEPTE TRÁFICO INICIADO DESDE EL EXTERIOR** cosa que no hace nunca por motivos de protección.


Sockets.
-----------------------------------------------------------------------

Un *socket* es un objeto Java que nos permite contactar con un programa o servidor remoto. Dicho objeto nos proporcionará flujos de entrada y/o salida y podremos comunicarnos con dicho programa.

Existe otro tipo de sockets, los *ServerSocket*. Se utilizan para crear programas que acepten conexiones o peticiones.

Todos los objetos mencionados en este tema están en el paquete ``java.net``.






Creación de sockets.
-----------------------------------------------------------------------


En el siguiente código puede verse el proceso básico de creación de un socket. En los párrafos siguientes explicaremos el significado de los bloques de código.:


.. code-block:: java

	public class Conector {
		public static void main(String[] args) {
			String destino="www.google.com";
			int puertoDestino=80;
			Socket socket=new Socket();
			InetSocketAddress direccion=new InetSocketAddress(
					destino, puertoDestino);
			try {
				socket.connect(direccion);
				//Si llegamos aquí es que la conexión
				//sí se hizo.
				
				InputStream is=socket.getInputStream();
				OutputStream os=socket.getOutputStream();
				
				
			} catch (IOException e) {
				System.out.println(
					"No se pudo establecer la conexion "+
					" o hubo un fallo al leer datos."
				);
			}	
		}
	}

	
Para poder crear un socket primero necesitamos una dirección con la que contactar. Toda dirección está formada por dirección IP (o DNS) y un puerto. En nuestro caso intentaremos contactar con ``www.google.com:80``.

.. code-block:: java

	String destino="www.google.com";
	int puertoDestino=80;
	Socket socket=new Socket();
	InetSocketAddress direccion=new 
		InetSocketAddress(
				destino, puertoDestino);
	



Enlazado y establecimiento de conexiones.
-----------------------------------------------------------------------

El paso crítico para iniciar la comunicación es llamar al método ``connect``. Este método puede disparar una excepción del tipo ``IOException`` que puede significar dos cosas:

* La conexión no se pudo establecer.
* Aunque la conexión se estableció no fue posible leer o escribir datos.

Así, la conexión debería realizarse así:

.. code-block:: java

	try {
		socket.connect(direccion);
		//Si llegamos aquí es que la conexión
		//sí se hizo.
			
		InputStream is=socket.getInputStream();
		OutputStream os=socket.getOutputStream();	
			
	}  //Fin del try
	catch (IOException e) {
		System.out.println(
			"No se pudo establecer la conexion "+
			" o hubo un fallo al leer datos."
		);
	} //Fin del catch IOException



Utilización de sockets para la transmisión y recepción de información.
-----------------------------------------------------------------------

La clase ``Socket`` tiene dos métodos llamados ``getInputStream`` y ``getOutputSream`` que nos permiten obtener *flujos orientados a bytes*. Recordemos que es posible crear nuestros propios flujos, con más métodos que ofrecen más comodidad.


El ejemplo completo
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Podemos contactar con un programa cualquiera escrito en cualquier lenguaje y enviar las peticiones de acuerdo a un protocolo. Nuestro programa podrá leer las respuestas independientemente de como fuera el servidor.


.. code-block:: java

	public class Conector {
		public static void main(String[] args) {
			System.out.println("Iniciando...");
			String destino="10.8.0.253";
			int puertoDestino=80;
			Socket socket=new Socket();
			InetSocketAddress direccion=new InetSocketAddress(
					destino, puertoDestino);
			try {
				socket.connect(direccion);
				//Si llegamos aquí es que la conexión
				//sí se hizo.
				
				InputStream is=socket.getInputStream();
				OutputStream os=socket.getOutputStream();
				
				//Flujos que manejan caracteres
				InputStreamReader isr=
						new InputStreamReader(is);
				OutputStreamWriter osw=
						new OutputStreamWriter(os);

				//Flujos de líneas
				BufferedReader bReader=
						new BufferedReader(isr);
				PrintWriter pWriter=
						new PrintWriter(osw);
				
				
				pWriter.println("GET /index.html");
				pWriter.flush();
				String linea;
				FileWriter escritorArchivo=
						new FileWriter("resultado.txt");
				while ((linea=bReader.readLine()) != null ){
					escritorArchivo.write(linea);
				}
				escritorArchivo.close();
				pWriter.close();
				bReader.close();
				isr.close();
				osw.close();
				is.close();
				os.close();
			} catch (IOException e) {
				System.out.println(
					"No se pudo establecer la conexion "+
					" o hubo un fallo al leer datos."
				);
			} //Fin del catch		
		} //Fin del main
	} //Fin de la clase Conector

	
	
Programación de aplicaciones cliente y servidor.
-----------------------------------------------------------------------

Al crear aplicaciones cliente y servidor puede ocurrir que tengamos que implementar varias operaciones:

* Si tenemos que programar el servidor **deberemos definir un protocolo** de acceso a ese servidor.

* Si tenemos que programar solo el cliente **necesitaremos conocer el protocolo de acceso** a ese servidor.

* Si tenemos que programar los dos tendremos que empezar por **definir el protocolo de comunicación entre ambos**.

En el ejemplo siguiente puede verse un ejemplo para Python 3 que implementa un servidor de cálculo. El servidor tiene un protocolo muy rígido (demasiado) que consiste en lo siguiente:

1. El servidor espera que primero envíen la operación que puede ser ``+`` o ``-``. La operación debe terminar con un fin de línea UNIX (``\n``)
2. Despues acepta un número de dos cifras (ni una ni tres) terminado en un fín de línea UNIX.
3. Despues acepta un segundo número de dos cifras terminado en un fin de línea UNIX.

.. code-block:: python

	import socketserver
	TAM_MAXIMO_PARAMETROS=64
	PUERTO=9876
	class GestorConexion(
		socketserver.BaseRequestHandler):
		
		def leer_cadena(self, LONGITUD):
			cadena=self.request.recv(LONGITUD)
			return cadena.strip()
		
		def convertir_a_cadena(self, bytes):
			return bytes.decode("utf-8")
		
		def calcular_resultado(
			self, n1, op, n2):
			n1=int(n1)
			n2=int(n2)
			
			op=self.convertir_a_cadena(op)
			if (op=="+"):
				return n1+n2
			if (op=="-"):
				return n1-n2
			return 0
		"""Controlador de evento 'NuevaConexion"""
		def handle(self):
			direccion=self.client_address[0]
			operacion   =   self.leer_cadena(2)
			num1        =   self.leer_cadena(3)
			num2        =   self.leer_cadena(3)
			print (direccion+" pregunta:"+str(num1)+" "+str(operacion)+" "+str(num2))
			
			resultado=self.calcular_resultado(num1, operacion, num2)
			print ("Devolviendo a " + direccion+" el resultado "+str(resultado))
			bytes_resultado=bytearray(str(resultado), "utf-8");
			self.request.send(bytes_resultado)
	servidor=socketserver.TCPServer(("10.13.0.20", 9876), GestorConexion)
	print ("Servidor en marcha.")
	servidor.serve_forever()

La comunicación Java con el servidor sería algo así:

.. code-block:: java

	byte[] bSuma="+\n".getBytes();
	byte[] bOp1="42\n".getBytes();
	byte[] bOp2="34\n".getBytes();
				
	os.write(bSuma);
	os.write(bOp1);
	os.write(bOp2);
	os.flush();
				
	InputStreamReader isr=
		new InputStreamReader(is);
	BufferedReader br=
		new BufferedReader(isr);
	String cadenaRecibida=br.readLine();
	System.out.println("Recibido:"+
			cadenaRecibida);
							
	is.close();
	os.close();
	socket.close();

	
Ejemplo de servidor Java
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Supongamos que se nos pide crear un servidor de operaciones de cálculo que sea menos estricto que el anterior:

* Cualquier parámetro que envíe el usuario debe ir terminado en un fin de línea UNIX ("\n").
* El usuario enviará primero un símbolo "+", "-", "*" o "/". 
* Despues se puede enviar un positivo de 1 a 8 cifras. El usuario podría equivocarse y enviar en vez de "3762" algo como "37a62". En ese caso se asume que el parámetro es 0.
* Despues se envía un segundo positivo de 1 a 8 cifras igual que el anterior.
* Cuando se haya procesado todo el servidor contestará al cliente con un positivo de 1 a 12 cifras.

Antes de empezar crear el código que permita procesar estos parámetros complejos.


.. code-block:: java

	public class ServidorCalculo {
		public int extraerNumero(String linea){
			/* 1. Comprobar si es un número
			 * 2. Ver si el número es correcto (32a75)
			 * 3. Ver si tiene de 1 a 8 cifras
			 */
			int numero;
			try{
				numero=Integer.parseInt(linea);
			}
			catch (NumberFormatException e){
				numero=0;
			}
			/* Si el número es mayor de 100 millones no
			 * es válido tampoco
			 */
			if (numero>=100000000){
				numero=0;
			}
			return numero;
			
		}
		public void escuchar(){
			System.out.println("Arrancado el servidor");
			
			while (true){
				
			}
		}
	}
	
Así, el código completo del servidor sería:

.. code-block:: java

	public class ServidorCalculo {
		public int extraerNumero(String linea){
			/* 1. Comprobar si es un número
			 * 2. Ver si el número es correcto (32a75)
			 * 3. Ver si tiene de 1 a 8 cifras
			 */
			int numero;
			try{
				numero=Integer.parseInt(linea);
			}
			catch (NumberFormatException e){
				numero=0;
			}
			/* Si el número es mayor de 100 millones no
			 * es válido tampoco
			 */
			if (numero>=100000000){
				numero=0;
			}
			return numero;
		}
		
		public int calcular(String op, String n1, String n2){
			int resultado=0;
			char simbolo=op.charAt(0);
			int num1=this.extraerNumero(n1);
			int num2=this.extraerNumero(n2);
			if (simbolo=='+'){
				resultado=num1+num2;
			}
			return resultado;
		}
		
		public void escuchar() throws IOException{
			System.out.println("Arrancado el servidor");
			ServerSocket socketEscucha=null;
			try {
				socketEscucha=new ServerSocket(9876);
			} catch (IOException e) {
				System.out.println(
						"No se pudo poner un socket "+
						"a escuchar en TCP 9876");
				return;
			}
			while (true){
				Socket conexion=socketEscucha.accept();
				System.out.println("Conexion recibida!");
				InputStream is=conexion.getInputStream();
				InputStreamReader isr=
						new InputStreamReader(is);
				BufferedReader bf=
						new BufferedReader(isr);
				String linea=bf.readLine();
				String num1=bf.readLine();
				String num2=bf.readLine();
				/* Calculamos el resultado*/
				Integer result=this.calcular(linea, num1, num2);
				OutputStream os=conexion.getOutputStream();
				PrintWriter pw=new PrintWriter(os);
				pw.write(result.toString()+"\n");
				pw.flush();
			}
		}
	}

Y el cliente sería:

.. code-block:: java

	public class ClienteCalculo {
		public static BufferedReader getFlujo(InputStream is){
			InputStreamReader isr=
					new InputStreamReader(is);
			BufferedReader bfr=
					new BufferedReader(isr);
			return bfr;
		}
		/**
		 * @param args
		 * @throws IOException 
		 */
		public static void main(String[] args) throws IOException {
			InetSocketAddress direccion=new
					InetSocketAddress("10.13.0.20", 9876);
			Socket socket=new Socket();
			socket.connect(direccion);
			BufferedReader bfr=
					ClienteCalculo.getFlujo(
							socket.getInputStream());
			PrintWriter pw=new 
					PrintWriter(socket.getOutputStream());
			pw.print("+\n");
			pw.print("42\n");
			pw.print("84\n");
			pw.flush();
			String resultado=bfr.readLine();
			System.out.println
				("El resultado fue:"+resultado);
		}
	}

	
	
Utilización de hilos en la programación de aplicaciones en red.
-----------------------------------------------------------------------

En el caso de aplicaciones que necesiten aceptar varias conexiones **habrá que mover todo el código de gestión de peticiones a una clase que implemente Runnable**

Ahora el servidor será así:

.. code-block:: java

	while (true){
		Socket conexion=socketEscucha.accept();
		System.out.println("Conexion recibida");
		Peticion p=new Peticion(conexion);
		Thread hilo=new Thread(p);
		hilo.start();
	}
	
Pero ahora tendremos una clase Petición como esta:

.. code-block:: java

	public class Peticion implements Runnable{
		BufferedReader bfr;
		PrintWriter pw;
		Socket socket;
		public Peticion(Socket socket){
			this.socket=socket;
		}
		public int extraerNumero(String linea){
			/* 1. Comprobar si es un número
			 * 2. Ver si el número es correcto (32a75)
			 * 3. Ver si tiene de 1 a 8 cifras
			 */
			int numero;
			try{
				numero=Integer.parseInt(linea);
			}
			catch (NumberFormatException e){
				numero=0;
			}
			/* Si el número es mayor de 100 millones no
			 * es válido tampoco
			 */
			if (numero>=100000000){
				numero=0;
			}
			return numero;
			
		}
		
		public int calcular(String op, String n1, String n2){
			int resultado=0;
			char simbolo=op.charAt(0);
			int num1=this.extraerNumero(n1);
			int num2=this.extraerNumero(n2);
			if (simbolo=='+'){
				resultado=num1+num2;
			}
			return resultado;
		}
		public void run(){
			try {
				InputStream is=socket.getInputStream();
				InputStreamReader isr=
						new InputStreamReader(is);
				bfr=new BufferedReader(isr);
				OutputStream os=socket.getOutputStream();
				pw=new PrintWriter(os);
				String linea;
				while (true){
					linea = bfr.readLine();
					String num1=bfr.readLine();
					String num2=bfr.readLine();
					/* Calculamos el resultado*/
					Integer result=this.calcular(linea, num1, num2);
					System.out.println("El servidor dio resultado:"+result);
					pw.write(result.toString()+"\n");
					pw.flush();
				}
			} catch (IOException e) {
			}	
		}
	}


Ejercicio: servicio de ordenación
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Crear una arquitectura cliente/servidor que permita a un cliente, enviar dos cadenas a un servidor para saber cual de ellas va antes que otra:

* Un cliente puede enviar las cadenas "hola", "mundo". El servidor comprobará que en el diccionario la primera va antes que la segunda, por lo cual contestará "hola", "mundo".

* Si el cliente enviase "mundo", "hola" el servidor debe devolver la respuesta "hola", "mundo".

Debido a posibles mejoras futuras, se espera que el servidor sea capaz de saber qué versión del protocolo se maneja. Esto es debido a que en el futuro se espera lanzar una versión 2 del protocolo en la que se puedan enviar varias cadenas seguidas.

Crear el protocolo, el código Java del cliente y el código Java del servidor con capacidad para procesar muchas peticiones a la vez (multihilo).	

Se debe aceptar que un cliente que ya tenga un socket abierto envíe todas las parejas de cadenas que desee.

Una clase Protocolo
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Dado que los protocolos pueden ser variables puede ser útil encapsular el comportamiento del protocolo en una pequeña clase separada:

.. code-block:: java

	public class Protocolo {
		private final String terminador="\n";
		public String getMensajeVersion(int version){
			Integer i=version;
			return i.toString()+terminador;
		}	
		public int getNumVersion(String mensaje){
			Integer num=Integer.parseInt(mensaje);
			return num;
		}
		public String getMensaje(String cadena){
			return cadena+terminador;
		}	
	}
	
Una clase con funciones de utilidad
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Algunas operaciones son muy sencillas, pero muy engorrosas. Alargan el código innecesariamente y lo hacen más difícil de entender. Si además se realizan a menudo puede ser interesante empaquetar toda la funcionalidad en una clase.

.. code-block:: java

	public class Utilidades {
		/* Obtiene un flujo de escritura
		a partir de un socket*/
		public PrintWriter getFlujoEscritura
			(Socket s) throws IOException{
			OutputStream os=s.getOutputStream();
			PrintWriter pw=new PrintWriter(os);
			return pw;		
		}
		/* Obtiene un flujo de lectura
		a partir de un socket*/
		public BufferedReader 
			getFlujoLectura(Socket s) 
					throws IOException{
			InputStream is=s.getInputStream();
			InputStreamReader isr=
					new InputStreamReader(is);
			BufferedReader bfr=new BufferedReader(isr);
			return bfr;
		}
	}

La clase Petición
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public class Peticion implements Runnable {
		Socket conexionParaAtender;
		
		public Peticion ( Socket s ){
			this.conexionParaAtender=s;
		}
		@Override
		public void run() {
			try{
				PrintWriter flujoEscritura=
					Utilidades.getFlujoEscritura(
							this.conexionParaAtender
							);
				BufferedReader flujoLectura=
					Utilidades.getFlujoLectura(
							conexionParaAtender);
				String protocolo=
						flujoLectura.readLine();
				int numVersion=
						Protocolo.getNumVersion(protocolo);
				if (numVersion==1){
					String linea1=
							flujoLectura.readLine();
					String linea2=
							flujoLectura.readLine();
					//Linea 1 va despues en el
					if (linea1.compareTo(linea2)>0){
						 dicc
						flujoEscritura.println(linea2);
						flujoEscritura.println(linea1);
						flujoEscritura.flush();
					} else {
						flujoEscritura.println(linea1);
						flujoEscritura.println(linea2);
						flujoEscritura.flush();
					}
				}
			}
			catch (IOException e){
				System.out.println(
						"No se pudo crear algún flujo");
				return ;
			}	
		}	
	}
	
La clase Servidor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public class ServidorOrdenacion {
		public void escuchar() throws IOException{
			ServerSocket socket;
			try{
				socket=new ServerSocket(9876);
			}
			catch(Exception e){
				System.out.println("No se pudo arrancar");
				return ;
			}
			while (true){
				System.out.println("Servidor esperando");
				Socket conexionCliente=
						socket.accept();
				System.out.println("Alguien conectó");
				Peticion p=
						new Peticion(conexionCliente);
				Thread hiloAsociado=
						new Thread(p);
				hiloAsociado.start();
			}
		} // Fin del método escuchar
		public static void  main(String[] argumentos){
			ServidorOrdenacion s=
					new ServidorOrdenacion();		
			try {
				s.escuchar();
			} catch (Exception e){
				System.out.println("No se pudo arrancar");
				System.out.println(" el cliente o el serv");
			}
		}
	}


La clase Cliente
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public class Cliente {
		public void ordenar(String s1, String s2) throws IOException{
			InetSocketAddress direccion=
					new InetSocketAddress("10.13.0.20", 9876);
			Socket conexion=
					new Socket();
			conexion.connect(direccion);
			System.out.println("Conexion establecida");
			/* Ahora hay que crear flujos de salida, enviar
			 * cadenas por allí y esperar los resultados.
			 */
			try{
				
				BufferedReader flujoLectura=
					Utilidades.getFlujoLectura(conexion);
				PrintWriter flujoEscritura=
					Utilidades.getFlujoEscritura(conexion);
				
				flujoEscritura.println("1");
				flujoEscritura.println(s1);
				flujoEscritura.println(s2);
				flujoEscritura.flush();
				String linea1=flujoLectura.readLine();
				String linea2=flujoLectura.readLine();
				System.out.println("El servidor devolvió "+
						linea1 + " y "+linea2);
			} catch (IOException e){
				
			}
		}
		public static void main(String[] args) {
			Cliente c=new Cliente();
			try {
				c.ordenar("aaa", "bbb");
			} catch (IOException e) {
				System.out.println("Fallo la conexion o ");
				System.out.println("los flujos");
			} //Fin del catch
		} //Fin del main
	} //Fin de la clase

Ampliación
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Finalmente la empresa va a necesitar una versión mejorada del servidor que permita a otros cliente enviar un número de palabras y luego las palabras. Se desea hacer todo sin romper la compatibilidad con los clientes viejos. Mostrar el código Java del servidor y del cliente.

En el servidor se añade este código extra a la hora de comprobar el protocolo:

.. code-block:: java

	
	
	if (numVersion==2){
		System.out.println("Llegó un v2");
		String lineaCantidadPalabras=
		flujoLectura.readLine();
		int numPalabras=
			Integer.parseInt 
			(lineaCantidadPalabras);
		String[] palabras=
					new String[numPalabras];
		for (int i=0;i<numPalabras;i++){
			palabras[i]=
				flujoLectura.readLine();
		}
		palabras=this.ordenar(palabras);
		for (int i=0; i<palabras.length; i++){
			flujoEscritura.println(palabras[i]);
		}
		flujoEscritura.flush();
	}

Y finalmente solo habría que implementar un método en la petición que reciba un vector de ``String`` (las palabras) y devuelva el mismo vector pero ordenado.


