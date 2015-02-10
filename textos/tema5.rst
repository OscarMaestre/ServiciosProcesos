================================================
Utilización de técnicas de programación segura
================================================

Introducción
------------------------------------------------------
En general, cuando se envía algo a través de sockets se envía como "texto plano", es decir, no sabemos si hay alguien usando un sniffer en la red y por tanto no sabemos si alguien está capturando los datos.

En general, cualquier sistema que pretenda ser seguro necesitará usar cifrado.

Prácticas de programación segura.
----------------------------------------------------

Para enviar mensajes cifrados se necesita algún mecanismo o algoritmo para convertir un texto normal en uno más difícil de comprender.

El esquema general de todos los métodos es tener código como el siguiente:


.. code-block:: java

	public String cifrar (String texto, String clave)
	{
	}

	public String descifrar(String texto,String clave)
	{
	}

Método César
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Si el alfabeto es el siguiente:

ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789_
BCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789_A


El mensaje HOLA MUNDO, con clave 1 sería así

	HOLAMUNDO
	IPMBNVÑEP
	
	
El descifrado simplemente implicaría el método inverso. Si el desplazamiento es un valor distinto de 1, lo único que hay que hacer es construir el alfabeto rotado tantas veces como el desplazamiento

Esta clase implementa un sistema de rotado básico para poder efectuar 

.. code-block:: java


	public class Cifrador {
		private String alfabeto=
				"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"+
				"0123456789 ";
		private String alfabetoCifrado;
		/* Dada una cadena como 
		 * "ABC" y un número (p.ej 2)
		 * devuelve la cadena rotada a la izq
		 * tantas veces como indique el numero,
		 * en este caso "CAB" 
		 */
		public String rotar(String cad,int numVeces){
			char[] resultado=new char[cad.length()];
			for (int i=0; i<cad.length();i++){
				int posParaExtraer=(i+numVeces)%cad.length();
				resultado[i]=cad.charAt(posParaExtraer);
			}
			String cadResultado=String.copyValueOf(resultado);
			return cadResultado;
		}
		public String cifrar 
			(String mensaje, String clave){
			String mensajeCifrado="";
			
			return mensajeCifrado;
		}
		public String descifrar
			(String mensajeCifrado, String clave){
			String mensajeDescifrado="";
			
			return mensajeDescifrado;
		}
		public static void main(String[] args){
			Cifrador c=new Cifrador();
			String cad=c.rotar("ABCDEFG", 0);
			System.out.println(cad);
		}
	}
	







Criptografía de clave pública y clave privada.
----------------------------------------------------

Los principales sistemas modernos de seguridad utilizan dos claves ,una para cifrar y otra para descifrar. Esto se puede usar de diversas formas.


Principales aplicaciones de la criptografía.
----------------------------------------------------

* Mensajería segura: todo el mundo da su clave de cifrado pero conserva la de descifrado. Si queremos enviar un mensaje a alguien cogemos su clave de cifrado y ciframos el mensaje que le enviamos. Solo él podrá descifrarlo.


* Firma digital: pilar del comercio electrónico. Permite verificar que un archivo no ha sido modificado.
* Mensajería segura: en este tipo de mensajería se intenta evitar que un atacante (quizá con un *sniffer*) consiga descifrar nuestros mensajes.
* Autenticación: los sistemas de autenticación intentan resolver una cuestión clave en la informática: **verificar que una máquina es quien dice ser**



Protocolos criptográficos.
----------------------------------------------------

En realidad protocolos criptográficos hay muchos, y suelen dividirse en sistemas simétricos o asimétricos.

* Los sistemas simétricos son aquellos basados en una función que convierte un mensaje en otro mensaje cifrado. Si se desea descifrar algo se aplica el proceso inverso con la misma clave que se usó.

* Los sistemas asimétricos utilizan una clave de cifrado y otra de descifrado. Aunque se tenga una clave es matemáticamente imposible averiguar la otra clave por lo que se puede dar a todo el mundo una de las claves (llamada habitualmente **clave pública**) y conservar la otra (llamada **clave privada**). Además, podemos usar las claves para lo que queramos y por ejemplo en unos casos cifraremos con la clave pública y en otros tal vez cifremos con la clave privada.

Hoy por hoy, las mayores garantías las ofrecen los asimétricos, de los cuales hay varios sistemas. El inconveniente que pueden tener los asimétricos es que son más lentos computacionalmente.

En este curso usaremos el cifrado asimétrico RSA.


Encriptación de información.
------------------------------------------------------------

El siguiente código muestra como crear una clase que permita cifrar y descifrar textos.

