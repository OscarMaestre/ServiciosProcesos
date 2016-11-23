package com.examenes;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ContadorAparicionesPalabraTest {

	@Test
	public void test() {
		long apariciones;
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"hola hola", "hola");
		Assert.assertEquals(2, apariciones);
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"ola ola", "hola");
		Assert.assertEquals(0, apariciones);
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"Java es un lenguaje", "java");
		Assert.assertEquals(0, apariciones);
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"Java es un lenguaje", "Java");
		Assert.assertEquals(1, apariciones);
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"Java es un lenguaje ", "lenguaje");
		Assert.assertEquals(1, apariciones);
		apariciones=ContadorAparicionesPalabra.contarApariciones(
				"aaa", "aa");
		Assert.assertEquals(2, apariciones);
		
		
		
	}
}
