package es.ies.multiproceso;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class SumadorTest {

	@Test
	public void test() {
		int n1=1;
		int n2=5;
		int resultadoObtenido;
		resultadoObtenido=Sumador.sumar(
				n1, n2);
		Assert.assertEquals(15, resultadoObtenido);
		resultadoObtenido=Sumador.sumar(
				n2, n1);
		Assert.assertEquals(15, resultadoObtenido);
		n1=-5;
		n2=-1;
		resultadoObtenido=Sumador.sumar(
				n2, n1);
		Assert.assertEquals(-15, resultadoObtenido);
		resultadoObtenido=Sumador.sumar(
				n1, n2);
		Assert.assertEquals(-15, resultadoObtenido);
		
		
		
	}

}
