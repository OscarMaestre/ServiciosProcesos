package com.ies;

import java.util.Random;

class CalculadorAreas implements Runnable{
	float base, altura, area;
	public int contador=0;
	public CalculadorAreas(float b, float a){

		this.base=b;
		this.altura=a;
	}
	public synchronized void incrementarContador(){
		contador = contador +1;
	}
	@Override
	public void run() {
		Random generador;
		generador=new Random();
		area=base * altura / 2;
		try {
			Thread.sleep(generador.nextInt(650));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.incrementarContador();
		
	}
}
public class AreasEnParalelo{
	public static void main(String[] args) throws InterruptedException{
		CalculadorAreas ca=new CalculadorAreas(1,2);
		final int MAX_HILOS = 10000;
		Thread[] hilos=new Thread[MAX_HILOS];
		for (int i=0; i<MAX_HILOS; i++){
			hilos[i]=new Thread(ca);
			hilos[i].start();
		}
		for (int i=0; i<MAX_HILOS; i++){
			hilos[i].join();
		}
		System.out.println(
			"Total de calculos:" +
			ca.contador	);
	}
}