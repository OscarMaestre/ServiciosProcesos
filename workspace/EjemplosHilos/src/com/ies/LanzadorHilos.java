package com.ies;

import java.util.Random;

class HiloCalculo implements Runnable{
	public void run() {
		String miNombre;		
		miNombre=Thread.currentThread().getName();
		for (double d=0.0d; d<10; d=d+0.05){
			double r=Math.sqrt(d);
			System.out.print("Soy "+miNombre);
			System.out.println(" y voy por el :"+d);
		}
	}
}
public class LanzadorHilos {
	public static void main(String[] args) throws InterruptedException {
		Thread hilo1, hilo2;
		Random generador;
		hilo1=new Thread(new HiloCalculo());
		hilo1.setName("Hilo 1");
		hilo1.start();
		hilo2=new Thread(new HiloCalculo());
		hilo2.setName("Hilo 2");
		hilo1.setPriority(Thread.MAX_PRIORITY);
		hilo2.start();
		hilo1.join();
		System.out.println("Paro el hilo 2");
		hilo2.sleep(3000);
		hilo2.join();
		
		System.out.println("Soy el padre ");
		System.out.println("Y sé que mis hilos acabaron");
	}
}
