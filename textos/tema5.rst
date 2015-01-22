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

Principales aplicaciones de la criptografía.
----------------------------------------------------

Protocolos criptográficos.
----------------------------------------------------

Política de seguridad.
------------------------------------------------------------


Programación de mecanismos de control de acceso.
------------------------------------------------------------

Encriptación de información.
------------------------------------------------------------

Protocolos seguros de comunicaciones.
------------------------------------------------------------

Programación de aplicaciones con comunicaciones seguras.
------------------------------------------------------------

Pruebas y depuración.
------------------------------------------------------------