.. code-block:: java

	public class GestorCifrado {
		KeyPair claves;
		KeyPairGenerator generadorClaves;
		Cipher cifrador;
		public GestorCifrado() 
				throws NoSuchAlgorithmException, 
				NoSuchPaddingException{
			generadorClaves=
					KeyPairGenerator.getInstance("RSA");
			/*Usaremos una longitud de clave 
			 * de 1024 bits */
			generadorClaves.initialize(1024);
			claves=generadorClaves.generateKeyPair();
			cifrador=Cipher.getInstance("RSA");
		}
		public PublicKey getPublica(){
			return claves.getPublic();
		}
		public PrivateKey getPrivada(){
			return claves.getPrivate();
		}
		
		public byte[] cifrar(byte[] paraCifrar,
				Key claveCifrado
				) throws InvalidKeyException, 
				IllegalBlockSizeException, 
				BadPaddingException{
			byte[] resultado;
			/* Se pone el cifrador en modo cifrado*/
			cifrador.init(Cipher.ENCRYPT_MODE, 
					claveCifrado);
			resultado=cifrador.doFinal(paraCifrar);
			return resultado;	
		}
		
		public byte[] descifrar(
				byte[] paraDescifrar,
				Key claveDescifrado) 
						throws InvalidKeyException,
						IllegalBlockSizeException, 
						BadPaddingException{
			byte[] resultado;
			/* Se pone el cifrador en modo descifrado*/
			cifrador.init(Cipher.DECRYPT_MODE,
					claveDescifrado);
			resultado=cifrador.doFinal(paraDescifrar);
			return resultado;
		}
		
		
		
		public static void main(String[] args) 
				throws NoSuchAlgorithmException,
				NoSuchPaddingException,
				InvalidKeyException,
				IllegalBlockSizeException,
				BadPaddingException,
				UnsupportedEncodingException 
		{
			GestorCifrado gestorCifrado=
					new GestorCifrado();
			String mensajeOriginal="Hola mundo";
			Key clavePublica=gestorCifrado.getPublica();
			
			byte[] mensajeCifrado=
					gestorCifrado.cifrar(
							mensajeOriginal.getBytes(),
							clavePublica
			);
			String cadCifrada=
					new String(mensajeCifrado, "UTF-8");
			
			System.out.println
				("Cadena original:"+mensajeOriginal);
			System.out.println
				("Cadena cifrada:"+cadCifrada);
			
			/* Cogemos la cadCifrada y la desciframos
			 * con la otra clave
			 */
			Key clavePrivada;
			clavePrivada=gestorCifrado.getPrivada();
			byte[] descifrada=
					gestorCifrado.descifrar(
							mensajeCifrado,clavePrivada);
			String mensajeDescifrado;
			mensajeDescifrado=
					new String(descifrada, "UTF-8");
			System.out.println(
					"El mensaje descifrado es:"+
							mensajeDescifrado);
		}
	}


.. WARNING::
   Los objetos que cifran y descifran en Java utilizan estrictamente objetos ``byte[]``, que
   son los que debemos manejar siempre. Las conversiones a ``String`` las hacemos nosotros para poder visualizar resultados.

Protocolos seguros de comunicaciones.
------------------------------------------------------------

En general, ahora que ya conocemos sockets, el uso de servidores y clientes y el uso de la criptografía de clave asimétrica ya es posible crear aplicaciones que se comuniquen de forma muy segura.

En general, todo protocolo que queramos implementar dará estos pasos.

1. Todo cliente genera su pareja de claves.
2. Todo servidor genera su pareja de claves.
3. Cuando un cliente se conecte a un servidor, le envía su clave de cifrado y conserva la de descifrado.
4. Cuando un servidor recibe la conexión de un cliente recibe la clave de cifrado de dicho cliente.
5. El servidor envía su clave pública al cliente.
6. Ahora cliente y servidor pueden enviar mensajes al otro con la garantía de que solo servidor y cliente respectivamente pueden descifrar.

En realidad se puede asegurar más el proceso haciendo que en el paso 5 el servidor cifre su propia clave pública con la clave pública del cliente. De esta forma, incluso aunque alguien robara la clave privada del cliente tampoco tendría demasiado, ya que tendría que robar la clave privada del servidor.

Programación de aplicaciones con comunicaciones seguras.
------------------------------------------------------------

El protocolo básico ya se ha comentado en el paso anterior. Ya se dispone de una clase (``GestorCifrado``) que permite manipular el cifrado de forma básica, sin embargo ahora cliente y servidor deben enviar sus claves públicas respectivas, es decir

* El servidor recibirá la clave pública del cliente.
* El cliente recibirá la clave pública del servidor (puede la clave pública del servidor se envíe cifrada con la clave pública del servidor).

.. WARNING::
   Enviar una clave pública como String **ES MUY PELIGROSO**. La clase String podría construir una cadena que contenga finales de línea que podrían estropear un protocolo que se base en enviar mensajes que se supone que solo tienen una línea.

El proceso para el cliente es más o menos así:

