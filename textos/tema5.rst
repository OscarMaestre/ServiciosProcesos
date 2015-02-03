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

Programación de aplicaciones con comunicaciones seguras.
------------------------------------------------------------


Política de seguridad.
------------------------------------------------------------


Programación de mecanismos de control de acceso.
------------------------------------------------------------

Pruebas y depuración.
------------------------------------------------------------
