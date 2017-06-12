package ssc0103.coup.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MinhaClass {

	public Integer metodo1() {
		return 1;
	}

	public Integer metodo2() {
		return 15;
	}

	static public Double metodoEstatico() {
		return 5.655555;
	}

	public void chamaUmMetodoDestaClass(Method naoSeiQueMetodoEEste) {
		try {
			// Métodos não-estáticos precisam de uma instância de referência
			Integer valorDeRetorno = (Integer) naoSeiQueMetodoEEste.invoke(this);
			System.out.println(valorDeRetorno);

		} catch (IllegalAccessException | InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		MinhaClass instancia = new MinhaClass();

		Method metodo;
		try {
			// Pegar um método específico
			metodo = MinhaClass.class.getDeclaredMethod("metodo1", (Class<?>[]) null);
			instancia.chamaUmMetodoDestaClass(metodo);

			// Pegar outro método específico
			metodo = MinhaClass.class.getDeclaredMethod("metodo2", (Class<?>[]) null);
			instancia.chamaUmMetodoDestaClass(metodo);

			// Metodos estaticos não precisam de instâncias
			metodo = MinhaClass.class.getDeclaredMethod("metodoEstatico", (Class<?>[]) null);
			System.out.println(metodo.invoke(null));

			// Para pegar todos os métodos de uma classe:
			Method[] todosOsMetodosDeclarados = MinhaClass.class.getDeclaredMethods();

			// Da pra fazer umas coisas doidas a partir disso:
			for (Method m : todosOsMetodosDeclarados)
				System.out.println(
					"Metodo da classe " + m.getDeclaringClass().getName() + 
					", com o nome " + m.getName() + 
					", e retorna " + m.getReturnType().getName());

		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
	}
}