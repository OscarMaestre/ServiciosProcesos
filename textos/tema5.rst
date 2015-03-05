Utilización de técnicas de programación segura
================================================

Introducción
-----------------------------------------------------
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

ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789-
BCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789-A


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
Por fortuna Java dispone de clases ya prefabricadas que facilitan enormemente el que dos aplicaciones intercambios datos de forma segura a través de una red. Se deben considerar los siguientes puntos:

* El servidor debe tener su propio certificado. Si no lo tenemos, se puede generar primero una pareja de claves con la herramienta ``keytool``, como se muestra en la figura adjunta. La herramienta guardará la pareja de claves en un almacén (el cual tiene su propia clave). Despues generaremos un certificado a partir de esa pareja con ``keytool -export -file certificadoservidor.cer -keystore almacenclaves``.
* El código del servidor necesitará indicar el fichero donde se almacenan las claves y la clave para acceder a ese almacén.
* El cliente necesita indicar que confía en el certificado del servidor. Dicho certificado del servidor puede estar guardado (por ejemplo) en el almacén de claves del cliente.
* Aunque no suele hacerse también podría hacerse a la inversa y obligar al cliente a tener un certificado que el servidor pudiera importar, lo que aumentaría la seguridad.
    
.. figure:: ../imagenes/generacion_clave.png
   :figwidth: 50%
   :align: center
   
   Generando la pareja de claves del servidor.
   
   
Los pasos desglosados implican ejecutar estos comandos en el servidor::

    # El servidor genera una pareja de claves que se almacena en un
    #fichero llamado "clavesservidor". Dentro del fichero se indica
    #un alias para poder referirnos a esa clave fácilmente
    keytool -genkeypair -keyalg RSA
         -alias servidor -keystore clavesservidor
    
    #El servidor genera su "certificado", es decir un fichero que
    #de alguna forma indica quien es él. El certificado se almacena
    #en un fichero llamado clavesservidor y a partir de él queremos
    #generar el certificado de un alias que tiene que haber llamado servidor
    keytool --exportcert -alias servidor
         -file servidor.cer -keystore clavesservidor


En el cliente daremos estos pasos::

    #Se genera una pareja de claves (en realidad no nos hace falta solo
    #queremos tener un almacén de claves.
    keytool -genkeypair -keyalg RSA -alias cliente -keystore clavescliente
    
    #Se importa el certificado del servidor indicando que pertenece a
    #la lista de certificados confiables.
    keytool -importcert -trustcacerts -alias servidor -file servidor.cer -keystore clavescliente
    
Una vez creados los ficheros iniciales se deben dar los siguientes pasos en Java (servidor y cliente van por separado):

1. El servidor debe cargar su almacén de claves (el fichero ``clavesservidor``)
2. Ese almacén (cargado en un objeto Java llamado ``KeyStore``), se usará para crear un gestor de claves (objeto ``KeyManager``), el cual se obtiene a partir de una "fábrica" llamada ``KeyManagerFactory``.
3. Se creará un contexto SSL (objeto ``SSLContext``) a partir de la fábrica comentada.
4. El objeto ``SSLContext`` permitirá crear una fábrica de sockets que será la que finalmente nos permita tener un ``SSLServerSocket``, es decir un socket de servidor que usará cifrado.
    
    
El código Java del servidor sería algo así:

