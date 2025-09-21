package br.com.fluz;

public class PrimeiraClasse {

	public static void main(String[] args) {
		String ambiente = args[0];
				
				if (ambiente.equalsIgnoreCase("DEV")) {
					
					System.out.print("Inicializando em ambiente de Desenvovimento...");
				} else if (ambiente.equalsIgnoreCase("Test")) {
					System.out.print("Inicializando em ambiente de Testes...");
				} else {
					System.out.print("Você deve especificar em qual ambiente o projeto deve inicializar: Desenvovimento ou Testes");
				}
		

	}

}
