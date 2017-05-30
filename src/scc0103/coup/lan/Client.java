package scc0103.coup.lan;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
	
	public static void main(String[] args) {
		 	
	     Socket cliente = null;
		try {
			cliente = new Socket("192.168.56.1", 30000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     System.out.println("O cliente se conectou ao servidor!");
	     
	     Scanner teclado = new Scanner(System.in);
	     PrintStream saida = null;
		try {
			saida = new PrintStream(cliente.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     while (teclado.hasNextLine()) {
	       saida.println(teclado.nextLine());
	     }
	     
	     saida.close();
	     teclado.close();
	     try {
			cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