.. code-block:: java

    public OtroServidor (String rutaAlmacen, String claveAlmacen){
		this.rutaAlmacen=rutaAlmacen;
		this.claveAlmacen=claveAlmacen;
	}
	
	public SSLServerSocket getServerSocketSeguro() 
			throws KeyStoreException, NoSuchAlgorithmException, 
			CertificateException, IOException, 
			KeyManagementException, UnrecoverableKeyException
	{
		SSLServerSocket serverSocket=null;
		/* Paso 1, se carga el almacén de claves*/
		FileInputStream fichAlmacen=
				new FileInputStream(this.rutaAlmacen);
		/* Paso 1.1, se crea un almacén del tipo por defecto 
		 * que es un JKS (Java Key Store), a día de hoy*/
		KeyStore almacen=KeyStore.getInstance(KeyStore.getDefaultType());
		almacen.load(fichAlmacen, claveAlmacen.toCharArray());
		/* Paso 2: obtener una fábrica de KeyManagers que ofrezcan
		 * soporte al algoritmo por defecto*/
		KeyManagerFactory fabrica=
				KeyManagerFactory.getInstance(
				    KeyManagerFactory.getDefaultAlgorithm());
		fabrica.init(almacen, claveAlmacen.toCharArray());
		/* Paso 3:Intentamos obtener un contexto SSL
		 * que ofrezca soporte a TLS (el sistema más 
		 * seguro hoy día) */
		SSLContext contextoSSL=SSLContext.getInstance("TLS");
		contextoSSL.init(fabrica.getKeyManagers(), null, null);
		/* Paso 4: Se obtiene una fábrica de sockets que permita
		 * obtener un SSLServerSocket */
		SSLServerSocketFactory fabricaSockets=
				contextoSSL.getServerSocketFactory();
		serverSocket=
			(SSLServerSocket) 
				fabricaSockets.createServerSocket(puerto);
		return serverSocket;
	}
	public void escuchar() 
	    throws KeyManagementException, UnrecoverableKeyException, 
                KeyStoreException, NoSuchAlgorithmException, 
		CertificateException, IOException
	{
		SSLServerSocket socketServidor=this.getServerSocketSeguro();
		BufferedReader entrada;
		PrintWriter salida;
		while (true){
			Socket connRecibida=socketServidor.accept();
			System.out.println("Conexion segura recibida");
			entrada=
                            new BufferedReader(
                            new InputStreamReader(connRecibida.getInputStream()));
			salida=
                            new PrintWriter(
                                new OutputStreamWriter(
                                connRecibida.getOutputStream()));
			String linea=entrada.readLine();
			salida.println(linea.length());
			salida.flush();
		}
	}

En el cliente se tienen que dar algunos pasos parecidos:

1. En primer lugar se carga el almacén de claves del cliente (que contiene el certificado del servidor y que es la clave para poder "autenticar" el servidor)
2. El almacén del cliente se usará para crear un "gestor de confianza" (``TrustManager``) que Java usará para determinar si puede confiar o no en una conexión. Usaremos un ``TrustManagerFactory`` que usará el almacén del cliente para crear objetos que puedan gestionar la confianza.
3. Se creará un contexto SSL (``SSLContext``) que se basará en los ``TrustManager`` que pueda crear la fábrica.
4. A partir del contexto SSL el cliente ya puede crear un socket seguro (``SSLSocket``) que puede usar para conectar con el servidor de forma segura.

El código del cliente sería algo así:

.. code-block:: java

    public class OtroCliente {
	String almacen="/home/usuario/clavescliente";
	String clave="abcdabcd";
	SSLSocket conexion;
	public OtroCliente(String ip, int puerto) 
			throws UnknownHostException, IOException,
			KeyManagementException, NoSuchAlgorithmException, 
			KeyStoreException, CertificateException{
		
		conexion=this.obtenerSocket(ip,puerto);
	}
	/* Envía un mensaje de prueba para verificar que la conexión
	 * SSL es correcta */
	public void conectar() throws IOException{
		System.out.println("Iniciando..");
		BufferedReader entrada;
		PrintWriter salida;
		entrada=new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		salida=new PrintWriter(new OutputStreamWriter(conexion.getOutputStream()));
		/* De esta linea se intenta averiguar la longitud*/
		salida.println("1234567890");
		salida.flush();
		/* Si todo va bien, el servidor nos contesta el numero*/
		String num=entrada.readLine();
		int longitud=Integer.parseInt(num);
		System.out.println("La longitud devuelta es:"+longitud);
		
	}
	public SSLSocket obtenerSocket(String ip, int puerto) 
			throws KeyStoreException, NoSuchAlgorithmException, 
			CertificateException, IOException, KeyManagementException
	{
		System.out.println("Obteniendo socket");
		SSLSocket socket=null;
		/* Paso 1: se carga al almacén de claves 
		 * (que recordemos debe contener el 
		 * certificado del servidor)*/
		KeyStore almacenCliente=KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream ficheroAlmacenClaves=
				new FileInputStream( this.almacen );
		almacenCliente.load(ficheroAlmacenClaves, clave.toCharArray());
		System.out.println("Almacen cargado");
		/* Paso 2, crearemos una fabrica de gestores de confianza
		 * que use el almacén cargado antes (que contiene el
		 * certificado del servidor)
		 */
		TrustManagerFactory fabricaGestoresConfianza=
				TrustManagerFactory.getInstance(
						TrustManagerFactory.getDefaultAlgorithm());
		fabricaGestoresConfianza.init(almacenCliente);
		System.out.println("Fabrica Trust creada");
		/*Paso 3: se crea el contexto SSL, que ofrezca
		 * soporte al algoritmo TLS*/
		SSLContext contexto=SSLContext.getInstance("TLS");
		contexto.init(
				null, fabricaGestoresConfianza.getTrustManagers(), null);
		/* Paso 4: Se crea un socket que conecte con el servidor*/
		System.out.println("Contexto creado");
		SSLSocketFactory fabricaSockets=
				contexto.getSocketFactory();
		socket=(SSLSocket) fabricaSockets.createSocket(ip, puerto);
		/* Y devolvemos el socket*/
		System.out.println("Socket creado");
		return socket;
	}
    }