1. Conectar
2. Obtener flujos para poder leer y escribir cosas de y hacia el servidor.
3. Convertir su clave pública en una lista de bytes
4. Enviar un mensaje al servidor diciéndole la longitud de la clave que se va a enviar (es decir, cuantos bytes se van a enviar).
5. Enviar los bytes de la clave pública y hacer flush.



Esto puede implicar que en unos casos usemos unas clases de flujos u otros (para enviar líneas podemos usar los ``PrintWriter`` pero para enviar bytes usaremos ``OutputStream``)

Por desgracia enviar una clave pública en forma de bytes implica luego tener que volver a convertirla en un objeto Java. Para ello necesitaremos usar clases como ``KeyStore`` y ``X509EncodedKeySpec``. Por ejemplo, podemos añadir este método a nuestra clase ``GestorCifrado``.

.. code-block:: java

	public PublicKey convertirDesdeBytes(byte[] bytesClave) throws NoSuchAlgorithmException, InvalidKeySpecException{
		KeyFactory fabricaClaves=
			KeyFactory.getInstance("RSA");
		X509EncodedKeySpec claveCodificada=
				new X509EncodedKeySpec(bytesClave);
		PublicKey clave=
			fabricaClaves.generatePublic(
				claveCodificada);
		return clave;
	}	

Aparte de enviar nuestras claves públicas como secuencias de bytes, también se podría utilizar la serialización Java para enviar un objeto a través de la red.


La clase Servidor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public class Servidor {
		ServerSocket socketServidor;
		
		private final int PUERTO=9876;
		public void escuchar(){
			try {
				socketServidor=new ServerSocket(PUERTO);
				while (true)
				{
					Socket conexion=socketServidor.accept();
					Peticion p=new Peticion(conexion);
					Thread hiloAsociado=
							new Thread(p);
					hiloAsociado.start();
				}
			} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static void main(String[] args){
			Servidor s=new Servidor();
			s.escuchar();
		}
	}
	

La clase Peticion
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public class Peticion implements Runnable{
		Socket conexion;
		GestorCifrado gestorCifrado;
		Cipher cifradorMensajesParaCliente;
		public Peticion (Socket s) throws NoSuchAlgorithmException, NoSuchPaddingException{
			this.conexion=s;
			gestorCifrado=new GestorCifrado();
		}
		@Override
		public void run() {
			/*Todo servidor empieza esperando
			 * que el cliente le envíe la clave pública
			 */
			System.out.println("Llego una conexion!");
			try {
				InputStream flujoLectura=
						conexion.getInputStream();
				OutputStream flujoEscritura=
						conexion.getOutputStream();
				BufferedReader lector=
						new BufferedReader(
								new InputStreamReader(
										flujoLectura
									)
				);
				
				PrintWriter escritor=
						new PrintWriter(
								flujoEscritura
				);
				/* Primero llega una línea con la longitud.
				 * Esa línea no puede tener más de 6
				 * caracteres 
				 */
				String linea=lector.readLine();
				System.out.println("Longitud clave:"+linea);
				int longitudClave=Integer.parseInt(linea);
				byte[] bytesClave=new byte[longitudClave];
				flujoLectura.read(bytesClave);
							
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
La clase Cliente
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	public final class Cliente {
		private String ipServidor="10.13.0.20";
		private int puerto=9876;
		Cipher cifradorMensajesParaServidor;
		public void enviarMensaje(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException{
			Socket conexion;
			GestorCifrado gestorCifrado=
					new GestorCifrado();
			InetSocketAddress direccion=
					new InetSocketAddress(ipServidor, puerto);
			try {
				conexion=new Socket();
				conexion.connect(direccion);
				/* Para enviar y recibir necesitaremos flujos*/
				InputStream flujoLectura=
						conexion.getInputStream();
				OutputStream flujoEscritura=
						conexion.getOutputStream();
				PrintWriter escritor=
						new PrintWriter(flujoEscritura);
				PublicKey clavePublicaCliente=
						gestorCifrado.getPublica();
				byte[] bytesClavePublicaCliente=
						clavePublicaCliente.getEncoded();
				int longitudClave=
						bytesClavePublicaCliente.length;
				System.out.println(
						"La clave del cliente tiene "+
						longitudClave+" bytes."
				);
				escritor.println(longitudClave);
				escritor.flush();
				flujoEscritura.write(bytesClavePublicaCliente);
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} /*Fin del método enviarMensaje*/
		public static void main(String[] argumentos) 
				throws NoSuchAlgorithmException, NoSuchPaddingException{
			Cliente c=new Cliente();
			c.enviarMensaje("Hola servidor");
		}
	}
	

Política de seguridad.
------------------------------------------------------------


Programación de mecanismos de control de acceso.
------------------------------------------------------------

Pruebas y depuración.
------------------------------------------------------------
