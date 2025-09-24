package Exercício1;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner r = new Scanner(System.in);

        System.out.println("Digite os nomes separados por vírgula:");
        String res = r.nextLine();
        String[] nomes = res.split(", ");
        String[] nomes2 = res.split(", ");

        List<String> lista = Arrays.asList(nomes);
        List<String> lista2 = Arrays.asList(nomes2);

        //Com bubbleSort
        Ordenador.bubbleSortStrings(nomes);

        //Sem bubbleSort
        Arrays.sort(nomes2);

        System.out.println("***** Exercício com BubbleSort *****");
        lista.forEach(n -> System.out.println(n));
        System.out.println("***** Exercício sem BubbleSort *****");
        lista2.forEach(n -> System.out.println(n));
    }
}