Firmado de aplicaciones
-----------------------------

Utilizando la criptografía de clave pública es posible "firmar" aplicaciones. El firmado es un mecanismo que permite al usuario de una aplicación el verificar que la aplicación no ha sido alterada desde que el programador la creó (virus o programas malignos, personal descontento con la empresa, etc...). 

Antes de efectuar el firmado se debe disponer de un par de claves generadas con la herramienta ``keytool`` mencionada anteriormente. Supongamos que el almacén de claves está creado y que en él hay uno o varios *alias* creados. El proceso de firmado es el siguiente:

1. Crear la aplicación, que puede estar formada por un conjunto de clases pero que en última instancia tendrá un ``main``.
2. Empaquetar la aplicación con ``jar cfe Aplicacion.jar com.ies.Aplicacion DirectorioPaquete``. Este comando crea un fichero (``f``) JAR en el cual el punto de entrada (``e``) es la clase ``com.ies.Aplicacion`` (que es la que tendrá el ``main``).
3. Puede comprobarse que la aplicación dentro del JAR se ejecuta correctamente con ``java -jar Aplicacion.jar``.
4. Ahora se puede ejecutar ``jarsigner Aplicacion.jar <alias>``.

Con estos pasos se tiene un aplicación firmada que el usuario puede verificar si así lo desea. De hecho, si se extrae el contenido del JAR con ``jar -xf Aplicacion.jar`` se extraen los archivos ``.class`` y un fichero ``META-INF/Manifest`` que se puede abrir con un editor para ver que realmente está firmado.

Para que otras personas puedan comprobar que nuestra aplicacion es correcta los programadores deberemos exportar un certificado que los usuarios puedan importar para hacer el verificado. Recordemos que el comando es::

	keytool -exportcert -keystore ..\Almacen.store -file Programador.cer -alias Programador

Verificado de aplicaciones
--------------------------------

Si ahora otro usuario desea ejecutar nuestra aplicación deberá importar nuestro certificado. El proceso de verificado es simple:

1. El usuario importa el certificado.
2. Ahora que tiene el certificado puede comprobar la aplicación con ``jarsigner -verify Aplicacion.jar <alias_del_programador>``

El comando deberá responder con algo como ``jar verified``. Sin embargo si no tenemos un certificado firmado por alguna autoridad de certificación (CA) la herramienta se quejará de que algunos criterios de seguridad no se cumplen.


Ejercicio
-----------------
Intenta extraer el archivo JAR y reemplaza el ``.class`` por alguna otra clase. Vuelve a crear el archivo .JAR y vuelve a intentar verificarlo, ¿qué ocurre?

Recordatorio
---------------

Hemos hecho el proceso de firmado y verificado con **certificados autofirmados**, lo cual es útil para practicar pero **completamente inútil desde el punto de vista de la seguridad**. Para que un certificado sea seguro debemos hacer que previamente alguna autoridad de certificación nos lo firme primero (para lo cual suele ser habitual el tener que pagar). 


Política de seguridad.
------------------------------------------------------------


Programación de mecanismos de control de acceso.
------------------------------------------------------------

Pruebas y depuración.
------------------------------------------------------------
