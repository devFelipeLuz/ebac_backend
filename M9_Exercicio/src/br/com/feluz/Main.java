package br.com.feluz;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Converte converte = new Converte();

        System.out.println("Digite um numero inteiro: ");
        int numeroInicial = reader.nextInt();
        converte.setNumeroInicial(numeroInicial);
        converte.converteNumero();
        converte.printFinalNumber();
    }
}
